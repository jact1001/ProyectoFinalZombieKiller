package interfaz;

import java.net.URL;

import javax.swing.JButton;
import javax.swing.JFrame;

public interface IPanel {
	public Button createButton(JButton aEditar, URL rutaImagen, String comando, JFrame inter);
}
