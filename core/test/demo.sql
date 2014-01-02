
-- Create demo persons
INSERT INTO tps_person (id, email, email_verified, first_name, last_name, password) VALUES ('1', 'jim@arnellconsulting.com', '1', 'Jim', 'Arnell', 'password');
INSERT INTO tps_person (id, email, email_verified, first_name, last_name, password) VALUES ('2', 'jonna@arnellconsulting.com', '1', 'Jonna', 'Arnell', 'password');

-- Create customers
INSERT INTO tps_customer (id, city, country, line1, state, zip, person_id, name) VALUES ('1', 'Stockholm', 'SE', 'Road 1', 'Stockholm', '10000', '1', 'Customer A');
INSERT INTO tps_customer (id, city, country, line1, state, zip, person_id, name) VALUES ('2', 'Stockholm', 'SE', 'Road 1', 'Stockholm', '10000', '1', 'Customer B');
INSERT INTO tps_customer (id, city, country, line1, state, zip, person_id, name) VALUES ('3', 'Stockholm', 'SE', 'Road 1', 'Stockholm', '10000', '2', 'Customer C');

-- Create projects
INSERT INTO tps_project (id, name, rate, person_id, customer_id) VALUES ('1', 'Project A', '500', '1', '1');
INSERT INTO tps_project (id, name, rate, person_id, customer_id) VALUES ('2', 'Project B', '1500', '1', '1');
INSERT INTO tps_project (id, name, rate, person_id, customer_id) VALUES ('3', 'Project C', '650', '1', '2');
INSERT INTO tps_project (id, name, rate, person_id, customer_id) VALUES ('4', 'Project D', '850', '2', '1');

--
commit;
