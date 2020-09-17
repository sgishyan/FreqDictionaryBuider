package adventure;

import java.util.*;

/**
 * Created by suren on 1/11/19.
 */
public class MaxTest {
    public static void main(String[] args) throws InterruptedException {

    }

    private static int arrayMaxValue(int array[])  {
        int max = 0;
        for (int i = 0; i < array.length; ++i) {
            if (array[i] > max) {
                max = array[i];
            }
        }
        return max;
    }

    public static void stringArrayCustomSort(ArrayList<String> strings) {
        Collections.sort(strings, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.length() - o2.length();
            }
        });
    }
}


