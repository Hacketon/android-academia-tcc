package workoutsystem.control;

import workoutsystem.dao.IPerfilDao;
import workoutsystem.dao.IUsuarioDao;
import workoutsystem.dao.PerfilDao;
import workoutsystem.dao.UsuarioDao;
import workoutsystem.model.Perfil;
import workoutsystem.model.Usuario;
import workoutsystem.view.GUIManipularPerfil;

public class ControlePerfil {

	
	public String cadastrasPerfil(Perfil perfil){
		String mensagem = "erro ao cadastrar Perfil";
		IPerfilDao dao = new PerfilDao();
		if(dao.criarPerfil(perfil)){
			mensagem = "Criado com sucesso";
		}else {
			return mensagem;
		}
		return mensagem;
	}
	
	public void instanciarPerfil(){
		
		IPerfilDao dao = new PerfilDao();
		GUIManipularPerfil manipulaperfil = new GUIManipularPerfil();
		Perfil perfil = dao.buscarPerfil();
		manipulaperfil.carregarPerfil(perfil);
		
	}
}
