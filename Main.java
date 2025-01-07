import java.io.FileWriter;
import java.io.IOException;

public class Main {

    public static Color ray_color(Ray r, Hittable world){

        HitRecord rec = new HitRecord();
        if(world.hit(r, 0, rtweekend.INFINITY, rec)){
            Color sphereColor = new Color(0.5,0.6,0.7);
            return new Color(0.5 * (rec.normal.x() + 1),
                    0.5 * (rec.normal.y() + 1),
                    0.5 * (rec.normal.z() + 1));
        }
        Vec3 unitDirection = Vec3.unitVector(r.direction());
        double a = 0.5 * (unitDirection.y() + 1.0); //tweak the direction or added value to get diff gradient
        return new Color(
                (1.0 - a) * 1.0 + a * 0.5,
                (1.0 - a) * 1.0 + a * 0.7,
                (1.0 - a) * 1.0 + a * 1.0 );
    }

    public static void main(String[] args) {
        //image

        double aspect_ratio = 16.0 / 9.0;
        int image_width = 400;

        //calculate image height to be at least 1
        int image_height = (int) (image_width / aspect_ratio);
        if (image_height < 1) {
            image_height = 1;
        }

        String fileName = "output.ppm";

        try(FileWriter writer = new FileWriter(fileName)) {
            writer.write("P3\n" + image_width + " " + image_height + "\n255\n");

            //world
            HittableList world = new HittableList();


            world.add(new Sphere(new Vec3.Point3(0, 100.5, -1), 100));
            world.add(new Sphere(new Vec3.Point3(0,0,-1), 0.5));

            //camera parameters
            double viewportHeight = 2.0;
            double viewportWidth = viewportHeight * (image_width / (double) image_height);
            double focalLength = 1.0;
            Vec3.Point3 cameraCenter = new Vec3.Point3(0, 0, -2); //tweak the z value to zoom in or zoom out

            //vectors defining viewports
            Vec3 viewportU = new Vec3(viewportWidth, 0, 0);
            Vec3 viewportV = new Vec3(0, -viewportHeight, 0);

            //pixel delta vectors
            Vec3 pixelDeltaU = viewportU.divide(image_width);
            Vec3 pixelDeltaV = viewportV.divide(image_height);

            //upper left pixel
            Vec3 viewportUpperLeft = cameraCenter
                    .sub(new Vec3(0, 0, focalLength))
                    .sub(viewportU.multiply(0.5))
                    .sub(viewportV.multiply(0.5));
            Vec3 pixel00Loc = viewportUpperLeft.add(pixelDeltaU.multiply(0.5)).add(pixelDeltaV.multiply(0.5));

            //render
            for (int j = 0; j < image_height; j++) {
                for (int i = 0; i < image_width; i++) {
                    // calculate the pixel center in world space
                    Vec3 pixelCenter = pixel00Loc.add(pixelDeltaU.multiply(i)).add(pixelDeltaV.multiply(j));
                    Vec3 rayDirection = pixelCenter.sub(cameraCenter);
                    Ray r = new Ray(cameraCenter, rayDirection);

                    // color calculation for the ray
                    Color pixelColor = ray_color(r, world);

                    // write the color of the pixel to the file
                    Color.writeColor(writer, pixelColor);
                }
            }
        }
        catch (IOException e){
            e.getMessage();
        }
    }
}