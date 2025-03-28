package com.nt.ms;

import java.util.List;
import java.util.Map;

import org.codehaus.stax2.validation.XMLValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nt.advice.ExceptionInfo;
import com.nt.bindings.PlanData;
import com.nt.service.IAdminMgmtService;

@RestController
@RequestMapping("/plan-api")
public class AdminOperationsController {
	@Autowired
	private   IAdminMgmtService   planService;
	
	@GetMapping("/categories")
	public   ResponseEntity<Map<Integer,String>>   showPlanCategories(){
		//invoke  service  class methods
			Map<Integer,String> mapCategories=planService.getPlanCategories();
			return  new ResponseEntity<Map<Integer,String>>(mapCategories, HttpStatus.OK);
	}
	
	@PostMapping("/register")
	public   ResponseEntity<String>   savePlan(@RequestBody PlanData plan){
		//use service
			String  msg=planService.registerPlan(plan);
			return  new ResponseEntity<String>(msg,HttpStatus.CREATED);
		}
	
	@GetMapping("/all")
	public  ResponseEntity<List<PlanData>>   getAllPlans(){
		//use  service
			List<PlanData> list=planService.showAllPlans();
			return  new ResponseEntity<List<PlanData>>(list,HttpStatus.OK);
	}

	
	@GetMapping("/find/{planId}")
	public    ResponseEntity<PlanData>  getTravelPlanById(@PathVariable Integer planId){
		//use  service
			 PlanData  plan=planService.showPlanById(planId);
			 return  new ResponseEntity<PlanData>(plan,HttpStatus.OK);
	}
	
	@PutMapping("/update")
	public    ResponseEntity<String>    updatePlan(@RequestBody PlanData plan){
		//use service
			String msg=planService.updatePlan(plan);
			return  new ResponseEntity<String>(msg,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/delete/{planId}")
	public   ResponseEntity<String>   removePlanByPlanId(@PathVariable Integer planId){
		 //use service
			String msg=planService.deletePlan(planId);
			return  new ResponseEntity<String>(msg,HttpStatus.OK);
	}
	
	@PutMapping("/status-change/{planId}/{status}")
	public   ResponseEntity<String>   changeStatusByPlanId(@PathVariable Integer planId,
			                                                                                                  @PathVariable String  status){
		 //use service
			String msg=planService.changePlanStatus(planId, status);
			return  new ResponseEntity<String>(msg,HttpStatus.OK);
	}
	
/*	@ExceptionHandler(IllegalArgumentException.class)
	public  ResponseEntity<ExceptionInfo>   handleIAE(IllegalArgumentException iae){
	    ExceptionInfo  info=new ExceptionInfo();
	    info.setMessage(iae.getMessage());
	    info.setCode(3000);
	    return  new ResponseEntity<ExceptionInfo>(info,HttpStatus.INTERNAL_SERVER_ERROR);
	}*/
	
}//class
