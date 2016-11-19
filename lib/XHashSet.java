/*
 * Released under the MIT License.
 * Copyright 2016 Oscar Vega-Gisbert.
 */
package lib;

import java.util.*;

/**
 * Conjunto para implementar ejemplos de Sistemas Inteligentes.
 * <p>El control de estados repetidos en una búsqueda no informada
 * se puede hacer con la clase {@code java.util.HashSet}, pero
 * en el caso de la búsqueda en profundidad, además de comprobar
 * si un estado está repetido, hay que ver si se ha llegado a él
 * con una profundidad menor, porque en tal caso, hay que borrar
 * el estado que había en el conjunto, y añadir el nuevo sin
 * considerarlo repetido. Por ello, es necesaria esta clase.
 * @param <T> Tipo de los elementos almacenados en el conjunto.
 */
public class XHashSet<T> implements Iterable<T>
{
private final HashMap<T,T> map = new HashMap<>();

/**
 * Añade un estado al conjunto. El estado no debe existir.
 * @param e Estado que se añade al conjunto.
 */
public void add(T e)
{
    if(map.put(e, e) != null)
        throw new AssertionError();
}

/**
 * Obtiene un estado idéntico si existe.
 * @param e Estado con el que se buscará un estado idéntico.
 * @return Estado idéntico si existe o null si no existe.
 */
public T get(T e)
{
    return map.get(e);
}

/**
 * Elimina un estado del conjunto. El estado debe existir.
 * @param e Estado a eliminar.
 */
public void remove(T e)
{
    if(map.remove(e) != e)
        throw new AssertionError();
}

/**
 * Indica si el conjunto está vacío.
 * @return True si el conjunto está vacío.
 */
public boolean isEmpty()
{
    return map.isEmpty();
}

/**
 * Obtiene el tamaño de la cola.
 * @return Tamaño de la cola.
 */
public int size()
{
    return map.size();
}

/**
 * Obtiene un iterador sobre el conjunto de elementos.
 * @return iterador
 */
@Override public Iterator<T> iterator()
{
    return map.keySet().iterator();
}

} // XHashSet
