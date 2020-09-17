package carparking;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by suren on 10/16/17.
 */
public class LevelChecker {

    public static void main(String[] args) throws IOException, InterruptedException {

        int startIndex = 1;
        int endIndex = 225;
        int bad = 0;
        int levelsCount = 1000;

        String levelsFile = "/home/suren/Documents/temp/carparking/levels.txt";
        ArrayList<String> levels = readFile(levelsFile);


        //r.exec("cmd /c pdftk C:\\tmp\\trashhtml_to_pdf\\b.pdf C:\\tmp\\trashhtml_to_pdf\\a.pdf cat output C:\\tmp\\trashhtml_to_pdf\\d.pdf");


        for (int i = 0 ;i < levels.size(); i++) {


            String levelLine = levels.get(i);
            String[] tokens = levelLine.split(" ");
            String level = tokens[1].replace("o", ".");
            int difficulty = Integer.parseInt(tokens[0]);
           // System.out.println("level = " + level);
            try {


                Runtime rt = Runtime.getRuntime();

                //Process pr = rt.exec("cmd /c dir");
                Process pr = rt.exec("/home/suren/Downloads/go/bin/go run /home/suren/src/github.com/fogleman/rush/cmd/solve/main.go " + level);
                //Process pr = rt.exec("/home/suren/Downloads/go/bin/go run /home/suren/src/github.com/fogleman/rush/cmd/solve/main.go");

                BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream()));

                int result = pr.waitFor();

                //System.out.println("Result  = " + result);
                StringBuilder output = new StringBuilder();
                String line;
                while ((line = input.readLine()) != null) {
                    output.append(line);
                  //  System.out.println(line);
                }

                if (result != 0) {
                    //System.out.println(result);
                }
               // System.out.println(output);

                int startSolution = output.indexOf("[");
                int endSolution = output.indexOf("]");
                String solutionString = output.substring(startSolution + 1, endSolution);

                //Own solution
                GameBoard board = new GameBoard(level);
                Checker solver = new Checker();
                solver.solve(board);
                String ownSolution = solver.getSolutionString();




                if (solutionString.equals(ownSolution)) {
//                    System.out.println("Good solution ");
//                    System.out.println(solutionString);
//                    System.out.println(ownSolution);

                } else {
                    System.out.println("Bad solution " + i);
                    bad++;
                    System.out.println(solutionString);
                    System.out.println(ownSolution);
                }

                if (difficulty != solver.solutionMoves.size()) {
                    System.out.println("Wrong Solution " + difficulty + " " + solver.solutionMoves.size());
                }

            } catch (Exception e) {
                System.out.println(e.toString());
                e.printStackTrace();
            }

        }


        System.out.println("Bads count = " + bad);

    }

    private static ArrayList<String> readFile(String levelFile) throws IOException {
        BufferedReader dic1 = new BufferedReader(new FileReader(levelFile));
        ArrayList<String> words = new ArrayList<String>();
        try {

            String line = dic1.readLine();
            while (line != null) {
                String word = line.trim();
                words.add(word);
                line = dic1.readLine();
            }
        } finally {
            dic1.close();
            return words;
        }
    }

}
