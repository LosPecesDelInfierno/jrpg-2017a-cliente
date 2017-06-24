package chat;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import java.awt.Font;

import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ScrollPaneConstants;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JLabel;
import javax.swing.ImageIcon;


public class VentanaChat extends JFrame {

	private JPanel contentPane;
	private JTextField txtAsda;
	private JButton btnEnviar;
	private JTextArea textArea;
	private int idUsuarioDestinoChat;
	private String usuarioDestinoChat;
	private JLabel lblNewLabel;
	private MessengerClient cliente;

	public VentanaChat(MessengerClient cliente, int idUsuarioDestinoChat, String usuarioDestinoChat) {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);		
		
		this.cliente = cliente;
		this.idUsuarioDestinoChat = idUsuarioDestinoChat;
		this.usuarioDestinoChat = usuarioDestinoChat;
		setTitle(usuarioDestinoChat);
		setResizable(false);
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				mostrarVentanaConfirmacion();
			}
		});
		
		setBounds(100, 100, 640, 480);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 13, 610, 379);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBackground(new Color(255, 255, 255, 150));
		contentPane.add(scrollPane);
		
		textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setFont(new Font("Microsoft Sans Serif", Font.PLAIN, 12));
		//textArea.setBackground(new Color(255, 255, 255, 150));
		scrollPane.setViewportView(textArea);
		
		txtAsda = new JTextField();
		txtAsda.setBounds(12, 405, 507, 27);
		txtAsda.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent arg0) {
				if(arg0.getKeyCode() == KeyEvent.VK_ENTER) {
					escribeEnTextArea();
					hacerFocoTextField(txtAsda);
				}
			}
		});
		contentPane.add(txtAsda);
		txtAsda.setColumns(10);
		
		btnEnviar = new JButton("Enviar");
		btnEnviar.setBounds(531, 407, 91, 23);
		btnEnviar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				escribeEnTextArea();
				
			}
		});
		contentPane.add(btnEnviar);
		
		lblNewLabel = new JLabel("New label");
		lblNewLabel.setIcon(new ImageIcon("/recursos/mercado/fondoChatSinBE.png"));
		lblNewLabel.setBounds(0, 0, 634, 445);
		contentPane.add(lblNewLabel);

		setVisible(true);
		txtAsda.requestFocus();

	}
	
	public void escribeEnTextArea() {
		textArea.setCaretPosition(textArea.getText().length());
		String mensaje = txtAsda.getText();
		try {
			cliente.enviarMensaje(mensaje, this.idUsuarioDestinoChat);
			
			if (this.usuarioDestinoChat != "SALA") 
				recibirMensaje("YO", mensaje);
			txtAsda.setText("");
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Hubo un problema al enviar el mensaje. Intente nuevamente");
		}
	}
	
	private void mostrarVentanaConfirmacion() {
		int res = JOptionPane.showConfirmDialog(this, "Desea salir de la sesi�n de chat?", "Confirmar cerrar", JOptionPane.YES_NO_OPTION);
		if(res == JOptionPane.YES_OPTION)
			dispose();
	}
	
	public String getUsuarioDestino() {
		return this.usuarioDestinoChat;
	}
	
	public int getIdUsuarioDestino() {
		return this.idUsuarioDestinoChat;
	}
	
	private void hacerFocoTextField(JTextField textField) {
		textField.requestFocus();
		textField.selectAll();
	}
	
	protected void recibirMensaje(String enviadoPor, String texto) {
		textArea.append(enviadoPor + " > " + texto + "\n");
	}
}
