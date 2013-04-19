package workoutsystem.utilitaria;

import java.util.List;
import java.util.Set;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import net.sf.oval.internal.util.Assert;
public class Validadora<T> {

	private T objeto;

	public Validadora(T objeto) {
		this.objeto = objeto;
	}

	public boolean validarObjeto() {
		Validator validator = new Validator();
		List<ConstraintViolation> violations = validator.validate(this.objeto);

		boolean validador = true;

		if (violations.size() != 0 ){
			validador = false;
		}

		return validador;
	}

	public String getMessage(){
		Validator validator = new Validator();
		List<ConstraintViolation> violations = validator.validate(this.objeto);
		String mensagem = "";
		for (ConstraintViolation c : violations) {
			mensagem += c.getMessage();
		}

		return mensagem;

	}
}
