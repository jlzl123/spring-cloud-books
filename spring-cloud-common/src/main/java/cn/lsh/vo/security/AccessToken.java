package cn.lsh.vo.security;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@ApiModel(value="访问令牌")
public class AccessToken {

	@ApiModelProperty(value="访问令牌值",required=true)
	@JsonProperty("access_token")
	private String token;
	
	@ApiModelProperty(value="访问令牌类型",required=true)
	@JsonProperty("token_type")
	private String tokenType;
	
	@ApiModelProperty(value="令牌有效时间，单位为秒",required=true)
	@JsonProperty("expires_in")
	private long expiresIn;
}
