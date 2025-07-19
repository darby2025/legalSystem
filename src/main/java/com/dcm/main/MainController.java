package com.dcm.main;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.dcm.LegalAppApplication;
import com.dcm.mail.EmailServiceImpl;
import com.dcm.modal.Acts;
import com.dcm.modal.Case;
import com.dcm.modal.Document;
import com.dcm.modal.Lawyer;
import com.dcm.modal.Logs;
import com.dcm.modal.Payment;
import com.dcm.modal.Reminder;
import com.dcm.modal.Test;
import com.dcm.service.ActsService;
import com.dcm.service.CaseService;
import com.dcm.service.CasesTriggerService;
import com.dcm.service.DocumentService;
import com.dcm.service.DocumentDraftService;
import com.dcm.service.LawyerService;
import com.dcm.service.LogsService;
import com.dcm.service.PayService;
import com.dcm.service.PaymentService;
import com.dcm.service.ReminderService;
import com.dcm.service.TestService;
import com.dcm.service.UpdatecaseService;
import com.dcm.service.UpdatesService;
import com.dcm.service.UserService;

@Controller
@SessionAttributes("name")
public class MainController {

	private static final Logger LOGGER = LoggerFactory.getLogger(LegalAppApplication.class);

	@Autowired
	private ActsService actsService;

	@Autowired
	private LawyerService lawyerService;

	@Autowired
	private ReminderService reminderService;

	@Autowired
	private DocumentService documentService;

	@Autowired
	private CaseService caseService;

	@Autowired
	private PaymentService paymentService;

	@Autowired
	private PayService payservice;

	@Autowired
	private UserService userservice;

	@Autowired
	private UpdatecaseService updatecaseService;

	@Autowired
	private LogsService logsService;

	@Autowired
	private CasesTriggerService casesTriggerService;

	@Autowired
	private UpdatesService updatesService;

	@Autowired
	private TestService testService;

        @Autowired
        private EmailServiceImpl emailService;

        @Autowired
        private DocumentDraftService draftService;

	/* Spring Security */
	@RequestMapping("/")
	public String login(RedirectAttributes redirectAttributes, Principal principal) {
		redirectAttributes.addFlashAttribute("msg", "Authentication Successfull ");
		String name = principal.getName();
		LOGGER.info("Logged in by: " + name);
		return "redirect:/home";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public ModelAndView login(@RequestParam(value = "error", required = false) String error,
			@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("error", "Invalid username and password!");
			LOGGER.info("Login error!");
		}

		if (logout != null) {
			model.addObject("msg", "Logged out successfully.");
			LOGGER.info("Logged out!");
		}
		model.setViewName("login");
		return model;

	}

	@RequestMapping("/accessDenied")
	public String accessDenied() {

		return "accessDenied";

	}

	@ResponseBody
	@RequestMapping("/count")
	public Long Count(HttpServletRequest request) {
		Long nol = lawyerService.noOfLawyer();
		System.out.println(nol);
		return nol;

	}

	@ResponseBody
	@RequestMapping("/countacts")
	public Long CountActs() {
		Long noa = actsService.noOfActs();
		System.out.println(noa);
		return noa;

	}

	@ResponseBody
	@RequestMapping("/countusers")
	public Long CountUsers() {
		Long nou = userservice.noOfUsers();
		System.out.println(nou);
		return nou;

	}

	@ResponseBody
	@RequestMapping("/countcases")
	public Long CountCases() {
		Long noc = caseService.noOfCases();
		System.out.println(noc);
		return noc;

	}

	/** Test Controller */

	@RequestMapping(value = "/test", method = RequestMethod.GET)
	public String test() {

		return "test";
	}

	@RequestMapping(value = "/mail", method = RequestMethod.GET)
	public String mail() {
		try {
			String[] to = userservice.getEmail();
			emailService.hearingReminder(to);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "test";
	}


	@RequestMapping(value = "/id", method = RequestMethod.GET)
	public @ResponseBody List<com.dcm.modal.Case> id() {
		return caseService.AllCases();
	}
	@RequestMapping(value = "/state", method = RequestMethod.GET)
	public @ResponseBody List<com.dcm.modal.Case> State() {
		return caseService.AllCases();
	}
	@RequestMapping(value = "/court", method = RequestMethod.GET)
	public @ResponseBody List<com.dcm.modal.Case> Court() {
		return caseService.AllCases();
	}
	
	
	@RequestMapping(value = "/field1", method = RequestMethod.GET)
	public @ResponseBody List<Test> field1() {
		return testService.showAllTest();
	}

	@RequestMapping(value = "/name", method = RequestMethod.GET)
	public @ResponseBody List<Test> name() {
		return testService.showAllTest();
	}

	@RequestMapping("/home")
	public String home(HttpServletRequest request, Principal principal, Model model) {
		String name = principal.getName();
		model.addAttribute("name", name);
		request.setAttribute("reminder", reminderService.AllReminder());
		request.setAttribute("doh", updatecaseService.TodayDOH());

		List<Case> chart = caseService.CategoryCount();
		
		// for Cateogry -wise chart
		int a = 0, b = 0, c = 0, d = 0;
		Map<String, Integer> m = new HashMap<>();
		for (int i = 0; i < chart.size(); i++) {
			String key = chart.get(i).getCateogry();
			switch (key) {
			case "A":
				a++;
				break;
			case "B":
				b++;
				break;
			case "C":
				c++;
				break;
			case "D":
				d++;
				break;
			default:
				break;
			}

		}
		m.put("A", a);
		m.put("B", b);
		m.put("C", c);
		m.put("D", d);
		request.setAttribute("m", m);

                // for court -wise chart
                Map<String, Integer> m2 = new HashMap<>();

                for (Case c : chart) {
                        String court = c.getCourt();
                        m2.merge(court, 1, Integer::sum);
                }
                request.setAttribute("m2", m2);
		
		System.out.println("Map 2 value : " + m2);
		return "home";

	}
	/* Users controller */

	@RequestMapping("/user")
	public String Users(HttpServletRequest request) {
		request.setAttribute("users", userservice.showAllUsers());
		request.setAttribute("mode", "All_User");
		return "user";
	}

	@RequestMapping("/viewuser")
	public String ViewUsers(HttpServletRequest request, @RequestParam String name) {
		request.setAttribute("user", userservice.findUser(name));
		
		request.setAttribute("mode", "View_User");
		return "user";
	}

	// @PreAuthorize("hasAnyRole('SYSTEM') or hasRole('ADMIN')")
	@PreAuthorize("hasAnyRole('SYSTEM')")
	@RequestMapping("/newuser")
	public String NewUsers(HttpServletRequest request) {
		request.setAttribute("mode", "New_User");
		return "user";
	}

	@RequestMapping("/save-user")
	public String saveUsers(@ModelAttribute com.dcm.modal.Users user, BindingResult bindingResult,
			Principal principal) {
		userservice.saveUser(user);
		String name = principal.getName();
		LOGGER.info("Event: New User Added by " + name);
		return "redirect:/user";
	}

	/* Cases controller */
	@RequestMapping("/case")
	public String Case(HttpServletRequest request) {
		request.setAttribute("cases", caseService.AllCases());
		request.setAttribute("mode", "All_Cases");
		return "case";

	}

	@RequestMapping("/case-submit")
	public String SaveCase(@ModelAttribute com.dcm.modal.Case c, @ModelAttribute com.dcm.modal.UpdateCase updatecase,
			@ModelAttribute Payment payment, @RequestParam String advocate, BindingResult bindingResult,
			Principal principal) {
		c.setUpdatecase(updatecase);
		c.setPayment(payment);
		System.out.println(advocate);
		Lawyer lawyer1 = lawyerService.findLawyer(advocate);
		c.setLawyer(lawyer1);
		caseService.SaveCase(c);
		String name = principal.getName();
		LOGGER.info("Event: New Case Added by " + name);
		return "redirect:/case";

	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping("/newcase")
	public String NewCase(HttpServletRequest request) {
		request.setAttribute("title", caseService.CaseTitle());
		request.setAttribute("l", lawyerService.AllLawyerName());
		request.setAttribute("section", actsService.AllActsName());
		request.setAttribute("mode", "Add_Cases");
		return "case";

	}

	@RequestMapping("/view-cases")
	public String ViewCase(HttpServletRequest request, @RequestParam String caseno) {
		request.setAttribute("c", caseService.getCaseDetail(caseno));
		request.setAttribute("document", documentService.showByCaseno(caseno));
		request.setAttribute("reminder", reminderService.showByCaseno(caseno));
		request.setAttribute("updatecase", updatecaseService.getBycaseno(caseno));
		int paymentid = caseService.getCaseDetail(caseno).getPayment().getPaymentid();
		request.setAttribute("pay", payservice.getPay(paymentid));
		System.out.println("Update Id: " + caseService.getCaseDetail(caseno).getUpdatecase().getUpdateid());
		request.setAttribute("casesTrigger",
				casesTriggerService.getCasesTrigger(caseService.getCaseDetail(caseno).getUpdatecase().getUpdateid()));
		request.setAttribute("title", caseService.CaseTitle());
		request.setAttribute("mode", "View_Cases");
		return "case";

	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@ResponseBody
	@RequestMapping(path = "/case-update", method = RequestMethod.POST)
	public void UpdateCase(@ModelAttribute com.dcm.modal.UpdateCase updatecase, BindingResult bindingResult,
			HttpServletRequest request) {

		updatecaseService.saveMyUpdateCaseDetail(updatecase);
		LOGGER.info("Event: Case Status Updated");

	}

	@ResponseBody
	@RequestMapping("/lawyer-name")
	public String LawyerName(HttpServletRequest request, @RequestParam String name) {
		request.setAttribute("lawyer", lawyerService.findLawyer(name));
		return "lawyer";
	}

	/* Lawyer controller */

	@RequestMapping("/lawyer")
	public String lawyer(HttpServletRequest request) {
		request.setAttribute("lawyer", lawyerService.showAllLawyer());
		request.setAttribute("mode", "All_Lawyer");
		return "lawyer";
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping("/newlawyer")
	public String NewLawyer(HttpServletRequest request) {
		request.setAttribute("mode", "Add_Lawyer");
		return "lawyer";

	}

	@PostMapping("/save-lawyer")
	public String saveLawyer(@ModelAttribute Lawyer lawyer, BindingResult bindingResult) {
		lawyerService.saveMyLawyer(lawyer);
		LOGGER.info("Event: New Lawyer Added");
		return "redirect:/lawyer";

	}

	@RequestMapping("/edit-lawyer")
	public String editLawyer(@RequestParam int lawyerid, HttpServletRequest request) {
		request.setAttribute("lawyer", lawyerService.editMyLawyer(lawyerid));
		request.setAttribute("mode", "Add_Lawyer");
		return "lawyer";
	}

	@RequestMapping("/viewlawyer")
	public String viewLawyer(@RequestParam int lawyerid, HttpServletRequest request) {
		request.setAttribute("lawyer", lawyerService.editMyLawyer(lawyerid));
		System.out.println("Hi " + lawyerService.editMyLawyer(lawyerid).getCases().toString());
		request.setAttribute("mode", "View_Lawyer");
		return "lawyer";
	}

	/* Acts controller */
	@RequestMapping("/acts")
	public String acts(HttpServletRequest request) {
		request.setAttribute("acts", actsService.showAllActs());
		request.setAttribute("mode", "All_Act");
		return "acts";
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping("/newacts")
	public String NewActs(HttpServletRequest request) {
		request.setAttribute("title", caseService.CaseTitle());
		request.setAttribute("mode", "New_Act");
		return "acts";
	}

	@PostMapping("/saveact")
	public String registerAct(@ModelAttribute Acts act, BindingResult bindingResult, HttpServletRequest request) {
		actsService.saveMyAct(act);
		request.setAttribute("msg", "Acts saved into the system successfully");
		LOGGER.info("New Act added");
		return "redirect:/acts";

	}

	/* Reminder controller */
	@RequestMapping("/reminder")
	public String reminder(HttpServletRequest request) {
		request.setAttribute("reminder", reminderService.showAllReminder());
		request.setAttribute("mode", "All_Reminder");
		return "reminder";
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping("/newreminder")
	public String NewReminder(HttpServletRequest request) {
		request.setAttribute("title", caseService.CaseTitle());
		request.setAttribute("mode", "New_Reminder");
		return "reminder";

	}

	@PostMapping("/save-reminder")
	public String saveReminder(@ModelAttribute Reminder reminder, BindingResult bindingResult) {
		reminderService.saveReminder(reminder);
		LOGGER.info("Event: New Reminder created");
		return "redirect:/reminder";
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@ResponseBody
	@RequestMapping(path = "/set-reminder", method = RequestMethod.POST)
	public void setReminder(HttpServletRequest request, @ModelAttribute Reminder r) {
		reminderService.saveReminder(r);
		LOGGER.info("Event: New Reminder set");
	}

	/* Documents controller */
	@RequestMapping("/document")
	public String document(HttpServletRequest request) {
		request.setAttribute("document", documentService.showAllDocument());
		request.setAttribute("mode", "All_Document");
		return "document";
	}

	@PostMapping("/upload-document") // //new annotation since 4.3
	public String singleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam String type,
			RedirectAttributes redirectAttributes, @ModelAttribute Document document, BindingResult bindingResult) {
		System.out.println("Check 1");
		document.setFile(file.getOriginalFilename());
		System.out.println("Check 1");
		documentService.saveMyDocument(document);
		if (file.isEmpty()) {
			redirectAttributes.addFlashAttribute("message",
					"There was no file provided to upload. Please select a file to upload");
			return "redirect:/document";
		}

		try {
			// Get the file and save it somewhere
			File files = new File(type);
			if (!files.exists()) {
				if (files.mkdir()) {
					System.out.println("Directory is created!");
				} else {
					System.out.println("Failed to create directory!");
				}
			}
			byte[] bytes = file.getBytes();
			Path path = Paths.get(type, file.getOriginalFilename());
			Files.write(path, bytes);
			redirectAttributes.addFlashAttribute("message",
					"You successfully uploaded '" + file.getOriginalFilename() + "'");
			LOGGER.info("Event: Document Uploaded {}" + file.getOriginalFilename());
		} catch (IOException e) {
			e.printStackTrace();
		}
		LOGGER.info("New Document uploaded!");
		return "redirect:/document";
	}

	@ResponseBody
	@RequestMapping(value = "/{type}/{file}", method = RequestMethod.GET)
	public void loadFile(@PathVariable("file") String file, @PathVariable("type") String type,
			HttpServletResponse response) throws IOException {
		System.out.println(file);
		System.out.println(type);
		final Path rootLocation = Paths.get(type);
		Path filename = rootLocation.resolve(file);
		System.out.println(filename);
		File f = new File(filename.toString());

		String mimeType = URLConnection.guessContentTypeFromName(f.getName());
		if (mimeType == null) {
			System.out.println("mimetype is not detectable, will take default");
			mimeType = "application/octet-stream";
		}

		System.out.println("mimetype : " + mimeType);

		response.setContentType(mimeType);

		/*
		 * "Content-Disposition : inline" will show viewable types [like
		 * images/text/pdf/anything viewable by browser] right on browser while
		 * others(zip e.g) will be directly downloaded [may provide save as popup, based
		 * on your browser setting.]
		 */
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + f.getName() + "\""));

		InputStream inputStream = new BufferedInputStream(new FileInputStream(f));

		// Copy bytes from source to destination(outputstream in this example), closes
		// both streams.
		FileCopyUtils.copy(inputStream, response.getOutputStream());

	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping("/newdocument")
	public String NewDocument(HttpServletRequest request) {
		request.setAttribute("title", caseService.CaseTitle());
		request.setAttribute("mode", "New_Document");
		return "document";

	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@ResponseBody
        @RequestMapping(value = "/case-upload", method = RequestMethod.POST)
        public void caseFileUpload(@RequestParam("file") MultipartFile file, @RequestParam String type,
                        @ModelAttribute Document d, BindingResult bindingResult) {
		System.out.println("Check 1");
		d.setFile(file.getOriginalFilename());
		System.out.println("Check 1");
		documentService.saveMyDocument(d);
		if (file.isEmpty()) {

		}
		try {
			// Get the file and save it somewhere
			File files = new File(type);
			if (!files.exists()) {
				if (files.mkdir()) {
					System.out.println("Directory is created!");
				} else {
					System.out.println("Failed to create directory!");
				}
			}
			byte[] bytes = file.getBytes();
			Path path = Paths.get(type, file.getOriginalFilename());
			Files.write(path, bytes);
			LOGGER.info("Event: Document Uploaded {}" + file.getOriginalFilename());
		} catch (IOException e) {
			e.printStackTrace();
                }

        }

        /* Document Draft */
        @RequestMapping("/draft")
        public String draftForm(Model model) {
                model.addAttribute("templates", Arrays.asList("demand_letter.txt", "affidavit.txt", "contract.txt"));
                return "draft";
        }

        @PostMapping("/draft/generate")
        public ResponseEntity<byte[]> generateDraft(@RequestParam String template, @RequestParam String values,
                        @RequestParam String type) throws Exception {
                Map<String, String> map = new ObjectMapper().readValue(values, new TypeReference<Map<String, String>>() {});
                String content = draftService.generateContent(template, map);
                Path file = Files.createTempFile("draft", type.equals("word") ? ".docx" : ".pdf");
                if ("word".equals(type)) {
                        draftService.exportToWord(content, file);
                } else {
                        draftService.exportToPdf(content, file);
                }
                byte[] data = Files.readAllBytes(file);
                Files.deleteIfExists(file);
                String filename = "document." + ("word".equals(type) ? "docx" : "pdf");
                MediaType media = "word".equals(type)
                                ? MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.wordprocessingml.document")
                                : MediaType.APPLICATION_PDF;
                return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                                .contentType(media)
                                .body(data);
        }

        /* Case-logs controller */

	@RequestMapping("/case-logs")
	public String CaseLogs(HttpServletRequest request) {
		request.setAttribute("title", caseService.CaseTitle());
		request.setAttribute("mode", "Case-Logs");

		return "case-logs";
	}

	@RequestMapping("/get-logs")
	public String getLogs(@RequestParam String caseno, HttpServletRequest request) {
		request.setAttribute("title", caseService.CaseTitle());
		request.setAttribute("logs", logsService.getLog(caseno));
		request.setAttribute("mode", "Case-Logs");

		return "case-logs";
	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@RequestMapping("/add-logs")
	public String AddLogs(HttpServletRequest request) {
		request.setAttribute("title", caseService.CaseTitle());
		request.setAttribute("mode", "Add-logs");
		return "case-logs";
	}

	@PostMapping("/save-logs")
	public String saveLogs(@ModelAttribute Logs logs, BindingResult bindingResult, HttpServletRequest request) {
		request.setAttribute("msg", "Logs Entries Updated Seccessfully");
		logsService.saveLogs(logs);
		LOGGER.info("Event: New Log entered");
		return "redirect:/add-logs";

	}

	@PreAuthorize("hasAnyRole('ADMIN')")
	@ResponseBody
	@PostMapping("/set-log")
	public void setLogs(@ModelAttribute Logs logs, @RequestParam String date, BindingResult bindingResult,
			HttpServletRequest request) {
		System.out.println("From log input " + date);
		request.setAttribute("msg", "Logs Entries Updated Seccessfully");
		LOGGER.info("Event: New Log entered");
		logsService.saveLogs(logs);

	}

	/* Payment */
	@PreAuthorize("hasAnyRole('ADMIN')")
	@ResponseBody
	@RequestMapping(path = "/payment-update", method = RequestMethod.POST)
	public void PaymentUpdate(HttpServletRequest request, @ModelAttribute Payment payment) {
		paymentService.UpdatePaymentById(payment);
		LOGGER.info("Event: Payment Updated");
	}

	/* Updates Menu Bar */

	@RequestMapping("/updates")
	public String updates(HttpServletRequest request) throws IOException {

		request.setAttribute("updates", updatesService.showAllUpdates());
		request.setAttribute("mode", "All_Updates");

		return "updates";
	}

	@RequestMapping("/logs")
	public String Logs(HttpServletRequest request) throws IOException {

		File file = new File("logs/info.log");
		BufferedReader br = new BufferedReader(new FileReader(file));
		String st;
		List<String> logs = new ArrayList<String>();
		while ((st = br.readLine()) != null) {
			logs.add(st);
		}

		request.setAttribute("logs", logs);
		request.setAttribute("mode", "All_Logs");
		br.close();
		return "updates";
	}
}
