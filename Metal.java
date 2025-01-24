public class Metal extends Material{
    Color albedo;
    double fuzz;

    public Metal(Color albedo, double fuzz){
        this.albedo = albedo;
        this.fuzz = (fuzz < 1) ? fuzz : 1;
    }

    @Override
    public boolean scatter(Ray r, HitRecord rec, Color attenuation, Ray scattered) {
        Vec3 reflected = Vec3.reflect(Vec3.unitVector(r.direction()), rec.normal);
        reflected = Vec3.unitVector(reflected).add(Vec3.randomUnitVector().multiply(fuzz));

        attenuation.set(0, albedo.x());
        attenuation.set(1, albedo.y());
        attenuation.set(2, albedo.z());

        Vec3.Point3 scatterOrigin = new Vec3.Point3(rec.p);
        scattered.setOrigin(scatterOrigin);
        scattered.setDirection(reflected);

        return (Vec3.dot(scattered.direction(), rec.normal) > 0);
    }
}
