
Padrões Atuais

insert into grupomuscular (nome) values ('Peito'),('Ombro'),('Costas'),('Biceps'),('Triceps'),('Aerobico'),('Membros Inferiores'),('Abdomen');


insert into diaSemana (diaSemana) values ('Segunda'),('Terça'),('Quarta'),('Quinta'),('Sexta'),('Sabado'),('Domingo');

insert into medida (nome, lado, unidade) values ('Altura','a','cm'),('Peso','a','kg'),('Cintura','a','cm'),('Quadril','a','cm'),('Peito','a','cm')
,('Braco','d','cm'),('Braco','e','cm'),('Coxa','d','cm'),('Coxa','e','cm'),('Panturilha','d','cm'),('Panturilha','e','cm');



teste

insert into exercicio (nome, descricao, personalizado, codigogrupomuscular) values ('Supino','Exercicio para peito com barra','0','1');
insert into exercicio (nome, descricao, personalizado, codigogrupomuscular) values ('Rosca Direta','Exercicio para biceps com barra','0','4');

insert into passo (sequencia, explicacao, codigoexercicio) values ('1','Fique com os braços retos','1'),('2','Suba a barra de forma reta','1');
insert into passo (sequencia, explicacao, codigoexercicio) values ('1','Fique com os braços retos','2'),('2','Suba a barra ','2');
