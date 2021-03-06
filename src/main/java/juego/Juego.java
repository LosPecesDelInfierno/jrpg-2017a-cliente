package juego;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.JOptionPane;
import cliente.Cliente;
import cliente.EscuchaMensajes;
import comunicacion.ContextoProcesador;
import dominio.Personaje;
import estados.Estado;
import estados.EstadoBatalla;
import estados.EstadoComercio;
import estados.EstadoJuego;
import mensajeria.PaqueteMensaje;
import mensajeria.PaqueteMovimiento;
import mensajeria.PaquetePersonaje;

public class Juego implements Runnable {

	private Pantalla pantalla;
	private final String NOMBRE;
	private final int ANCHO;
	private final int ALTO;

	private Thread hilo;
	private boolean corriendo;

	private BufferStrategy bs; // Estrategia para graficar mediante buffers
								// (Primero se "grafica" en el/los buffer/s y
								// finalmente en el canvas)
	private Graphics g;

	// Estados
	private Estado estadoJuego;
	private Estado estadoBatalla;
	private Estado estadoComercio;

	// HandlerMouse
	private HandlerMouse handlerMouse;

	// Camara
	private Camara camara;

	// Conexion
	private Cliente cliente;
	private EscuchaMensajes escuchaMensajes;
	private PaqueteMovimiento ubicacionPersonaje;

	private CargarRecursos cargarRecursos;

	public Juego(final String nombre, final int ancho, final int alto, Cliente cliente) {
		this.NOMBRE = nombre;
		this.ALTO = alto;
		this.ANCHO = ancho;
		this.cliente = cliente;

		// Inicializo la ubicacion del personaje
		ubicacionPersonaje = new PaqueteMovimiento();
		ubicacionPersonaje.setIdPersonaje(getPersonaje().getId());
		ubicacionPersonaje.setFrame(0);
		ubicacionPersonaje.setDireccion(6);

		// Creo el escucha de mensajes
		escuchaMensajes = new EscuchaMensajes(this);
		escuchaMensajes.start();

		handlerMouse = new HandlerMouse();

		iniciar();

		cargarRecursos = new CargarRecursos(cliente);
		cargarRecursos.start();
	}

	public void iniciar() { // Carga lo necesario para iniciar el juego
		pantalla = new Pantalla(NOMBRE, ANCHO, ALTO, cliente);

		pantalla.getCanvas().addMouseListener(handlerMouse);

		camara = new Camara(this, 0, 0);

		Personaje.cargarTablaNivel();
	}

	private void actualizar() { // Actualiza los objetos y sus posiciones

		if (Estado.getEstado() != null) {
			Estado.getEstado().actualizar();
		}
	}

	private void graficar() { // Grafica los objetos y sus posiciones
		bs = pantalla.getCanvas().getBufferStrategy();
		if (bs == null) { // Seteo una estrategia para el canvas en caso de que
							// no tenga una
			pantalla.getCanvas().createBufferStrategy(3);
			return;
		}

		g = bs.getDrawGraphics(); // Permite graficar el buffer mediante g

		g.clearRect(0, 0, ANCHO, ALTO); // Limpiamos la pantalla

		// Graficado de imagenes
		g.setFont(new Font("Book Antiqua", 1, 15));

		if (Estado.getEstado() != null) {
			Estado.getEstado().graficar(g);
		}

		// Fin de graficado de imagenes

		bs.show(); // Hace visible el pr�ximo buffer disponible
		g.dispose();
	}

	@Override
	public void run() { // Hilo principal del juego

		int fps = 60; // Cantidad de actualizaciones por segundo que se desean
		double tiempoPorActualizacion = 1000000000 / fps; // Cantidad de
															// nanosegundos en
															// FPS deseados
		double delta = 0;
		long ahora;
		long ultimoTiempo = System.nanoTime();
		long timer = 0; // Timer para mostrar fps cada un segundo
		int actualizaciones = 0; // Cantidad de actualizaciones que se realizan
									// realmente

		while (corriendo) {
			ahora = System.nanoTime();
			delta += (ahora - ultimoTiempo) / tiempoPorActualizacion; // Calculo
																		// para
																		// determinar
																		// cuando
																		// realizar
																		// la
																		// actualizacion
																		// y el
																		// graficado
			timer += ahora - ultimoTiempo; // Sumo el tiempo transcurrido hasta
											// que se acumule 1 segundo y
											// mostrar los FPS
			ultimoTiempo = ahora; // Para las proximas corridas del bucle

			if (delta >= 1) {
				actualizar();
				graficar();
				actualizaciones++;
				delta--;
			}

			if (timer >= 1000000000) { // Si paso 1 segundo muestro los FPS
				pantalla.getFrame().setTitle(NOMBRE + " | " + "FPS: " + actualizaciones);
				actualizaciones = 0;
				timer = 0;
			}
		}

		stop();
	}

	public synchronized void start() { // Inicia el juego
		if (corriendo)
			return;

		estadoJuego = new EstadoJuego(this);
		Estado.setEstado(estadoJuego);
		pantalla.mostrar();
		corriendo = true;
		hilo = new Thread(this);
		hilo.start();
	}

	public synchronized void stop() { // Detiene el juego
		if (!corriendo)
			return;
		try {
			corriendo = false;
			hilo.join();
		} catch (InterruptedException e) {
			JOptionPane.showMessageDialog(null, "Fallo al intentar detener el juego.");
			e.printStackTrace();
		}
	}

	public int getAncho() {
		return ANCHO;
	}

	public int getAlto() {
		return ALTO;
	}

	public HandlerMouse getHandlerMouse() {
		return handlerMouse;
	}

	public Camara getCamara() {
		return camara;
	}

	public EstadoJuego getEstadoJuego() {
		return (EstadoJuego) estadoJuego;
	}

	public EstadoBatalla getEstadoBatalla() {
		return (EstadoBatalla) estadoBatalla;
	}

	public void setEstadoBatalla(EstadoBatalla estadoBatalla) {
		this.estadoBatalla = estadoBatalla;
	}

	public EstadoComercio getEstadoComercio() {
		return (EstadoComercio) estadoComercio;
	}

	public void setEstadoComercio(EstadoComercio estadoComercio) {
		this.estadoComercio = estadoComercio;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public EscuchaMensajes getEscuchaMensajes() {
		return escuchaMensajes;
	}

	public PaquetePersonaje getPersonaje() {
		return getCliente().getPaquetePersonaje();
	}

	public PaqueteMovimiento getUbicacionPersonaje() {
		return ubicacionPersonaje;
	}

	public void actualizarUsuariosChat() {
		pantalla.getMessengetClient().setUsuariosEnLista();
	}
	
	public void recibirMensaje(PaqueteMensaje paqueteMensaje) {
		// IDEA: Cambiar color del texto según tipo (ej: difusión en negro,
		// privado en amarillo)
		// TODO: Mandar esto donde corresponda.
		String emisor = escuchaMensajes.getPersonajesConectados().get(paqueteMensaje.getIdEmisor()).getNombre();
		pantalla.getMessengetClient().recibirMensaje(paqueteMensaje, emisor);
	}
}
