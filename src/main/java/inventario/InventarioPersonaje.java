package inventario;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;

import java.awt.Component;
import javax.swing.Box;
import javax.swing.ImageIcon;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import javax.swing.SwingConstants;

public class InventarioPersonaje extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					InventarioPersonaje frame = new InventarioPersonaje();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public InventarioPersonaje() {
		setResizable(false);
		setTitle("Inventario Personaje");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 406, 389);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		ImageIcon armadura = new ImageIcon("Imagenes/armaduraDaedrica.png");
		ImageIcon casco = new ImageIcon("Imagenes/cascoDaedrico.png");
		ImageIcon arma = new ImageIcon("Imagenes/mazaDaedrica.png");
		ImageIcon escudo= new ImageIcon("Imagenes/escudoDaedrico.png");
		ImageIcon botas = new ImageIcon("Imagenes/botasDaedricas.png");
		ImageIcon guantes = new ImageIcon("Imagenes/guantesDaedrios.png");
		
		String descripcionArmadura = "Defensa +50, Agilidad -5";
		String descripcionCasco = "Defenza +10";
		String descripcionArma = "Fuerza +75, destreza -3";
		String descripcionEscudo = "Defenza +25";
		String descripcionBotas = "Defensa +5, Destreza +20";
		String descripcionGuantes = "Defenza +5";
		
		JLabel labelArmadura = new JLabel(armadura);		
		labelArmadura.setHorizontalAlignment(SwingConstants.CENTER);
		labelArmadura.setBounds(154, 128, 104, 205);
		labelArmadura.addMouseListener(new MouseAdapter() {			
			@Override
			public void mouseEntered(MouseEvent e) {
				labelArmadura.setToolTipText(descripcionArmadura);
			}
		});	
		contentPane.add(labelArmadura);
		
		JLabel labelArma = new JLabel(arma);		
		labelArma.setHorizontalAlignment(SwingConstants.CENTER);
		labelArma.setBounds(25, 109, 82, 134);
		labelArma.addMouseListener(new MouseAdapter() {			
			@Override
			public void mouseEntered(MouseEvent e) {
				labelArma.setToolTipText(descripcionArma);
			}
		});
		contentPane.add(labelArma);
		
		JLabel labelEscudo = new JLabel(escudo);		
		labelEscudo.setHorizontalAlignment(SwingConstants.CENTER);
		labelEscudo.setBounds(299, 109, 88, 134);
		labelEscudo.addMouseListener(new MouseAdapter() {			
			@Override
			public void mouseEntered(MouseEvent e) {
				labelEscudo.setToolTipText(descripcionEscudo);
			}
		});
		contentPane.add(labelEscudo);
		
		JLabel labelCasco = new JLabel(casco);
		labelCasco.setHorizontalAlignment(SwingConstants.CENTER);
		labelCasco.setBounds(154, 11, 104, 92);
		labelCasco.addMouseListener(new MouseAdapter() {			
			@Override
			public void mouseEntered(MouseEvent e) {
				labelCasco.setToolTipText(descripcionCasco);
			}
		});
		contentPane.add(labelCasco);
		
		JLabel labelBotas = new JLabel(botas);
		labelBotas.setHorizontalAlignment(SwingConstants.CENTER);
		labelBotas.addMouseListener(new MouseAdapter() {			
			@Override
			public void mouseEntered(MouseEvent e) {
				labelBotas.setToolTipText(descripcionBotas);
			}
		});
		labelBotas.setBounds(299, 254, 88, 92);
		contentPane.add(labelBotas);
		
		JLabel labelGuantes = new JLabel(guantes);		
		labelGuantes.setHorizontalAlignment(SwingConstants.CENTER);
		labelGuantes.setBounds(25, 254, 82, 92);
		labelGuantes.addMouseListener(new MouseAdapter() {			
			@Override
			public void mouseEntered(MouseEvent e) {
				labelGuantes.setToolTipText(descripcionGuantes);
			}
		});
		contentPane.add(labelGuantes);
		
		JLabel labelFondo = new JLabel(new ImageIcon("./Imagenes/fondoInventario.jpg"));
		labelFondo.setHorizontalAlignment(SwingConstants.CENTER);
		labelFondo.setBounds(0, 0, 400, 360);
		contentPane.add(labelFondo);
	
	}
}
