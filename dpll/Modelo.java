/*
 * Released under the MIT License.
 * Copyright 2016 Oscar Vega-Gisbert.
 */
package dpll;

import java.util.*;

/**
 * Un modelo consiste en la asignación de valores de verdad a literales.
 */
public class Modelo
{
private Map<String,Boolean> m_simbolos = new TreeMap<>();

/**
 * Construye un modelo vacío.
 */
public Modelo()
{
}

/**
 * Construye un modelo idéntico a otro.
 * @param m Modelo a copiar.
 */
public Modelo(Modelo m)
{
    m_simbolos.putAll(m.m_simbolos);
}

/**
 * Copia un modelo en este modelo.
 * @param m Modelo a copiar.
 */
public void set(Modelo m)
{
    m_simbolos.clear();
    m_simbolos.putAll(m.m_simbolos);
}

/**
 * Obtiene el valor de un símbolo.
 * @param simbolo Símbolo del que se desea obtener su valor.
 * @return Valor del símbolo. Si el símbolo no existe en el modelo,
 *         devuelve null.
 */
public Boolean get(String simbolo)
{
    return m_simbolos.get(simbolo);
}

/**
 * Asigna un valor a un símbolo.
 * @param simbolo Símbolo al que asignar el valor.
 * @param valor Valor a asignar al símbolo.
 */
public void set(String simbolo, boolean valor)
{
    m_simbolos.put(simbolo, valor);
}

/**
 * Añade al modelo los símbolos y los valores del map.
 * @param simbolos Símbolos con sus valores a añadir al modelo.
 */
public void set(Map<String,Boolean> simbolos)
{
    m_simbolos.putAll(simbolos);
}

/**
 * Devuelve una representación del modelo como cadena.
 * @return Representación del modelo como cadena.
 */
@Override public String toString()
{
    StringBuilder sb = new StringBuilder();

    for(Map.Entry<String,Boolean> e : m_simbolos.entrySet())
    {
        sb.append(", ");
        sb.append(e.getKey());
        sb.append('=');
        sb.append(e.getValue());
    }

    return sb.length()==0 ? "" : sb.substring(2);
}

} // Modelo
