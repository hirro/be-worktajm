package com.arnellconsulting.tps.webflow;

import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.equals.RooEquals;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.serializable.RooSerializable;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooEquals
@RooSerializable
public class Registration {

    @NotNull
    private String corporateName;

    @NotNull
    private String email;

    private String password;
}
