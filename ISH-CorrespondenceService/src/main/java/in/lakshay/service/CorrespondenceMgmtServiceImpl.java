package in.lakshay.service;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.logging.Logger;

import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import in.lakshay.binding.COSummary;
import in.lakshay.entity.CitizenAppRegistrationEntity;
import in.lakshay.entity.CoTriggersEntity;
import in.lakshay.entity.DcCaseEntity;
import in.lakshay.entity.EligibilityDetailsEntity;
import in.lakshay.exceptions.ApplicationException;
import in.lakshay.exceptions.ResourceNotFoundException;
import in.lakshay.repository.IApplicationRegistrationRepository;
import in.lakshay.repository.ICOTriggerRepository;
import in.lakshay.repository.IDcCaseRepository;
import in.lakshay.repository.IEligibilityDeterminationRepository;
import in.lakshay.utils.EmailUtils;

@Service
public class CorrespondenceMgmtServiceImpl implements ICorrespondenceMgmtService {
    private static final Logger logger = Logger.getLogger(CorrespondenceMgmtServiceImpl.class.getName());
    @Autowired
    private ICOTriggerRepository triggerRepo;
    @Autowired
    private IEligibilityDeterminationRepository elgiRepo;
    @Autowired
    private IDcCaseRepository caseRepo;

    @Autowired
    private IApplicationRegistrationRepository citizenRepo;
    @Autowired
    private EmailUtils mailUtil;

    @Value("${correspondence.pdf.output-dir:./}")
    private String pdfOutputDir;

    int pendingTriggers = 0;
    int successTrigger = 0;


    @Override
    public COSummary processPendingTriggers() {
        CitizenAppRegistrationEntity citizenEntity = null;
        EligibilityDetailsEntity eligiEntity = null;

        logger.info("Starting to process pending triggers");
        logger.info("PDF output directory configured as: " + pdfOutputDir);

        // get all  pending  triggers
        List<CoTriggersEntity> triggersList = triggerRepo.findByTriggerStatus("pending");
        logger.info("Found " + triggersList.size() + " pending triggers to process");

        //prepare  COSummary Report
        COSummary summary = new COSummary();
        summary.setTotalTriggers(triggersList.size());

        //  Process the triggers in multithreaded env... using Executor Framework
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ExecutorCompletionService<Object> pool = new ExecutorCompletionService<Object>(executorService);


        //  process  each  pending trigger
        for (CoTriggersEntity triggerEntity : triggersList) {
            pool.submit(() -> {
                try {
                    logger.info("Processing trigger for case number: " + triggerEntity.getCaseNo());
                    processTrigger(summary, triggerEntity);
                    successTrigger++;
                    logger.info("Successfully processed trigger for case number: " + triggerEntity.getCaseNo());
                } catch (ResourceNotFoundException e) {
                    // Log the error and continue processing other triggers
                    logger.warning("Resource not found: " + e.getMessage());
                    pendingTriggers++;
                } catch (Exception e) {
                    // Log the error and continue processing other triggers
                    logger.severe("Error processing trigger: " + e.getMessage());
                    e.printStackTrace();
                    pendingTriggers++;
                }
                return null;
            });

        }//for

        summary.setPendingTriggers(pendingTriggers);
        summary.setSuccessTriggers(successTrigger);
        return summary;
    }

    //helper method
    private CitizenAppRegistrationEntity processTrigger(COSummary summary, CoTriggersEntity triggerEntity) throws Exception {
        CitizenAppRegistrationEntity citizenEntity = null;

        //  get  Eligibility details  based  on caseno
        logger.info("Looking up eligibility details for case number: " + triggerEntity.getCaseNo());
        EligibilityDetailsEntity eligiEntity = elgiRepo.findByCaseNo(triggerEntity.getCaseNo());
        if (eligiEntity == null) {
            logger.warning("No eligibility details found for case number: " + triggerEntity.getCaseNo());
            throw new ResourceNotFoundException("Eligibility details", "caseNo", triggerEntity.getCaseNo());
        }
        logger.info("Found eligibility details for case number: " + triggerEntity.getCaseNo());

        //get   appId based  on CaseNo
        Optional<DcCaseEntity> optCaseEntity = caseRepo.findById(triggerEntity.getCaseNo());
        if (!optCaseEntity.isPresent()) {
            throw new ResourceNotFoundException("Case", "caseNo", triggerEntity.getCaseNo());
        }

        DcCaseEntity caseEntity = optCaseEntity.get();
        Integer appId = caseEntity.getAppId();

        // get the Citizen details  based on  the appId
        Optional<CitizenAppRegistrationEntity> optCitizenEntity = citizenRepo.findById(appId);
        if (!optCitizenEntity.isPresent()) {
            throw new ResourceNotFoundException("Citizen", "appId", appId);
        }

        citizenEntity = optCitizenEntity.get();
        //  generate pdf doc  having   eligibility details  and  send that pdf doc  as email

        generatePdfAndSendMail(eligiEntity, citizenEntity);

        return citizenEntity;
    }

    //helper  method to generate the pdf doc
    private void generatePdfAndSendMail(EligibilityDetailsEntity eligiEntity, CitizenAppRegistrationEntity citizenEntity) throws Exception {

        // Create output directory if it doesn't exist
        File outputDir = new File(pdfOutputDir);
        if (!outputDir.exists()) {
            logger.info("Creating PDF output directory: " + outputDir.getAbsolutePath());
            boolean created = outputDir.mkdirs();
            if (!created) {
                logger.warning("Failed to create directory: " + outputDir.getAbsolutePath());
            }
        }
        logger.info("Saving PDF to: " + pdfOutputDir);

        // create Document  obj (openPdf)
        Document document = new Document(PageSize.A4);
        //  create  pdf file  to write the content  to it
        String filePath = pdfOutputDir + File.separator + eligiEntity.getCaseNo() + ".pdf";
        File file = new File(filePath);
        FileOutputStream fos = new FileOutputStream(file);
        //get  PdfWriter  to  write  to the document and response obj
        PdfWriter.getInstance(document, fos);
        //open the document
        document.open();
        //Define  Font  for the Paragraph
        Font font = FontFactory.getFont(FontFactory.TIMES_BOLD);
        font.setSize(30);
        font.setColor(Color.CYAN);

        //create the paragraph having  content  and above font  style
        Paragraph para = new Paragraph("Plan Approval/ Denial Communication", font);
        para.setAlignment(Paragraph.ALIGN_CENTER);
        //add paragraph  to  document
        document.add(para);


        //  Display search results as the pdf table
        PdfPTable table = new PdfPTable(10);
        table.setWidthPercentage(70);
        table.setWidths(new float[]{3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f, 3.0f});
        table.setSpacingBefore(2.0f);

        //prepare  heading  row cells  in the pdf table
        PdfPCell cell = new PdfPCell();
        cell.setBackgroundColor(Color.gray);
        cell.setPadding(5);
        Font cellFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        cellFont.setColor(Color.BLACK);

        cell.setPhrase(new Phrase("TraceID", cellFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("CaseNo", cellFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("HolderName", cellFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("HolderSSN", cellFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("PlanName", cellFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("PlanStatus", cellFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("PlanStartDate", cellFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("PlanEndDate", cellFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("BenefitAmt", cellFont));
        table.addCell(cell);
        cell.setPhrase(new Phrase("DenialReason", cellFont));
        table.addCell(cell);

        // add   data cells  to   pdftable
        table.addCell(String.valueOf(eligiEntity.getEdTraceId()));
        table.addCell(String.valueOf(eligiEntity.getCaseNo()));
        table.addCell(eligiEntity.getHolderName());
        table.addCell(String.valueOf(eligiEntity.getHolderSSN()));
        table.addCell(eligiEntity.getPlanName());
        table.addCell(eligiEntity.getPlanStatus());
        table.addCell(String.valueOf(eligiEntity.getPlanStartDate()));
        table.addCell(String.valueOf(eligiEntity.getPlanEndDate()));
        table.addCell(String.valueOf(eligiEntity.getBenefitAmt()));
        table.addCell(String.valueOf(eligiEntity.getDenialReason()));

        // add table  to  document
        document.add(table);
        //close the document
        document.close();
        // send the generated pdf doc as the email  message
        try {
            String subject = "Plan approval/denial notification";
            String body = "Hello " + citizenEntity.getFullName() + ",<br><br>This email contains complete details about your plan approval or denial.<br><br>Please see the attached PDF for details.";
            logger.info("Attempting to send email to: " + citizenEntity.getEmail());
            mailUtil.sendMail(citizenEntity.getEmail(), subject, body, file);
            logger.info("Email sent successfully");
        } catch (Exception e) {
            // Log the error but continue processing
            logger.severe("Failed to send email: " + e.getMessage());
            logger.info("Continuing with trigger update despite email failure");
        }

        //  update   Co_Trigger  table
        updateCoTrigger(eligiEntity.getCaseNo(), file);
    }

    private void updateCoTrigger(Long caseNo, File file) throws Exception {
        // check Trigger avaaiblity  based on the   caseNo
        CoTriggersEntity triggerEntity = triggerRepo.findByCaseNo(caseNo);
        // get  byte[]  representing  pdf doc  contentn
        byte[] pdfContent = new byte[(int) file.length()];
        FileInputStream fis = new FileInputStream(file);
        fis.read(pdfContent);
        if (triggerEntity != null) {
            triggerEntity.setCoNoticePdf(pdfContent);
            triggerEntity.setTriggerStatus("completed");
            triggerRepo.save(triggerEntity);
        }
        fis.close();
    }

}
