package comunicacion;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import juego.Juego;
import mensajeria.PaqueteAtacar;
import mensajeria.PaqueteBatalla;
import mensajeria.PaqueteComercio;
import mensajeria.PaqueteFinalizarBatalla;
import mensajeria.PaqueteIntercambio;
import mensajeria.PaqueteMensaje;
import mensajeria.PaqueteMovimiento;
import mensajeria.PaquetePersonaje;
import mensajeria.PaqueteUsuario;

public class ContextoProcesador {
	private PaquetePersonaje paquetePersonaje;
	private PaqueteUsuario paqueteUsuario;
	private PaqueteBatalla paqueteBatalla;
	private PaqueteAtacar paqueteAtacar;
	private PaqueteFinalizarBatalla paqueteFinalizarBatalla;
	private PaqueteComercio paqueteComercio;
	private PaqueteIntercambio paqueteIntercambio;
	private PaqueteMensaje paqueteMensaje;
	private ObjectInputStream entrada;
	private ObjectOutputStream salida;
	private Juego juego;
	
	private Map<Integer, PaqueteMovimiento> ubicacionPersonajes;
	private Map<Integer, PaquetePersonaje> personajesConectados;

	public ContextoProcesador(ObjectInputStream entrada, Juego juego) {
		this.setUbicacionPersonajes(new HashMap<>());
		this.setPersonajesConectados(new HashMap<>());
		this.setJuego(juego);
	}

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

	public Map<Integer, PaquetePersonaje> getPersonajesConectados() {
		return personajesConectados;
	}

	public void setPersonajesConectados(Map<Integer, PaquetePersonaje> personajesConectados) {
		this.personajesConectados = personajesConectados;
	}

	public Map<Integer, PaqueteMovimiento> getUbicacionPersonajes() {
		return ubicacionPersonajes;
	}

	public void setUbicacionPersonajes(Map<Integer, PaqueteMovimiento> ubicacionPersonajes) {
		this.ubicacionPersonajes = ubicacionPersonajes;
	}

	public PaqueteBatalla getPaqueteBatalla() {
		return paqueteBatalla;
	}

	public void setPaqueteBatalla(PaqueteBatalla paqueteBatalla) {
		this.paqueteBatalla = paqueteBatalla;
	}

	public PaqueteAtacar getPaqueteAtacar() {
		return paqueteAtacar;
	}

	public void setPaqueteAtacar(PaqueteAtacar paqueteAtacar) {
		this.paqueteAtacar = paqueteAtacar;
	}

	public PaqueteFinalizarBatalla getPaqueteFinalizarBatalla() {
		return paqueteFinalizarBatalla;
	}

	public void setPaqueteFinalizarBatalla(PaqueteFinalizarBatalla paqueteFinalizarBatalla) {
		this.paqueteFinalizarBatalla = paqueteFinalizarBatalla;
	}

	public PaqueteComercio getPaqueteComercio() {
		return paqueteComercio;
	}

	public void setPaqueteComercio(PaqueteComercio paqueteComercio) {
		this.paqueteComercio = paqueteComercio;
	}

	public PaqueteIntercambio getPaqueteIntercambio() {
		return paqueteIntercambio;
	}

	public void setPaqueteIntercambio(PaqueteIntercambio paqueteIntercambio) {
		this.paqueteIntercambio = paqueteIntercambio;
	}

	public PaqueteMensaje getPaqueteMensaje() {
		return paqueteMensaje;
	}

	public void setPaqueteMensaje(PaqueteMensaje paqueteMensaje) {
		this.paqueteMensaje = paqueteMensaje;
	}

	public Juego getJuego() {
		return juego;
	}

	public void setJuego(Juego juego) {
		this.juego = juego;
	}
}
