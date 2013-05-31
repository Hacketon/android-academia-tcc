package workoutsystem.dao;

import java.sql.SQLException;
import java.util.List;

import workoutsystem.model.Especificacao;
import workoutsystem.model.Exercicio;
import workoutsystem.model.Ficha;
import workoutsystem.model.Treino;



public interface IFichaDao {

	/**
	 * Metodo responsavel por listar todas as fichas existentes
	 * @return lista de fichas
	 * @throws SQLException
	 */
	public List<Ficha> listarFichas() throws SQLException;
		
	/**
	 * Metodo responsavel pela cria��o de uma ficha
	 * @param ficha
	 * @return
	 */
	public abstract boolean inserirFicha(Ficha ficha) throws SQLException;
	
	
	/**
	 * Metodo responsavel pela busca de uma ficha atrav�s de 
	 * um nome
	 * @param nome 
	 * @return Ficha
	 */
	public Ficha buscarFicha(String nome) throws SQLException;
	/**
	 * Metodo responsavel pela exclus�o de uma ficha 
	 * @param codigoFicha
	 * @return
	 */
	public boolean excluirFicha(long codigoFicha) throws SQLException;
	
	/**
	 * Metodo responsavel pela exclus�o da especificacao de um exercicio 
	 * em um treino
	 * @param codigoTreino = long 
	 * @param codigoExercicio = long
	 * @return
	 * @throws SQLException
	 */
	public boolean excluirEspecificacao(long codigoTreino,long codigoExercicio)
	throws SQLException;
	
	public boolean setPerfil(int codigoPerfil) throws SQLException;
	
	/**
	 * Metodo responsavel pela busca da ficha atual do perfil cadastrado
	 * @return Ficha atual do usuario ou nulo , sen�o tiver ficha.
	 * @throws SQLException
	 */
	public Ficha buscarFichaAtual() throws SQLException;
	/**
	 * Metodo responsavel pela altera��o da ficha atual do perfil cadastrado
	 * @return true , alterado com sucesso , false quando n�o for possivel alterar
	 * @throws SQLException
	 */
	public boolean alterarFichaAtual(int codigoFicha) throws SQLException;
	/**
	 * Metodo responsavel pela busca de uma ficha atrav�s do seu codigo .
	 * @param i
	 * @return Ficha
	 * @throws SQLException
	 */
	public Ficha buscarFichaCodigo(long i) throws SQLException;

	/**
	 * Metodo responsavel pela desativa��o das fichas atuais
	 * @return true , caso consiga 
	 * @throws SQLException 
	 */
	public boolean desativarFichaAtual() throws SQLException;
	
	
	
	
	
	
	
	
}
