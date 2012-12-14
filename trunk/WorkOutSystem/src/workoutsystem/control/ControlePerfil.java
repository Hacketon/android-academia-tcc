package workoutsystem.control;

import android.widget.Toast;
import workoutsystem.dao.PerfilDao;
import workoutsystem.dao.UsuarioDao;
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
		if(buscarPerfil() == null){
			if(dao.criarPerfil(perfil) && dao.frequenciaPerfil(perfil)){
				mensagem ="Criado com sucesso"; 
			}else{
				mensagem = atualizarPerfil(perfil);
			}
		}
		return mensagem;
	}

	public String atualizarPerfil(Perfil perfil){
		String mensagem = "Erro ao atualizar perfil";
		IPerfilDao dao = new PerfilDao();
		boolean verificar = false;
		if(dao.buscarPerfil() != null){
			if(dao.atualizarPerfil(perfil) && dao.frequenciaPerfil(perfil)){
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
		if(dao.excluirPerfil(perfil.getCodigo())){
			mensagem = "Excluido com sucesso";
		}
		return mensagem;
	}
}
