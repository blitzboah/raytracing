import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;

public class Color extends Vec3{
    public Color(){
        super();
    }

    public Color(double r, double g, double b){
        super(r,g,b);
    }

    public static void writeColor(FileWriter writer, Color pixelColor) throws IOException {
        //clamp values between 0 to 1
        double r = Math.min(Math.max(pixelColor.x(), 0), 1);
        double g = Math.min(Math.max(pixelColor.y(), 0), 1);
        double b = Math.min(Math.max(pixelColor.z(), 0), 1);

        //translate 0,1 to byte range [0,255]
        int rByte = (int)(255.99 * r);
        int gByte = (int)(255.99 * g);
        int bByte = (int)(255.99 * b);

        //makes sure it stays in the range
        rByte = Math.min(Math.max(rByte, 0), 255);
        gByte = Math.min(Math.max(gByte, 0), 255);
        bByte = Math.min(Math.max(bByte, 0), 255);


        writer.write(rByte +" "+ gByte +" "+ bByte +"\n");
    }
}
