-- CREATE USER 'shell'@localhost IDENTIFIED BY 'shell';


DROP TABLE IF EXISTS shell;

-- create shell table
CREATE TABLE shell(
shell_id INTEGER PRIMARY KEY AUTO_INCREMENT,
shell_name VARCHAR(255) NOT NULL CHECK(LENGTH(shell_name)> 0),
shell_int INTEGER NOT NULL,
shell_double DOUBLE NOT NULL,
shell_boolean BOOLEAN NOT NULL
);

INSERT INTO shell (shell_name, shell_int, shell_double, shell_boolean)
VALUES 
('Arnold', 1, 1.1, true),
('Linda', 3, 3.1, false),
-- ('Michael', 'Biehn', 'Kyle Reese'),
('Edward', 5, 5.5, true),
-- ('Earl', 'Boen', 'Dr. Siberman'),
-- ('Lance', 'Henriksen', 'Detective Hal Vukovich'),
-- ('Bill', 'Paxton', 'Punk Leader'),
-- ('Paul', 'Winfield', 'Lieutenant Ed Traxler'),
-- ('kristanna', 'Loken', 'Terminatrix'),
-- ('William', 'Wisher Jr.', 'Policeman #1'),
('Robert', 10, 10.01, false);


SELECT * FROM shell;




