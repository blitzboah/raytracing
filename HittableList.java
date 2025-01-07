import java.util.ArrayList;
import java.util.List;

public class HittableList implements Hittable {
    private List<Hittable> objects;

    public HittableList(){
        this.objects = new ArrayList<>();
    }

    public HittableList(Hittable object){
        this();
        add(object);
    }

    public void add(Hittable object){
        objects.add(object);
    }

    public void clear(){
        objects.clear();
    }

    @Override
    public boolean hit(Ray r, double rayTmin, double rayTmax, HitRecord rec){
        HitRecord tempRec = new HitRecord();
        boolean hitAnything = false;
        double closestSoFar = tempRec.t;

        for(Hittable object : objects){
            if(object.hit(r, rayTmin, rayTmax, tempRec)){
                hitAnything = true;
                closestSoFar = tempRec.t;
                rec.set(tempRec);
            }
        }
        return hitAnything;
    }
}
