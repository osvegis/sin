/*
 * Released under the MIT License.
 * Copyright 2016 Oscar Vega-Gisbert.
 */
package dtree;

import java.util.*;

public class Atributos
{
private final Data[] m_data;

public Atributos(Object[][] atributos)
{
    m_data = new Data[atributos.length];

    for(int i = 0; i < atributos.length; i++)
        m_data[i] = new Data(atributos[i]);
}

public int size()
{
    return m_data.length;
}

public int size(int atributo)
{
    return m_data[atributo].m_valores.length;
}

public String nombre(int atributo)
{
    return m_data[atributo].m_nombre;
}

public Object valor(int atributo, int i)
{
    return m_data[atributo].m_valores[i];
}

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
