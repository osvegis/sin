/*
 * Released under the MIT License.
 * Copyright 2016 Oscar Vega-Gisbert.
 */
package dpll;

import java.util.*;

/**
 * Disyunción de literales.
 */
public class Clausula
{
private final Map<String,Boolean> m_simbolos = new LinkedHashMap<>();

/**
 * Cláusula formada por una disyunción de literales.
 * <p>Para expresar un literal negado, se usará !.
 * @param expresion Literales separados por comas.
 */
public Clausula(String expresion)
{
    StringTokenizer st = new StringTokenizer(expresion, ",");

    while(st.hasMoreTokens())
    {
        String  nombre   = st.nextToken().trim();
        boolean positivo = true;

        if(nombre.charAt(0) == '!')
        {
            nombre   = nombre.substring(1).trim();
            positivo = false;
        }

        if(m_simbolos.put(nombre, positivo) != null)
        {
            throw new RuntimeException(
                "El literal "+ nombre +" está repetido.");
        }
    }
}

/**
 * Evalúa la cláusula con un modelo.
 * @param m Modelo con el que evaluar la cláusula.
 * @return true si la cláusula es cierta con el modelo.
 */
public Boolean evaluar(Modelo m)
{
    boolean nulos = false;

    for(Map.Entry<String,Boolean> e : m_simbolos.entrySet())
    {
        String  s = e.getKey();
        Boolean v = m.get(s);

        if(v == null)
            nulos = true;
        else if(e.getValue().equals(v))
            return true; //.........................................RETURN
    }

    return nulos ? null : false;
}

/**
 * Comprueba si la cláusula es unitaria.
 * @param modelo Modelo con el que comprobar si la cláusula es unitaria.
 * @return Literal unitario si la cláusula es unitaria.
 */
public String unitaria(Modelo modelo)
{
    String u = null;

    for(Map.Entry<String,Boolean> e : m_simbolos.entrySet())
    {
        String  s = e.getKey();
        Boolean v = e.getValue(),
                m = modelo.get(s);

        if(m == null)
        {
            if(u != null)
                return null; //.....................................RETURN

            u = s;
        }
        else if(m.equals(v))
        {
            // Algún símbolo es cierto en el modelo.
            return null; //.........................................RETURN
        }
    }

    return u;
}

/**
 * Obtiene el conjunto de símbolos que forman la cláusula.
 * @return Conjunto de símbolos que forman la cláusula.
 */
public Set<String> simbolos()
{
    return m_simbolos.keySet();
}

/**
 * Comprueba si el símbolo es positivo.
 * @param simbolo El símbolo debe existir en la cláusula.
 * @return true si el símbolo es positivo.
 */
public boolean get(String simbolo)
{
    return m_simbolos.get(simbolo);
}

/**
 * Compara una cláusula con otra.
 * @param obj Cláusula a comparar.
 * @return true si las dos cláusulas son iguales.
 */
@Override public boolean equals(Object obj)
{
    if(this == obj)
        return true; //.............................................RETURN

    if(obj == null || !(obj instanceof Clausula))
        return false; //............................................RETURN

    return m_simbolos.equals(((Clausula)obj).m_simbolos);
}

/**
 * Calcula el código hash de la cláusula.
 * @return Código hash de la cláusula.
 */
@Override public int hashCode()
{
    return m_simbolos.hashCode();
}

/**
 * Devuelve una representación de la cláusula como cadena.
 * @return Representación de la cláusula como cadena.
 */
@Override public String toString()
{
    StringBuilder sb = new StringBuilder();

    for(Map.Entry<String,Boolean> e : m_simbolos.entrySet())
    {
        sb.append(',');

        if(!e.getValue())
            sb.append('!');

        sb.append(e.getKey());
    }

    return sb.length()==0 ? "" : sb.substring(1);
}

/**
 * Devuelve una representación de la cláusula como cadena
 * aplicando un modelo.
 * @param m Modelo para aplicar la terminación anticipada.
 * @return Representación de la cláusula como cadena.
 */
public String toString(Modelo m)
{
    StringBuilder sb = new StringBuilder();

    for(Map.Entry<String,Boolean> e : m_simbolos.entrySet())
    {
        Boolean value = m.get(e.getKey());

        if(value == null)
        {
            sb.append(',');
        
            if(!e.getValue())
                sb.append('!');

            sb.append(e.getKey());
        }
        else if(value.equals(e.getValue()))
        {
            return "true";
        }
    }

    return sb.length()==0 ? "" : sb.substring(1);
}

} // Clausula
