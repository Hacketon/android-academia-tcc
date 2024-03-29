package workoutsystem.utilitaria;

import java.util.List;
import java.util.Set;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import net.sf.oval.internal.util.Assert;
public class Validadora<T> {

	private T objeto;
	private String error;

	
	public Validadora(T objeto) {
		this.objeto = objeto;
		this.error = "";
	}

	public boolean validarObjeto() {
		Validator validator = new Validator();
		List<ConstraintViolation> violations = validator.validate(this.objeto);

		boolean validador = true;

		if (violations.size() != 0 ){
			for (ConstraintViolation c : violations) {
				error += c.getMessage()+"\n";
			}
			validador = false;
		}

		return validador;
	}

	public String getMessage(){
		error = "";
		validarObjeto();
		return error;
	}
	/**
	 * metodo responsavel por retorno de um nome valido !
	 * @param comparacao = string de comparação 
	 * @param comparar = string de busca 
	 * @return nome valido;
	 */
	public static String compararNome(String comparacao,String comparar){
		if(comparacao.contains(comparar)){
			String nome = "t";
			long contador = Math.round(Math.random()*20000);
			comparar = nome + contador;
			compararNome(comparacao, comparar);
		}
		return comparar;
	}
	public static String verificarString(String valorOriginal) {
		int contador = 0;
		int espaco = 0;
		int vchar = 0;

		valorOriginal = valorOriginal.trim();
		
		String auxiliar = "";
		while (contador <valorOriginal.length()){
			String valor = valorOriginal.substring(contador,contador+1);
			char character = valor.charAt(vchar);
			if(Character.isSpaceChar(character)){
				espaco += 1;
			}

			if(espaco==1 && Character.isSpaceChar(character)){
				auxiliar += character; 
			}
			if(!Character.isSpaceChar(character)){
				espaco = 0;
				auxiliar += character; 
			}
			contador++;

		}
		return auxiliar;
	}

}
