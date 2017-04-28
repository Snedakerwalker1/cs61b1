import lab14lib.Generator;

/**
 * Created by wsnedaker on 4/28/2017.
 */
public class SawToothGenerator implements Generator {
    int period;
    int state;
    double slope;
    int thing;

    public  SawToothGenerator(int period) {
        this.period = period;
        this.state = 0;
        this.thing = 0;
        this.slope = 2.0 / period;
    }

    public double next() {
        state += 1;
        return normalize(state);
    }

    private double normalize(int part) {
        return ((part % period) * this.slope) - 1.0;
    }
}
