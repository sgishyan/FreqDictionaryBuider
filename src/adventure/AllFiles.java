package adventure;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by suren on 1/9/19.
 */
class AllFiles {

    public static void printAllFileNamesInDirectory(File path) {
        if (path.isDirectory()) {
            for (File innerFilePath : path.listFiles()) {
                printAllFileNamesInDirectory(innerFilePath);
            }
        }else {
            System.out.println(path.getName());
        }
    }

    public static void main(String[] args) {
        ArrayList<String> a = new ArrayList<>();
        a.add("sdf343");
        a.add("олр1եֆսդթփ9");
        a.add("sdfsdf");
        a.add("սադֆսֆս");
        a.add("1234");
        customSort(a);
        for (String s : a) {
            System.out.println(s);
        }
        //printAllFileNamesInDirectory(new File("/home/suren/Documents/Creative Soft"));
    }

    public static void customSort(ArrayList<String> strings) {
        Collections.sort(strings, new Comparator<String>() {
            @Override
            public int compare(String string1, String string2) {
               return digitsNumberInString(string2) - digitsNumberInString(string1);
            }
            private int digitsNumberInString(String string) {
                int digitsNumber = 0;
                for (int i = 0; i < string.length(); ++i) {
                    if (Character.isDigit(string.charAt(i))) {
                        digitsNumber++;
                    }
                }
                return digitsNumber;
            }
        });
    }
}