package workoutsystem.control;

import java.util.List;

import workoutsystem.dao.FichaDao;
import workoutsystem.model.DiaSemana;

public class ControleFicha {

	public List<DiaSemana> listarDias() {
		return new FichaDao().listarDias();
	}

}
