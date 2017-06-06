package testsCliente;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import com.google.gson.Gson;

import mensajeria.Comando;
import mensajeria.Paquete;
import mensajeria.PaqueteUsuario;
//import servidor.Servidor;

//import servidor.Conector;
//import servidor.EscuchaCliente;

public class ServidorStub extends Thread {

	private final int PUERTO = 9999;
	private ServerSocket serverSocket;

	public void run() {
		try {

			// conexionDB = new Conector();
			// conexionDB.connect();

			// log.append("Iniciando el servidor..." + System.lineSeparator());
			serverSocket = new ServerSocket(PUERTO);
			// log.append("Servidor esperando conexiones..." +
			// System.lineSeparator());
			String ipRemota;

			// atencionConexiones.start();
			// atencionMovimientos.start();

			while (true) {
				Socket cliente = serverSocket.accept();
				final Gson gson = new Gson();
				ipRemota = cliente.getInetAddress().getHostAddress();
				// log.append(ipRemota + " se ha conectado" +
				// System.lineSeparator());

				ObjectOutputStream salida = new ObjectOutputStream(cliente.getOutputStream());
				ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
				
				Paquete paquete;
				Paquete paqueteSv = new Paquete(null, 0);
				PaqueteUsuario paqueteUsuario = new PaqueteUsuario();

				String cadenaLeida = (String) entrada.readObject();

				while (!((paquete = gson.fromJson(cadenaLeida, Paquete.class)).getComando() == Comando.DESCONECTAR)) {

					switch (paquete.getComando()) {

					case Comando.REGISTRO:
						// Paquete que le voy a enviar al usuario
						paqueteSv.setComando(Comando.REGISTRO);
						
						//No envío el paquete al Conector.java simulo que pudo conectarse
						
						//paqueteUsuario = (PaqueteUsuario) (gson.fromJson(cadenaLeida, PaqueteUsuario.class)).clone();

						// Si el usuario se pudo registrar le envio un msj de
						// exito
							paqueteSv.setMensaje(Paquete.msjExito);
							salida.writeObject(gson.toJson(paqueteSv));
							// Si el usuario no se pudo registrar le envio un
							// msj de fracaso
						break;
						
					}
					cadenaLeida = (String) entrada.readObject();
				}
				entrada.close();
				salida.close();
				cliente.close();
			}
		} catch (Exception e) {
			// log.append("Fallo la conexi�n." + System.lineSeparator());
			e.printStackTrace();
		}
	}
}