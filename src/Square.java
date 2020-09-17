import java.util.*;

/**
 * Created by suren on 6/1/17.
 */
public class Square {

    private int side;
    public Square (int side) {
        this.side = side;
    }

    public int compareTo(Square o) {
        return side - o.side;
    }

    public static boolean isPalindrome(int a) {
        int reversedA = 0;
        int temp = a;
        while (temp > 0) {
            int lastDigit = temp % 10;
            reversedA = reversedA * 10 + lastDigit;
            temp /= 10;
        }
        return reversedA == a;
    }
    public static void main(String[] args) {
        System.out.println(isPalindrome(1011));
        System.out.println(isPalindrome(12321));
        System.out.println(isPalindrome(223));
        System.out.println(isPalindrome(12));
        System.out.println(isPalindrome(11));
    }
}
