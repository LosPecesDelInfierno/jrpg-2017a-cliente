package comunicacion;

import java.util.Map;

import com.google.gson.Gson;

import mensajeria.PaqueteDeMovimientos;
import mensajeria.PaqueteMovimiento;

public class ProcesadorMovimiento extends Procesador {

	public ProcesadorMovimiento(ContextoProcesador contextoProcesador, Gson gson) {
		super(contextoProcesador, gson);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String procesar(String cadenaLeida) {
		contextoProcesador.setUbicacionPersonajes((Map<Integer, PaqueteMovimiento>) gson
				.fromJson(cadenaLeida, PaqueteDeMovimientos.class).getPersonajes());
		return null;
	}

}
