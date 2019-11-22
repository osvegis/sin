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
private final Map<String,Boolean> m_simbolos = new TreeMap<>();

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
    if(m_simbolos.put(simbolo, valor) != null)
    {
        throw new IllegalArgumentException(
            "El símbolo "+ simbolo +" ya existe en el modelo.");
    }
}

/**
 * Añade al modelo los símbolos y los valores del map.
 * @param simbolos Símbolos con sus valores a añadir al modelo.
 */
public void set(Map<String,Boolean> simbolos)
{
    for(Map.Entry<String,Boolean> e : simbolos.entrySet())
        set(e.getKey(), e.getValue());
}

/**
 * Elimina un símbolo del modelo comprobando que tiene el valor indicado.
 * @param simbolo Símbolo a eliminar.
 * @param valor Valor del símbolo a eliminar.
 */
public void remove(String simbolo, boolean valor)
{
    Boolean v = m_simbolos.remove(simbolo);

    if(v == null)
    {
        throw new IllegalArgumentException(
            "El símbolo "+ simbolo +" no existe en el modelo.");
    }

    if(v != valor)
    {
        throw new IllegalArgumentException(
            "El símbolo "+ simbolo +" no tenía el valor: "+ valor);
    }
}

/**
 * Elimina un conjunto de símbolos comprobando sus valores.
 * @param simbolos Conjunto de símbolos a eliminar.
 */
public void remove(Map<String,Boolean> simbolos)
{
    for(Map.Entry<String,Boolean> e : simbolos.entrySet())
        remove(e.getKey(), e.getValue());
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
