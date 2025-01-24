import java.util.Random;

public class Vec3 {
    private double[] e;
    private static final Random random = new Random();

    public Vec3(){
        this.e = new double[]{0,0,0};
    }

    public Vec3(double e0, double e1, double e2){
        this.e = new double[]{e0, e1, e2};
    }

    public double x(){
        return e[0];
    }

    public double y(){
        return e[1];
    }

    public double z(){
        return e[2];
    }

    //negation
    public Vec3 nega(){
        return new Vec3(-e[0], -e[1], -e[2]);
    }

    public double get(int i){
        return e[i];
    }

    public void set(int i, double value){
        e[i] = value;
    }

    //operators
    public Vec3 add(Vec3 v){
        return new Vec3(e[0]+v.e[0], e[1]+v.e[1], e[2]+v.e[2]);
    }

    public Vec3 sub(Vec3 v){
        return new Vec3(e[0]-v.e[0], e[1]-v.e[1], e[2]-v.e[2]);
    }

    public Vec3 multiply(Vec3 v){
        return new Vec3(e[0]*v.e[0], e[1]*v.e[1], e[2]*v.e[2]);
    }

    public Vec3 multiply(double t){
        return new Vec3(t*e[0], t*e[1], t*e[2]);
    }

    public Vec3 divide(double t){
        return this.multiply(1/t);
    }

    public Vec3 multiplyInPlace(double t) {
        e[0] *= t;
        e[1] *= t;
        e[2] *= t;
        return this;
    }

    public Vec3 divideInPlace(double t) {
        return this.multiplyInPlace(1 / t);
    }

    public double length(){
        return Math.sqrt(lengthSquared());
    }

    public double lengthSquared(){
        return e[0]*e[0] + e[1]*e[1] + e[2]*e[2];
    }

    public boolean nearZero(){
        double s = 1e-8;
        return (Math.abs(e[0]) < s) && (Math.abs(e[1]) < s) && (Math.abs(e[2]) < s);
    }

    public static Vec3 random(){
        return new Vec3(rtweekend.random_double(), rtweekend.random_double(), rtweekend.random_double());
    }

    public static Vec3 random(double min, double max){
        return new Vec3(rtweekend.random_double(min, max), rtweekend.random_double(min, max), rtweekend.random_double(min, max));
    }

    public static double dot(Vec3 u, Vec3 v) {
        return u.e[0] * v.e[0] + u.e[1] * v.e[1] + u.e[2] * v.e[2];
    }

    public static Vec3 cross(Vec3 u, Vec3 v) {
        return new Vec3(
                u.e[1] * v.e[2] - u.e[2] * v.e[1],
                u.e[2] * v.e[0] - u.e[0] * v.e[2],
                u.e[0] * v.e[1] - u.e[1] * v.e[0]
        );
    }

    public static Vec3 unitVector(Vec3 v) {
        return v.divide(v.length());
    }

    public static Vec3 randomUnitVector(){
        while (true){
            Vec3 p = Vec3.randomPoint(-1,1);
            double lengthSquared = p.lengthSquared();
            if(1e-160 < lengthSquared && lengthSquared <= 1){
                return p.divide(Math.sqrt(lengthSquared));
            }
        }
    }

    public static Vec3 randomPoint(double min, double max) {
        double x = min + (max - min) * random.nextDouble();
        double y = min + (max - min) * random.nextDouble();
        double z = min + (max - min) * random.nextDouble();
        return new Vec3(x, y, z);
    }

    public static Vec3 randomOnHemisphere(Vec3 normal){
        Vec3 on_unit_sphere = randomUnitVector();
        if(Vec3.dot(on_unit_sphere, normal) > 0.0)
            return on_unit_sphere;
        else
            return on_unit_sphere.nega();
    }

    public static Vec3 reflect(Vec3 v, Vec3 n){
        return v.sub(n.multiply(2 * Vec3.dot(v,n)));
    }

    public static Vec3 refract(Vec3 uv, Vec3 n, double etai_over_etat) {
        double cos_theta = Math.min(Vec3.dot(uv.nega(), n), 1.0);
        Vec3 r_out_perp = uv.add(n.multiply(cos_theta)).multiply(etai_over_etat);
        Vec3 r_out_parallel = n.multiply(-Math.sqrt(Math.abs(1.0 - r_out_perp.lengthSquared())));
        return r_out_perp.add(r_out_parallel);
    }

    @Override
    public String toString() {
        return e[0] + " " + e[1] + " " + e[2];
    }

    // point3 is just an alias for Vec3
    public static class Point3 extends Vec3 {
        public Point3() {
            super();
        }

        public Point3(double x, double y, double z) {
            super(x, y, z);
        }

        public Point3(Vec3 v){
            super(v.x(), v.y(), v.z());
        }
    }

}