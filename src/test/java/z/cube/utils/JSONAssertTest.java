package z.cube.utils;

import org.json.JSONException;
import org.junit.Test;
import org.skyscreamer.jsonassert.JSONAssert;

public class JSONAssertTest {
    @Test
    public final void test() throws JSONException {
        String expected = "{id:1,name:\"Joe\",friends:[{id:2,name:\"Pat\",pets:[\"dog\"]},{id:3,name:\"Sue\",pets:[\"bird\",\"fish\"]}],pets:[]}";
        String actual = "{id:1,name:\"Joe\",friends:[{id:2,name:\"Pat\",pets:[\"dog\"]},{id:3,name:\"Sue\",pets:[\"cat\",\"fish\"]}],pets:[]}";
        JSONAssert.assertEquals(expected, actual, false);
    }
    @Test
    public final void test2() throws JSONException {
        String excpected="{name:'Lisi',age:2}";
        String actual="{age:2,name:'Lisi'}";
        JSONAssert.assertEquals(excpected,actual,false);

    }
}
