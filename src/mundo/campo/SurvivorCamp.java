package mundo.campo;

import mundo.armas.Armas;
import mundo.atacantes.Boss;
import mundo.atacantes.Enemigo;
import mundo.atacantes.Zombie;
import mundo.partida.Partida;
import mundo.personaje.Personaje;
import mundo.puntajes.Puntaje;
import mundo.puntajes.Puntajes;
import mundo.utils.NombreInvalidoException;
import mundo.utils.Utils;

import java.io.*;
import java.util.ArrayList;

public class SurvivorCamp implements ISurvivorCamp {

	Partida partida = new Partida();
	Puntajes puntajes = new Puntajes();
	Armas armas = new Armas();
	Utils utils = new Utils();

	/**
	 * obtiene el estado actual del juego
	 * 
	 * @return estado del juego
	 */
	public char getEstadoJuego() {
		return partida.getEstadoJuego();
	}

	/**
	 * cambia el estado del juego
	 * 
	 * @param estadoJuego
	 */
	public void setEstadoJuego(char estadoJuego) {
		partida.setEstadoJuego(estadoJuego);
	}

	/**
	 * obtiene la ronda en el instante en que se llama el método, 0 si el estado
	 * es sin partida
	 * 
	 * @return ronda actual
	 */
	public byte getRondaActual() {
		return partida.getRondaActual();
	}

	/**
	 * cambia la ronda en la que se encuentra, en general sube una ronda, pero
	 * si se carga una partida puede variar
	 */
	public void actualizarRondaActual(byte rondaActual) {
		partida.actualizarRondaActual(rondaActual);
	}

	/**
	 * obtiene el jefe de la partida, null si no existe
	 * 
	 * @return jefe
	 */
	public Boss getJefe() {
		return partida.getJefe();
	}

	/**
	 * obtiene el personaje que está disparando
	 * 
	 * @return personaje en juego
	 */
	public Personaje getPersonaje() {
		return partida.getPersonaje();
	}

	/**
	 * crea el jefe de la ronda 10
	 * 
	 * @return jefe creado
	 */
	public Boss generarBoss() {
		return partida.generarBoss();
	}

	/**
	 * Genera un zombie respecto al nivel en que se encuentra
	 * </pre>
	 * la ronda va de 1 a 9
	 * 
	 * @param nivel
	 *            o ronda en el que se genera
	 * @return zombie creado
	 */
	public Zombie generarZombie(int nivel) {
		return partida.generarZombie(nivel);
	}

	/**
	 * verifica el número de zombies que se encuentra en la partida
	 * 
	 * @return número de zombies
	 */
	public int contarZombies() {
		return partida.contarZombies();
	}

	/**
	 * verifica por cada zombie la posición del disparo hasta que lo encuentre
	 * 
	 * @param posX
	 *            del disparo
	 * @param posY
	 *            del disparo
	 * @return true si le dio a alguno, false si falló el disparo
	 */
	public boolean leDio(int posX, int posY) {
		return partida.leDio(posX, posY);
	}

	/**
	 * cambia los estados del personaje con corde a que recibe un arañazo
	 * zombie, termina el juego si muere
	 */
	public void enemigoAtaca() {
		partida.enemigoAtaca();
	}

	/**
	 * cambia el estado del juego de pausado a en curso o viceversa
	 * 
	 * @return estado final
	 */
	public char pausarJuego() {
		return partida.pausarJuego();
	}

	/**
	 * Se eliminan todos los zombies que hay en la pantalla, cada uno brinda 50
	 * puntos al personaje
	 */
	public void seLanzoGranada() {
		partida.seLanzoGranada();
	}

	/**
	 * obtiene el zombie nodo más lejano al personaje
	 * 
	 * @return zombie nodo de arriba
	 */
	public Zombie getZombNodoLejano() {
		return partida.getZombNodoLejano();
	}

	/**
	 * obtiene el zombie nodo más cercano al personaje
	 * 
	 * @return zombie nodo de abajo
	 */
	public Zombie getZombNodoCercano() {
		return partida.getZombNodoCercano();
	}

	/**
	 * carga el Puntaje que se guarda en forma de raiz de AB
	 * 
	 * @throws IOException
	 *             en caso de que no se haya guardado algún puntaje
	 * @throws ClassNotFoundException
	 *             en caso de que haya ocurrido un error al guardar los datos
	 */
	public void cargarPuntajes() throws IOException, ClassNotFoundException {
		puntajes.cargarPuntajes();
	}

	/**
	 * asigna la raiz del arbol binario y llena el arreglo de mejores puntajes
	 *
	 * @param raiz
	 */
	public void actualizarPuntajes(Puntaje raiz) {
		puntajes.actualizarPuntajes(raiz);
	}

	/**
	 * carga la última partida guardada devuelve la partida clonada porque la
	 * actual pasa a estar sin juego y así elimina los hilos en ejecución
	 * 
	 * @return una partida con las características de la nueva partida
	 * @throws Exception
	 *             de cualquier tipo para mostrar en pantalla
	 */
	public SurvivorCamp cargarPartida() throws Exception {
		return partida.cargarPartida();
	}

	/**
	 * guarda la partida actual
	 * 
	 * @throws IOException
	 *             en caso de que el jugador abra el ejecutable desde una
	 *             carpeta inválida
	 */
	public void guardarPartida() throws IOException {
		partida.guardarPartida();
	}

	/**
	 * cambia el arma del personaje, en esta versión solo tiene 2 armas em total
	 */
	public void cambiarArma() {
		partida.cambiarArma();
	}

	/**
	 * el enemigo hace lo que le pertenece después de terminar su golpe
	 * 
	 * @param enemy
	 *            el enemigo que acaba de atacar
	 */
	public void enemigoTerminaSuGolpe(Enemigo enemy) {
		partida.enemigoTerminaSuGolpe(enemy);
	}

	/**
	 * verifica las posiciones de los zombies cercanos y su estado para saber si
	 * puede acuchillar
	 * 
	 * @param x
	 * @param y
	 * @return true si logra achuchillar a alguno
	 */
	public boolean acuchilla(int x, int y) {
		return partida.acuchilla(x, y);
	}

	/**
	 * obtiene la cantidad de zombies que han salido en toda la partida
	 * 
	 * @return cantidad de zombies generados
	 */
	public int getCantidadZombiesGenerados() {
		return partida.getCantidadZombiesGenerados();
	}

	/**
	 * devuelve el número de referencia al arma que se encuentra a la derecha de
	 * la actual
	 * 
	 * @return número del arma Mostrada
	 */
	public int moverArmaVisibleDerecha() {
		return armas.moverArmaVisibleDerecha();
	}

	/**
	 * devuelve el número de referencia al arma que se encuentra a la izquierda
	 * de la actual
	 * 
	 * @return número del arma Mostrada
	 */
	public int moverArmaVisibleIzquierda() {
		return armas.moverArmaVisibleIzquierda();
	}

	/**
	 * obtiene el arma que se muestra actualmente en el panelArmas
	 * 
	 * @return número del arma mostrada
	 */
	public int getArmaMostrada() {
		return armas.getArmaMostrada();
	}

	/**
	 * añade un puntaje obtenido por el jugador
	 *
	 * @param nombreJugador
	 * @throws IOException
	 *             en caso de que ocurra un problema al guardar el puntaje
	 *             serializado
	 */
	public void aniadirMejoresPuntajes(String nombreJugador) throws IOException {
		puntajes.aniadirMejoresPuntajes(nombreJugador, partida.getPersonaje());
	}

	/**
	 * ordena el arreglo con corde a la cantidad de kill con tiros a la cabeza
	 * 
	 * @return arreglo de puntajes
	 */
	public ArrayList<Puntaje> ordenarPuntajePorTirosALaCabeza() {
		return puntajes.ordenarPuntajePorTirosALaCabeza();
	}

	/**
	 * ordena el arreglo con corde a la cantidad de kill
	 * 
	 * @return arreglo de puntajes
	 */
	public ArrayList<Puntaje> ordenarPuntajePorBajas() {
		return puntajes.ordenarPuntajePorBajas();
	}

	/**
	 * crea un arreglo con el árbol binario usando el método inOrden
	 * 
	 * @return arreglo de puntajes
	 */
	public ArrayList<Puntaje> ordenarPuntajePorScore() {
		return puntajes.ordenarPuntajePorScore();
	}

	/**
	 * obtiene la raíz del árbol binario de Puntajes
	 * 
	 * @return raizPuntajes
	 */
	public Puntaje getRaizPuntajes() {
		return puntajes.getRaizPuntajes();
	}

	/**
	 * busca el puntaje del nombre ingresado por parámetro con búsqueda binaria
	 * 
	 * @param nombre
	 * @return mejor puntaje del nombre buscado
	 */
	public Puntaje buscarPuntajeDe(String nombre) {
		return puntajes.buscarPuntajeDe(nombre);
	}

	/**
	 * Verifica que el nombre pasado por parámetro sea completamente alfabético
	 * @param nombrePlayer
	 * @throws NombreInvalidoException
	 */
	public void verificarNombre(String nombrePlayer) throws NombreInvalidoException {
		utils.verificarNombre(nombrePlayer);
	}
}
