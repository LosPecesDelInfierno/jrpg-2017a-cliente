package comunicacion;

import com.google.gson.Gson;

import mensajeria.PaqueteAtacar;

public class ProcesadorAtacar extends Procesador {

	public ProcesadorAtacar(ContextoProcesador contextoProcesador, Gson gson) {
		super(contextoProcesador, gson);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String procesar(String cadenaLeida) {
		contextoProcesador.setPaqueteAtacar((PaqueteAtacar) gson.fromJson(cadenaLeida, PaqueteAtacar.class));
		contextoProcesador.getJuego().getEstadoBatalla().getEnemigo().refreshAtacante(contextoProcesador.getPaqueteAtacar());
		contextoProcesador.getJuego().getEstadoBatalla().getPersonaje().refreshAtacado(contextoProcesador.getPaqueteAtacar());
		contextoProcesador.getJuego().getEstadoBatalla().setMiTurno(true);
		return null;
	}

}
