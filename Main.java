public class Main {
    public static void main(String[] args) {

        //double R = Math.cos(rtweekend.PI/4);

        //Lambertian left = new Lambertian(new Color(0,0,1));
        //Lambertian right = new Lambertian(new Color(1,0,0));

        //materials
        Lambertian material_ground = new Lambertian(new Color(0.2,0.8,0.2));
        Lambertian material_center = new Lambertian(new Color(0.9,0.9,0.9));
        Dielectric material_left = new Dielectric(1.00 / 1.33);
        //Dielectric material_bubble = new Dielectric(1.00 / 1.50 );
        Metal material_right = new Metal(new Color(0.8,0.6,0.2), 1.0);

        //world
        HittableList world = new HittableList();

        //world.add(new Sphere(new Vec3.Point3(-R,0,-1),R, left));
        //world.add(new Sphere(new Vec3.Point3(R,0,-1), R, right));

        world.add(new Sphere(new Vec3.Point3(0.0, -100.5, 0.0), 100.0, material_ground));
        world.add(new Sphere(new Vec3.Point3(0.0, 0.0, -1.0), 0.5, material_center));
        world.add(new Sphere(new Vec3.Point3(-1.0, 0.0, 0.0), 0.5, material_left));
        //world.add(new Sphere(new Vec3.Point3(1.0, 0.0, -1.0), 0.45, material_bubble));
        world.add(new Sphere(new Vec3.Point3(1.0, 0.0, 0.0), 0.5, material_right));

        Camera cam = new Camera();

        cam.aspect_ratio = 16.0/9.0;
        cam.image_width = 1200;
        cam.samples_per_pixel = 500;
        cam.max_depth = 50;
        cam.vfov = 30;
        cam.lookform = new Vec3.Point3(-2,2,1);
        cam.lookAt = new Vec3.Point3(0,0,0);
        cam.vup = new Vec3(0,1,0);

        cam.render(world);
    }
}