package workoutsystem.control;

import java.util.List;
import java.util.Set;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
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

}
