import java.io.FileWriter;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {

         //world
        HittableList world = new HittableList();
        world.add(new Sphere(new Vec3.Point3(0, -100.5, -1), 100));
        world.add(new Sphere(new Vec3.Point3(0,0,-1), 0.5));

        Camera cam = new Camera();

        cam.aspect_ratio = 16.0/9.0;
        cam.image_width = 400;
        cam.samples_per_pixel = 100;

        cam.render(world);
    }
}