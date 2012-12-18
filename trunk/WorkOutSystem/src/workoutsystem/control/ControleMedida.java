package workoutsystem.control;

import workoutsystem.dao.MedidaDao;
import workoutsystem.interfaces.IMedidaDao;
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
				mensagem = " adicionada com sucesso";
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
	
	public Double buscarValorMedicao(int codigo){
		Double valor = null ;
		Medicao medicao = new Medicao();
		IMedidaDao dao = new MedidaDao();
		
		if(dao.buscarValorMedicao(codigo)!= null){
			medicao = dao.buscarValorMedicao(codigo);
			valor = medicao.getValor();
		}
		
		return valor;
	}
	
}