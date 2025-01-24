import java.io.FileWriter;
import java.io.IOException;

public class Camera {
    // image properties
    public double aspect_ratio = 16.0 / 9.0;
    public int image_width = 400;
    public int image_height = (int) (image_width / aspect_ratio);
    public int samples_per_pixel = 10; //count of random samples for each pixel
    public int max_depth = 10;

    public double vfov = 90;
    public Vec3.Point3 lookform = new Vec3.Point3(0,0,0);
    public Vec3.Point3 lookAt = new Vec3.Point3(0,0,-1);
    public Vec3 vup = new Vec3(0,1,0);


    // camera parameters
    private double pixel_samples_scales; //color scale factor for a sum of pixel samples
    private Vec3.Point3 cameraCenter;
    private double viewportHeight;
    private double viewportWidth;
    private double focalLength = 1.0;

    // precomputed properties
    private Vec3 viewportU;
    private Vec3 viewportV;
    private Vec3 pixelDeltaU;
    private Vec3 pixelDeltaV;
    private Vec3 pixel00Loc;
    private Vec3 u,v,w;

    private void init() {
        cameraCenter = lookform;

        focalLength = (lookform.sub(lookAt)).length();
        pixel_samples_scales = 1.0 / samples_per_pixel;

        double theta = rtweekend.degreesToRadian(vfov);
        double h = Math.tan(theta/2);
        viewportHeight = 2*h*focalLength;
        viewportWidth = viewportHeight * ((double) image_width/image_height);

        w = Vec3.unitVector(lookform.sub(lookAt));
        u = Vec3.unitVector(Vec3.cross(vup, w));
        v = Vec3.cross(w,u);

        viewportU = u.multiply(viewportWidth);
        viewportV = v.nega().multiply(viewportHeight);

        // pixel delta vectors
        pixelDeltaU = viewportU.divide(image_width);
        pixelDeltaV = viewportV.divide(image_height);

        // upper left pixel
        Vec3 viewportUpperLeft = cameraCenter
                .sub(w.multiply(focalLength)) // center - (focal_length * w)
                .sub(viewportU.divide(2))     // - viewport_u/2
                .sub(viewportV.divide(2));    // - viewport_v/2
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
                  Color pixelColor = new Color(0,0,0);
                  for(int sample = 0; sample < samples_per_pixel; sample++){
                      Ray r = getRay(i,j);
                      pixelColor = pixelColor.add(rayColor(r,max_depth,world));
                  }

                    // write the color of the pixel to the file
                    Color scaledColor = pixelColor.multiply(pixel_samples_scales);
                    Color.writeColor(writer, scaledColor);
                }
            }
        } catch (IOException e) {
            e.getMessage();
        }
    }

    public Ray getRay(int i, int j){
        Vec3 offset = sampleSquare();
        Vec3 pixel_sample = pixel00Loc.add(pixelDeltaU.multiply(i + offset.x())).add(pixelDeltaV.multiply(j + offset.y()));
        Vec3.Point3 ray_origin = cameraCenter;
        Vec3 ray_direction = pixel_sample.sub(ray_origin);

        return new Ray(ray_origin, ray_direction);
    }

    public Vec3 sampleSquare(){
        return new Vec3(rtweekend.random_double() - 0.5, rtweekend.random_double() - 0.5, 0);
    }

    public Color rayColor(Ray r, int depth, Hittable world) {
        if(depth <= 0) return new Color(0,0,0);

        HitRecord rec = new HitRecord();
        if (world.hit(r, new Interval(0.001, Double.POSITIVE_INFINITY), rec)) {
            Ray scattered = new Ray(rec.p, new Vec3());
            Color attenuation = new Color();
            if(rec.mat.scatter(r,rec,attenuation,scattered)) {
                Vec3 temp = attenuation.multiply(rayColor(scattered, depth-1, world));
                return new Color(temp.x(), temp.y(), temp.z()); // Create a new Color from the Vec3
            }
        }

        Vec3 unitDirection = Vec3.unitVector(r.direction());
        double t = 0.5 * (unitDirection.y() + 1.0);
        return new Color(
                (1.0 - t) * 1.0 + t * 0.2,
                (1.0 - t) * 1.0 + t * 0.3,
                (1.0 - t) * 1.0 + t * 1.0
        );
    }
}