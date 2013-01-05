package com.arnellconsulting.tps.model;

import javax.validation.constraints.NotNull;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Registration {

    @NotNull
    @NotBlank
    @Length(min = 5, message = "The field must be at least 5 characters")
    private String corporateName;

    @NotNull
    @NotBlank
    @Email(message = "Email Address is not a valid format")
    private String email;
}
