package com.eazybytes.eazyschool.controller;

import com.eazybytes.eazyschool.model.Contact;
import com.eazybytes.eazyschool.service.ContactService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContactController {
    private static Logger log = LoggerFactory.getLogger(ContactController.class);
    private  final ContactService contactService;
    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }
    @RequestMapping("/contact")
    public String displayContactPage(Model model){
        model.addAttribute("contact", new Contact());
        return "contact.html";
    }
//    @RequestMapping(value="/saveMsg",method=POST)
//    @PostMapping("/saveMsg")
//    public ModelAndView saveMessage(@RequestParam String name,@RequestParam String mobileNum,
//                                    @RequestParam String email,@RequestParam String subject,
//                                    @RequestParam String message){
//        log.info("Name : "+name);
//        log.info("Mobile : "+mobileNum);
//        log.info("Email : "+email);
//        log.info("Subject : "+subject);
//        log.info("Message : "+message);
//        return new ModelAndView("redirect:/contact");
//    }
    @PostMapping("/saveMsg")
    public String saveMessage(@Valid @ModelAttribute("contact") Contact  contact, Errors errors){
        if(errors.hasErrors()){
            log.error("Contact form validation failed due to : "+errors.toString());
            return "contact.html"; //just return the contact page without any action along with the errors if any available
        }
        contactService.saveMessageDetails(contact);
        contactService.setCounter(contactService.getCounter()+1);
        log.info("Number of times the contact page is submitted :"+contactService.getCounter());
        return "redirect:/contact"; //invoke action and return contact page
    }
}
