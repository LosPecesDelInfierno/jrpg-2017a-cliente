package cliente;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import javax.swing.JOptionPane;

import com.google.gson.Gson;

import comunicacion.ComandoDesconocidoException;
import comunicacion.ContextoProcesador;
import comunicacion.Procesador;
import comunicacion.ProcesadorFactory;
import frames.*;
import juego.Juego;
import mensajeria.Comando;
import mensajeria.Paquete;
import mensajeria.PaqueteIntercambio;
import mensajeria.PaquetePersonaje;
import mensajeria.PaqueteUsuario;

public class Cliente extends Thread {

	private Socket cliente;
	private String miIp;
	private ContextoProcesador contexto;
	private ObjectInputStream entrada;
	private ObjectOutputStream salida;

	// Objeto gson
	private final Gson gson = new Gson();

	// Acciones que realiza el usuario
	private int accion;

	// Ip y puerto
	private String ip;
	private int puerto;

	public int getAccion() {
		return accion;
	}

	public void setAccion(int accion) {
		this.accion = accion;
	}

	private Juego wome;
	private MenuCarga menuCarga;

	public Cliente(String ip, int puerto) {

		try {
			cliente = new Socket(ip, puerto);
			miIp = cliente.getInetAddress().getHostAddress();
			entrada = new ObjectInputStream(cliente.getInputStream());
			salida = new ObjectOutputStream(cliente.getOutputStream());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Fallo al iniciar la aplicación. Revise la conexión con el servidor.");
			System.exit(1);
			e.printStackTrace();
		}
	}

	public Cliente() {

		Scanner sc;

		try {
			sc = new Scanner(new File("config.txt"));
			ip = sc.nextLine();
			puerto = sc.nextInt();
			sc.close();
		} catch (FileNotFoundException e) {
			JOptionPane.showMessageDialog(null, "No se ha encontrado el archivo de configuración config.txt");
			e.printStackTrace();
		}

		try {
			cliente = new Socket(ip, puerto);
			miIp = cliente.getInetAddress().getHostAddress();
			entrada = new ObjectInputStream(cliente.getInputStream());
			salida = new ObjectOutputStream(cliente.getOutputStream());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Fallo al iniciar la aplicación. Revise la conexión con el servidor.");
			System.exit(1);
			e.printStackTrace();
		}
	}

	public void run() {
		synchronized (this) {
			try {
				this.contexto = new ContextoProcesador(entrada, salida);
				while (!getPaqueteUsuario().isInicioSesion()) {
					new MenuJugar(this).setVisible(true);
					wait();

					switch (getAccion()) {
					case Comando.REGISTRO:
						getPaqueteUsuario().setComando(Comando.REGISTRO);
						break;
					case Comando.INICIOSESION:
						getPaqueteUsuario().setComando(Comando.INICIOSESION);
						break;
					case Comando.SALIR:
						getPaqueteUsuario().setIp(getMiIp());
						getPaqueteUsuario().setComando(Comando.DESCONECTAR);
						break;
					}
					salida.writeObject(gson.toJson(getPaqueteUsuario()));

					if (getAccion() == Comando.SALIR) {
						this.cliente.close();
						System.exit(1);
					}

					String cadenaLeida = (String) entrada.readObject();
					Procesador proceso = ProcesadorFactory.crear(gson.fromJson(cadenaLeida, Paquete.class).getComando(),
							contexto, gson);
					proceso.procesar(cadenaLeida);
				}

				// Creo un paquete con el comando mostrar mapas
				getPaquetePersonaje().setComando(Comando.MOSTRARMAPAS);

				// Abro el menu de eleccion del mapa
				MenuMapas menuElegirMapa = new MenuMapas(this);
				menuElegirMapa.setVisible(true);

				// Espero a que el usuario elija el mapa
				wait();

				// Establezco el mapa en el paquete personaje
				getPaquetePersonaje().setIp(miIp);

				// Le envio el paquete con el mapa seleccionado
				salida.writeObject(gson.toJson(getPaquetePersonaje()));

				// Instancio el juego y cargo los recursos
				wome = new Juego("World Of the Middle Earth", 800, 600, this, getPaquetePersonaje());

				// Muestro el menu de carga
				menuCarga = new MenuCarga(this);
				menuCarga.setVisible(true);

				// Espero que se carguen todos los recursos
				wait();

				// Inicio el juego
				wome.start();

				// Finalizo el menu de carga
				menuCarga.dispose();

			} catch (IOException | InterruptedException | ClassNotFoundException | ComandoDesconocidoException e) {
				JOptionPane.showMessageDialog(null, "Fallo la conexión con el servidor durante el inicio de sesión.");
				System.exit(1);
				e.printStackTrace();
			}
		}

	}

	public Socket getSocket() {
		return cliente;
	}

	public void setSocket(Socket cliente) {
		this.cliente = cliente;
	}

	public String getMiIp() {
		return miIp;
	}

	public void setMiIp(String miIp) {
		this.miIp = miIp;
	}

	public ObjectInputStream getEntrada() {
		return entrada;
	}

	public void setEntrada(ObjectInputStream entrada) {
		this.entrada = entrada;
	}

	public ObjectOutputStream getSalida() {
		return salida;
	}

	public void setSalida(ObjectOutputStream salida) {
		this.salida = salida;
	}

	public PaqueteUsuario getPaqueteUsuario() {
		return contexto.getPaqueteUsuario();
	}

	public PaquetePersonaje getPaquetePersonaje() {
		return contexto.getPaquetePersonaje();
	}

	public Juego getJuego() {
		return wome;
	}

	public MenuCarga getMenuCarga() {
		return menuCarga;
	}
}
