package interfaz;

import interfaz.PanelPuntajes;
import interfaz.PanelTablero;
import java.awt.BorderLayout;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import mundo.Adaptador;
import mundo.Main;

@SuppressWarnings("serial")
public class VentanaPrincipal extends JFrame {
	public static PanelPuntajes puntajes;
	public static Timer temporizadorMovimiento;
	public static Timer temporizadorVidas;
	public static Timer temporizadorSerpientes;
	public static Timer temporizadorDireccionSerpientes;
	private static Timer temporizadorGanador;
	private static PanelTablero panelT;
	private static Icon iconoGanador;

	public static void main(String[] args) {
		panelT = new PanelTablero();
		puntajes = new PanelPuntajes();
		tareaMovimiento();
		tareaVidas();
		moverSerpientes();
		cambioDireccionSerpientes();
		new VentanaPrincipal(panelT);
		temporizadorGanador = new Timer();
		temporizadorGanador.schedule(new TimerTask() {
			@Override
			public void run() {
				if (Main.puntos >= 50000) {
					Main.isPaused = true;
					Main.gameOver = true;
					VentanaPrincipal.runGameWon();
				}
			}
		}, 0, 1000);
	}

	public VentanaPrincipal(JPanel panel) {
		iconoGanador = new ImageIcon(this.getClass().getResource("/imagenes/ganador.png"));
		addKeyListener(new Adaptador());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setLayout(new BorderLayout(0, 0));
		setSize(640, 670);
		setLocationRelativeTo(null);
		add(panel, BorderLayout.CENTER);
		add(puntajes, BorderLayout.SOUTH);
		setVisible(true);
		setFocusable(true);
	}

	public static void tareaVidas() {
		temporizadorVidas = new Timer();
		temporizadorVidas.schedule(new TimerTask() {
			@Override
			public void run() {
				if (Main.gameOver) {
					this.cancel();
				}
				Main.intervaloVidas = ThreadLocalRandom.current().nextInt(40000, 60000);
				System.out.println(Main.intervaloVidas);
				panelT.crearNuevaFresa();
			}
		}, 0, (long) Main.intervaloVidas);
	}

	public static void tareaMovimiento() {
		temporizadorMovimiento = new Timer();
		temporizadorMovimiento.schedule(new TimerTask() {
			@Override
			public void run() {
				if (Main.gameOver || !panelT.mover()) {
					this.cancel();
				}
			}
		}, 0, (long) Main.timer);
	}

	public static void moverSerpientes() {
		temporizadorSerpientes = new Timer();
		temporizadorSerpientes.schedule(new TimerTask() {
			@Override
			public void run() {
				if (Main.gameOver || !panelT.moverSerpiente(1, panelT.getLastS1())
						|| !panelT.moverSerpiente(2, panelT.getLastS2())) {
					this.cancel();
				}
			}
		}, 0, (long) ((Main.timer * 3) / 2 ));
	}

	public static void cambioDireccionSerpientes() {
		temporizadorDireccionSerpientes = new Timer();
		temporizadorDireccionSerpientes.schedule(new TimerTask() {
			@Override
			public void run() {
				if (Main.gameOver) {
					cancel();
				}
				Main.direccionS2 = VentanaPrincipal.direccionAleatoria();
				Main.direccionS1 = VentanaPrincipal.direccionAleatoria();
			}
		}, 0, (long) (Main.timer * 5));
	}

	public static void remove() {
		panelT.remove();
	}

	private static String direccionAleatoria() {
		int num = ThreadLocalRandom.current().nextInt(0, 4);
		switch (num) {
		case 0: {
			return "arr";
		}
		case 1: {
			return "aba";
		}
		case 2: {
			return "izq";
		}
		case 3: {
			return "der";
		}
		}
		return "der";
	}

	private static void runGameWon() {
		JOptionPane.showMessageDialog(null, "Fin del juego!\nPuntaje: " + Main.puntos, "Game over", 0, iconoGanador);
		System.exit(0);
	}

}