package plumber.engine;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by suren on 2/25/20.
 */
public class Destribution {
    public static void main(String[] args) {
        Set<Integer> portal= new TreeSet<>();
        Set<Integer> switcher= new TreeSet<>();
        Set<Integer> classic= new TreeSet<>();
        Set<Integer> construct= new TreeSet<>();

        Random random = new Random();
        for (int i = 1; i <= 128 ; i++) {
            int type = random.nextInt(4);
                switch (type) {
                    case 1:
                        portal.add(i);
                        break;
                    case 2:
                        switcher.add(i);
                        break;
                    case 3:
                        construct.add(i);
                        break;
                    case 0:
                        classic.add(i);
                        break;
                    default:

                }

        }
        printArray(portal);
        printArray(switcher);
        printArray(construct);
        printArray(classic);


    }

    private static void printArray(Set<Integer> set) {
        System.out.println("int [] indexes = {");
        for (int a : set) {
            System.out.print(a + ", ");
        }
        System.out.println("}");

    }
}
