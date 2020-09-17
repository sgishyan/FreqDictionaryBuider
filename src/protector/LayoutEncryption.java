package protector;

import java.io.*;
import java.util.*;

/**
 * Created by suren on 4/13/18.
 */
public class LayoutEncryption {

    private static int key = 20000;

    Map<String, String> fileNameMap = new HashMap<>();
    Map<String, String> idMap = new HashMap<>();
    String valuesInputFolderPath;
    String valuesOutFolderPath;
    String encryptionFileName;
    String encryptionMethodName;

    public LayoutEncryption(String valuesFolder, String outputFolder, String encryptionFileName, String encryptionMethodName) {
        valuesInputFolderPath = valuesFolder;
        valuesOutFolderPath = outputFolder;
        this.encryptionMethodName = encryptionMethodName;
        this.encryptionFileName = encryptionFileName;
        try {
            renameLayoutFileNames(valuesInputFolderPath + "/res/layout");
        } catch (IOException e) {
            e.printStackTrace();
        }
        javaCodeRenaming(valuesInputFolderPath + "/java");
        xmlIdsRenaming((valuesInputFolderPath + "/res/layout"));
    }

    private void renameLayoutFileNames(String path) throws IOException {

        Set<String> names = nameGenerator(1000);
        Iterator<String> iterator = names.iterator();
        int nameIndex = 0;
        File root = new File(path);
        System.out.println("Path = " + path);
        for (File file : root.listFiles()) {
            if(file.isDirectory()) {
                renameLayoutFileNames(file.getAbsolutePath());
            }else {

                System.out.println(file.getName());

                if (file.getName().endsWith(".xml")) {



                    //Renaming id's

                    String xmlFile = readFile(file.getAbsolutePath());
                    //Replacing not well formed fragments
                    xmlFile = xmlFile.replaceAll("@android:id/", "@id/");
                    int index = 0;
                    while(index != -1) {
                        index = xmlFile.indexOf("android:id", index);
                        if (index == -1) {
                            break;
                        }
                        int stringStart = xmlFile.indexOf("\"", index);
                        int idStart = stringStart + 6;
                        int idEnd = xmlFile.indexOf("\"", idStart + 1);
                        String id = xmlFile.substring(idStart, idEnd);
                        String newId = null;
                        if (idMap.containsKey(id)) {
                            newId = idMap.get(id);
                        }else {
                            newId = iterator.next();
                            idMap.put(id, newId);
                        }

                        StringBuilder temp = new StringBuilder(xmlFile);
                        temp.replace(idStart, idEnd, newId);
                        xmlFile = temp.toString();
                        System.out.println("Replacing " + id + "  " + newId);
                        index = idEnd + newId.length() - id.length();
                    }

                    writeToFile(xmlFile, file.getAbsolutePath());


                    //Renaming file
                    String oldName = file.getName().substring(0, file.getName().length() - 4);
                    String newName = iterator.next();
                    fileNameMap.put(oldName, newName);
                    File newFile = new File(path + "/" + newName+ ".xml");
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
        System.out.println("Renaming java... " + javaFile.getAbsolutePath());
        String file = readFile(javaFile.getAbsolutePath());
        boolean changesMade = false;

        for (String key : fileNameMap.keySet()) {
            if (file.contains("R.layout." + key + " ")) {
                System.out.println("renaming in java..." + key);
                file = file.replaceAll("R.layout." + key + " ", "R.layout." + fileNameMap.get(key) + " ");
                changesMade = true;
            }

            if (file.contains("R.layout." + key + ")")) {
                System.out.println("renaming in java..." + key);
                file = file.replaceAll("R.layout." + key + "\\)", "R.layout." + fileNameMap.get(key) + ")");
                changesMade = true;
            }

            if (file.contains("R.layout." + key + "}")) {
                System.out.println("renaming in java..." + key);
                file = file.replaceAll("R.layout." + key + "\\}", "R.layout." + fileNameMap.get(key) + "}");
                changesMade = true;
            }

            if (file.contains("R.layout." + key + ",")) {
                System.out.println("renaming in java..." + key);
                file = file.replaceAll("R.layout." + key + ",", "R.layout." + fileNameMap.get(key) + ",");
                changesMade = true;
            }
        }

        for (String key : idMap.keySet()) {
            if (file.contains("R.id." + key + " ")) {
                System.out.println("renaming in java..." + key);
                file = file.replaceAll("R.id." + key + " ", "R.id." + idMap.get(key) + " ");
                changesMade = true;
            }

            if (file.contains("R.id." + key + "\n")) {
                System.out.println("renaming in java..." + key);
                file = file.replaceAll("R.id." + key + "\n", "R.id." + idMap.get(key) + "\n");
                changesMade = true;
            }

            if (file.contains("R.id." + key + "\\")) {
                System.out.println("renaming in java..." + key);
                file = file.replaceAll("R.id." + key + "\\", "R.id." + idMap.get(key) + "\\");
                changesMade = true;
            }

            if (file.contains("R.id." + key + ":")) {
                System.out.println("renaming in java..." + key);
                file = file.replaceAll("R.id." + key + ":", "R.id." + idMap.get(key) + ":");
                changesMade = true;
            }

            if (file.contains("R.id." + key + "}")) {
                System.out.println("renaming in java..." + key);
                file = file.replaceAll("R.id." + key + "\\}", "R.id." + idMap.get(key) + "}");
                changesMade = true;
            }

            if (file.contains("R.id." + key + ",")) {
                System.out.println("renaming in java..." + key);
                file = file.replaceAll("R.id." + key + ",", "R.id." + idMap.get(key) + ",");
                changesMade = true;
            }

            if (file.contains("R.id." + key + ")")) {
                System.out.println("renaming in java..." + key);
                file = file.replaceAll("R.id." + key + "\\)", "R.id." + idMap.get(key) + ")");
                changesMade = true;
            }
        }
        if (changesMade) {
            writeToFile(file, javaFile.getAbsolutePath());
        }

    }

    public void xmlIdsRenaming(String path) {
        File root = new File(path);
        for (File file : root.listFiles()) {
            if (file.isDirectory()) {
                xmlIdsRenaming(file.getAbsolutePath());
            } else {
                try {
                    System.out.println("Renaming Layout... " + file.getName());
                    renameIdsInLayouts(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void renameIdsInLayouts(File fileToRename) throws IOException {
        System.out.println(fileToRename.getAbsolutePath());
        String file = readFile(fileToRename.getAbsolutePath());
        boolean changesMade = false;

        for (String key : idMap.keySet()) {
            if (file.contains(key)) {
                System.out.println("renaming.." + key);
                file = file.replaceAll("/" + key + "\"", "/" + idMap.get(key) +"\"");
                changesMade = true;
            }
        }
        if (changesMade) {
            writeToFile(file, fileToRename.getAbsolutePath());

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

    public static void main(String[] args) {
        LayoutEncryption le = new LayoutEncryption("/home/suren/Documents/plumber_exp/main", "", "__0xfde_", "__0xfde__");
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
