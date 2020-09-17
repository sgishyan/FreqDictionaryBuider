package connections;


/**
 * Created by David on 8/17/17
 */

public class Logic {

    private static Matrix sMatrix;
    private static ConnectionPool sConnectionPool;
    private static boolean sIsInitialized = false;

    private Logic() {
    }

    public static void init(Matrix matrix, ConnectionPool connectionPool) {
        System.out.println("Log__ init");
        sMatrix = matrix;
        sConnectionPool = connectionPool;
        sIsInitialized = true;

    }

    public static Matrix getMatrix() {
        checkState();
        return sMatrix;
    }

    public static ConnectionPool getConnectionPool() {
        checkState();
        return sConnectionPool;
    }

    private static void checkState() {
        if (!sIsInitialized) {
            throw new IllegalStateException("Class com.mobiloids.connections.logic.Logic is not initialized!");
        }
    }

}
