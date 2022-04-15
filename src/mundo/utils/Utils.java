package mundo.utils;

import mundo.NombreInvalidoException;

public class Utils {

    /**
     * Verifica que el nombre pasado por parámetro sea completamente alfabético
     * @param nombrePlayer
     * @throws NombreInvalidoException
     */
    public void verificarNombre(String nombrePlayer) throws NombreInvalidoException{
        for (int i = 0; i < nombrePlayer.length(); i++) {
            char caracter = nombrePlayer.charAt(i);
            if((caracter > 90 && caracter < 97) || caracter < 65 || caracter > 122)
                throw new NombreInvalidoException(caracter);
        }
    }
}
