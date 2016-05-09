package Instanciable;

import java.util.ArrayList;
import javax.imageio.ImageIO;

import Abstracto.Solido;
import Instanciable.Armas.Armas;
import Instanciable.Gráfico.Animacion;
import Instanciable.Gráfico.hud;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Player extends Solido  {
	private int health, maxHealth, manad1 = 30, nAnimacion = 9, cDisparo, cEspada = 0, energia, contletra = 0, lcd = 0, pos;
	private boolean parpadeo, planeo, ataqueb, disparo, atkespada = false, contr = false, agachar, rodar;
	private long parpadeoT;
	boolean ataquef;
	private double mana;
	private char letra;
	private char[] abc = {'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'ñ' ,'o' , 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'y', 'x', 'z'};
	private ArrayList<BufferedImage[]> sprites;
	private final int[] numFrames = {2, 3, 3, 3, 3, 3, 3, 3,2}, fps = {400,40,150,100,100,100, 50, 50,50}, ANCHO = {20, 20, 20, 20, 20, 20, 40, 20,40}, ALTO = {30,30,30,30,30,30,30,15,15};
	private static final int NORMAL = 0, ANDAR = 1, DISPARO = 2, CAER = 3, SALTAR = 4, PLANEAR = 5, ATKESPADA = 6, AGACHAR = 7, ATKCAIDA = 8;
	private BufferedImage hoja;
	public hud hud;
	private Armas espada;
	
	//Constructor
	public Player(int player) {
		this.ataqueb = disparo = rodar = false;
		this.mana = this.energia = health = maxHealth = 100;
		mDerecha = true;
		ancho = 20;
		alto = 30;
		moveSpeed = 0.3;
		maxSpeed = 1.6;
		stopSpeed = 0.4;
		vCaida = 0.15;
		letra = 'a';
		maxCaida = 4.0;
		vSalto = -4.8;
		pSalto = 0.3;
		this.xreal = x-ancho/2;
		this.yreal = y-alto/2;
		espada =new Armas(25/*Daño*/, 10/*Speed*/, 0/*Knockback*/, 3/*Frames*/,(int) this.x+5/*x*/,(int) this.y+5/*y*/, 2/*alto*/, 10/*ancho*/, "espada"/*Daño*/, player);
		/*Imagenes===================================================*/
		try {
			/*Cargar hoja de imágenes================================*/
			if(player == 1) hoja = ImageIO.read(getClass().getResource("/Sprites/Player/player1.png"));
			else hoja = ImageIO.read(getClass().getResource("/Sprites/Player/player2.png"));
			hud = new hud(player);
			/*======================================================*/
			sprites = new ArrayList<BufferedImage[]>();
			/*Array de imágenes====================================*/
			/*Crear en bucle cada imagen separada*/
			for(int i = 0; i < nAnimacion; i++) {
				BufferedImage[] bi = new BufferedImage[numFrames[i]];//Crear la array de imagenes
				for(int j = 0; j < numFrames[i]; j++) bi[j] = hoja.getSubimage((j * ANCHO[i])+(1+1*j), (i * alto)+1*i+1, ANCHO[i], ALTO[i]); //Añadir las imagenes
				sprites.add(bi); 
				/*Se añade en cada posición de la arraylist el conjunto de imagenes de cada animación
				 *lo que luego vale para aplicarlas llamandolas con un número que es asignado a cada nombre clave.*/
			}
			/*====================================================*/
		}
		catch(Exception e) {e.printStackTrace();}
		animacion = new Animacion();
		animacion.setAnimacion(sprites.get(NORMAL));
		animacion.setDelay(400);
		ancho = ANCHO[accion];
	}
		
	private void pTemporal() {
		if(izquierda) {
			dx -= moveSpeed;
			if(abajo) dx = -maxSpeed*4;
			else if(dx < -maxSpeed) dx = -maxSpeed;
		}
		else if(derecha) {
			if(dx > maxSpeed && dx > 0) {
				dx = maxSpeed;
			} else if((dx < -maxSpeed && dx < 0)){
				dx = -maxSpeed;
			}
			dx += moveSpeed;
			if(abajo) dx = maxSpeed*4;
			else if(dx > maxSpeed) dx = maxSpeed;
		}
		else {
			if(dx > 0) {
				if(dx > maxSpeed && dx > 0) {
					dx = maxSpeed;
				} else if((dx < -maxSpeed && dx < 0)){
					dx = -maxSpeed;
				}
				dx -= stopSpeed;
				if(dx < 0) dx = 0;
			}
			else if(dx < 0) {
				dx += stopSpeed;
				if(dx > 0) dx = 0;
			}
		}
		if(salto && !caer) {
			dy = vSalto;
			caer = true;
		}
		if(caer) {
				if(dy > 0 && planeo) dy += vCaida * 0.1;
				else dy += vCaida;
				if(dy > 0) salto = false;
				if(dy < 0 && !salto) dy += pSalto;
				if(dy > maxCaida) dy = maxCaida;
		}
	}	
	public void update() {
		// update position
		pTemporal();
		checkSuelo();
		if(rodar) setParpadeo(true);
		if(y == 212 && agachar) y = 216;
		else if(y == 216 && !agachar) y = 212;
		this.xreal = x-ancho/2;
		this.yreal = y-alto/2;
		if(espada.getOn()) {
			espada.update(((xreal+ancho/1.3)*(mDerecha? 1 : 0))+((xreal+espada.getAncho()/5)*(mDerecha? 0 : 1)+1*(mDerecha? -1 : 1)), y, atkespada);
		} else if(cEspada > 20) cEspada = 20;
		if(mana < 100) this.mana += 0.5;
		if(energia < 100) energia += 1;
		if(lcd > 0) lcd -= 1;
		// check done parpadeo
		if(parpadeo) {
			long tiempo = (System.nanoTime() - parpadeoT) / 1000000;
			if(parpadeoT == 1) {
				if(!abajo) {
					parpadeo = false;
					parpadeoT = 0;
				}
			}
			if(tiempo > 500) parpadeo = false;
			
		}
		// set animacion
		updateAnimacion();
	}
	
	//Pintar
	public void updateAnimacion() {
		if(dy > 0) {
			if(planeo) {
				if(accion != PLANEAR) {
					acciontemp = PLANEAR;
					espada.setOn(false);
				}
			} else if(atkespada) {
				acciontemp = ATKCAIDA;
			}
			else if(accion != CAER) {
				acciontemp = CAER;
				espada.setOn(false);
			}
		}
		else if(dy < 0) {
			if(accion != SALTAR) {
				acciontemp = SALTAR;
				espada.setOn(false);
			}
		}
		else if((izquierda || derecha) && !agachar) {
			if(accion != ANDAR) {
				acciontemp = ANDAR;
				espada.setOn(false);
			}
		}
		else {
			if(atkespada && cEspada > 20) {
				acciontemp = ATKESPADA;
			}
			else if(disparo) {
				acciontemp = DISPARO;
				cDisparo = 30;
				disparo = false;
			} else if(agachar) {
				acciontemp = AGACHAR;
				espada.setOn(false);
			}
			else if(cDisparo <= 0 && accion != NORMAL && cEspada < 20) {
				acciontemp = NORMAL;
			}
			else if(accion != DISPARO)cDisparo = 0;
			else cDisparo -= 1;
		} 
		if(acciontemp != accion) {
			if(acciontemp == ATKESPADA || acciontemp == ATKCAIDA) {
				if(mDerecha)x += 20/2;
				else x -= 20/2;
			} else if(accion == ATKESPADA || accion == ATKCAIDA){
				if(mDerecha)x -= 20/2;
				else x += 20/2;
			}
			if((accion == ATKESPADA || accion == ATKCAIDA) && cEspada > 20) {
				cEspada = 20;
			}
			accion = acciontemp;
			ancho = ANCHO[accion];
			alto = ALTO[accion];
			System.out.println(acciontemp);
			
			animacion.setAnimacion(sprites.get(acciontemp));
			animacion.setDelay(fps[accion]);
		}
		animacion.update();
		if(derecha) mDerecha = true;
		if(izquierda) mDerecha = false;
	}
	public void draw(Graphics2D g) {
		if(parpadeo || rodar) {
			long elapsed = (System.nanoTime() - parpadeoT) / 1000000;
			if(elapsed / 100 % 2 == 0) return;
		}
		
		if(atkespada) {
			if(cEspada == 0) {cEspada = 80; energia -= 40;}
			if(cEspada > 20) {
				espada.setOn(true);
				//espada.draw(g);
				cEspada -= 1;
			} else if(cEspada > 1){
				espada.setOn(false);
				cEspada -= 1;
			} else {
				 cEspada = 0;
				 atkespada = false;
			}
		}
		/*if(player == 1) g.drawString(""+letra, 70, 50);
		else g.drawString(""+letra, 170, 50);*/
		super.draw(g);
	}
	
	//Ahorcado
	public void setFalse() {
		this.abajo = false;
		this.izquierda = false;
		this.salto = false;
		this.planeo = false;
		this.derecha = false;
		this.ataque = false;
		this.ataqueb = false;
	}	
	public void letritas(String frasem){	
		if(lcd == 0) {
			contletra += 1;
			/*System.out.println(frasem);
			for(int i = pos; i < frasem.length(); i++) {
				if(contletra > 26) {contletra-= 26;}
				if(frasem.charAt(i) == abc[contletra]) {contletra += 1;}
			}*/
			if(contletra > 26) {contletra-= 26;}
			this.letra = abc[contletra];
			lcd = 20;
		}
	}
	public void letritasm(String frasem){	
		if(lcd == 0) {
			contletra -= 1;
			if(contletra < 0) {contletra+= 26;}
			this.letra = abc[contletra];
			lcd = 20;
		}
	}
	
	//Setter/Getter
	public void setAgachar(boolean on) {
		this.agachar = on;
	}
	public void setParpadeo(boolean p) {
		if(!parpadeo) {
			this.parpadeo = p; 
			if(abajo) parpadeoT = 1;
			else parpadeoT = System.nanoTime();
		}
	}
	public void setAtaqueb(boolean b) {this.ataqueb = b;}
	public void setAtaque(boolean b) {this.ataque = true;}
	public boolean getAtaque() {return this.ataque;}
	public boolean getAtaqueb() {return this.ataqueb;}
	public boolean getmDerecha() {return this.mDerecha;}
	public double getAlto() {return this.alto;}
	public double getMana() {return mana;}
	public void setMana(double d) {this.mana = d;}
	public int getManad1() {return manad1;}
	public void setManad1(int manad1) {this.manad1 = manad1;}
	public void setHealth(int health) {
		if(!parpadeo) {
			this.health = health;
		}
	}
	public int getHealth() { return health; }
	public int getMaxHealth() { return maxHealth; }
	public void setPlaneo(boolean b) { planeo = b;}
	public boolean getDisparo() {return disparo;}
	public void setDisparo(boolean disparo) {this.disparo = disparo;}
	public boolean getAtkespada() {return atkespada;}
	public void setAtkespada(boolean atkespada) {this.atkespada = atkespada;}
	public boolean getContr() {return contr;}
	public void setContr(boolean contr) {this.contr = contr;}
	public int getAccion() {return this.accion;}
	public void setEnergia(int energ) { this.energia = energ;}
	public int getEnergia() { return this.energia;}
	public int getPos() { return this.pos;}
	public void setPos(int pos) { this.pos = pos;}
	public char getLetra() { return this.letra;}
	public void setAbajo(boolean b) { 
		this.abajo = b;
		if(!parpadeo)setParpadeo(true);
	}
}