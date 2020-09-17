package protectorOldPlumber;

import java.io.*;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by suren on 4/10/18.
 */
public class StringProtection {
    static int [] __ = {299,714,402,586,741,600,364,272,269,401,701,534,409,286,685,506,334,390,725,716,369,497,666,268,585,495,660,434,621,536,615,489,693,711,535,395,393,607,739,258,573,504,253,729,719,630,492,646,322,634,485,473,500,644,570,480,596,498,304,511,438,512,501,341,305,686,325,749,733,575,444,657,673,738,569,324,665,499,526,417,287,637,588,522,610,661,662,653,530,307,563,643,328,735,517,435,651,389,259,486,418,406,695,360,428,667,343,748,316,601,672,387,609,372,740,376,652,676,732,533,301,496,294,547,448,633,458,317,252,382,419,674,441,718,479,367,602,354,256,471,671,384,283,429,356,380,407,262,454,338,345,302,297,490,388,365,478,656,431,622,545,518,612,346,449,257,300,326,456,465,603,280,521,296,443,682,412,562,616,261,734,424,507,542,285,295,584,375,659,728,274,564,373,271,690,558,698,282,541,680,337,396,620,697,309,684,416,288,704,371,332,447,469,706,723,636,648,683,327,359,710,377,400,524,391,580,339,730,335,708,348,466,578,670,436,525,528,405,363,336,627,323,291,705,333,263,303,527,742,394,529,265,481,260,470,477,722,737,321,319,557,532,472,487,264,565,310,474,306,452,619,664,649,463,566,331,266,414,598,540,726,595,611,404,313,392,509,747,639,276,549,531,445,681,613,423,450,700,410,692,724,546,658,515,462,378,590,623,439,516,744,650,668,713,330,293,505,675,355,699,568,709,581,399,628,267,308,544,482,315,572,720,614,548,608,430,460,736,467,422,284,576,342,374,357,298,514,556,519,318,484,279,625,591,717,618,593,273,642,589,398,289,421,631,745,624,599,655,560,552,379,426,715,594,687,351,408,491,677,385,645,314,459,281,702,689,510,349,255,555,721,694,493,440,712,425,455,433,592,669,502,629,520,437,579,403,508,464,597,561,320,420,523,270,344,352,550,543,678,432,551,743,582,413,606,574,617,483,731,577,254,353,411,275,691,442,488,383,446,250,427,278,571,640,451,361,350,457,626,638,538,635,292,663,679,503,583,370,559,397,632,347,641,688,647,654,415,727,329,746,468,475,461,587,703,567,340,386,696,494,277,605,362,311,553,513,312,453,366,476,381,290,358,554,604,251,707,539,368,537};

    //static int [] __ = {130,60,89,117,46,25,73,56,75,57,74,34,93,71,95,20,16,125,82,61,87,23,102,110,83,72,27,18,45,123,35,43,97,109,69,113,94,134,13,98,50,132,15,10,21,52,14,41,81,137,48,22,29,112,101,17,79,124,44,58,88,86,53,104,122,49,33,108,68,129,64,136,99,126,11,55,76,128,24,12,40,84,66,59,38,31,111,36,39,105,135,37,127,106,19,63,115,67,26,120,118,80,92,121,116,107,42,114,30,65,103,28,54,96,47,100,62,78,70,90,85,131,119,51,32,77,91,133};
    static byte[] qvzx___ = {0x06, 0x51, 0x31, 0x56, 0x0A, 0x4C, 0x5A, 0x44,
            0x06, 0x51, 0x21, 0x34, 0x0A, 0x4C, 0x56, 0x44,
            0x06, 0x51, 0x31, 0x56, 0x0A, 0x4C, 0x5A, 0x44,
            0x06, 0x51, 0x31, 0x16, 0x0A, 0x4C, 0x5A, 0x44,
            0x06, 0x51, 0x71, 0x16, 0x0A, 0x4C, 0x5A, 0x44,
            0x06, 0x51, 0x61, 0x56, 0x0A, 0x4C, 0x5A, 0x44,
            0x06, 0x51, 0x18, 0x56, 0x0A, 0x4C, 0x5A, 0x44,
            0x66, 0x51, 0x00, 0x00, 0x22, 0x4E, 0x5A, 0x44,
            0x16, 0x51, 0x31, 0x56, 0x1F, 0x1C, 0x5A, 0x44,
            0x34, 0x51, 0x31, 0x56, 0x0A, 0x2C, 0x5A, 0x44,
            0x28, 0x51, 0x31, 0x56, 0x4A, 0x4F, 0x1A, 0x44,
            0x1F, 0x51, 0x31, 0x56, 0x5A, 0x4C, 0x5A, 0x44,
            0x06, 0x51, 0x31, 0x56, 0x0A, 0x3C, 0x54, 0x44,
            0x06, 0x51, 0x31, 0x56, 0x3A, 0x4A, 0x52, 0x44,
            0x06, 0x51, 0x31, 0x56, 0x2A, 0x4C, 0x5A, 0x44,
            0x06, 0x51, 0x31, 0x56, 0x1A, 0x7B, 0x5A, 0x44
    };

    static int ____aaaa____(int x) {
        return 1<<x;

    }

    static int _____aaaa___(int x) {
        return 1<< x;

    }



    private static final int STEP = 17;
    static int key = 21365;
    static int key2 = 29125;
    static int key3 = 21185;
    static String filename = "/home/suren/Documents/creative_soft_new_server/plumber_2/app/src/main/java/com/mobiloids/plumbernew/PlumberGamePlay.java";


    public static String encryptString(String str) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Object o = Class.forName("java.lang.StringBuilder").newInstance();
        Method m = o.getClass().getMethod("append", Character.TYPE);
        StringBuilder a = new StringBuilder();

        for (int i = 0; i < str.length(); i++) {
            m.invoke(o, (char)((int)str.charAt(i) ^ (1<<i) ^ key));
            //(Class.forName("java.lang.StringBuilder"))(o).getMethod("append")((char)((int)str.charAt(i) ^ (1<<i) ^ key));
           a.append((char)((int)str.charAt(i) ^ (1<<i) ^ key));
        }
        return o.toString();
    }

    public static String encryptStringImage(Object x, int q, int w) throws Exception {


        int _ =  125 * Integer.parseInt("17");
        Object o = Class.forName("java.lang.StringBuilder").newInstance();
        Class c1 = Class.forName("java.lang.String");
        Method m1 = o.getClass().getMethod("append", Character.TYPE);
        Method m2 = o.getClass().getMethod("setCharAt", Integer.TYPE, Character.TYPE);
        Method m3 = "String".getClass().getMethod("charAt", Integer.TYPE);
        Method m4 = "String".getClass().getMethod("length");
        StringBuilder a = new StringBuilder();

        for (int i = 0; i < (Integer)m4.invoke(x); i++){
            m1.invoke(o, (char)((char)m3.invoke(x, i) ^ _));
        }

//        for (int i = 0; i< a.length(); i+= STEP) {
//            a.setCharAt(i, (char) (a.charAt(i)^ key3));
//        }
        return o.toString();
    }

    public static String decryptString(String str) {
        StringBuilder a = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            a.append((char)((int)str.charAt(i)  ^ (1<<i) ^ key));
        }
        return a.toString();
    }


    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException, IOException {
//        String a = "iVBORw0KGgoAAAANSUhEUgAAA";
//        String b = encryptString(a);
//        System.out.println(b);
//        String c = decryptString(b);
//        System.out.println(c);
//        makeReflection();
        //generateArray(10);
//        String s =  encodeStringInArrayStyle("All rights and copyrights belong to Creative Soft LLC(Mobiloids)", 10);
//        System.out.println(s);
//        try {
//            System.out.println(_____(s, 10));
//        } catch (Exception e) {
//            e.printStackTrace();
       generateArray(250);

//       // String a = C4534._0_("My name is table", 10);
//        System.out.println(a);
//        String b = C4534._1_(a, 10);
//        System.out.println(b);
        /*
        String encoded = encoder("/home/suren/Documents/creative_soft_new_server/plumberWithoutAppodeal/app/src/main/assets/images/pipe90_1.png");
        System.out.println(encoded);
        String encodedEnc = null;
        try {
            encodedEnc = encryptStringImage(encoded, 12, 16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //encodedEnc.toCharArray();
        System.out.println(encodedEnc);
        String decoded = null;
        try {
            decoded = encryptStringImage(encodedEnc,12, 16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(decoded);
//        String a1 = encryptString("java.lang.StringBuilder");
//        System.out.println(a1);
//        String a2 = decryptString(a1);
//        System.out.println(a2);
      //  System.out.println(encryptString("java.lang.StringBuilder"));
//        System.out.println(encryptString("java.lang.String"));
//        System.out.println(encryptString("append"));
//        System.out.println(encryptString("charAt"));
//        System.out.println(encryptString("length"));
*/
    }

    public static String encoder(String imagePath) {
        String base64Image = "";
        File file = new File(imagePath);
        try (FileInputStream imageInFile = new FileInputStream(file)) {
            // Reading a Image file from file system
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);
            base64Image = Base64.getEncoder().encodeToString(imageData);
        } catch (FileNotFoundException e) {
            System.out.println("Image not found" + e);
        } catch (IOException ioe) {
            System.out.println("Exception while reading the Image " + ioe);
        }
        return base64Image;
    }



    public static void makeReflection() throws IOException {
        String file = readFile(filename);
        int start = 0;
        do {
            start = file.indexOf("//[Reflect]", start + 1) + "//[Reflect]".length();
            if (start == -1) {
                break;
            }
            int end = file.indexOf(";", start);
            if (start == -1) {
                break;
            }

            //Finding type
            String typeDeclaration = file.substring(start, end).trim();
            String[] declarationTokens = typeDeclaration.split(" ");
            int index = 1;
            while(declarationTokens[index].length() < 2) {
                index++;
            }
            String type = declarationTokens[index];
            String variable = declarationTokens[declarationTokens.length - 1];
            System.out.println("Marked Line: " + file.substring(start, end) + " " + start);
            System.out.println("Marked type: " + type);
            System.out.println("Market variable: " + variable);
            //Finding type full name
            String[] lines = file.split(";");
            String fullType = null;
            for (String line : lines) {
                if (line.contains("import") && line.contains(type)) {
                    System.out.println("Type Full Name import: " + line);
                    fullType = line.substring(line.indexOf(" ")).trim();
                    System.out.println("Type Full Name : " + fullType);
                }
            }
            String typeReflectDeclaration;
           // Object o = Class.forName("java.lang.StringBuilder").newInstance();
            typeReflectDeclaration = "Class.forName(\"" + fullType + "\");";
            System.out.println("Type declaration: " + typeReflectDeclaration);

            //Finding all method calls on variable
           // while(containsMethodCall(file));


            start = end + 1;
            break;
        } while (true);


    }

    public static String _0_(String x, int y) {
        Object o = null;
        try {
            o = Class.forName("java.lang.StringBuilder").newInstance();
            Method m1 = o.getClass().getMethod("append", Character.TYPE);
            Method m3 = "__1sdasdacc".getClass().getMethod("charAt", Integer.TYPE);
            Method m4 = "cvbcvbas__".getClass().getMethod("length");
            StringBuilder a = new StringBuilder();

            for (int i = 0; i < (Integer)m4.invoke(x); i++){
                //System.out.println((char) m3.invoke(x, i));
                if ((int)(char) m3.invoke(x, i) > 1000) {
                    m1.invoke(o, (char)((char) m3.invoke(x, i)));
                } else {
                    m1.invoke(o, (char)(__[((char) m3.invoke(x, i))] + y));
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        return o.toString();
    }
    /**
     * Matches strings like {@code obj.myMethod(params)} and
     * {@code if (something)} Remembers what's in front of the parentheses and
     * what's inside.
     * <p>
     * {@code (?U)} lets {@code \\w} also match non-ASCII letters.
     */
    public static final Pattern PARENTHESES_REGEX = Pattern.compile("(?U)([.\\w]+)\\s*\\((.*)\\)");

    /*
     * After these Java keywords may come an opening parenthesis.
     */
    private static List<String> keyWordsBeforeParens = Arrays.asList("while", "for", "if",
            "try", "catch", "switch");

    private static boolean containsMethodCall(final String s) {
        final Matcher matcher = PARENTHESES_REGEX.matcher(s);

        int searchIndex = 0;
        while (matcher.find(searchIndex)) {


            final String beforeParens = matcher.group(1);
            final String insideParens = matcher.group(2);
            System.out.println("beforeParens: " + beforeParens);
            System.out.println("insideParens: " + insideParens);

            if (keyWordsBeforeParens.contains(beforeParens)) {
                System.out.println("Keyword: " + beforeParens);
                return containsMethodCall(insideParens);
            } else {

                System.out.println("Method name: " + beforeParens);
                return true;
            }
        }
        return false;
    }


    private static String readFile(String englishFile) throws IOException {
        BufferedReader dic1 = new BufferedReader(new FileReader(englishFile));
        StringBuilder file = new StringBuilder();
        try {

            String line = dic1.readLine();
            while (line != null) {
                String word = line.trim();
                file.append(word);
                line = dic1.readLine();
            }
        } finally {
            dic1.close();
            return file.toString();
        }
    }

    private static void generateArray(int offset) {
        ArrayList<Integer> array =new ArrayList<Integer>();
        for (int i = 0; i < 500; i++) {
            array.add(i + offset);
        }
        Collections.shuffle(array);
        System.out.print("int [] __ = {");
        for(int i = 0; i < array.size() - 1; i++) {
            System.out.print(array.get(i) + ",");
        }
        System.out.print(array.get(array.size() - 1) + "};");

        int key[] = new int[array.size()];
        for(int i = 0; i < array.size(); i++) {
            key[array.get(i) - offset] = i;
        }

        System.out.println("------------------------------------------");
        System.out.print("int [] __ = {");
        for(int i = 0; i < key.length - 1; i++) {
            System.out.print(key[i] + ",");
        }
        System.out.print(key[key.length - 1] + "};");
    }

    private static String encodeStringInArrayStyle(String str, int offset) {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < str.length(); i++) {
            int s = str.charAt(i) + offset;
            for (int j = 0; j < __.length; j++ ) {
                if (__[j] == s) {
                    temp.append((char)j);
                    continue;
                }
            }
        }
        return temp.toString();
    }

    private static Object decodeStringInArrayStyle(String x, int y) throws Exception{
        Object o = Class.forName("java.lang.StringBuilder").newInstance();
        Method m1 = o.getClass().getMethod("append", Character.TYPE);
        Method m3 = "__1sdasdacc".getClass().getMethod("charAt", Integer.TYPE);
        Method m4 = "cvbcvbas__".getClass().getMethod("length");
        StringBuilder a = new StringBuilder();

        for (int i = 0; i < (Integer)m4.invoke(x); i++){
            m1.invoke(o, (char)(__[((char) m3.invoke(x, i))] - y));
        }
        return o.toString();
    }

    private static void reflectionForDrawables() throws Exception {
        Class c1 = Class.forName("android.graphics.drawable.Drawable");
        Object o = Class.forName("java.lang.StringBuilder").newInstance();
        Method m1 = c1.getMethod("createFromStream", Object.class, String.class);
        m1.invoke(null,null);


    }




}
