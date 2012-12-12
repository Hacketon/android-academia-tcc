package workoutsystem.control;

import android.widget.Toast;
import workoutsystem.dao.IPerfilDao;
import workoutsystem.dao.IUsuarioDao;
import workoutsystem.dao.PerfilDao;
import workoutsystem.dao.UsuarioDao;
import workoutsystem.model.Perfil;
import workoutsystem.model.Usuario;
import workoutsystem.view.GUIManipularPerfil;

public class ControlePerfil {

	
	public String cadastrasPerfil(Perfil perfil){
		String mensagem = "Erro ao cadastrar Perfil";
		IPerfilDao dao = new PerfilDao();
		if(dao.criarPerfil(perfil)){
			mensagem = "Criado com sucesso";
		}else {
			return mensagem;
		}
		return mensagem;
	}
	
	public Perfil buscarPerfil(){
		IPerfilDao dao = new PerfilDao();
		Perfil perfil = null;
		
		if(dao.buscarPerfil()!= null){
			 perfil = dao.buscarPerfil();
		}
		
		return perfil;
		
	}
	
	public String excluirPerfil(){
		String mensagem = "erro ao excluir";
		IPerfilDao dao = new PerfilDao();
		
		if(dao.excluirPerfil()){
			mensagem = "Excluido com sucesso";
		}
		
		return mensagem;
	}
}
