package workoutsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import android.util.Log;
import workoutsystem.model.Especificacao;
import workoutsystem.model.Exercicio;
import workoutsystem.model.GrupoMuscular;
import workoutsystem.model.Passo;


public class ExercicioDao implements IExercicioDao {

	@Override
	public boolean adicionarExercicio(Exercicio e) throws SQLException {
		boolean verificador;

		Connection con = ResourceManager.getConexao();
		String sql = "insert into exercicio (nome, descricao, padrao,ativo,codigogrupomuscular)" +
		" values (?,?,?,?,?) ";

		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setString(1, e.getNome().trim());
		prepare.setString(2, e.getDescricao().trim());
		prepare.setInt(3, e.getPadrao());
		prepare.setInt(4, e.getAtivo());
		prepare.setInt(5, e.getGrupoMuscular().getCodigo());


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
		"  nome = ? , descricao = ? , codigogrupomuscular = ? " +
		" where codigo = ?";
		PreparedStatement prepared = con.prepareStatement(sql);
		prepared.setString(1, e.getNome());
		prepared.setString(2, e.getDescricao());
		prepared.setInt(3, e.getGrupoMuscular().getCodigo());
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
			p.setImagem(result.getInt(3));
			passos.add(p);
		}
		prepared.close();
		con.close();

		return passos;

	}

	@Override
	public int buscarGrupoMuscular(String nome) throws SQLException {
		int codigo = 0;

		Connection con = ResourceManager.getConexao();
		String sql = "select grupomuscular.codigo from grupomuscular where " +
		" grupomuscular.nome like ? ";
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

	public List<GrupoMuscular> listarGrupos() {
		List<GrupoMuscular> lista = null;
		try{
			Connection con = ResourceManager.getConexao();
			String sql = "select codigo,nome from grupomuscular";
			PreparedStatement prepare = con.prepareStatement(sql);
			ResultSet result = prepare.executeQuery();
			lista = new ArrayList<GrupoMuscular>();

			while (result.next()){
				GrupoMuscular grupo = new GrupoMuscular();
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
			String sql = "select codigo,nome,descricao,padrao,codigogrupomuscular" +
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
				GrupoMuscular grupo = new GrupoMuscular();
				grupo.setCodigo(result.getInt(5));
				grupo.setNome(buscarGrupoMuscular(grupo.getCodigo()));
				exercicio.setListaPassos(visualizarPassos(exercicio));
				exercicio.setGrupoMuscular(grupo);
			}
			prepared.close();
			con.close();
		}catch (SQLException e) {

		}
		return exercicio;
	}



	@Override
	public List<Exercicio> listarExercicios(int grupo,int personalizado) {
		List<Exercicio> lista = null;
		try{
			Connection con = ResourceManager.getConexao();
			String sql = "select exercicio.codigo,exercicio.nome, " +
			"exercicio.descricao,exercicio.padrao, " +
			"exercicio.codigogrupomuscular,grupomuscular.nome " +
			"from exercicio inner join grupomuscular " +
			"on exercicio.codigogrupomuscular = grupomuscular.codigo "+
			"where grupomuscular.codigo like ? and exercicio.padrao = ? " +
			"and exercicio.ativo = 1";

			PreparedStatement prepare = con.prepareStatement(sql);
			prepare.setInt(1, grupo);
			prepare.setInt(2, personalizado);
			ResultSet resultSet = prepare.executeQuery();
			lista = new ArrayList<Exercicio>();
			while (resultSet.next()){
				Exercicio exercicio = new Exercicio();
				exercicio.setCodigo(resultSet.getInt(1));
				exercicio.setNome(resultSet.getString(2));
				exercicio.setDescricao(resultSet.getString(3));
				exercicio.setPadrao(resultSet.getInt(4));
				GrupoMuscular grupoMuscular= new GrupoMuscular();
				grupoMuscular.setCodigo(resultSet.getInt(5));
				grupoMuscular.setNome(resultSet.getString(6));
				exercicio.setGrupoMuscular(grupoMuscular);
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
	public Exercicio buscarExercicioGrupoMuscular(GrupoMuscular grupo) {
		Exercicio exercicio = new Exercicio();
		GrupoMuscular grupomuscular = new GrupoMuscular();
		try{
			Connection con = ResourceManager.getConexao();
			String sql = "select nome, descricao, codigogrupomuscular, padrao from exercicio " +
			" where codigogrupomuscular = ?;";
			PreparedStatement prepare = con.prepareStatement(sql);	
			prepare.setInt(1, exercicio.getGrupoMuscular().getCodigo());
			ResultSet result  = prepare.executeQuery();


			//Verificar se está correto 
			if(result.next()){
				exercicio.setNome(result.getString(1));
				exercicio.setDescricao(result.getString(2));
				//verificar está parte
				grupomuscular.setCodigo(result.getInt(3));
				exercicio.setGrupoMuscular(grupomuscular);
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
	public String buscarGrupoMuscular(int codigo) throws SQLException {
		String nome= "";

		Connection con = ResourceManager.getConexao();
		String sql = "select grupomuscular.nome from grupomuscular where " +
		" grupomuscular.codigo like ? ";
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
			" from [exercicios_treino_ficha] " +
			" where [treino_codigo] = ?";
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setLong(aux++, codigoTreino);
		ResultSet result = prepare.executeQuery();
		List<Exercicio> list = new ArrayList<Exercicio>();

		while (result.next()){
			Exercicio e = new Exercicio();
			GrupoMuscular g = new GrupoMuscular();
			aux = 1;
			e.setCodigo(result.getLong("exercicio_codigo"));
			e.setNome(result.getString("exercicio_nome"));
			e.setDescricao(result.getString("exercicio_descricao"));
			e.setPadrao(result.getInt("exercicio_padrao"));
			e.setAtivo(result.getInt("exercicio_ativo"));
			g.setCodigo(result.getInt("grupo_codigo"));
			g.setNome(result.getString("grupo_nome"));
			e.setGrupoMuscular(g);

			list.add(e);
		}
		return list;
	}


	@Override
	public List<Exercicio> buscarExercicioTreino(int codigoGrupo,
			int codigoAtivo) throws SQLException {
		int aux = 1;
		Connection con = ResourceManager.getConexao();

		String sql = " select exercicio_codigo,exercicio_nome,exercicio_descricao, " +
		" exercicio_padrao,exercicio_ativo,grupo_codigo,grupo_nome " + 
		" from [exercicios_fora_treino] " +
		" where especificacao_treino is null " +
		" and especificacao_exercicio is null " +
		" and exercicio_grupo = ? and exercicio_ativo = ? ";

		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setInt(aux++, codigoGrupo);
		prepare.setInt(aux++, codigoAtivo);

		ResultSet result = prepare.executeQuery();
		List<Exercicio> lista = new ArrayList<Exercicio>();
		while (result.next()) {
			aux = 1;
			Exercicio e = new Exercicio();
			GrupoMuscular g = new GrupoMuscular();
			e.setCodigo(result.getLong(aux++));
			e.setNome(result.getString(aux++));
			e.setDescricao(result.getString(aux++));
			e.setPadrao(result.getInt(aux++));
			e.setAtivo(result.getInt(aux++));

			g.setCodigo(result.getInt(aux++));
			g.setNome(result.getString(aux++));

			e.setGrupoMuscular(g);

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

			GrupoMuscular g = new GrupoMuscular();
			g.setCodigo(result.getInt("grupo_codigo"));
			g.setNome(result.getString("grupo_nome"));

			e.setGrupoMuscular(g);

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

			GrupoMuscular g = new GrupoMuscular();
			g.setCodigo(result.getInt("grupo_codigo"));
			g.setNome(result.getString("grupo_nome"));

			e.setGrupoMuscular(g);

			list.add(e);
		}

		prepare.close();
		con.close();
		return list;
	}


	@Override
	public List<Especificacao> listarEspecificacao(long codigoTreino)
	throws SQLException {
		int aux = 1;
		String sql =" select especificacao_ordem, especificacao_repeticao," +
					" especificacao_carga,especificacao_unidade, " +
					" exercicio_codigo,exercicio_nome, exercicio_descricao," +
					" exercicio_ativo,exercicio_padrao, " +
					" grupo_nome, grupo_codigo, treino_codigo, " +
					" ficha_codigo " +
					" from especificacao_exercicio_treino " +
					" where treino_codigo = ? order by  especificacao_ordem asc";
		
		Connection con = ResourceManager.getConexao();
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setLong(aux++, codigoTreino);
		ResultSet result = prepare.executeQuery();
		List<Especificacao> list = new ArrayList<Especificacao>();

		while (result.next()){
			Especificacao esp = new Especificacao();
			esp.setOrdem(result.getInt("especificacao_ordem"));
			esp.setCarga(result.getInt("especificacao_carga"));
			esp.setUnidade(result.getString("especificacao_unidade"));
			esp.setCodigoTreino(result.getLong("treino_codigo"));
			esp.setQuantidade(result.getInt("especificacao_repeticao"));
			
			Exercicio ex = new Exercicio();
			ex.setNome(result.getString("exercicio_nome"));
			ex.setCodigo(result.getLong("exercicio_codigo"));
			ex.setDescricao(result.getString("exercicio_descricao"));
			ex.setAtivo(result.getInt("exercicio_ativo"));
			
			esp.setExercicio(ex);
			
			
			GrupoMuscular gr = new GrupoMuscular();
			gr.setCodigo(result.getInt("grupo_codigo"));
			gr.setNome(result.getString("grupo_nome"));
			
			ex.setGrupoMuscular(gr);
			
			list.add(esp);

		}

		prepare.close();
		con.close();
		return list;
	}





}