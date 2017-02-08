/**
 * Created by wsnedaker on 2/6/2017.
 */
public class OffByN implements CharacterComparator {
    private int integer;
    public OffByN(int n) {
        this.integer = n;
    }
    @Override
    public boolean equalChars(char x, char y) {
        if (x - y == this.integer || x - y == -this.integer) {
            return true;
        } else {
            return false;
        }
    }
}

