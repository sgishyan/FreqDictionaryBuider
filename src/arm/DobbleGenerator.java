package arm;

/**
 * Created by suren on 5/8/18.
 */
public class DobbleGenerator {
    static int[] numbers = new int[57];
    static int[][] variations = new int[57][57];
    static int var = 0;
    public static void main(String[] args) {
        int i, j, k, r = 0, n = 7;

        // first card
        System.out.print("Card " +  (++r) + " :");
        for (i = 0; i <= n; i++) {
            PRINT(i);
            variations[var][i] = i + 1;
        }
        var++;
        System.out.println();

        // n following cards
        for (j = 0; j < n; j++) {
            System.out.print("Card " +  (++r) + " :");
            PRINT (0);
            variations[var][0] = 1;
            for (k = 0; k < n; k++) {
                PRINT (n+1 + n*j + k);
                variations[var][k + 1] = n+1 + n*j + k + 1;
            }
            System.out.println();
            var++;
        }

        // n*n following cards
        for (i = 0; i < n; i++) {
            for (j = 0; j < n; j++) {
                System.out.print("Card " +  (++r) + " :");
                PRINT (i+1);
                variations[var][0] = i + 2;
                for (k = 0; k < n; k++) {
                    PRINT (n+1 + n*k + (i*k+j)%n); // Good for n = prime number
                    variations[var][k+1] = n+1 + n*k + (i*k+j)%n + 1;
                }
                var++;
                System.out.println();
            }
        }
        System.out.println("Total usage");
        for (int w=0;w < numbers.length;w++) {
            System.out.println((w+1) + " : " + numbers[w]);
        }

        for(int a = 0; a< numbers.length; a++) {
            System.out.println("Card " + (a + 1));
            for (int b = 0; b <= n; b++) {
                System.out.print(variations[a][b] + " ");
            }
            System.out.println();
        }

        for(int a = 0; a < numbers.length; a++) {
            for (int b = a + 1; b < numbers.length; b++) {
                int count = 0;
                for(int c = 0; c<= n; c++) {
                    for(int d=0;d<=n;d++) {
                        if (variations[a][c] == variations[b][d]) {
                            count++;
                        }
                    }
                }
                if (count != 1) {
                    System.out.println("ERROR " + a + "  " + b);
                }
            }
        }
    }
    public static void PRINT(int x) {
        System.out.print((x + 1) + " ");
        numbers[x]++;

    }
}
