package mundo.puntaje;

import mundo.ComparadorPuntajePorNombre;
import mundo.Personaje;
import mundo.Puntaje;

import java.io.*;
import java.util.ArrayList;

public class Puntajes {

    private ArrayList<Puntaje> mejoresPuntajes;
    private Puntaje raizPuntajes;

    public Puntajes() {
        mejoresPuntajes = new ArrayList<>();
    }

    /**
     * añade un puntaje obtenido por el jugador
     *
     * @param nombreJugador
     * @throws IOException
     *             en caso de que ocurra un problema al guardar el puntaje
     *             serializado
     */
    public void aniadirMejoresPuntajes(String nombreJugador, Personaje personaje) throws IOException {
        mundo.Puntaje score = new mundo.Puntaje(personaje.getScore(), personaje.getHeadShots(), personaje.getMatanza(),
                nombreJugador);
        if (raizPuntajes != null)
            raizPuntajes.aniadirPorPuntaje(score);
        else
            raizPuntajes = score;
        mejoresPuntajes.add(score);
        guardarPuntajes();
    }

    /**
     * guarda el Puntaje raíz en la carpeta
     *
     * @throws IOException
     *             en caso de que ocurra un problema al guardar la raíz con las
     *             nuevas asociaciones
     */
    private void guardarPuntajes() throws IOException {
        File carpeta = new File(System.getProperty("user.dir") + "/PartidasGuardadas");
        File archivoPuntajes = new File(carpeta.getAbsolutePath() + "/puntajes.txt");
        if (!carpeta.exists())
            carpeta.mkdirs();
        ObjectOutputStream escritor = new ObjectOutputStream(new FileOutputStream(archivoPuntajes));
        escritor.writeObject(raizPuntajes);
        escritor.close();
    }

    /**
     * asigna la raiz del arbol binario y llena el arreglo de mejores puntajes
     *
     * @param raiz
     */
    public void actualizarPuntajes(Puntaje raiz) {
        raizPuntajes = raiz;
        if (raizPuntajes != null) {
            mejoresPuntajes = new ArrayList<>();
            raizPuntajes.generarListaInOrden(mejoresPuntajes);
        }
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
        File carpeta = new File(System.getProperty("user.dir") + "/PartidasGuardadas");
        File archivoPuntajes = new File(carpeta.getAbsolutePath() + "/puntajes.txt");
        ObjectInputStream oIS = new ObjectInputStream(new FileInputStream(archivoPuntajes));
        Puntaje puntaje = (Puntaje) oIS.readObject();
        actualizarPuntajes(puntaje);
    }

    /**
     * ordena el arreglo con corde a la cantidad de kill con tiros a la cabeza
     *
     * @return arreglo de puntajes
     */
    public ArrayList<Puntaje> ordenarPuntajePorTirosALaCabeza() {
        for (int i = 0; i < mejoresPuntajes.size(); i++) {
            Puntaje masHeadShot = mejoresPuntajes.get(i);
            int posACambiar = i;
            for (int j = i; j < mejoresPuntajes.size() - 1; j++) {
                if (masHeadShot.getTirosALaCabeza() - mejoresPuntajes.get(j + 1).getTirosALaCabeza() < 0) {
                    masHeadShot = mejoresPuntajes.get(j + 1);
                    posACambiar = j + 1;
                } else if (masHeadShot.getTirosALaCabeza() - mejoresPuntajes.get(j + 1).getTirosALaCabeza() == 0) {
                    if (masHeadShot.compareTo(mejoresPuntajes.get(j + 1)) < 0) {
                        masHeadShot = mejoresPuntajes.get(j + 1);
                        posACambiar = j + 1;
                    }
                }
            }
            mejoresPuntajes.set(posACambiar, mejoresPuntajes.get(i));
            mejoresPuntajes.set(i, masHeadShot);
        }
        return mejoresPuntajes;
    }

    /**
     * ordena el arreglo con corde a la cantidad de kill
     *
     * @return arreglo de puntajes
     */
    public ArrayList<Puntaje> ordenarPuntajePorBajas() {
        for (int i = 0; i < mejoresPuntajes.size(); i++) {
            Puntaje masKill = mejoresPuntajes.get(i);
            int posACambiar = i;
            for (int j = i; j < mejoresPuntajes.size() - 1; j++) {
                if (compare(masKill, mejoresPuntajes.get(j + 1)) < 0) {
                    masKill = mejoresPuntajes.get(j + 1);
                    posACambiar = j + 1;
                }
            }
            mejoresPuntajes.set(posACambiar, mejoresPuntajes.get(i));
            mejoresPuntajes.set(i, masKill);
        }
        return mejoresPuntajes;
    }

    /**
     * crea un arreglo con el árbol binario usando el método inOrden
     *
     * @return arreglo de puntajes
     */
    public ArrayList<Puntaje> ordenarPuntajePorScore() {
        ArrayList ordenados = new ArrayList<>();
        if (raizPuntajes != null)
            raizPuntajes.generarListaInOrden(ordenados);
        return ordenados;
    }

    /**
     * obtiene la raíz del árbol binario de Puntajes
     *
     * @return raizPuntajes
     */
    public Puntaje getRaizPuntajes() {
        return raizPuntajes;
    }

    /**
     * busca el puntaje del nombre ingresado por parámetro con búsqueda binaria
     *
     * @param nombre
     * @return mejor puntaje del nombre buscado
     */
    public Puntaje buscarPuntajeDe(String nombre) {
        mejoresPuntajes.sort(new ComparadorPuntajePorNombre());
        int inicio = 0;
        int fin = mejoresPuntajes.size() - 1;
        Puntaje puntajeBuscado = null;
        int medio = (inicio + fin) / 2;
        while (inicio <= fin && puntajeBuscado == null) {
            if (mejoresPuntajes.get(medio).getNombreKiller().compareToIgnoreCase(nombre) == 0) {
                puntajeBuscado = mejoresPuntajes.get(medio);
                boolean hayMas = true;
                medio = medio + 1;
                while (medio <= fin && hayMas) {
                    if (mejoresPuntajes.get(medio).getNombreKiller().compareToIgnoreCase(nombre) == 0)
                        puntajeBuscado = mejoresPuntajes.get(medio);
                    else
                        hayMas = false;
                    medio = medio + 1;
                }
            } else if (mejoresPuntajes.get(medio).getNombreKiller().compareToIgnoreCase(nombre) > 0)
                fin = medio - 1;
            else
                inicio = medio + 1;
            medio = (inicio + fin) / 2;
        }
        return puntajeBuscado;
    }

    private int compare(Puntaje o1, Puntaje o2) {
        int porBajas = o1.getBajas() - o2.getBajas();
        if (porBajas != 0)
            return porBajas;
        return o1.compareTo(o2);
    }
}
