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
	 * Metodo responsavel por listar treinos a partir de uma ficha
	 * @param codigo
	 * @return lista de treinos
	 * @throws SQLException
	 */
	public List<Treino> listarTreinos(long codigo) throws SQLException;
	/**
	 * Metodo responsavel por listar as especificacoes de um exercicio 
	 * a partir de um treino
	 * @return lista de especificacao
	 * @throws SQLException
	 */
	public List<Especificacao> listarEspecificacao(long l,long m,
			long n)
	throws SQLException;
	
	
	/**
	 * Metodo responsavel por inserir um treino e relacionar com uma ficha
	 * @param treino
	 * @return
	 */
	public abstract boolean inserirTreino(Treino treino) throws SQLException;
	/**
	 * Metodo responsavel pela criação de uma ficha
	 * @param ficha
	 * @return
	 */
	public abstract boolean inserirFicha(Ficha ficha) throws SQLException;
	/**
	 * Metodo responsavel por adicionar a especificacao um exercicio em uma ficha
	 * @param especificacao
	 * @return
	 */
	public abstract boolean inserirEspecificacao(Especificacao especificacao) throws SQLException;
	
	/**
	 * Metodo responsavel pela busca de uma ficha através de 
	 * um nome
	 * @param nome 
	 * @return Ficha
	 */
	public Ficha buscarFicha(String nome) throws SQLException;
	/**
	 * Metodo responsavel pela exclusão de uma ficha 
	 * @param codigoFicha
	 * @return
	 */
	public boolean excluirFicha(long codigoFicha) throws SQLException;
	/**
	 * Metodo responsavel pela exclusão de um treino separadamente
	 * @param codigoTreino
	 * @param codigoFicha
	 * @return
	 * @throws SQLException
	 */
	public boolean excluirTreino(long codigoTreino, long codigoFicha) throws SQLException;
	/**
	 * Metodo responsavel pela exclusão da especificacao de um treino 
	 * @param codigoTreino
	 * @return
	 * @throws SQLException
	 */
	public boolean excluirEspecificacao(long codigoTreino) throws SQLException;
	public boolean setPerfil(int codigoPerfil) throws SQLException;
	/**
	 * Metodo responsavel pela reordenação dos treinos de uma ficha
	 * @param ordem
	 * @param codigoTreino
	 * @return 
	 * @throws SQLException 
	 */
	public boolean reordenarTreino(int ordem, long codigoTreino) throws SQLException;
	
	/**
	 * Metodo responsavel pela busca da ficha atual do perfil cadastrado
	 * @param codigoPerfil
	 * @return Ficha atual do usuario ou nulo , senão tiver ficha.
	 * @throws SQLException
	 */
	public Ficha buscarFichaAtual(int codigoPerfil) throws SQLException;
	/**
	 * Metodo responsavel pela alteração da ficha atual do perfil cadastrado
	 * @return true , alterado com sucesso , false quando não for possivel alterar
	 * @throws SQLException
	 */
	public boolean alterarFichaAtual(int codigoFicha) throws SQLException;
	/**
	 * Metodo responsavel pela busca de uma ficha através do seu codigo .
	 * @param i
	 * @return Ficha
	 * @throws SQLException
	 */
	public Ficha buscarFichaCodigo(long i) throws SQLException;
	public boolean verificarExercicio(long codigoExercicio) throws SQLException;
	/**
	 * Metodo responsavel pela busca de um treino em um determinada ficha
	 * @param nomeTreino
	 * @param codigoFicha
	 * @return true = se o treino existe caso contrario false
	 * @throws SQLException 
	 */
	public boolean buscarTreino(String nomeTreino, long codigoFicha) throws SQLException;
	
	/**
	 * Metodo responsavel pela alteração do nome dos treinos
	 * @param nomeTreino
	 * @param codigoFicha
	 * @param codigoTreino 
	 * @return
	 */
	public boolean alterarNomeTreino(String nomeTreino, long codigoFicha, long codigoTreino) throws SQLException;
	/**
	 * Metodo responsavel pela busca da quantidade de treinos em uma determinada ficha
	 * @param codigoFicha
	 * @return
	 */
	public int buscarQuantidadeTreino(long codigoFicha) throws SQLException;
	
	
	
	
}
