package connections;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Created by suren on 10/16/17.
 */
public class LevelChecker {

    public static void main(String[] args) {

        int startIndex = 1;
        int endIndex = 225;
        String packName = "Final/christmas";
        int bad = 0;



        for (int i = startIndex; i <= endIndex; i++) {

            String filename1 = "/home/suren/Documents/temp/connections/" + packName + "/sol" + i + ".txt";



            try {
                Runtime rt = Runtime.getRuntime();
                //Process pr = rt.exec("cmd /c dir");
                Process pr = rt.exec("/home/suren/Downloads/scala-2.10.6/bin/scala -cp /home/suren/Downloads/copris-numberlink-v1-1.jar numberlink.Solver -o multi " + filename1);


                BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

                StringBuilder output = new StringBuilder();
                String line;
                while ((line = input.readLine()) != null) {
                    output.append(line);
                }
                int result = pr.waitFor();

                if (result != 0) {
                    System.out.println(result);
                }
                //System.out.println(output);

                if (output.toString().contains("NumOfSolutions = 1")) {
                    System.out.println("Unique solution " + i);
                } else {
                    System.out.println("Bad solution " + i);
                    bad++;
                }


            } catch(Exception e) {
                System.out.println(e.toString());
                e.printStackTrace();
            }




    }
        if (bad == 0) {
            System.out.println("Everything is OK");
        } else {
            System.out.println("!!!!!!  ALERT !!!!!!! There are " + bad + " errors");
        }
    }
}
