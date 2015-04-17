package com.JMS;

import javax.jms.*;  

import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;  
import org.springframework.jms.core.JmsTemplate;  
import org.springframework.jms.core.MessageCreator;  
import org.springframework.stereotype.Component;  
  
@Component("myMessageSender")  
public class MyMessageSender implements BeanNameAware{  
@Autowired  
private JmsTemplate jmsTemplate;  
public void sendMessage(final int message , final String queueName){  
    jmsTemplate.send(queueName, new MessageCreator(){  
  
        @Override  
        public Message createMessage(Session session) throws JMSException {  
            return session.createTextMessage(""+message);  
        }  
    });  
}
@Override
public void setBeanName(String beanName) {
	System.out.println("Bean Name is : "+ beanName );
	
}  
}  	
