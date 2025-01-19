public class rtweekend {
    //constants
    public static final double INFINITY = Double.POSITIVE_INFINITY;
    public static final double PI = 3.1415926535897932385;

    //util funcs

    //convert deg to radian
    public static double degreesToRadian(double degrees){
        return degrees * PI / 180.0;
    }

    public static double random_double(){
        return Math.random();
    }

    public static double random_double(double min, double max){
        return min + (max-min) * random_double();
    }
}
