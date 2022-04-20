package interfaz;

import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class PanelMenuButtons implements Button {

	@Override
	public void configurarBoton(JButton aEditar, URL rutaImagen, String comando) {
		// TODO Auto-generated method stub
		aEditar.setBorder(null);
		aEditar.setContentAreaFilled(false);
		aEditar.setActionCommand(comando);
		ImageIcon letras = new ImageIcon(rutaImagen);
		aEditar.setIcon(letras);
		aEditar.addActionListener((ActionListener) this);
		aEditar.addMouseListener((MouseListener) this);
	}

}
