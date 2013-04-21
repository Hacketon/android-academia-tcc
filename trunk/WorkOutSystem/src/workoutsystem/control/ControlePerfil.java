package workoutsystem.control;

import java.util.ArrayList;
import java.util.List;

import workoutsystem.dao.FichaDao;
import workoutsystem.dao.PerfilDao;
import workoutsystem.interfaces.IDiaSemana;
import workoutsystem.interfaces.IPerfilDao;
import workoutsystem.model.Frequencia;
import workoutsystem.model.Perfil;

public class ControlePerfil {


	public String cadastrarPerfil(Perfil perfil){
		String mensagem = "Erro ao cadastrar Perfil";
		IPerfilDao dao = new PerfilDao();
		if(buscarPerfil() == null){
			if(dao.criarPerfil(perfil)){
				if(dao.frequenciaPerfil(perfil)){
					mensagem ="Criado com sucesso"; 

				}

			}
		}


		return mensagem;
	}

	public String atualizarPerfil(Perfil perfil){
		String mensagem = "Erro ao atualizar perfil";
		IPerfilDao dao = new PerfilDao();
		if(buscarPerfil() != null){
			if(dao.atualizarPerfil(perfil)&& dao.frequenciaPerfil(perfil)){

				mensagem ="Atualizado com sucesso"; 

			}
		}

		return mensagem;
	}

	public Perfil buscarPerfil(){
		IPerfilDao dao = new PerfilDao();
		
		Perfil perfil = dao.buscarPerfil();
		return perfil;

	}

	public String excluirPerfil(Perfil perfil){
		String mensagem = "Erro ao excluir";
		IPerfilDao dao = new PerfilDao();
		if(dao.excluirPerfil()&& dao.excluirFrequencia(perfil)){
			mensagem = "Excluido com sucesso";
		}
		return mensagem;
	}

	public int codigoFrequencia(String Nome){
		int codigo = 0;
		IDiaSemana dao = new FichaDao();
		if(dao.buscarCodigoDia(Nome) != 0){
			codigo = dao.buscarCodigoDia(Nome);
		}
		return codigo;
	}



	public List<Frequencia> buscarFrequencia(Perfil perfil){
		List<Frequencia> dias = new ArrayList<Frequencia>(); 
		IPerfilDao dao = new PerfilDao();

		if(dao.buscarFrequencia(perfil)!= null){
			dias = dao.buscarFrequencia(perfil);
		}else{
			dias = null;
		}


		return dias;

	}
	public int quantidadeDias(Perfil perfil){
		int qtd = 0;
		IPerfilDao dao = new PerfilDao();
		if(dao.quantidadeDias(perfil)!= 0){
			qtd = dao.quantidadeDias(perfil);
		}
		return qtd;
	}
}

