/*
 * Released under the MIT License.
 * Copyright 2016 Oscar Vega-Gisbert.
 */
package rio;

public class Estado
{
private Estado padre;
private String accion;

// En la orilla izquierda: false
// En la orilla derecha: true
private boolean col, oveja, lobo, hombre;

public Estado()
{
    accion = "Estado Inicial";
}

private Estado(Estado estado, String accion)
{
    padre = estado;
    this.accion = accion;
    col    = estado.col;
    oveja  = estado.oveja;
    lobo   = estado.lobo;
    hombre = !estado.hombre;
}

public Estado viajarOveja()
{
    if(hombre != oveja)
        return null; // No están en la misma orilla

    Estado r = new Estado(this, "Viajar...Oveja");
    r.oveja = !oveja;
    return r.esCorrecto() ? r : null;
}

public Estado viajarCol()
{
    if(hombre != col)
        return null; // No están en la misma orilla

    Estado r = new Estado(this, "Viajar.....Col");
    r.col = !col;
    return r.esCorrecto() ? r : null;
}

public Estado viajarSolo()
{
    Estado r = new Estado(this, "Viajar....Solo");
    return r.esCorrecto() ? r : null;
}

public Estado viajarLobo()
{
    if(hombre != lobo)
        return null; // No están en la misma orilla

    Estado r = new Estado(this, "Viajar....Lobo");
    r.lobo = !lobo;
    return r.esCorrecto() ? r : null;
}

public boolean esObjetivo()
{
    return col && oveja && lobo && hombre;
}

public boolean esCorrecto()
{
    // La col y la oveja no pueden estar juntas sin el hombre.
    // La oveja y el lobo no pueden estar juntos sin el hombre.

    return (hombre == col || col != oveja) &&
           (hombre == oveja || oveja != lobo);
}

public void printSolucion()
{
    if(padre != null)
        padre.printSolucion();

    System.out.print(accion);
    System.out.print(":  {");
    System.out.print(oveja  ? "" : " oveja");
    System.out.print(lobo   ? "" : " lobo");
    System.out.print(col    ? "" : " col");
    System.out.print(hombre ? "" : " hombre");
    System.out.print(" } ");

    if(oveja || lobo || col || hombre)
        System.out.print(hombre ? " -> " : " <- ");

    System.out.print(" {");
    System.out.print(oveja  ? " oveja"  : "");
    System.out.print(lobo   ? " lobo"   : "");
    System.out.print(col    ? " col"    : "");
    System.out.print(hombre ? " hombre" : "");
    System.out.println(" }");
}

@Override public int hashCode()
{
    int hash = 3;
    hash = 83 * hash + (oveja  ? 1 : 0);
    hash = 83 * hash + (lobo   ? 1 : 0);
    hash = 83 * hash + (col    ? 1 : 0);
    hash = 83 * hash + (hombre ? 1 : 0);
    return hash;
}

@Override public boolean equals(Object obj)
{
    if(obj == null || getClass() != obj.getClass())
        return false;

    final Estado e = (Estado)obj;

    return oveja == e.oveja && lobo == e.lobo
           && col == e.col && hombre == e.hombre;
}

} // Estado
