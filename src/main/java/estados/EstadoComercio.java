package estados;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

import javax.swing.JOptionPane;

import com.google.gson.Gson;

import dominio.Item;
import interfaz.MenuComercio;
import juego.Juego;
import mensajeria.PaqueteComercio;
import mensajeria.PaqueteIntercambio;
import mensajeria.PaquetePersonaje;
import mundo.Mundo;
import recursos.Recursos;

public class EstadoComercio extends Estado {

	private Mundo mundo;
	private int[] posMouse;
	private PaquetePersonaje paquetePersonaje;
	private PaquetePersonaje paqueteEnemigo;
	private PaqueteIntercambio paqueteIntercambio;

	private boolean miTurno;

	private Gson gson = new Gson();

	private MenuComercio menuComercio;

	public EstadoComercio(Juego juego, PaqueteComercio paqueteComercio) {
		super(juego);
		mundo = new Mundo(juego, "recursos/mundoComercio.txt", "");
		miTurno = paqueteComercio.isMiTurno();

		paquetePersonaje = juego.getEscuchaMensajes().getPersonajesConectados().get(paqueteComercio.getId());
		paqueteEnemigo = juego.getEscuchaMensajes().getPersonajesConectados().get(paqueteComercio.getIdEnemigo());

		menuComercio = new MenuComercio(miTurno, paquetePersonaje, paqueteEnemigo);

		paqueteIntercambio = new PaqueteIntercambio();
		paqueteIntercambio.setId(paqueteComercio.getId());
		paqueteIntercambio.setIdEnemigo(paqueteComercio.getIdEnemigo());

		// limpio la accion del mouse
		juego.getHandlerMouse().setNuevoClick(false);

	}

	@Override
	public void actualizar() {

		juego.getCamara().setxOffset(-350);
		juego.getCamara().setyOffset(150);

		if (miTurno) {

			int boton;

			if (juego.getHandlerMouse().getNuevoClickDerecho()) {
				posMouse = juego.getHandlerMouse().getPosMouse();

				boton = menuComercio.getBotonClickeado(posMouse[0], posMouse[1]);

				if (boton >= 0 && boton <= 15) {
					menuComercio.printInfoItem(boton);
				}

				juego.getHandlerMouse().setNuevoClickDerecho(false);
			}

			if (juego.getHandlerMouse().getNuevoClick()) {
				posMouse = juego.getHandlerMouse().getPosMouse();

				boton = menuComercio.getBotonClickeado(posMouse[0], posMouse[1]);

				if (boton >= 0 && boton <= 15) {
					menuComercio.printInfoItem(boton);
					menuComercio.clickEnBoton(boton);
				}

				// boton aceptar
				if (boton == menuComercio.ACEPTAR) {
					// TODO: por el momento intercambio turnos para probar
					armarPaqueteIntercambio();
					enviarPaqueteIntercambio();
					setMiTurno(false);
				}

				juego.getHandlerMouse().setNuevoClick(false);
			}
		}

	}

	@Override
	public void graficar(Graphics g) {

		g.setColor(Color.BLACK);
		g.fillRect(0, 0, juego.getAncho(), juego.getAlto());
		mundo.graficar(g);

		g.drawImage(Recursos.personaje.get(paquetePersonaje.getRaza()).get(2)[0], 0, 300, 256, 256, null);
		g.drawImage(Recursos.personaje.get(paqueteEnemigo.getRaza()).get(6)[0], 0, 50, 256, 256, null);

		mundo.graficarObstaculos(g);
		menuComercio.graficar(g);

		g.setColor(Color.GREEN);
	}

	public void recibirPaqueteIntercambio(PaqueteIntercambio paquete) {
		paqueteIntercambio.setId(paquete.getIdEnemigo());
		paqueteIntercambio.setIdEnemigo(paquete.getId());
		for(int i = 0; i < 8; i++) {
			paqueteIntercambio.setSeleccionadoPersonaje(i, paquete.getSeleccionadoEnemigo(i));
			paqueteIntercambio.setSeleccionadoEnemigo(i, paquete.getSeleccionadoPersonaje(i));
		}
	}

	public void enviarPaqueteIntercambio() {
		try {
			juego.getCliente().getSalida().writeObject(gson.toJson(paqueteIntercambio));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Fallo la conexion con el servidor.");
			e.printStackTrace();
		}
	}

	public void armarPaqueteIntercambio() {
		for (int i = 0; i < 8; i++) {
			paqueteIntercambio.setSeleccionadoEnemigo(i, menuComercio.getEstadoBoton(i));
		}
		
		for (int i = 8; i < 16; i++) {
			paqueteIntercambio.setSeleccionadoPersonaje(i-8, menuComercio.getEstadoBoton(i));
		}
	}

	public void setMiTurno(boolean miTurno) {
		this.miTurno = miTurno;
		menuComercio.setHabilitado(miTurno);
	}

	public void actualizarBotonesActivos() {
		// el Personaje del paquete que recibi es mi enemigo
		for (int i = 0; i < 8; i++) {
			menuComercio.setEstadoBoton(i, paqueteIntercambio.getSeleccionadoEnemigo(i));
		}
		for (int i = 8; i < 16; i++) {
			menuComercio.setEstadoBoton(i, paqueteIntercambio.getSeleccionadoPersonaje(i - 8));
		}

	}

}
