package Lara_Croft;

public class MascaraColisiones {
	private int[] x = {200,0,0}, y = {165,110,170}, alto = {20,20,0}, ancho = {200,200,200};
	
	public int Colision(double xe, double ye, int altoe, int anchoe, double vCaida) {
		int colision = 0;
		for(int i=0; i < 3; i++) if((ye >= y[i] && (ye <= y[i]+alto[i]-16))&&(xe >= x[i] && xe <= x[i]+ancho[i])&&(vCaida > 0)) colision = i+1;
		return colision;
	}

	public MascaraColisiones() {super();}
	public int getX(int i) {return x[i];}
	public int getY(int i) {return y[i];}
	public int getAlto(int i) {return alto[i];}
	public int getAncho(int i) {return ancho[i];}
}