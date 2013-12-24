/*
 * Copyright 2013 Jim Arnell
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */



package com.arnellconsulting.tps.model;

import junit.framework.TestCase;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Created by jiar on 2013-12-22.
 */
public class CustomerTest extends TestCase {
    @Test
    public void testGetters() {
       final Person person = TestConstants.createPersonA();
       final Customer customer = TestConstants.createCustomerA();
       assertThat(customer.getCompanyName(), is(TestConstants.CUSTOMER_A_NAME));
       assertThat(customer.getReferencePerson(), is(TestConstants.CUSTOMER_A_REFERENCE_PERSON));
       assertThat(customer.getCompanyName(), is(TestConstants.CUSTOMER_A_NAME));
       assertThat(customer.getBillingAddress().getLine1(), is(TestConstants.ADDRESS_A_LINE1));
       assertThat(customer.getBillingAddress().getLine2(), is(TestConstants.ADDRESS_A_LINE2));
       assertThat(customer.getBillingAddress().getCity(), is(TestConstants.ADDRESS_A_CITY));
       assertThat(customer.getBillingAddress().getZip(), is(TestConstants.ADDRESS_A_ZIP));
       assertThat(customer.getProjects(),  not(nullValue()));

    }
}
