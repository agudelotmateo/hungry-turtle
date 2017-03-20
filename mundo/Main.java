package mundo;

import java.util.concurrent.ThreadLocalRandom;

public class Main {
	
	public static boolean reinicio = false;
	public static boolean flechas = false;
	public static boolean isPaused = false;
	public static boolean gameOver = false;
	public static String direccion = "der";
	public static String direccionS1 = "izq";
	public static String direccionS2 = "aba";
	public static int vidas = 3;
	public static int puntos = 0;
	public static int timer = 150;
	public static int intervaloVidas = ThreadLocalRandom.current().nextInt(40000, 60000);

	public static int posAleatoria() {
		return ThreadLocalRandom.current().nextInt(0, 19);
	}
}
