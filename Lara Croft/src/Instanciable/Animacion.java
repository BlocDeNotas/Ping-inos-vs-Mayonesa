package Instanciable;

import java.awt.image.BufferedImage;

public class Animacion {
	
	private BufferedImage[] frames;
	private int frameActual;
	private long tiempoInicial,delay;
	
	public void setAnimacion(BufferedImage[] frames) { //Mandas una imagen y la cambias a la animación privada del objeto que la ha llamado.
		this.frames = frames; //Cambiar las imagenes
		frameActual = 0;
		tiempoInicial = System.nanoTime();
	}
	
	public void setDelay(long d) { delay = d; }
	
	public void update() {
		if(delay == -1) return;
		long elapsed = (System.nanoTime() - tiempoInicial) / 1000000;
		if(elapsed > delay) {
			frameActual++;
			tiempoInicial = System.nanoTime();
		}
		if(frameActual == frames.length) frameActual = 0;
	}
	
	public int getFrame() { return frameActual; }
	public BufferedImage getImage() { return frames[frameActual]; }
	
}