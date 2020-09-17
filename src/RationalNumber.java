/**
 * Created by Alla on 3/2/2015.
 */
public class RationalNumber {

    private int m;
    private int n;

    public RationalNumber(int m, int n) {
        this.m = m;
        this.n = n;
    }
    public RationalNumber() {
        m = 0;
        n = 1;
    }

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int getN() {
        return n;
    }

    public void setN(int n) {
        this.n = n;
    }

    public RationalNumber add(RationalNumber rat) {
        RationalNumber tmp = new RationalNumber();
        tmp.m = m * rat.n + n * rat.m;
        tmp.n = n * rat.n;
        return tmp;
    }

    public RationalNumber substract(RationalNumber rat) {
        RationalNumber tmp = new RationalNumber();
        tmp.m = m * rat.n - n * rat.m;
        tmp.n = n * rat.n;
        return tmp;
    }

    public RationalNumber multiply(RationalNumber rat) {
        m *= rat.m;
        n *= rat.n;
        return this;
    }
    public RationalNumber divide(RationalNumber rat) {
        m *= rat.n;
        n *= rat.m;
        return this;
    }

    public String toString() {
        return m + "/" + n;
    }

    public boolean equals (RationalNumber rat) {
        if(m * rat.n == n * rat.m)
            return true;
        else
            return false;
    }
    public int GCD(int a, int b) {
        if(b == 0) {
            return a;
        }
        return GCD(b, a%b);
    }

    public RationalNumber reduce() {
        int gcd = GCD(m, n);
        m /= gcd;
        n /= gcd;
        return this;
    }
    public static void main(String[] args){

        RationalNumber a = new RationalNumber();
        a.m = 7;
        a.n = 20;
        RationalNumber b = new RationalNumber();
        b.m = 3;
        b.n = 5;
        RationalNumber c, d;
        c = a.add(b);
        d = a.substract(b);
        c.reduce();
        d.reduce();
        System.out.println(c.toString());
        System.out.println(d.toString());
        if(c.equals(d)){
            System.out.println("c equals d");
        }
        else{
            System.out.println("c does not equal d");
        }

    }



}
