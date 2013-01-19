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
		List<Medicao> lista = dao.buscarMedicao(codigo);
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
	
	public List<Medicao> ultimasMedicoes(int codigoPerfil,int codigoMedicao){
		return null;
	}
}
