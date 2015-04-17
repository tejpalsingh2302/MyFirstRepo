package com.soap;

import java.util.List;
import java.util.logging.Logger;

import javax.jms.Queue;
import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;

import com.JMS.MyMessageReceiver;
import com.JMS.MyMessageSender;


@WebService(endpointInterface = "com.soap.SoapWebServiceInterface", serviceName="SoapWebServiceInterface")
public class SoapWebServiceInterfaceImpl implements SoapWebServiceInterface{

	Logger log = Logger.getLogger(SoapWebServiceInterfaceImpl.class.getName());
	
	@Autowired
	public MyMessageReceiver myMessageReceiver ;
	
	@Autowired
	public MyMessageSender myMessageSender ;
	

	@Override
	public Integer gcd() {
		Integer firstNumber = myMessageReceiver.receiveMessage();
		Integer SecondNumber = myMessageReceiver.receiveMessage();
		if(firstNumber!=null && SecondNumber!=null){
			log.info("Frist Number : " + firstNumber + " Second Number : " + SecondNumber);
			int gcd = findGCD(firstNumber, SecondNumber);
			log.info("GCD is : "+ gcd);
			myMessageSender.sendMessage(gcd,"mygcdqueue");
			return gcd;
		}
		else {
			log.info("queue is emply");
			return null;
		}
	}

	@Override
	public List<Integer> gcdList() {
		log.info("Getting gcd list ");
		return myMessageReceiver.getMessagesInQueue(mygcdqueue);
		
	}

	@Override
	public int gcdSum() {
		// TODO Auto-generated method stub
		log.info("Doing sum of gcd");
		List<Integer> list = myMessageReceiver.getMessagesInQueue(mygcdqueue);
		int sum =0;
		for(Integer i : list){
			sum+=i;
		}
		log.info("Sum is : " + sum );
		return sum;
	}
	 private int findGCD(int number1, int number2) {
		 if(number2 == 0){
	            return number1;
	        }
	        return findGCD(number2, number1%number2);
	    }
	 @Autowired
	 public Queue mygcdqueue;
}
