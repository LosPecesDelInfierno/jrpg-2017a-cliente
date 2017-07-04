package cliente;

import java.io.ObjectInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import com.google.gson.Gson;

import estados.Estado;
import estados.EstadoBatalla;
import estados.EstadoComercio;
import juego.Juego;
import mensajeria.Comando;
import mensajeria.Paquete;
import mensajeria.PaqueteAtacar;
import mensajeria.PaqueteBatalla;
import mensajeria.PaqueteComercio;
import mensajeria.PaqueteDeMovimientos;
import mensajeria.PaqueteDePersonajes;
import mensajeria.PaqueteFinalizarBatalla;
import mensajeria.PaqueteIntercambio;
import mensajeria.PaqueteMensaje;
import mensajeria.PaqueteMovimiento;
import mensajeria.PaquetePersonaje;

public class EscuchaMensajes extends Thread {

	private Juego juego;
	private Cliente cliente;
	private ObjectInputStream entrada;
	private final Gson gson = new Gson();

	private Map<Integer, PaqueteMovimiento> ubicacionPersonajes;
	private Map<Integer, PaquetePersonaje> personajesConectados;

	public EscuchaMensajes(Juego juego) {
		this.juego = juego;
		cliente = juego.getCliente();
		entrada = cliente.getEntrada();
	}

	public void run() {

		try {

			Paquete paquete;
			PaqueteMensaje paqueteMensaje;
			PaquetePersonaje paquetePersonaje;
			PaqueteMovimiento personaje;
			PaqueteBatalla paqueteBatalla;
			PaqueteAtacar paqueteAtacar;
			PaqueteFinalizarBatalla paqueteFinalizarBatalla;
			PaqueteComercio paqueteComercio;
			PaqueteIntercambio paqueteIntercambio;
			personajesConectados = new HashMap<>();
			ubicacionPersonajes = new HashMap<>();

			while (true) {

				String objetoLeido = (String) entrada.readObject();

				paquete = gson.fromJson(objetoLeido, Paquete.class);

				switch (paquete.getComando()) {

				case Comando.CONEXION:
					personajesConectados = (Map<Integer, PaquetePersonaje>) gson
							.fromJson(objetoLeido, PaqueteDePersonajes.class).getPersonajes();
					break;

				case Comando.MOVIMIENTO:
					ubicacionPersonajes = (Map<Integer, PaqueteMovimiento>) gson
							.fromJson(objetoLeido, PaqueteDeMovimientos.class).getPersonajes();
					break;

				case Comando.BATALLA:
					paqueteBatalla = gson.fromJson(objetoLeido, PaqueteBatalla.class);
					juego.getPersonaje().setEstado(Estado.estadoBatalla);
					Estado.setEstado(null);
					juego.setEstadoBatalla(new EstadoBatalla(juego, paqueteBatalla));
					Estado.setEstado(juego.getEstadoBatalla());
					break;

				case Comando.ATACAR:
					paqueteAtacar = (PaqueteAtacar) gson.fromJson(objetoLeido, PaqueteAtacar.class);
					juego.getEstadoBatalla().getEnemigo().refreshAtacante(paqueteAtacar);
					juego.getEstadoBatalla().getPersonaje().refreshAtacado(paqueteAtacar);
					juego.getEstadoBatalla().setMiTurno(true);
					break;

				case Comando.FINALIZARBATALLA:
					paqueteFinalizarBatalla = (PaqueteFinalizarBatalla) gson.fromJson(objetoLeido,
							PaqueteFinalizarBatalla.class);
					juego.getPersonaje().setEstado(Estado.estadoJuego);
					Estado.setEstado(juego.getEstadoJuego());
					break;
					
				case Comando.COMERCIO:
					paqueteComercio = gson.fromJson(objetoLeido, PaqueteComercio.class);
					juego.getPersonaje().setEstado(Estado.estadoComercio);
					Estado.setEstado(null);
					juego.setEstadoComercio(new EstadoComercio(juego, paqueteComercio));
					Estado.setEstado(juego.getEstadoComercio());
					break;

				case Comando.INTERCAMBIAR:
					paqueteIntercambio = gson.fromJson(objetoLeido, PaqueteIntercambio.class);
					juego.getEstadoComercio().recibirPaqueteIntercambio(paqueteIntercambio);
					juego.getEstadoComercio().setMiTurno(true);
					juego.getEstadoComercio().actualizarBotonesActivos();
					juego.getEstadoComercio().proponerIntercambio();
					break;
					
				case Comando.MENSAJE:
					paqueteMensaje = gson.fromJson(objetoLeido, PaqueteMensaje.class);
					juego.recibirMensaje(paqueteMensaje);
					break;

				case Comando.ACTUALIZARPERSONAJE:
					paquetePersonaje = (PaquetePersonaje) gson.fromJson(objetoLeido, PaquetePersonaje.class);

					personajesConectados.remove(paquetePersonaje.getId());
					personajesConectados.put(paquetePersonaje.getId(), paquetePersonaje);

					if (juego.getPersonaje().getId() == paquetePersonaje.getId()) {
						juego.actualizarPersonaje();
						juego.getEstadoJuego().actualizarPersonaje();
					}
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Fallo la conexi�n con el servidor.");
			e.printStackTrace();
		}
	}

	public Map<Integer, PaqueteMovimiento> getUbicacionPersonajes() {
		return ubicacionPersonajes;
	}

	public Map<Integer, PaquetePersonaje> getPersonajesConectados() {
		return personajesConectados;
	}
}