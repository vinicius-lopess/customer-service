CREATE TABLE 'customer' (
	'id' bigint(20) NOT NULL AUTO_INCREMENT,
	'endereco' varchar(100) NOT NULL,
	'cidade' varchar(100) NOT NULL,
	'uf' varchar(100) NOT NULL,
	'genero' varchar(8) NOT NULL,
	'nome' varchar(100) NOT NULL,
	'sobre_nome' varchar(100) NOT NULL,
	'num_cpf' varchar(30) NOT NULL,
	'dth_nascimento' datetime NOT NULL,
PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4;