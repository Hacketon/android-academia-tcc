package workoutsystem.control;

import java.util.ArrayList;
import java.util.List;

import workoutsystem.dao.MedidaDao;
import workoutsystem.interfaces.IMedidaDao;
import workoutsystem.model.Medida;

public class ControleEvolucao {

	public List<Medida> buscarMedidas(){
		List<Medida> lista = new ArrayList<Medida>();
		IMedidaDao daoMed = new MedidaDao();
		
		if(daoMed.buscarMedidas() != null){
			lista = daoMed.buscarMedidas();
		}else{
			return null;
		}
		
		return lista;
	}
	
	
}
