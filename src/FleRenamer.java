import java.io.File;

/**
 * Created by suren on 10/14/19.
 */
public class FleRenamer {
    public static final String PATH = "/home/suren/Documents/new_levels/temp";
    public static final int START_NUMBER = 10;
    public static final int END_NUMBER = 124;
    public static final int SHIFT = 50;

    public static void main(String[] args) {
        File folder = new File(PATH);
        File[] listOfFiles = folder.listFiles();

        for (int i = END_NUMBER; i >= START_NUMBER; i--) {

            String fileName = i + ".txt";
            File f = new File(PATH + "/" + fileName);
            f.renameTo(new File(PATH + "/" + (i + SHIFT) + ".txt"));


        }
        System.out.println("Rename is done");
    }

}
