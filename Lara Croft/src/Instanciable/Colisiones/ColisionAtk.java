package Instanciable.Colisiones;

public class ColisionAtk {
	private double x, y;
	private int alto, ancho;
	
	public ColisionAtk(double x, double y, int alto, int ancho) {
		this.alto = alto;
		this.ancho = ancho;
		this.x = x;
		this.y = y;
	}
	
	public void update(double x, double y) {
		this.x = x;
		this. y = y;
	}
	
	public boolean Colision(double playerX, double playerY, int altoe, int anchoe) {
		boolean colision = false;
		//if((playerY <= y && (playerY+altoe >= y)) && (playerX <= x && playerX+anchoe >= x)) colision = true;
		if((y+alto > playerY) && (y <= playerY+altoe )) {
			if(x < playerX) {
				if(x+ancho > playerX) {
					colision = true;
				}
			} else {
				if(x < playerX+anchoe) {
					if(x>playerX) {
						colision = true;
					}
				}
			}
		}
		//if((y >= playerY && (y <= playerY+altoe )) && (x+ancho >= playerX && x <= playerX+anchoe)) colision = true;
		return colision;
	}
}
