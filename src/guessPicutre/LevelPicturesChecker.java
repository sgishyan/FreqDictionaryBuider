package guessPicutre;

import mobiloids.Pair;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by suren on 6/21/17.
 */
public class LevelPicturesChecker {
    static boolean  [] imageUsage;

    public static void main(String[] args) throws IOException {

        String picturesFolder = "/home/suren/Documents/creative_soft_new_server/GtpHindi/app/src/main/assets/images";
        String levelsFolder = "/home/suren/Documents/creative_soft_new_server/GtpHindi/app/src/main/assets/levels";

        File pictures = new File(picturesFolder);
        File[] pictureFiles = pictures.listFiles();
        imageUsage = new boolean[pictureFiles.length];

        ArrayList<String> pictureList = new ArrayList<>();

        for (File f : pictureFiles) {
            pictureList.add(f.getName());
           // System.out.println("Image : " + f.getName());
        }
        System.out.println("---------------------------------------------------------");
        checkFolder(levelsFolder, pictureList);
        for(int i = 0; i < imageUsage.length; i++) {
           if(!imageUsage[i]) {

               File df = new File(picturesFolder + "/" + pictureList.get(i));
               boolean isDeleted = df.delete();
               System.out.println("Unused image: " + pictureList.get(i) + " " + isDeleted);
           }
        }

    }


    private static void checkFolder(String englishLevelsDir, ArrayList<String> allPictures) throws IOException {
        File folder = new File(englishLevelsDir);
        ArrayList<Pair<String,String>> words = new ArrayList<Pair<String,String>>();
        for (final File fileEntry : folder.listFiles()) {


            if (fileEntry.isDirectory()) {
                checkFolder(fileEntry.getAbsolutePath(), allPictures);
                continue;
            }

            BufferedReader dic1 = new BufferedReader(new FileReader(fileEntry.getAbsolutePath()));
            try {

                String line = dic1.readLine().trim().toLowerCase();
                if (!allPictures.contains(line)) {
                    System.out.println("Missing : " + line);
                } else {
                    for(int i = 0; i < allPictures.size(); i++) {
                        if (line.equals(allPictures.get(i))) {
                            if (imageUsage[i]) {
                              //  System.out.println("Duplicate usage : " + line);
                            } else {
                                imageUsage[i] = true;
                            }
                        }
                    }
                }

            } finally {
                dic1.close();
            }

        }
    }

 }
