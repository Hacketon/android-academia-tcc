package workoutsystem.control;

import java.sql.SQLException;
import java.util.List;

import workoutsystem.dao.ExercicioDao;
import workoutsystem.dao.IExercicioDao;
import workoutsystem.dao.ITreinoDao;
import workoutsystem.dao.TreinoDao;
import workoutsystem.model.Exercicio;
import workoutsystem.model.Serie;
import workoutsystem.model.Treino;
import workoutsystem.utilitaria.Validadora;

public class ControleTreino {

	public boolean reordenarTreino(List<Treino> treinos) throws Exception {
		ITreinoDao dao = new TreinoDao();
		boolean verificacao = true;
		try{
			for(Treino t : treinos){
				verificacao = 
					dao.reordenarTreino
					(t.getOrdem(),t.getCodigo());
				if(!verificacao){
					break;
				}
			}

		}catch (Exception e) {
			throw new Exception("Erro ao reordenar os exercicios");
		}

		return verificacao;


	}


	public String manipularTreino
	(String nomeTreino,long codigoFicha, long codigoTreino) throws Exception {
		Treino t = new Treino();
		
		t.setNome(Validadora.verificarString(nomeTreino));
		t.setCodigoFicha(codigoFicha);
		t.setCodigo(codigoTreino);
		
		Validadora<Treino> validadora = new Validadora<Treino>(t);
		String mensagem = validadora.getMessage();
		
		ITreinoDao dao = new TreinoDao();
		int resultado = 0;
		
		if(mensagem.equalsIgnoreCase("")){
			if(dao.buscarTreino(t.getNome(),t.getCodigoFicha())){
				mensagem = "Erro treino j� existente nesta ficha";
				throw new Exception(mensagem);
			}else{
				if(dao.alterarNomeTreino(t.getNome(),
						t.getCodigoFicha(),
						t.getCodigo())){
					mensagem = "Nome alterado com sucesso"; 
				}else{
					resultado = dao.buscarQuantidadeTreino(t.getCodigoFicha());
					resultado = resultado + 1;
					t.setOrdem(resultado);
					if(dao.inserirTreino(t)){
						mensagem = "Treino criado com sucesso";
					}else{
						mensagem = "N�o foi possivel criar o treino";
						throw new Exception(mensagem);
					}
				}
			}
		}else{
			throw new Exception(mensagem);
		}

		return mensagem;

	}


	public boolean reordenarSerie
	(List<Integer> ordemAntiga, List<Serie> serie) 
	throws Exception {
		ITreinoDao dao = new TreinoDao();
		boolean verificacao = true;
		try{
			for(Integer ordem : ordemAntiga){

				for(Serie t : serie){
					verificacao = 
						dao.reordenarSerie
						(ordem,t.getOrdem(),t.getCodigoTreino());
					if(!verificacao){
						break;
					}
				}
			}
		}catch (Exception e) {
			throw new Exception("Erro ao reordenar as series");
		}

		return verificacao;


	}

	public List<Serie> listarSerie(long codigoTreino) 
	throws SQLException{
		IExercicioDao dao = new ExercicioDao();
		List<Serie> esp = dao.listarSerie(codigoTreino);
		return esp;
	}



	public void removerSerie(long codigoTreino,
			List<Exercicio> listaRemocao) throws Exception {
		String erro = "N�o h� exercicios para ser removidos";
		ITreinoDao dao = new TreinoDao();
		if(listaRemocao.size()<=0){
			throw new Exception(erro);
		}else{
			for(Exercicio e : listaRemocao){
				dao.excluirSerie
				(codigoTreino,e.getCodigo());
			}
		}

	}


	public String removerTreino(long codigoTreino, long codigoFicha) throws Exception  {
		ITreinoDao dao = new TreinoDao();
		String mensagem = "";

		dao.excluirSerie(codigoTreino);
		if(dao.excluirTreino(codigoTreino,codigoFicha)){
			mensagem = "Treino excluido com sucesso";
		}else{
			throw new Exception("N�o foi possivel excluir o treino");
		}

		return mensagem;

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


	public String removerSerie(long codigoTreino, long ordem) throws Exception {
		ITreinoDao dao = new TreinoDao();
		String mensagem = "N�o foi possivel realizar a remo��o"; 
		
		try{
			boolean resultado = dao.excluirSerie(codigoTreino,ordem);
			
			if(resultado){
				mensagem= "Serie removida com sucesso";
			}
				
		}catch (Exception e) {
			throw new Exception(mensagem);
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
		String mensagem = "O numero minimo de series � 1";
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
}