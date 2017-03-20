package mundo;

import interfaz.VentanaPrincipal;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Adaptador extends KeyAdapter {
	public void keyPressed(KeyEvent e) {
		if (Main.flechas) {
			int key = e.getKeyCode();
			if (key == KeyEvent.VK_LEFT) {
				Main.direccion = "izq";
			}
			if (key == KeyEvent.VK_RIGHT) {
				Main.direccion = "der";
			}
			if (key == KeyEvent.VK_UP) {
				Main.direccion = "arr";
			}
			if (key == KeyEvent.VK_DOWN) {
				Main.direccion = "aba";
			}
			if (e.getKeyChar() == 's') {
				ralentizar();
			}
			if (e.getKeyChar() == 'w') {
				acelerar();
			}
		} else {
			char key = e.getKeyChar();
			if (key == 'a') {
				Main.direccion = "izq";
			}
			if (key == 'd') {
				Main.direccion = "der";
			}
			if (key == 'w') {
				Main.direccion = "arr";
			}
			if (key == 's') {
				Main.direccion = "aba";
			}
			if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				ralentizar();
			}
			if (e.getKeyCode() == KeyEvent.VK_UP) {
				acelerar();
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (!Main.isPaused) {
				VentanaPrincipal.temporizadorMovimiento.cancel();
				VentanaPrincipal.temporizadorVidas.cancel();
				VentanaPrincipal.temporizadorSerpientes.cancel();
				VentanaPrincipal.temporizadorDireccionSerpientes.cancel();
				Main.isPaused = true;
			} else if (Main.isPaused) {
				VentanaPrincipal.tareaMovimiento();
				VentanaPrincipal.tareaVidas();
				VentanaPrincipal.moverSerpientes();
				VentanaPrincipal.cambioDireccionSerpientes();
				Main.reinicio = true;
				Main.isPaused = false;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			if (Main.flechas) {
				Main.flechas = false;
			} else {
				Main.flechas = true;
			}
		}
	}

	private void ralentizar() {
		if (Main.timer <= 225) {
			if (Main.isPaused) {
				Main.timer += 25;
			} else {
				VentanaPrincipal.temporizadorMovimiento.cancel();
				VentanaPrincipal.temporizadorSerpientes.cancel();
				Main.timer += 25;
				VentanaPrincipal.tareaMovimiento();
				VentanaPrincipal.moverSerpientes();
			}
			System.out.println(Main.timer);
		}
	}

	private void acelerar() {
		if (Main.timer >= 75) {
			if (Main.isPaused) {
				Main.timer -= 25;
			} else {
				VentanaPrincipal.temporizadorMovimiento.cancel();
				VentanaPrincipal.temporizadorSerpientes.cancel();
				Main.timer -= 25;
				VentanaPrincipal.tareaMovimiento();
				VentanaPrincipal.moverSerpientes();
			}
			System.out.println(Main.timer);
		}
	}
}