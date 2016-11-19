/*
 * Released under the MIT License.
 * Copyright 2016 Oscar Vega-Gisbert.
 */
package ehd;

import java.util.*;

/**
 * Base de conocimiento con la implementación de un algoritmo de
 * encadenamiento hacia delante en lógica proposicional.
 */
public class BaseConocimiento
{
private TreeSet<String> m_hechos = new TreeSet<>();
private TreeSet<Implicacion> m_implicaciones = new TreeSet<>();
private TreeMap<String,TreeSet<Implicacion>> m_simbolos = new TreeMap<>();

/**
 * Añade un hecho a la base de conocimiento.
 * @param hecho Hecho a añadir a la base de conocimiento.
 */
public void decir(String hecho)
{
    if(!m_hechos.add(hecho.trim()))
        throw new AssertionError();

    if(!m_simbolos.containsKey(hecho))
        m_simbolos.put(hecho, new TreeSet<>());
}

/**
 * Añade una cláusula de Horn a la base de conocimiento.
 * @param premisa Premisa de la implicación. Debe ser una secuencia de
 *      proposiciones separadas por comas. Las comas equivalen a conjunciones.
 * @param conclusion Único literal positivo de la cláusula de Horn.
 */
public void decir(String premisa, String conclusion)
{
    Implicacion r = new Implicacion(premisa, conclusion);

    if(!m_implicaciones.add(r))
        throw new AssertionError();

    int n = r.getNumSimbolosPremisa();

    for(int i = 0; i < n; i++)
    {
        TreeSet<Implicacion> set = m_simbolos.get(r.getSimboloPremisa(i));

        if(set == null)
        {
            set = new TreeSet<>();
            m_simbolos.put(r.getSimboloPremisa(i), set);
        }

        set.add(r);
    }
}

/**
 * Algoritmo de encadenamiento hacia delante.
 * @param simbolo Proposición que se desea averiguar si se
 *                deduce de la base de conocimiento.
 * @return true si el símbolo proposicional se deduce de la
 *         base de conocimiento.
 */
public boolean implicacionEHD(String simbolo)
{
    TreeMap<Implicacion,Integer> cuenta = newCuenta();

    TreeSet<String> inferido = new TreeSet<>(),
                    agenda   = newAgenda();

    while(!agenda.isEmpty())
    {
        String p = agenda.pollFirst();

        if(inferido.add(p))
        {
            System.out.println(p);

            for(Implicacion r : m_simbolos.get(p))
            {
                int c = reducirCuenta(cuenta, r);
                System.out.println("  "+ c +"  :  "+ r);

                if(c == 0)
                {
                    if(r.getConclusion().equals(simbolo))
                        return true; //.............................RETURN

                    agenda.add(r.getConclusion());
                }
            }
        }
    }

    return false;
}

private TreeSet<String> newAgenda()
{
    TreeSet<String> agenda = new TreeSet<>();
    agenda.addAll(m_hechos);
    return agenda;
}

private TreeMap<Implicacion,Integer> newCuenta()
{
    TreeMap<Implicacion,Integer> m = new TreeMap<>();

    for(Implicacion r : m_implicaciones)
        m.put(r, r.getNumSimbolosPremisa());

    return m;
}

private int reducirCuenta(TreeMap<Implicacion,Integer> m, Implicacion r)
{
    int c = m.get(r) - 1;
    assert c >= 0;
    m.put(r, c);
    return c;
}

} // BaseConocimiento
