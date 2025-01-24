public class Ray {
    private Vec3.Point3 orig;
    private Vec3 dir;

    public Ray(){}

    public Ray(Vec3.Point3 origin, Vec3 direction){
        this.orig = origin;
        this.dir = direction;
    }

    public Vec3.Point3 origin(){
        return orig;
    }

    public Vec3 direction(){
        return dir;
    }

    public Vec3 at(double t){
        return orig.add(dir.multiply(t));
    }

    public void setOrigin(Vec3.Point3 origin) {
        this.orig = origin;
    }

    public void setDirection(Vec3 direction) {
        this.dir = direction;
    }
}
