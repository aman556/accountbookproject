package com.example.javaproject.accountbook.controller;

import com.example.javaproject.accountbook.UserExcelExporter;
import com.example.javaproject.accountbook.model.Company;
import com.example.javaproject.accountbook.model.Data;
import com.example.javaproject.accountbook.repository.CompanyRepository;
import com.example.javaproject.accountbook.model.User;
import com.example.javaproject.accountbook.repository.DataRepository;
import com.example.javaproject.accountbook.repository.UserRepository;
import com.example.javaproject.accountbook.service.DataService;
import org.apache.tomcat.jni.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
//import org.springframework.http.HttpHeaders.HttpHeaders;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Properties;

@Controller
public class AppController {

    @Autowired
    private UserRepository repo;

    @Autowired
    private CompanyRepository com;

    @Autowired
    private DataService dataService;
    @Autowired
    private DataRepository datarepo;

    private Company companyAtt;
    private String companyEmail,companyName;
    //private String currentUserEmail;

    @GetMapping("/sendEmail")
    public String exportExcelSentMail(HttpServletResponse response){
        final String username = "amansharma14041998@gmail.com";
        final String password = "8512810555";

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");

        Session session = Session.getInstance(props,
                new javax.mail.Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });

        try {

            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(username));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(companyEmail));
            message.setSubject("Pooja Packaging");
            message.setText("Data Backup");
            //message.set
            List<Data> listData=datarepo.findByACompanyName(companyName);
            UserExcelExporter excelExporter = new UserExcelExporter(listData);

            //FileSystemResource file = new FileSystemResource(new File(excelExporter));
            //MimeMessageHelper helper = new MimeMessageHelper(message, true);
            //helper.addAttachment();
            Transport.send(message);



        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
        /*response.setContentType("application/octet-stream");
        String header = "Content-Disposition";
        //HttpHeaders headers = new HttpHeaders();
        List<Data> listData=datarepo.findByACompanyName(companyName);
        DateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());
        String fileName = companyName+"_" + currentDateTime +".xlsx";
        String headerValue = "attachment; filename=" + fileName;

        UserExcelExporter excelExporter = new UserExcelExporter(listData);
        ByteArrayInputStream bis = excelExporter.exportData(response);*/

        //headers.add("Content-Disposition",headerValue);
        return "redirect:/datapage";
    }



    @GetMapping("/register_success1")
    public  String companyDetail(Model model){
        model.addAttribute("company",new Company());

        return "register_success";
    }

    @GetMapping("/register")
    public  String showSignUpForm(Model model){
        model.addAttribute("user",new User());

        return "signup";
    }

    @GetMapping("/register_company")
    public  String showRegisterCompanyForm(Model model){
        model.addAttribute("company",new Company());

        return "registercompany";
    }

    @GetMapping("")
    public  String viewHomePage(){
        return "index";
    }




    @PostMapping("/procss_reister")
    public String processRegistration(User user){
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        repo.save(user);
        //currentUserEmail=user.getEmail();
        return "company";
    }


    @PostMapping("/company_register")
    public String companyRegistration(Company company){
        //company.setUserEmail(currentUserEmail);
        com.save(company);
        companyEmail=company.getEmail();
        return "editcompany";
    }

    @PostMapping("/show_data")
    public String showCompanyDetail(Company company){
        companyName=company.getCompanyName();
         companyAtt=com.findBy(companyName);
            companyEmail=companyAtt.getEmail();
            return "redirect:/datapage";

    }

    @GetMapping("/list_users")
    public String viewUsersList(Model model){
        List<User> listCompany=repo.findAll();
        model.addAttribute("listUsers",listCompany);
        return "company";
    }

    @GetMapping("/datapage")
    public String viewDataPage(Model model){
        model.addAttribute("listData",datarepo.findByACompanyName(companyName));
        return "company_data";
    }

    @GetMapping("/showNewDataForm")
    public String shownewDataForm(Model model){
        Data data =new Data();
        model.addAttribute("data",data);
        return "new_data";
    }
    @PostMapping("/saveData")
    public String saveData(@ModelAttribute("data") Data data){
        data.setCompanyName(companyName);
        dataService.saveData(data);
        return "redirect:/datapage";
    }

    @GetMapping("/showFormForUpdate/{id}")
    public String showFormForUpdate(@PathVariable( value = "id") long id, Model model){
        Data data=dataService.getDataById(id);
        model.addAttribute("data",data);
        return "update_data";
    }

    @GetMapping("/deleteData/{id}")
    public String deleteData(@PathVariable (value = "id") long id){
        this.dataService.deleteDataById(id);
        return "redirect:/datapage";
    }

    @GetMapping("/downloadExcelFile")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";


        DateFormat dateFormat = new SimpleDateFormat("yyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormat.format(new Date());
        String fileName = companyName+"_" + currentDateTime +".xlsx";
        String headerValue = "attachment; filename=" + fileName;
        response.setHeader(headerKey,headerValue);

        List<Data> listData=datarepo.findByACompanyName(companyName);

        UserExcelExporter excelExporter = new UserExcelExporter(listData);
        excelExporter.export(response);
    }
}
