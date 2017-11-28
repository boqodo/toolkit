package java.z.cube.retroift;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RetrofitXmlTest {

	public static void main(String[] args) {
		Retrofit retrofit = new Retrofit.Builder()
				.addConverterFactory(SimpleXmlConverterFactory.create())
				.baseUrl("http://10.172.3.120:8090/centerServer/")
				.build();
		YyzzptService service = retrofit.create(YyzzptService.class);
		Call<ResponseBody> call = service
				.getHospitalDepartmentList("<data>\r\n"
						+ "	<funCode>006301</funCode>\r\n"
						+ "	<frontHospitalId>00778</frontHospitalId>\r\n"
						+ "	<destHospitalId>957102</destHospitalId>\r\n"
						+ "	<serialNo>f38dd63e3be05d241e6c390567c4dbfd</serialNo>\r\n"
						+ "</data>\r\n" + "");
		try {
			Response<ResponseBody> hospitalRootDepartmentList = call.execute();
			System.out.println(hospitalRootDepartmentList.body().string());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
