package com.JMS;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.jms.JMSException;
import javax.jms.Queue;
import javax.jms.QueueBrowser;
import javax.jms.Session;
import javax.jms.Message;
import javax.jms.TextMessage;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.SessionCallback;
import org.springframework.stereotype.Component;

@Component("myMessageReceiver")
public class MyMessageReceiver {

	@Autowired
	protected JmsTemplate jmsTemplate; 

	public List<Integer> getMessagesInQueue(final Queue queue) {
		final List<Integer> list = new ArrayList<Integer>();
		jmsTemplate.execute(new SessionCallback<QueueBrowser>() {
			public QueueBrowser doInJms(Session session) throws JMSException {
				QueueBrowser browser =  session.createBrowser(queue);
				Enumeration messages = browser.getEnumeration();

				System.out.println("" + messages.hasMoreElements());
				while(messages.hasMoreElements()) {
					TextMessage msg =  (TextMessage) messages.nextElement();
					System.out.println(""+msg.getText());
					list.add(Integer.valueOf(msg.getText()));
				}
				return null;
			}
		}, true);


		return list;
	}

	public Integer receiveMessage(){
		int number = 0 ;
		System.out.println("MESSAGE SENT TO myMessageQueue");
		jmsTemplate.setReceiveTimeout(10);
		Message receivedMessage=jmsTemplate.receive("myqueue");
		if(receivedMessage!=null){
			TextMessage msg = (TextMessage)receivedMessage;
			try {
				System.out.println("Message Received :"+msg.getText());
				number = Integer.parseInt(msg.getText());
			} catch (JMSException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return number;
		}
		else {
			return null;
		}
	}

}
