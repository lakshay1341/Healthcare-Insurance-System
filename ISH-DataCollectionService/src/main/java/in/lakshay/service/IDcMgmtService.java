package in.lakshay.service;

import java.util.List;

import in.lakshay.bindings.ChildInputs;
import in.lakshay.bindings.DcSummaryReport;
import in.lakshay.bindings.EducationInputs;
import in.lakshay.bindings.IncomeInputs;
import in.lakshay.bindings.PlanSelectionInputs;

public interface IDcMgmtService {

    public Long generateCaseNo(Integer appId);

    public List<String> showAllPlanNames();

    public Long savePlanSelection(PlanSelectionInputs plan);

    public Long saveIncomeDetails(IncomeInputs income);

    public Long saveEducationDetails(EducationInputs education);

    public Long saveChildrenDetails(List<ChildInputs> children);

    public DcSummaryReport showDcSummary(Long caseNo);
}
