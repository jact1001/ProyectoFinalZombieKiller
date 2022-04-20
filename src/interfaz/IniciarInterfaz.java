package interfaz;

public class IniciarInterfaz {

	public static void main(String[] args) {
		InterfazZombieKiller inter = InterfazZombieKiller.getInstance();
		inter.setVisible(true);
		inter.setLocationRelativeTo(null);
	}
}
