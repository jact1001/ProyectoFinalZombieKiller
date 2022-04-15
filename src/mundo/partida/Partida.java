package mundo.partida;

import mundo.*;
import mundo.campo.SurvivorCamp;

import java.io.*;

public class Partida {

    public static final char PAUSADO = 'P';
    public static final char EN_CURSO = 'J';
    public static final char SIN_PARTIDA = 'N';

    private char estadoJuego;

    public static final int ANCHO_PANTALLA = 1000;

    private int cantidadZombiesGenerados;

    public static final int NUMERO_ZOMBIES_RONDA = 16;

    private byte rondaActual = 0;

    private Personaje personaje;

    private Zombie zombNodoLejano;
    private Zombie zombNodoCercano;
    private Boss jefe;

    public Partida() {
        personaje = new Personaje();
        estadoJuego = SIN_PARTIDA;
        zombNodoLejano = new Caminante();
        zombNodoCercano = new Caminante();
        zombNodoLejano.setLentitud((short) 500);
        zombNodoLejano.setAlFrente(zombNodoCercano);
        zombNodoCercano.setAtras(zombNodoLejano);
    }

    /**
     * carga la última partida guardada devuelve la partida clonada porque la
     * actual pasa a estar sin juego y así elimina los hilos en ejecución
     *
     * @return una partida con las características de la nueva partida
     * @throws Exception
     *             de cualquier tipo para mostrar en pantalla
     */
    public mundo.campo.SurvivorCamp cargarPartida() throws Exception {
        File carpeta = new File(System.getProperty("user.dir") + "/PartidasGuardadas");
        File archivoPersonaje = new File(carpeta.getAbsolutePath() + "/personaje.txt");
        try {
            ObjectInputStream oIS = new ObjectInputStream(new FileInputStream(archivoPersonaje));
            Personaje personaje = (Personaje) oIS.readObject();
            oIS.close();
            cargarDatosCampo(carpeta, personaje);
        } catch (IOException e) {
            throw new Exception(
                    "No se ha encontrado una partida guardada o es posible que haya abierto el juego desde \"Acceso rápido\"");
        } catch (DatosErroneosException e) {
            throw new Exception(e.getMessage());
        } catch (NumberFormatException e) {
            throw new Exception("En el archivo hay caracteres donde deberían haber números");
        }
        return (SurvivorCamp) clone();
    }

    /**
     * guarda la partida actual
     *
     * @throws IOException
     *             en caso de que el jugador abra el ejecutable desde una
     *             carpeta inválida
     */
    public void guardarPartida() throws IOException {
        File carpeta = new File(System.getProperty("user.dir") + "/PartidasGuardadas");
        File archivoPersonaje = new File(carpeta.getAbsolutePath() + "/personaje.txt");
        if (!carpeta.exists())
            carpeta.mkdirs();
        ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream(archivoPersonaje));
        escritor.writeObject(personaje);
        escritor.close();
        try {
            guardarDatosCampo(carpeta);
        } catch (IOException e) {
            throw new IOException(
                    "Error al guardar el archivo, es posible que haya abierto el juego desde \"Acceso rápido\"");
        }
    }

    /**
     * carga los datos de los enemigos del archivo de texto plano
     *
     * @param carpeta
     * @param personaje
     *            para asignarselo a la partida si todos los datos son válidos
     * @throws Exception
     *             si hay información inválida
     */
    private void cargarDatosCampo(File carpeta, Personaje personaje) throws Exception {
        File datosZombie = new File(carpeta.getAbsolutePath() + "/zombies.txt");
        BufferedReader bR = new BufferedReader(new FileReader(datosZombie));
        int ronda = 0;
        if (personaje.getMatanza() % NUMERO_ZOMBIES_RONDA == 0)
            ronda = personaje.getMatanza() / NUMERO_ZOMBIES_RONDA;
        else
            ronda = personaje.getMatanza() / NUMERO_ZOMBIES_RONDA + 1;
        String lineaActual = bR.readLine();
        int contadorZombiesEnPantalla = 0;
        Zombie masCercano = null;
        Zombie ultimoAgregado = null;

        while (lineaActual != null) {
            if (!lineaActual.startsWith("/") && !lineaActual.equals("")) {
                String[] datos = lineaActual.split("_");
                Zombie aAgregar = null;
                byte salud = Byte.parseByte(datos[0]);
                if (datos.length > 1) {
                    int posX = Integer.parseInt(datos[1]);
                    int posY = Integer.parseInt(datos[2]);
                    String estadoActual = datos[3];
                    byte frameActual = Byte.parseByte(datos[4]);
                    verificarDatosZombie(posX, posY, estadoActual, frameActual);
                    if (datos.length == 7) {
                        int direccionX = Integer.parseInt(datos[5]);
                        int direccionY = Integer.parseInt(datos[6]);
                        verificarDatosCaminante(direccionX, direccionY);
                        aAgregar = new Caminante(posX, posY, direccionX, direccionY, estadoActual, frameActual, salud,
                                ronda);
                    } else if (datos.length == 5) {
                        aAgregar = new Rastrero(posX, posY, estadoActual, frameActual, salud, ronda);
                    }
                }
                if (aAgregar != null) {
                    if (masCercano != null) {
                        ultimoAgregado.setAtras(aAgregar);
                        aAgregar.setAlFrente(ultimoAgregado);
                    } else
                        masCercano = aAgregar;
                    ultimoAgregado = aAgregar;
                    if (!aAgregar.getEstadoActual().equals(Zombie.MURIENDO)
                            && !aAgregar.getEstadoActual().equals(Zombie.MURIENDO_INCENDIADO))
                        contadorZombiesEnPantalla++;
                } else
                    cargaBossSiExiste(ronda, salud);
            }
            lineaActual = bR.readLine();
        }
        bR.close();
        int zombiesExcedidos = contadorZombiesEnPantalla + (personaje.getMatanza() % NUMERO_ZOMBIES_RONDA)
                - NUMERO_ZOMBIES_RONDA;
        if (zombiesExcedidos > 0)
            throw new DatosErroneosException(zombiesExcedidos);
        else {
            enlazaZombiesSiHabian(masCercano, ultimoAgregado);
            rondaActual = (byte) ronda;
            cantidadZombiesGenerados = personaje.getMatanza() + contadorZombiesEnPantalla;
            this.personaje = personaje;
        }
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
        short level = (short) nivel;
        int tipoZombie = 0;
        if ((level == 3 || level == 4 || level == 8))
            tipoZombie = (int) (Math.random() * 2);
        else if (level == 6 || level == 9)
            tipoZombie = 1;
        Zombie aGenerar;

        if (tipoZombie == 1)
            aGenerar = new Rastrero(level, zombNodoLejano);
        else
            aGenerar = new Caminante(level, zombNodoLejano);

        aGenerar.introducirse(zombNodoLejano.getAlFrente(), zombNodoLejano);
        cantidadZombiesGenerados++;
        return aGenerar;
    }

    /**
     * obtiene la cantidad de zombies que han salido en toda la partida
     *
     * @return cantidad de zombies generados
     */
     public int getCantidadZombiesGenerados() {
        return cantidadZombiesGenerados;
    }

    /**
     * obtiene la ronda en el instante en que se llama el método, 0 si el estado
     * es sin partida
     *
     * @return ronda actual
     */
    public byte getRondaActual() {
        return rondaActual;
    }

    /**
     * cambia la ronda en la que se encuentra, en general sube una ronda, pero
     * si se carga una partida puede variar
     */
    public void actualizarRondaActual(byte rondaActual) {
        this.rondaActual = rondaActual;
    }

    /**
     * método auxiliar para generar un boss con respecto a la salud que le entra
     * por parámetro
     *
     * @param ronda
     * @param salud
     */
    private void cargaBossSiExiste(int ronda, byte salud) {
        if (ronda == 10) {
            jefe = new Boss(salud);
            zombNodoCercano.setAtras(zombNodoLejano);
            zombNodoLejano.setAlFrente(zombNodoCercano);
        }
    }

    /**
     * conecta los zombies cargados a los nodos para que sean parte del juego
     *
     * @param masCercano
     * @param ultimoAgregado
     */
    private void enlazaZombiesSiHabian(Zombie masCercano, Zombie ultimoAgregado) {
        if (ultimoAgregado != null) {
            zombNodoCercano.setAtras(masCercano);
            masCercano.setAlFrente(zombNodoCercano);
            zombNodoLejano.setAlFrente(ultimoAgregado);
            ultimoAgregado.setAtras(zombNodoLejano);
            jefe = null;
        }
    }

    /**
     * método auxiliar que verifica las direcciones a las que se mueven los
     * caminantes la suma de sus direcciones no puede ser menor a 4
     *
     * @param direccionX
     * @param direccionY
     * @throws DatosErroneosException
     */
    private void verificarDatosCaminante(int direccionX, int direccionY) throws DatosErroneosException {
        if (Math.abs(direccionX) + direccionY < 4)
            throw new DatosErroneosException();
    }

    /**
     * verifica que los datos generales de loz zombies estén dentro de los
     * límites del juego
     *
     * @param posX
     * @param posY
     * @param estadoActual
     * @param frameActual
     * @throws DatosErroneosException
     */
    private void verificarDatosZombie(int posX, int posY, String estadoActual, byte frameActual)
            throws DatosErroneosException {
        if (posX > ANCHO_PANTALLA - Zombie.ANCHO_IMAGEN || posX < 0 || posY > Zombie.POS_ATAQUE
                || posY < Zombie.POS_INICIAL || frameActual > 31
                || (!estadoActual.equals(Zombie.CAMINANDO) && !estadoActual.equals(Zombie.MURIENDO_INCENDIADO)
                && !estadoActual.equals(Zombie.MURIENDO) && !estadoActual.equals(Zombie.ATACANDO)))
            throw new DatosErroneosException();
    }

    /**
     * escribe los datos de los enemigos
     *
     * @param carpeta
     *            carpeta en la que se va a guardar el archivo
     * @throws IOException
     *             en caso de que ocurra un error inesperado
     */
    private void guardarDatosCampo(File carpeta) throws IOException {
        File datosZombie = new File(carpeta.getAbsolutePath() + "/zombies.txt");
        BufferedWriter bW = new BufferedWriter(new FileWriter(datosZombie));
        String texto = "/salud/posX/posY/estado/frame/dirX/dirY";
        if (jefe != null)
            texto += "\n" + jefe.getSalud();
        else
            texto = escribirDatosZombie(texto, zombNodoCercano.getAtras());

        bW.write(texto);
        bW.close();
    }

    /**
     * escribe los datos de los zombies de manera recursiva
     *
     * @param datos
     * @param actual
     * @return el texto con la información de los zombies
     */
    private String escribirDatosZombie(String datos, Zombie actual) {
        if (actual.getEstadoActual().equals(Zombie.NODO))
            return datos;
        datos += "\n" + actual.getSalud() + "_" + actual.getPosX() + "_" + actual.getPosY() + "_"
                + actual.getEstadoActual() + "_" + actual.getFrameActual();
        if (actual instanceof Caminante) {
            datos += "_" + ((Caminante) actual).getDireccionX();
            datos += "_" + ((Caminante) actual).getDireccionY();
        }
        return escribirDatosZombie(datos, actual.getAtras());
    }

    /**
     * crea el jefe de la ronda 10
     *
     * @return jefe creado
     */
    public Boss generarBoss() {
        jefe = new Boss();
        return jefe;
    }

    /**
     * cambia el arma del personaje, en esta versión solo tiene 2 armas em total
     */
    public void cambiarArma() {
        personaje.cambiarArma();
    }

    /**
     * el enemigo hace lo que le pertenece después de terminar su golpe
     *
     * @param enemy
     *            el enemigo que acaba de atacar
     */
    public void enemigoTerminaSuGolpe(Enemigo enemy) {
        personaje.setEnsangrentado(false);
        enemy.terminaDeAtacar();
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
        Zombie aAcuchillar = zombNodoCercano.getAtras();
        boolean seEncontro = false;
        while (!aAcuchillar.getEstadoActual().equals(Zombie.NODO) && !seEncontro) {
            if (aAcuchillar.getEstadoActual().equals(Zombie.ATACANDO)
                    && aAcuchillar.comprobarDisparo(x, y, Cuchillo.DANIO)) {
                if (aAcuchillar.getEstadoActual().equals(Zombie.MURIENDO))
                    personaje.aumentarScore(40);
                seEncontro = true;
                personaje.setEnsangrentado(false);
                personaje.getCuchillo().setEstado(Arma.CARGANDO);
            }
            aAcuchillar = aAcuchillar.getAtras();
        }
        if (jefe != null) {
            if (jefe.getEstadoActual().equals(Enemigo.ATACANDO) && jefe.comprobarDisparo(x, y, Cuchillo.DANIO)) {
                personaje.setEnsangrentado(false);
                personaje.getCuchillo().setEstado(Arma.CARGANDO);
                seEncontro = true;
                if (jefe.getEstadoActual().equals(Boss.DERROTADO)) {
                    personaje.aumentarScore(100);
                    estadoJuego = SIN_PARTIDA;
                }
            }
        }
        return seEncontro;
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
        personaje.ataco();
        boolean leDio = false;
        Zombie actual = zombNodoCercano.getAtras();
        while (!actual.getEstadoActual().equals(Zombie.NODO) && !leDio) {
            if (actual.comprobarDisparo(posX, posY, personaje.getPrincipal().getDanio())) {
                leDio = true;
                personaje.getPrincipal().setEnsangrentada(true);
                if (actual.getSalud() <= 0) {
                    personaje.aumentarScore(10 + actual.getSalud() * (-10));
                    if (actual.getEstadoActual().equals(Zombie.MURIENDO_HEADSHOT))
                        personaje.aumentarTirosALaCabeza();
                }

                personaje.setEnsangrentado(false);
            }
            actual = actual.getAtras();
        }
        if (jefe != null)
            if (jefe.comprobarDisparo(posX, posY, personaje.getPrincipal().getDanio())) {
                personaje.getPrincipal().setEnsangrentada(true);
                personaje.setEnsangrentado(false);
                leDio = true;
                if (jefe.getEstadoActual().equals(Boss.DERROTADO)) {
                    personaje.aumentarScore(20 + jefe.getSalud() * (-20));
                    estadoJuego = SIN_PARTIDA;
                }
            }
        return leDio;
    }

    /**
     * cambia los estados del personaje con corde a que recibe un arañazo
     * zombie, termina el juego si muere
     */
    public void enemigoAtaca() {
        personaje.setEnsangrentado(true);
        personaje.setSalud((byte) (personaje.getSalud() - 1));
        if (personaje.getSalud() <= 0) {
            estadoJuego = SIN_PARTIDA;
        }
    }

    /**
     * obtiene el jefe de la partida, null si no existe
     *
     * @return jefe
     */
    public Boss getJefe() {
        return jefe;
    }

    /**
     * verifica el número de zombies que se encuentra en la partida
     *
     * @return número de zombies
     */
    public int contarZombies() {
        Zombie actual = zombNodoCercano.getAtras();
        int contador = 0;
        while (!actual.getEstadoActual().equals(Zombie.NODO)) {
            contador++;
            actual = actual.getAtras();
        }
        return contador;
    }

    /**
     * obtiene el personaje que está disparando
     *
     * @return personaje en juego
     */
    public Personaje getPersonaje() {
        return personaje;
    }

    /**
     * obtiene el estado actual del juego
     *
     * @return estado del juego
     */
    public char getEstadoJuego() {
        return estadoJuego;
    }

    /**
     * cambia el estado del juego
     *
     * @param estadoJuego
     */
    public void setEstadoJuego(char estadoJuego) {
        this.estadoJuego = estadoJuego;
    }

    /**
     * cambia el estado del juego de pausado a en curso o viceversa
     *
     * @return estado final
     */
    public char pausarJuego() {
        if (estadoJuego == PAUSADO)
            estadoJuego = EN_CURSO;
        else
            estadoJuego = PAUSADO;
        return estadoJuego;
    }

    /**
     * Se eliminan todos los zombies que hay en la pantalla, cada uno brinda 50
     * puntos al personaje
     */
    public void seLanzoGranada() {
        Zombie actual = zombNodoCercano.getAtras();
        personaje.setEnsangrentado(false);
        while (!actual.getEstadoActual().equals(Zombie.NODO)) {
            if (actual.recibeGranada())
                personaje.aumentarScore(50);
            actual = actual.getAtras();
        }
        personaje.lanzoGranada();
    }

    /**
     * obtiene el zombie nodo más lejano al personaje
     *
     * @return zombie nodo de arriba
     */
    public Zombie getZombNodoLejano() {
        return zombNodoLejano;
    }

    /**
     * obtiene el zombie nodo más cercano al personaje
     *
     * @return zombie nodo de abajo
     */
    public Zombie getZombNodoCercano() {
        return zombNodoCercano;
    }
}
