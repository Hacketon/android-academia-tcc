package workoutsystem.control;

import java.util.ArrayList;
import java.util.List;

import workoutsystem.dao.MedidaDao;
import workoutsystem.interfaces.IMedidaDao;
import workoutsystem.model.Medicao;
import workoutsystem.model.Medida;

public class ControleMedida {
	
	
	//corrigir codigo
	public String adicionarMedicao(List<Medicao> medicoes){
		String mensagem = "  Erro ao adicionar Medicao";
		IMedidaDao dao = new MedidaDao();
		
		if(medicoes != null){
			//verificar codigo
			
			if(dao.adicionarMedicao(medicoes)){
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
	
	
	public List<Medicao> buscarMedicao(int codigo){
		List<Medicao> lista = new ArrayList<Medicao>();
		IMedidaDao dao = new MedidaDao();
		
		if(dao.buscarMedicao(codigo)!= null){
			lista  = dao.buscarMedicao(codigo);
			
		}
		
		return lista;
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
