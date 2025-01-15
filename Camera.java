import java.io.FileWriter;
import java.io.IOException;

public class Camera {
    // image properties
    public double aspect_ratio = 16.0 / 9.0;
    public int image_width = 400;
    public int image_height = (int) (image_width / aspect_ratio);

    // camera parameters
    private Vec3.Point3 cameraCenter = new Vec3.Point3(0, 0, 0);
    private double viewportHeight = 2.0;
    private double viewportWidth = viewportHeight * aspect_ratio;
    private double focalLength = 1.0;

    // precomputed properties
    private Vec3 viewportU;
    private Vec3 viewportV;
    private Vec3 pixelDeltaU;
    private Vec3 pixelDeltaV;
    private Vec3 pixel00Loc;

    private void init() {
        // vectors defining the viewport
        viewportU = new Vec3(viewportWidth, 0, 0);
        viewportV = new Vec3(0, -viewportHeight, 0);

        // pixel delta vectors
        pixelDeltaU = viewportU.divide(image_width);
        pixelDeltaV = viewportV.divide(image_height);

        // upper left pixel
        Vec3 viewportUpperLeft = cameraCenter
                .sub(new Vec3(0, 0, focalLength))
                .sub(viewportU.multiply(0.5))
                .sub(viewportV.multiply(0.5));
        pixel00Loc = viewportUpperLeft.add(pixelDeltaU.multiply(0.5)).add(pixelDeltaV.multiply(0.5));
    }

    public void render(Hittable world) {
        init();
        String fileName = "output.ppm";

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("P3\n" + image_width + " " + image_height + "\n255\n");
            // render
            for (int j = 0; j < image_height; j++) {
                for (int i = 0; i < image_width; i++) {
                    // calculate the pixel center in world space
                    Vec3 pixelCenter = pixel00Loc.add(pixelDeltaU.multiply(i)).add(pixelDeltaV.multiply(j));
                    Vec3 rayDirection = pixelCenter.sub(cameraCenter);
                    Ray r = new Ray(cameraCenter, rayDirection);

                    // color calculation for the ray
                    Color pixelColor = rayColor(r, world);

                    // write the color of the pixel to the file
                    Color.writeColor(writer, pixelColor);
                }
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public Color rayColor(Ray r, Hittable world) {
        HitRecord rec = new HitRecord();
        if (world.hit(r, new Interval(0, Double.POSITIVE_INFINITY), rec)) {
            return new Color(0.5 * (rec.normal.x() + 1),
                    0.6 * (rec.normal.y() + 1),
                    0.7 * (rec.normal.z() + 1));
        }
        Vec3 unitDirection = Vec3.unitVector(r.direction());
        double t = 0.5 * (unitDirection.y() + 1.0);
        return new Color(
                (1.0 - t) * 1.0 + t * 0.5,
                (1.0 - t) * 1.0 + t * 0.7,
                (1.0 - t) * 1.0 + t * 1.0
        );
    }
}