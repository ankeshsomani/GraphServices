package com.mastek.designschool.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mastek.designschool.common.dto.CreateLoanServiceRequest;
import com.mastek.designschool.common.dto.ServiceResponse;
import com.mastek.designschool.common.dto.SuspicionIndexResponse;
import com.mastek.designschool.common.service.GraphService;

@RestController
public class GraphServiceController {
	public static final String APPLICATION_JSON = "application/json";
	
	@Autowired
	GraphService graphService;
	
	
	
	@CrossOrigin
	@RequestMapping(value="/test", method = RequestMethod.POST,consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
	public @ResponseBody ServiceResponse createNode(@RequestBody CreateLoanServiceRequest createLoanServiceRequest) {
		ServiceResponse serviceResponse= new ServiceResponse(true, "hello ankesh,you passed "+createLoanServiceRequest.getFirstName());
		
		return serviceResponse;

	}
	
	@CrossOrigin
	@RequestMapping(value="/createLoan", method = RequestMethod.POST,consumes = APPLICATION_JSON, produces = APPLICATION_JSON)
	public @ResponseBody ServiceResponse createLoanService(@RequestBody CreateLoanServiceRequest createLoanServiceRequest) {
		ServiceResponse serviceResponse= null;
		//GraphDatabaseService graphDB = new GraphDatabaseFactory().newEmbeddedDatabase(new File(GraphServiceConstants.GRAPH_DB_PATH));
		serviceResponse=graphService.createLoan(createLoanServiceRequest);
	
		return serviceResponse;

	}


	@CrossOrigin
	@RequestMapping(value="/runRules", method = RequestMethod.GET, produces = APPLICATION_JSON)
	public @ResponseBody SuspicionIndexResponse getSuspicionIndex() {
		SuspicionIndexResponse response= null;
		response=graphService.getSuspicionIndex();
	
		return response;

	}
	
}
