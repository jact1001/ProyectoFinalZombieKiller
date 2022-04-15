package mundo.campo;

import mundo.*;

import java.io.*;
import java.util.ArrayList;

interface ISurvivorCamp extends Cloneable {

	/**
	 * obtiene el estado actual del juego
	 * 
	 * @return estado del juego
	 */
	char getEstadoJuego();

	/**
	 * cambia el estado del juego
	 */
	void setEstadoJuego(char estadoJuego);

	/**
	 * obtiene la ronda en el instante en que se llama el m�todo, 0 si el estado
	 * es sin partida
	 * 
	 * @return ronda actual
	 */
	byte getRondaActual();

	/**
	 * obtiene el jefe de la partida, null si no existe
	 * 
	 * @return jefe
	 */
	Boss getJefe();

	/**
	 * cambia la ronda en la que se encuentra, en general sube una ronda, pero
	 * si se carga una partida puede variar
	 */
	void actualizarRondaActual(byte rondaActual);

	/**
	 * obtiene el personaje que est� disparando
	 * 
	 * @return personaje en juego
	 */
	Personaje getPersonaje();

	/**
	 * crea el jefe de la ronda 10
	 * 
	 * @return jefe creado
	 */
	Boss generarBoss();

	/**
	 * Genera un zombie respecto al nivel en que se encuentra
	 * </pre>
	 * la ronda va de 1 a 9
	 * 
	 * @param nivel
	 *            o ronda en el que se genera
	 * @return zombie creado
	 */
	Zombie generarZombie(int nivel);

	/**
	 * verifica el n�mero de zombies que se encuentra en la partida
	 * 
	 * @return n�mero de zombies
	 */
	int contarZombies();

	/**
	 * verifica por cada zombie la posici�n del disparo hasta que lo encuentre
	 * 
	 * @param posX
	 *            del disparo
	 * @param posY
	 *            del disparo
	 * @return true si le dio a alguno, false si fall� el disparo
	 */
	boolean leDio(int posX, int posY);

	/**
	 * cambia los estados del personaje con corde a que recibe un ara�azo
	 * zombie, termina el juego si muere
	 */
	void enemigoAtaca();

	/**
	 * cambia el estado del juego de pausado a en curso o viceversa
	 * 
	 * @return estado final
	 */
	char pausarJuego();

	/**
	 * Se eliminan todos los zombies que hay en la pantalla, cada uno brinda 50
	 * puntos al personaje
	 */
	void seLanzoGranada();

	/**
	 * obtiene el zombie nodo m�s lejano al personaje
	 * 
	 * @return zombie nodo de arriba
	 */
	Zombie getZombNodoLejano();

	/**
	 * obtiene el zombie nodo m�s cercano al personaje
	 * 
	 * @return zombie nodo de abajo
	 */
	Zombie getZombNodoCercano();

	/**
	 * carga el Puntaje que se guarda en forma de raiz de AB
	 * 
	 * @throws IOException
	 *             en caso de que no se haya guardado alg�n puntaje
	 * @throws ClassNotFoundException
	 *             en caso de que haya ocurrido un error al guardar los datos
	 */
	void cargarPuntajes() throws IOException, ClassNotFoundException;

	/**
	 * asigna la raiz del arbol binario y llena el arreglo de mejores puntajes
	 */
	void actualizarPuntajes(Puntaje raiz);

	/**
	 * carga la �ltima partida guardada devuelve la partida clonada porque la
	 * actual pasa a estar sin juego y as� elimina los hilos en ejecuci�n
	 * 
	 * @return una partida con las caracter�sticas de la nueva partida
	 * @throws Exception
	 *             de cualquier tipo para mostrar en pantalla
	 */
	ISurvivorCamp cargarPartida() throws Exception;

	/**
	 * guarda la partida actual
	 * 
	 * @throws IOException
	 *             en caso de que el jugador abra el ejecutable desde una
	 *             carpeta inv�lida
	 */
	void guardarPartida() throws IOException;

	/**
	 * cambia el arma del personaje, en esta versi�n solo tiene 2 armas em total
	 */
	void cambiarArma();

	/**
	 * el enemigo hace lo que le pertenece despu�s de terminar su golpe
	 * 
	 * @param enemy
	 *            el enemigo que acaba de atacar
	 */
	void enemigoTerminaSuGolpe(Enemigo enemy);

	/**
	 * verifica las posiciones de los zombies cercanos y su estado para saber si
	 * puede acuchillar
	 * 
	 * @return true si logra achuchillar a alguno
	 */
	boolean acuchilla(int x, int y);

	/**
	 * obtiene la cantidad de zombies que han salido en toda la partida
	 * 
	 * @return cantidad de zombies generados
	 */
	int getCantidadZombiesGenerados();

	/**
	 * devuelve el n�mero de referencia al arma que se encuentra a la derecha de
	 * la actual
	 * 
	 * @return n�mero del arma Mostrada
	 */
	int moverArmaVisibleDerecha();

	/**
	 * devuelve el n�mero de referencia al arma que se encuentra a la izquierda
	 * de la actual
	 * 
	 * @return n�mero del arma Mostrada
	 */
	int moverArmaVisibleIzquierda();

	/**
	 * obtiene el arma que se muestra actualmente en el panelArmas
	 * 
	 * @return n�mero del arma mostrada
	 */
	int getArmaMostrada();

	/**
	 * a�ade un puntaje obtenido por el jugador
	 * 
	 * @throws IOException
	 *             en caso de que ocurra un problema al guardar el puntaje
	 *             serializado
	 */
	void aniadirMejoresPuntajes(String nombreJugador) throws IOException;

	/**
	 * ordena el arreglo con corde a la cantidad de kill con tiros a la cabeza
	 * 
	 * @return arreglo de puntajes
	 */
	ArrayList<Puntaje> ordenarPuntajePorTirosALaCabeza();

	/**
	 * ordena el arreglo con corde a la cantidad de kill
	 * 
	 * @return arreglo de puntajes
	 */
	ArrayList<Puntaje> ordenarPuntajePorBajas();

	/**
	 * crea un arreglo con el �rbol binario usando el m�todo inOrden
	 * 
	 * @return arreglo de puntajes
	 */
	ArrayList<Puntaje> ordenarPuntajePorScore();

	/**
	 * obtiene la ra�z del �rbol binario de Puntajes
	 * 
	 * @return raizPuntajes
	 */
	Puntaje getRaizPuntajes();

	/**
	 * busca el puntaje del nombre ingresado por par�metro con b�squeda binaria
	 * 
	 * @return mejor puntaje del nombre buscado
	 */
	Puntaje buscarPuntajeDe(String nombre);

	/**
	 * Verifica que el nombre pasado por par�metro sea completamente alfab�tico
	 * @throws NombreInvalidoException
	 */
	void verificarNombre(String nombrePlayer) throws NombreInvalidoException;
}
