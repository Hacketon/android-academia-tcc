package workoutsystem.interfaces;
import java.util.List;
import workoutsystem.model.Frequencia;

public interface IDiaSemana {
	
	public abstract List<Frequencia> listarDias();
	
	public abstract int buscarCodigoDia(String Nome);
}
