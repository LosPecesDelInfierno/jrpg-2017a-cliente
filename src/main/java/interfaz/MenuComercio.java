package interfaz;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;

import juego.Pantalla;
import mensajeria.PaquetePersonaje;
import dominio.Item;
import recursos.CargadorImagen;
import recursos.Recursos;

public class MenuComercio {

	private static final int x = 400;
	private static final int y = 25;
	private static final int anchoBoton = 60;
	private static final int[][] espaciosEnemigo = {{x + 22, y + 70}, {x + 82, y + 70}, {x + 142, y + 70}, {x + 202, y + 70},
			{x + 22, y + 130}, {x + 82, y + 130}, {x + 142, y + 130}, {x + 202, y + 130}};
	private static final int[][] espaciosPropios = {{x + 22, y + 320}, {x + 82, y + 320}, {x + 142, y + 320}, {x + 202, y + 320},
			{x + 22, y + 380}, {x + 82, y + 380}, {x + 142, y + 380}, {x + 202, y + 380}};
	private boolean habilitado;
	private PaquetePersonaje personaje;
	private PaquetePersonaje enemigo;
	
	public MenuComercio(boolean habilitado, PaquetePersonaje personaje, PaquetePersonaje enemigo) {
		this.habilitado = habilitado;
		this.personaje = personaje;
		this.enemigo = enemigo;
	}		

	
	public void graficar(Graphics g) {

		g.drawImage(Recursos.menuMercado, x, y, null);

		// Dibujo los items de los personajes
		for(int i = 1; i <= 6; i++) {
			Item item = this.enemigo.getItem(i);
			if( item != null ) {
				g.drawImage(CargadorImagen.cargarImagen("/armamento/" + item.getFoto()), espaciosEnemigo[i - 1][0],
						espaciosEnemigo[i - 1][1], anchoBoton, anchoBoton, null); 
			}
		}
		
		for(int i = 1; i <= 6; i++) {
			Item item = this.personaje.getItem(i);
			if( item != null ) {
				g.drawImage(CargadorImagen.cargarImagen("/armamento/" + item.getFoto()), espaciosPropios[i - 1][0],
						espaciosPropios[i - 1][1], anchoBoton, anchoBoton, null); 
			}
		}

		// Dibujo las leyendas
//		g.setFont(new Font("Book Antiqua", 1, 14));
//		g.drawString(personaje.getHabilidadesRaza()[0], x + 95, y + 94);
//		g.drawString(personaje.getHabilidadesRaza()[1], x + 95, y + 168);
//		g.drawString(personaje.getHabilidadesCasta()[0], x + 268, y + 94);
//		g.drawString(personaje.getHabilidadesCasta()[1], x + 268, y + 168);
//		g.drawString(personaje.getHabilidadesCasta()[2], x + 442, y + 94);
//		g.drawString("Ser energizado", x + 442, y + 168);

		// Dibujo el turno de quien es
		g.setFont(new Font("Book Antiqua", 1, 14));
		g.setColor(Color.WHITE);
		if (habilitado)
			Pantalla.centerString(g, new Rectangle(x, y + 10, Recursos.menuMercado.getWidth(), 25), "Mi Turno");
		else
			Pantalla.centerString(g, new Rectangle(x, y + 10, Recursos.menuMercado.getWidth(), 25), "Turno Rival");

	}

//	public int getBotonClickeado(int mouseX, int mouseY) {
//		if (!habilitado)
//			return 0;
//		for (int i = 0; i < botones.length; i++) {
//			if (mouseX >= botones[i][0] && mouseX <= botones[i][0] + anchoBoton && mouseY >= botones[i][1]
//					&& mouseY <= botones[i][1] + anchoBoton)
//				return i + 1;
//		}
//		return 0;
//	}
//
//	public boolean clickEnMenu(int mouseX, int mouseY) {
//		if (mouseX >= x && mouseX <= x + Recursos.menuBatalla.getWidth() && mouseY >= y
//				&& mouseY <= y + Recursos.menuBatalla.getHeight())
//			return habilitado;
//		return false;
//	}
//
//	public void setHabilitado(boolean b) {
//		habilitado = b;
//	}

}
