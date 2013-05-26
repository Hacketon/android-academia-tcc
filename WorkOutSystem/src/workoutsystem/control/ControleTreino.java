package workoutsystem.control;

import java.sql.SQLException;
import java.util.List;

import workoutsystem.dao.ExercicioDao;
import workoutsystem.dao.FichaDao;
import workoutsystem.dao.IExercicioDao;
import workoutsystem.dao.IFichaDao;
import workoutsystem.model.Especificacao;
import workoutsystem.model.Exercicio;
import workoutsystem.model.Treino;
import workoutsystem.utilitaria.Validadora;

public class ControleTreino {
	
	public boolean reordenarTreino(List<Treino> treinos) throws Exception {
		IFichaDao dao = new FichaDao();
		boolean verificacao = true;
		try{
			for(Treino t : treinos){
				verificacao = 
					dao.reordenarTreino
					(t.getOrdem(),t.getCodigoTreino());
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
		t.setNome(nomeTreino.trim());
		t.setCodigoFicha(codigoFicha);
		t.setCodigoTreino(codigoTreino);
		Validadora<Treino> validadora = new Validadora<Treino>(t);
		String mensagem = validadora.getMessage();
		IFichaDao dao = new FichaDao();
		int resultado = 0;

		if(mensagem.equalsIgnoreCase("")){
			if(dao.buscarTreino(t.getNome(),t.getCodigoFicha())){
				mensagem = "Erro treino já existente nesta ficha";
				throw new Exception(mensagem);
			}else{
				if(dao.alterarNomeTreino(t.getNome(),
						t.getCodigoFicha(),
						t.getCodigoTreino())){
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
	
	
	public boolean reordenarEspecificacoes
	(List<Integer> ordemAntiga, List<Especificacao> especificacao) 
	throws Exception {
		IFichaDao dao = new FichaDao();
		boolean verificacao = true;
		try{
			for(Integer ordem : ordemAntiga){

				for(Especificacao t : especificacao){
					verificacao = 
						dao.reordenarEspecificacao
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
	
	public List<Especificacao> listarEspecificacoes(long codigoTreino) 
	throws SQLException{
		IExercicioDao dao = new ExercicioDao();
		List<Especificacao> esp = dao.listarEspecificacao(codigoTreino);
		return esp;
	}


	
	public void removerEspecificacao(long codigoTreino,
			List<Exercicio> listaRemocao) throws Exception {
		String erro = "Não há exercicios para ser removidos";
		IFichaDao dao = new FichaDao();
		if(listaRemocao.size()<=0){
			throw new Exception(erro);
		}else{
			for(Exercicio e : listaRemocao){
				dao.excluirEspecificacao
					(codigoTreino,e.getCodigo());
			}
		}
		
	}
	
	
	public String removerTreino(long codigoTreino, long codigoFicha) throws Exception  {
		IFichaDao dao = new FichaDao();
		String mensagem = "";
		dao.excluirEspecificacao(codigoTreino);
		if(dao.excluirTreino(codigoTreino,codigoFicha)){
			mensagem = "Treino excluido com sucesso";
		}else{
			throw new Exception("Não foi possivel excluir o treino");
		}

		return mensagem;

	}
}
