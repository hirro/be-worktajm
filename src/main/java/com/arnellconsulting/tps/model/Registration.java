package com.arnellconsulting.tps.model;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Registration {

    @NotNull
    @NotBlank
    @Column(unique = true)
    private String corporateName;

    @NotNull
    @NotBlank
    @Email(message = "Email Address is not a valid format")
    @Column(unique = true)
    private String email;

    @Size(min = 10, max=20)
    private String password;
}
