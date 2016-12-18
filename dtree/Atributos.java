/*
 * Released under the MIT License.
 * Copyright 2016 Oscar Vega-Gisbert.
 */
package dtree;

import java.util.*;

/**
 * Conjunto de atributos.
 */
public class Atributos
{
private final Data[] m_data;

/**
 * Construye un conjunto de atributos.
 * El primer elemento de cada array es el nombre del atributo,
 * y el resto, son sus valores posibles.
 * @param atributos
 */
public Atributos(Object[][] atributos)
{
    m_data = new Data[atributos.length];

    for(int i = 0; i < atributos.length; i++)
        m_data[i] = new Data(atributos[i]);
}

/**
 * Devuelve el número de atributos.
 * @return Número de atributos.
 */
public int size()
{
    return m_data.length;
}

/**
 * Devuelve el número de valores posibles de un atributo.
 * @param atributo Índice del atributo.
 * @return Número de valores del atributo.
 */
public int size(int atributo)
{
    return m_data[atributo].m_valores.length;
}

/**
 * Devuelve el nombre de un atributo.
 * @param atributo Índice del atributo.
 * @return Nombre del atributo.
 */
public String nombre(int atributo)
{
    return m_data[atributo].m_nombre;
}

/**
 * Devuelve el valor i-ésimo de un atributo.
 * @param atributo Índice del atributo.
 * @param i Índice del valor.
 * @return Valor del atributo i-ésimo.
 */
public Object valor(int atributo, int i)
{
    return m_data[atributo].m_valores[i];
}

/**
 * Indica si un atributo puede tener un determinado valor.
 * @param atributo Índice del atributo.
 * @param valor Valor a comprobar si puede ser del atributo.
 * @return {@code true} si el atributo puede tener el valor indicado.
 */
public boolean contains(int atributo, Object valor)
{
    return m_data[atributo].m_set.contains(valor);
}

private static class Data
{
    private final String   m_nombre;
    private final Object[] m_valores;
    private final HashSet<Object> m_set = new HashSet<>();

    private Data(Object[] valores)
    {
        m_nombre  = (String)valores[0];
        m_valores = Arrays.copyOfRange(valores, 1, valores.length);

        for(Object v : m_valores)
        {
            if(!m_set.add(v))
                throw new IllegalArgumentException("Valor repetido: v");
        }
    }
} // Data

} // Atributos
