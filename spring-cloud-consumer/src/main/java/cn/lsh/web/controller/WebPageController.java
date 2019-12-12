package cn.lsh.web.controller;

import cn.lsh.exception.ResponseStatus;
import cn.lsh.service.BookConsumerService;
import cn.lsh.util.Constants;
import cn.lsh.vo.response.BaseResponse;
import cn.lsh.vo.security.Audience;
import cn.lsh.vo.security.LoginParamter;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("/page")
public class WebPageController {

	@Autowired
	private BookConsumerService bookConsumerService;

	@Autowired
	private Audience audience;

	@RequestMapping("/index")
	public String index(ModelMap modelMap) {
		return "index";
	}

	@PostMapping("/login")
	public void login(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		String userName = req.getParameter("userName");
		String passWord = req.getParameter("passWord");
		LoginParamter loginParamter = new LoginParamter(audience.getClientId(), userName, passWord);
		BaseResponse response = bookConsumerService.getToken(loginParamter);
		if (response.getCode() == ResponseStatus.OK.getCode()) {
			Map<String, String> data = (Map<String, String>) response.getData();
			String accessToken = data.get("access_token");
			// String expieIn = data.get("expires_in");
			String tokenType = data.get("token_type");
			// resp.addHeader(Constants.AUTHORIZATION, Constants.BEARER + "_" + accessToken);
			// resp.addHeader("Set-Cookie", Constants.AUTHORIZATION  + "=" + Constants.BEARER + "_" + accessToken);
			//将token设置在浏览器cookie中
			Cookie cookie = new Cookie(Constants.AUTHORIZATION, Constants.BEARER + "_" + accessToken);
			//设置cookie作用范围，默认为当前目录及其子目录，这里设置为根目录，对整个web应用都起作用
			cookie.setPath("/");
			resp.addCookie(cookie);

			// req.getRequestDispatcher("/swagger-ui.html").forward(req, resp);
			resp.sendRedirect("/swagger-ui.html");
		} else if (response.getCode() == ResponseStatus.INVALID_USER_NAME.getCode() ||
				response.getCode() == ResponseStatus.INVALID_PASSWORD.getCode()) {
			req.setAttribute("errorMsg", response.getMessage());
			req.getRequestDispatcher("index").forward(req, resp);
		} else {
			resp.setContentType("text/html;charset=UTF-8");
			resp.getWriter().println(JSON.toJSONString(response));
		}
	}

}
