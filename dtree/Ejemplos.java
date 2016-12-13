/*
 * Released under the MIT License.
 * Copyright 2016 Oscar Vega-Gisbert.
 */
package dtree;

import java.util.*;

/**
 * Conjunto de ejemplos.
 */
public class Ejemplos
{
private final Atributos m_atributos;
private final Data[] m_data;
private final int m_positivos, m_negativos;

/**
 * Construye un conjunto de ejemplos a partir de los atributos y de un
 * array bidimensional con los valores de los atributos.
 * @param atributos Conjunto de atributos.
 * @param ejemplos  Valores de los ejemplos. La primera dimensión se
 *      se corresponde con los ejemplos, y la segunda con los atributos
 *      de cada ejemplo.
 */
public Ejemplos(Atributos atributos, Object[][] ejemplos)
{
    m_atributos = atributos;
    m_data = new Data[ejemplos.length];

    int positivos = 0,
        negativos = 0;
    
    for(int i = 0; i < ejemplos.length; i++)
    {
        Data d = m_data[i] = new Data(ejemplos[i]);

        if(d.m_positivo)
            positivos++;
        else
            negativos++;
    }
    
    m_positivos = positivos;
    m_negativos = negativos;
}

/**
 * Indica si el conjunto de ejemplos está vacío.
 * @return {@code true} si el conjunto está vacío.
 */
public boolean isEmpty()
{
    return m_data.length == 0;
}

/**
 * Devuelve el número de ejemplos.
 * @return Número de ejemplos.
 */
public int size()
{
    return m_data.length;
}

/**
 * Devuelve el valor de la mayoría.
 * @return Valor de la mayoría.
 */
public boolean mayoria()
{
    return m_positivos >= m_negativos;
}

/**
 * Comprueba si todos los elementos son positivos o negativos.
 * @return {@code true}, {@code false} o {@code null} si todos los
 *      ejemplos son positivos, negativos, o de los dos tipos,
 *      respectivamente.
 */
public Boolean positivos()
{
    if(m_negativos == 0)
        return true;
    else if(m_positivos == 0)
        return false;
    else
        return null;
}

/**
 * Indica si un ejemplo es positivo.
 * @param ejemplo Índice del ejemplo.
 * @return {@code true} si el ejemplo es positivo.
 */
public boolean positivo(int ejemplo)
{
    return m_data[ejemplo].m_positivo;
}

/**
 * Devuelve el valor de un atributo de un ejemplo.
 * @param ejemplo  Índice del ejemplo.
 * @param atributo Índice del atributo.
 * @return Valor del atributo del ejemplo.
 */
public Object valor(int ejemplo, int atributo)
{
    return m_data[ejemplo].m_valores[atributo];
}

private class Data
{
    private final boolean m_positivo;
    private final Object[] m_valores;

    private Data(Object[] valores)
    {
        m_positivo = (Boolean)valores[0];
        m_valores = Arrays.copyOfRange(valores, 1, valores.length);

        if(m_atributos.size() != m_valores.length)
        {
            throw new IllegalArgumentException(
                "El número de atributos no coincide con el de valores.");
        }

        for(int i = 0; i < m_valores.length; i++)
        {
            if(!m_atributos.contains(i, m_valores[i]))
            {
                throw new IllegalArgumentException(
                    "El atributo "+ m_atributos.getNombre(i) +
                    " no admite el valor "+ m_valores[i] +".");
            }
        }
    }
} // Data

} // Ejemplos
