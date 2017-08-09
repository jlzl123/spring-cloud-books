package cn.lsh.exception;

/*
 * 业务异常
 */
public class SystemException extends Exception{

	public SystemException() {
		// TODO Auto-generated constructor stub
		super();
	}
	
	public SystemException(String message){
		super(message);
	}
	
	public SystemException(String message,Throwable cause){
		super(message, cause);
	}
	
	public SystemException(Throwable cause){
		super(cause);
	}
}
