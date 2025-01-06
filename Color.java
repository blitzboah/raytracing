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
        double r = pixelColor.x();
        double g = pixelColor.y();
        double b = pixelColor.z();

        //translate 0,1 to byte range [0,255]
        int rByte = (int)(255.99 * r);
        int gByte = (int)(255.99 * g);
        int bByte = (int)(255.99 * b);

        writer.write(rByte +" "+ gByte +"  "+ bByte +"\n");
    }
}
