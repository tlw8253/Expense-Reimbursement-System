CREATE USER 'exp_reimb_sys2'@localhost IDENTIFIED BY 'exp_reimb_sys2';


DROP TABLE IF EXISTS employee;

-- create exp_reimb_sys table
CREATE TABLE employee(
emp_username VARCHAR(10) PRIMARY KEY NOT NULL,
emp_password VARCHAR(20) NOT NULL CHECK(LENGTH(emp_password)>= 6),
emp_first_name VARCHAR(60) NOT NULL,
emp_last_name VARCHAR(60) NOT NULL,
emp_email VARCHAR(60) NOT NULL
);

INSERT INTO employee (emp_username, emp_password, emp_first_name, emp_last_name, emp_email)
VALUES 
('tlw874', '12345678','Tomas', 'Ykel', 'tlw874@wwms.com'),
('zwz123', '12345678','Zack', 'Brow', 'zwz123@wwms.com');


SELECT * FROM employee;




