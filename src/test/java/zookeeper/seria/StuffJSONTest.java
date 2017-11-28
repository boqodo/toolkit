package java.zookeeper.seria;

import org.apache.commons.lang3.CharEncoding;
import org.junit.Test;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import z.cube.podam.ExPackage;
import z.cube.utils.JsonUtilsEx;

import com.dyuproject.protostuff.JsonIOUtil;
import com.dyuproject.protostuff.LinkedBuffer;
import com.dyuproject.protostuff.Schema;

public class StuffJSONTest {

	@Test
	public final void test() throws Exception {
		boolean numeric = false;
		
		PodamFactory factory = new PodamFactoryImpl();
		ExPackage bean = factory.manufacturePojo(ExPackage.class);
		long time1 = System.currentTimeMillis();
		
		
		for(int i=0;i<1000000;i++){
			LinkedBuffer buffer = LinkedBuffer.allocate( LinkedBuffer.DEFAULT_BUFFER_SIZE );
			Schema<ExPackage> s=SchemaUtils.getSchema(ExPackage.class);
			byte[] b=JsonIOUtil.toByteArray(bean, s, numeric, buffer);
			String res=new String(b,CharEncoding.UTF_8);
			//System.out.println(res);
		}
		System.out.println("protostuffjson方案[序列化10000次]耗时：" + (System.currentTimeMillis() - time1) + "ms");

		long time2 = System.currentTimeMillis();
		for(int i=0;i<1000000;i++){
			String res=JsonUtilsEx.generate(bean);
			//System.out.println(res);
		}
		System.out.println("fasterxml方案[序列化10000次]耗时：" + (System.currentTimeMillis() - time2) + "ms");
		ExPackage ep = new ExPackage();
		//JsonIOUtil.mergeFrom(baos.toByteArray(), ep, SchemaUtils.getSchema(ExPackage.class), numeric);
		System.out.println(JsonUtilsEx.generate(ep));
	}
}
