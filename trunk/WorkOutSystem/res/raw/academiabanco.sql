create table exercicio 
(
	codigoexercicio int identity (1,1) not null,
	nomeexercicio varchar(30) not null,
	grupomuscular varchar (20) not null,
	descricao varchar (100) null,
	personalizado bit not null,
	
	primary key (codigoexercicio)

);

create table treino
(
	codigotreino int identity (1,1) not null,
	nometreino varchar (2) not null,
	
	primary key (codigotreino)
);

create table usuario 
(	
	codigousuario int identity (1,1) not null,
	nome varchar(12) not null,
	senha varchar(20) not null,
	
	primary key (codigousuario)
);


-- Com chave estrangeira

create table perfil 
(	
	codigoperfil int identity (1,1) not null,
	nome varchar (20) not null,
	sexo bit not null,
	codigousuario int not null,
	
	primary key (codigoperfil),
	foreign key (codigousuario) references usuario (codigousuario)
);

create table ficha
(
	codigoficha int identity (1,1) not null,
	nomeficha varchar(20) not null,
	duracaoDias int not null,
	objetivo varchar (100) null,
	realizacoes int not null,
	codigoperfil int not null,
	
	primary key (codigoficha),
	foreign key (codigoperfil) references perfil (codigoperfil)
);

create table realizacao
(
	codigorealizacao int identity (1,1) not null,
	codigotreino int not null,
	datarealizacao datetime not null,
	
	primary key (codigorealizacao),
	foreign key (codigotreino) references treino (codigotreino)

);

create table passo
(
	codigopasso int identity (1,1) not null,
	sequencia int not null,
	explicacao text not null,
	codigoexercicio int not null,
	
	primary key (codigopasso),
	foreign key (codigoexercicio) references exercicio (codigoexercicio)
);

create table medida
(
	codigomedida int identity (1,1) not null,
	nome varchar (20) not null,
	lado varchar (1) null,
	codigoperfil int not null,
	
	primary key (codigomedida),
	foreign key (codigoperfil) references perfil (codigoperfil)
);

create table medicao
(
	codigomedicao int identity (1,1) not null,
	valor decimal (9,2) not null,
	datamedicao datetime not null,
	codigomedida int not null,
	
	primary key (codigomedicao),
	foreign key (codigomedida) references medida (codigomedida)
);

create table avatar
(
	codigoavatar int identity (1,1) not null,
	nomeavatar varchar (20) not null,
	imagem int not null,
	nivel varchar (30) not null,
	mensagem varchar (200) not null,
	codigoperfil int not null,
	
	primary key (codigoavatar),
	foreign key (codigoperfil) references perfil (codigoperfil)	
);

create table especificacao
(
	codigoespecificacao int identity (1,1) not null,
	codigoficha int not null,
	codigoexercicio int not null,
	codigotreino int not null,
	carga int not null,
	unidade varchar(20) not null,
	serie int not null,
	ordem int not null,
	quantidade int not null,
	
	primary key (codigoespecificacao,codigoficha,codigoexercicio,codigotreino),
	foreign key (codigoficha) references ficha (codigoficha),
	foreign key (codigoexercicio) references exercicio (codigoexercicio),
	foreign key (codigotreino) references treino (codigotreino)

);


create table frequencia
(
	codigofrequencia int identity (1,1) not null,
	diasemana varchar(15) not null,
	codigotreino int not null,
	codigoperfil int not null,
	
	primary key (codigofrequencia),
	foreign key (codigotreino) references treino (codigotreino),
	foreign key (codigoperfil) references perfil (codigoperfil)
	);