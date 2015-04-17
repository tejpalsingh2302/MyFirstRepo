package com.rest;

import java.util.logging.Logger;

import javax.jms.Queue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;

import com.JMS.MyMessageReceiver;
import com.JMS.MyMessageSender;

@Path("/rest/")
public class RestService {
	Logger log = Logger.getLogger(RestService.class.getName());
	@Autowired
	public MyMessageSender myMessageSender ;
	
	@Autowired
	public MyMessageReceiver myMessageReceiver ;
	
	@Autowired
	public Queue defaultDestination ;
	
	
	@GET
	@Path("/service/list")
	@Produces("application/json")
	public Response list(){
		log.info("Getting list of queue");
		return Response.status(Status.OK).entity(myMessageReceiver.getMessagesInQueue(defaultDestination)).type(MediaType.APPLICATION_JSON).build();
	}
	@GET
	@Path("/service/push/{firstNumber}/{secondNumber}")
	public String push(@PathParam(value = "firstNumber")int firstNumber, @PathParam(value = "secondNumber") int secondNumber){
		
	try {
			log.info("First Number is : " + firstNumber+" second Number is : " + secondNumber);
			myMessageSender.sendMessage(firstNumber,"myqueue");
			myMessageSender.sendMessage(secondNumber,"myqueue");
			log.info("Number Added successfully");
			return "Number Added Successfully";
	}	catch (Exception ex){
		ex.printStackTrace();
		return "Number not added";
	}  
		
	}
}
