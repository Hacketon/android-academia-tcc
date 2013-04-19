package workoutsystem.control;

import java.util.List;

import workoutsystem.dao.FichaDao;
import workoutsystem.model.Frequencia;

public class ControleFicha {

	public List<Frequencia> listarDias() {
		return new FichaDao().listarDias();
	}

}
