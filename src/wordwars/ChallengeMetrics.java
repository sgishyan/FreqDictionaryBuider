package wordwars;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Created by suren on 6/24/16.
 */
public class ChallengeMetrics {

    public static void main(String[] args) throws FileNotFoundException {
        Set<String> allWords ;
        Set<String> basicWords ;
        Set<String> mediumWords ;
        allWords = readWords("/home/suren/Documents/temp/eng/wordwars/allWords.txt");
        basicWords = readWords("/home/suren/Documents/temp/eng/wordwars/easy.txt");
        mediumWords = readWords("/home/suren/Documents/temp/eng/wordwars/medium.txt");

//        StartingWithLetterChallenge challenge = new StartingWithLetterChallenge(allWords, basicWords, mediumWords, 4, 30);
//        EndingWithLetterChallenge challengeEnd = new EndingWithLetterChallenge(allWords, basicWords, mediumWords, 4, 30);
        //MakeWordsWithLengthNChallenge challenge1 = new MakeWordsWithLengthNChallenge(allWords, basicWords, mediumWords, 4, 30);
        //MakeWords mkChallenge = new MakeWords(allWords, basicWords, mediumWords, 10, 30);


        List<ChallengeMetric> challenges = new ArrayList<ChallengeMetric>();


//
//        //Begin
//        for(char letter = 'a'; letter <= 'z'; letter++) {
//            for (int count = 3; count < 10; count++) {
//                StartingWithLetterChallenge challenge = new StartingWithLetterChallenge(allWords, basicWords, mediumWords, count, 30, letter);
//                challenges.add(challenge);
//            }
//            for (int count = 3; count < 10; count++) {
//                StartingWithLetterChallenge challenge = new StartingWithLetterChallenge(allWords, basicWords, mediumWords, count, 45, letter);
//                challenges.add(challenge);
//            }
//        }
//
//
//
//
//        //End
//        for(char letter = 'a'; letter <= 'z'; letter++) {
//            for (int count = 3; count < 10; count++) {
//                EndingWithLetterChallenge challenge = new EndingWithLetterChallenge(allWords, basicWords, mediumWords, count, 30, letter);
//                challenges.add(challenge);
//            }
//            for (int count = 3; count < 10; count++) {
//                EndingWithLetterChallenge challenge = new EndingWithLetterChallenge(allWords, basicWords, mediumWords, count, 45, letter);
//                challenges.add(challenge);
//
//            }
//        }
//
//        //MW
//        for (int count = 3; count < 50; count++) {
//            MakeWords challenge = new MakeWords(allWords, basicWords, mediumWords, count, 30);
//            challenges.add(challenge);
//        }
//        for (int count = 3; count < 50; count++) {
//            MakeWords challenge = new MakeWords(allWords, basicWords, mediumWords, count, 45);
//            challenges.add(challenge);
//        }
//
//        for (int count = 3; count < 50; count++) {
//            MakeWords challenge = new MakeWords(allWords, basicWords, mediumWords, count, 60);
//            challenges.add(challenge);
//        }
//
//
//        //Len
//        for (int len = 3; len < 7; len++) {
//            for (int count = 3; count < 20; count++) {
//                MakeWordsWithLengthNChallenge challenge = new MakeWordsWithLengthNChallenge(allWords, basicWords, mediumWords, count, 30, len);
//                challenges.add(challenge);
//            }
//
//            for (int count = 3; count < 20; count++) {
//                MakeWordsWithLengthNChallenge challenge = new MakeWordsWithLengthNChallenge(allWords, basicWords, mediumWords, count, 45, len);
//                challenges.add(challenge);
//            }
//
//            for (int count = 3; count < 20; count++) {
//                MakeWordsWithLengthNChallenge challenge = new MakeWordsWithLengthNChallenge(allWords, basicWords, mediumWords, count, 60, len);
//                challenges.add(challenge);
//            }
//        }
//
//
//        //Pal
//        for (int count = 3; count <= 10; count++) {
//            Palindrom challenge = new Palindrom(allWords, basicWords, mediumWords, count, 30);
//            challenges.add(challenge);
//        }
//
//        for (int count = 3; count <= 10; count++) {
//            Palindrom challenge = new Palindrom(allWords, basicWords, mediumWords, count, 45);
//            challenges.add(challenge);
//        }
//
//        for (int count = 3; count <= 10; count++) {
//            Palindrom challenge = new Palindrom(allWords, basicWords, mediumWords, count, 60);
//            challenges.add(challenge);
//        }
//
//
//
//        //Double Letter
//        for(char l1 = 'a'; l1 <= 'z'; l1++) {
//            for(char l2 = 'a'; l2 <= 'z'; l2++) {
//
//                    DoubleLetter challenge = new DoubleLetter(allWords, basicWords, mediumWords, 30, l1, l2);
//                    challenges.add(challenge);
//
//            }
//        }
//
//
//
//        for(char l1 = 'a'; l1 <= 'z'; l1++) {
//            for(char l2 = 'a'; l2 <= 'z'; l2++) {
//                    DoubleLetter challenge = new DoubleLetter(allWords, basicWords, mediumWords, 30, l1, l2);
//                    challenges.add(challenge);
//            }
//        }
//        //Pal
//        for (int count = 3; count <= 10; count++) {
//            Palindrom challenge = new Palindrom(allWords, basicWords, mediumWords, count, 30);
//            challenges.add(challenge);
//        }
//
//        for (int count = 3; count <= 10; count++) {
//            Palindrom challenge = new Palindrom(allWords, basicWords, mediumWords, count, 45);
//            challenges.add(challenge);
//        }
//
//        for (int count = 3; count <= 10; count++) {
//            Palindrom challenge = new Palindrom(allWords, basicWords, mediumWords, count, 60);
//            challenges.add(challenge);
//        }
//
//
//        //Stairs
        Stairs challenge1 = new Stairs(allWords, basicWords, mediumWords, 30, 2, 3, 4, 3, 2);
        Stairs challenge2 = new Stairs(allWords, basicWords, mediumWords, 30, 3, 4, 5, 4, 3);
        Stairs challenge3 = new Stairs(allWords, basicWords, mediumWords, 30, 2, 4, 6);
        Stairs challenge4 = new Stairs(allWords, basicWords, mediumWords, 30, 2, 4, 6, 4, 2);
        Stairs challenge5 = new Stairs(allWords, basicWords, mediumWords, 30, 4, 5, 4);
        Stairs challenge6 = new Stairs(allWords, basicWords, mediumWords, 30, 5, 6, 7);
        challenges.add(challenge1);
        challenges.add(challenge2);
        challenges.add(challenge3);
        challenges.add(challenge4);
        challenges.add(challenge5);
        challenges.add(challenge6);
//
//
//
//
//        //Collect
//        for(char letter = 'a'; letter <= 'z'; letter++) {
//            for (int count = 3; count < 20; count++) {
//                CollectLetters challenge = new CollectLetters(allWords, basicWords, mediumWords, count, 30, letter);
//                challenges.add(challenge);
//            }
//            for (int count = 3; count < 30 ; count++) {
//                CollectLetters challenge = new CollectLetters(allWords, basicWords, mediumWords, count, 45, letter);
//                challenges.add(challenge);
//
//            }
//        }
//
        //SET
//        SET  challengeSet1 = new SET(allWords, basicWords, mediumWords,30, 'a', 'b', 'c', 'd');
//        SET  challengeSet2 = new SET(allWords, basicWords, mediumWords,30, 'e', 'o', 'i', 'u');
//        SET  challengeSet3 = new SET(allWords, basicWords, mediumWords,30, 'a', 'd', 't', 'y');
//        SET  challengeSet4 = new SET(allWords, basicWords, mediumWords,30, 'e', 'h', 'p');
//        SET  challengeSet5 = new SET(allWords, basicWords, mediumWords,30, 'x', 'y', 'z');
//        SET  challengeSet6 = new SET(allWords, basicWords, mediumWords,30, 't', 'r', 'n', 'm', 's', 'p');
//        SET  challengeSet7 = new SET(allWords, basicWords, mediumWords,30, 'w', 'q', 'k', 'j');
//        SET  challengeSet8 = new SET(allWords, basicWords, mediumWords,30, 'g', 'p', 'b');
//        SET  challengeSet9 = new SET(allWords, basicWords, mediumWords,30, 'y', 'h', 'k', 't');
//        SET  challengeSet10 = new SET(allWords, basicWords, mediumWords,30, 'a', 'b', 'c', 'd', 'e');
//        SET  challengeSet11 = new SET(allWords, basicWords, mediumWords,30, 't', 'p', 'i', 'j', 'v');
//        challenges.add(challengeSet1);
//        challenges.add(challengeSet2);
//        challenges.add(challengeSet3);
//        challenges.add(challengeSet4);
//        challenges.add(challengeSet5);
//        challenges.add(challengeSet6);
//        challenges.add(challengeSet7);
//        challenges.add(challengeSet8);
//        challenges.add(challengeSet9);
//        challenges.add(challengeSet10);
//        challenges.add(challengeSet11);



        Collections.sort(challenges);
        for(ChallengeMetric challengeMetric : challenges) {
            challengeMetric.printMetrics();
        }


//        challenge.printMetrics();
//        challengeEnd.printMetrics();
//        challenge1.printMetrics();
//        mkChallenge.printMetrics();

    }

    public static Set<String> readWords(String fileName) throws FileNotFoundException {
        Set<String> words = new TreeSet<String>();
        BufferedReader dic1 = new BufferedReader(new FileReader(fileName));
        try {

            String line = dic1.readLine();

            while (line != null) {
                words.add(line.trim());
                line = dic1.readLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                dic1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return words;
    }
}
