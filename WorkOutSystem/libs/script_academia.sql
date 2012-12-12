-- usuario
insert into usuario (nome, senha)  values ('gabrielalves','1616');

insert into usuario (nome, senha) values ('caiofelipe','1010');

insert into usuario (nome, senha) values ('lucasalves','1238');

select * from usuario;

--exercicios
-- personalizado bit 1 - não personalizado e 0 - para personalizado

insert into exercicio (nomeexercicio , grupomuscular, descricao, personalizado) values ('Rosca Direta','Biceps', 'Exercicios direcionado para os biceps',0),('Supino','Peito', 'Exercicios direcionado para o peito',0),('Supino Personalizado','Peito', 'Exercicio de supino criado pelo usuario', 1);

select * from exercicio;

-- treino
insert into treino (nometreino) values ('A'),('B'),('C');

select * from treino;

-- perfil

insert into perfil (nome, sexo, codigousuario) values ('Gabriel', 0, 1),('Caio',0,2),('Lucas',1,3);

select * from perfil;

--ficha

insert into ficha (nomeficha, duracaoDias, objetivo, realizacoes, codigoperfil, codigousuario) values ('Ficha Piramide',60,'Ganho de Massa Magra',0,1,1),('Ficha Piramide',60,'Ganho de Massa Magra',0,2,2),('Ficha Inicial',30,'Ficha realizada para iniciantes, como adaptação',5,3,2);

select * from ficha;

-- realizacao

insert into realizacao (codigotreino, datarealizacao) values (1,12/11/2012),(2,10/10/2012),(1,12/08/2012);

select * from realizacao;

-- passo

insert into passo (sequencia, explicacao, codigoexercicio) values (1,'Posiciona a Barra de forma reta',4),(2,'Eleva a Barra sem deslocar o braço',5),(1,'Posiciona a barra sobre o peito',6);

select * from passo;

-- medida



insert into medida (nome, lado, unidade) values ('Altura','a','cm'),('Peso','a','kg'),('Cintura','a','cm'),('Quadril','a','cm'),('Peito','a','cm')
,('Braco','d','cm'),('Braco','e','cm'),('Coxa','d','cm'),('Coxa','e','cm'),('Panturilha','d','cm'),('Panturilha','e','cm');

select * from medida;

-- medicao

Select Convert (char(10),datamedicao,103) from medicao;


insert into medicao (valor, datamedicao, codigomedida, codigousuario, codigoperfil) values (30.0, '12/11/2012' , 8, 1, 1 ),(30.0, '12/11/2012', 9, 2,2),(40.0, '12/11/2012', 10, 3,3);

select * from medicao;


-- avatar

insert into avatar (nomeavatar, imagem, nivel, mensagem, codigoperfil, codigousuario) values ('Gabs', 1,'Iniciante','Mensagem do dia bla bla bla',1 ,1),('Caioo', 1,'Medio','Mensagem do dia bla bla bla',2 ,2),('Lucas', 1,'Avançado','Mensagem do dia bla bla bla',3,3 );


select * from avatar;

-- especificacao

insert into especificacao (codigoficha, codigoexercicio, codigotreino, carga, unidade, serie, ordem, quantidade) values (1,4,2,30,'Kg',1,3,10),(2,5,3,40,'Kg',2,1,10),(1,6,2,30,'Kg',2,3,10);


select * from especificacao;


-- diaSemana

insert into diaSemana (diaSemana) values ('Segunda'),('Terça'),('Quarta'),('Quinta'),('Sexta'),('Sabado'),('Domingo');
select * from diaSemana;

-- FrequenciaTreino

insert into frequenciaTreino (codigodia, codigotreino) values (1,1),(1,1),(2,1);

select * from frequenciaTreino;

-- FrequenciaPerfil

insert into frequenciaPerfil (codigodia, codigoperfil, codigousuario) values (3,2,1),(5,3,3),(3,1,1);


select * from frequenciaPerfil;

-- GrupoMuscular

insert into grupomuscular (nome) values ('Peito'),('Ombro'),('Costas'),('Biceps'),('Triceps'),('Aerobico'),('Membros Inferiores'),('Abdomen');

select * from grupomuscular;

