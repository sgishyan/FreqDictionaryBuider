package protector;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

/**
 * Created by suren on 5/8/18.
 */
public class StringEncryption {

    private final String valuesInputFolderPath;
    String importStatement;
    String met;
    int code;
    public StringEncryption(String path, String importText, String method, int encCode) {
        importStatement = importText;
        met = method;
        code = encCode;
        valuesInputFolderPath = path;
        makeEncryption(valuesInputFolderPath );


    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        new StringEncryption("/home/suren/Documents/plumber_exp/main", "import static com.mobiloids.waterpipespuzzle.apwefmfgjjw._1_;",  "_1_", 10);
       // new RemoveLogs("/home/suren/Documents/plumber_exp/main", "");
        //new ValuesEncryption("/home/suren/Documents/plumber_exp/main", "", "Encryptor", "agflkrtqxzc$1$");
       // new LayoutEncryption("/home/suren/Documents/plumber_exp/main", "", "Encryptor", "agflkrtqxzc$1$");
        //new JavaFileNameEncryptions("/home/suren/Documents/plumber_exp/main", "", "Encryptor", "agflkrtqxzc$1$");
    }


    public void makeEncryption(String path) {
        File root = new File(path);
        for (File file : root.listFiles()) {
            if(file.isDirectory()) {
                makeEncryption(file.getAbsolutePath());
            }else {
                try {
                    //System.out.println(file.getName());
                    if (file.getName().endsWith(".java")) {
                        encrypt(file);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void encrypt(File fileToEncrypt) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        System.out.println(fileToEncrypt.getAbsolutePath());
        String file = readFile(fileToEncrypt.getAbsolutePath());
//        if (file.contains("[DO_NOT_ENCODE]")) {
//            return;
//        }
        String[] lines = file.split("\n");
        int index = 0;
        boolean changesMade = false;

        StringBuilder newFile = new StringBuilder(file);

        while (index != -1) {

            int startIndex = newFile.indexOf("\"", index);
            if (startIndex == -1) {
                break;
            }
            int endIndex = newFile.indexOf("\"", startIndex + 1);
            if (endIndex == -1) {
                break;
            }
            //Skipping constant strings
            if(newFile.substring(startIndex - 20, startIndex).contains("@SuppressLint") ||
                    newFile.substring(startIndex - 10, startIndex).contains("case")
                    ||
                    (startIndex > 200 &&
                            newFile.substring(startIndex - 80, startIndex).contains("static") &&
                            newFile.substring(startIndex - 80, startIndex).contains("final") &&
                            newFile.substring(startIndex - 200, startIndex).contains("private class")
                            )
                    ){
                index = endIndex + 1;
                System.out.println("Text to skip.." + newFile.substring(startIndex + 1, endIndex));
                continue;
            }
            String textToEncode = newFile.substring(startIndex + 1, endIndex);
            String encodedString = null;
            System.out.println("Text to encode : " + textToEncode);
            try {
                encodedString = StringProtection._0_(textToEncode, code);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String prefix = met + "(\"";
            String postfix = "\", " + code + ")";
            changesMade = true;
            newFile.replace(startIndex, endIndex + 1, prefix + encodedString + postfix);
            index = endIndex + prefix.length() + postfix.length() + 1;
            //System.out.println(startIndex + " " + endIndex + " " + index);
        }
        if (changesMade) {
            writeToFile(newFile.toString().replaceFirst(";", ";\n" + importStatement), fileToEncrypt.getAbsolutePath());
        }

    }

    private static String readFile(String englishFile) throws IOException {
        BufferedReader dic1 = new BufferedReader(new FileReader(englishFile));
        StringBuilder file = new StringBuilder();
        try {

            String line = dic1.readLine();
            while (line != null) {
                String word = line.trim();
                file.append(word +"\n");
                line = dic1.readLine();
            }
        } finally {
            dic1.close();
            return file.toString();
        }
    }
    private static void writeToFile(ArrayList<String> tokens, String filename) {
        System.out.println("Writing File : " + filename);
        File file = new File(filename);
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));
            for (String token : tokens) {
                output.write(token + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void writeToFile(String fileText, String filename) {
        System.out.println("Writing File : " + filename);
        File file = new File(filename);
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));
            output.write(fileText + "\n");
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
