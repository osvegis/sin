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
private final double m_informacion;
private static final double LOG2 = Math.log(2);

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

        if(d.positivo)
            positivos++;
        else
            negativos++;
    }
    
    m_positivos   = positivos;
    m_negativos   = negativos;
    m_informacion = informacion(positivos, negativos);
}

/**
 * Construye un subconjunto con los ejemplos que tengan
 * el valor indicado en un atributo.
 * @param ejemplos Conjunto original.
 * @param atributo Atributo a seleccionar.
 * @param valor    Valor del atributo a seleccionar.
 */
public Ejemplos(Ejemplos ejemplos, int atributo, Object valor)
{
    m_atributos = new Atributos(ejemplos.m_atributos, atributo);
    Data[] data = new Data[ejemplos.size()];
    int    size = 0;

    int positivos = 0,
        negativos = 0;

    for(int e = 0; e < data.length; e++)
    {
        if(valor.equals(ejemplos.valor(e, atributo)))
        {
            Data d = data[size] = new Data(ejemplos.m_data[e], atributo);
            size++;

            if(d.positivo)
                positivos++;
            else
                negativos++;
        }
    }

    m_data = Arrays.copyOfRange(data, 0, size);
    m_positivos   = positivos;
    m_negativos   = negativos;
    m_informacion = informacion(positivos, negativos);
}

/**
 * Devuelve el conjunto de atributos.
 * @return Conjunto de atributos.
 */
public Atributos atributos()
{
    return m_atributos;
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
public boolean valorMayoria()
{
    return m_positivos >= m_negativos;
}

/**
 * Comprueba si todos los ejemplos tienen la misma clasificación.
 * @return {@code true}, {@code false} o {@code null} si todos los
 *      ejemplos son positivos, negativos, o de los dos tipos,
 *      respectivamente.
 */
public Boolean clasificacion()
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
    return m_data[ejemplo].positivo;
}

/**
 * Devuelve el valor de un atributo de un ejemplo.
 * @param ejemplo  Índice del ejemplo.
 * @param atributo Índice del atributo.
 * @return Valor del atributo del ejemplo.
 */
public Object valor(int ejemplo, int atributo)
{
    return m_data[ejemplo].valores[atributo];
}

/**
 * Elige el mejor atributo para separar los ejemplos.
 * @return Índice del mejor atributo.
 */
public int elegirAtributo()
{
    int    size     = m_atributos.size(),
           atributo = -1;
    double ganancia = 0;
    
    for(int a = 0; a < size; a++)
    {
        double g = ganancia(a);

        if(atributo == -1 || ganancia < g)
        {
            atributo = a;
            ganancia = g;
        }
    }

    return atributo;
}

private double informacion(int positivos, int negativos)
{
    double inf   = 0,
           total = positivos + negativos;

    if(positivos > 0)
    {
        double pPos = positivos / total;
        inf -= pPos * Math.log(pPos) / LOG2;
    }

    if(negativos > 0)
    {
        double pNeg = negativos / total;
        inf -= pNeg * Math.log(pNeg) / LOG2;
    }

    return inf;
}

public double ganancia(int atributo)
{
    return m_informacion - resto(atributo);
}

private double resto(int atributo)
{
    double resto = 0;
    
    int numValores  = m_atributos.size(atributo),
        numEjemplos = m_data.length;

    for(int i = 0; i < numValores; i++)
    {
        Object vi = m_atributos.valor(atributo, i);
        int    pi = 0,
               ni = 0;

        for(int e = 0; e < numEjemplos; e++)
        {
            if(vi.equals(valor(e, atributo)))
            {
                if(positivo(e))
                    pi++;
                else
                    ni++;
            }
        }

        double ti = pi + ni;
        resto += (ti / numEjemplos) * informacion(pi, ni);
    }

    return resto;
}

private class Data
{
    private final boolean  positivo;
    private final Object[] valores;

    private Data(Object[] posValores)
    {
        positivo = (Boolean)posValores[0];
        valores  = Arrays.copyOfRange(posValores, 1, posValores.length);

        if(m_atributos.size() != valores.length)
        {
            throw new IllegalArgumentException(
                "El número de atributos no coincide con el de valores.");
        }

        for(int i = 0; i < valores.length; i++)
        {
            if(!m_atributos.contains(i, valores[i]))
            {
                throw new IllegalArgumentException(
                    "El atributo "+ m_atributos.nombre(i) +
                    " no admite el valor "+ valores[i] +".");
            }
        }
    }

    private Data(Data data, int atribExcl)
    {
        positivo = data.positivo;
        valores  = new Object[data.valores.length - 1];
        System.arraycopy(data.valores, 0, valores, 0, atribExcl);

        System.arraycopy(data.valores, atribExcl + 1,
                         valores, atribExcl, valores.length - atribExcl);
    }
} // Data

} // Ejemplos
