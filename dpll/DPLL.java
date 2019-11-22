/*
 * Released under the MIT License.
 * Copyright 2016 Oscar Vega-Gisbert.
 */
package dpll;

import java.util.*;

/**
 * Algoritmo Davis-Putnam-Logemann-Loveland para comprobar
 * la satisfacibilidad de una sentencia de lógica proposicional,
 * expresada en forma normal conjuntiva (FNC).
 */
public class DPLL
{
/**
 * Comprueba si una sentencia es satisfacible.
 * @param sentencia Sentencia en forma normal conjuntiva.
 * @return true si la sentencia es satisfacible.
 */
public static boolean run(Sentencia sentencia)
{
    Modelo modelo = new Modelo();
    return run(sentencia, modelo);
}

/**
 * Comprueba si una sentencia es satisfacible.
 * @param sentencia Sentencia en forma normal conjuntiva.
 * @param modelo Modelo vacío. Si la sentencia es satisfacible será un
 * modelo válido.
 * @return true si la sentencfia es satisfacible.
 */
public static boolean run(Sentencia sentencia, Modelo modelo)
{
    TreeSet<String> simbolos = new TreeSet<>();

    for(Clausula c : sentencia.clausulas())
        simbolos.addAll(c.simbolos());

    return run(sentencia, modelo, simbolos);
}

private static boolean run(Sentencia sentencia, Modelo modelo,
                           TreeSet<String> simbolos)
{
    System.out.println("\n Simbolos: "+ simbolos);
    System.out.println(  "   Modelo: "+ modelo);
    System.out.println(  "Sentencia: "+ sentencia.toString(modelo));
    Boolean e = sentencia.evaluar(modelo);

    if(e != null)
    {
        // Todas las cláusulas son ciertas (e==true)
        // o alguna cláusula es falsa (e==false).
        return e;
    }

    Map<String,Boolean> puros = sentencia.simbolosPuros(simbolos, modelo);

    if(!puros.isEmpty())
    {
        System.out.println("    Puros: "+ puros);
        return run(sentencia, modelo, simbolos, puros);
    }

    Map<String,Boolean> unitarias = sentencia.clausulasUnitarias(modelo);

    if(!unitarias.isEmpty())
    {
        System.out.println("Unitarias: "+ unitarias);
        return run(sentencia, modelo, simbolos, unitarias);
    }

    String primero = simbolos.first();

    return run(sentencia, modelo, simbolos, primero, true) ||
           run(sentencia, modelo, simbolos, primero, false);
}

private static boolean run(
        Sentencia sentencia, Modelo modelo,
        TreeSet<String> simbolos, Map<String,Boolean> heuristica)
{
    modelo.set(heuristica);
    simbolos.removeAll(heuristica.keySet());

    if(run(sentencia, modelo, simbolos))
    {
        return true;
    }
    else
    {
        modelo.remove(heuristica);
        simbolos.addAll(heuristica.keySet());
        return false;
    }
}

private static boolean run(
        Sentencia sentencia, Modelo modelo,
        TreeSet<String> simbolos, String primero, boolean valor)
{
    modelo.set(primero, valor);
    simbolos.remove(primero);
    System.out.println(" Probamos: "+ primero +"="+ valor);

    if(run(sentencia, modelo, simbolos))
    {
        return true;
    }
    else
    {
        modelo.remove(primero, valor);
        simbolos.add(primero);
        return false;
    }
}

} // SatisfacibleDPLL
