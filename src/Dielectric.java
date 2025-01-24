public class Dielectric extends Material {

    private double refraction_index;

    public Dielectric(double refraction_index) {
        this.refraction_index = refraction_index;
    }

    private static double reflectance(double cosine, double refraction_index) {
        // schlick's approximation for reflectance
        double r0 = (1 - refraction_index) / (1 + refraction_index);
        r0 *= r0;
        return r0 + (1 - r0) * Math.pow((1 - cosine), 5);
    }

    @Override
    public boolean scatter(Ray r, HitRecord rec, Color attenuation, Ray scattered) {
        attenuation.set(0, 1.0);
        attenuation.set(1, 1.0);
        attenuation.set(2, 1.0);

        double ri = rec.front_face ? (1.0 / refraction_index) : refraction_index;

        Vec3 unit_direction = Vec3.unitVector(r.direction());
        double cos_theta = Math.min(Vec3.dot(unit_direction.nega(), rec.normal), 1.0);
        double sin_theta = Math.sqrt(1.0 - cos_theta * cos_theta);

        boolean cannot_refract = ri * sin_theta > 1.0;
        Vec3 direction;

        if (cannot_refract || reflectance(cos_theta, ri) > rtweekend.random_double()) {
            // reflect the ray
            direction = Vec3.reflect(unit_direction, rec.normal);
        } else {
            // refract the ray
            direction = Vec3.refract(unit_direction, rec.normal, ri);
        }

        scattered.setOrigin(rec.p);
        scattered.setDirection(direction);
        return true;
    }

}