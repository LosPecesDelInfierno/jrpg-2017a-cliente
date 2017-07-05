package comunicacion;

import com.google.gson.Gson;

import estados.Estado;
import estados.EstadoComercio;
import mensajeria.PaqueteComercio;

public class ProcesadorComercio extends Procesador {

	public ProcesadorComercio(ContextoProcesador contextoProcesador, Gson gson) {
		super(contextoProcesador, gson);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String procesar(String cadenaLeida) {
		contextoProcesador.setPaqueteComercio(gson.fromJson(cadenaLeida, PaqueteComercio.class));
		contextoProcesador.getJuego().getPersonaje().setEstado(Estado.estadoComercio);
		Estado.setEstado(null);
		contextoProcesador.getJuego().setEstadoComercio(new EstadoComercio(contextoProcesador.getJuego(),
				contextoProcesador.getPaqueteComercio()));
		Estado.setEstado(contextoProcesador.getJuego().getEstadoComercio());
		return null;
	}

}
