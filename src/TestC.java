import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.*;

/**
 * Created by suren on 4/16/19.
 */
public class TestC {


    public static class MyScanner {
        BufferedReader br;
        StringTokenizer st;

        public MyScanner() {
            br = new BufferedReader(new InputStreamReader(System.in));
        }

        String next() {
            while (st == null || !st.hasMoreElements()) {
                try {
                    st = new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }

        int nextInt() {
            return Integer.parseInt(next());
        }

        long nextLong() {
            return Long.parseLong(next());
        }

        double nextDouble() {
            return Double.parseDouble(next());
        }

        String nextLine() {
            String str = "";
            try {
                str = br.readLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return str;
        }
    }

    public static void main(String[] args) {
        MyScanner scanner = new MyScanner();
        int q;
        int c, m, x;

        q = scanner.nextInt();

          for (int i = 0; i < q; i++) {

              c = scanner.nextInt();
              m = scanner.nextInt();
              x = scanner.nextInt();
              int target = Math.min(c,m);
              int free = c + m + x - 2 * target;
              if (free >= target) {
                  System.out.println(target);
                  continue;
              }



              System.out.println((c + m + x) / 3);



        }
    }

    private static boolean divisorsCompare(long candidate, int[] d) {
        ArrayList<Long> numbers = new ArrayList<>();
        long i;
        for ( i = 2; i * i <= candidate; i++) {
            if (candidate % i == 0) {
                // System.out.println("Divisor " + i);
                numbers.add(i);
                if (i * i != candidate)
                    numbers.add((candidate / i));
            }
        }
        if (d.length != numbers.size()) {
            //System.out.println("LENGTH" + d.length + " " + numbers.size());
            return false;
        }
//        for (Long f : numbers) {
//            System.out.print(f + " ");
//        }
        Arrays.sort(d);
        Collections.sort(numbers);
        for (int j = 0; j < d.length; j++) {
            if(d[j] != numbers.get(j)) {
                return false;
            }
        }

        return true;
    }

    static int max(int[] a, int n) {
        int max = a[0];
        for (int i = 1; i < n; i++) {
            if (a[i] > max ) {
                max = a[i];
            }
        }
        return max;
    }

    static int divisorsCount(long a) {
        int count = 0;
        long i;
        for ( i = 2; i * i < a; i++) {
            if (a % i == 0) {
                count+=2;
            }

        }
        if (i * i == a)
            count++;
        return count;
    }

    static long gcd(long a, long b)
    {
        // System.out.println(a + " : " + b);
        if (b == 0)
            return a;
        return gcd(b, a % b);
    }

    static long lcm(int a[], int n)
    {
        long result = a[0];
        for (int i = 1; i < n; i++) {
            // System.out.println("LCM " +  a[i] + " " + result);
            result = (((a[i] * result)) /
                    (gcd(a[i], result)));
        }
        return result;
    }
}
