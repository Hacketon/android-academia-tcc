package workoutsystem.control;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import workoutsystem.dao.ISerieDao;
import workoutsystem.dao.ITreinoDao;
import workoutsystem.dao.SerieDao;
import workoutsystem.dao.TreinoDao;
import workoutsystem.model.Ficha;
import workoutsystem.model.Rotina;
import workoutsystem.model.Serie;

public class ControleRotina {
	
	public List<Rotina> buscarHistoricoMensal(Calendar data) throws Exception{
		ITreinoDao dao = new TreinoDao();
		Calendar calendar = Calendar.getInstance();
		String mensagem = "Não há historico de treinos para este mês";
		int minimo = data.getActualMinimum(Calendar.DAY_OF_MONTH);
		int maximo = data.getActualMaximum(Calendar.DAY_OF_MONTH);
		int mes = data.get(Calendar.MONTH);
		int ano = data.get(Calendar.YEAR); 
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		//primeiro dia da busca !
		calendar.set(ano, mes, minimo);
		String primeiraData = sdf.format(calendar.getTime());
		//ultimo dia da busca !
		calendar.set(ano, mes, maximo);
		String ultimaData = sdf.format(calendar.getTime());
		
		List<Rotina> historico = dao.listarHistoricoTreino(primeiraData, ultimaData);
		if(historico.isEmpty()){
			throw new Exception(mensagem);
		}
		return historico;
	}
	
	public boolean inserirRealizacao(Rotina realizacao) throws SQLException, ParseException {
		ITreinoDao dao = new TreinoDao();
		boolean retorno = false;
		Rotina resultado = dao.buscarTreinoIniciado();
		if(resultado.getTreino().getCodigo() != realizacao.getTreino().getCodigo()){
			Calendar data = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String dataString = sdf.format(data.getTime());
			realizacao.setDataRealizacao(sdf.parse(dataString));
			retorno = dao.inserirRealizacaoTreino(realizacao);
		}
		
		return retorno;
	}
	
	public boolean removerRealizacaoSerie(Serie serie) throws SQLException {
		ISerieDao dao = new SerieDao();
		boolean retorno = dao.removerRealizacaoSerie(serie);
		return retorno;

	}

	public boolean removerRealizacaoSerie() throws SQLException {
		ISerieDao dao = new SerieDao();
		boolean retorno = dao.removerTodasRealizacoesSerie();
		return retorno;

	}


	public List<Serie> listarRealizacaoSerie(long codigoTreino) throws SQLException {
		ISerieDao dao = new SerieDao();
		List<Serie> retorno = dao.listarRealizacaoSerie(codigoTreino);
		return retorno;

	}

	public Rotina buscarTreinamento() throws Exception {
		ITreinoDao dao = new TreinoDao();
		Rotina retorno = dao.buscarTreinoIniciado();
		//retorno.setSerie(listarRealizacaoSerie(retorno.getCodigo()));
		return retorno;

	}

	public Rotina buscarUltimoTreinamento() throws Exception{
		ITreinoDao dao = new TreinoDao();
		Rotina realizacao = dao.buscarUltimoTreinoRealizado();
		String mensagem = "Nenhum treino foi realizado!";
		if(realizacao.getCodigo() == 0){
			throw new Exception(mensagem);
		}
		return realizacao;
	}
	public boolean inserirRealizacaoSerie(Serie serie) throws SQLException {
		ISerieDao dao = new SerieDao();
		boolean retorno = dao.inserirRealizacaoSerie(serie);
		return retorno;

	}

	public boolean atualizarRealizacao(int completa,int chave) throws Exception{
		ITreinoDao dao = new TreinoDao();
		ControleFicha controleFicha = new ControleFicha();
		boolean resultado = dao.atualizarRealizacao(completa,chave);
		if(chave == 0){
			controleFicha.atualizarRealizacoes();
		}
		return resultado;
		
	}

	public long calcularConclusao() throws Exception {
		ControleFicha controle = new ControleFicha();
		Ficha ficha = controle.buscarFichaAtual();
		long progresso = 0;
		double calculo = 0.0;
		if(ficha.getCodigo() == 0) {
			progresso = 0 ;
		}else{
			calculo = ficha.getRealizacoes()*100/ficha.getDuracao();
			progresso = Math.round(calculo);
		}
		return progresso;
	}

	public String colorirConclusao(long progresso) {
		String cor = "";
		if(progresso>=0 && progresso<=30){
			cor = "#00FF00";
		}else if(progresso>=31 && progresso<=70){
			cor="#FFFF00";
		}else if(progresso>=71){
			cor="#FF0000";
		}
		return cor;
	}

}
