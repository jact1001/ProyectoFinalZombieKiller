package hilo;

import interfaz.InterfazZombieKiller;
import mundo.atacantes.Caminante;
import mundo.atacantes.Enemigo;
import mundo.atacantes.Rastrero;
import mundo.campo.ISurvivorCamp;
import mundo.atacantes.Zombie;
import mundo.utils.Params;

public class HiloEnemigo extends Thread {

	private InterfazZombieKiller principal;
	private Zombie nodoCercano;
	private ISurvivorCamp campo;

	public HiloEnemigo(InterfazZombieKiller principal, Zombie nodoCercano, ISurvivorCamp campo) {
		this.principal = principal;
		this.nodoCercano = nodoCercano;
		this.campo = campo;
	}

	@Override
	public void run() {
		try {
			while (campo.getEstadoJuego() != Params.SIN_PARTIDA) {
				Zombie enMovimiento = nodoCercano.getAtras();
				while (!enMovimiento.getEstadoActual().equals(Zombie.NODO)) {
					String estado = enMovimiento.ataco();
					if (estado.equals(Enemigo.ATACANDO)) {
						if (enMovimiento instanceof Caminante) {
							if (enMovimiento.getFrameActual() == 8)
								principal.leDaAPersonaje();
							else if (enMovimiento.getFrameActual() == 13)
								campo.enemigoTerminaSuGolpe(enMovimiento);
						} else if (enMovimiento instanceof Rastrero) {
							if (enMovimiento.getFrameActual() == 13)
								principal.leDaAPersonaje();
							else if (enMovimiento.getFrameActual() == 16)
								campo.enemigoTerminaSuGolpe(enMovimiento);
						}
					} else if (estado.equals(Zombie.MURIENDO) || estado.equals(Zombie.MURIENDO_INCENDIADO)) {
						// System.out.println(chombi.getFrameActual());
						if (enMovimiento instanceof Caminante) {
							if (enMovimiento.getFrameActual() == 17) {
								if (estado.equals(Zombie.MURIENDO))
									enMovimiento.eliminarse();
								else {
									nodoCercano.setAtras(campo.getZombNodoLejano());
									campo.getZombNodoLejano().setAlFrente(nodoCercano);
									enMovimiento = nodoCercano;
								}
							}
						} else if (enMovimiento instanceof Rastrero) {
							if (enMovimiento.getFrameActual() == 11) {
								if (estado.equals(Zombie.MURIENDO))
									enMovimiento.eliminarse();
								else {
									nodoCercano.setAtras(campo.getZombNodoLejano());
									campo.getZombNodoLejano().setAlFrente(nodoCercano);
									enMovimiento = nodoCercano;
								}
							}
						}
					}
					enMovimiento = enMovimiento.getAtras();
				}
				while (campo.getEstadoJuego() == Params.PAUSADO) {
					sleep(500);
				}
				// System.out.println("Corre el hilo de los enemigos");
				sleep(nodoCercano.getAtras().getLentitud());
				principal.refrescar();
			}
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}
}
