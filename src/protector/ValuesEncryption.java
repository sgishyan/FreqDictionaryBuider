package protector;

import plumber.engine.PlumberConstructModel;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * Created by suren on 4/12/18.
 */
public class ValuesEncryption {

    private static int key = 20000;

    Map<String, String> renaming = new HashMap<>();
    Map<String, String> encrypting = new HashMap<>();
    Map<String, String> xmlNonEncode = new HashMap<>();
    Set<String> doNotEncode = new TreeSet<>();
    String valuesInputFolderPath;
    String valuesOutFolderPath;
    String encryptionFileName;
    String encryptionMethodName;
    String importStatement;
    public ValuesEncryption(String valuesFolder, String outputFolder, String encryptionFileName, String encryptionMethodName, String importState) {

        importStatement = importState;
        valuesInputFolderPath = valuesFolder;
        valuesOutFolderPath = outputFolder;
        this.encryptionMethodName = encryptionMethodName;
        this.encryptionFileName = encryptionFileName;

        //Making doNotEncode set
        xmlValuesScan(valuesInputFolderPath + "/res/layout");
        //Manifest renaming
        try {
            scanLayout(new File(valuesInputFolderPath + "/AndroidManifest.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        makeEncryption(valuesInputFolderPath + "/res");
        xmlValuesRenaming(valuesInputFolderPath + "/res/layout");
        //Manifest renaming
        try {
            renameLayout(new File(valuesInputFolderPath + "/AndroidManifest.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        //Java source renaming
        javaFileRenaming(valuesInputFolderPath + "/java");


    }

    private void javaFileRenaming(String path) {
        File root = new File(path);
        for (File file : root.listFiles()) {
            if(file.isDirectory()) {
                javaFileRenaming(file.getAbsolutePath());
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


        for (String key : renaming.keySet()) {
            int index = 0;
            while(index != -1) {
                int start = file.indexOf("getString(R.string." + key,index);
                if (start == -1) {
                    break;
                }

                if (file.charAt(start - 1) == '.' ) {
                    String context = "";
                    int contextIndex =  start - 2;
                    while (Character.isJavaIdentifierPart(file.charAt(contextIndex)) ) {
                        context = file.charAt(contextIndex) + context;
                        contextIndex--;
                    }
                    file = file.replace(context + ".getString(R.string." + key, encryptionMethodName + "(" + context + ".getString(R.string." + renaming.get(key) + ")");
                    System.out.println("Context.." + context);
                    changesMade = true;
                }
                index = start + 1;
            }
//            if (file.contains("R.string." + key)) {
//                System.out.println("renaming in java..." + key);
//                file = file.replace("getString(R.string." + key, encryptionMethodName + "(getString(R.string." + renaming.get(key) + ")");
//                // file = file.replace("R.string." + key, "R.string." + renaming.get(key));
//                changesMade = true;
//            }
//            if (file.contains("R.string." + key)) {
//                System.out.println("renaming in java..." + key);
//                file = file.replace("R.string." + key, "R.string." + renaming.get(key) );
//                // file = file.replace("R.string." + key, "R.string." + renaming.get(key));
//                changesMade = true;
//            }
        }

        for (String key : renaming.keySet()) {
            if (file.contains("R.string." + key)) {
                System.out.println("renaming in java..." + key);
                file = file.replace("getString(R.string." + key, encryptionMethodName + "(getString(R.string." + renaming.get(key) + ")");
               // file = file.replace("R.string." + key, "R.string." + renaming.get(key));
                changesMade = true;
            }
            if (file.contains("R.string." + key)) {
                System.out.println("renaming in java..." + key);
                file = file.replace("R.string." + key, "R.string." + renaming.get(key) );
                // file = file.replace("R.string." + key, "R.string." + renaming.get(key));
                changesMade = true;
            }
        }
        if (changesMade) {
            writeToFile(file.replaceFirst(";", ";\n" + importStatement), javaFile.getAbsolutePath());

        }

    }

    public void makeEncryption(String path) {
        File root = new File(path);
        for (File file : root.listFiles()) {
            if(file.isDirectory()) {
                makeEncryption(file.getAbsolutePath());
            }else {
                try {
                    //System.out.println(file.getName());
                    if (file.getName().equals("strings.xml")) {
                        encrypt(file);
                    }
                    if (file.getName().equals("donottranslate.xml")) {
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

        String[] lines = file.split("\n");
        int index = 0;
        boolean namesStarted = false;

        StringBuilder newFile = new StringBuilder();

        ArrayList<String> tokens = new ArrayList<>();
        ArrayList<String> titleTokens = new ArrayList<>();
        Set<String> names = nameGenerator(1000);
        Iterator<String> namesIterator = names.iterator();
        while (index < lines.length) {

            String line = lines[index].trim();
            if (line.contains("\\n")) {
                System.out.println("Special Line.." + line);
                line = line.replace("\\n", "\n");
                line = line.replace("\\'", "'");
                System.out.println("Special Line after.." + line);
            }
            line = line.replace("\\'", "'");
            System.out.println("Special Line after.." + line);
                        if (line.length() < 2) {
                index++;
                continue;
            }
            System.out.println("line = " + line);
            while(line.contains("<string") && !line.contains("/string") && index < lines.length) {
                System.out.println(line);
                index++;
                line += lines[index].trim();
            }

            if (line.contains("name")) {
                namesStarted = true;
                int startTagName = line.indexOf("\"") + 1;
                int endTagName = line.indexOf("\"", startTagName + 1);
                int startText = line.indexOf(">") + 1;
                int endText = line.indexOf("<", startText + 1);

                System.out.println(line.substring(startTagName, endTagName));
                System.out.println(line.substring(startText, endText));
                String key = line.substring(startTagName, endTagName);
                String value = null;

                if(renaming.containsKey(key)) {
                    value = renaming.get(key);
                }
                else {
                    value = namesIterator.next();
                    renaming.put(key, value);

                }
                if (doNotEncode.contains(key)) {
                    System.out.println("Do not encode.." + key);
                 } else {
                    String source = line.substring(startText, endText);
                    if (source.contains("\\n"))
                    {
                        System.out.println("Replacing..");
                        System.out.println("\\n.." + source);

                        endText--;
                        System.out.println("After.." + source);

                    }
//                    if (source.contains("\\"))
//                    {
//                        source = source.replace("\\", "");
//                        System.out.println("Replacing..'");
//                        System.out.println("\\n.." + source);
//                        endText--;
//                        System.out.println("After.." + source);
//
//                    }
                    String enc = apwefmfgjjw._3_(source);
                    String dec = apwefmfgjjw._3_(enc);
                    if (!dec.equals(source)) {
                        System.out.println("-------WRONG ENCODING--------");
                        System.out.println(source);
                        System.out.println(dec);
                        System.out.println("-----------------------------");
                    }else {
                        System.out.println("-------RIGHT ENCODING--------");
                        System.out.println(source);
                        System.out.println(dec);
                        System.out.println("-----------------------------");
                    }


                    encrypting.put(source, enc);
                }
                xmlNonEncode.put(key, line.substring(startText, endText));
              // System.out.println(file.indexOf(line.substring(startText, endText)));

                String name = line.substring(startText, endText);

                System.out.println("Name = " + name );
                line = line.replaceFirst(key, value);
                StringBuilder sb = new StringBuilder(line);
                System.out.println("Line = " + line );
                System.out.println("Line > = " + line.indexOf(">") );
                int start = line.indexOf(name, line.indexOf(">"));
                System.out.println(start + " " + name.length());

                if (!doNotEncode.contains(key)) {
                    sb = sb.replace(start, start + name.length(), encrypting.get(name));
                }
                newFile.append(sb +"\n");
                tokens.add(sb.toString());

            }else {
                if (!namesStarted) {
                    titleTokens.add(line);
                }
            }
            index++;
        }

        Collections.shuffle(tokens);
        tokens.addAll(0, titleTokens);
        tokens.add("</resources>");
        writeToFile(tokens, fileToEncrypt.getAbsolutePath());
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {

        String a = "Hurry up, you don’t";
        String c = apwefmfgjjw._3_(a);
        String b = apwefmfgjjw._3_(c);
       // System.out.println(b.indexOf("\\n"));
       // b = b.replace("\\n", "\n");
       // b = b.replace("\'","'");
        //System.out.println(b.indexOf("\\n"));



        System.out.println(a.equals(b));
        System.out.println(a.length() + " " + b.length());
        for(int i = 0 ; i< a.length(); i++) {
            System.out.println(a.charAt(i) + " " + b.charAt(i));
        }
          System.out.println("Touch the pipes\nto turn them.");
          System.out.println(apwefmfgjjw._3_("ﾫﾐﾊﾜﾗ\uFFDFﾋﾗﾚ\uFFDFﾏﾖﾏﾚﾌﾣﾑﾋﾐ\uFFDFﾋﾊﾍﾑ\uFFDFﾋﾗﾚﾒ\uFFD1"));
          new RemoveLogs("/home/suren/Documents/plumber_exp/main", "");
          new StringEncryption("/home/suren/Documents/plumber_exp/main", "import static com.mobiloids.waterpipespuzzle.apwefmfgjjv._1_;",  "_1_", 10);
          new EncryptImages("/home/suren/Documents/plumber_exp/main", "");
          new LayoutEncryption("/home/suren/Documents/plumber_exp/main", "", "apwefmfgjjw", "_3_");
          new JavaFileNameEncryptions("/home/suren/Documents/plumber_exp/main", "", "apwefmfgjjw", "_3_");
           new ValuesEncryption("/home/suren/Documents/plumber_exp/main", "", "apwefmfgjjw", "_3_", "import static com.mobiloids.waterpipespuzzle.apwefmfgjjw._3_;");
    }

    public static Set<String> nameGenerator(int number) {
        Set<String> names = new TreeSet<>();
        Random rand = new Random();
        while(names.size() < number) {
            StringBuilder name = new StringBuilder();
            int length = 15;
            for (int i= 0; i < length; i++) {
                name.append((char)(rand.nextInt(122-97) + 97));
            }
            names.add(name.toString());
        }
        return names;
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

    public static String encryptString(String str) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Object o = Class.forName("java.lang.StringBuilder").newInstance();
        Method m = o.getClass().getMethod("append", Character.TYPE);
        StringBuilder a = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            m.invoke(o, (char)((int)str.charAt(i) ^ (1<<i) ^ key));
            a.append((char)((int)str.charAt(i) ^ (1<<i) ^ key));
        }
        return o.toString();
    }


    public void xmlValuesRenaming(String path) {
        File root = new File(path);
        for (File file : root.listFiles()) {
            if (file.isDirectory()) {
                xmlValuesRenaming(file.getAbsolutePath());
            } else {
                try {
                    System.out.println("Renaming Layout... " + file.getName());
                    renameLayout(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void renameLayout(File fileToRename) throws IOException {
        System.out.println(fileToRename.getAbsolutePath());
        String file = readFile(fileToRename.getAbsolutePath());
        boolean changesMade = false;

        for (String key : renaming.keySet()) {
            if (file.contains("@string/" + key +"\"")) {
                System.out.println("renaming.." + key);
                file = file.replace("@string/" + key +"\"", "@string/" + renaming.get(key) +"\"");
                changesMade = true;
            }
        }
        if (changesMade) {
            writeToFile(file, fileToRename.getAbsolutePath());

        }

    }

    public void xmlValuesScan(String path) {
        File root = new File(path);
        for (File file : root.listFiles()) {
            if (file.isDirectory()) {
                xmlValuesScan(file.getAbsolutePath());
            } else {
                try {
                    System.out.println("Scanning Layout... " + file.getName());
                    scanLayout(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    private void scanStringsInJavaFile(File javaFile) throws IOException {
        System.out.println("Scanning java... " + javaFile.getAbsolutePath());
        String file = readFile(javaFile.getAbsolutePath());
        boolean changesMade = false;


        for (String key : renaming.keySet()) {
            int index = 0;
            while (index != -1) {
                int start = file.indexOf("getString(R.string." + key, index);
                if (start == -1) {
                    break;
                }

                if (file.charAt(start - 1) == '.') {
                    String context = "";
                    int contextIndex = start - 2;
                    while (Character.isJavaIdentifierPart(file.charAt(contextIndex))) {
                        context = file.charAt(contextIndex) + context;
                        contextIndex--;
                    }
                    file = file.replace(context + ".getString(R.string." + key, encryptionMethodName + "(" + context + ".getString(R.string." + renaming.get(key) + ")");
                    System.out.println("Context.." + context);
                    changesMade = true;
                }
                index = start + 1;
            }
//            if (file.contains("R.string." + key)) {
//                System.out.println("renaming in java..." + key);
//                file = file.replace("getString(R.string." + key, encryptionMethodName + "(getString(R.string." + renaming.get(key) + ")");
//                // file = file.replace("R.string." + key, "R.string." + renaming.get(key));
//                changesMade = true;
//            }
//            if (file.contains("R.string." + key)) {
//                System.out.println("renaming in java..." + key);
//                file = file.replace("R.string." + key, "R.string." + renaming.get(key) );
//                // file = file.replace("R.string." + key, "R.string." + renaming.get(key));
//                changesMade = true;
//            }
        }

        for (String key : renaming.keySet()) {
            if (file.contains("R.string." + key)) {
                System.out.println("renaming in java..." + key);
                file = file.replace("getString(R.string." + key, encryptionMethodName + "(getString(R.string." + renaming.get(key) + ")");
                // file = file.replace("R.string." + key, "R.string." + renaming.get(key));
                changesMade = true;
            }
            if (file.contains("R.string." + key)) {
                System.out.println("holding in java..." + key);
                doNotEncode.add(key);
                file = file.replace("R.string." + key, "R.string." + renaming.get(key));
                // file = file.replace("R.string." + key, "R.string." + renaming.get(key));
                changesMade = true;
            }
        }
//        if (changesMade) {
//            writeToFile(file.replaceFirst(";", ";\n" + importStatement), javaFile.getAbsolutePath());

    }
    private void scanLayout(File fileToRename) throws IOException {
        System.out.println(fileToRename.getAbsolutePath());
        String file = readFile(fileToRename.getAbsolutePath());

        int index = 0;
        while(index != -1) {
            int start = file.indexOf("@string/", index);
            if (start == -1) {
                break;
            }
            int end = file.indexOf("\"", start + 1);
            String id = file.substring(start + 8, end);
            doNotEncode.add(id);
            System.out.println("New used token.." + id);
            index = end;
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


