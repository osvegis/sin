/*
 * Released under the MIT License.
 * Copyright 2016 Oscar Vega-Gisbert.
 */
package reinas;

/**
 * Problema de las N reinas.
 */
public class Main
{
// Columnas ocupadas por las reinas en cada fila:
// reinas[y] indica en qué columna está la reina de la fila y.
private static int[] reinas;

private static boolean
    rx[], // Columnas ocupadas por reinas.
    dr[], // Diagonales de izquierda a derecha ocupadas.
    ds[]; // Diagonales de derecha a izquierda ocupadas.

private static int soluciones; // Número de soluciones encontradas.

private static void busquedaRecursiva(int y)
{
    int r, s, n = reinas.length;

    if(y == n)
    {
        printSolucion();
        return;
    }

    for(int x = 0; x < n; x++)
    {
        if(!rx[x] && !dr[r = n + x - y] && !ds[s = x + y])
        {
            rx[x] = dr[r] = ds[s] = true;
            reinas[y] = x;
            busquedaRecursiva(y + 1);
            rx[x] = dr[r] = ds[s] = false;
        }
    }
}

private static void busquedaIterativa()
{
    int r, s, x = 0, y = 0, n = reinas.length;

    for(;;)
    {
        if(y > 0)
        {
            if(y == n)
                printSolucion();

            y--;
            x = reinas[y];
            rx[x] = dr[n + x - y] = ds[x + y] = false;
            x++;

            if(y == 0 && x == n)
                break;
        }

        while(x < n)
        {
            if(!rx[x] && !dr[r = n + x - y] && !ds[s = x + y])
            {
                rx[x] = dr[r] = ds[s] = true;
                reinas[y] = x;
                y++;
                x = 0;
            }
            else
            {
                x++;
            }
        }
    }
}

private static void printSolucion()
{
    StringBuilder sb = new StringBuilder();

    for(int y = 0; y < reinas.length; y++)
    {
        int r = reinas[y];

        for(int x = 0; x < reinas.length; x++)
            sb.append(r == x ? " X" : " ·");

        sb.append('\n');
    }

    soluciones++;
    System.out.println("Solución: "+ soluciones +"\n");
    System.out.println(sb);
}

public static void main(String args[])
{
    int n = 8;

    if(args.length == 1)
        n = Integer.parseInt(args[0]);

    System.out.println(n +" reinas");
    reinas = new int[n];
    rx = new boolean[n];
    dr = new boolean[2 * n];
    ds = new boolean[2 * n];

    //busquedaRecursiva(0);
    busquedaIterativa();
}

} // Main
