public class Vec3 {
    private double[] e;

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
