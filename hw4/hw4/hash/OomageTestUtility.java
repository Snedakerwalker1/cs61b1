package hw4.hash;

import java.util.List;

public class OomageTestUtility {
    public static boolean haveNiceHashCodeSpread(List<Oomage> oomages, int M) {
        int[] buckets = new int[M ];
        int N = oomages.size();
        for (int i = 0; i < M; i += 1) {
            buckets[i] = 0;
        }
        int bucketnum = 0;
        for (Oomage o : oomages) {
            bucketnum = (o.hashCode() & 0x7FFFFFFF) % M;
            buckets[bucketnum] += 1;
        }
        for (int i = 0; i < M; i += 1) {
            if (N / 50 > buckets[i]) {
                return false;
            }
            if (N / 2.5 < buckets[i]) {
                return false;
            }
        }
        return true;
    }
}
