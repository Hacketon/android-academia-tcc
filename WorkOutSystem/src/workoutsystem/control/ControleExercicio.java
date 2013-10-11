package workoutsystem.control;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import workoutsystem.dao.ExercicioDao;
import workoutsystem.dao.IExercicioDao;
import workoutsystem.dao.ITreinoDao;
import workoutsystem.dao.TreinoDao;
import workoutsystem.model.Exercicio;
import workoutsystem.model.Grupo;
import workoutsystem.model.Passo;
import workoutsystem.utilitaria.Validadora;
import workoutsystem.view.R;


public class ControleExercicio {
	/**
	 * Metodo responsavel pela alteração / adição ou reativação 
	 * de um exercicio
	 * @param exercicio
	 * @return mensagem , confirmação da operação 
	 * @throws Exception
	 */
	public String manipularExercicio(Exercicio exercicio) throws Exception{
		String mensagem = "";
		String erro = "";
		IExercicioDao dao = new ExercicioDao();
		exercicio.setNome(Validadora.verificarString(exercicio.getNome()));
		Validadora<Exercicio> v = new Validadora<Exercicio>(exercicio);
		erro = v.getMessage();
		
		if(erro.equalsIgnoreCase("")){
			Grupo grupo = exercicio.getGrupo();
			grupo.setCodigo(dao.buscarGrupo(grupo.getNome()));
			if (!dao.buscarExercicio(exercicio.getCodigo())){
				if (dao.buscarExercicio(exercicio.getNome()) == null){
					if(dao.adicionarExercicio(exercicio)){
						mensagem = "Exercicio : criado com sucesso !";
					}
				}else if (dao.reativarExercicio(exercicio.getNome(),0)){
					mensagem = "Exercicio : reativado com sucesso !";
				}else{
					erro = "Exercicio : já existente !";
					throw new Exception(erro);
				}
			}else{
				if (!dao.buscarExercicio(exercicio.getNome(),exercicio.getCodigo())){
					if (dao.alterarExercicio(exercicio.getCodigo(), exercicio)){
						mensagem = "Exercicio : atualizado com sucesso";
					}
				}
			}
		}else{
			mensagem = v.getMessage();
		}
		return mensagem;
	}


	public String excluirExercicio(List<Exercicio> lista) throws SQLException{
		String mensagem = "Exericio : foram removidos com sucesso!";
		boolean resultado = false;
		IExercicioDao dao = new  ExercicioDao();
		ITreinoDao daoTreino = new TreinoDao();
		ControleSerie controle = new ControleSerie();
		controle.removerSerie(lista);
		for (Exercicio e : lista){
			resultado = dao.excluirExercicio(e.getCodigo());
			if(!resultado){
				break;
			}
		}
		if(!resultado){
			mensagem = "Não foi possivel remover os exercicios!";
		}

		return mensagem;


	}

	public String buscarExercicioGrupo(Grupo g){
		String mensagem = "Erro ao buscar Exercicio";
		IExercicioDao dao = new ExercicioDao();
		if(dao.buscarExercicioGrupo(g) != null){
			mensagem = "Encontardos os exercicios";
		}else{
			mensagem = "Exercicio não foram encontrados";
		}
		return mensagem;
	}

	public Exercicio buscarExercicioNome(String nome){
		IExercicioDao dao = new ExercicioDao();
		return dao.buscarExercicio(nome);
	}

	public List<Grupo> listarGrupos(){
		return new ExercicioDao().listarGrupos();
	}

	public List<Exercicio> listarExercicios(String grupo, int personalizado)
	throws SQLException {
		IExercicioDao exercicioDao = new ExercicioDao();
		int ngrupo = exercicioDao.buscarGrupo(grupo);
		return exercicioDao.listarExercicios(ngrupo, personalizado);

	}

	/**
	 * Metodo responsavel por listar os exercicios que estão 
	 * disponiveis para adicionar no exercicio
	 * @param codigoTreino
	 * @param codigoGrupo
	 * @return lista dos exercicios ou Exception caso não acha
	 * @throws Exception 
	 */
	public List<Exercicio> listarExercicioDisponiveis
	(long codigoTreino,long codigoGrupo) throws Exception {
		IExercicioDao dao = new ExercicioDao();
		List<Exercicio> listar = dao.listarExercicioFora(codigoTreino, codigoGrupo);
		List<Exercicio> listarSemTreino = dao.listarExercicioSemTreino(codigoGrupo);
		listar.addAll(listarSemTreino);
		if(listar.size()<=0){
			String erro = "Não há exercicios disponiveis para este grupo muscular";
			throw new Exception(erro);
		}

		return listar;

	}

	public List<Exercicio> listarExercicioTreino(long codigoTreino) throws Exception{
		IExercicioDao dao = new ExercicioDao();
		List<Exercicio> listar = dao.listarExercicioTreino(codigoTreino);
		if(listar.size()<0){
			String erro = "Não há exercicios disponiveis para este grupo muscular";
			throw new Exception(erro);
		}

		return listar;
	}
	
	
	public List<Exercicio> buscarExercicioPasso(long codigoTreino) throws Exception{
		String erro = "Erro no banco de dados!";
		List<Exercicio> lista = new ArrayList<Exercicio>();
		try{
			IExercicioDao dao = new ExercicioDao();
			lista = dao.buscarExercicioPasso(codigoTreino);
			
			if(lista.size() == 0 ){
				erro = "Nenhum exercicio possui explicação!";
				throw new Exception(erro);	
			}
		}catch (Exception e){
			erro = e.getMessage();
			throw new Exception(erro);
			
		}
		return lista;
	}

	/**
		 * Metodo responsavel por carregar uma imagem referente a um passo 
		 * na execução passo a passo de um exercicio padrão 
		 * @param passo [passo de um determinado exercicio]
		 * @return id 
		 * @throws IllegalAccessException 
		 * @throws IllegalArgumentException 
		 */
		public int carregarImagem(Passo passo) throws IllegalArgumentException, IllegalAccessException {
			Field[] campos = R.raw.class.getFields();
			String nome = passo.getImagem();
			String nenhuma = "nenhuma";
			int resource = 0 ;
			
			for(Field campo : campos){
				String nomeCampo = campo.getName();
				if(nome.equalsIgnoreCase(nomeCampo)){
					resource = campo.getInt(campo);
					break;
				}else if (nomeCampo.equalsIgnoreCase(nenhuma)){
					resource = campo.getInt(campo);
				}
			}
			
			return resource;
		}
}
