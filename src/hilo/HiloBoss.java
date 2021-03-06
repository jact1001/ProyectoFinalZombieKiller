package hilo;

import interfaz.InterfazZombieKiller;
import mundo.atacantes.Boss;
import mundo.atacantes.Enemigo;
import mundo.campo.ISurvivorCamp;
import mundo.utils.Params;

public class HiloBoss extends Thread{
	
		private InterfazZombieKiller principal;
		private Boss jefe;
		private ISurvivorCamp campo;

		public HiloBoss(InterfazZombieKiller principal, Boss jefe, ISurvivorCamp campo) {
			this.principal = principal;
			this.jefe = jefe;
			this.campo = campo;
		}

		@Override
		public void run() {
			try {
			int valorJefeCambiaPosicion = 0;
			while (campo.getEstadoJuego()!= Params.SIN_PARTIDA) {
					String estado = jefe.ataco();
					if (estado.equals(Enemigo.ATACANDO)) {
							if(jefe.getFrameActual()==19)
								principal.leDaAPersonaje();
							else if(jefe.getFrameActual()==21)
								campo.enemigoTerminaSuGolpe(jefe);
					}
				while (campo.getEstadoJuego()==Params.PAUSADO){
					sleep(500);
				}
				sleep(jefe.getLentitud());
				principal.refrescar();
			}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
}
