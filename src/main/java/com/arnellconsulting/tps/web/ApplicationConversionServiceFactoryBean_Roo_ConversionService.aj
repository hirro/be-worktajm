// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package com.arnellconsulting.tps.web;

import com.arnellconsulting.tps.model.Contract;
import com.arnellconsulting.tps.model.Corporate;
import com.arnellconsulting.tps.model.CorporateRegistration;
import com.arnellconsulting.tps.model.Customer;
import com.arnellconsulting.tps.model.Person;
import com.arnellconsulting.tps.model.Project;
import com.arnellconsulting.tps.model.TimeEntry;
import com.arnellconsulting.tps.web.ApplicationConversionServiceFactoryBean;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;

privileged aspect ApplicationConversionServiceFactoryBean_Roo_ConversionService {
    
    declare @type: ApplicationConversionServiceFactoryBean: @Configurable;
    
    public Converter<Contract, String> ApplicationConversionServiceFactoryBean.getContractToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.arnellconsulting.tps.model.Contract, java.lang.String>() {
            public String convert(Contract contract) {
                return new StringBuilder().append(contract.getValidFrom()).append(' ').append(contract.getValidTo()).append(' ').append(contract.getRate()).toString();
            }
        };
    }
    
    public Converter<Long, Contract> ApplicationConversionServiceFactoryBean.getIdToContractConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.arnellconsulting.tps.model.Contract>() {
            public com.arnellconsulting.tps.model.Contract convert(java.lang.Long id) {
                return Contract.findContract(id);
            }
        };
    }
    
    public Converter<String, Contract> ApplicationConversionServiceFactoryBean.getStringToContractConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.arnellconsulting.tps.model.Contract>() {
            public com.arnellconsulting.tps.model.Contract convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Contract.class);
            }
        };
    }
    
    public Converter<Corporate, String> ApplicationConversionServiceFactoryBean.getCorporateToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.arnellconsulting.tps.model.Corporate, java.lang.String>() {
            public String convert(Corporate corporate) {
                return new StringBuilder().append(corporate.getName()).toString();
            }
        };
    }
    
    public Converter<Long, Corporate> ApplicationConversionServiceFactoryBean.getIdToCorporateConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.arnellconsulting.tps.model.Corporate>() {
            public com.arnellconsulting.tps.model.Corporate convert(java.lang.Long id) {
                return Corporate.findCorporate(id);
            }
        };
    }
    
    public Converter<String, Corporate> ApplicationConversionServiceFactoryBean.getStringToCorporateConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.arnellconsulting.tps.model.Corporate>() {
            public com.arnellconsulting.tps.model.Corporate convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Corporate.class);
            }
        };
    }
    
    public Converter<CorporateRegistration, String> ApplicationConversionServiceFactoryBean.getCorporateRegistrationToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.arnellconsulting.tps.model.CorporateRegistration, java.lang.String>() {
            public String convert(CorporateRegistration corporateRegistration) {
                return new StringBuilder().append(corporateRegistration.getCorporateName()).append(' ').append(corporateRegistration.getEmail()).append(' ').append(corporateRegistration.getPassword()).toString();
            }
        };
    }
    
    public Converter<Long, CorporateRegistration> ApplicationConversionServiceFactoryBean.getIdToCorporateRegistrationConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.arnellconsulting.tps.model.CorporateRegistration>() {
            public com.arnellconsulting.tps.model.CorporateRegistration convert(java.lang.Long id) {
                return CorporateRegistration.findCorporateRegistration(id);
            }
        };
    }
    
    public Converter<String, CorporateRegistration> ApplicationConversionServiceFactoryBean.getStringToCorporateRegistrationConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.arnellconsulting.tps.model.CorporateRegistration>() {
            public com.arnellconsulting.tps.model.CorporateRegistration convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), CorporateRegistration.class);
            }
        };
    }
    
    public Converter<Customer, String> ApplicationConversionServiceFactoryBean.getCustomerToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.arnellconsulting.tps.model.Customer, java.lang.String>() {
            public String convert(Customer customer) {
                return new StringBuilder().append(customer.getName()).append(' ').append(customer.getOrganisationalNumber()).append(' ').append(customer.getStreet()).append(' ').append(customer.getStreetNumber()).toString();
            }
        };
    }
    
    public Converter<Long, Customer> ApplicationConversionServiceFactoryBean.getIdToCustomerConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.arnellconsulting.tps.model.Customer>() {
            public com.arnellconsulting.tps.model.Customer convert(java.lang.Long id) {
                return Customer.findCustomer(id);
            }
        };
    }
    
    public Converter<String, Customer> ApplicationConversionServiceFactoryBean.getStringToCustomerConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.arnellconsulting.tps.model.Customer>() {
            public com.arnellconsulting.tps.model.Customer convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Customer.class);
            }
        };
    }
    
    public Converter<Person, String> ApplicationConversionServiceFactoryBean.getPersonToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.arnellconsulting.tps.model.Person, java.lang.String>() {
            public String convert(Person person) {
                return new StringBuilder().append(person.getFirstName()).append(' ').append(person.getLastName()).append(' ').append(person.getEmail()).toString();
            }
        };
    }
    
    public Converter<Long, Person> ApplicationConversionServiceFactoryBean.getIdToPersonConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.arnellconsulting.tps.model.Person>() {
            public com.arnellconsulting.tps.model.Person convert(java.lang.Long id) {
                return Person.findPerson(id);
            }
        };
    }
    
    public Converter<String, Person> ApplicationConversionServiceFactoryBean.getStringToPersonConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.arnellconsulting.tps.model.Person>() {
            public com.arnellconsulting.tps.model.Person convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Person.class);
            }
        };
    }
    
    public Converter<Project, String> ApplicationConversionServiceFactoryBean.getProjectToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.arnellconsulting.tps.model.Project, java.lang.String>() {
            public String convert(Project project) {
                return new StringBuilder().append(project.getName()).append(' ').append(project.getDescription()).toString();
            }
        };
    }
    
    public Converter<Long, Project> ApplicationConversionServiceFactoryBean.getIdToProjectConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.arnellconsulting.tps.model.Project>() {
            public com.arnellconsulting.tps.model.Project convert(java.lang.Long id) {
                return Project.findProject(id);
            }
        };
    }
    
    public Converter<String, Project> ApplicationConversionServiceFactoryBean.getStringToProjectConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.arnellconsulting.tps.model.Project>() {
            public com.arnellconsulting.tps.model.Project convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), Project.class);
            }
        };
    }
    
    public Converter<TimeEntry, String> ApplicationConversionServiceFactoryBean.getTimeEntryToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<com.arnellconsulting.tps.model.TimeEntry, java.lang.String>() {
            public String convert(TimeEntry timeEntry) {
                return new StringBuilder().append(timeEntry.getStartTime()).append(' ').append(timeEntry.getEndTime()).append(' ').append(timeEntry.getComment()).toString();
            }
        };
    }
    
    public Converter<Long, TimeEntry> ApplicationConversionServiceFactoryBean.getIdToTimeEntryConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.Long, com.arnellconsulting.tps.model.TimeEntry>() {
            public com.arnellconsulting.tps.model.TimeEntry convert(java.lang.Long id) {
                return TimeEntry.findTimeEntry(id);
            }
        };
    }
    
    public Converter<String, TimeEntry> ApplicationConversionServiceFactoryBean.getStringToTimeEntryConverter() {
        return new org.springframework.core.convert.converter.Converter<java.lang.String, com.arnellconsulting.tps.model.TimeEntry>() {
            public com.arnellconsulting.tps.model.TimeEntry convert(String id) {
                return getObject().convert(getObject().convert(id, Long.class), TimeEntry.class);
            }
        };
    }
    
    public void ApplicationConversionServiceFactoryBean.installLabelConverters(FormatterRegistry registry) {
        registry.addConverter(getContractToStringConverter());
        registry.addConverter(getIdToContractConverter());
        registry.addConverter(getStringToContractConverter());
        registry.addConverter(getCorporateToStringConverter());
        registry.addConverter(getIdToCorporateConverter());
        registry.addConverter(getStringToCorporateConverter());
        registry.addConverter(getCorporateRegistrationToStringConverter());
        registry.addConverter(getIdToCorporateRegistrationConverter());
        registry.addConverter(getStringToCorporateRegistrationConverter());
        registry.addConverter(getCustomerToStringConverter());
        registry.addConverter(getIdToCustomerConverter());
        registry.addConverter(getStringToCustomerConverter());
        registry.addConverter(getPersonToStringConverter());
        registry.addConverter(getIdToPersonConverter());
        registry.addConverter(getStringToPersonConverter());
        registry.addConverter(getProjectToStringConverter());
        registry.addConverter(getIdToProjectConverter());
        registry.addConverter(getStringToProjectConverter());
        registry.addConverter(getTimeEntryToStringConverter());
        registry.addConverter(getIdToTimeEntryConverter());
        registry.addConverter(getStringToTimeEntryConverter());
    }
    
    public void ApplicationConversionServiceFactoryBean.afterPropertiesSet() {
        super.afterPropertiesSet();
        installLabelConverters(getObject());
    }
    
}
