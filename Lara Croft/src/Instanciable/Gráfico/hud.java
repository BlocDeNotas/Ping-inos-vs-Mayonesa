package Instanciable.Gráfico;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

import Abstracto.Pintable;

public class hud extends Pintable{
	private BufferedImage skin;
	private int x;
	
	public hud(int p) {
		if(p == 1) {
			try {skin = ImageIO.read(getClass().getResource("/Sprites/Player/hud1.png"));} catch (IOException e) {e.printStackTrace();}
			x = 0;
		} else {
			try {skin = ImageIO.read(getClass().getResource("/Sprites/Player/hud2.png"));} catch (IOException e) {e.printStackTrace();}
			x = 240;
		}
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(skin,x,0,null);
	}
}
