package com.arnellconsulting.tps.web;

import javax.persistence.TypedQuery;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.arnellconsulting.tps.model.Person;

@RequestMapping("/registration/**")
@Controller
public class RegistrationController {

    @RequestMapping(method = RequestMethod.GET, value="otp")
    public @ResponseBody String validateOtp(@RequestParam Integer days) {
        return String.valueOf(days * 50);
    }
    
    @RequestMapping(method = RequestMethod.GET, value="email")
    public @ResponseBody Boolean isUserNameUnique(@RequestParam String email) {
        TypedQuery<Person> query = Person.findPeopleByUserName(email);        
        boolean emailExists = query.getResultList().isEmpty();
        return emailExists;
    }
    
}
