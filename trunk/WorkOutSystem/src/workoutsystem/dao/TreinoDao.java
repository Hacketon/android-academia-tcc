package workoutsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import workoutsystem.model.Exercicio;
import workoutsystem.model.GrupoMuscular;
import workoutsystem.model.Serie;
import workoutsystem.model.Treino;

public class TreinoDao implements ITreinoDao {

	@Override
	public boolean inserirTreino(Treino treino) throws SQLException {
		int aux = 1;
		int resultado = 0;
		Connection con = ResourceManager.getConexao();
		String sql = "insert into treino (nome,ordem,codigoFicha) values (?,?,?)";
		boolean verificar = false;

		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setString(aux++,treino.getNome());
		prepare.setInt(aux++,treino.getOrdem());
		prepare.setLong(aux++, treino.getCodigoFicha());

		resultado = prepare.executeUpdate();

		if (resultado > 0){
			verificar = true;
		}

		prepare.close();
		con.close();

		return verificar;
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
	public boolean reordenarTreino(int ordem, long codigoTreino) throws SQLException {
		Connection con = ResourceManager.getConexao();
		int aux = 1;
		String sql = "update treino set ordem = ? where codigo = ?";
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setInt(aux++, ordem);
		prepare.setLong(aux++, codigoTreino);
		int resultado = prepare.executeUpdate();
		prepare.close();
		con.close();
		return resultado>0;
	}

	@Override
	public boolean excluirTreino(long codigoTreino, long codigoFicha)
	throws SQLException {
		int aux = 1;
		Connection con = ResourceManager.getConexao();
		String sql = "delete from treino where codigo = ? and codigoFicha = ?";
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setLong(aux++, codigoTreino);
		prepare.setLong(aux++, codigoFicha);
		int valor = prepare.executeUpdate();
		con.close();
		prepare.close();
		return (valor>0);
	}

	@Override
	public boolean verificarExercicio(long codigoExercicio) throws SQLException {
		String sql = "select * from serie where codigoexercicio = ?";
		Connection con = ResourceManager.getConexao();
		int aux = 1;
		boolean retorno = false;
		PreparedStatement prepared = con.prepareStatement(sql);
		prepared.setLong(aux++, codigoExercicio);
		ResultSet result = prepared.executeQuery();
		if(result.next()){
			retorno = true;
		}
		prepared.close();
		con.close();
		return retorno;
	}


	@Override
	public boolean buscarTreino(String nomeTreino, long codigoFicha) throws SQLException {
		String sql = "select codigo , nome ,ordem ,codigoFicha " +
		"from treino where " +
		"codigoFicha = ? and nome = ?";
		int aux = 1;
		Connection con = ResourceManager.getConexao();
		PreparedStatement prepared = con.prepareStatement(sql);
		prepared.setLong(aux++, codigoFicha);
		prepared.setString(aux++, nomeTreino);
		ResultSet result = prepared.executeQuery();
		boolean retorno = result.next();
		prepared.close();
		con.close();
		return retorno;
	}

	@Override
	public boolean alterarNomeTreino
	(String nomeTreino, long codigoFicha,long codigoTreino)
	throws SQLException {
		String sql = "update treino set nome = ? where codigoFicha = ? and codigo = ?";
		int aux = 1;
		Connection con = ResourceManager.getConexao();
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setString(aux++, nomeTreino);
		prepare.setLong(aux++, codigoFicha);
		prepare.setLong(aux++, codigoTreino);
		int retorno = prepare.executeUpdate();
		prepare.close();
		con.close();
		return retorno > 0 ;
	}

	@Override
	public int buscarQuantidadeTreino(long codigoFicha) throws SQLException {
		Connection con = ResourceManager.getConexao();
		int aux = 1;
		String sql = "select count (*) as numero_treino " +
		"from treino where codigoFicha = ?";
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setLong(aux++,codigoFicha);
		ResultSet result = prepare.executeQuery();
		int resultado = 0;
		if(result.next()){
			resultado = result.getInt("numero_treino");
		}
		return resultado;
	}


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
	public List<Treino> listarTreinos(long codigoFicha) throws SQLException{
		Connection con = ResourceManager.getConexao();
		int aux = 1;
		String sql = "select codigo,nome,ordem,codigoFicha from treino " +
		" where codigoFicha = ? order by ordem asc";
		PreparedStatement prepare = con.prepareStatement(sql);

		prepare.setLong(aux++, codigoFicha);
		ResultSet result = prepare.executeQuery();
		List<Treino> list = new ArrayList<Treino>(); 

		while(result.next()){
			aux = 1;
			Treino t = new Treino();
			t.setCodigo(result.getInt(aux++));
			t.setNome(result.getString(aux++));
			t.setOrdem(result.getInt(aux++));
			t.setCodigoFicha(result.getInt(aux++));

			list.add(t);
		}


		prepare.close();
		con.close();
		return list;
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
		return resultado;
	}
	@Override
	public boolean atualizarSerie(Serie serie) throws SQLException {
		int aux = 1;
		int resultado = 0;
		Connection con = ResourceManager.getConexao();
		boolean verificar = false;
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
			
			
			GrupoMuscular gr = new GrupoMuscular();
			gr.setCodigo(result.getInt("grupo_codigo"));
			gr.setNome(result.getString("grupo_nome"));
			
			ex.setGrupoMuscular(gr);
			
			list.add(serie);

		}

		prepare.close();
		con.close();
		return list;
	}
	@Override
	public List<Treino> buscarTreinoValido(long codigoFicha) throws SQLException {
		String sql = " select treino_codigo,treino_nome," +
				 	 " treino_ordem,treino_codigoficha" +
				 	 " from treino_serie where ficha_codigo = ?";
		int aux = 1;
		Connection con = ResourceManager.getConexao();
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setLong(aux++, codigoFicha);
		ResultSet result = prepare.executeQuery();
		List<Treino> listaTreino = new ArrayList<Treino>();
		while(result.next()){
			Treino treino = new Treino();
			treino.setCodigo(result.getInt("treino_codigo"));
			treino.setNome(result.getString("treino_nome"));
			treino.setOrdem(result.getInt("treino_ordem"));
			treino.setCodigoFicha(result.getInt("treino_codigoficha"));
			listaTreino.add(treino);
		}
		return listaTreino;
	}
	
	// criar controle e interface dao
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


	
	
	// tabelaRealizaSerie teste
	
	
	
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
	public String buscarUltimoTreino() throws SQLException {
		Connection con = ResourceManager.getConexao();
		int aux = 1;
		String resultado = "";
		String sql = "select treino.nome as treino from realizacao" +
				" inner join treino on treino.codigo = codigotreino " +
				"order by datarealizacao asc  ";
		
		PreparedStatement prepare = con.prepareStatement(sql);
		ResultSet result = prepare.executeQuery();
		if(result.next()){
			resultado = result.getString("treino");
		}
		return resultado;	
	
	}

	

}
