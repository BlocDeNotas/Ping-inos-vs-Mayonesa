package Lara_Croft;

public class Armas extends Objeto{
	private int daño,speed,knockback,frames,alto,ancho, player, p;
	private String nombre;
	private ColisionAtk ColisionArma;
	private double x,y;
	private boolean colis, on = false;
	private static int pato = 1;
	
	public Armas(int daño, int speed, int knockback, int frames, double x, double y, int alto, int ancho, String nombre, int player) {
		super();
		this.daño = daño;
		this.speed = speed;
		this.knockback = knockback;
		this.frames = frames;
		this.x = x;
		this.player = player;
		this.y = y;
		this.alto = alto;
		this.ancho = ancho;
		this.nombre = nombre;
		ColisionArma = new ColisionAtk(this.x, this.y, this.alto, this.ancho);
	}

	public boolean ataque(double xe, double ye, int altoe, int anchoe) {
		boolean ataque = false;
		if(ye >= y && (ye <= y+alto)) { //y >= 200 y y <= 250
			if(xe >= x && xe <= x+ancho) {// x >= 100 y x <= 200
				ataque = true;
			}
		}
		return ataque;
	}
	
	public int getDaño() {return daño;}
	public void setDaño(int daño) {this.daño = daño;}
	public int getSpeed() {return speed;}
	public void setSpeed(int speed) {this.speed = speed;}
	public int getKnockback() {return knockback;}
	public void setKnockback(int knockback) {this.knockback = knockback;}
	public int getFrames() {return frames;}
	public void setFrames(int frames) {this.frames = frames;}
	public double getX() {return x;}
	public void setX(double d) {this.x = d;}
	public double getY() {return y;}
	public void setY(double d) {this.y = d;}
	public int getAlto() {return alto;}
	public void setAlto(int alto) {this.alto = alto;}
	public int getAncho() {return ancho;}
	public void setAncho(int ancho) {this.ancho = ancho;}
	public String getNombre() {return nombre;}
	public void setNombre(String nombre) {this.nombre = nombre;}
	public int getPlayer() {return player;}
	public boolean getOn() {return on;}
	public void setOn(boolean on) {this.on = on;}

	
	public void update(double d, double e, boolean atk) {
		this.x = d;
		this.y = e-2;
		if(player == 2) p = 1; else p = 2;
		if(atk && on) {
			ColisionArma.update(this.x, this.y);
			colis = ColisionArma.Colision(Room.playergetX(p), Room.playergetY(p), Room.playergetAlto(p), Room.playergetAncho(p));
			if(colis) {
				Room.playersetHp(p, daño);
				Room.playersetParpadeo(p, true);
			}
		}
	}
}
