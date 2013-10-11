package workoutsystem.control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import workoutsystem.dao.FichaDao;
import workoutsystem.dao.IDiaSemana;
import workoutsystem.dao.IFichaDao;
import workoutsystem.dao.IPerfilDao;
import workoutsystem.dao.PerfilDao;
import workoutsystem.model.Frequencia;
import workoutsystem.model.Perfil;
import workoutsystem.utilitaria.Validadora;

public class ControlePerfil {


	public String cadastrarPerfil(Perfil perfil) throws Exception{

		IPerfilDao dao = new PerfilDao();
		ControleFicha controle = new ControleFicha();
		Perfil p = new Perfil();
		p = buscarPerfil();
		Validadora<Perfil> validadora = new Validadora<Perfil>(perfil);
		String mensagem = validadora.getMessage();
		if (mensagem.equalsIgnoreCase("")){
			if(p == null){
				if(dao.criarPerfil(perfil) ){
					p = buscarPerfil();
					if(dao.frequenciaPerfil(p , perfil)){
						mensagem ="Perfil : criado com sucesso";
					}
				}
			}else{
				throw new Exception(mensagem);
			}

		}else{
			throw new Exception(mensagem);
		}

		return mensagem;
	}

	public String atualizarPerfil(Perfil perfil) throws Exception{
		IPerfilDao dao = new PerfilDao();
		Validadora<Perfil> validadora = new Validadora<Perfil>(perfil);
		String mensagem = validadora.getMessage();
		if (mensagem.equalsIgnoreCase("")){
			if(buscarPerfil() != null){
				if(dao.atualizarPerfil(perfil)&& dao.frequenciaPerfilAtualiza(perfil)){
					mensagem ="Perfil : atualizado com sucesso"; 
				}
			}
		}


		return mensagem;
	}

	public Perfil buscarPerfil(){
		IPerfilDao dao = new PerfilDao();
		Perfil perfil = dao.buscarPerfil();
		return perfil;

	}

	public String excluirPerfil(Perfil perfil) throws SQLException{
		String mensagem = "";
		IPerfilDao dao = new PerfilDao();
		IFichaDao daoficha = new FichaDao();
		daoficha.desativarFichaAtual();
		if(dao.excluirPerfil()&& dao.excluirFrequencia(perfil)){
			mensagem = "Perfil : excluido com sucesso";

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

