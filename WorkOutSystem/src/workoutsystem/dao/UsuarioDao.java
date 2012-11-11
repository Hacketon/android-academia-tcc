package workoutsystem.dao;

import workoutsystem.model.Usuario;

public class UsuarioDao implements IUsuarioDao {

	@Override
	public Usuario buscarUsuario(Usuario u) {
		Usuario usuario = new Usuario();
		usuario.setNome("usuario");
		usuario.setSenha("123");
		return usuario;
	}

	@Override
	public void cadastrarUsuario(Usuario u) {
		// implementar
		
	}


}
