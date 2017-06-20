package chat;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import chat.VentanaChat;

import javax.swing.JScrollPane;
import javax.swing.DefaultListModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionEvent;
import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import mensajeria.PaqueteMensaje;
import mensajeria.PaquetePersonaje;
import cliente.Cliente;
import com.google.gson.Gson;

import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class MessengerClient extends Thread {

	private Socket mySocket;
	//private EscuchaMensajes escuchaMensajes;
	private String username;
	//private ArchivoDePropiedades properties;
	private ObjectInputStream in;
	private ObjectOutputStream out; 
	
	private JMenu mnChat;
	private JMenuItem mntmConfigIpPuerto;
	private JMenuItem mntmDesconectar_1;
	private JMenuItem mntmConectar;
	private JPanel contentPane;
	private JList<String> listUsuarios;
	private Map<String, VentanaChat> chatsAbiertos;
	private JLabel lblUsuarios;
	private JFrame VC;
	private Cliente cliente;
	private JTextField textFieldDifusion;
	private final Gson gson = new Gson();
	
	public MessengerClient(Cliente cliente) {
		this.cliente = cliente;
		this.username = cliente.getPaquetePersonaje().getNombre();
	}
	
//	protected synchronized void cerrarTodo() {
//		try {
//			for (VentanaChat ventanaChat : chatsAbiertos.values()) {
//				ventanaChat.dispose();
//			}
//			if (in != null) in.close();
//			if (out != null) out.close();
//			if (!mySocket.isClosed()) mySocket.close();
//			if (this.escuchaMensajes != null && this.escuchaMensajes.isAlive()) {
//				this.escuchaMensajes.stop();
//			}
//			this.escuchaMensajes = null;
//		} catch (Exception e){
//			e.printStackTrace();
//		} finally {
//			actualizarVentana(false);
//		}
//	}
	
//	private boolean getUsername() throws IOException, ClassNotFoundException {
//    	do {
//        	String user = getNameDialog();
//        	if (user == null) {
//        		return false;
//        	}
//        	if (user.trim().isEmpty()) {
//        		continue;
//        	}
//        	out.writeObject(new Message(MessageType.LOGIN, user));
//        	out.flush();
//        	Message response = (Message) in.readObject();
//        	if (response.getType() != MessageType.LOGIN) continue; // Lo ignoro.
//        	if (response.getSuccess()) {
//        		this.username = user;
//        		this.escuchaMensajes = new EscuchaMensajes(this, this.in);
//        		this.escuchaMensajes.start();
//        		this.VC.setTitle("Chat. Logeado como: " + username);
//        		setUsuariosEnLista(response.getContent().split(","));
//        	} else {
//        		mostrarDialog(response.getContent(), "Error", JOptionPane.INFORMATION_MESSAGE);
//        	}
//        } while (this.username == null);
//    	return true;
//    }
	
//	private void actualizarVentana(boolean conectado) {
//		mntmConectar.setEnabled(!conectado);
//		mntmDesconectar_1.setEnabled(conectado);
//		if (!conectado) {
//			VC.setTitle("Chat - Estás desconectado");
//			setUsuariosEnLista(null);
//		}
//		mnChat.setVisible(conectado);
//		mntmConfigIpPuerto.setEnabled(!conectado);
//	}
//	
//	private String getNameDialog() {
//        return JOptionPane.showInputDialog(
//            VC,
//            "Ingrese nombre de usuario:",
//            "LOGIN",
//            JOptionPane.PLAIN_MESSAGE);
//    }
	
	/**
	 * @wbp.parser.entryPoint
	 */
	public void crearVentanaCliente() {
		chatsAbiertos = new HashMap<String, VentanaChat>();
		VC = new JFrame();
//		VC.addWindowListener(new WindowAdapter() {
//			@Override
//			public void windowClosing(WindowEvent arg0) {
////				abrirVentanaConfirmaSalir();
//			}
//		});
		
		try
		{
		   UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
		   e.printStackTrace();
		}
		
		VC.setTitle("Chat");
		VC.setResizable(false);
		VC.setBounds(100, 100, 379, 600);
		
		JMenuBar menuBar = new JMenuBar();
		VC.setJMenuBar(menuBar);
		
		JMenu mnArchivo = new JMenu("Archivo");
		menuBar.add(mnArchivo);
		
		mntmConectar = new JMenuItem("Conectar");
		mntmDesconectar_1 = new JMenuItem("Desconectar");
		mntmDesconectar_1.setEnabled(false);
		JMenuItem mntmSalir = new JMenuItem("Salir");
		mnArchivo.add(mntmConectar);
		mnArchivo.add(mntmDesconectar_1);
		mnArchivo.add(mntmSalir);
		
		mntmConectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
//					iniciarConexion();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
		});
		
		mntmSalir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				abrirVentanaConfirmaSalir();
			}
		});
		
		mntmDesconectar_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				cerrarTodo();
			}
		});
		
		mnChat = new JMenu("Chat");
		menuBar.add(mnChat);
		
		JMenuItem mntmSalaDeChat = new JMenuItem("Sala de Chat");
		mntmSalaDeChat.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				getChatWindow("SALA");
			}
		});
		mnChat.add(mntmSalaDeChat);
		
		JMenuItem mntmSesionPrivada = new JMenuItem("Sesión privada");
		mntmSesionPrivada.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				seleccionarElementoLista();
			}
		});
		mnChat.add(mntmSesionPrivada);
		
		JMenu mnAyuda = new JMenu("Ayuda");
		menuBar.add(mnAyuda);
		
		mntmConfigIpPuerto = new JMenuItem("Configurar IP-Puerto");
		mntmConfigIpPuerto.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
//				abrirVentanaConfiguracion();
			}
		});
		mnAyuda.add(mntmConfigIpPuerto);
		
		JMenuItem mntmAcerca = new JMenuItem("Acerca");
		mntmAcerca.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//abrirVentanaAyuda();
			}
		});
		mnAyuda.add(mntmAcerca);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		VC.setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(0, 0, 373, 181);
		contentPane.add(scrollPane_1);
		
		JTextArea textAreaDifusion = new JTextArea();
		scrollPane_1.setViewportView(textAreaDifusion);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(0, 217, 373, 322);
		contentPane.add(scrollPane);
		
		listUsuarios = new JList<String>();
		listUsuarios.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				seleccionaDobleClickChat(arg0);
			}
		});
		scrollPane.setViewportView(listUsuarios);

		lblUsuarios = new JLabel();
		lblUsuarios.setBounds(0, 464, 373, 14);
		contentPane.add(lblUsuarios);
		
		textFieldDifusion = new JTextField();
		textFieldDifusion.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					PaqueteMensaje mensaje = new PaqueteMensaje(cliente.getJuego().getPersonaje().getId(), textFieldDifusion.getText());
					try {
						cliente.getSalida().writeObject(gson.toJson(mensaje));
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					textFieldDifusion.setText("");
				}
			}
		});
		textFieldDifusion.setBounds(0, 182, 281, 25);
		contentPane.add(textFieldDifusion);
		textFieldDifusion.setColumns(10);
		
		JButton btnDifusion = new JButton("Difundir");
		btnDifusion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PaqueteMensaje mensaje = new PaqueteMensaje(cliente.getJuego().getPersonaje().getId(), textFieldDifusion.getText());
				try {
					cliente.getSalida().writeObject(gson.toJson(mensaje));
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				textFieldDifusion.setText("");
			}
		});
		btnDifusion.setBounds(276, 182, 97, 25);
		contentPane.add(btnDifusion);
		
		setUsuariosEnLista();
		//actualizarVentana(false);
		VC.setVisible(true);
	}
	
//	private void abrirVentanaAyuda() {
//		new VentanaAyuda(VC);
//	}
	
//	private void abrirVentanaConfirmaSalir() {
//		int opcion = JOptionPane.showConfirmDialog(VC, "Desea salir del Chat?", "Confirmación", JOptionPane.YES_NO_OPTION);
//		if(opcion == JOptionPane.YES_OPTION) {
//			if(this.escuchaMensajes != null) {
////				cerrarTodo();
//			}
//			System.exit(0);
//		}
//	}
	
//	private void iniciarConexion() throws UnknownHostException, IOException, ClassNotFoundException {
//		properties = new ArchivoDePropiedades("config.properties");
//		properties.lectura();
//		try {
//			mySocket = new Socket(properties.getIP(), properties.getPuerto());			
//		} catch (ConnectException e) {
//			mostrarDialog("Verifique el archivo de configuración", "Error al conectar", JOptionPane.ERROR_MESSAGE);
//			return;
//		}
//		in = new ObjectInputStream(mySocket.getInputStream());
//        out = new ObjectOutputStream(mySocket.getOutputStream());
//        if (getUsername()) {
//        	actualizarVentana(true);
//        } else {
//        	cerrarTodo();
//        }
//	}
	
//	protected synchronized void mostrarDialog(String texto, String titulo, int tipo) {
//		JOptionPane.showMessageDialog(VC, texto, titulo, tipo);
//	}
//	
	public void setUsuariosEnLista() {
		DefaultListModel<String> modeloLista = new DefaultListModel<String>();
		String txtUsuarios;

		for (PaquetePersonaje pj : cliente.getJuego().getEscuchaMensajes().getPersonajesConectados().values()) {
			if(!pj.getNombre().equals(this.username)) {
				modeloLista.addElement(pj.getNombre() + "(" + pj.getId() + ")");
			}
		}
		txtUsuarios = modeloLista.isEmpty() ? "No hay nadie conectado" : "Cantidad de Usuarios Conectados: " + modeloLista.getSize();
		listUsuarios.setModel(modeloLista);
		lblUsuarios.setText(txtUsuarios);
	}
	
	private void seleccionarElementoLista() {
		if(!listUsuarios.isSelectionEmpty())
			getChatWindow(listUsuarios.getSelectedValue());
		//else
			//mostrarDialog("Seleccione un elemento de la lista", "Seleccionar Usuario", JOptionPane.INFORMATION_MESSAGE);
	}

//	private void abrirVentanaConfiguracion() {
//		new VentanaConfiguracion(VC);
//	}

	private void seleccionaDobleClickChat(MouseEvent me) {
		if(me.getClickCount() == 2)
			seleccionarElementoLista();
	}
	
	private VentanaChat getChatWindow(String user) {
    	if (!chatsAbiertos.containsKey(user)) {
    		chatsAbiertos.put(user, openChatWindow(user));
    	}
    	return chatsAbiertos.get(user);
    }
	
	private VentanaChat openChatWindow(String recipient) {
		VentanaChat ventanaChat = new VentanaChat(this, recipient);
		
		ventanaChat.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosed(WindowEvent e) {
				chatsAbiertos.remove(ventanaChat.getUsuarioDestino());
			}
		});
		ventanaChat.setVisible(true);
		return ventanaChat;
	}
	
//	protected synchronized void recibirMensaje(Message msg) {
//		String chatName = msg.getType().equals(MessageType.DIFUSION) ? "SALA" : msg.getSender();
//		getChatWindow(chatName).recibirMensaje(msg.getSender(), msg.getContent());
//	}

	protected synchronized void enviarMensaje(String texto, String destinatario) throws IOException {
		//MessageType tipoMensaje = destinatario.equals("SALA") ? MessageType.DIFUSION : MessageType.MENSAJE;
		
		//out.writeObject(new Message(tipoMensaje, this.username, texto, destinatario));
    	//out.flush();
	}
}