/*
 * Released under the MIT License.
 * Copyright 2016 Oscar Vega-Gisbert.
 */
package dtree;

import java.util.*;

public class ArbolDecision
{
private final Ejemplos m_ejemplos;
private final Nodo m_raiz;

public ArbolDecision(Ejemplos ejemplos)
{
    if(ejemplos.isEmpty())
        throw new IllegalArgumentException("No hay ejemplos.");

    m_ejemplos = ejemplos;
    // En la primera llamada no importa el valor 'porDefecto'
    // porque el conjunto de ejemplos no estará vacío.
    m_raiz = aprender(ejemplos, true);
}

@Override public String toString()
{
    return m_raiz.toString();
}

private Nodo aprender(Ejemplos ejemplos, boolean porDefecto)
{
    if(ejemplos.isEmpty())
        return new Nodo(porDefecto); //.............................RETURN

    Boolean clasificacion = ejemplos.clasificacion();

    if(clasificacion != null)
    {
        // Todos los ejemplos tienen la misma clasificación.
        return new Nodo(clasificacion); //..........................RETURN
    }

    Atributos atributos = ejemplos.atributos();
    boolean   mayoria   = ejemplos.valorMayoria();

    if(atributos.isEmpty())
        return new Nodo(mayoria); //................................RETURN

    int  mejor = ejemplos.elegirAtributo();
    Nodo arbol = new Nodo(atributos.nombre(mejor));
    int mejorSize = atributos.size(mejor);
    
    for(int i = 0; i < mejorSize; i++)
    {
        Object   vi = atributos.valor(mejor, i);
        Ejemplos ei = new Ejemplos(ejemplos, mejor, vi);
        arbol.add(vi, aprender(ei, mayoria));
    }

    return arbol;
}

private class Nodo
{
    // Si es un nodo hoja, 'nombre' será de tipo 'Boolean' e indicará
    // el resultado de la clasificación.
    // Si no es un nodo hoja, 'nombre' será el nombre de un atributo.
    private Object nombre;
    private HashMap<Object,Nodo> hijos;

    private Nodo(boolean resultado)
    {
        nombre = resultado;
    }

    private Nodo(String atributo)
    {
        nombre = atributo;
    }

    private void add(Object valor, Nodo nodo)
    {
        if(hijos == null)
            hijos = new HashMap<>();

        if(hijos.put(valor, nodo) != null)
            throw new AssertionError();
    }

    @Override public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(" Nodo: ");
        sb.append(nombre);

        if(hijos != null)
        {
            sb.append("\nHijos: ");
            String coma = ", ";

            for(Object k : hijos.keySet())
            {
                sb.append(k);
                sb.append(coma);
            }

            sb.setLength(sb.length() - coma.length());

            for(Map.Entry<Object,Nodo> e : hijos.entrySet())
            {
                sb.append("\n\n");
                sb.append("Valor: ");
                sb.append(nombre);
                sb.append(" / ");
                sb.append(e.getKey());
                sb.append('\n');
                sb.append(e.getValue());
            }
        }

        return sb.toString();
    }
}

} // ArbolDecision
