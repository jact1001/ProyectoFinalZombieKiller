package mundo.armas;

public class Armas {

    private int armaMostrada;

    /**
     * devuelve el número de referencia al arma que se encuentra a la derecha de
     * la actual
     *
     * @return número del arma Mostrada
     */
    public int moverArmaVisibleDerecha() {
        if (armaMostrada == 3)
            armaMostrada = 0;
        else
            armaMostrada = armaMostrada + 1;
        return armaMostrada;
    }

    public int moverArmaVisibleIzquierda() {
        if (armaMostrada == 0)
            armaMostrada = 3;
        else
            armaMostrada = armaMostrada - 1;
        return armaMostrada;
    }

    /**
     * obtiene el arma que se muestra actualmente en el panelArmas
     *
     * @return número del arma mostrada
     */
    public int getArmaMostrada() {
        return armaMostrada;
    }
}
