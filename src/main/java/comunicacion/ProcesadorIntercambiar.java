package comunicacion;

import com.google.gson.Gson;

import mensajeria.PaqueteIntercambio;

public class ProcesadorIntercambiar extends Procesador {

	public ProcesadorIntercambiar(ContextoProcesador contextoProcesador, Gson gson) {
		super(contextoProcesador, gson);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String procesar(String cadenaLeida) {
		contextoProcesador.setPaqueteIntercambio( gson.fromJson(cadenaLeida, PaqueteIntercambio.class));
		contextoProcesador.getJuego().getEstadoComercio().recibirPaqueteIntercambio(contextoProcesador.getPaqueteIntercambio());
		contextoProcesador.getJuego().getEstadoComercio().setMiTurno(true);
		contextoProcesador.getJuego().getEstadoComercio().actualizarBotonesActivos();
		return null;
	}

}
