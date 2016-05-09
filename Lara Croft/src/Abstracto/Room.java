package Abstracto;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List; //Op

import Instanciable.Disparo;
import Instanciable.Fondo;
import Instanciable.Juego;
import Instanciable.Player;

public abstract class Room {
	private static Fondo fondo;
	private static Player[] player = {null, new Player(1),new Player(2)};//, player2;
	private static Disparo disparo;
	public static List<Disparo> disparos = new ArrayList<Disparo>();
	private static boolean[] disp = {true, false, false};
	private static int tempdisp = -1, borrar = -1, fin = 0, yhp = 4, ymana = 12, yenergia = 16;	
	private static double[] cont= {1, 1, 1}, contR = {0, 0, 0},xcosa = {0, 30, 240};
	private static double contd = 1;
	
	public static void init() {
		fondo = new Fondo("/Backgrounds/fondo.jpg", 0.1);
		for(int i = 1; i <= 2; i++) {
			player[i] = new Player(i);
			player[i].setPosition(100*i, 100*i);
		}
	}
	
	public static void update() {for(int i = 1; i <= 2; i++) player[i].update();} //Llamar al update de los 2 jugadores
	
	public static void draw(Graphics2D g) {
		fondo.draw(g); //Pintar el fondo
		borrar = -1; //Resetear el index de borrado de la arraylist de disparos.
		for(int i = 1; i <= 2; i++) {
			player[i].hud.draw(g);
			player[i].draw(g); //Pintar los 2 jugadores
			g.setColor(Color.black);
			//nombre
			g.drawString("P"+i, (int)xcosa[i], 50);
			//HP
			g.setColor(Color.red);
			g.fillRect ((int)xcosa[i], (int)yhp, ((player[i].getHealth()*100/player[i].getMaxHealth())*50)/100, 7);
			//Mana
			if(player[i].getContr()) g.setColor(Color.yellow);
			else g.setColor(Color.blue);
			g.fillRect((int)xcosa[i], (int)ymana, (int)((player[i].getMana()*100/100)*50)/100, 3);
			//ENERGIA
			g.setColor(Color.green);
			g.fillRect((int)xcosa[i], (int)yenergia, (int)((player[i].getEnergia()*100/100)*50)/100, 3);
			g.setColor(Color.white); //Resetear el color
		}
		
		for(Disparo dispis: disparos) { //Recorrer disparos
			dispis.draw(g); //Pintar los disparos
			if(dispis.getX() > 320 || dispis.getX() < 0) borrar = disparos.indexOf(dispis); else dispis.update(); //Si hay un disparo fuera del campo almacenar su index.
			if(dispis.getF()){ //F es si el disparo ha entrado en colisión con el disparo, es decir, éste disparo debeía haver acabado y se debe restar hp del jugador.
				int obj = (((dispis.getPlayer())? 1 : 0)+1); //Almacenar el jugador al que está dirigido el ataque.
				if(obj ==1) obj=2; else obj = 1;
				player[obj].setHealth((player[obj].getHealth()-dispis.getDaño())); //Restarle la vida al jugador
				player[obj].setParpadeo(true);
				System.out.println("Vida del player"+obj+":"+player[obj].getHealth()); //Mostrar la vida
				if(player[obj].getHealth() <= 0) { //Si el pj ha muerto
					if(obj == 1) fin = 2; else fin = 1; //Fin almacena el personaje que ha ganado.
					System.out.println("Gana el player"+fin); //Mostrar el personaje ganador.
				}
				borrar = disparos.indexOf(dispis); //almacenar el index del disparo.
			}
		}
		if(borrar != -1) disparos.remove(borrar); //Borrar el disparo
	}
	
	public static void teclas() {
		player[1].setFalse();
		player[2].setFalse();
		//Player1=====================================================
		if(Juego.teclas.contains(KeyEvent.VK_LEFT)) player[1].setIzquierda(true);
		if(Juego.teclas.contains(KeyEvent.VK_RIGHT)) player[1].setDerecha(true);
		if(Juego.teclas.contains(KeyEvent.VK_DOWN)) salto(1);
		if(Juego.teclas.contains(KeyEvent.VK_UP)) {player[1].setSalto(true); player[1].setPlaneo(true);}
		
		//Espadas==============================================
		//P1
		if(Juego.teclas.contains(KeyEvent.VK_DELETE) && player[1].getEnergia() > 40) playerAtk(1);
		else player[1].setAtaqueb(false);
		//P2
		if(Juego.teclas.contains(KeyEvent.VK_H)  && player[2].getEnergia() > 40) playerAtk(2);
		else player[2].setAtaqueb(false);
		//======================================================
		
		//Disparos===============================================
		//P1
		if(Juego.teclas.contains(KeyEvent.VK_INSERT)) {
			disp[1] = false;
			if(cont[1] < 2)cont[1] += 0.03;
			else player[1].setContr(true);
		} else if(cont[1] > 1){
			contd = cont[1];
			disparo(1, false, contd); 
			player[1].setDisparo(true);
			cont[1] = 1;
			player[1].setContr(false);
		}
		//P2
		if(Juego.teclas.contains(KeyEvent.VK_G)) {
			disp[2] = false;
			if(cont[2] < 2)cont[2] += 0.03;
			else player[2].setContr(true);
		} else if(cont[2] > 1){
			contd = cont[2];
			disparo(2, true, contd); 
			player[2].setDisparo(true);
			cont[2] = 1;
			player[2].setContr(false);
		}
		//====================================================
		
		//Player2=====================================================
		if(Juego.teclas.contains(KeyEvent.VK_A)) player[2].setIzquierda(true);
		if(Juego.teclas.contains(KeyEvent.VK_D)) player[2].setDerecha(true);
		if(Juego.teclas.contains(KeyEvent.VK_S)) salto(2);
		if(Juego.teclas.contains(KeyEvent.VK_T)) player[2].setAgachar(true);
		else player[2].setAgachar(false);
		if(Juego.teclas.contains(KeyEvent.VK_W)) {player[2].setSalto(true);player[2].setPlaneo(true);}
		
		//============================================================
	}

	
	//Salto
	public static void salto(int p) {
		if(contR[p] < 20 && player[p].getEnergia() >= 20) {
			player[p].setAbajo(true);
			player[p].setEnergia(player[p].getEnergia()-10);
			contR[p] += 1;
		} else if(contR[p] < 40){
			player[p].setAbajo(false);
			contR[p] += 1;
		} else {
			contR[p] = 0;
		}
	}
	
	//ESPADA=======================================================
	public static void playerAtk(int pj) {
		player[pj].setAtaqueb(true); 
		player[pj].setAtaqueb(true); player[pj].setAtkespada(true);
	}
	//=============================================================

	//Disparo==============================================================================
	public static void disparo(int p, boolean  p2, double contd2) {
		if(!disp[p] && player[p].getMana() >= player[p].getManad1() && fin == 0) {
			player[p].setAtaque(true); 
			if(tempdisp == -1) disparo/*[numDisparos]*/ = new Disparo(player[p].getx()+((player[p].getancho()*(player[p].getmDerecha()? 1 : -1)/2)), player[p].gety(), player[p].getmDerecha(), p2, contd);
			else disparo/*[tempdisp]*/ = new Disparo(player[p].getx()+((player[p].getancho()*(player[p].getmDerecha()? 1 : -1))/2), player[p].gety(), player[p].getmDerecha(), p2, contd);
			disparos.add(disparo);
			disp[p] = true;
			double mana = player[p].getMana();
			player[p].setMana(mana-=player[p].getManad1());
		}
	}
	//====================================================================================

	//Setter / Getter========================================================
	public static double playergetX(int p) {return player[p].getXreal();}
	public static double playergetY(int p) {return player[p].getYreal();}
	public static int playergetAncho(int p) {return player[p].getancho();}
	public static int playergetAlto(int p) {return (int) player[p].getAlto();}
	public static void playersetHp(int p, int daño) {player[p].setHealth((player[p].getHealth()-daño));}
	public static boolean playermDerecha(int p) {return player[p].getmDerecha();}
	public static int playergetAccion(int p) {return player[p].getAccion();}
	public static void playersetParpadeo(int p, boolean par) { player[p].setParpadeo(true);}
	//===============================================================================
}