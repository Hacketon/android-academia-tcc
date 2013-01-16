package workoutsystem.control;

import java.util.ArrayList;
import java.util.List;

import workoutsystem.dao.MedidaDao;
import workoutsystem.interfaces.IMedidaDao;

public class ControleEvolucao {

	public List<String> buscarMedidas(){
		List<String> lista = new ArrayList<String>();
		IMedidaDao daoMed = new MedidaDao();
		
		if(daoMed.buscarMedidas() != null){
			lista = daoMed.buscarMedidas();
		}else{
			return null;
		}
		
		return lista;
	}
	
	
}
