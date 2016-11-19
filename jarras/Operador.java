/*
 * Released under the MIT License.
 * Copyright 2016 Oscar Vega-Gisbert.
 */
package jarras;

import java.util.function.*;

//************************************************************************
public enum Operador implements UnaryOperator<Estado>
{
    llenarA {@Override public Estado apply(Estado e)
    {
        return e.llenarA();
    }},

    llenarB {@Override public Estado apply(Estado e)
    {
        return e.llenarB();
    }},

    vaciarA {@Override public Estado apply(Estado e)
    {
        return e.vaciarA();
    }},

    vaciarB {@Override public Estado apply(Estado e)
    {
        return e.vaciarB();
    }},

    traspAB {@Override public Estado apply(Estado e)
    {
        return e.traspAB();
    }},

    traspBA {@Override public Estado apply(Estado e)
    {
        return e.traspBA();
    }}
} // Operador
