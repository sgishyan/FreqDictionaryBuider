package SpiderProtector;

/**
 * Created by suren on 5/14/18.
 */
public class ImageTemp {
//    public static Drawable loadImage(Context context, String path) {
//        path = path.replace(".png",".mp3");
//        String imageStringEnc = read(path, context);
//        StringBuilder imageString2 = null;
//        try {
//            imageString2 = (StringBuilder) rtyui(imageStringEnc, 12, 16);
//        } catch (Exception e) {
//            System.out.println("image error:" + e.getMessage());
//            e.printStackTrace();
//        }
//        Bitmap bitmap = decode(imageString2.toString());
//        return new BitmapDrawable(context.getResources(), bitmap);
//    }
//
//    public static Bitmap decode(String encodedImage){
//        byte[] decodedString = Base64.decode(encodedImage, Base64.DEFAULT);
//        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//        return decodedByte;
//    }
//
//    public static Object rtyui(Object x, int q, int w) throws Exception {
//        int _ =  125 + Integer.parseInt("17");
//
//
//        Class c1 = Class.forName("java.lang.String");
//        Constructor<?> c = Class.forName("java.lang.StringBuilder").getConstructor(c1);
//        Object o =  c.newInstance(x);
//        Method m1 = o.getClass().getMethod("append", Character.TYPE);
//        Method m2 = o.getClass().getMethod("setCharAt", Integer.TYPE, Character.TYPE);
//        Method m3 = "String".getClass().getMethod("charAt", Integer.TYPE);
//        Method m4 = "String".getClass().getMethod("length");
//        StringBuilder a = new StringBuilder();
//
//        int __ = Integer.parseInt("31");
//        for (int i = 0; i < __; i+=3){
//            m2.invoke(o, i, (char)((char)m3.invoke(x, i) ^ _));
//        }
//        return o;
//    }
//
//    public static String read(String filename, Context context) {
//
//        BufferedReader reader = null;
//        StringBuilder str = new StringBuilder();
//        try {
//            reader = new BufferedReader(
//                    new InputStreamReader(context.getAssets().open(filename)));
//
//            // do reading, usually loop until end of file reading
//            String mLine;
//            while ((mLine = reader.readLine()) != null) {
//                str.append(mLine);
//            }
//        } catch (IOException e) {
//            //log the exception
//        } finally {
//            if (reader != null) {
//                try {
//                    reader.close();
//                } catch (IOException e) {
//                    //log the exception
//                }
//            }
//        }
//        return str.toString();
//    }
}
