/*
 * Released under the MIT License.
 * Copyright 2016 Oscar Vega-Gisbert.
 */
package jarras;

public class Estado
{
private Estado padre;
private String accion;

private int jarraA, // capA litros de capacidad
            jarraB; // capB litros de capacidad

/*
// Problema original de las jarras
private static final int capA = 3,
                         capB = 4,
                         objA = 0,
                         objB = 2;
*/

// Jungla de cristal
private static final int capA = 3,
                         capB = 5,
                         objA = 0,
                         objB = 4;

public Estado()
{
    // Las dos jarras vacías.
    accion = "inicial";
}

public Estado(Estado estado, String nombreAccion, int a, int b)
{
    assert a >= 0 && a <= capA && b >= 0 && b <= capB;
    padre  = estado;
    accion = nombreAccion;
    jarraA = a;
    jarraB = b;
}

public int getA()
{
    return jarraA;
}

public int getB()
{
    return jarraB;
}

public boolean esObjetivo()
{
    return jarraA == objA && jarraB == objB;
}

public Estado llenarA()
{
    return jarraA == capA ? null : new Estado(this, "llenar A", capA, jarraB);
}

public Estado llenarB()
{
    return jarraB == capB ? null : new Estado(this, "llenar B", jarraA, capB);
}

public Estado vaciarA()
{
    return jarraA == 0 ? null : new Estado(this, "vaciar A", 0, jarraB);
}

public Estado vaciarB()
{
    return jarraB == 0 ? null : new Estado(this, "vaciar B", jarraA, 0);
}

public Estado traspAB()
{
    if(jarraA == 0 || jarraB == capB)
        return null;

    int r = capB - jarraB,
        a = Math.max(0, jarraA - r),
        b = Math.min(capB, jarraA + jarraB);

    return new Estado(this, "traspasar de A a B", a, b);
}

public Estado traspBA()
{
    if(jarraA == capA || jarraB == 0)
        return null;

    int r = capA - jarraA,
        a = Math.min(capA, jarraA + jarraB),
        b = Math.max(0, jarraB - r);

    return new Estado(this, "traspasar de B a A", a, b);
}

public void printSolucion()
{
    if(padre == null)
        System.out.printf("%18s  :  A  -  B\n", "");
    else
        padre.printSolucion();

    System.out.printf("%18s  :  %d  -  %d\n", accion, jarraA, jarraB);
}

@Override public int hashCode()
{
    return 97 * (97 * 7 + jarraA) + jarraB;
}

@Override public boolean equals(Object obj)
{
    if(obj == null || getClass() != obj.getClass())
        return false;

    final Estado e = (Estado)obj;
    return jarraA == e.jarraA && jarraB == e.jarraB;
}

} // Estado
