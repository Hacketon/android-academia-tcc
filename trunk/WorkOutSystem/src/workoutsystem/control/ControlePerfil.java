package workoutsystem.control;

import android.widget.Toast;
import workoutsystem.dao.FichaDao;
import workoutsystem.dao.PerfilDao;
import workoutsystem.dao.UsuarioDao;
import workoutsystem.interfaces.IDiaSemana;
import workoutsystem.interfaces.IFichaDao;
import workoutsystem.interfaces.IPerfilDao;
import workoutsystem.interfaces.IUsuarioDao;
import workoutsystem.model.Perfil;
import workoutsystem.model.Usuario;
import workoutsystem.view.GUIManipularPerfil;

public class ControlePerfil {


	public String cadastrarPerfil(Perfil perfil){
		String mensagem = "Erro ao cadastrar Perfil";
		IPerfilDao dao = new PerfilDao();
		boolean verificar = false;
		ControleUsuario controle = new ControleUsuario();
		Usuario u = controle.buscarUsuario();
		if(buscarPerfil() == null){
			if (u != null){
				if(dao.criarPerfil(perfil,u) && dao.frequenciaPerfil(perfil)){
				//if(dao.criarPerfil(perfil,u)){
					mensagem ="Criado com sucesso"; 
				}
			}
			
		}
		return mensagem;
	}

	public String atualizarPerfil(Perfil perfil){
		String mensagem = "Erro ao atualizar perfil";
		IPerfilDao dao = new PerfilDao();
		ControleUsuario controle = new ControleUsuario();
		boolean verificar = false;
		Usuario u = controle.buscarUsuario();
		if (u!= null){
			if(dao.buscarPerfil(u) != null){
				if(dao.atualizarPerfil(perfil,u) && dao.frequenciaPerfil(perfil)){
				//if(dao.atualizarPerfil(perfil,u)){
					mensagem ="Atualizado com sucesso"; 
				}
			}
		}
		
		return mensagem;
	}

	public Perfil buscarPerfil(){
		IPerfilDao dao = new PerfilDao();
		Perfil perfil = null;
		ControleUsuario controle = new ControleUsuario();
		Usuario u = controle.buscarUsuario();
		if (u != null){
			perfil = dao.buscarPerfil(u);
		}
		
		return perfil;

	}

	public String excluirPerfil(Usuario usuario){
		String mensagem = "Erro ao excluir";
		IPerfilDao dao = new PerfilDao();
		if(dao.excluirPerfil(usuario.getCodigo())){
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
	
	
}
