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

    TreeSet<String> simResto = new TreeSet<>(simbolos);
    Modelo modResto = new Modelo(modelo);

    Map<String,Boolean> puros = sentencia.simbolosPuros(simbolos, modelo);

    if(!puros.isEmpty())
    {
        System.out.println("    Puros: "+ puros);
        return run(sentencia, modelo, modResto, simResto, puros);
    }

    Map<String,Boolean> unitarias = sentencia.clausulasUnitarias(modelo);

    if(!unitarias.isEmpty())
    {
        System.out.println("Unitarias: "+ unitarias);
        return run(sentencia, modelo, modResto, simResto, unitarias);
    }

    String primero = simResto.pollFirst();

    return run(sentencia, modelo, modResto, simResto, primero, true) ||
           run(sentencia, modelo, modResto, simResto, primero, false);
}

private static boolean run(
        Sentencia sentencia, Modelo modelo, Modelo modResto,
        TreeSet<String> simResto, Map<String,Boolean> heuristica)
{
    simResto.removeAll(heuristica.keySet());
    modResto.set(heuristica);

    if(run(sentencia, modResto, simResto))
    {
        modelo.set(modResto);
        return true;
    }
    else
    {
        return false;
    }
}

private static boolean run(
        Sentencia sentencia, Modelo modelo, Modelo modResto,
        TreeSet<String> simResto, String primero, boolean valor)
{
    modResto.set(primero, valor);
    System.out.println(" Probamos: "+ primero +"="+ valor);

    if(run(sentencia, modResto, simResto))
    {
        modelo.set(modResto);
        return true;
    }
    else
    {
        return false;
    }
}

} // SatisfacibleDPLL
