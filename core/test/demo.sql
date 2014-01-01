
-- Create demo persons
INSERT INTO person (id, email, email_verified, first_name, last_name, password) VALUES ('1', 'jim@arnellconsulting.com', '1', 'Jim', 'Arnell', 'password');
INSERT INTO person (id, email, email_verified, first_name, last_name, password) VALUES ('2', 'jonna@arnellconsulting.com', '1', 'Jonna', 'Arnell', 'password');

-- Create customers
INSERT INTO customer (id, city, country, line1, state, zip, person, name) VALUES ('1', 'Stockholm', 'SE', 'Road 1', 'Stockholm', '10000', '1', 'Customer A');
INSERT INTO customer (id, city, country, line1, state, zip, person, name) VALUES ('2', 'Stockholm', 'SE', 'Road 1', 'Stockholm', '10000', '1', 'Customer B');
INSERT INTO customer (id, city, country, line1, state, zip, person, name) VALUES ('3', 'Stockholm', 'SE', 'Road 1', 'Stockholm', '10000', '2', 'Customer C');

-- Create projects
INSERT INTO project (id, name, rate, person, customer_id) VALUES ('1', 'Project A', '500', '1', '1');
INSERT INTO project (id, name, rate, person, customer_id) VALUES ('2', 'Project B', '1500', '1', '1');
INSERT INTO project (id, name, rate, person, customer_id) VALUES ('3', 'Project C', '650', '1', '2');
INSERT INTO project (id, name, rate, person, customer_id) VALUES ('4', 'Project D', '850', '2', '1');

--
commit;
