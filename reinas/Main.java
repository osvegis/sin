/*
 * Released under the MIT License.
 * Copyright 2016 Oscar Vega-Gisbert.
 */
package reinas;

import java.util.*;

/**
 * Problema de las 8 reinas.
 */
public class Main
{
private static int nodosExplorados;

private static void busquedaProfundidad(Estado e, ArrayList<Estado> sol)
{
    nodosExplorados++;

    if(e.esObjetivo())
    {
        sol.add(e);
    }
    else if(e.getNumReinas() < 8)
    {
        for(int i = 0; i < 8; i++)
        {
            Estado s = e.sucesor(i);

            if(s != null)
                busquedaProfundidad(s, sol);
        }
    }
}

public static void main(String args[])
{
    nodosExplorados = 0;
    ArrayList<Estado> soluciones = new ArrayList<>();
    busquedaProfundidad(new Estado(), soluciones);

    for(int i = 0; i < soluciones.size(); i++)
    {
        System.out.println("Solución: "+ (i+1) +"\n");
        System.out.println(soluciones.get(i));
    }

    System.out.println(nodosExplorados +" nodos explorados.");
}

} // Main
