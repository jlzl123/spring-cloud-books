package cn.lsh.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class RedisConfig {
	
	@Bean
	public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory){
		RedisTemplate<Object, Object> redisTemplate=new RedisTemplate<Object, Object>();
		redisTemplate.setConnectionFactory(connectionFactory);
		
		//使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
		Jackson2JsonRedisSerializer<Object> serializer=new Jackson2JsonRedisSerializer<Object>(Object.class);
		
		ObjectMapper mappper=new ObjectMapper();
		mappper.setVisibility(PropertyAccessor.ALL,JsonAutoDetect.Visibility.ANY);
		mappper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
		serializer.setObjectMapper(mappper);
		
		redisTemplate.setValueSerializer(serializer);
		//使用StringRedisSerializer来序列化和反序列化redis的key值
		redisTemplate.setKeySerializer(new StringRedisSerializer());
		redisTemplate.afterPropertiesSet();
		return redisTemplate;
	}
}
