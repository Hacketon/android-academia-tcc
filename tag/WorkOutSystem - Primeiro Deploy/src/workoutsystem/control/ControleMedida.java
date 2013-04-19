package workoutsystem.control;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import workoutsystem.dao.MedidaDao;
import workoutsystem.interfaces.IMedidaDao;
import workoutsystem.model.Medicao;
import workoutsystem.model.Medida;

public class ControleMedida implements Comparator<Medicao> {
	
	
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
	
	public int buscarMedida(String nome, String lado){
		int codigo = 0;
		IMedidaDao dao = new MedidaDao();
		
		if(dao.buscarMedida(nome, lado)!= 0){
			codigo = dao.buscarMedida(nome, lado);
		}
		return codigo;
	}
	
	
	public List<Medicao> buscarMedicao(int codigo){
		IMedidaDao dao = new MedidaDao();
		return  dao.buscarMedicao(codigo);
	}
	
	
	
	public Double buscarValor(int codigo){
		Double valor = null ;
		IMedidaDao dao = new MedidaDao();
		
		if(dao.buscarValorMedicao(codigo)!= null){
			valor = dao.buscarValorMedicao(codigo);
		
		}
		
		return valor;
	}
	
	public String alterarMedicoes(List<Medicao> medicoes){
		String mensagem = "Erro ao atualizar";
		IMedidaDao dao = new MedidaDao();
		
		if(dao.alterarMedicao(medicoes)== true){
			mensagem = "Atualizado com sucesso";
		}		
		
		return mensagem;
	}
	
	public String alterarUltimasMedicoes(List<Medicao> medicoes){
		String mensagem = "Erro ao atualizar";
		IMedidaDao dao = new MedidaDao();
		
		if(dao.alterarUltimaMedicao(medicoes)!= false){
			mensagem = "Atualizado com sucesso";
		}		
		
		return mensagem;
	}
	public String excluirMedicoes(int codigo){
		String mensagem = "Erro ao excluir Medicões! ";
		IMedidaDao dao = new MedidaDao();
		
		if(dao.excluirMedicoes(codigo)== true){
			mensagem = "Todas Medições foram excluídas! ";
		}
		return mensagem;
	}

	public boolean verificarMedicao(int codigo) {
		IMedidaDao dao = new  MedidaDao();
		return dao.verificarMedicao(codigo);
		
	}
	
	public List<Medicao> ultimasMedicoes(int codigoPerfil,int codigoMedida){
		IMedidaDao dao = new  MedidaDao();
		return dao.ultimasMedicoes(codigoPerfil, codigoMedida);
		
	}

	@Override
	public int compare(Medicao m1, Medicao m2) {
		int retorno; 
		if (m1.getValor() > m2.getValor()){
			retorno =  1;
		}else if (m1.getValor() == m2.getValor()){
			retorno =  0;
		}else {
			retorno =  -1;
		}
		
		return retorno ;
		
		
	}

	public List<Medida> ultimaMedicao(int codigo) {
		IMedidaDao dao = new MedidaDao();
		return dao.ultimaMedicao(codigo);
	}

	
	
//	double retorno = 0.0;
//	if (m1.getValor() > m2.getValor()){
//		retorno = m1.getValor();
//	}else if (m1.getValor() == m1.getValor()){
//		retorno = m1.getValor();
//	}else{
//		retorno = m2.getValor();
//	}
}
