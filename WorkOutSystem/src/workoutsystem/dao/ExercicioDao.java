package workoutsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import workoutsystem.model.Serie;
import workoutsystem.model.Exercicio;
import workoutsystem.model.Grupo;
import workoutsystem.model.Passo;


public class ExercicioDao implements IExercicioDao {

	@Override
	public boolean adicionarExercicio(Exercicio e) throws SQLException {
		boolean verificador;

		Connection con = ResourceManager.getConexao();
		String sql = "insert into exercicio (nome, descricao, padrao,ativo,codigogrupo)" +
		" values (?,?,?,?,?) ";

		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setString(1, e.getNome().trim());
		prepare.setString(2, e.getDescricao().trim());
		prepare.setInt(3, e.getPadrao());
		prepare.setInt(4, e.getAtivo());
		prepare.setInt(5, e.getGrupo().getCodigo());


		if(prepare.executeUpdate()!=0){
			verificador = true;
		}else{
			verificador = false;
		}
		prepare.close();
		con.close();
		return verificador;

	}


	@Override
	public boolean alterarExercicio(long codigo, Exercicio e) throws SQLException {
		boolean verificador = false;
		Connection con = ResourceManager.getConexao();
		String sql = "update exercicio set " +
		"  nome = ? , descricao = ? , codigogrupo = ? " +
		" where codigo = ?";
		PreparedStatement prepared = con.prepareStatement(sql);
		prepared.setString(1, e.getNome());
		prepared.setString(2, e.getDescricao());
		prepared.setInt(3, e.getGrupo().getCodigo());
		prepared.setLong(4, codigo);
		int resultado = prepared.executeUpdate();
		if (resultado > 0 ){
			verificador = true;
		}

		prepared.close();
		con.close();

		return verificador;
	}


	@Override
	public boolean excluirExercicio(long codigo) throws SQLException {
		boolean verificador = false;

		Connection con = ResourceManager.getConexao(); 
		String sql = "update exercicio set ativo = 0 where codigo = ?";
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setLong(1, codigo);
		int resultado = prepare.executeUpdate();

		if (resultado > 0 ){
			verificador = true;
		}else{
			verificador = false;
		}

		con.close();
		prepare.close();
		return verificador;
	}

	@Override
	public List<Passo> visualizarPassos(Exercicio exercicio) throws SQLException {
		List<Passo> passos = null;

		Connection con = ResourceManager.getConexao();
		String sql = "select passo.sequencia,passo.explicacao, passo.imagem" +
		" from passo inner join exercicio " +
		" on passo.codigoexercicio = exercicio.codigo" +
		" where exercicio.codigo = ?";
		PreparedStatement prepared = con.prepareStatement(sql);
		prepared.setLong(1, exercicio.getCodigo());
		ResultSet result = prepared.executeQuery();
		passos = new ArrayList<Passo>();
		while (result.next()) {
			Passo p = new Passo();
			p.setSequencia(result.getInt(1));
			p.setExplicacao(result.getString(2));
			p.setImagem(result.getString(3));
			passos.add(p);
		}
		prepared.close();
		con.close();

		return passos;

	}

	@Override
	public int buscarGrupo(String nome) throws SQLException {
		int codigo = 0;

		Connection con = ResourceManager.getConexao();
		String sql = "select grupo.codigo from grupo where " +
		" grupo.nome like ? ";
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setString(1, nome);
		ResultSet result = prepare.executeQuery();

		if (result.next()){
			codigo = result.getInt(1);
		}

		prepare.close();
		con.close();


		return codigo;
	}

	public List<Grupo> listarGrupos() {
		List<Grupo> lista = null;
		try{
			Connection con = ResourceManager.getConexao();
			String sql = "select codigo,nome from grupo";
			PreparedStatement prepare = con.prepareStatement(sql);
			ResultSet result = prepare.executeQuery();
			lista = new ArrayList<Grupo>();

			while (result.next()){
				Grupo grupo = new Grupo();
				grupo.setCodigo(result.getInt(1));
				grupo.setNome(result.getString(2));
				lista.add(grupo);
			}

			prepare.close();
			con.close();

		}catch(SQLException e) {
			Log.e ("SQL",e.getMessage());
		}
		return lista;

	}


	@Override
	public boolean buscarExercicio(String nome,long codigo){
		boolean verificador = false;
		try{
			Connection con = ResourceManager.getConexao();
			String sql = "select * from exercicio where nome like ? and codigo != ?";
			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setString(1,nome);
			prepare.setLong(2,codigo);
			ResultSet result = prepare.executeQuery();
			if (result.next()){
				verificador = true;
			}

			prepare.close();
			con.close();
		}catch (SQLException e) {

		}


		return verificador;
	}

	@Override
	public Exercicio buscarExercicio(String nome) {
		Exercicio exercicio = null;
		try{
			Connection con = ResourceManager.getConexao();
			String sql = "select codigo,nome,descricao,padrao,codigogrupo" +
			" from exercicio where nome like ?";
			PreparedStatement prepared = con.prepareStatement(sql);
			prepared.setString(1, nome);
			ResultSet result = prepared.executeQuery();
			if(result.next()){
				exercicio = new Exercicio();
				exercicio.setCodigo(result.getInt(1));
				exercicio.setNome(result.getString(2));
				exercicio.setDescricao(result.getString(3));
				exercicio.setPadrao(result.getInt(4));
				Grupo grupo = new Grupo();
				grupo.setCodigo(result.getInt(5));
				grupo.setNome(buscarGrupo(grupo.getCodigo()));
				exercicio.setListaPassos(visualizarPassos(exercicio));
				exercicio.setGrupo(grupo);
			}
			prepared.close();
			con.close();
		}catch (SQLException e) {

		}
		return exercicio;
	}



	@Override
	public List<Exercicio> listarExercicios(int codigogrupo,int personalizado) {
		List<Exercicio> lista = null;
		try{
			Connection con = ResourceManager.getConexao();
			String sql = "select exercicio.codigo,exercicio.nome, " +
			"exercicio.descricao,exercicio.padrao, " +
			"exercicio.codigogrupo,grupo.nome " +
			"from exercicio inner join grupo " +
			"on exercicio.codigogrupo = grupo.codigo "+
			"where grupo.codigo like ? and exercicio.padrao = ? " +
			"and exercicio.ativo = 1";
			int aux = 1;
			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setInt(aux++, codigogrupo);
			prepare.setInt(aux++, personalizado);
			ResultSet resultSet = prepare.executeQuery();
			lista = new ArrayList<Exercicio>();
			while (resultSet.next()){
				Exercicio exercicio = new Exercicio();
				exercicio.setCodigo(resultSet.getInt(1));
				exercicio.setNome(resultSet.getString(2));
				exercicio.setDescricao(resultSet.getString(3));
				exercicio.setPadrao(resultSet.getInt(4));
				Grupo grupo1 = new Grupo();
				grupo1.setCodigo(resultSet.getInt(5));
				grupo1.setNome(resultSet.getString(6));
				exercicio.setGrupo(grupo1);
				exercicio.setListaPassos(visualizarPassos(exercicio));
				lista.add(exercicio);
			}

			prepare.close();
			con.close();
		}catch (SQLException e) {
			// TODO: handle exception
		}
		return lista;
	}




	@Override
	public Exercicio buscarExercicioGrupo(Grupo grupo) {
		Exercicio exercicio = new Exercicio();
		Grupo grupoResult = new Grupo();
		try{
			Connection con = ResourceManager.getConexao();
			String sql = "select nome, descricao, codigogrupo, padrao from exercicio " +
			" where codigogrupo = ?;";
			PreparedStatement prepare = con.prepareStatement(sql);	
			prepare.setInt(1, exercicio.getGrupo().getCodigo());
			ResultSet result  = prepare.executeQuery();


			//Verificar se est� correto 
			if(result.next()){
				exercicio.setNome(result.getString(1));
				exercicio.setDescricao(result.getString(2));
				//verificar est� parte
				grupoResult.setCodigo(result.getInt(3));
				exercicio.setGrupo(grupoResult);
				//
				exercicio.setPadrao(result.getInt(4));
			}

			prepare.close();
			result.close();

		}catch (Exception e) {
			Log.e ("SQL",e.getMessage());
		}

		return exercicio;
	}


	@Override
	public boolean buscarExercicio (long codigo) {
		boolean verificador = false;
		try{
			Connection con = ResourceManager.getConexao();
			String sql = "select * from exercicio where codigo = ?";
			PreparedStatement prepared = con.prepareStatement(sql);
			prepared.setLong(1, codigo);
			ResultSet result = prepared.executeQuery();

			if (result.next()){
				verificador = true;
			}
			prepared.close();
			con.close();
		}catch(SQLException ex){

		}
		return verificador;
	}


	



	@Override
	public String buscarGrupo(int codigo) throws SQLException {
		String nome= "";

		Connection con = ResourceManager.getConexao();
		String sql = "select grupo.nome from grupo where " +
		" grupo.codigo like ? ";
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setInt(1, codigo);
		ResultSet result = prepare.executeQuery();

		if (result.next()){
			nome = result.getString(1);
		}

		prepare.close();
		con.close();


		return nome;
	}


	@Override
	public boolean reativarExercicio(String nomeExercicio, int i) throws SQLException {
		boolean retorno = false;;
		String sql = "update exercicio set ativo = 1 where nome like ? " +
		" and ativo = ?";
		Connection con = ResourceManager.getConexao();
		PreparedStatement prepared = con.prepareStatement(sql);
		int codigo = 1 ; 
		prepared.setString(codigo++, nomeExercicio);
		prepared.setInt(codigo++,i);

		codigo = prepared.executeUpdate();
		if (codigo > 0){
			retorno = true;
		}

		prepared.close();
		con.close();
		return retorno;

	}


	@Override
	public List<Exercicio> listarExercicioTreino (long codigoTreino)
	throws SQLException {
		int aux = 1;
		Connection con = ResourceManager.getConexao();
		String sql = 
			" select distinct exercicio_codigo," +
			" exercicio_nome, exercicio_descricao," +
			" exercicio_padrao,exercicio_ativo,grupo_codigo,grupo_nome" +
			" from [exercicio_treino_ficha] " +
			" where [treino_codigo] = ?";
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setLong(aux++, codigoTreino);
		ResultSet result = prepare.executeQuery();
		List<Exercicio> list = new ArrayList<Exercicio>();

		while (result.next()){
			Exercicio e = new Exercicio();
			Grupo g = new Grupo();
			aux = 1;
			e.setCodigo(result.getLong("exercicio_codigo"));
			e.setNome(result.getString("exercicio_nome"));
			e.setDescricao(result.getString("exercicio_descricao"));
			e.setPadrao(result.getInt("exercicio_padrao"));
			e.setAtivo(result.getInt("exercicio_ativo"));
			g.setCodigo(result.getInt("grupo_codigo"));
			g.setNome(result.getString("grupo_nome"));
			e.setGrupo(g);

			list.add(e);
		}
		prepare.close();
		con.close();
		return list;
	}


	@Override
	public List<Exercicio> buscarExercicioTreino(int codigoGrupo,
			int codigoAtivo) throws SQLException {
		int aux = 1;
		Connection con = ResourceManager.getConexao();

		String sql = " select exercicio_codigo,exercicio_nome,exercicio_descricao, " +
		" exercicio_padrao,exercicio_ativo,grupo_codigo,grupo_nome " + 
		" from [exercicio_fora_treino] " +
		" where serie_treino is null " +
		" and serie_exercicio is null " +
		" and exercicio_grupo = ? and exercicio_ativo = ? ";

		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setInt(aux++, codigoGrupo);
		prepare.setInt(aux++, codigoAtivo);

		ResultSet result = prepare.executeQuery();
		List<Exercicio> lista = new ArrayList<Exercicio>();
		while (result.next()) {
			aux = 1;
			Exercicio e = new Exercicio();
			Grupo g = new Grupo();
			e.setCodigo(result.getLong(aux++));
			e.setNome(result.getString(aux++));
			e.setDescricao(result.getString(aux++));
			e.setPadrao(result.getInt(aux++));
			e.setAtivo(result.getInt(aux++));

			g.setCodigo(result.getInt(aux++));
			g.setNome(result.getString(aux++));

			e.setGrupo(g);

			lista.add(e);

		}


		prepare.close();
		con.close();
		return lista;
	}


	@Override
	public List<Exercicio> listarExercicioFora(long codigoTreino,long codigoGrupo)
	throws SQLException {
		int aux = 1;
		String sql = " select distinct exercicio_codigo,exercicio_nome, " +
		" exercicio_padrao,exercicio_ativo, " +
		" exercicio_descricao,grupo_codigo,grupo_nome " +
		" from exercicio_fora_treino " +
		" where treino_codigo != ? and grupo_codigo = ?";
		Connection con = ResourceManager.getConexao();
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setLong(aux++, codigoTreino);
		prepare.setLong(aux++, codigoGrupo);
		ResultSet result = prepare.executeQuery();
		List<Exercicio> list = new ArrayList<Exercicio>();

		while (result.next()){
			Exercicio e = new Exercicio();
			e.setCodigo(result.getLong("exercicio_codigo"));
			e.setNome(result.getString("exercicio_nome"));
			e.setPadrao(result.getInt("exercicio_padrao"));
			e.setAtivo(result.getInt("exercicio_ativo"));
			e.setDescricao(result.getString("exercicio_descricao"));
			Grupo g = new Grupo();
			g.setCodigo(result.getInt("grupo_codigo"));
			g.setNome(result.getString("grupo_nome"));

			e.setGrupo(g);

			list.add(e);
		}

		prepare.close();
		con.close();
		return list;
	}
	
	
	
	public List<Exercicio> listarExercicioSemTreino(long codigoGrupo)
	throws SQLException {
		int aux = 1;
		String sql = " select distinct exercicio_codigo,exercicio_nome, " +
		" exercicio_padrao,exercicio_ativo, " +
		" exercicio_descricao,grupo_codigo,grupo_nome " +
		" from exercicio_fora_treino " +
		" where treino_codigo is null and grupo_codigo = ?";
		Connection con = ResourceManager.getConexao();
		PreparedStatement prepare = con.prepareStatement(sql);
		
		prepare.setLong(aux++, codigoGrupo);
		ResultSet result = prepare.executeQuery();
		List<Exercicio> list = new ArrayList<Exercicio>();

		while (result.next()){
			Exercicio e = new Exercicio();
			e.setCodigo(result.getLong("exercicio_codigo"));
			e.setNome(result.getString("exercicio_nome"));
			e.setPadrao(result.getInt("exercicio_padrao"));
			e.setAtivo(result.getInt("exercicio_ativo"));
			e.setDescricao(result.getString("exercicio_descricao"));

			Grupo g = new Grupo();
			g.setCodigo(result.getInt("grupo_codigo"));
			g.setNome(result.getString("grupo_nome"));

			e.setGrupo(g);

			list.add(e);
		}

		prepare.close();
		con.close();
		return list;
	}


	@Override
	public List<Exercicio> buscarExercicioPasso(long codigoTreino)
			throws SQLException {
		int aux = 1;
		String sql = " select distinct exercicio.codigo as exercicio_codigo ," + 
					 " exercicio.nome as exercicio_nome ," +
					 " exercicio.padrao as exercicio_padrao ,"+   
					 " exercicio.ativo as exercicio_ativo , "+       
					 " exercicio.descricao as exercicio_descricao " +
					 " from exercicio inner join passo " +
					 " on exercicio.codigo = passo.codigoexercicio " +
					 " inner join serie "+
					 " on serie.codigoexercicio = exercicio.codigo " +
					 " inner join treino " +
					 " on treino.codigo = serie.codigotreino where treino.codigo = ? ";
		Connection con = ResourceManager.getConexao();
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setLong(aux++, codigoTreino);
		ResultSet result = prepare.executeQuery();
		List<Exercicio> lista = new ArrayList<Exercicio>();
		while (result.next()){
			Exercicio exercicio = new Exercicio();
			
			exercicio.setNome(result.getString("exercicio_nome"));
			exercicio.setCodigo(result.getLong("exercicio_codigo"));
			exercicio.setPadrao(result.getInt("exercicio_padrao"));
			exercicio.setAtivo(result.getInt("exercicio_ativo"));
			exercicio.setDescricao(result.getString("exercicio_descricao"));
			exercicio.setListaPassos(visualizarPassos(exercicio));
			
			lista.add(exercicio);
		}
		con.close();
		prepare.close();
		

		return lista;
	}






}