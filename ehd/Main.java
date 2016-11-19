/*
 * Released under the MIT License.
 * Copyright 2016 Oscar Vega-Gisbert.
 */
package ehd;

public class Main
{
public static void main(String args[])
{
    BaseConocimiento bc = new BaseConocimiento();

    bc.decir("P", "Q");
    bc.decir("L,M", "P");
    bc.decir("B,L", "M");
    bc.decir("A,P", "L");
    bc.decir("A,B", "L");
    bc.decir("A");
    bc.decir("B");

    System.out.println(bc.implicacionEHD("Q"));
}

} // Main
