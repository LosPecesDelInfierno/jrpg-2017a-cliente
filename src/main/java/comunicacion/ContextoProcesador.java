package comunicacion;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import mensajeria.Paquete;
import mensajeria.PaquetePersonaje;
import mensajeria.PaqueteUsuario;
import socketCliente.Cliente;

public class ContextoProcesador {
	private Paquete paquete;
	private PaquetePersonaje paquetePersonaje;
	private PaqueteUsuario paqueteUsuario;
	private ObjectInputStream entrada;
	private ObjectOutputStream salida;
	private Cliente cliente;
	private Socket socketCliente;
	
	public ContextoProcesador(Paquete paquete, PaquetePersonaje paquetePersonaje, PaqueteUsuario paqueteUsuario,
			ObjectInputStream entrada, ObjectOutputStream salida, Cliente cliente, Socket socketCliente) {
		this.paquete = paquete;
		this.paquetePersonaje = paquetePersonaje;
		this.paqueteUsuario = paqueteUsuario;
		this.entrada = entrada;
		this.salida = salida;
		this.setCliente(cliente);
		this.setSocketCliente(socketCliente);
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

	public Paquete getPaquete() {
		return paquete;
	}

	public void setPaquete(Paquete paquete) {
		this.paquete = paquete;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Socket getSocketCliente() {
		return socketCliente;
	}

	public void setSocketCliente(Socket socketCliente) {
		this.socketCliente = socketCliente;
	}
}
