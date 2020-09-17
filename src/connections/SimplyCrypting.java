package connections;

import java.io.*;

/**
 * Created by suren on 11/3/17.
 */
public class SimplyCrypting {
    public static void main(String[] args) throws IOException {

        int startIndex = 1;
        int endIndex = 96;
        String packName = "Final/10x10hard";
        String encryptedPackName = "Final/10x10hardEnc";
        String decodedPackName = "Final/10x10hardDec";
        int bad = 0;

        String[] levels = new String [225];
        boolean isEvent = true;
        File root = new File("/home/suren/Documents/temp/connections/Final/original");
        File encodedRoot = new File("/home/suren/Documents/temp/connections/Final/originalEncoded");
        StringBuilder eventString = new StringBuilder();
        for(File pack : root.listFiles()) {
            if (pack.isDirectory()) {
                for (File level : pack.listFiles()) {

                    if (level.getName().contains("sol")) {
                        continue;
                    }
                    BufferedReader input = new BufferedReader(new FileReader(level.getAbsoluteFile()));
                    //System.out.println(level.getAbsolutePath());

                    StringBuilder output = new StringBuilder();
                    String line;
                    while ((line = input.readLine()) != null) {
                        output.append(line);
                        output.append("\n");
                    }

                    String text = output.toString();
                    String encodedText = xorMessage2(text, "Trytodosomethingelseinnext2018year");
                    if (isEvent) {
                        int index = Integer.parseInt(level.getName().substring(5, level.getName().indexOf('.'))) - 1;
                        System.out.println(level.getName() + " " + index);
                        levels[index] = encodedText;
                    } else {
                        String encodedFilename = encodedRoot + "/" + pack.getName() + "/" + level.getName();
                        writeToFile2(encodedFilename, encodedText);

                        input = new BufferedReader(new FileReader(encodedFilename));
                        output = new StringBuilder();
                        while ((line = input.readLine()) != null) {
                            output.append(line);
                            output.append("\n");
                        }
                    }

                    input.close();

                    String decodedText = xorMessage2(output.toString(), "Trytodosomethingelseinnext2018year");
                    //System.out.println(decodedText);
                    if (!isEvent && !decodedText.substring(0, decodedText.length() - 1).equals(text)) {
                        System.out.println("Error " + level.getAbsolutePath());
//                    System.out.println(decodedText);
//                    System.out.println("------------------");
//                    System.out.println(text);
                    }
                }
            }
            if (isEvent) {
                for (int i = 0; i < levels.length; i++) {
                    eventString.append(levels[i] + "\n");
                }

                String encodedFilename = encodedRoot + "/" + pack.getName() + "/" + "puzzles.txt";
                writeToFile2(encodedFilename, eventString.toString());

            }
        }


    }


    public static String xorMessage(String message, String key) {
        try {
            if (message == null || key == null) return null;

            char[] keys = key.toCharArray();
            char[] mesg = message.toCharArray();

            int ml = mesg.length;
            int kl = keys.length;
            char[] newmsg = new char[ml];

            for (int i = 0; i < ml; i++) {

                    newmsg[i] = (char) (mesg[i] ^ keys[i % kl]);
                
            }
            return new String(newmsg);
        } catch (Exception e) {
            return null;
        }
    }

    public static String xorMessage2(String message, String key) {
        try {
            if (message == null || key == null) return null;

            char[] keys = key.toCharArray();
            char[] mesg = message.toCharArray();

            int ml = mesg.length;
            int kl = keys.length;
            char[] newmsg = new char[ml];
            for (int i = 0; i < ml; i++) {
                if (newmsg[i] != '\n') {
                    newmsg[i] = (char) (mesg[i] ^ keys[0 % kl]);
                }
            }
            return new String(newmsg);
        } catch (Exception e) {
            return null;
        }
    }

    public static void writeToFile2(String filename, String level) {

        File file = new File(filename);
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));
            output.write(level +"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
