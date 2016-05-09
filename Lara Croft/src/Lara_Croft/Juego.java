package Lara_Croft;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.event.*;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class Juego extends JPanel 
	implements Runnable, KeyListener{
	
	/*Ventana============================*/
	public static final int ANCHO = 320,ALTO = 240,SCALE = 2, FPS = 60;
	private final boolean running = true;
	private final long tiempo = 1000 / FPS;
	/*===================================*/
	public static ArrayList<Integer> teclas = new ArrayList<Integer>();
	private Thread thread;
	/*Imágenes===========================*/
	private BufferedImage img;
	private Graphics2D g;
	/*===================================*/
	/*Constructor========================*/
	public Juego() {
		super();
		setPreferredSize(new Dimension(ANCHO * SCALE, ALTO * SCALE));
		setFocusable(true);
		requestFocus();	
	}
	/*====================================*/
	
	public void addNotify() {
		super.addNotify();
		if(thread == null) {
			thread = new Thread(this);
			addKeyListener(this);
			thread.start();
		}
	}
	
	private void init() {
		img = new BufferedImage(ANCHO, ALTO,BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) img.getGraphics();
		new Room();
	}
	
	public void run() {
		init();
		long start, elapsed, wait;
		/*Bucle mortál===============================*/
		while(running) {
			start = System.nanoTime(); //Tiempo inicial
			Room.teclas();
			Room.draw(g);
			Room.update();
			pintarPantalla();
			elapsed = System.nanoTime() - start;
			wait = tiempo - elapsed / 1000000;
			if(wait < 0) wait = 5;
			try {Thread.sleep(wait);}
			catch(Exception e) {e.printStackTrace();}
		}
		/*===========================================*/
	}
	
	private void pintarPantalla() {
		Graphics g2 = getGraphics();
		g2.drawImage(img, 0, 0,ANCHO * SCALE, ALTO * SCALE,null);
		g2.dispose();
	}
	//TECLAS
	public void keyTyped(KeyEvent key) {}
	public void keyPressed(KeyEvent k){if(!teclas.contains(k.getKeyCode()))teclas.add(k.getKeyCode());}
	public void keyReleased(KeyEvent k){if(teclas.contains(k.getKeyCode()))teclas.remove(teclas.indexOf(k.getKeyCode()));}
}