package Abstracto;

import Instanciable.Gráfico.Animacion;

public abstract class Pintable {
	protected boolean mDerecha;
	protected Animacion animacion;
	protected double x, y, xreal, yreal;
	protected int  ancho, alto;
	
	public void draw(java.awt.Graphics2D g) {
		if(mDerecha) g.drawImage(animacion.getImage(),(int)(x - ancho / 2),(int)(y - alto / 2),null);
		else g.drawImage(animacion.getImage(),(int)(x - ancho / 2 + ancho),(int)(y - alto / 2),-ancho,alto,null);
		g.drawRect ((int)xreal, (int)yreal, ancho, alto);
	}
}
