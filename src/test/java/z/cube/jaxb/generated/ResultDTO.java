package java.z.cube.jaxb.generated;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.annotation.JsonValue;


/**
 * 移动端请求结果传输对象
 * 
 * <pre>
 * 
 * isSuccess：
 * 	true	则表示请求成功并且业务逻辑执行正确，直接取data呈现或逻辑处理；
 * 	false	则表示请求出现异常或是业务逻辑执行出错；
 * 
 * 针对false的情况需要根据code做相应的逻辑处理：
 * 	"000"	则表示非处理的异常，直接展示（description）给用户即可
 * 	非000	则需要根据业务需要，做相应的逻辑判断处理；具体错误代码@see {@link MobileError}
 * 
 * description 异常信息的描述
 * 
 * </pre>
 */
public class ResultDTO<T> {


	/**
	 * 请求成功返回的结果传输对象 该对象携带相应的数据，该数据可以是任何可json化的数据
	 * 
	 * @param data
	 *            携带输出的数据
	 * @return
	 */
	public static final <T> ResultDTO<T> SUCCESS(T data) {
		return new ResultDTO<T>(true, null, null, data);
	}

	/**
	 * 请求成功返回的结果传输对象，并且携带的数据是boolean值true
	 */
	public static final ResultDTO<Boolean> SUCCESS = SUCCESS(true);

	/** * 是否请求成功，默认失败 */
	
    private boolean isSuccess;
	/** * 代码标识 */
    private String code;
	/** * 描述，一般失败才有描述，该描述是异常信息 */
    private String description;
	/** * 数据，需要展示处理的数据 */
    private T data;

	public ResultDTO() {
	}

	public ResultDTO(boolean isSuccess, String code, String description, T data) {
		super();
		this.isSuccess = isSuccess;
		this.code = code;
		this.description = description;
		this.data = data;
	}
	public boolean getIsSuccess() {
		return isSuccess;
	}
	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}

	@Override
	public String toString() {
		return "ResultDTO [isSuccess=" + isSuccess + ", code=" + code
				+ ", description=" + description + ", data=" + data + "]";
	}
}
