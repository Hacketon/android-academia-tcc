package workoutsystem.dao;

import java.util.List;

import workoutsystem.model.Medicao;
import workoutsystem.model.Medida;

public interface IMedidaDao {
/**
 * Metodo que buscará o codigo de medição
 * @param nome
 * @return
 */
	public abstract int buscarMedida(String nome, String lado);
	
	
	
	/**
	 * Metodo que trará o valor da medicao , dependendo do codigo informado
	 * @param codigo
	 * @return
	 */
	public abstract Double buscarValorMedicao(int codigo);
	/**
	 * Metodo que TUDO de medicao
	 * @param codigo
	 * @return
	 */

	public abstract List<Medicao> buscarMedicao(int codigo);

	/**
	 * metodo que ira adicionar medições por uma lista 
	 * @param medicao
	 * @return
	 */
	public abstract boolean adicionarMedicao(List<Medicao> medicao);
/**
 * metodo que irá alterar as medicoes apartit da data e codigomedida
 * @param medicoes
 * @return
 */
	public abstract boolean alterarMedicao(List<Medicao> medicoes);
	
	/**
	 * metodo que ira aletar as ultimas medicoes de cada grupo muscular
	 * @param m
	 * @return
	 */
	public abstract boolean alterarUltimaMedicao(List<Medicao> m);
	/**
	 * metodo que trará todas as medidas jogando em uma lista 
	 * @return
	 */
	public abstract List<Medida> buscarMedidas();

	/**
	 * metodo que irá excluir todas medicoes do perfil
	 * @param codigo
	 * @return
	 */
	public abstract boolean excluirMedicoes(int codigo);
	/**
	 * Metodo responsavel pela verificacao se um perfil possui 
	 * medicoes cadastradas
	 * @param codigo (do perfil)
	 * @return true caso possui , caso contrario false
	 */
	public abstract boolean verificarMedicao(int codigo);
	/**
	 * Metodo responsavel pela busca das três ultimas medicoes de uma determinada medida 
	 * e um determinado perfil 
	 * @param codigoPerfil
	 * @param codigoMedicao
	 * @return List (medicoes)
	 */
	public abstract List<Medicao> ultimasMedicoes(int codigoPerfil,int codigoMedida);
	/**
	 * Metodo responsavel pela busca das ultima medicao medicoes de uma determinada medida 
	 * e um determinado perfil 
	 * @param codigo (Perfil)
	 * @return List (medida)
	 */
	public abstract List<Medida> ultimaMedicao(int codigo) ;

/**
 * 
 * Metodo para buscar lista de medicao pelo codigo da medida
 * @param codigo
 * @return
 */

	public abstract List<Medicao> buscarListaMedicao(int codigo,int codigoPerfi);




}

