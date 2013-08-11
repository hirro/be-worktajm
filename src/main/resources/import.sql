-- ----------------------------------------------------------------------------------------------------------------------
-- Projects
-- ----------------------------------------------------------------------------------------------------------------------
insert into project (id, name) value (1, 'Project A');
insert into project (id, name) value (2, 'Project B');
insert into project (id, name) value (3, 'Project C');	
-- ----------------------------------------------------------------------------------------------------------------------
-- Persons
-- ----------------------------------------------------------------------------------------------------------------------
insert into person (id, email, first_name, last_name, email_verified) values (1, 'jim@arnellconsulting.com', 'Jim', 'Arnell', true);
insert into person (id, email, first_name, last_name, email_verified) values (2, 'jonna@arnellconsulting.com', 'Jonna', 'Arnell', false);
-- ----------------------------------------------------------------------------------------------------------------------
-- Time entries
-- ----------------------------------------------------------------------------------------------------------------------
insert into time_entry (id, start_time, end_time, person, project, comment) values (1,'2001-02-03 20:00:00', '2001-02-03 20:10:00', 1, 3, NULL);
insert into time_entry (id, start_time, end_time, person, project, comment) values (2,'2001-02-04 10:00:00', '2001-02-04 20:10:00', 1, 2, NULL);
insert into time_entry (id, start_time, end_time, person, project, comment) values (3,'2001-02-05 10:00:00', '2001-02-05 20:10:00', 1, 3, NULL);
insert into time_entry (id, start_time, end_time, person, project, comment) values (4,'2005-02-05 10:00:00', '2005-02-05 20:10:00', 2, 1, NULL);
-- ----------------------------------------------------------------------------------------------------------------------
-- Person time entry
-- ----------------------------------------------------------------------------------------------------------------------
update person set active_time_entry=3 where id=1
