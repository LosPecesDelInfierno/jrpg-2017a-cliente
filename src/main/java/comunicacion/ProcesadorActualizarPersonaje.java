package comunicacion;

import com.google.gson.Gson;

import mensajeria.PaquetePersonaje;

public class ProcesadorActualizarPersonaje extends Procesador {

	public ProcesadorActualizarPersonaje(ContextoProcesador contextoProcesador, Gson gson) {
		super(contextoProcesador, gson);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String procesar(String cadenaLeida) {
		contextoProcesador.setPaquetePersonaje((PaquetePersonaje) gson.fromJson(cadenaLeida, PaquetePersonaje.class));

		contextoProcesador.getPersonajesConectados().remove(contextoProcesador.getPaquetePersonaje().getId());
		contextoProcesador.getPersonajesConectados().put(contextoProcesador.getPaquetePersonaje().getId(),
				contextoProcesador.getPaquetePersonaje());

		if (contextoProcesador.getJuego().getPersonaje().getId() == contextoProcesador.getPaquetePersonaje().getId()) {
			contextoProcesador.getJuego().actualizarPersonaje();
			contextoProcesador.getJuego().getEstadoJuego().actualizarPersonaje();
		}
		return null;
	}

}
