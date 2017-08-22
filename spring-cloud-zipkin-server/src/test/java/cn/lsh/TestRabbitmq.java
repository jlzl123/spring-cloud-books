package cn.lsh;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

import cn.lsh.rabbitmq.Producer;
import cn.lsh.rabbitmq.QueueConsumer;

public class TestRabbitmq {

	@Test
	public void test() throws IOException, TimeoutException{
		QueueConsumer consuemr=new QueueConsumer("queue");
		Thread consumerThread=new Thread(consuemr);
		consumerThread.start();
		
		Producer producer=new Producer("queue");
		for(int i=0;i<100;i++){
			HashMap<String, Integer> message=new HashMap<String, Integer>();
			message.put("message number", i);
			producer.sendMessage(message);
			System.out.println("Message Number "+ i +" sent.");
		}
	}
}
