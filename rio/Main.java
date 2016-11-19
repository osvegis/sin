/*
 * Released under the MIT License.
 * Copyright 2016 Oscar Vega-Gisbert.
 */
package rio;

import java.util.*;

public class Main
{
private static Estado anchura()
{
    HashSet<Estado> repetidos = new HashSet<>();
    LinkedList<Estado> abiertos = new LinkedList<>();
    abiertos.add(new Estado());

    while(!abiertos.isEmpty())
    {
        Estado estado = abiertos.removeFirst();

        if(estado.esObjetivo())
            return estado;

        for(Operador o : Operador.values())
        {
            Estado sucesor = o.apply(estado);

            if(sucesor != null && repetidos.add(sucesor))
                abiertos.add(sucesor);
        }
    }

    return null;
}

public static void main(String args[])
{
    anchura().printSolucion();
}

} // Main
