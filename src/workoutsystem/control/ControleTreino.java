package workoutsystem.control;

import android.annotation.SuppressLint;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import workoutsystem.dao.ExercicioDao;
import workoutsystem.dao.IExercicioDao;
import workoutsystem.dao.ITreinoDao;
import workoutsystem.dao.TreinoDao;
import workoutsystem.model.Exercicio;
import workoutsystem.model.Serie;
import workoutsystem.model.Treino;
import workoutsystem.utilitaria.Validadora;

@SuppressLint("UseSparseArrays")
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
	(String nomeTreino,long codigoFicha, int codigoTreino) throws Exception {
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
				mensagem = "Erro treino já existente nesta ficha";
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
						mensagem = "Não foi possivel criar o treino";
						throw new Exception(mensagem);
					}
				}
			}
		}else{
			throw new Exception(mensagem);
		}

		return mensagem;

	}



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


	public String removerTreino(long codigoTreino, long codigoFicha) throws Exception  {
		ITreinoDao dao = new TreinoDao();
		String mensagem = "";

		dao.excluirSerieTreino(codigoTreino);
		if(dao.excluirTreino(codigoTreino,codigoFicha)){
			mensagem = "Treino excluido com sucesso";
		}else{
			throw new Exception("Não foi possivel excluir o treino");
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
	
	public boolean reordenarSerie(List<Integer> codAntigo,long treino) throws Exception{
		int novo = 1;
		ITreinoDao dao = new TreinoDao();
		boolean retorno = true;
		String erro = "Não foi possivel reordenar a ficha!";
		String teste = "";
		for(Integer antigo : codAntigo){
			teste = teste + "codigo antigo > "+antigo+ "codigo novo > " +novo +"\n";
			retorno = dao.reordenarSerie(antigo, novo,treino);
			novo = novo + 1;
			if(!retorno){
				throw new Exception(erro);
			}
			
		}
			return retorno;
		
	}

/*
 * 	public void reordenarLista(int antigo, int novo, long codigoTreino) throws Exception {

			
	public boolean reordenarSerie(HashMap<Integer, Integer> listaCodigo,long codigoTreino) 
			throws Exception {
		ITreinoDao dao = new TreinoDao();
		String erro = "Não foi possivel fazer a reordenação";
		boolean verificar = true;
		for(Map.Entry<Integer, Integer> valor : listaCodigo.entrySet()){
			if(!dao.reordenarSerie(valor.getKey(), valor.getValue(), codigoTreino)){
				verificar = false;
				throw new Exception(erro);
			}
		}
		return verificar;
		}
			HashMap<Integer, Integer> listaCodigo = new HashMap<Integer, Integer>();
			int auxNovo = 0;
			int auxAntigo = 0;
			
			if(antigo<novo){
				//while(){
					//listaCodigo.put(antigo);	
				//}
			}else{
				auxAntigo = antigo+1; 
				auxNovo = novo + 1;
				listaCodigo.put(auxAntigo, auxNovo);
				while(novo<=antigo){
					novo = auxNovo + 1;
					listaCodigo.put(auxNovo,novo);
					auxNovo = auxNovo +1;
				}
			}
			
			reordenarSerie(listaCodigo,codigoTreino);


		}

 */
	

	

		
	
}
