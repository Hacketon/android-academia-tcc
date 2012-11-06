package workoutsystem.utilitaria;

public enum Ajuda {


	Perfil("Ajuda Perfil"),
	Exercicio ("Ajuda Exercicio"),
	Rotina ("Ajuda Rotina"),
	Evolucao("Ajuda Evolucao"),
	Ficha ("Ajuda Ficha");


	private String ajuda;

	private Ajuda(String help){
		ajuda = help;
	}

	public String getAjuda(){
		return ajuda;
	}

}
