package interfaz;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class PanelComoJugarButtons implements Button{
	
	private JButton aEditar;
	private URL rutaImagen;
	private String comando;
	private InterfazZombieKiller principal;
	
	public PanelComoJugarButtons(JButton aEditar, URL rutaImagen, String comando, JFrame inter) {
		super();
		this.aEditar = aEditar;
		this.rutaImagen = rutaImagen;
		this.comando = comando;
		this.principal = (InterfazZombieKiller) inter;
		configurarBoton();
	}

	@Override
	public void configurarBoton() {
		// TODO Auto-generated method stub
		aEditar.setBorder(null);
		aEditar.setFocusable(false);
		aEditar.setContentAreaFilled(false);
		aEditar.setActionCommand(comando);
		ImageIcon letras = new ImageIcon(rutaImagen);
		aEditar.setIcon(letras);
		aEditar.addMouseListener(new MouseListener() {
			@Override
			public void mouseReleased(MouseEvent e) {
			}
			@Override
			public void mousePressed(MouseEvent e) {
			}
			@Override
			public void mouseExited(MouseEvent e) {
				ImageIcon iI = new ImageIcon(getClass().getResource("/img/Palabras/volver.png"));
				aEditar.setIcon(iI);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				ImageIcon iI = new ImageIcon(getClass().getResource("/img/Palabras/volver_p.png"));
				aEditar.setIcon(iI);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				principal.mostrarComoJugar();
			}
		});
	}
	
}
