package guessPicutre;

import java.util.Scanner;

/**
 * Created by suren on 6/1/17.
 */
public class B {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        String[] bld = new String[n];
        for(int i = 0; i< n; i++)
            bld[i] = sc.next();
        int[] solLeft = new int[n];
        int addition = 0;
        int last = 0;

        for (int i = n - 1; i >= 0; i--) {
            int min = 0;
            int max = m + 1;

            for (int j = 0; j < m + 2; j++)
            {
                if (bld[i].charAt(j) == '1') {
                    if (min == 0) {
                        min = j;
                    }
                    max = j;
                }
            }
            if (i == n - 1) {
                if (min == 0) {
                    solLeft[i] = 0;
                    last = 0;
                } else {
                    solLeft[i] = max;
                    last = max;
                }

            } else
            {

                int right;
                int left;

                if (min == 0) {
                    solLeft[i] = solLeft[i + 1];
                    addition++;
                } else {
                    left = last + max + 1;
                    right = m + 2 - last + (m + 1) - min;
                    if (left < right) {
                        solLeft[i] = left + solLeft[i +1] + addition;
                        last = max;
                    } else{
                        solLeft[i] = right + solLeft[i +1] + addition;
                        last = min;
                    }
                    addition = 0;
                }

            }


        }
//        for (int j = 0; j < n; j++)
//        {
//            System.out.println(solLeft[j] + " " + last);
//        }
        System.out.println(solLeft[0]);
    }
}
