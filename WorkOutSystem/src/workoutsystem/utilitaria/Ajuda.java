package workoutsystem.utilitaria;

public enum Ajuda {
	
	EXERCICIO("Na tela Exercício, você poderá buscar os" +
					" exercícios padrões separado por grupos musculares, e verificar " +
					"a correta forma de execução, além de acessar a aba de exercícios criados, " +
					"podendo adicionar exercícios, incluindo seu nome, grupo muscular e descrição. \n\n\n" +
					"- Como criar um exercício? \n\n " +
					"1 – Selecione o opção EXRCICIO no menu principal. \n\n"+
					"2 – Seleciona a aba CRIADO, clique no menu do celular, em seguida na opção ADICIONAR," +
					" preencha os campos com o nome do exercício o grupo muscular e uma breve descrição , e salve. \n\n\n"+
					"- Como visualizar o passo a passo? \n\n"+
					"1 – Selecione a aba PADRÃO, na tela exercício. \n\n"+
					"2 – Selecione o grupo muscular e o exercício desejado. \n\n"+
					"3 – O sistema irá iniciar a tela de passo a passo, caso o exercício possua " +
					"essa função, apresentando a ilustração e descrição da postura a ser realizada.\n\n"+
					"4 – Ao clicar nas setas irá verificar os passos seguintes com as ilustrações e descrições a " +
					"serem feitas.\n\n"),
					
	PERFIL("Nessa tela você poderá cadastrar, alterar ou remover seu perfil. Com ele criado você pode realizar " +
			"outras funções no WorkOut System, como criar fichas, realizar rotinas, adicionar medidas, verificar evolução." +
			"\n\n\n" +
			"- Como criar e alterar um perfil?\n\n" +
			"1- Selecionar a opção PERFIL no menu Principal.\n\n" +
			"2- O sistema apresenta a tela de entrada de dados solicitando nome," +
			" sexo, e frequência que são realizados os treinos. \n\n" +
			"3- Preencha os campos informados e clique em SALVAR. \n\n" +
			"4 - O sistema irá validar as informações e enviará uma mensagem de usuário cadastrado com sucesso. \n\n " +
			"- Como excluir um perfil? \n\n" +
			"1- Na tela perfil, com seus campos já preenchidos, clique em EXCLUIR. \n\n" +
			"2- O sistema exclui o perfil e todas suas medições, informando mensagem de exclusão bem sucedida. \n\n"),
			
	ROTINA(" Na tela Rotina, você pode realizar as rotinas selecionando o treino desejado, além de ter a opção preview, " +
			"com os exercícios referentes à rotina selecionada. A aba HISTÓRICO apresenta todas as rotinas" +
			" realizadas por você separadas por mês. \n\n\n" +
			"-Como realizar o treino ? \n\n" +
			"1 – Na aba TREINOS, selecione o treino que irá realizar. \n\n" +
			"2 – No menu do celular, selecione a opção PREVIEW, o sistema irá mostrar todos os exercícios que compõe o treino. \n\n" +
			"3 – Também no menu do celular clique em REALIZAR,  o aplicativo irá mostrar uma tela com todas as séries listadas. \n\n" +
			"4 – Caso queria alterar ou inserir o valor da carga, dê um clique longo na série a ser executada. \n\n" +
			"5 – Você também pode verificar o passo a passo dos exercícios, acessando o menu do celular " +
			"e clicando em PASSO A PASSO. \n\n" +
			"6 – Para finalizar uma série, basta selecionar as desejadas e clicar em FINALIZAR SÉRIE. \n\n" +
			"7 – Você também pode finalizar o treino inteiro, selecionando a opção FINALIZAR TREINO. \n\n" +
			"- Como visualizar o histórico? \n\n" +
			"1 – Vá até a aba HISTÓRICO. \n\n" +
			"2 – O aplicativo irá mostrar a data que foi realizada os treinos, separados por mês. \n\n"),
			
	FICHA("Nessa tela você irá criar suas fichas, colocando seus treinos desejados formando séries dos exercícios " +
			"selecionados. \n\n\n" +
			"- Como adicionar uma ficha? \n\n" +
			"1 – Ao acessar a tela ficha, clique no menu do celular, e selecione a opção ADICIONAR. \n\n" +
			"2 – Irá abrir uma tela com os campos ficha , duração de treinos , objetivo e Alerta " +
			"(Para avisar que sua ficha está terminando ). \n\n" +
			"3 – Preencha esses dados e salve a ficha. \n\n" +
			"4 – Na aba treino selecione a opção NOVO TREINO. \n\n" +
			"5 – Uma tela irá aparecer para você digitar o nome do treino. \n\n" +
			"6 – Coloque o nome do treino e confirme. \n\n" +
			"7 – Você também possui a opção TREINO EXISTENTE podendo incluir um treino de outra ficha," +
			" na ficha que você está montando. \n\n" +
			"8 – Agora iremos montar ás séries do treino criado, clique no treino criado, " +
			"em seguida irá abrir uma tela com três abas, BUSCA EXERCICIOS e SÉRIE. \n\n" +
			"9 – Para buscar os exercícios, selecione a aba BUSCA e o grupo muscular desejado. \n\n" +
			"10 – Para adicionar os exercícios no treino criado, dê um clique longo. \n\n" +
			"11 – Este exercício, irá para a aba Exercícios, para remover basta selecionar " +
			" e com o menu do selecionar clicar na opção EXCLUIR. \n\n" +
			"12 – Para adicionar as séries desse exercício, dê um clique longo. \n\n" +
			"13 – Você verá uma janela com tela com as opções série, carga e unidade. \n\n" +
			"13 – Preencha os campos e confirme. \n\n" +
			"14 – Conforme o número de séries preenchidas você vera na aba SERIE as séries a incluídas. \n\n" +
			"- Como excluir uma ficha ?" +
			"1 – Na tela inicial FICHA , aparecerão todas as fichas existentes." +
			"2 – Selecione as fichas que você deseja excluir, e com o menu do celular, clique na opção REMOVER." +
			"- Como mudar de ficha ? \n\n" +
			"1 – Na tela inicial de ficha abra o menu do celular e seleciona opção MUDAR FICHA ATUAL, " +
			"selecione a ficha desejada. \n\n" +
			"2 – Para visualizar as informações dessa ficha, basta selecionar a opção Dados ficha atual," +
			" e o sistema irá exibir o nome, objetivo, número de treino, e execuções da ficha desejada. \n\n"),
	
	
	MEDIDA("Nessa tela você irá inserir suas medidas divididas entre grupos (Principais / Superiores/ Inferiores)." +
			" Ao clicar no menu NOVO , você irá inserir dados na data do dia, ao clicar no menu ALTERAR, " +
			"você irá mudar dados da última medida inserida, podendo salvar ou cancelar ambas opções. \n\n\n" +
			"- Como inserir uma nova medida? \n\n" +
			"1 – Selecione a opção medidas no menu principal. \n\n" +
			"2 – No menu do celular, selecione a opção NOVO. \n\n" +
			"3 – O sistema irá exibir a data atual, e cadastrar todos dados inseridos na data apresentada. \n\n" +
			"4 – Depois de inserir os valores desejados, clique em SALVAR. \n\n" +
			"- Como alterar uma medida cadastrada ? \n\n" +
			"1 - No menu do celular, selecione a opção ALTERAR. \n\n" +
			"2 – Todos dados inseridos, irão modificar a ultima data cadastrada. \n\n" +
			"3 – Depois de inserir os valores desejados, clique em SALVAR. \n\n"),
			
			
	EVOLUCAO("Na tela Evolução, você irá acompanhas o desenvolvimento das suas medidas inseridas, contendo 3 barras" +
			" de progresso com as últimas datas inseridas. Também existe a aba HISTÓRICO, com todas as medidas," +
			" mostrando seu desenvolvimento. \n\n\n" +
			"- Como visualizar a evolução? \n\n" +
			"1 – Depois de inseridas as medidas corporais, acesse a opção EVOLUÇÃO no menu principal. \n\n" +
			"2 – Na aba EVOLUÇÃO você verá suas últimas 3 últimos valores de cada medida inserida, mostrando" +
			" se houve ou não uma evolução. \n\n" +
			"3 – Na aba HISTÓRICO você terá todas as medidas inseridas, separadas por meses, com ilustrações" +
			" mostrando se houve ou não evolução comparada com a anterior. \n\n"),
	;
	
	
	private Ajuda(String a){
		ajuda = a;
	}
	
	private String ajuda;
	
	public String getAjuda(){
		return ajuda;
	}

}
