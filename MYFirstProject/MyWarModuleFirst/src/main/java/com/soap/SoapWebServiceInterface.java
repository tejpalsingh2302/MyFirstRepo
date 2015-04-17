package com.soap;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface SoapWebServiceInterface {
	
	@WebMethod
	public Integer gcd();
	
	@WebMethod
	public List<Integer> gcdList();
	
	@WebMethod
	public int gcdSum();
}
