import lab14lib.Generator;

/**
 * Created by wsnedaker on 4/28/2017.
 */
public class AcceleratingSawToothGenerator implements Generator {
    int period;
    int state;
    double slope;
    double acceleration;
    int thing;


    public AcceleratingSawToothGenerator(int period, double a) {
        this.period = period;
        this.acceleration = a;
        this.state = 0;
        this.thing = 0;

    }
    public double next() {
        state += 1;
        if (thing == period - 1) {
            thing = 0;
            this.period = (int) (acceleration * period);
        }
        else {
            thing += 1;
        }
        return normalize(thing);
    }

    private double normalize(int part) {
        if (part == this.period - 1) {
            return 1.0;
        }
        return ((part) * 2.0 / this.period) - 1.0;
    }
}
