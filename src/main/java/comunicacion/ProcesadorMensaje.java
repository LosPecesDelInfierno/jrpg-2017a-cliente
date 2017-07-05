package comunicacion;

import com.google.gson.Gson;

import mensajeria.PaqueteMensaje;

public class ProcesadorMensaje extends Procesador {

	public ProcesadorMensaje(ContextoProcesador contextoProcesador, Gson gson) {
		super(contextoProcesador, gson);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String procesar(String cadenaLeida) {
		contextoProcesador.setPaqueteMensaje(gson.fromJson(cadenaLeida, PaqueteMensaje.class));
		contextoProcesador.getJuego().recibirMensaje(contextoProcesador.getPaqueteMensaje());
		return null;
	}

}
