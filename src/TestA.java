
import mobiloids.Pair;
import java.util.*;

/**
 * Created by suren on 4/16/19.
 */

public class TestA {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<Pair<Integer, Integer>, Integer> path = new HashMap<>();

        int t, n;
        t = scanner.nextInt();
        main_loop:
        for (int i= 0; i< t; i++) {
            n = scanner.nextInt();
            String str =  scanner.next();
            int x = 0;
            int y = 0;

            path.clear();
            int min = n + 1;
            int minX = 0;
            int minY = 0;
            path.put(new Pair<>(0, 0), 0);

            for(int j = 0; j < n; j++) {
                char dir = str.charAt(j);

                switch (dir) {
                    case 'L':
                        x--;
                        break;
                    case 'R':
                        x++;
                        break;
                    case 'U':
                        y++;
                        break;
                    case 'D':
                        y--;
                        break;
                }
                //System.out.println( x + " " + y);

                Pair<Integer, Integer> point = new Pair<>(x, y);
                if (path.containsKey(point)) {
                    int length = j - path.get(point);
                    if (length < min) {
                        min = length;
                        minX = path.get(point) + 1;
                        minY = j + 1;
                    }
                }
                path.put(point, j + 1);


            }

            if (min == n + 1) {
                System.out.println(-1);
                continue ;
            }
            System.out.println(minX + " " + minY);

        }

    }


}
