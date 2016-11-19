/*
 * Released under the MIT License.
 * Copyright 2016 Oscar Vega-Gisbert.
 */
package reinas;

/**
 * Estado de las 8 reinas.
 */
public class Estado
{
private int reinas[];

/**
 * Constructor del estado inicial.
 */
public Estado()
{
    reinas = new int[0];
}

private Estado(Estado e)
{
    reinas = new int[e.reinas.length + 1];
    System.arraycopy(e.reinas, 0, reinas, 0, e.reinas.length);
}

/**
 * Indica cuántas reinas hay en el tablero.
 * @return Número de reinas en el tablero.
 */
public int getNumReinas()
{
    return reinas.length;
}

/**
 * Crea el estado sucesor i-ésimo, que consiste en añadir una reina
 * en la fila i de la siguiente columna.
 * @param i Fila donde se añadirá la reina.
 * @return Estado sucesor.
 */
public Estado sucesor(int i)
{
    assert i >= 0 && i < 8;
    int j = reinas.length;
    assert j >= 0 && j < 8;
    Estado e = new Estado(this);
    e.reinas[j] = i;
    return e.esValido() ? e : null;
}

/**
 * Indica si en la fila {@code i}, columna {@code j} hay una reina.
 * Si las coordenadas están fuera del tablero, devuelve false.
 * @param i Fila.
 * @param j Columna.
 * @return true si hay una reina en la casilla indicada.
 */
public boolean getReina(int i, int j)
{
    if(i < 0 || i > 7 || j < 0 || j > 7 || j >= reinas.length)
        return false;
    else
        return i == reinas[j];
}

/**
 * Indica si el estado es objetivo, es decir, si hay 8 reinas y
 * no se matan entre ellas.
 * @return true si es un estado objetivo.
 */
public boolean esObjetivo()
{
    return reinas.length == 8 && esValido();
}

/**
 * Indica si en el tablero no se mata ninguna reina.
 * @return true si no se mata ninguna reina.
 */
public boolean esValido()
{
    for(int i = 0; i < 8; i++)
    {
        boolean horizontal = false,
                diagAsc1   = false,
                diagAsc2   = false,
                diagDes1   = false,
                diagDes2   = false;

        for(int j = 0; j < 8; j++)
        {
            if(getReina(i, j))
            {
                if(horizontal)
                    return false; //................................RETURN

                horizontal = true;
            }

            if(getReina(i+j, j))
            {
                if(diagDes1)
                    return false; //................................RETURN

                diagDes1 = true;
            }

            if(getReina(j, i+j))
            {
                if(diagDes2)
                    return false; //................................RETURN

                diagDes2 = true;
            }

            if(getReina(i-j, j))
            {
                if(diagAsc1)
                    return false; //................................RETURN

                diagAsc1 = true;
            }

            if(getReina(7-i+j, 7-j))
            {
                if(diagAsc2)
                    return false; //................................RETURN

                diagAsc2 = true;
            }
        }
    }

    return true; //.................................................RETURN
}

/**
 * Convierte el estado actual a una cadena que representa el tablero.
 * @return Tablero representado como una cadena.
 */
@Override public String toString()
{
    StringBuilder sb = new StringBuilder();

    for(int i = 0; i < 8; i++)
    {
        for(int j = 0; j < 8; j++)
            sb.append(getReina(i, j) ? " X" : " ·");

        sb.append('\n');
    }

    return sb.toString();
}

} // Estado
