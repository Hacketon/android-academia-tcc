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
	
	
	public Medicao buscarMedicao(int codigo){
		
		Medicao medicao = new Medicao();
		IMedidaDao dao = new MedidaDao();
		
		if(dao.buscarMedicao(codigo)!= null){
			medicao = dao.buscarMedicao(codigo);
			
		}
		
		return medicao;
	}
	
	
	public Double buscarValor(int codigo){
		Double valor = null ;
		IMedidaDao dao = new MedidaDao();
		
		if(dao.buscarValorMedicao(codigo)!= null){
			valor = dao.buscarValorMedicao(codigo);
		
		}
		
		return valor;
	}
	
}
