insert into project (id, name) value (1, 'Project A');
insert into project (id, name) value (2, 'Project B');
insert into project (id, name) value (3, 'Project C');	

insert into person (id, email, first_name, last_name, password, email_verified, active_time_entry) values (1, 'jim@arnellconsulting.com', 'Jim', 'Arnell', 'password', 1, NULL);

insert into time_entry (id, start_time, end_time, person, project, comment) values (1,'2001-02-03 20:00:00', NULL, 1, 3, NULL);

update person set active_time_entry=1 where (id=1);
