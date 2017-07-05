package comunicacion;

import com.google.gson.Gson;

import estados.Estado;
import estados.EstadoBatalla;
import mensajeria.PaqueteBatalla;

public class ProcesadorBatalla extends Procesador {

	public ProcesadorBatalla(ContextoProcesador contextoProcesador, Gson gson) {
		super(contextoProcesador, gson);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String procesar(String cadenaLeida) {
		contextoProcesador.setPaqueteBatalla(gson.fromJson(cadenaLeida, PaqueteBatalla.class));
		contextoProcesador.getJuego().getPersonaje().setEstado(Estado.estadoBatalla);
		Estado.setEstado(null);
		contextoProcesador.getJuego().setEstadoBatalla(
				new EstadoBatalla(contextoProcesador.getJuego(), contextoProcesador.getPaqueteBatalla()));
		Estado.setEstado(contextoProcesador.getJuego().getEstadoBatalla());
		return null;
	}

}
