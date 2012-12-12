package workoutsystem.control;

import workoutsystem.dao.IMedidaDao;
import workoutsystem.dao.MedidaDao;
import workoutsystem.model.Medicao;
import workoutsystem.model.Medida;

public class ControleMedida {
	
	
	//corrigir codigo
	public String adicionarMedicao(Medicao medicao){
		String mensagem = "  Erro ao adicionar Medicao";
		IMedidaDao dao = new MedidaDao();
		
		if(medicao != null){
			//verificar codigo
			
			if(dao.adicionarMedicao(medicao)){
				mensagem = "Medidas adicionadas com sucesso";
			}
		}

		return mensagem;	
	}
	
	public int buscarMedida(String nome, String lado){
		int codigo = 0;
		IMedidaDao dao = new MedidaDao();
		
		if(dao.buscarMedida(nome, lado)!= 0){
			codigo = dao.buscarMedida(nome, lado);
		}
		return codigo;
	}
}
