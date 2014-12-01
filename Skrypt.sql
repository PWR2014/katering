-- Table: employee

-- DROP TABLE employee;

CREATE TABLE employee
(
  id integer,
  haslo text,
  uzytkownik text,
  uprawnienia integer
)
WITH (
  OIDS=FALSE
);
ALTER TABLE employee
  OWNER TO postgres;

  
  
  insert into employee (id,haslo,uzytkownik,uprawnienia) values (1,'haslo1','wacek',1);
insert into employee (id,haslo,uzytkownik,uprawnienia) values (2,'haslo2','Zenek',1);
insert into employee (id,haslo,uzytkownik,uprawnienia) values (3,'haslo3','Maciek',2);