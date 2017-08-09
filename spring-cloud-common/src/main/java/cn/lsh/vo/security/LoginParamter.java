package cn.lsh.vo.security;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@ApiModel(value="登录参数")
public class LoginParamter {//from model 参数验证
	
	@NotNull(message="不能为null")
	@NotBlank(message="不能为空")
	@ApiModelProperty(value="传入的客户端id，相当于微信的openID",required=true)
	@JsonProperty("client_id")//该属性对应序列化json数据的属性
	private String clientId;
	
	@NotNull(message="不能为null")
	@NotBlank(message="不能为空")
	@ApiModelProperty(value="访问API网关的用户名",required=true)
	@JsonProperty("user_name")
	private String userName;
	
	@NotNull(message="不能为null")
	@NotBlank(message="不能为空")
	@ApiModelProperty(value="访问API网关的密码",required=true)
	@JsonProperty("password")
	private String password;
}
