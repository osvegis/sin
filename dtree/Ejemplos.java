/*
 * Released under the MIT License.
 * Copyright 2016 Oscar Vega-Gisbert.
 */
package dtree;

import java.util.*;

public class Ejemplos
{
private final Data[] m_data;

public Ejemplos(Atributos atributos, Object[][] ejemplos)
{
    m_data = new Data[ejemplos.length];

    for(int i = 0; i < ejemplos.length; i++)
        m_data[i] = new Data(atributos, ejemplos[i]);
}

public boolean isEmpty()
{
    return m_data.length == 0;
}

public int size()
{
    return m_data.length;
}

/**
 * Comprueba si todos los elementos son positivos o negativos.
 * @return {@code true}, {@code false} o {@code null} si todos los
 *      ejemplos son positivos, negativos, o de los dos tipos,
 *      respectivamente.
 */
public Boolean positivos()
{
    boolean positivo = positivo(0);

    for(int e = 1; e < m_data.length; e++)
    {
        if(positivo != positivo(e))
            return null;
    }

    return positivo;
}

public boolean positivo(int ejemplo)
{
    return m_data[ejemplo].m_positivo;
}

public Object valor(int ejemplo, int atributo)
{
    return m_data[ejemplo].m_valores[atributo];
}

private static class Data
{
    private final boolean m_positivo;
    private final Object[] m_valores;

    private Data(Atributos atributos, Object[] valores)
    {
        m_positivo = (Boolean)valores[0];
        m_valores = Arrays.copyOfRange(valores, 1, valores.length);

        if(atributos.size() != m_valores.length)
        {
            throw new IllegalArgumentException(
                "El número de atributos no coincide con el de valores.");
        }

        for(int i = 0; i < m_valores.length; i++)
        {
            if(!atributos.contains(i, m_valores[i]))
            {
                throw new IllegalArgumentException(
                    "El atributo "+ atributos.getNombre(i) +
                    " no admite el valor "+ m_valores[i] +".");
            }
        }
    }
} // Data

} // Ejemplos
