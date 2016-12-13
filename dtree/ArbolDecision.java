/*
 * Released under the MIT License.
 * Copyright 2016 Oscar Vega-Gisbert.
 */
package dtree;

public class ArbolDecision
{
private final Atributos m_atributos;
private final Ejemplos  m_ejemplos;
private final double    m_total, m_informacion;
private static final double LOG2 = Math.log(2);

public ArbolDecision(Atributos atributos, Ejemplos ejemplos)
{
    m_atributos = atributos;
    m_ejemplos  = ejemplos;

    int positivos = 0,
        negativos = 0;

    int numEjemplos = m_ejemplos.size();

    for(int e = 0; e < numEjemplos; e++)
    {
        if(ejemplos.positivo(e))
            positivos++;
        else
            negativos++;
    }

    m_total       = positivos + negativos;
    m_informacion = informacion(positivos, negativos);
}

public double ganancia(int atributo)
{
    return m_informacion - resto(atributo);
}

private double resto(int atributo)
{
    double resto = 0;
    int numValores = m_atributos.size(atributo);

    for(int i = 0; i < numValores; i++)
    {
        Object vi = m_atributos.getValor(atributo, i);
        int    pi = 0,
               ni = 0;

        int numEjemplos = m_ejemplos.size();

        for(int e = 0; e < numEjemplos; e++)
        {
            if(vi.equals(m_ejemplos.valor(e, atributo)))
            {
                if(m_ejemplos.positivo(e))
                    pi++;
                else
                    ni++;
            }
        }

        double ti = pi + ni;
        resto += (ti / m_total) * informacion(pi, ni);
    }

    return resto;
}

private double informacion(int positivos, int negativos)
{
    double inf   = 0,
           total = positivos + negativos;

    if(positivos > 0)
    {
        double pPos = positivos / total;
        inf -= pPos * Math.log(pPos) / LOG2;
    }

    if(negativos > 0)
    {
        double pNeg = negativos / total;
        inf -= pNeg * Math.log(pNeg) / LOG2;
    }

    return inf;
}

} // ArbolDecision
