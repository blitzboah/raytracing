public class Sphere implements Hittable{

    private Vec3.Point3 center;
    private double radius;

    public Sphere(Vec3.Point3 center, double radius){
        this.center = center;
        this.radius = radius;
    }
    @Override
    public boolean hit(Ray r, double rayTmin, double rayTmax, HitRecord rec) {
        Vec3 oc = r.origin().sub(center);
        double a = r.direction().lengthSquared();
        double h = Vec3.dot(r.direction(), oc);
        double c = oc.lengthSquared() - radius*radius;

        double discriminant = h*h - a*c;

        if(discriminant < 0){
            return false;
        }

        double sqrtd = Math.sqrt(discriminant);

        double root = (h - sqrtd) / a;
        if(root <= rayTmin || rayTmax <= root){
            root = (h + sqrtd) / a;
            if(root <= rayTmin || rayTmax <= root)
                return false;
        }

        rec.t = root;
        rec.p = new Vec3.Point3(r.at(rec.t));
        Vec3 outward_normal = rec.p.sub(center).divide(radius);
        rec.setFaceNormal(r, outward_normal);

        return true;
    }
}
