package comunicacion;

import com.google.gson.Gson;

import mensajeria.Comando;
import mensajeria.Paquete;

public class ProcesadorSalir extends Procesador {

	public ProcesadorSalir(ContextoProcesador contextoProcesador, Gson gson) {
		super(contextoProcesador, gson);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String procesar(String cadenaLeida) {
		// El usuario no pudo iniciar sesi�n
		contextoProcesador.getPaqueteUsuario().setInicioSesion(false);
		try {
			contextoProcesador.getSalida().writeObject(gson.toJson(new Paquete(Comando.DESCONECTAR), Paquete.class));
			contextoProcesador.getSocketCliente().close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
