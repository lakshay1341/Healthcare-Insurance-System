package in.lakshay.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import in.lakshay.binding.ElgibilityDetails;
import in.lakshay.entity.ElgibilityDetailsEntity;

@Component
public class EDDetailsProcessor implements ItemProcessor<ElgibilityDetailsEntity, ElgibilityDetails> {

	@Override
	public ElgibilityDetails process(ElgibilityDetailsEntity item) throws Exception {
		if(item.getPlanStatus().equalsIgnoreCase("approved")) {
			   ElgibilityDetails  details=new  ElgibilityDetails();
			   BeanUtils.copyProperties(item, details);
			   return details;
		}
		return null;
	}

}
