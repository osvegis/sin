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
 * @param atributos Array de arrays de atributos.
 */
public Atributos(Object[][] atributos)
{
    HashSet<String> nombre = new HashSet<>();
    m_data = new Data[atributos.length];

    for(int i = 0; i < atributos.length; i++)
    {
        Data d = m_data[i] = new Data(atributos[i]);
        
        if(!nombre.add(d.nombre))
        {
            throw new IllegalArgumentException(
                "El atributo '"+ d.nombre +"' está repetido.");
        }
    }
}

/**
 * Construye un conjunto de atributos a partir de otro
 * sin uno de sus elementos.
 * @param atributos Conjunto original de atributos.
 * @param iExcluir  Índice del atributo a excluir.
 */
public Atributos(Atributos atributos, int iExcluir)
{
    m_data = new Data[atributos.m_data.length - 1];
    System.arraycopy(atributos.m_data, 0, m_data, 0, iExcluir);

    System.arraycopy(atributos.m_data, iExcluir + 1,
                     m_data, iExcluir, m_data.length - iExcluir);
}

/**
 * Indica si el conjunto de atributos está vacío.
 * @return {@code true} si el conjunto de atributos está vacío.
 */
public boolean isEmpty()
{
    return m_data.length == 0;
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
    return m_data[atributo].valores.length;
}

/**
 * Devuelve el nombre de un atributo.
 * @param atributo Índice del atributo.
 * @return Nombre del atributo.
 */
public String nombre(int atributo)
{
    return m_data[atributo].nombre;
}

/**
 * Devuelve el valor i-ésimo de un atributo.
 * @param atributo Índice del atributo.
 * @param i Índice del valor.
 * @return Valor del atributo i-ésimo.
 */
public Object valor(int atributo, int i)
{
    return m_data[atributo].valores[i];
}

/**
 * Indica si un atributo puede tener un determinado valor.
 * @param atributo Índice del atributo.
 * @param valor    Valor a comprobar si puede ser del atributo.
 * @return {@code true} si el atributo puede tener el valor indicado.
 */
public boolean contains(int atributo, Object valor)
{
    return m_data[atributo].set.contains(valor);
}

private static class Data
{
    private final String   nombre;
    private final Object[] valores;
    private final HashSet<Object> set = new HashSet<>();

    private Data(Object[] nomValores)
    {
        nombre  = (String)nomValores[0];
        valores = Arrays.copyOfRange(nomValores, 1, nomValores.length);

        for(Object v : valores)
        {
            if(!set.add(v))
                throw new IllegalArgumentException("Valor repetido: v");
        }
    }
} // Data

} // Atributos
