package workoutsystem.control;

import java.sql.SQLException;
import java.util.List;

import android.widget.Toast;

import workoutsystem.dao.ISerieDao;
import workoutsystem.dao.SerieDao;
import workoutsystem.dao.TreinoDao;
import workoutsystem.model.Exercicio;
import workoutsystem.model.Serie;
import workoutsystem.utilitaria.Validadora;

public class ControleSerie {

	public List<Serie> listarSerie(long codigoTreino) 
	throws SQLException{
		ISerieDao dao = new SerieDao();
		List<Serie> esp = dao.listarSerie(codigoTreino);
		return esp;
	}



	public void removerSerie(long codigoTreino,List<Exercicio> exercicio) 
	throws Exception {
		ISerieDao dao = new SerieDao();
		for(Exercicio e : exercicio){
			dao.removerSerie(codigoTreino,e.getCodigo());
		}

	}

	public boolean removerSerie(List<Exercicio> exercicio) throws SQLException{
		ISerieDao dao = new SerieDao();
		boolean retorno = false;
		for(Exercicio e : exercicio){
			retorno = dao.removerSerie(e.getCodigo());
		}
		return retorno;
	}

	public String alterarCarga(Double carga, int codigo){
		String mensagem = "";
		ISerieDao dao = new SerieDao();

		try {
			if(dao.alterarCarga(carga, codigo)){
				mensagem = "Alterado com sucesso";
			}else{
				mensagem = "Erro ao alterar";
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return mensagem;
	}




	public String atualizarSerie(Serie serie) throws Exception {
		boolean resultado = false;
		String mensagem = "Serie alterada com sucesso";
		ISerieDao dao = new SerieDao();
		Validadora<Serie> val = new Validadora<Serie>(serie);
		String erro = val.getMessage();
		if(erro.equalsIgnoreCase("")){
			resultado = dao.atualizarSerie(serie);
		}else{
			throw new Exception(erro);
		}

		if(!resultado){
			mensagem = "Erro ao alterar a serie";
			throw new Exception(mensagem);
		}


		return  mensagem;

	} 

	public String manipularSerie(List<Serie> esp) throws Exception {
		String mensagem = "O numero minimo de series é 1";
		if(esp.size()<=0){
			throw new Exception(mensagem);
		}else{
			if(esp.get(0).getOrdem() == 0){
				mensagem = adicionarSerie(esp);
			}else{
				mensagem = atualizarSerie(esp.get(0));
			}

		}
		return mensagem;
	}

	/**
	 * Metodo responsavel pela reordenação das series de um treino 
	 * 
	 * @param posicaoInicial [Posição da onde esta saindo a serie]
	 * @param posicaoFinal [Posição da onde esta indo a serie]
	 * @param codigoTreino [Codigo do treino correspondente a serie]
	 * @param operacao [Reordenação do tipo remoção ou movimentação]
	 * @param codigos [Codigo das series que serão reordenadas ]
	 * @return [Resultado da operação: verdadeiro = sucesso , false = fracasso]
	 * @throws SQLException
	 */
	public boolean reordenarSerie(int posicaoInicial,
			int posicaoFinal, long codigoTreino,
			boolean operacao,List<Integer> codigos)
	throws SQLException{
		boolean retorno = false;
		ISerieDao dao = new SerieDao();
		if(operacao){
			retorno = dao.reordenarSerieRemocao
			(posicaoInicial, posicaoFinal, codigoTreino);
		}else{
			for(Integer codigo : codigos){
				posicaoInicial++;
				retorno = dao.reordenarSerie(posicaoInicial,codigo);
			}
			
		}
		
		return retorno;
		
	}

	public String adicionarSerie(List<Serie> lista) throws Exception{
		ISerieDao dao = new SerieDao();
		boolean resultado = false;
		int quantidade = 0;
		String mensagem = "Series adicionadas com sucesso";
		String erro = "";
		for(Serie esp : lista){
			Validadora<Serie> val = new Validadora<Serie>(esp);
			erro = val.getMessage();
			if(erro.equalsIgnoreCase("")){
				quantidade= dao.buscarQuantidadeSerie(esp.getCodigoTreino());
				quantidade = quantidade + 1;
				esp.setOrdem(quantidade);
				resultado = dao.inserirSerie(esp);
				if(!resultado){
					erro = "Erro ao adicionar series";
					throw new Exception(mensagem);
				}
			}else{
				throw new Exception(erro);
			}
		}


		return  mensagem;
	}

	public String removerSerieCodigo(int codigo) throws Exception{
		ISerieDao dao = new SerieDao();
		String mensagem = "Não foi possivel realizar a remoção"; 

		try{
			boolean resultado = dao.excluirSerieCodigo(codigo);

			if(resultado){
				mensagem= "Serie removida com sucesso";
			}

		}catch (Exception e) {
			throw new Exception(mensagem);
		}

		return mensagem;


	}


	public String removerSerie(int ordem,int treino) throws Exception {
		ISerieDao dao = new SerieDao();
		String mensagem = "Não foi possivel realizar a remoção"; 

		try{
			int codigo = dao.buscarSerie(ordem, treino);
			boolean resultado = dao.excluirSerieCodigo(codigo);

			if(resultado){
				mensagem= "Serie removida com sucesso";
			}

		}catch (Exception e) {
			throw new Exception(mensagem);
		}

		return mensagem;

	}
}
