package workoutsystem.control;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import workoutsystem.dao.ExercicioDao;
import workoutsystem.dao.FichaDao;
import workoutsystem.dao.IExercicioDao;
import workoutsystem.dao.IFichaDao;
import workoutsystem.model.Especificacao;
import workoutsystem.model.Exercicio;
import workoutsystem.model.Ficha;
import workoutsystem.model.Frequencia;
import workoutsystem.model.Treino;
import workoutsystem.utilitaria.Validadora;

public class ControleFicha {

	public List<Frequencia> listarDias() {
		return new FichaDao().listarDias();
	}

	public boolean manipularFicha(Ficha ficha) throws SQLException{
		inserirFicha(ficha);
		return false;

	}

	public List<Ficha> buscarFicha() throws SQLException{
		IFichaDao dao = new FichaDao();
		IExercicioDao daoExercicio = new ExercicioDao();
		List<Ficha> lista = dao.listarFichas();
		for(Ficha f : lista){
			f.setTreinos(dao.listarTreinos(f.getCodigoFicha()));
			for(Treino t : f.getTreinos()){
				t.setExercicios
				(daoExercicio.listarExercicioTreino(t.getCodigoTreino()));
				for(Exercicio e : t.getExercicios()){
					e.setListaEspecificacao(dao.listarEspecificacao(
							t.getCodigoTreino(),
							e.getCodigo(),
							t.getCodigoFicha()));
				}
			}

		}
		return lista;
	}



	private boolean inserirFicha(Ficha ficha) throws SQLException{
		IFichaDao dao = new FichaDao();
		dao.inserirFicha(ficha);
		for (Treino treino : ficha.getTreinos()){
			dao.inserirTreino(treino);
			for (Exercicio exercicio :treino.getExercicios()){
				for(Especificacao especificacao : exercicio.getListaEspecificacao()){
					dao.inserirEspecificacao(especificacao);
				}

			}
		}
		return true;
	}

	public boolean excluirFicha(List<String> deletados) throws SQLException {
		boolean resultado = true;
		IFichaDao dao = new FichaDao();
		String mensagem = "Exercicio excluido!";

		for (String texto : deletados){
			String nome = texto; 
			Ficha ficha = dao.buscarFicha(nome);
			for(Treino t : ficha.getTreinos()){
				for (Exercicio e : t.getExercicios()){
					e.getListaEspecificacao();
				}


			}
			resultado = dao.excluirFicha(ficha.getCodigoFicha());

		}

		return resultado;

	}

	public Ficha buscarFichaNome(String nome) throws Exception {
		IFichaDao dao = new  FichaDao();
		Ficha ficha = dao.buscarFicha(nome.trim());
		if(ficha == null){
			String erro = "Não foi possivel achar a ficha";
			throw new Exception(erro);
		}
		return ficha;

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

	public void setPerfil(int codigoPerfil) throws SQLException {
		IFichaDao dao = new FichaDao();
		boolean resultado = dao.setPerfil(codigoPerfil);
	}

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

	public Ficha buscarFichaCodigo(long i) throws Exception{
		Ficha f = null;
		IFichaDao dao = new FichaDao();
		IExercicioDao daoExercicio = new ExercicioDao();
		f = dao.buscarFichaCodigo(i);
		if(f != null){
			f.setTreinos(dao.listarTreinos(f.getCodigoFicha()));
			for(Treino t : f.getTreinos()){
				t.setExercicios
				(daoExercicio.listarExercicioTreino(t.getCodigoTreino()));
				for(Exercicio e : t.getExercicios()){
					e.setListaEspecificacao(dao.listarEspecificacao(
							t.getCodigoTreino(),
							e.getCodigo(),
							t.getCodigoFicha()));
				}
			}

		}else{
			throw new Exception("Erro ao encontrar a ficha");
		}

		return f;

	}
	
	
	public String manipularTreino(String nomeTreino,long codigoFicha, long codigoTreino) throws Exception {
		Treino t = new Treino();
		t.setNomeTreino(nomeTreino.trim());
		t.setCodigoFicha(codigoFicha);
		t.setCodigoTreino(codigoTreino);
		Validadora<Treino> validadora = new Validadora<Treino>(t);
		String mensagem = validadora.getMessage();
		IFichaDao dao = new FichaDao();
		int resultado = 0;
		
		if(mensagem.equalsIgnoreCase("")){
			if(dao.buscarTreino(t.getNomeTreino(),t.getCodigoFicha())){
				mensagem = "Erro treino já existente nesta ficha";
				throw new Exception(mensagem);
			}else{
				if(dao.alterarNomeTreino(t.getNomeTreino(),
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

}
