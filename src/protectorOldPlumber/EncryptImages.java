package protectorOldPlumber;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Base64;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by suren on 4/19/18.
 */
public class EncryptImages {
    String path;
    String output;


            static byte[] qvzx___ = {0x06, 0x51, 0x31, 0x56, 0x0A, 0x4C, 0x5A, 0x44,
            0x06, 0x51, 0x21, 0x34, 0x0A, 0x4C, 0x56, 0x44,
            0x06, 0x51, 0x31, 0x56, 0x0A, 0x4C, 0x5A, 0x44,
            0x06, 0x51, 0x31, 0x16, 0x0A, 0x4C, 0x5A, 0x44,
            0x06, 0x51, 0x71, 0x16, 0x0A, 0x4C, 0x5A, 0x44,
            0x06, 0x51, 0x61, 0x56, 0x0A, 0x4C, 0x5A, 0x44,
            0x06, 0x51, 0x18, 0x56, 0x0A, 0x4C, 0x5A, 0x44,
            0x66, 0x51, 0x00, 0x00, 0x22, 0x4E, 0x5A, 0x44,
            0x16, 0x51, 0x31, 0x56, 0x1F, 0x1C, 0x5A, 0x44,
            0x34, 0x51, 0x31, 0x56, 0x0A, 0x2C, 0x5A, 0x44,
            0x28, 0x51, 0x31, 0x56, 0x4A, 0x4F, 0x1A, 0x44,
            0x1F, 0x51, 0x31, 0x56, 0x5A, 0x4C, 0x5A, 0x44,
            0x06, 0x51, 0x31, 0x56, 0x0A, 0x3C, 0x54, 0x44,
            0x06, 0x51, 0x31, 0x56, 0x3A, 0x4A, 0x52, 0x44,
            0x06, 0x51, 0x31, 0x56, 0x2A, 0x4C, 0x5A, 0x44,
            0x06, 0x51, 0x31, 0x56, 0x1A, 0x7B, 0x5A, 0x44
    };


    public static void main(String[] args) {

        String s1 = new String("abc");
        String s2 = "abc";
        String s3 = new String("abc");
        String s4 = "abc";
        String s5 = s1 + s2;


        int a = ~0;
        int b = 0;
        System.out.println((a >> 4) ^ b);

       // new EncryptImages("/home/suren/Documents/plumber_exp_classic_classic/main", "");
    }
    public EncryptImages(String valuesFolder, String outputFolder) {
        path = valuesFolder;
        try {
            encodeImages(path + "/assets");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    private void encodeImages(String path) throws IOException {

        File root = new File(path);
        for (File file : root.listFiles()) {
            System.out.println("File in folder " + file.getName());
            if(file.isDirectory()) {
                encodeImages(file.getAbsolutePath());
            }else {

                if (file.getName().endsWith(".png")) {
                    System.out.println(file.getName());
                    //Renaming file
                    String base64String = encoder(file.getAbsolutePath());
                   // writeToFile(base64String, file.getAbsolutePath().replace(".png",".mp3"));

                    try {
                        String encrypted = ((StringBuilder) encryptStringImage(base64String, 12, 16)).toString();
                        writeToFile(encrypted, file.getAbsolutePath().replace(".png",".mp3"));
                        if (file.exists()) {
                            file.delete();
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static Object encryptStringImage(Object x, int q, int w) throws Exception {

        int ___ =  105 + Integer.parseInt("19");


        Class c1 = Class.forName("java.lang.String");
        Constructor<?> c = Class.forName("java.lang.StringBuilder").getConstructor(c1);
        Object o =  c.newInstance(x);
        Method m1 = o.getClass().getMethod("append", Character.TYPE);
        Method m2 = o.getClass().getMethod("setCharAt", Integer.TYPE, Character.TYPE);
        Method m3 = "String".getClass().getMethod("charAt", Integer.TYPE);
        Method m4 = "String".getClass().getMethod("length");
        StringBuilder a = new StringBuilder();

        int __ = Integer.parseInt("101");
        for (int i = 0; i < __; i+=4){
            m2.invoke(o, i, (char)((char)m3.invoke(x, i) ^ ___));
        }
        return o;

    }


    public static String encoder(String imagePath) {
        String base64Image = "";
        File file = new File(imagePath);
        try (FileInputStream imageInFile = new FileInputStream(file)) {
            // Reading a Image file from file system
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);
            base64Image = Base64.getEncoder().encodeToString(imageData);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
        return base64Image;
    }

    private static void writeToFile(String fileText, String filename) {
        System.out.println("Writing File : " + filename);
        File file = new File(filename);
        BufferedWriter output = null;
        try {
            output = new BufferedWriter(new FileWriter(file));
            output.write(fileText);
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
