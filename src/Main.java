public class Main {
    public static void main(String[] args) {

        //add or remove balls here. more balls more time ig, cuz its single threaded also can depend on camera's width n height

        //materials
        Lambertian material_ground = new Lambertian(new Color(0.2,0.8,0.2));
        Lambertian material_center = new Lambertian(new Color(0.9,0.9,0.9));
        Dielectric material_left = new Dielectric(1.00 / 1.33);
        Dielectric glass = new Dielectric(1.50);
        Metal metal_1 = new Metal(new Color(0.8,0.8,0.8), 0.05);
        Metal material_right = new Metal(new Color(0.8,0.6,0.2), 1.0);

        //world
        HittableList world = new HittableList();

        world.add(new Sphere(new Vec3.Point3(0.0, -100.5, 0.0), 100.0, glass));
        world.add(new Sphere(new Vec3.Point3(-2.0, 0.0, 2.0), 0.4, material_center));
        world.add(new Sphere(new Vec3.Point3(-2.0, 0.0, 0.0), 0.5, material_left));
        world.add(new Sphere(new Vec3.Point3(0.0, 0.0, 0.0), 0.3, metal_1));
        world.add(new Sphere(new Vec3.Point3(1.0, 0.0, 2.0), 0.5, material_right));

        Camera cam = new Camera();

        cam.aspect_ratio = 16.0/9.0;
        cam.image_width = 1200;
        cam.samples_per_pixel = 500;
        cam.max_depth = 50;
        cam.vfov = 90;
        cam.lookform = new Vec3.Point3(-3,2,4);
        cam.lookAt = new Vec3.Point3(0,0,0);
        cam.vup = new Vec3(0,1,0);

        cam.render(world);
    }
}