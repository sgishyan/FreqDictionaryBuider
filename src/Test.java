import java.io.*;

/**
 * Created by suren on 9/25/19.
 */
public class Test {
    public static void main(String[] args) {
        String filePath = "test.txt";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath));
            String line = bufferedReader.readLine();
            while (line != null) {
                System.out.println(line);
                line = bufferedReader.readLine();
            }
        }
        catch (IOException e) {
           e.printStackTrace();
        }

    }

    static class MyRunnable implements Runnable {

        private int value;
        private int repeatCount;
        public MyRunnable(int value, int repeatCount) {
            this.value = value;
            this.repeatCount = repeatCount;
        }
        @Override
        public void run() {
            for(int i = 0; i < repeatCount; i++) {
                System.out.print(value + " ");
            }
        }
    }
}
