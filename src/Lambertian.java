public class Lambertian extends Material {

    private Color albedo;

    public Lambertian(Color albedo){
        this.albedo = albedo;
    }
    @Override
    public boolean scatter(Ray r, HitRecord rec, Color attenuation, Ray scattered) {
        Vec3 scatterd_dir = rec.normal.add(Vec3.randomUnitVector());

        if(scatterd_dir.nearZero()){
            scatterd_dir = rec.normal;
        }

        attenuation.set(0, albedo.x());
        attenuation.set(1, albedo.y());
        attenuation.set(2, albedo.z());

        Vec3.Point3 scatterOrigin = new Vec3.Point3(rec.p);
        scattered.setOrigin(scatterOrigin);
        scattered.setDirection(scatterd_dir);

        return true;
    }
}
