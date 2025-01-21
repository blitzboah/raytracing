public abstract class Material {
    public abstract boolean scatter(Ray r, HitRecord rec, Color attenuation, Ray scattered);
}

