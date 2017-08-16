package cn.lsh.exception;

public enum ResponseStatus {

		OK(200,"成功"),
	    DATA_CREATE_ERROR(100, "新增数据失败"),
	    DATA_REQUERY_ERROR(101, "查询数据失败"),
	    DATA_UPDATED_ERROR(102, "更新数据失败"),
	    DATA_DELETED_ERROR(103, "删除数据失败"),
	    DATA_INPUT_ERROR(104, "数据未输入"),
	    PARAMETER_VALIDATION(105, "参数验证失败-{0}"),
	    PARAMETER_ERROR(106, "参数错误"),
	    INVALID_CLIENT_ID(300, "clientID无效"),
	    INVALID_USER_NAME(301, "用户名错误"),
	    INVALID_PASSWORD(302, "密码错误"),
	    INVALID_TOKEN(303, "access_token无效"),
	    NO_AUTHORIZATION(304, "无Authorization传入"),
		INVALID_SERVICE(-99,"无法访问服务，该服务可能由于某种未知原因被关闭。请重启服务！");
	
	private int code;
	private String message;
	
	private ResponseStatus(int code,String message) {
		// TODO Auto-generated constructor stub
		this.code=code;
		this.message=message;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getMessage() {
		return message;
	}
}
