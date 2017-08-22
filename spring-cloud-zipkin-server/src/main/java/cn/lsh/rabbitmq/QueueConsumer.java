package cn.lsh.rabbitmq;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;

import lombok.extern.slf4j.Slf4j;

import com.rabbitmq.client.AMQP.BasicProperties;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.Envelope;
import com.rabbitmq.client.ShutdownSignalException;

@Slf4j
public class QueueConsumer extends EndPoint implements Runnable,Consumer{

	public QueueConsumer(String endPointName) throws IOException,
			TimeoutException {
		super(endPointName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void handleCancel(String conusmerTag) throws IOException {
		// TODO Auto-generated method stub
		log.info("Consumer {} cancel",conusmerTag); 
	}

	@Override
	public void handleCancelOk(String conusmerTag) {
		// TODO Auto-generated method stub
		log.info("Consumer {} cancel",conusmerTag); 
	}

	@Override
	public void handleConsumeOk(String consumerTag) {
		// TODO Auto-generated method stub
		log.info("Consumer {} registerd",consumerTag);
	}

	@Override
	public void handleDelivery(String ConsumerTag, Envelope env,
			BasicProperties props, byte[] body) throws IOException {
		// TODO Auto-generated method stub
		Map map=SerializationUtils.deserialize(body);
		log.info("Message Number {} received.",map.get("message number"));
	}

	@Override
	public void handleRecoverOk(String conusmerTag) {
		// TODO Auto-generated method stub
		log.info("Conusmer {} recover.",conusmerTag);
	}

	@Override
	public void handleShutdownSignal(String consumerTag, ShutdownSignalException sig) {
		// TODO Auto-generated method stub
		log.info("Conusmer {},{} shutdown");
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			channel.basicConsume(endPointName, true,this);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			log.error("exception message is: {}", ExceptionUtils.getStackTrace(e));
		}
	}

}
