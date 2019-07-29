DROP DATABASE IF EXISTS `felipe_aubay`;
CREATE DATABASE felipe_aubay;

CREATE TABLE felipe_aubay.EMPREGADO (
    codEmpregado int (4) NOT NULL AUTO_INCREMENT ,
    nome varchar(50) NOT NULL,
	sobrenome varchar(50) NOT NULL,
	codDepartamento int (4) NOT NULL,
	codCargo int (4) NOT NULL,
    PRIMARY KEY (codEmpregado)
    );
	

CREATE TABLE felipe_aubay.DEPARTAMENTO  (
	codDepartamento int (4) NOT NULL AUTO_INCREMENT ,
    nomeDepartamento varchar(50) NOT NULL,
	descricaoDepartamento varchar(250) NOT NULL,
    PRIMARY KEY (codDepartamento),
    FOREIGN KEY (codDepartamento) REFERENCES felipe_aubay.EMPREGADO(codDepartamento)
    );
	

CREATE TABLE felipe_aubay.CARGO (
	codCargo int (4) NOT NULL AUTO_INCREMENT ,
    nomeCargo varchar(50) NOT NULL,
	salario int (10) NOT NULL,
    PRIMARY KEY (codCargo),
    FOREIGN KEY (codCargo) REFERENCES felipe_aubay.EMPREGADO(codCargo)
    );


INSERT INTO felipe_aubay.CARGO (`nomeCargo`, `salario`) VALUES
	('Gerente', 6000),
	('Vendedor', 3000),
	('Recepcionista', 1500);
	
INSERT INTO felipe_aubay.DEPARTAMENTO (`nomeDepartamento`, `descricaoDepartamento`) VALUES
	('Depto de Testes', 'Area voltada para testes integrados'),
	('Depto de Marketing', 'Area voltada para venda de produtos'),
	('Depto de Recursos Humanos', 'Area voltada para a manipulação de recursos');
	
INSERT INTO felipe_aubay.EMPREGADO (`nome`, `sobrenome`, `codDepartamento`, `codCargo`) VALUES
	('João', 'Bernardino', 1, 1),
	('Adriane', 'Oshiro', 3, 2),
	('Lucas', 'Daolio', 3, 3),
	('Rafael', 'Damasco', 3, 3),
	('José', 'Roberto', 2, 3);
	
SELECT nome, salario 
	FROM felipe_aubay.EMPREGADO,felipe_aubay.CARGO 
		WHERE felipe_aubay.EMPREGADO.codCargo=felipe_aubay.CARGO.codCargo ,
		AND salario>5000

SELECT nome, salario, nomeCargo 
	FROM felipe_aubay.EMPREGADO,felipe_aubay.CARGO 
		WHERE felipe_aubay.EMPREGADO.codCargo=felipe_aubay.CARGO.codCargo 
		AND salario<2000 
		AND nomeCargo!='Gerente'
		
SELECT nome, salario, nomeCargo 
	FROM felipe_aubay.EMPREGADO,felipe_aubay.CARGO 
		WHERE felipe_aubay.EMPREGADO.codCargo=felipe_aubay.CARGO.codCargo 
		AND salario > 2000
		AND salario < 6000
		AND nomeCargo='Gerente'
			OR nomeCargo='Vendedor'
	

	