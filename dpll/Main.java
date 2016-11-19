/*
 * Released under the MIT License.
 * Copyright 2016 Oscar Vega-Gisbert.
 */
package dpll;

public class Main
{
public static void main(String args[])
{
    /*
    // Reglas de la página 28 (mundo de wumpus)
    Sentencia sentencia = new Sentencia(
        "!H11",
        "!B11,H12,H21", "!H12,B11", "!H21,B11",
        "!B21,H11,H22,H31", "!H11,B21", "!H22,B21", "!H31,B21",
        "!B11",
        "B21");
    */

    /*
    // Reglas del ejercicio 6.
    Sentencia sentencia = new Sentencia(
        "!A,B,E", "!B,A", "!E,A",
        "!E,D",
        "!C,!F,!B",
        "!E,B",
        "!B,F",
        "!B,C");
    */

    //Sentencia sentencia = new Sentencia(
    //    "!A,!D", "A,B,C", "A,!B,!C", "B,!C,D", "!B,C,D");

    Sentencia sentencia = new Sentencia(
        "A,!B,!C", "!A,B,C", "!A,D", "!B,C,!D", "B,!C,!D");

    System.out.println("Sentencia: "+ sentencia);
    Modelo modelo = new Modelo();
    boolean satisfacible = DPLL.run(sentencia, modelo);
    System.out.println("\nSatisfacible: "+ satisfacible);

    if(satisfacible)
        System.out.println("      Modelo: "+ modelo);
}

} // Main
