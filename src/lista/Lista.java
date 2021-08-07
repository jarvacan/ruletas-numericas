package lista;

/**
 *
 * @author Jocellyn Luna
 * @param <E>
 */

public interface Lista <E> extends Iterable<E>{
    boolean add(E element);
    //boolean removeFirst();
    //boolean removeLast();
    E getFirst();
    E getLast();
    boolean insert(int index, E element);
    boolean contains(E element);
    E get(int index);
    int indexOf(E element);
    boolean isEmpty();
    E remove(int index);
    boolean remove(E element);
    E set(int index, E element);
    int size();
}
