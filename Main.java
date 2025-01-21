public class Main {
    public static void main(String[] args) {

        //materials
        Lambertian material_ground = new Lambertian(new Color(0.2,0.8,0.2));
        Lambertian material_center = new Lambertian(new Color(0.9,0.9,0.9));
        Metal material_left = new Metal(new Color(0.8,0.8,0.8));
        Metal material_right = new Metal(new Color(0.8,0.6,0.2));

        //world
        HittableList world = new HittableList();

        world.add(new Sphere(new Vec3.Point3(0.0, -100.5, -1.0), 100.0, material_ground));
        world.add(new Sphere(new Vec3.Point3(0.0, 0.0, -1.2), 0.5, material_center));
        world.add(new Sphere(new Vec3.Point3(-1.0, 0.0, -1.0), 0.5, material_left));
        world.add(new Sphere(new Vec3.Point3(1.0, 0.0, -1.0), 0.5, material_right));

        Camera cam = new Camera();

        cam.aspect_ratio = 16.0/9.0;
        cam.image_width = 400;
        cam.samples_per_pixel = 100;
        cam.max_depth = 50;

        cam.render(world);
    }
}