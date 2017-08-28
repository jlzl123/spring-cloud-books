package cn.lsh;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.turbine.EnableTurbine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import de.codecentric.boot.admin.config.EnableAdminServer;
import de.codecentric.boot.admin.notify.LoggingNotifier;
import de.codecentric.boot.admin.notify.Notifier;
import de.codecentric.boot.admin.notify.RemindingNotifier;
import de.codecentric.boot.admin.notify.filter.FilteringNotifier;

@SpringBootApplication
@EnableDiscoveryClient
@EnableHystrixDashboard
@EnableTurbine
@EnableAdminServer
@EnableAutoConfiguration//开启对@ConfigurationProperties注解配置Bean的支持
@Configuration
public class AdminServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminServerApplication.class, args);
	}
	
	@Profile("secure")
	@Configuration
	public static class SecurityConfig extends WebSecurityConfigurerAdapter{
		
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// TODO Auto-generated method stub
			//定义/login.html跳转到登录页面，并且登录提交一个post请求/login
			http.formLogin().loginPage("/login.html").loginProcessingUrl("/login").permitAll();
			
			http.logout().logoutUrl("/logout");
			
			http.csrf().disable();
			
			//定义不需要权限认证的url
			http.authorizeRequests()
			    .antMatchers("/login.html", "/**/*.css","/img/**","/third-party/**")
		        .permitAll();
			//定义任何其他请求都要经过权限认证
			http.authorizeRequests().antMatchers("/**").authenticated();
			
			http.httpBasic();
		}
	}
	
	
	@Configuration
	public static class NotifierConfig{
		
		@Bean
		@Primary
		public RemindingNotifier remindingNotifier(){
			RemindingNotifier notifier=new RemindingNotifier(filteringNotifier(loggingNotifier()));
			//设定时间，5分钟提醒一次
			notifier.setReminderPeriod(TimeUnit.SECONDS.toMillis(10));
			return notifier;
		}
		
		@Scheduled(fixedRate=1_000L)
		public void remind(){
			remindingNotifier().sendReminders();
		}
		
		@Bean
		public FilteringNotifier filteringNotifier(Notifier delegate){
			return new FilteringNotifier(delegate);
		}
		
		@Bean
		public LoggingNotifier loggingNotifier(){
			return new LoggingNotifier();
		}
	}
}
