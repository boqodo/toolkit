package java.z.cube.retroift;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface YyzzptService {
	
	@POST("trans")
	Call<ResponseBody> getHospitalDepartmentList(@Body String body);
}