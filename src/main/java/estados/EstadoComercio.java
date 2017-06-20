package estados;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Map;

import com.google.gson.Gson;

import dominio.Item;
import interfaz.MenuComercio;
import juego.Juego;
import mensajeria.PaqueteComercio;
import mensajeria.PaquetePersonaje;
import mundo.Mundo;
import recursos.Recursos;

public class EstadoComercio extends Estado {

	private Mundo mundo;
	private int[] posMouse;
	private PaquetePersonaje paquetePersonaje;
	private PaquetePersonaje paqueteEnemigo;
//	private PaqueteAtacar paqueteAtacar;
//	private PaqueteFinalizarBatalla paqueteFinalizarBatalla;
	private boolean miTurno;
	private boolean haySpellSeleccionada;
	private boolean seRealizoAccion;

	private Gson gson = new Gson();

//	private BufferedImage miniaturaPersonaje;
//	private BufferedImage miniaturaEnemigo;

	private MenuComercio menuComercio;

	public EstadoComercio(Juego juego, PaqueteComercio paqueteComercio) {
		super(juego);
		mundo = new Mundo(juego, "recursos/mundoComercio.txt", "");
		miTurno = paqueteComercio.isMiTurno();

		paquetePersonaje = juego.getEscuchaMensajes().getPersonajesConectados().get(paqueteComercio.getId());
		paqueteEnemigo = juego.getEscuchaMensajes().getPersonajesConectados().get(paqueteComercio.getIdEnemigo());

		menuComercio = new MenuComercio(miTurno, paquetePersonaje, paqueteEnemigo);

//		miniaturaEnemigo = Recursos.personaje.get(enemigo.getNombreRaza()).get(5)[0];
//		miniaturaPersonaje = Recursos.personaje.get(personaje.getNombreRaza()).get(5)[0];

//		paqueteFinalizarBatalla = new PaqueteFinalizarBatalla();
//		paqueteFinalizarBatalla.setId(personaje.getIdPersonaje());
//		paqueteFinalizarBatalla.setIdEnemigo(enemigo.getIdPersonaje());

		// por defecto batalla perdida
//		juego.getEstadoJuego().setHaySolicitud(true, juego.getPersonaje(), MenuInfoPersonaje.menuPerderBatalla);

		// limpio la accion del mouse
		juego.getHandlerMouse().setNuevoClick(false);

	}

	@Override
	public void actualizar() {

		juego.getCamara().setxOffset(-350);
		juego.getCamara().setyOffset(150);

		seRealizoAccion = false;
		haySpellSeleccionada = false;

		if (miTurno) {

			if (juego.getHandlerMouse().getNuevoClick()) {
				posMouse = juego.getHandlerMouse().getPosMouse();

				//TODO: Capturar clics en menuComercio
				
				if (haySpellSeleccionada && seRealizoAccion) {
					//TODO: Armar paqueteIntercambiar
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
	
}
