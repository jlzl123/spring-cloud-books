package cn.lsh.rabbitmq;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class EndPoint {
	Channel channel;
	String endPointName;
	private Connection connection;
	
	public EndPoint(String endPointName) throws IOException, TimeoutException {
		// TODO Auto-generated constructor stub
	    this.endPointName=endPointName;
	    
	    ConnectionFactory factory=new ConnectionFactory();
	    connection=factory.newConnection();
	    channel=connection.createChannel();
	    channel.queueDeclare(endPointName, false, false, false, null);
	}
}
