package protector;

import java.io.*;
import java.util.*;

/**
 * Created by suren on 4/13/18.
 */
public class JavaFileNameEncryptions {
    private static int key = 20000;

    Map<String, String> fileNameMap = new HashMap<>();
    Map<String, String> idMap = new HashMap<>();
    String valuesInputFolderPath;
    String valuesOutFolderPath;
    String encryptionFileName;
    String encryptionMethodName;

    public JavaFileNameEncryptions(String valuesFolder, String outputFolder, String encryptionFileName, String encryptionMethodName) {
        valuesInputFolderPath = valuesFolder;
        valuesOutFolderPath = outputFolder;
        this.encryptionMethodName = encryptionMethodName;
        this.encryptionFileName = encryptionFileName;
        try {
            renameJavaFileNames(valuesInputFolderPath + "/java");
        } catch (IOException e) {
            e.printStackTrace();
        }
        javaCodeRenaming(valuesInputFolderPath + "/java");
        try {
            renameStringsInJavaFile(new File(valuesInputFolderPath + "/AndroidManifest.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JavaFileNameEncryptions le = new JavaFileNameEncryptions("/home/suren/Documents/plumber_exp/main", "", "Encryptor", "agflkrtqxzc$1$");
    }

    private void renameJavaFileNames(String path) throws IOException {

        Set<String> names = nameGenerator(1000);
        Iterator<String> iterator = names.iterator();
         File root = new File(path);
        for (File file : root.listFiles()) {
            if(file.isDirectory()) {
                renameJavaFileNames(file.getAbsolutePath());
            }else {

                System.out.println(file.getName());
                if(file.getName().startsWith("apwefmfgjj")) {
                    continue;
                }
                if (file.getName().endsWith(".java")) {

                    //Renaming file
                    String oldName = file.getName().substring(0, file.getName().length() - 5);
                    String newName = iterator.next();
                    fileNameMap.put(oldName, newName);
                    System.out.println("Map: " + oldName + " " + newName);
                    File newFile = new File(path + "/" + newName + ".java");
                    file.renameTo(newFile);
                    System.out.println(file.getAbsolutePath());
                }

            }
        }

    }

    private void javaCodeRenaming(String path) {

        File root = new File(path);
        for (File file : root.listFiles()) {
            if(file.isDirectory()) {
                javaCodeRenaming(file.getAbsolutePath());
            }else {

                System.out.println(file.getName());
                if (file.getName().endsWith(".java")) {
                    try {
                        renameStringsInJavaFile(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    private void renameStringsInJavaFile(File javaFile) throws IOException {

        System.out.println("Renaming FILE CONTENT... " + javaFile.getAbsolutePath());
        String file = readFile(javaFile.getAbsolutePath());
        StringBuilder temp = new StringBuilder(file);
        boolean changesMade = false;
        for (String key : fileNameMap.keySet()) {

            int start = 1;
            while (start != -1) {

                start = temp.indexOf(key, start);
                if (start == -1) {
                    break;
                }
                if (Character.isLetter(temp.charAt(start - 1)) || Character.isLetter(temp.charAt(start + key.length()))) {
                    start = start + key.length();
                    continue;
                }else {
                    temp = temp.replace(start, start + key.length(), fileNameMap.get(key));
                    System.out.println(key);
                    start = start + fileNameMap.get(key).length();
                    changesMade = true;
                }
            }
        }
//        for (String key : fileNameMap.keySet()) {
//            file.replaceAll("\p{Punct}" + key)
//
//            if (file.contains(" " + key + " ")) {
//                System.out.println("renaming in java..." + key + " " + fileNameMap.get(key));
//                file = file.replaceAll(" " + key + " ", " " + fileNameMap.get(key) + " ");
//                changesMade = true;
//            }
//
//            if (file.contains(" " + key + ",")) {
//                System.out.println("renaming in java..." + key + " " + fileNameMap.get(key));
//                file = file.replaceAll(" " + key + ",", " " + fileNameMap.get(key) + ",");
//                changesMade = true;
//            }
//
//            if (file.contains(" " + key + "․")) {
//                System.out.println("renaming in java..." + key + " " + fileNameMap.get(key));
//                file = file.replaceAll(" " + key + "․", " " + fileNameMap.get(key) + "․");
//                changesMade = true;
//            }
//
//            if (file.contains(" " + key + ":")) {
//                System.out.println("renaming in java..." + key + " " + fileNameMap.get(key));
//                file = file.replaceAll(" " + key + ":", " " + fileNameMap.get(key) + ":");
//                changesMade = true;
//            }
//
//            if (file.contains(" " + key + "{")) {
//                System.out.println("renaming in java..." + key + " " + fileNameMap.get(key));
//                file = file.replaceAll(" " + key + "\\{", " " + fileNameMap.get(key) + "{");
//                changesMade = true;
//            }
//
//            if (file.contains(" " + key + "(")) {
//                System.out.println("renaming in java..." + key + " " + fileNameMap.get(key));
//                file = file.replaceAll(" " + key + "\\(", " " + fileNameMap.get(key) + "(");
//                changesMade = true;
//            }
//
//        }
        if (changesMade) {
            writeToFile(temp.toString(), javaFile.getAbsolutePath());

        }

    }

    public static Set<String> nameGenerator(int number) {
        Set<String> names = new TreeSet<>();
        Random rand = new Random();
        while(names.size() < number) {
            StringBuilder name = new StringBuilder();
            int length = rand.nextInt(10) + 5;
            for (int i= 0; i < length; i++) {
                name.append((char)(rand.nextInt(122-97) + 97));
            }
            names.add(name.toString());
        }
        return names;
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
