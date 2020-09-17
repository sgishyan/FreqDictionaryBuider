/**
 * Created by suren on 4/26/17.
 */
public class LambdaTest {
    public static void main(String[] main) {



        Thread t = new Thread(() -> System.out.println("Running..."));
        t.start();
    }

    private static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("Running...");
        }
    }
}
