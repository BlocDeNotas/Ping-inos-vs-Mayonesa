package Instanciable;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Abstracto.Room;
import Abstracto.Solido;
import Instanciable.Colisiones.ColisionAtk;
import Instanciable.Gráfico.Animacion;

public class Disparo extends Solido {
	private ColisionAtk colisiondisp;
	private boolean player,col,f,mDerecha;
	private int daño;
	private BufferedImage[] bi = new BufferedImage[2];
	private BufferedImage hoja = null;
	
	//Constructor
	public Disparo(double x, double y, boolean dir, boolean player, double contd) {
		this.x = x;
		this.y = y;
		System.out.println("x"+x);
		xreal = x-ancho/2;
		yreal = y-alto/2;
		ancho = alto = 10;
		daño = (int)(10*contd);
		System.out.println(daño);
		mDerecha = dir; //Hacia donde mira el personaje.
		this.player = player; //Player almacena el jugador que lanza el golpe
		this.f = false; //f es la variable de muerte del personaje.
		try {
			if(player) hoja = ImageIO.read(getClass().getResource("/Sprites/Player/disparo.png")); //Según el jugador el disparo contendrá un aspecto o otro.
			else hoja = ImageIO.read(getClass().getResource("/Sprites/Player/disparo2.png"));
		} catch (IOException e) {e.printStackTrace();}
		
		for(int j = 0; j < 2; j++) bi[j] = hoja.getSubimage((j * ancho)+1*(1+j), 1, ancho, alto); //Añadir las imagenes 0 o 10 + 1, 1 , 10, 10
		ancho = alto = (int)(10*contd);
		colisiondisp = new ColisionAtk(this.x, this.y, this.ancho, this.alto); //Crear una máscara de colisiones para el disparo
		System.out.println(contd);
		animacion = new Animacion();
		animacion.setAnimacion(bi);
	}

	//Pintar
	public void draw(Graphics2D g) {super.draw(g);}
	
	//Bucle
	public void update() {
		col = false;
		if(mDerecha) this.x += 2;
		else this.x -= 2;
		xreal = x-ancho/2;
		yreal = y-alto/2;
		animacion.setDelay(100);
		animacion.update();
		colisiondisp.update(this.xreal, this.yreal);
		if(player) col = colisiondisp.Colision(Room.playergetX(1), Room.playergetY(1), Room.playergetAlto(1), Room.playergetAncho(1));
		else col = colisiondisp.Colision(Room.playergetX(2), Room.playergetY(2), Room.playergetAlto(2), Room.playergetAncho(2));
		if(col) f = true; else if(checkSuelo()) {f = true;}
	}
	
	//Setter & Getters===================================
	public double getX() {return this.x;}
	public boolean getF() {return f;}
	public boolean getPlayer() {return player;}
	public int getDaño() {return daño;}
	public void setDaño(int daño) {this.daño = daño;}
	//==================================================
	
}