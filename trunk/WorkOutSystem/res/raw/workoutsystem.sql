
create table exercicio 
(
	codigo integer primary key autoincrement,
	nomeexercicio varchar(30) not null,
	grupomuscular varchar (20) not null,
	descricao text null,
	personalizado boolean not null
);

create table treino
(
	codigo integer primary key autoincrement,
	nometreino varchar (2) not null
);

create table usuario 
(	
	codigo integer primary key autoincrement,
	nome varchar(12) not null,
	senha varchar(20) not null
);


create table medida
(
  codigo integer primary key autoincrement,  
  nome varchar (20) not null,  
  unidade varchar (2) not null,  
  lado varchar (1) not null

);


create table perfil 
(	
	codigo integer primary key autoincrement,
	nome varchar (20) not null,
	sexo boolean not null,
	codigousuario int not null,
    
  unique (codigo,codigousuario)
	foreign key (codigousuario) references usuario (codigo)
);

create table ficha
(
	codigo integer primary key autoincrement,
	nomeficha varchar(20) not null,
	duracaoDias int not null,
	objetivo varchar (100) null,
	realizacoes int not null,
	codigoperfil int not null,
	codigousuario int not null,

	foreign key (codigoperfil) references perfil (codigo)
  foreign key (codigousuario) references perfil(codigousuario)
);

create table realizacao
(
	codigo integer primary key autoincrement,
	codigotreino int not null,
	datarealizacao datetime not null,
	
	foreign key (codigotreino) references treino (codigo)

);


create table passo
(
	codigo integer primary key autoincrement,
	sequencia int not null,
	explicacao text not null,
	codigoexercicio int not null,
	
	foreign key (codigoexercicio) references exercicio (codigo)
);





create table medicao
(
	codigo integer primary key autoincrement,
	valor decimal (5,2) not null,
	datamedicao datetime not null,
	codigomedida int not null,
  codigoperfil int not null,	

  unique (codigo,codigomedida,codigoperfil)
	foreign key (codigomedida) references medida (codigo),
  foreign key (codigoperfil) references perfil (codigo)
);

create table avatar
(
	codigo integer primary key autoincrement,
	nomeavatar varchar (20) not null,
	imagem int not null,
	nivel varchar (30) not null,
	mensagem varchar (200) not null,
	codigoperfil int not null,
	
	
	foreign key (codigoperfil) references perfil (codigo)	
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
	foreign key (codigoficha) references ficha (codigo),
	foreign key (codigoexercicio) references exercicio (codigo),
	foreign key (codigotreino) references treino (codigo)

);


create table diasemana
(
	codigo integer primary key autoincrement,
	diasemana varchar(15) not null

);


create table frequenciaperfil
(
	codigodia int not null,
	codigoperfil int not null,
	
	primary key (codigodia,codigoperfil)
	foreign key (codigoperfil) references perfil (codigo)
  foreign key (codigodia) references diasemana (codigo)
);

create table frequenciatreino
(
	codigodia int not null,
	codigotreino int not null,
	
	primary key (codigodia,codigotreino)
	foreign key (codigotreino) references treino (codigo)
  foreign key (codigodia) references diasemana (codigo)
);
