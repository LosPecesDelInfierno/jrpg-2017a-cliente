package comunicacion;

import java.util.Map;

import com.google.gson.Gson;

import mensajeria.PaqueteDePersonajes;
import mensajeria.PaquetePersonaje;

public class ProcesadorConexion extends Procesador {

	public ProcesadorConexion(ContextoProcesador contextoProcesador, Gson gson) {
		super(contextoProcesador, gson);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String procesar(String cadenaLeida) {
		contextoProcesador.setPersonajesConectados((Map<Integer, PaquetePersonaje>) gson
				.fromJson(cadenaLeida, PaqueteDePersonajes.class).getPersonajes());
		return null;
	}

}
