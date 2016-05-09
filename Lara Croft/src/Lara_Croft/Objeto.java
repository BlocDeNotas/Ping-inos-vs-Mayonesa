package Lara_Croft;

public abstract class Objeto {
	private int[] xb = {200,0}, yb = {165,110}, altob = {20,20}, anchob = {200,200};
	protected int ancho, alto, accion, col, acciontemp;
	protected Animacion animacion;
	protected boolean izquierda, derecha, arriba, abajo, salto, caer, ataque, mDerecha, suelo;
	protected double moveSpeed, maxSpeed, stopSpeed, vCaida, maxCaida, vSalto, pSalto, x,y,dx,dy,xdest,ydest, xreal, yreal;
	MascaraColisiones bloque = new MascaraColisiones();
	
	public boolean checkSuelo() {
		xdest = x + dx;
		ydest = y + dy;
		//Calcular si va a salirse de pantalla
		if (xdest > 0 && xdest < 300) x = xdest;
		else xdest -= dx;
		//====================================
		//Calcular si el player supera la y max
		if(ydest < 212) {
			y = ydest;
			caer = true;
		} else if (caer) {
			caer = false; 
			ydest = 212; 
			dy = 0;
		}
		//====================================
		col = bloque.Colision(x, y+15, alto, ancho, dy);
		if(bloque.Colision(x, y+15, alto, ancho, dy) > 0) {
			suelo = true;
			if (y-5 <= (bloque.getY(col-1)) ) {ydest -=dy;caer = false;ydest = bloque.getY(col-1)-15; dy = 0;} //No se mueve la y si está arriba.
			else if(y < (bloque.getY(col-1) + bloque.getAlto(col-1)))xdest -= dx; //Si no está abajo
		} 
		this.setPosition(xdest, ydest); //Aplicar
		return suelo;
	}
	
	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	public int getx() { return (int)x; }
	public int gety() { return (int)y; }
	public int getancho() { return ancho; }
	public int getHeight() { return alto; }
	public void setIzquierda(boolean b) { izquierda = b; }
	public void setDerecha(boolean b) { derecha = b; }
	public void setArriba(boolean b) { arriba = b; }
	public void setAbajo(boolean b) { abajo = b; }
	public void setSalto(boolean b) { salto = b; }
	public double getXreal() {return xreal;}
	public void setXreal(double xreal) {this.xreal = xreal;}
	public double getYreal() {return yreal;}
	public void setYreal(double yreal) {this.yreal = yreal;}

	public void draw(java.awt.Graphics2D g) {
		/*Plataformas========================================*/
		for(int i=0; i < 2; i++) g.drawRect (xb[i], yb[i], anchob[i], altob[i]);
		/*===================================================*/
		if(mDerecha) g.drawImage(animacion.getImage(),(int)(x - ancho / 2),(int)(y - alto / 2),null);
		else g.drawImage(animacion.getImage(),(int)(x - ancho / 2 + ancho),(int)(y - alto / 2),-ancho,alto,null);
		g.drawRect ((int)xreal, (int)yreal, ancho, alto);
	}
}