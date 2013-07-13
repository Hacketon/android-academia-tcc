package workoutsystem.control;

import java.sql.SQLException;
import java.util.List;

import workoutsystem.dao.ITreinoDao;
import workoutsystem.dao.TreinoDao;
import workoutsystem.model.Exercicio;
import workoutsystem.model.Serie;
import workoutsystem.utilitaria.Validadora;

public class ControleSerie {

	public List<Serie> listarSerie(long codigoTreino) 
	throws SQLException{
		ITreinoDao dao = new TreinoDao();
		List<Serie> esp = dao.listarSerie(codigoTreino);
		return esp;
	}
	
	

	public void removerSerie(long codigoTreino,List<Exercicio> exercicio) 
	throws Exception {
		ITreinoDao dao = new TreinoDao();
		for(Exercicio e : exercicio){
			dao.removerSerie(codigoTreino,e.getCodigo());
		}

	}
	
	public String alterarCarga(Double carga, int codigo){
		String mensagem = "";
		TreinoDao dao = new TreinoDao();

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
		ITreinoDao dao = new TreinoDao();
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
	
	
	public boolean reordenarSerie(List<Serie> series) throws Exception{
		int novo = 1;
		ITreinoDao dao = new TreinoDao();
		boolean retorno = true;
		String erro = "Não foi possivel reordenar a ficha!";

		for(Serie antigo : series){
			retorno = dao.reordenarSerie(novo,antigo.getCodigo());
			novo = novo + 1;
			if(!retorno){
				throw new Exception(erro);
			}

		}
		return retorno;

	}
	
	public String adicionarSerie(List<Serie> lista) throws Exception{
		ITreinoDao dao = new TreinoDao();
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
		ITreinoDao dao = new TreinoDao();
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
		ITreinoDao dao = new TreinoDao();
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
