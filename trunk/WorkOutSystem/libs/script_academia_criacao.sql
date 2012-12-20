
create table treino
(
	codigo integer primary key autoincrement,
	nome varchar (2) not null
);

create table usuario 
(	
	codigo integer primary key autoincrement,
	nome varchar(12) not null,
	senha varchar(20) not null,
	logado boolean not null
);

create table medida
(
	codigo integer primary key autoincrement,
	nome varchar (20) not null,
	lado varchar (1) not null,
	unidade varchar (5) not null
	
);

                        -- Novo : codigodia é autoincrement ?
create table diaSemana
(
  codigo integer primary key,
  diaSemana varchar (20) not null
);


create table grupomuscular
(
	codigo integer primary key autoincrement,
	nome varchar(30) not null

);

create table ficha
(
	codigo integer primary key autoincrement,
	nome varchar(20) not null,
	duracaoDias int not null,
	objetivo varchar (100) null,
	realizacoes int not null
 
);



-- Com chave estrangeira

create table exercicio 
(
	codigo integer primary key autoincrement,
	nome varchar(30) not null,
	descricao varchar (100) null,
	personalizado boolean not null,
	codigogrupomuscular int not null,
	foreign key (codigogrupomuscular) references grupomuscular 

(codigo)
);


create table perfil 
(	
	codigo integer primary key autoincrement,
	nome varchar (20) not null,
	sexo boolean not null,
	codigousuario int not null,
	foreign key (codigousuario) references usuario (codigo)
);

                         -- Novo                         



create table frequenciaPerfil
(
     codigodia int not null,   
     codigoperfil int not null,         

     foreign key (codigoperfil) references  perfil(codigo),
     foreign key (codigodia) references  diaSemana(codigo)

);

create table perfilFicha
(
       codigoperfil int not null,       
       codigoficha int not null,       
       atual boolean not null,       

       foreign key (codigoperfil) references perfil(codigo),
       foreign key (codigoficha) references ficha(codigo)
       
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
	valor decimal (9,2) not null,
	datamedicao datetime not null,
	codigomedida int not null,
	codigoperfil int not null,  

	foreign key (codigoperfil) references perfil (codigo),
	foreign key (codigomedida) references medida (codigo)
);

create table avatar
(
	codigo integer primary key autoincrement,
	nome varchar (20) not null,
	imagem int not null,
	nivel varchar (30) not null,
	mensagem varchar (200) not null,
	codigoperfil int not null,
	
	foreign key (codigoperfil) references perfil (codigo)	
);

create table frequenciaTreino
(
   codigodia int not null,   
   codigotreino int not null, 
   codigoficha int not null,	  

   foreign key (codigoficha) references ficha (codigo),	
   foreign key (codigotreino) references treino (codigo),
   foreign key (codigodia) references  diaSemana(codigo)
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
	
	primary key (codigoficha , codigoexercicio ,codigotreino),
	foreign key (codigoficha) references ficha (codigo),
	foreign key (codigoexercicio) references exercicio (codigo),
	foreign key (codigotreino) references treino (codigo)

);
