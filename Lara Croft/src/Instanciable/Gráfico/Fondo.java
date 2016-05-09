package Instanciable.Gráfico;

import java.awt.*;
import java.awt.image.*;
import javax.imageio.ImageIO;

public class Fondo {
	
	private BufferedImage image, p1;
	private double x, y;
	
	public Fondo(String s, double ms) {
		try {
			image = ImageIO.read(getClass().getResource(s));
			p1 = ImageIO.read(getClass().getResource("/Backgrounds/plataforma.png"));
		}
		catch(Exception e) {e.printStackTrace();}	
	}
	
	public void draw(Graphics2D g) {
		g.drawImage(image, (int)x, (int)y, null); 
		g.drawImage(p1, 200, 165, null);
		g.drawImage(p1, 0, 110, null);
	}
}