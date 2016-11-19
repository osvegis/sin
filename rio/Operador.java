/*
 * Released under the MIT License.
 * Copyright 2016 Oscar Vega-Gisbert.
 */
package rio;

import java.util.function.*;

//************************************************************************
public enum Operador implements UnaryOperator<Estado>
{
    viajarOveja (e -> e.viajarOveja()),
    viajarCol   (e -> e.viajarCol()),
    viajarSolo  (e -> e.viajarSolo()),
    viajarLobo  (e -> e.viajarLobo());

    private UnaryOperator<Estado> m_op;

    private Operador(UnaryOperator<Estado> op)
    {
        m_op = op;
    }

    @Override public Estado apply(Estado e)
    {
        return m_op.apply(e);
    }

} // Operador
