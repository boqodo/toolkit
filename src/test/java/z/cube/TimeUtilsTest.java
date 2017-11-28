package java.z.cube;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.Duration;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.text.ParseException;

/**
 * Created by KingSoft on 2015/4/4.
 */
public class TimeUtilsTest {

    @Test
    public final void testTime() throws ParseException {
        long start = DateUtils.parseDate("2014-07-01", "yyyy-MM-dd").getTime();
        long end = DateUtils.parseDate("2016-12-22", "yyyy-MM-dd").getTime();
        Duration duration = new Duration(start, end);
        System.out.println(duration.getStandardDays());
    }
}
