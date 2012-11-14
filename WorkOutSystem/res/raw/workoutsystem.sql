create table exercicio 
(
	codigoexercicio integer primary key autoincrement,
	nomeexercicio varchar(30) not null,
	grupomuscular varchar (20) not null,
	descricao varchar (100) null,
	personalizado boolean not null
);

create table treino
(
	codigotreino integer primary key autoincrement,
	nometreino varchar (2) not null
);

create table usuario 
(	
	codigousuario integer primary key autoincrement,
	nome varchar(12) not null,
	senha varchar(20) not null
);


-- Com chave estrangeira

create table perfil 
(	
	codigoperfil integer primary key autoincrement,
	nome varchar (20) not null,
	sexo boolean not null,
	codigousuario int not null,
	foreign key (codigousuario) references usuario (codigousuario)
);

create table ficha
(
	codigoficha integer primary key autoincrement,
	nomeficha varchar(20) not null,
	duracaoDias int not null,
	objetivo varchar (100) null,
	realizacoes int not null,
	codigoperfil int not null,
	
	foreign key (codigoperfil) references perfil (codigoperfil)
);

create table realizacao
(
	codigorealizacao integer primary key autoincrement,
	codigotreino int not null,
	datarealizacao datetime not null,
	
	foreign key (codigotreino) references treino (codigotreino)

);


create table passo
(
	codigopasso integer primary key autoincrement,
	sequencia int not null,
	explicacao text not null,
	codigoexercicio int not null,
	
	foreign key (codigoexercicio) references exercicio (codigoexercicio)
);


create table medida
(
	codigomedida integer primary key autoincrement,
	nome varchar (20) not null,
	lado varchar (1) null,
	codigoperfil int not null,
	
	foreign key (codigoperfil) references perfil (codigoperfil)
);

create table medicao
(
	codigomedicao integer primary key autoincrement,
	valor decimal (9,2) not null,
	datamedicao datetime not null,
	codigomedida int not null,
	

	foreign key (codigomedida) references medida (codigomedida)
);

create table avatar
(
	codigoavatar integer primary key autoincrement,
	nomeavatar varchar (20) not null,
	imagem int not null,
	nivel varchar (30) not null,
	mensagem varchar (200) not null,
	codigoperfil int not null,
	
	
	foreign key (codigoperfil) references perfil (codigoperfil)	
);

create table especificacao
(
	codigoficha int not null,
	codigoexercicio int not null,
	codigotreino int not null,
	carga int not null,
	unidade varchar(20) not null,
	serie int not null,
	ordem int not null,
	quantidade int not null,
	
	primary key (codigoficha,codigoexercicio,codigotreino),
	foreign key (codigoficha) references ficha (codigoficha),
	foreign key (codigoexercicio) references exercicio (codigoexercicio),
	foreign key (codigotreino) references treino (codigotreino)

);


create table frequencia
(
	codigofrequencia integer primary key autoincrement,
	diasemana varchar(15) not null,
	codigotreino int not null,
	codigoperfil int not null,
	
	
	foreign key (codigotreino) references treino (codigotreino),
	foreign key (codigoperfil) references perfil (codigoperfil)
	);