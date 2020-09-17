package SpiderProtector;

import java.io.*;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by suren on 4/17/18.
 */
public class RemoveLogs {

    public RemoveLogs(String valuesFolder, String outputFolder) {

        removeLogs(valuesFolder + "/java");

    }

    public void removeLogs(String path) {
        File root = new File(path);
        for (File file : root.listFiles()) {
            if(file.isDirectory()) {
                removeLogs(file.getAbsolutePath());
            }else {

                System.out.println(file.getName());

                if (file.getName().endsWith(".java")) {

                    String f = null;
                    try {
                        f = readFile(file.getAbsolutePath());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    boolean changesMade = false;
                    int startIndex = 1;
                    //Removing souts
                    while(startIndex != -1) {
                        startIndex = f.indexOf("System.out.print",startIndex);
                        if (startIndex == -1) {
                            break;
                        } else {

                            int endIndex = f.indexOf(";", startIndex);
                            StringBuilder temp = new StringBuilder(f);
                            System.out.println("Removing sout..." + temp.substring(startIndex, endIndex + 1));
                            temp.replace(startIndex,endIndex, "");
                            f = temp.toString();
                            changesMade = true;
                        }
                    }

                    //Removing Logs
                    startIndex = 1;
                    while(startIndex != -1) {
                        startIndex = f.indexOf("Log.",startIndex);
                        if (startIndex == -1) {
                            break;
                        } else {

                            int endIndex = f.indexOf(";", startIndex);
                            StringBuilder temp = new StringBuilder(f);
                            System.out.println("Removing Log..." + temp.substring(startIndex, endIndex + 1));
                            temp.replace(startIndex,endIndex, "");
                            f = temp.toString();
                            changesMade = true;
                        }
                    }
                    if (changesMade) {
                        if (changesMade) {
                            writeToFile(f, file.getAbsolutePath());
                        }
                    }

                }

            }
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
}
