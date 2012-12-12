package workoutsystem.control;

import workoutsystem.dao.IMedidaDao;
import workoutsystem.dao.MedidaDao;
import workoutsystem.model.Medicao;
import workoutsystem.model.Medida;

public class ControleMedida {
	
	
	//corrigir codigo
	public String adicionarMedicao(Medicao medicao){
		String mensagem = "Erro ao adicionar Medicao";
		IMedidaDao dao = new MedidaDao();
		
		if(medicao != null){
			Medida medida = medicao.getCodigoMedida();
			//verificar codigo
			dao.buscarMedida(medida.getNome(), medida.getLado());
			if(dao.adicionarMedicao(medicao)){
				mensagem = "Medidas adicionadas com sucesso";
			}
		}

		return mensagem;	
	}
}
