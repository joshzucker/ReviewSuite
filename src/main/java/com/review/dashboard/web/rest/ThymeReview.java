package com.review.dashboard.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.review.dashboard.domain.Customer;
import com.review.dashboard.domain.CustomerAccessToken;
import com.review.dashboard.domain.Review1;
import com.review.dashboard.domain.Review2;
import com.review.dashboard.repository.Review1Repository;
import com.review.dashboard.repository.Review2Repository;
import com.review.dashboard.service.CustomerService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.security.Principal;
import java.time.ZonedDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * REST controller for managing Review1.
 */
@Controller
@RequestMapping("/api")
public class ThymeReview {

    private final Logger log = LoggerFactory.getLogger(ThymeReview.class);

    private static final String ENTITY_NAME = "review1";
        
    private final Review1Repository review1Repository;
    
    private final Review2Repository review2Repository;
    
  private final CustomerService customerService;
  
    

    public ThymeReview(Review1Repository review1Repository,Review2Repository review2Repository,CustomerService customerService) {
        this.review1Repository = review1Repository;
        this.review2Repository = review2Repository;
        this.customerService = customerService;
    }


    @RequestMapping(value = "/api/formreview1", method = RequestMethod.POST)
    public String showreview1(@Valid @ModelAttribute("Review1") Review1 review1, BindingResult bindingResult, Model model,  Principal principal, HttpServletRequest request) {
    
        /*User user = 
                (User) SecurityContextHolder.getContext()
                                            .getAuthentication().getPrincipal();
        //User user = userService.getUserWithAuthorities();
        if (review1.getId() != null) {
            return "error";
        }
        review1.setUser(user);
        review1Repository.save(review1);
        
        */
        String token = review1.getToken();
        String result = customerService.validateCustomerAccessToken(token);
        
        CustomerAccessToken myToken = customerService.getCustomerAccessToken(token);
        if(result!=null){
        return "Invalid Token";
        }
      
      
        review1.setUser(myToken.getUser());
        review1.setCustomer(myToken.getCustomer());
        
        Review1 review = review1Repository.save(review1);
        
        Customer customer = customerService.findOne(myToken.getCustomer().getId());
        customer.setReviewId(review1.getId());
        customer.setDateReceived(ZonedDateTime.now());
        customer.setIsReview1EmailClicked(true); 
        customer.setIsReview2EmailClicked(false);
        
        
        return "review1";
    }
    
    @RequestMapping(value = "/api/formreview2", method = RequestMethod.POST)
    public String showreview2(@Valid @ModelAttribute("Review2") Review2 review2, BindingResult bindingResult, Model model,  HttpServletRequest request) {
     
        
        String token = review2.getToken();
        String result = customerService.validateCustomerAccessToken(token);
        
        CustomerAccessToken myToken = customerService.getCustomerAccessToken(token);
        if(result!=null){
        return "Invalid Token";
        }
      
        review2.setUser(myToken.getUser());
        review2.setCustomer(myToken.getCustomer());
        
        Review2 review = review2Repository.save(review2);
        
        Customer customer = customerService.findOne(myToken.getCustomer().getId());
        customer.setReviewId(review2.getId());
        customer.setDateReceived(ZonedDateTime.now());
        customer.setIsReview2EmailClicked(true);  
        customer.setIsReview1EmailClicked(false);
        
        customerService.save(customer);
        
      
        model.addAttribute("form1","Thank you for your feedback");

        return "review2";
    }
   
    @RequestMapping(value ="/review1", method = RequestMethod.GET)
    public String sendReview1( Model model, @RequestParam("id2") long id2, @RequestParam("token") String token) {
        
        
        CustomerAccessToken myToken = customerService.getCustomerAccessToken(token);
        if(id2 != myToken.getCustomer().getId()){
            return "error";
        }
  
         model.addAttribute("token",token);
     
        return "review1";
    }
    
    @RequestMapping(value ="/review2", method = RequestMethod.GET)
    public String sendReview2( Model model, @RequestParam("id2") long id2, @RequestParam("token") String token) {
      
        CustomerAccessToken myToken = customerService.getCustomerAccessToken(token);
        if(id2 != myToken.getCustomer().getId()){
            return "error";
        }
  
       
       model.addAttribute("token",token);
     
        return "review2";
    }

}
