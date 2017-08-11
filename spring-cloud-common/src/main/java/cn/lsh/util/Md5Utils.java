package cn.lsh.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Md5Utils {
	private static final Logger LOGGER=LoggerFactory.getLogger(Md5Utils.class);
	
	private Md5Utils(){
		
	}

	public static String getMd5(String instr){
		MessageDigest md5;
		try {
			md5=MessageDigest.getInstance("Md5");
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			LOGGER.error("exception message is: {}", ExceptionUtils.getStackTrace(e));
			return "";
		}
		
		char[] charArray=instr.toCharArray();
		byte[] byteArray=new byte[charArray.length];
		for(int i=0;i<charArray.length;i++){
			byteArray[i]=(byte) charArray[i];
		}
		byte[] md5Bytes=md5.digest(byteArray);
		StringBuilder hexValue=new StringBuilder();
		
		for(byte md5Byte:md5Bytes){
			int val=((int)md5Byte) & 0xff;
			if(val<16){
				hexValue.append(0);
			}
			hexValue.append(Integer.valueOf(val));
		}
		return hexValue.toString();
	}

}
