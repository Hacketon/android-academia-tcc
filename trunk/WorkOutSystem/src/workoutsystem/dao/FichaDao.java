package workoutsystem.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import workoutsystem.interfaces.IDiaSemana;
import workoutsystem.interfaces.IFichaDao;
import workoutsystem.model.Especificacao;
import workoutsystem.model.Exercicio;
import workoutsystem.model.Ficha;
import workoutsystem.model.Frequencia;
import workoutsystem.model.Treino;

public class FichaDao implements IDiaSemana,IFichaDao{

	@Override
	public List<Frequencia> listarDias() {
		List<Frequencia> listaDias = null;
		try{
			Connection con = ResourceManager.getConexao();
			String sql = "select codigo,diasemana from diasemana";
			PreparedStatement prepare = con.prepareStatement(sql);
			listaDias = new ArrayList<Frequencia>();
			ResultSet result = prepare.executeQuery();
			while (result.next()){
				Frequencia dia = new Frequencia();
				dia.setCodigo(result.getInt(1));
				dia.setDiaSemana(result.getString(2));
				listaDias.add(dia);
			}

			prepare.close();
			con.close();
		}catch (SQLException e) {

		}

		return listaDias;
	}

	public int buscarCodigoDia(String Nome){
		int codigo = 0;
		try{
			Connection con = ResourceManager.getConexao();
			String sql =" select codigo from diaSemana where diaSemana like ?";
			PreparedStatement prepared = con.prepareStatement(sql);
			prepared.setString(1, Nome);
			ResultSet result = prepared.executeQuery();

			if (result.next()) {
				codigo = result.getInt(1);
			}
			prepared.close();
			con.close();
		}catch (SQLException e) {

		}

		return codigo;

	}

	public List<Ficha> listarFichas() throws SQLException{
		Connection con = ResourceManager.getConexao();
		String sql = "select codigo,nome,duracaoDias, " +
				" objetivo,realizacoes, " +
				" ficha_atual,padrao from ficha";
		PreparedStatement prepared = con.prepareStatement(sql);
		ResultSet result = prepared.executeQuery();
		List<Ficha> list = new ArrayList<Ficha>();

		while(result.next()){
			Ficha f = new Ficha();
			f.setCodigoFicha(result.getInt("codigo"));
			f.setDuracaoDias(result.getInt("duracaoDias"));
			f.setAtual(result.getInt("ficha_atual"));
			f.setNomeFicha(result.getString("nome"));
			f.setObjetivo(result.getString("objetivo"));
			f.setPadrao(result.getInt("padrao"));
			f.setRealizacoes(result.getInt("realizacoes"));
			list.add(f);
		}
		

		prepared.close();
		con.close();

		return list;
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
			t.setCodigoTreino(result.getInt(aux++));
			t.setNomeTreino(result.getString(aux++));
			t.setOrdem(result.getInt(aux++));
			t.setCodigoFicha(result.getInt(aux++));

			list.add(t);
		}
		

		prepare.close();
		con.close();
		return list;
	}

	@Override
	public List<Especificacao> listarEspecificacao(long codigoTreino,
			long codigoExercicio,int codigoFicha) throws SQLException {
		int aux = 1;
		Connection con = ResourceManager.getConexao();

		String sql = "select exercicio_codigo,treino_codigo,especificacao_ordem," +
		" especificacao_repeticao,especificacao_carga, " +
		" especificacao_unidade from [especificacao_exercicio_treino] " +
		" where exercicio_codigo = ? and treino_codigo = ? and ficha_codigo = ? " +
		" order by especificacao_ordem asc";

		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setLong(aux++, codigoExercicio);
		prepare.setLong(aux++, codigoTreino);
		prepare.setInt(aux++, codigoFicha);
		ResultSet result = prepare.executeQuery();
		List<Especificacao> list = new ArrayList<Especificacao>();

		while (result.next()){
			
			Especificacao esp = new Especificacao();
			esp.setCodigoExercicio(result.getInt("exercicio_codigo"));
			esp.setCodigoTreino(result.getInt("treino_codigo"));
			esp.setOrdem(result.getInt("especificacao_ordem"));
			esp.setQuantidade(result.getInt("especificacao_repeticao"));
			esp.setCarga(result.getInt("especificacao_carga"));
			esp.setUnidade(result.getString("especificacao_unidade"));


			list.add(esp);
		}
		

		prepare.close();
		con.close();
		
		return list;
	}

	@Override
	public boolean inserirTreino(Treino treino) throws SQLException {
		int aux = 1;
		int resultado = 0;
		Connection con = ResourceManager.getConexao();
		String sql = "insert into treino (nome,ordem,codigoFicha) values (?,?,?)";
		boolean verificar = false;
		
		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setString(aux++,treino.getNomeTreino());
		prepare.setInt(aux++,treino.getOrdem());
		prepare.setInt(aux++, treino.getCodigoFicha());
		
		resultado = prepare.executeUpdate();

		if (resultado > 0){
			verificar = true;
		}

		prepare.close();
		con.close();
		
		return verificar;
	}

	@Override
	public boolean inserirFicha(Ficha ficha) throws SQLException {
		int aux = 1;
		int resultado = 0;
		boolean verificar = false;
		Connection con = ResourceManager.getConexao();
		String sql = "insert into ficha(nome,duracaoDias,objetivo, " +
		" realizacoes,ficha_atual,padrao)"+ 
		"values (?,?,?,?,?,?)";

		PreparedStatement prepare = con.prepareStatement(sql);

		prepare.setString(aux++, ficha.getNomeFicha());
		prepare.setInt(aux++, ficha.getDuracaoDias());
		prepare.setString(aux++, ficha.getObjetivo());
		prepare.setInt(aux++, ficha.getRealizacoes());
		prepare.setInt(aux++, ficha.getAtual());
		prepare.setInt(aux++, ficha.getPadrao());
		resultado= prepare.executeUpdate();

		if (resultado > 0){
			verificar = true;
		}


		prepare.close();
		con.close();
		
		return  verificar;
	}

	@Override
	public boolean inserirEspecificacao(Especificacao especificacao) throws SQLException{
		int aux = 1;
		int resultado = 0;
		Connection con = ResourceManager.getConexao();
		boolean verificar = false;
		String sql = "insert into especificacao " +
		"  (codigoexercicio,codigotreino,ordem," +
		"	repeticao,unidade,carga)"+ 
		"values (?,?,?,?,?,?);";
		
		PreparedStatement prepare = con.prepareStatement(sql);
		
		prepare.setInt(aux++, especificacao.getCodigoExercicio());
		prepare.setInt(aux++, especificacao.getCodigoTreino());
		prepare.setInt(aux++, especificacao.getOrdem());
		prepare.setInt(aux++, especificacao.getQuantidade());
		prepare.setString(aux++, especificacao.getUnidade());
		prepare.setDouble(aux++, especificacao.getCarga());
		
		resultado = prepare.executeUpdate();
		
		if (resultado > 0){
			verificar = true;
		}

		prepare.close();
		con.close();
		return verificar;
	}

	@Override
	public Ficha buscarFicha(String nome) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean excluirFicha(long codigoFicha) {
		// TODO Auto-generated method stub
		return false;
	}

	




}
