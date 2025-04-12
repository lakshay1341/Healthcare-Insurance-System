package in.lakshay.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.lakshay.exceptions.ApplicationException;
import in.lakshay.exceptions.ResourceNotFoundException;

import in.lakshay.bindings.PlanData;
import in.lakshay.config.AppConfigProperties;
import in.lakshay.constants.PlanConstants;
import in.lakshay.entity.PlanCategory;
import in.lakshay.entity.PlanEntity;
import in.lakshay.repository.IPlanCategoryRepository;
import in.lakshay.repository.IPlanRepository;

@Service
public class AdminMgmtServiceImpl implements IAdminMgmtService {
	@Autowired
	private  IPlanRepository   planRepo;
	@Autowired
	private  IPlanCategoryRepository   planCategoryRepo;

	private  Map<String,String> messages;


	@Autowired
	public   AdminMgmtServiceImpl(AppConfigProperties props) {
	 		messages=props.getMessages();
	}

	@Override
	public String registerPlan(PlanData plan) {
		//conver  PlaData  Binding obj  to  TravelPlan
		PlanEntity  entity=new PlanEntity();
		 BeanUtils.copyProperties(plan, entity);
		 //save the object
		PlanEntity savedEntity=planRepo.save(entity);
			return  savedEntity.getPlanId()!=null?messages.get(PlanConstants.SAVE_SUCCESS)+savedEntity.getPlanId() : messages.get(PlanConstants.SAVE_FAILURE);


	}

	@Override
	public Map<Integer, String> getPlanCategories() {
		//get  All TravelPlan Categories
		List<PlanCategory> list=planCategoryRepo.findAll();
		Map<Integer,String>  categoriesMap=new  HashMap<Integer, String>();
		list.forEach(category->{
			categoriesMap.put(category.getCategoryId(), category.getCategoryName());
		});
		return categoriesMap;
	}



	@Override
	public List<PlanData> showAllPlans() {
		List<PlanEntity>  listEntities=planRepo.findAll();
		List<PlanData>   listPlanData=new ArrayList<PlanData>();
		listEntities.forEach(entity->{
			PlanData  data=new PlanData();
			BeanUtils.copyProperties(entity, data);
			listPlanData.add(data);
		});
		return   listPlanData;
	}

	@Override
	public PlanData showPlanById(Integer planId) {
		  PlanEntity entity=planRepo.findById(planId).orElseThrow(()->new in.lakshay.exceptions.ResourceNotFoundException("Plan", "planId", planId));
	      //convert  Entity obj  to Binding obj
		  PlanData  data=new PlanData();
		  BeanUtils.copyProperties(entity, data);
		  return  data;
	}

	@Override
	public String updatePlan(PlanData plan) {
		Optional<PlanEntity> optEntity=planRepo.findById(plan.getPlanId());
		if(optEntity.isPresent()) {
			//update the object
			PlanEntity  entity=optEntity.get();
			BeanUtils.copyProperties(plan, entity);
			planRepo.save(entity);
			return plan.getPlanId()+messages.get(PlanConstants.UPDATE_SUCCESS);
		}
		else {
			throw new ResourceNotFoundException("Plan", "planId", plan.getPlanId());
		}
	}//method

	@Override
	public String deletePlan(Integer planId) {
		Optional<PlanEntity> optEntity=planRepo.findById(planId);
		if(optEntity.isPresent()) {
			//delete the object
			planRepo.deleteById(planId);
			return planId+messages.get(PlanConstants.DELETE_SUCCESS);
		}
		else {
			throw new ResourceNotFoundException("Plan", "planId", planId);
		}

	}

	@Override
	public String changePlanStatus(Integer planId, String status) {
		Optional<PlanEntity> optEntity=planRepo.findById(planId);
		if(optEntity.isPresent()) {
			PlanEntity entity=optEntity.get();
			entity.setActiveSw(status);
			planRepo.save(entity);
			return planId+messages.get(PlanConstants.STATUS_CHANGE_SUCCESS);
		}
		else {
			throw new ResourceNotFoundException("Plan", "planId", planId);
		}
	}


}
