package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.DebugGraphics;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

import mundo.campo.SurvivorCamp;
import mundo.utils.Params;

public class PanelMenu extends JPanel implements KeyListener, IPanel {

	private static final String CONTINUAR = "Continuar";
	private static final String INICIAR = "Iniciar Nuevo Juego";
	private static final String CARGAR = "Cargar Partida";
	private static final String GUARDAR = "Guardar Partida";
	private static final String COMO_JUGAR = "Cómo jugar";
	private static final String CREDITOS = "Créditos";
	private static final String MEJORES_PUNTAJES = "Mejores Puntajes";

	private InterfazZombieKiller principal;
	private JButton butIniciarJuego;
	private JButton butContinuar;
	private JButton butCargar;
	private JButton butGuardar;
	private JButton butComoJugar;
	private JButton butCreditos;
	private JButton butPuntajes;

	public PanelMenu(InterfazZombieKiller interfazZombieKiller) {
		setFocusable(true);
		setLayout(new GridLayout(9, 2));
		principal = interfazZombieKiller;
		addKeyListener(this);

		
		  JLabel aux = new JLabel();
		  add(aux); 
		  aux = new JLabel(); 
		  add(aux);
		  
		  aux = new JLabel(); 
		  add(aux);
		  butIniciarJuego = new JButton();
		  createButton(butIniciarJuego, getClass().getResource("/img/Palabras/nuevo.png"), INICIAR, principal);
		  add(butIniciarJuego);
		  
		  aux = new JLabel(); 
		  add(aux); 
		  butContinuar = new JButton();
		  createButton(butContinuar, getClass().getResource("/img/Palabras/continuar.png"), CONTINUAR, principal);
		  add(butContinuar);
		  
		  aux = new JLabel(); 
		  add(aux); 
		  butCargar = new JButton();
		  createButton(butCargar, getClass().getResource("/img/Palabras/cargar.png"), CARGAR, principal); 
		  add(butCargar);
		  
		  aux = new JLabel(); 
		  add(aux); 
		  butGuardar = new JButton();
		  createButton(butGuardar, getClass().getResource("/img/Palabras/guardar.png"), GUARDAR, principal);
		  add(butGuardar);
		  
		  aux = new JLabel(); 
		  add(aux); 
		  butComoJugar = new JButton();
		  createButton(butComoJugar, getClass().getResource("/img/Palabras/como jugar.png"), COMO_JUGAR, principal);
		  add(butComoJugar);
		  
		  aux = new JLabel(); 
		  add(aux); 
		  butPuntajes = new JButton();
		  createButton(butPuntajes, getClass().getResource("/img/Palabras/puntajes.png"), MEJORES_PUNTAJES, principal);
		  add(butPuntajes);
		  
		  aux = new JLabel(); 
		  add(aux); 
		  butCreditos = new JButton();
		  createButton(butCreditos, getClass().getResource("/img/Palabras/creditos.png"), CREDITOS, principal);
		  add(butCreditos);
		 
	}
	 
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		requestFocusInWindow();
		Image Palabras = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/img/Fondo/fondoMenu.png"));
		g.drawImage(Palabras, 0, 0, null);
		
		if(principal.getEstadoPartida()== Params.PAUSADO) {
			butContinuar.setEnabled(true);
			butGuardar.setEnabled(true);
		}
		else{
			butContinuar.setEnabled(false);
			butGuardar.setEnabled(false);
		}
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		System.out.println(arg0.getKeyCode());
		if (arg0.getKeyCode() == 80 && principal.getEstadoPartida()==Params.PAUSADO)
			principal.pausarJuego();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}


	@Override
	public Button createButton(JButton aEditar, URL rutaImagen, String comando, JFrame inter) {
		// TODO Auto-generated method stub
		return new PanelMenuButtons(aEditar, rutaImagen, comando, inter);
	}

}
