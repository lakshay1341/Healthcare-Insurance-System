package com.nt.service;

import java.util.List;
import java.util.Map;

import com.nt.bindings.PlanData;


public interface IAdminMgmtService {
	
	public  String   registerPlan(PlanData plan);  //save operation
	public Map<Integer ,String>  getPlanCategories();  // for select operation
	public  List<PlanData>   showAllPlans();  //for select operation
	public  PlanData     showPlanById(Integer  planId);  //  for edit operation form launch (To show the existing record for editing)
	public   String     updatePlan(PlanData plan);  //for edit  operation  form submission
	public   String    deletePlan(Integer planId);  //for deletion operation (hard deletion)
	public   String    changePlanStatus(Integer planId , String  status);  //for   soft deletion activity
	
	

}
