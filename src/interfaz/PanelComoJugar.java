package interfaz;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class PanelComoJugar extends JPanel implements IPanel {

	private static final String SALIR = "Salir";
	private PanelDatosCuriosos panelDatosC;
	private PanelArmas panelArmas;
	private JScrollPane scroll;
	private JButton butSalir;
	private InterfazZombieKiller principal;
	
	public PanelComoJugar (InterfazZombieKiller inter) {
		setBackground(Color.BLACK);
		setLayout(new BorderLayout());
		principal = inter;
		panelDatosC = new PanelDatosCuriosos();
		panelArmas = new PanelArmas(principal);
		
		butSalir = new JButton();
		add(butSalir, BorderLayout.SOUTH);
		butSalir.setActionCommand(SALIR);
		
		createButton(butSalir, getClass().getResource("/img/Palabras/volver.png"), SALIR, principal);
	
		JPanel aux = new JPanel ();
		aux.setLayout(new BorderLayout());
		aux.add(panelDatosC, BorderLayout.NORTH);
		aux.add(panelArmas, BorderLayout.CENTER);
		
		scroll = new JScrollPane(aux);
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		add(scroll, BorderLayout.CENTER);	
	}

	@Override
	public Button createButton(JButton aEditar, URL rutaImagen, String comando, JFrame inter) {
		// TODO Auto-generated method stub
		return new PanelComoJugarButtons(aEditar, rutaImagen, comando, inter);
	}

}
