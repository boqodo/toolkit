package java.z.cube.sis;


import org.springframework.beans.BeanUtils;
import static org.assertj.core.api.Assertions.*;
import org.junit.Test;
import org.objenesis.ObjenesisHelper;


public class ObjenesisTest {

	
	@Test
	public final void test(){
		//没有默认构造函数，并且该构造函数是私有的都可以实例化成功
		Tv p=ObjenesisHelper.newInstance(Tv.class);
		assertThat(p).isNotNull();
		Tv x=BeanUtils.instantiate(Tv.class);
		assertThat(x).isNotNull();
	}
}
