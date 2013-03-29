package com.arnellconsulting.tps.webflow;

import java.math.BigDecimal;

import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;
import org.springframework.stereotype.Controller;

import com.arnellconsulting.tps.model.Contract;
import com.arnellconsulting.tps.model.Corporate;
import com.arnellconsulting.tps.model.Customer;
import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.model.Project;

@RooJavaBean
@RooToString
@RooEquals
@RooSerializable
public class Registration{

    @NotNull
    private String corporateName = "";

    @NotNull
    private String email;

    private String password;
    
}
