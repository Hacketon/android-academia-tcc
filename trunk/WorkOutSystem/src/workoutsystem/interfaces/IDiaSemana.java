package workoutsystem.interfaces;
import java.util.List;
import workoutsystem.model.DiaSemana;

public interface IDiaSemana {
	
	public abstract List<DiaSemana> listarDias();
	
	public abstract int buscarCodigoDia(String Nome);
}
