package workoutsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import workoutsystem.model.Exercicio;
import workoutsystem.model.Grupo;
import workoutsystem.model.Serie;

public class SerieDao implements ISerieDao {
	
	
	@Override
	public int buscarQuantidadeSerie(long codigoTreino) throws SQLException {
		Connection con = ResourceManager.getConexao();
		int aux = 1;
		String sql = "select count (*) as numero_serie" +
		" from serie where codigoTreino = ?";
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setLong(aux++,codigoTreino);
		ResultSet result = prepare.executeQuery();
		int resultado = 0;
		if(result.next()){
			resultado = result.getInt("numero_serie");
		}

		prepare.close();
		con.close();
		return resultado;
	}

	@Override
	public boolean reordenarSerie(int novo,int codigo) throws SQLException{
		Connection con = ResourceManager.getConexao();
		int aux = 1;
		String sql = "update serie set ordem = ? " +
		" where codigo = ?";
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setInt(aux++,novo);
		prepare.setInt(aux++, codigo);
		int resultado = prepare.executeUpdate();
		con.close();
		prepare.close();
		return resultado>0;
	}
	
	
	@Override
	public boolean excluirSerieCodigo(long codigo) throws SQLException {
		int aux = 1;
		Connection con = ResourceManager.getConexao();
		String sql = "delete from serie where codigo = ?";
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setLong(aux++, codigo);
		int valor = prepare.executeUpdate();
		con.close();
		prepare.close();
		return (valor>=0);
	}
	@Override
	public int buscarSerie(int ordem,int codigotreino ) throws SQLException{
		Connection con = ResourceManager.getConexao();
		int aux = 1;
		int resultado = 0;
		String sql = "select codigo from serie where ordem = ? and codigotreino = ? ";
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setInt(aux++,ordem);
		prepare.setInt(aux++,codigotreino);
		ResultSet result = prepare.executeQuery();
		if(result.next()){
			resultado = result.getInt("codigo");
		}

		prepare.close();
		con.close();
		return resultado;
	}
	@Override
	public boolean atualizarSerie(Serie serie) throws SQLException {
		int aux = 1;
		int resultado = 0;
		Connection con = ResourceManager.getConexao();
		
		String sql = 
			"   update serie" +
			"   set codigoexercicio = ?,codigotreino = ? ,ordem = ? ," +
			"	repeticao = ? ,unidade = ?,carga = ? " +
			"   where ordem = ? and codigoTreino = ?";

		PreparedStatement prepare = con.prepareStatement(sql);

		prepare.setLong(aux++, serie.getExercicio().getCodigo());
		prepare.setLong(aux++, serie.getCodigoTreino());
		prepare.setInt(aux++, serie.getOrdem());
		prepare.setInt(aux++, serie.getQuantidade());
		prepare.setString(aux++, serie.getUnidade());
		prepare.setDouble(aux++, serie.getCarga());
		prepare.setInt(aux++, serie.getOrdem());
		prepare.setLong(aux++, serie.getCodigoTreino());

		resultado = prepare.executeUpdate();
		prepare.close();
		con.close();
		return resultado>0;


	}
	
	@Override
	public boolean removerSerie(long codigoTreino, long codigoExercicio) throws SQLException{
		int aux = 1;
		Connection con = ResourceManager.getConexao();
		String sql = "delete from serie where codigoTreino = ? and codigoExercicio=?";
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setLong(aux++, codigoTreino);
		prepare.setLong(aux++, codigoExercicio);
		int valor = prepare.executeUpdate();
		con.close();
		prepare.close();
		return (valor>=0);
	}
	


	@Override
	public boolean excluirSerieTreino(long codigoTreino) throws SQLException {
		int aux = 1;
		Connection con = ResourceManager.getConexao();
		String sql = "delete from serie where codigoTreino = ?";
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setLong(aux++, codigoTreino);
		int valor = prepare.executeUpdate();
		con.close();
		prepare.close();
		return (valor>=0);
	}
	
	@Override
	public boolean inserirRealizacaoSerie(Serie serie) throws SQLException{
		int aux = 1;
		int resultado = 0;
		Connection con = ResourceManager.getConexao();
		String sql = "insert into realizacaoSerie " +
				" (codigoserie,codigotreino,codigoexercicio, ordem,unidade,carga, quantidade)" +
				"values (?,?,?,?,?,?,?);";

		PreparedStatement prepare = con.prepareStatement(sql);

		prepare.setLong(aux++, serie.getCodigo());
		prepare.setLong(aux++, serie.getCodigoTreino());
		prepare.setLong(aux++, serie.getExercicio().getCodigo());
		prepare.setInt(aux++, serie.getOrdem());
		prepare.setString(aux++, serie.getUnidade());
		prepare.setDouble(aux++, serie.getCarga());
		prepare.setInt(aux++, serie.getQuantidade());
		
		resultado = prepare.executeUpdate();
		prepare.close();
		con.close();
		return resultado>0;
	}

	

	@Override
	public boolean removerRealizacaoSerie(Serie serie) throws SQLException{
		int aux = 1;
		Connection con = ResourceManager.getConexao();
		String sql = "delete from realizacaoSerie where  codigoserie = ?";
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setLong(aux++, serie.getCodigo());
		int valor = prepare.executeUpdate();
		con.close();
		prepare.close();
		return (valor>=0);
	}

	
	
	
	@Override
	public List<Serie> listarRealizacaoSerie()	throws SQLException {
		
		String sql = " select codigoserie,codigotreino ,ordem,carga, unidade, quantidade, nome from serie_realizacao ";
		
		Connection con = ResourceManager.getConexao();
		PreparedStatement prepare = con.prepareStatement(sql);
		ResultSet result = prepare.executeQuery();
		List<Serie> list = new ArrayList<Serie>();
		Exercicio exercicio = new Exercicio();
		
		while (result.next()){
						Serie serie = new Serie();
			serie.setCodigo(result.getInt("codigoserie"));
			serie.setCodigoTreino(result.getInt("codigotreino"));
			serie.setOrdem(result.getInt("ordem"));
			serie.setCarga(result.getInt("carga"));
			serie.setUnidade(result.getString("unidade"));
			serie.setQuantidade(result.getInt("quantidade"));
			exercicio.setNome(result.getString("nome"));
			serie.setExercicio(exercicio);
			
			
			list.add(serie);

		}

		prepare.close();
		con.close();
		return list;
	}

	@Override
	public boolean inserirRealizacao(Serie serie, long codigoFicha) throws SQLException {
		
	
		int aux = 1;
		int resultado = 0;
		Connection con = ResourceManager.getConexao();
		String sql = "insert into realizacao" +
				"(datarealizacao,codigoserie, codigotreino, codigoficha)" +
				"values (?,?,?,?);";

		PreparedStatement prepare = con.prepareStatement(sql);

		java.util.Date data = new java.util.Date(); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String dataFormat = sdf.format(data); 
		
		prepare.setString(aux++, dataFormat);
		prepare.setLong(aux++, serie.getCodigo());
		prepare.setLong(aux++, serie.getCodigoTreino());
		prepare.setLong(aux++, codigoFicha);
		
		resultado = prepare.executeUpdate();
		prepare.close();
		con.close();
		return resultado>0;

	}

	
	
	@Override
	public List<Serie> listarSerie(long codigoTreino)
	throws SQLException {
		int aux = 1;
		String sql =" select serie_codigo,serie_ordem, serie_repeticao," +
					" serie_carga,serie_unidade, " +
					" exercicio_codigo,exercicio_nome, exercicio_descricao," +
					" exercicio_ativo,exercicio_padrao, " +
					" grupo_nome, grupo_codigo, treino_codigo, " +
					" ficha_codigo " +
					" from serie_exercicio_treino " +
					" where treino_codigo = ? order by  serie_ordem asc";
		
		Connection con = ResourceManager.getConexao();
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setLong(aux++, codigoTreino);
		ResultSet result = prepare.executeQuery();
		List<Serie> list = new ArrayList<Serie>();
		
		while (result.next()){
			Serie serie = new Serie();
			serie.setCodigo(result.getInt("serie_codigo"));
			serie.setOrdem(result.getInt("serie_ordem"));
			serie.setCarga(result.getInt("serie_carga"));
			serie.setUnidade(result.getString("serie_unidade"));
			serie.setCodigoTreino(result.getLong("treino_codigo"));
			serie.setQuantidade(result.getInt("serie_repeticao"));
			
			Exercicio ex = new Exercicio();
			ex.setNome(result.getString("exercicio_nome"));
			ex.setCodigo(result.getLong("exercicio_codigo"));
			ex.setDescricao(result.getString("exercicio_descricao"));
			ex.setAtivo(result.getInt("exercicio_ativo"));
			
			serie.setExercicio(ex);
			
			
			Grupo gr = new Grupo();
			gr.setCodigo(result.getInt("grupo_codigo"));
			gr.setNome(result.getString("grupo_nome"));
			
			ex.setGrupo(gr);
			
			list.add(serie);

		}

		prepare.close();
		con.close();
		return list;
	}

	public boolean alterarCarga(double novo,int codigo) throws SQLException{
		Connection con = ResourceManager.getConexao();
		int aux = 1;
		String sql = "update serie set carga = ? " +
		" where codigo = ?";
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setDouble(aux++,novo);
		prepare.setInt(aux++, codigo);
		int resultado = prepare.executeUpdate();
		con.close();
		prepare.close();
		return resultado>0;
	}


	@Override
	public boolean inserirSerie(Serie serie) throws SQLException{
		int aux = 1;
		int resultado = 0;
		Connection con = ResourceManager.getConexao();
		String sql = "insert into serie " +
		"  (codigoexercicio,codigotreino,ordem," +
		"	repeticao,unidade,carga)"+ 
		"values (?,?,?,?,?,?);";

		PreparedStatement prepare = con.prepareStatement(sql);

		prepare.setLong(aux++, serie.getExercicio().getCodigo());
		prepare.setLong(aux++, serie.getCodigoTreino());
		prepare.setInt(aux++, serie.getOrdem());
		prepare.setInt(aux++, serie.getQuantidade());
		prepare.setString(aux++, serie.getUnidade());
		prepare.setDouble(aux++, serie.getCarga());

		resultado = prepare.executeUpdate();
		prepare.close();
		con.close();
		return resultado>0;
	}

	@Override
	public boolean removerSerie(long codigo) throws SQLException {
		int aux = 1;
		Connection con = ResourceManager.getConexao();
		String sql = "delete from serie where codigoExercicio=?";
		PreparedStatement prepare = con.prepareStatement(sql);
		
		prepare.setLong(aux++, codigo);
		int valor = prepare.executeUpdate();
		con.close();
		prepare.close();
		return (valor>=0);
	
	}


}
