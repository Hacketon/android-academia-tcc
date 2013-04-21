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
		int aux = 1;
		Connection con = ResourceManager.getConexao();
		String sql = "select codigo,codigoperfil,duracaoDias, " +
		"ficha_atual,nome,objetivo,padrao, " +
		" realizacoes from ficha";
		PreparedStatement prepared = con.prepareStatement(sql);
		ResultSet result = prepared.executeQuery();
		List<Ficha> list = new ArrayList<Ficha>();

		while(result.next()){
			Ficha f = new Ficha();
			aux = 1;
			f.setCodigoFicha(result.getInt(aux++));
			f.setCodigoPerfil(result.getInt(aux++));
			f.setDuracaoDias(result.getInt(aux++));
			f.setAtual(result.getInt(aux++));
			f.setNomeFicha(result.getString(aux++));
			f.setObjetivo(result.getString(aux++));
			f.setPadrao(result.getInt(aux++));
			f.setRealizacoes(f.getRealizacoes());
			list.add(f);
		}

		return list;
	}

	public List<Treino> listarTreinos(int codigoFicha) throws SQLException{
		Connection con = ResourceManager.getConexao();
		int aux = 1;
		String sql = "select codigo,nome,ordem,codigoFicha from treino " +
		" where codigoFicha = ? order by ordem asc";
		PreparedStatement prepare = con.prepareStatement(sql);

		prepare.setInt(aux++, codigoFicha);
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
		return list;
	}

	@Override
	public List<Especificacao> listarEspecificacao(int codigoTreino,
			int codigoExercicio,int codigoFicha) throws SQLException {
		int aux = 1;
		Connection con = ResourceManager.getConexao();

		String sql = "select [exercicio_codigo],[treino_codigo],[especificacao_ordem]," +
		" [especificacao_repeticao],[especificacao_carga], " +
		" [especificacao_unidade] from [especificacao_exercicio_treino] " +
		" where [exercicio_codigo] = ? and [treino_codigo] = 1 and [ficha_codigo] = 1 " +
		" order by [especificacao_ordem] asc";

		PreparedStatement prepare = con.prepareStatement(sql);
		prepare.setInt(aux++, codigoTreino);
		prepare.setInt(aux++, codigoExercicio);
		prepare.setInt(aux++, codigoFicha);
		ResultSet result = prepare.executeQuery();
		List<Especificacao> list = new ArrayList<Especificacao>();

		while (result.next()){
			aux = 1;
			Especificacao esp = new Especificacao();
			esp.setCodigoExercicio(result.getInt(aux++));
			esp.setCodigoTreino(result.getInt(aux++));
			esp.setOrdem(result.getInt(aux++));
			esp.setQuantidade(result.getInt(aux++));
			esp.setCarga(result.getInt(aux++));
			esp.setUnidade(result.getString(aux++));


			list.add(esp);
		}
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
		return verificar;
	}




}
