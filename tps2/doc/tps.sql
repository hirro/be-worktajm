/* SQLEditor (Generic SQL)*/

CREATE TABLE Address
(
id INTEGER AUTO_INCREMENT  UNIQUE,
address1 VARCHAR(255),
address2 VARCHAR(255),
address3 VARCHAR(255),
city VARCHAR(255),
state VARCHAR(2),
country VARCHAR(2),
postal_code VARCHAR(16),
id_1 VARCHAR(255),
PRIMARY KEY (id)
);

CREATE TABLE ContractHourly
(
id INTEGER AUTO_INCREMENT  UNIQUE,
rate INTEGER,
contract_id INTEGER,
PRIMARY KEY (id)
);

CREATE TABLE Corporate
(
id INTEGER AUTO_INCREMENT  UNIQUE,
address_id INTEGER,
PRIMARY KEY (id)
);

CREATE TABLE Contract
(
id INTEGER AUTO_INCREMENT,
customer_id INTEGER UNIQUE,
corporate_id INTEGER,
PRIMARY KEY (id)
);

CREATE TABLE Customer
(
id INTEGER AUTO_INCREMENT,
address_id INTEGER,
PRIMARY KEY (id)
);

CREATE TABLE TimeEntry
(
id INTEGER AUTO_INCREMENT  UNIQUE,
user_id INTEGER UNIQUE,
project_id INTEGER UNIQUE,
start_time INTEGER,
end_time INTEGER,
comment VARCHAR2,
PRIMARY KEY (id)
);

CREATE TABLE Project
(
id INTEGER AUTO_INCREMENT,
user_id INTEGER,
PRIMARY KEY (id)
);

CREATE TABLE Employee
(
id INTEGER AUTO_INCREMENT,
corporate_id INTEGER,
group_id INTEGER,
PRIMARY KEY (id)
);

/* VIEW View1 - no query specified */

CREATE INDEX Address_id_idx ON Address(id);

CREATE INDEX ContractHourly_id_idx ON ContractHourly(id);

ALTER TABLE ContractHourly ADD FOREIGN KEY (contract_id) REFERENCES Contract (id);

CREATE INDEX Corporate_id_idx ON Corporate(id);

ALTER TABLE Corporate ADD FOREIGN KEY (address_id) REFERENCES Address (id);

CREATE INDEX Contract_id_idx ON Contract(id);

ALTER TABLE Contract ADD FOREIGN KEY (corporate_id) REFERENCES Corporate (id);

CREATE INDEX Customer_id_idx ON Customer(id);

ALTER TABLE Customer ADD FOREIGN KEY (id) REFERENCES Contract (customer_id);

ALTER TABLE Customer ADD FOREIGN KEY (address_id) REFERENCES Address (id);

CREATE INDEX TimeEntry_id_idx ON TimeEntry(id);

CREATE INDEX Project_id_idx ON Project(id);

ALTER TABLE Project ADD FOREIGN KEY (id) REFERENCES TimeEntry (project_id);

CREATE INDEX Employee_id_idx ON Employee(id);

ALTER TABLE Employee ADD FOREIGN KEY (id) REFERENCES TimeEntry (user_id);

ALTER TABLE Employee ADD FOREIGN KEY (corporate_id) REFERENCES Corporate (id);
