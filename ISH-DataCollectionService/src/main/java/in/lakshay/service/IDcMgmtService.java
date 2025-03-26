package in.lakshay.service;

import java.util.List;

import in.lakshay.bindings.ChildInputs;
import in.lakshay.bindings.DcSummaryReport;
import in.lakshay.bindings.EducationInputs;
import in.lakshay.bindings.IncomeInputs;
import in.lakshay.bindings.PlanSelectionInputs;

public interface IDcMgmtService {

    public Integer generateCaseNo(Integer appId);

    public List<String> showAllPlanNames();

    public Integer savePlanSelection(PlanSelectionInputs plan);

    public Integer saveIncomeDetails(IncomeInputs income);

    public Integer saveEducationDetails(EducationInputs education);

    public Integer saveChildrenDetails(List<ChildInputs> children);

    public DcSummaryReport showDcSummary(Integer caseNo);
}
