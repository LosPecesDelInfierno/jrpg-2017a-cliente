package comunicacion;

import java.util.Date;

import javax.swing.JOptionPane;

import com.google.gson.Gson;

import frames.MenuCreacionPj;
import mensajeria.Paquete;

public class ProcesadorRegistro extends Procesador {

	public ProcesadorRegistro(ContextoProcesador contextoProcesador, Gson gson) {
		super(contextoProcesador, gson);
	}

	@Override
	public String procesar(String cadenaLeida) {
		if (contextoProcesador.getPaquete().getMensaje().equals(Paquete.msjExito)) {
			MenuCreacionPj menuCreacionPJ = new MenuCreacionPj(contextoProcesador.getCliente(),
					contextoProcesador.getPaquetePersonaje(),contextoProcesador);
			
			menuCreacionPJ.setVisible(true);
			
			// aca deberia esperar termine el menu creacion.
			do {
			//No hago nada!!!!
			} while(!contextoProcesador.getPaqueteUsuario().isInicioSesion());
			
		} else {
			if (contextoProcesador.getPaquete().getMensaje().equals(Paquete.msjFracaso))
				JOptionPane.showMessageDialog(null, "No se pudo registrar.");

			// El usuario no pudo iniciar sesi�n
			contextoProcesador.getPaqueteUsuario().setInicioSesion(false);
		}

		return null;
	}

}
