package Lara_Croft;
import javax.swing.JFrame;

public class Ventana {
	public static void main(String[] args) {
		JFrame window = new JFrame("Penish Battle");
		window.setContentPane(new Juego());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);
		window.pack();
		window.setVisible(true);
	}
}