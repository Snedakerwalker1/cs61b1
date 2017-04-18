import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class AGRastererTest extends AGMapTest {
    /**
     * Test the rastering functionality of the student code, by calling their getMapRaster method
     * and ensuring that the resulting map is correct. All of the test data is stored in a
     * TestParameters object that is loaded by the AGMapTest constructor. Note that this test file
     * extends AGMapTest, where most of the interesting stuff happens.
     * @throws Exception
     */
    @Test
    public void testGetMapRaster() throws Exception {
        for (TestParameters p : params) {
            Map<String, Object> studentRasterResult = rasterer.getMapRaster(p.rasterParams);
            System.out.println(p.rasterResult);
            System.out.println(studentRasterResult);
            checkParamsMap("Returned result differed for input: " + p.rasterParams + ".\n",
                    p.rasterResult, studentRasterResult);
        }
    }
    @Test
    public void testMapRaster() throws Exception {
        Map<String, Double> params = new HashMap<>();
        params.put("w", 929.0);
        params.put("h", 944.0);
        params.put("lrlon", -122.2119140625);
        params.put("ullon", -122.2591326176749);
        params.put("ullat", 37.88746545843562);
        params.put("lrlat", 37.83495035769344);
        double lon_ddp = (-122.2119140625 - (-122.2591326176749)) / 929;
        System.out.println(lon_ddp);
        for (double i : rasterer.depth_DPP) {
            System.out.println(i);
        }
        Map<String, Object> results = rasterer.getMapRaster(params);
        System.out.println(results.get("depth"));
        double ne = (double) results.get("raster_lr_lat");
        Assert.assertTrue(ne == 37.83147657274216);

    }
}
