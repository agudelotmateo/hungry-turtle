package interfaz;

import java.awt.Color;
import java.awt.GridLayout;

import java.util.concurrent.ThreadLocalRandom;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import mundo.Main;

@SuppressWarnings("serial")
public class PanelTablero extends JPanel {
	
	private int tortugaI = 10;
	private int tortugaJ = 10;
	private int redI = -1;
	private int redJ = -1;
	private int s1I = -1;
	private int s1J = -1;
	private int s2I = -1;
	private int s2J = -1;
	private Color lastS1 = null;
	private Color lastS2 = null;
	private Icon iconoPerdedor;
	private JPanel[][] tablero;

	public PanelTablero() {
		tablero = new JPanel[20][20];
		setLayout(new GridLayout(20, 20, 0, 0));
		setBackground(Color.BLACK);
		
		for (int i = 0; i < tablero.length; i++) {
			for (int j = 0; j < tablero[i].length; j++) {
				tablero[i][j] = new JPanel();
				tablero[i][j].setBackground(Color.GRAY);
				add(tablero[i][j]);
			}
		}
		
		iniciarTortuga();
		crearNuevaSerpiente(1);
		crearNuevaSerpiente(2);
		crearNuevaUva();
	}

	public void crearNuevaUva() {
		int i = Main.posAleatoria();
		int j = Main.posAleatoria();
		while (!tablero[i][j].getBackground().equals(Color.GRAY)) {
			i = Main.posAleatoria();
			j = Main.posAleatoria();
		}
		tablero[i][j].setBackground(Color.MAGENTA);
		add(tablero[i][j], i * 20 + j);
	}

	public void crearNuevaSerpiente(int numero) {
		int i = Main.posAleatoria();
		int j = Main.posAleatoria();
		while (!tablero[i][j].getBackground().equals(Color.GRAY)) {
			i = Main.posAleatoria();
			j = Main.posAleatoria();
		}
		if (numero == 1) {
			s1I = i;
			s1J = j;
			tablero[i][j].setBackground(Color.GREEN);
			add(tablero[i][j], i * 20 + j);
		} else if (numero == 2) {
			s2I = i;
			s2J = j;
			tablero[i][j].setBackground(Color.ORANGE);
			add(tablero[i][j], i * 20 + j);
		}
	}

	public void crearNuevaFresa() {
		if (Main.reinicio) {
			Main.reinicio = false;
		} else {
			int i = Main.posAleatoria();
			int j = Main.posAleatoria();
			while (!tablero[i][j].getBackground().equals(Color.GRAY)) {
				i = Main.posAleatoria();
				j = Main.posAleatoria();
			}
			redI = i;
			redJ = j;
			tablero[i][j].setBackground(Color.RED);
			add(tablero[i][j], i * 20 + j);
			try {
				Thread.sleep(80 * Main.timer);
				if (!Main.isPaused) {
					remove();
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void remove() {
		if ((redI != -1) && (redJ != -1)) {
			tablero[redI][redJ].setBackground(Color.GRAY);
			add(tablero[redI][redJ], redI * 20 + redJ);
		}
	}

	public void iniciarTortuga() {
		tablero[tortugaI][tortugaJ].setBackground(Color.CYAN);
		add(tablero[tortugaI][tortugaJ], tortugaI * 20 + tortugaJ);
	}

	public boolean mover() {
		Color c = tablero[tortugaI][tortugaJ].getBackground();
		tablero[tortugaI][tortugaJ].setBackground(Color.GRAY);
		switch (Main.direccion) {
		case "der":
			tortugaJ++;
			break;
		case "izq":
			tortugaJ--;
			break;
		case "arr":
			tortugaI--;
			break;
		case "aba":
			tortugaI++;
		}
		if ((tortugaI > 19) || (tortugaJ > 19) || (tortugaI < 0) || (tortugaJ < 0)) {
			System.out.println("Fuera del tablero!");
			Main.isPaused = true;
			Main.gameOver = true;
			runGameOver();
			return false;
		}
		if (c.equals(Color.CYAN)) {
			c = tablero[tortugaI][tortugaJ].getBackground();
		}
		tablero[tortugaI][tortugaJ].setBackground(Color.CYAN);
		if ((c.equals(Color.GREEN)) || (c.equals(Color.ORANGE))) {
			Main.vidas -= 1;
			VentanaPrincipal.puntajes.numVidas.setText(Integer.toString(Main.vidas));
			System.out.println("Vidas: " + Main.vidas);
			if (Main.vidas < 1) {
				Main.isPaused = true;
				Main.gameOver = true;
				runGameOver();
				return false;
			}
		}
		if (c.equals(Color.RED)) {
			Main.vidas += 1;
			VentanaPrincipal.puntajes.numVidas.setText(Integer.toString(Main.vidas));
			Main.intervaloVidas = ThreadLocalRandom.current().nextInt(40000, 60000);
			System.out.println("Vidas: " + Main.vidas);
		}
		if (c.equals(Color.MAGENTA)) {
			Main.puntos += 100000 / Main.timer;
			VentanaPrincipal.puntajes.numPuntos.setText(Integer.toString(Main.puntos));
			crearNuevaUva();
			System.out.println("Puntos: " + Main.puntos);
		}
		add(tablero[tortugaI][tortugaJ], tortugaI * 20 + tortugaJ);
		repaint();

		return true;
	}

	public boolean moverSerpiente(int i, Color c) {
		if (Main.gameOver) {
			return false;
		}
		if ((c == null) || (c.equals(Color.CYAN)) || (c.equals(Color.GREEN)) || (c.equals(Color.ORANGE))) {
			c = Color.GRAY;
		}
		if (i == 1) {
			tablero[s1I][s1J].setBackground(c);
			switch (Main.direccionS1) {
			case "der":
				s1J++;
				break;
			case "izq":
				s1J--;
				break;
			case "arr":
				s1I--;
				break;
			case "aba":
				s1I++;
			}
			if ((s1I > 19) || (s1J > 19) || (s1I < 0) || (s1J < 0)) {
				lastS1 = c;
				crearNuevaSerpiente(1);
			} else {
				lastS1 = tablero[s1I][s1J].getBackground();
				tablero[s1I][s1J].setBackground(Color.GREEN);
				add(tablero[s1I][s1J], s1I * 20 + s1J);
				repaint();
			}
		}
		if (i == 2) {
			tablero[s2I][s2J].setBackground(c);
			switch (Main.direccionS2) {
			case "der":
				s2J++;
				break;
			case "izq":
				s2J--;
				break;
			case "arr":
				s2I--;
				break;
			case "aba":
				s2I++;
			}
			if ((s2I > 19) || (s2J > 19) || (s2I < 0) || (s2J < 0)) {
				lastS2 = c;
				crearNuevaSerpiente(2);
			} else {
				lastS2 = tablero[s2I][s2J].getBackground();
				tablero[s2I][s2J].setBackground(Color.ORANGE);
				add(tablero[s2I][s2J], s2I * 20 + s2J);
				repaint();
			}
		}
		return true;
	}

	public Color getLastS1() {
		return lastS1;
	}

	public Color getLastS2() {
		return lastS2;
	}

	private void runGameOver() {
		iconoPerdedor = new ImageIcon(getClass().getResource("/imagenes/perdedor.png"));
		JOptionPane.showMessageDialog(null, "Fin del juego!\nPuntaje: " + Main.puntos, "Game over", 0,
				iconoPerdedor);
		System.exit(0);
	}
}