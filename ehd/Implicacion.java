/*
 * Released under the MIT License.
 * Copyright 2016 Oscar Vega-Gisbert.
 */
package ehd;

import java.util.*;

/**
 * Cláusula de Horn para el algoritmo de encadenamiento hacia delante.
 */
public class Implicacion implements Comparable<Implicacion>
{
private String m_premisa[], m_conclusion;

/**
 * Construye una cláusula de Horn.
 * @param premisa Premisa de la implicación. Debe ser una secuencia de
 *      proposiciones separadas por comas. Las comas equivalen a conjunciones.
 * @param conclusion Único literal positivo de la cláusula de Horn.
 */
public Implicacion(String premisa, String conclusion)
{
    StringTokenizer st = new StringTokenizer(premisa, ",");
    m_premisa    = new String[st.countTokens()];
    m_conclusion = conclusion.trim();

    HashSet<String> simbolos = new HashSet<>();
    simbolos.add(conclusion);

    for(int i = 0; i < m_premisa.length; i++)
    {
        String s = st.nextToken().trim();

        if(!simbolos.add(s))
            throw new AssertionError();

        m_premisa[i] = s;
    }

    if(st.hasMoreTokens())
        throw new AssertionError();

    Arrays.sort(m_premisa);
}

/**
 * Devuelve el número de símbolos proposicionales de la premisa.
 * @return Número de símbolos de la premisa.
 */
public int getNumSimbolosPremisa()
{
    return m_premisa.length;
}

/**
 * Devuelve el símbolo i-ésimo de la premisa.
 * @param i Índice del símbolo a devolver.
 * @return Símbolo i-ésimo de la premisa.
 */
public String getSimboloPremisa(int i)
{
    return m_premisa[i];
}

/**
 * Devuelve el literal positivo de la cláusula de Horn.
 * @return Literal positivo de la cláusula de Horn.
 */
public String getConclusion()
{
    return m_conclusion;
}

/**
 * Establece un orden entre las cláusulas para poderlas almacenar
 * en estructuras de datos como TreeMap o TreeSet.
 * @param r Cláusula de Horn con la que realizar la comparación.
 * @return Resultado de comparar esta cláusula con otra.
 */
@Override public int compareTo(Implicacion r)
{
    int n = Math.min(m_premisa.length, r.m_premisa.length);

    for(int i = 0; i < n; i++)
    {
        int c = m_premisa[i].compareTo(r.m_premisa[i]);

        if(c != 0)
            return c; //............................................RETURN
    }

    int c = m_premisa.length - r.m_premisa.length;

    if(c != 0)
        return c; //................................................RETURN

    return m_conclusion.compareTo(r.m_conclusion);
}

/**
 * Devuelve una representación de la cláusula como cadena.
 * @return Representación de la cláusula como cadena.
 */
@Override public String toString()
{
    StringBuilder sb = new StringBuilder();

    for(String s : m_premisa)
    {
        if(sb.length() > 0)
            sb.append(',');

        sb.append(s);
    }

    sb.append(" => ");
    sb.append(m_conclusion);
    return sb.toString();
}

} // Clausula
