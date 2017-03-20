package interfaz;

import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import mundo.Main;

@SuppressWarnings("serial")
public class PanelPuntajes extends JPanel {
	
	private JLabel puntos;
	private JLabel vidas;
	public JLabel numPuntos;
	public JLabel numVidas;

	public PanelPuntajes() {
		setLayout(new FlowLayout());
		puntos = new JLabel("Puntos: ");
		add(puntos);
		numPuntos = new JLabel(Integer.toString(Main.puntos));
		add(numPuntos);
		add(new JLabel("              "));
		vidas = new JLabel("Vidas: ");
		add(vidas);
		numVidas = new JLabel(Integer.toString(Main.vidas));
		add(numVidas);
	}
}
