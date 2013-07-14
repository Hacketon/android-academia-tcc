package workoutsystem.control;

import java.sql.SQLException;
import java.util.List;

import workoutsystem.dao.ISerieDao;
import workoutsystem.dao.ITreinoDao;
import workoutsystem.dao.SerieDao;
import workoutsystem.dao.TreinoDao;
import workoutsystem.model.Serie;
import workoutsystem.model.Treino;
import workoutsystem.utilitaria.Validadora;
import android.annotation.SuppressLint;

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
					mensagem = adicionarTreino(t);
				}
			}
		}else{
			throw new Exception(mensagem);
		}

		return mensagem;

	}


	private String adicionarTreino(Treino t)throws Exception {
		String mensagem;
		ITreinoDao dao = new TreinoDao();
		int resultado;
		resultado = dao.buscarQuantidadeTreino(t.getCodigoFicha());
		resultado = resultado + 1;
		t.setOrdem(resultado);
		if(dao.inserirTreino(t)){
			mensagem = "Treino criado com sucesso";
		}else{
			mensagem = "Não foi possivel criar o treino";
			throw new Exception(mensagem);
		}
		return mensagem;
	}




	public String removerTreino(long codigoTreino, long codigoFicha) throws Exception  {
		ITreinoDao dao = new TreinoDao();
		ISerieDao daoSerie = new SerieDao();
		String mensagem = "";

		daoSerie.excluirSerieTreino(codigoTreino);
		if(dao.excluirTreino(codigoTreino,codigoFicha)){
			mensagem = "Treino excluido com sucesso";
		}else{
			throw new Exception("Não foi possivel excluir o treino");
		}

		return mensagem;

	}



	public List<Treino> buscarTreinoValido(long codigoFicha) throws Exception{
		ITreinoDao dao = new TreinoDao();
		List<Treino> treinos = dao.buscarTreinoValido(codigoFicha);
		String erro = "Não possui treinos com series!";
		if(treinos.size()<=0){
			throw new Exception(erro);
		}
		return treinos;
	}


	public List<Treino> listarTreinos(long codigoFicha) throws SQLException {
		ITreinoDao dao = new TreinoDao();
		return dao.listarTreinos(codigoFicha);
	}


	public String adicionarTreinoExistentes(List<Treino> treinos,
			List<Treino> listaAdicao,long codigoficha) throws Exception {
		String mensagem = "Treinos adicionados! ";
		TreinoDao dao = new TreinoDao();
		ControleSerie controle = new ControleSerie();
		long contador = 1;
		boolean conflito = false;
		String nnome = "t";
		int i = 0;
		for(Treino t : listaAdicao){
			t.setNome(Validadora.verificarString(t.getNome()));
			for(i = 0 ; i<treinos.size();i++){
				Treino t1 = treinos.get(i);
				t.setCodigoFicha(t1.getCodigoFicha());
				if(t.getNome().equalsIgnoreCase(t1.getNome())){
					contador = Math.round(Math.random()*100);
					nnome+=contador;
					conflito = true;
					t.setNome(nnome);
					nnome = "t";
					i = 0;
				}
			}
		}
		
		
		for(Treino t:listaAdicao){
			adicionarTreino(t);
			long codigo = dao.buscarUltimoTreino();
			List<Serie> series = controle.listarSerie(t.getCodigo());
			for(Serie s : series){
				s.setCodigoTreino(codigo);
			}
			controle.adicionarSerie(series);
		}
		if(conflito){
			mensagem += " : alguns treinos foram renomeados devido a conflito";
		}
		
		return mensagem;
	}
	
}
