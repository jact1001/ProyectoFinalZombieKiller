package interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class PanelMenuButtons implements ActionListener, MouseListener, Button{
	
	private JButton aEditar;
	private URL rutaImagen;
	private String comando;
	private InterfazZombieKiller principal;
	
	private static final String CONTINUAR = "Continuar";
	private static final String INICIAR = "Iniciar Nuevo Juego";
	private static final String CARGAR = "Cargar Partida";
	private static final String GUARDAR = "Guardar Partida";
	private static final String COMO_JUGAR = "Cómo jugar";
	private static final String CREDITOS = "Créditos";
	private static final String MEJORES_PUNTAJES = "Mejores Puntajes";
	
	public PanelMenuButtons(JButton aEditar, URL rutaImagen, String comando, JFrame inter) {
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
		aEditar.setContentAreaFilled(false);
		aEditar.setActionCommand(comando);
		ImageIcon letras = new ImageIcon(rutaImagen);
		aEditar.setIcon(letras);
		aEditar.addActionListener(this);
		aEditar.addMouseListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		ImageIcon defaultIcon;
		if(comando == INICIAR) {
			defaultIcon = new ImageIcon(getClass().getResource("/img/Palabras/nuevo_p.png"));
			aEditar.setIcon(defaultIcon);
		}
		else if(comando == CARGAR) {
			defaultIcon = new ImageIcon(getClass().getResource("/img/Palabras/cargar_p.png"));
			aEditar.setIcon(defaultIcon);
		}
		else if(comando == CONTINUAR && aEditar.isEnabled()) {
			defaultIcon = new ImageIcon(getClass().getResource("/img/Palabras/continuar_p.png"));
			aEditar.setIcon(defaultIcon);
		}
		else if(comando == GUARDAR && aEditar.isEnabled()) {
			defaultIcon = new ImageIcon(getClass().getResource("/img/Palabras/guardar_p.png"));
			aEditar.setIcon(defaultIcon);
		}
		else if(comando == CREDITOS) {
			defaultIcon = new ImageIcon(getClass().getResource("/img/Palabras/creditos_p.png"));
			aEditar.setIcon(defaultIcon);
		}
		else if(comando == COMO_JUGAR) {
			defaultIcon = new ImageIcon(getClass().getResource("/img/Palabras/como jugar_p.png"));
			aEditar.setIcon(defaultIcon);
		}
		else if(comando == MEJORES_PUNTAJES) {
			defaultIcon = new ImageIcon(getClass().getResource("/img/Palabras/puntajes_p.png"));
			aEditar.setIcon(defaultIcon);
		}
	}

	@Override
	public void mouseExited(MouseEvent e) {
		ImageIcon defaultIcon;
		if(comando == INICIAR) {
			defaultIcon = new ImageIcon(getClass().getResource("/img/Palabras/nuevo.png"));
			aEditar.setIcon(defaultIcon);
		}
		else if(comando == CARGAR) {
			defaultIcon = new ImageIcon(getClass().getResource("/img/Palabras/cargar.png"));
			aEditar.setIcon(defaultIcon);
		}
		else if(comando == CONTINUAR) {
			defaultIcon = new ImageIcon(getClass().getResource("/img/Palabras/continuar.png"));
			aEditar.setIcon(defaultIcon);
		}
		else if(comando == GUARDAR) {
			defaultIcon = new ImageIcon(getClass().getResource("/img/Palabras/guardar.png"));
			aEditar.setIcon(defaultIcon);
		}
		else if(comando == CREDITOS) {
			defaultIcon = new ImageIcon(getClass().getResource("/img/Palabras/creditos.png"));
			aEditar.setIcon(defaultIcon);
		}
		else if(comando == COMO_JUGAR) {
			defaultIcon = new ImageIcon(getClass().getResource("/img/Palabras/como jugar.png"));
			aEditar.setIcon(defaultIcon);
		}
		else if(comando == MEJORES_PUNTAJES) {
			defaultIcon = new ImageIcon(getClass().getResource("/img/Palabras/puntajes.png"));
			aEditar.setIcon(defaultIcon);
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		String cmnd = arg0.getActionCommand();
		if (cmnd.equals(INICIAR))
			principal.iniciarNuevaPartida();
		else if (cmnd.equals(CONTINUAR))
			principal.pausarJuego();
		else if (cmnd.equals(CARGAR))
			principal.cargarJuego();
		else if(cmnd.equals(GUARDAR))
			principal.guardarJuego();
		else if(cmnd.equals(COMO_JUGAR))
			principal.mostrarComoJugar();
		else if(cmnd.equals(MEJORES_PUNTAJES))
			principal.mostrarPuntajes();
		else if(cmnd.equals(CREDITOS))
			principal.mostrarCreditos();
	}



}
