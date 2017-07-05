package comunicacion;

import com.google.gson.Gson;

import estados.Estado;
import mensajeria.PaqueteFinalizarBatalla;

public class ProcesadorFinalizarBatalla extends Procesador{

	public ProcesadorFinalizarBatalla(ContextoProcesador contextoProcesador, Gson gson) {
		super(contextoProcesador, gson);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String procesar(String cadenaLeida) {
		contextoProcesador.setPaqueteFinalizarBatalla((PaqueteFinalizarBatalla) gson.fromJson(cadenaLeida,
				PaqueteFinalizarBatalla.class));
		contextoProcesador.getJuego().getPersonaje().setEstado(Estado.estadoJuego);
		Estado.setEstado(contextoProcesador.getJuego().getEstadoJuego());
		return null;
	}

	
}
