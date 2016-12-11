/*
 * Released under the MIT License.
 * Copyright 2016 Oscar Vega-Gisbert.
 */
package dtree;

public class Main
{
public static void main(String args[])
{
    Boolean T = true,
            F = false;

    Atributos atributos = new Atributos(new Object[][]
    {
        { "Alternativa",  T, F },
        { "Bar",          T, F },
        { "Vier/Sáb",     T, F },
        { "Hambriento",   T, F },
        { "Clientes",     "ninguno", "algunos", "lleno" },
        { "Precio",       "$", "$$", "$$$" },
        { "Lloviendo",    T, F },
        { "Reserva",      T, F },
        { "Tipo",         "francés","italiano","tailandés","burger" },
        { "TiempoEspera", "0-10", "10-30", "30-60", ">60" }
    });

    Ejemplos ejemplos = new Ejemplos(atributos, new Object[][]
    {
        // El primer elemento de cada ejemplo indica si es positivo.
        { T, T, F, F, T, "algunos", "$$$", F, T, "francés",   "0-10"  },
        { F, T, F, F, T, "lleno",   "$",   F, F, "tailandés", "30-60" },
        { T, F, T, F, F, "algunos", "$",   F, F, "burger",    "0-10"  },
        { T, T, F, T, T, "lleno",   "$",   T, F, "tailandés", "10-30" },
        { F, T, F, T, F, "lleno",   "$$$", F, T, "francés",   ">60"   },
        { T, F, T, F, T, "algunos", "$$",  T, T, "italiano",  "0-10"  },
        { F, F, T, F, F, "ninguno", "$",   T, F, "burger",    "0-10"  },
        { T, F, F, F, T, "algunos", "$$",  T, T, "tailandés", "0-10"  },
        { F, F, T, T, F, "lleno",   "$",   T, F, "burger",    ">60"   },
        { F, T, T, T, T, "lleno",   "$$$", F, T, "italiano",  "10-30" },
        { F, F, F, F, F, "ninguno", "$",   F, F, "tailandés", "0-10"  },
        { T, T, T, T, T, "lleno",   "$",   F, F, "burger",    "30-60" }
    });

    ArbolDecision arbol = new ArbolDecision(atributos, ejemplos);
    System.out.println(arbol.ganancia(4));
    System.out.println(arbol.ganancia(8));
}

} // Main
