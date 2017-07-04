package comunicacion;

import javax.swing.JOptionPane;

import com.google.gson.Gson;

import mensajeria.Paquete;
import mensajeria.PaquetePersonaje;

public class ProcesadorInicioDeSesion extends Procesador {

	public ProcesadorInicioDeSesion(ContextoProcesador contextoProcesador, Gson gson) {
		super(contextoProcesador, gson);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String procesar(String cadenaLeida) {
		if (contextoProcesador.getPaquete().getMensaje().equals(Paquete.msjExito)) {

			// El usuario ya inicio sesión
			contextoProcesador.getPaqueteUsuario().setInicioSesion(true);

			// Recibo el paquete personaje con los datos
			contextoProcesador.setPaquetePersonaje((PaquetePersonaje) gson.fromJson(cadenaLeida, PaquetePersonaje.class));

		} else {
			if (contextoProcesador.getPaquete().getMensaje().equals(Paquete.msjFracaso))
				JOptionPane.showMessageDialog(null,
						"Error al iniciar sesión. Revise el usuario y la contraseña");

			// El usuario no pudo iniciar sesi�n
			contextoProcesador.getPaqueteUsuario().setInicioSesion(false);
		}
		return null;
	}

}
