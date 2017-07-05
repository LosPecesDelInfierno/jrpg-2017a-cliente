package comunicacion;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import mensajeria.PaquetePersonaje;
import mensajeria.PaqueteUsuario;

public class ContextoProcesador {
	private PaquetePersonaje paquetePersonaje;
	private PaqueteUsuario paqueteUsuario;
	private ObjectInputStream entrada;
	private ObjectOutputStream salida;

	public ContextoProcesador(ObjectInputStream entrada, ObjectOutputStream salida) {
		this.entrada = entrada;
		this.salida = salida;
		this.paqueteUsuario = new PaqueteUsuario();
		this.paquetePersonaje = new PaquetePersonaje();
	}

	public PaquetePersonaje getPaquetePersonaje() {
		return this.paquetePersonaje;
	}

	public void setPaquetePersonaje(PaquetePersonaje paquetePersonaje) {
		this.paquetePersonaje = paquetePersonaje;
	}

	public PaqueteUsuario getPaqueteUsuario() {
		return paqueteUsuario;
	}

	public void setPaqueteUsuario(PaqueteUsuario paqueteUsuario) {
		this.paqueteUsuario = paqueteUsuario;
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
}
