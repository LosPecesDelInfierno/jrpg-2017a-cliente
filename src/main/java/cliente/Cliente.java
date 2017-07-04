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
import mensajeria.PaquetePersonaje;
import mensajeria.PaqueteUsuario;

public class Cliente extends Thread {

	private Socket socketCliente;
	private String miIp;
	private ObjectInputStream entrada;
	private ObjectOutputStream salida;

	// Objeto gson
	private final Gson gson = new Gson();

	// Paquete usuario y paquete personaje
	private PaqueteUsuario paqueteUsuario;
	private PaquetePersonaje paquetePersonaje;

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
			socketCliente = new Socket(ip, puerto);
			miIp = socketCliente.getInetAddress().getHostAddress();
			entrada = new ObjectInputStream(socketCliente.getInputStream());
			salida = new ObjectOutputStream(socketCliente.getOutputStream());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Fallo al iniciar la aplicación. Revise la conexión con el servidor.");
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
			socketCliente = new Socket(ip, puerto);
			miIp = socketCliente.getInetAddress().getHostAddress();
			entrada = new ObjectInputStream(socketCliente.getInputStream());
			salida = new ObjectOutputStream(socketCliente.getOutputStream());
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Fallo al iniciar la aplicación. Revise la conexión con el servidor.");
			System.exit(1);
			e.printStackTrace();
		}
	}

	public void run() {
		synchronized (this) {
			try {

				// Creo el paquete que le voy a enviar al servidor
				paqueteUsuario = new PaqueteUsuario();

				while (!paqueteUsuario.isInicioSesion()) {

					// Muestro el men� principal
					new MenuJugar(this).setVisible(true);

					// Creo los paquetes que le voy a enviar al servidor
					paqueteUsuario = new PaqueteUsuario();
					paquetePersonaje = new PaquetePersonaje();

					// Espero a que el usuario seleccione alguna accion
					wait();

					switch (getAccion()) {

					case Comando.REGISTRO:
						paqueteUsuario.setComando(Comando.REGISTRO);
						break;
					case Comando.INICIOSESION:
						paqueteUsuario.setComando(Comando.INICIOSESION);
						break;
					case Comando.SALIR:
						paqueteUsuario.setIp(getMiIp());
						paqueteUsuario.setComando(Comando.SALIR);
						break;
					}

					// Le envio el paquete al servidor
					salida.writeObject(gson.toJson(paqueteUsuario));

					// Recibo el paquete desde el servidor
					String cadenaLeida = (String) entrada.readObject();
					Paquete paquete = gson.fromJson(cadenaLeida, Paquete.class);
					ContextoProcesador contextoProcesador = new ContextoProcesador(paquete, paquetePersonaje, paqueteUsuario, 
							entrada, salida, this, socketCliente);
					Procesador proceso = ProcesadorFactory.crear(paqueteUsuario.getComando(), contextoProcesador, gson);
					proceso.procesar(cadenaLeida);
					do{
					}while(!contextoProcesador.getPaqueteUsuario().isInicioSesion());
//					switch (paquete.getComando()) {
//
//					case Comando.REGISTRO:
////						if (paquete.getMensaje().equals(Paquete.msjExito)) {
////
////							// Abro el menu para la creaci�n del personaje
////							MenuCreacionPj menuCreacionPJ = new MenuCreacionPj(this, paquetePersonaje, contextoProcesador);
////							menuCreacionPJ.setVisible(true);
////
////							// Espero a que el usuario cree el personaje
////							wait();
////
////							// Le envio los datos al servidor
////							paquetePersonaje.setUsuario(this.paqueteUsuario.getUsername());
////							paquetePersonaje.setComando(Comando.CREACIONPJ);
////							salida.writeObject(gson.toJson(paquetePersonaje));
////							JOptionPane.showMessageDialog(null, "Registro exitoso.");
////
////							// Recibo el paquete personaje con los datos (la id
////							// incluida)
////							paquetePersonaje = (PaquetePersonaje) gson.fromJson((String) entrada.readObject(),
////									PaquetePersonaje.class);
////
////							// Indico que el usuario ya inicio sesion
////							paqueteUsuario.setInicioSesion(true);
////
////						} else {
////							if (paquete.getMensaje().equals(Paquete.msjFracaso))
////								JOptionPane.showMessageDialog(null, "No se pudo registrar.");
////
////							// El usuario no pudo iniciar sesi�n
////							paqueteUsuario.setInicioSesion(false);
////						}
//						proceso.procesar(cadenaLeida);
//						break;
//
//					case Comando.INICIOSESION:
////						if (paquete.getMensaje().equals(Paquete.msjExito)) {
////
////							// El usuario ya inicio sesión
////							paqueteUsuario.setInicioSesion(true);
////
////							// Recibo el paquete personaje con los datos
////							paquetePersonaje = (PaquetePersonaje) gson.fromJson(cadenaLeida, PaquetePersonaje.class);
////
////						} else {
////							if (paquete.getMensaje().equals(Paquete.msjFracaso))
////								JOptionPane.showMessageDialog(null,
////										"Error al iniciar sesión. Revise el usuario y la contraseña");
////
////							// El usuario no pudo iniciar sesi�n
////							paqueteUsuario.setInicioSesion(false);
////						}
//						break;
//
//					case Comando.SALIR:
//						// El usuario no pudo iniciar sesi�n
////						paqueteUsuario.setInicioSesion(false);
////						salida.writeObject(gson.toJson(new Paquete(Comando.DESCONECTAR), Paquete.class));
////						socketCliente.close();
//						break;
//
//					default:
//						break;
//					}

				}

				// Creo un paquete con el comando mostrar mapas
				paquetePersonaje.setComando(Comando.MOSTRARMAPAS);

				// Abro el menu de eleccion del mapa
				MenuMapas menuElegirMapa = new MenuMapas(this);
				menuElegirMapa.setVisible(true);

				// Espero a que el usuario elija el mapa
				wait();

				// Establezco el mapa en el paquete personaje
				paquetePersonaje.setIp(miIp);

				// Le envio el paquete con el mapa seleccionado
				salida.writeObject(gson.toJson(paquetePersonaje));

				// Instancio el juego y cargo los recursos
				wome = new Juego("World Of the Middle Earth", 800, 600, this, paquetePersonaje);

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
		return socketCliente;
	}

	public void setSocket(Socket cliente) {
		this.socketCliente = cliente;
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
		return paqueteUsuario;
	}

	public PaquetePersonaje getPaquetePersonaje() {
		return paquetePersonaje;
	}

	public Juego getJuego() {
		return wome;
	}

	public MenuCarga getMenuCarga() {
		return menuCarga;
	}
}
