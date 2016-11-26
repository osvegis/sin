/*
 * Released under the MIT License.
 * Copyright 2016 Oscar Vega-Gisbert.
 */
package dpll;

import java.util.*;

/**
 * Sentencia en forma normal conjuntiva.
 */
public class Sentencia
{
private Set<Clausula> m_clausulas = new LinkedHashSet<>();

/**
 * Construye una sentencia en forma normal conjuntiva.
 * Cada cláusula expresa una disyunción de símbolos.
 * Los símbolos deben estar separados por comas.
 * Para expresar un símbolo negado, se usará !.
 * @param clausulas Cláusulas de la sentencia.
 */
public Sentencia(String...clausulas)
{
    for(String c : clausulas)
    {
        if(!m_clausulas.add(new Clausula(c)))
        {
            throw new RuntimeException(
                "La cláusula ("+ c +") está repetida.");
        }
    }
}

/**
 * Evalúa la sentencia con un modelo.
 * @param modelo Modelo con el que evaluar la cláusula.
 * @return true si la cláusula es cierta con el modelo.
 */
public Boolean evaluar(Modelo modelo)
{
    boolean nulos = false;

    for(Clausula c : m_clausulas)
    {
        Boolean e = c.evaluar(modelo);

        if(e == null)
            nulos = true;
        else if(!e)
            return false; //........................................RETURN
    }

    return nulos ? null : true;
}

/**
 * Obtiene el conjunto de cláusulas.
 * @return Conjunto de cláusulas.
 */
public Set<Clausula> clausulas()
{
    return m_clausulas;
}

/**
 * Busca símbolos puros.
 * @param simbolos Conjunto de símbolos a buscar.
 * @param modelo Modelo con el que buscar los símbolos.
 * @return Símbolos puros con sus valores.
 */
public Map<String,Boolean> simbolosPuros(Set<String> simbolos,
                                         Modelo modelo)
{
    Map<String,Boolean> puros = new HashMap<>();
    Set<String> impuros = new HashSet<>();

    for(Clausula clausula : m_clausulas)
    {
        Boolean e = clausula.evaluar(modelo);

        if(e != null && e)
            continue; //..........................................CONTINUE

        for(String s : clausula.simbolos())
        {
            if(!simbolos.contains(s) || impuros.contains(s))
                continue; //......................................CONTINUE

            Boolean v = clausula.get(s),
                    a = puros.put(s, v);

            if(a != null && !a.equals(v))
            {
                puros.remove(s);
                impuros.add(s);
            }
        }
    }

    return puros;
}

/**
 * Busca cláusulas unitarias.
 * @param modelo Modelo con el que buscar las cláusulas.
 * @return Cláusulas unitarias con su valor de verdad.
 */
public Map<String,Boolean> clausulasUnitarias(Modelo modelo)
{
    Map<String,Boolean> unitarias = new HashMap<>();

    for(Clausula clausula : m_clausulas)
    {
        String u = clausula.unitaria(modelo);

        if(u != null)
            unitarias.put(u, clausula.get(u));
    }

    return unitarias;
}

/**
 * Devuelve una representación de la sentencia como cadena.
 * @return Representación de la sentencia como cadena.
 */
@Override public String toString()
{
    StringBuilder sb = new StringBuilder();

    for(Clausula c : m_clausulas)
    {
        sb.append(",(");
        sb.append(c);
        sb.append(")");
    }

    return sb.length()==0 ? "" : sb.substring(1);
}

public String toString(Modelo m)
{
    Boolean result = evaluar(m);
    
    if(result != null)
        return result.toString();

    StringBuilder sb = new StringBuilder();
    
    for(Clausula c : m_clausulas)
    {
        if(c.evaluar(m) == null)
        {
            sb.append(",(");
            sb.append(c.toString(m));
            sb.append(")");
        }
    }

    return sb.length()==0 ? "" : sb.substring(1);
}

private static void printMap(String mensaje, Map<String,Boolean> map)
{
    System.out.println(mensaje);

    for(Map.Entry<String,Boolean> e : map.entrySet())
    {
        if(!e.getValue())
            System.out.print('!');

        System.out.println(e.getKey());
    }
}

public static void main(String args[])
{
    Sentencia sentencia = new Sentencia("A,!B", "!B,!C", "C,A");

    Set<String> simbolos = new TreeSet<>();
    simbolos.add("A");
    //simbolos.add("B");

    Modelo modelo = new Modelo();
    modelo.set("A", false);
    //modelo.set("B", false);

    Map<String,Boolean> puros = sentencia.simbolosPuros(simbolos, modelo);
    Map<String,Boolean> unitarias = sentencia.clausulasUnitarias(modelo);

    printMap("Símbolos Puros", puros);
    printMap("Cláusulas Unitarias", unitarias);
}

} // Sentencia
