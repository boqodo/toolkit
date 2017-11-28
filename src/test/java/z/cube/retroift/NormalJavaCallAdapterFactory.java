package java.z.cube.retroift;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.CallAdapter.Factory;
import retrofit2.Retrofit;
@SuppressWarnings("rawtypes")
public class NormalJavaCallAdapterFactory extends Factory {
	

	final static class NormalJavaCallAdapter implements CallAdapter{
		private final Type returnType;
		public NormalJavaCallAdapter(Type returnType){
			this.returnType= returnType;
		}

		@Override
		public Type responseType() {
			return returnType;
		}

		@Override
		public Object adapt(Call call) {
			try {
				return call.execute().body();
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(),e);
			}
		}
		
	}

	@Override
	public CallAdapter<?> get(Type returnType, Annotation[] annotations,
			Retrofit retrofit) {
		if (getRawType(returnType) == Call.class) {
		      return null;
		}
		return new NormalJavaCallAdapter(returnType);
	}
}
