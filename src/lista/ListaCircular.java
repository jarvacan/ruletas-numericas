/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lista;

import java.util.Iterator;
/**
 *
 * @author Margarita Chavez
 * @param <E>
 */

public class ListaCircular<E> implements Lista<E>{
    private E[] elements;
    private int capacity = 10;
    private int effectiveSize;
    
    public ListaCircular(){ //Todas las TDA van a inicializar vacias
        //No se puede hacer new de E directamente, por ello tienes que hacer el casting 
        elements = (E[]) new Object[capacity];    
        effectiveSize = 0;
    }
    
    @Override
    public boolean add(E element) {
        if(element == null){
            return false;
        }else if(effectiveSize == capacity){
            addCapacity();
        }
        
        elements[effectiveSize++] = element;
        return true;
    }
    
    @Override
    public boolean insert(int  index, E element){
        if(element == null || index >= effectiveSize)
            return false;
        else if (effectiveSize == capacity)
            addCapacity();
        
        for(int i = effectiveSize; i > index; i--){
            elements[i] = elements[i-1];
        }
        
        elements[index] = element;
        effectiveSize++;
        return true;
    }
    
    @Override
    public E remove(int index) {
        if(effectiveSize == 0 || index < 0 || index >= effectiveSize){
            return null;
        }
        E element = elements[index];
        for(int i = index; i < effectiveSize-1; i++){
            elements[i] = elements[i + 1];
        }
        elements[effectiveSize-1] = null;
        effectiveSize --;
        
        return element;
    }

    @Override
    public boolean remove(E element) {
        if(element == null) {
            return false;
        }
        
        for(int i = 0; i < effectiveSize; i++){
            if(elements[i].equals(element)){
                remove(i);
                return true;
            }
        }
        
        return false;
    }

    @Override
    public E set(int index, E element) {
        if (element == null || index < 0 || index >= effectiveSize){
            return null;
        }
        
        E i = elements[index];
        elements[index] = element;
        return i;
    }

    @Override
    public int size() {
        return effectiveSize;
    }
    
    private void addCapacity(){
        E[] tmp = (E[]) new Object [capacity*2];
        for(int i = 0; i < capacity; i++){
            tmp[i] = elements[i];
        }
        elements = tmp;
        capacity = capacity*2;
    }

    @Override
    public E getFirst() {
        return elements[0];
    }

    @Override
    public E getLast() {
        return elements[effectiveSize -1];
    }

    @Override
    public boolean isEmpty() {
        return effectiveSize == 0;
    }

    @Override
    public boolean contains(E element) {
        if(element == null || isEmpty()){
            return false;
        }
        
        for(int i = 0; i < effectiveSize ; i++){
            if(elements[i].equals(element)){
                return true;
            }
        }
        return false;
    }
    
    @Override
    public String toString(){
        StringBuilder s = new StringBuilder();
        s.append("[");
        
        for(int i = 0; i < effectiveSize; i++){
            if(i != effectiveSize-1){
                s.append(elements[i] + ", ");
            }else
               s.append(elements[i]);
        }
        
        s.append("]");
        return s.toString();
    }
    
    @Override
    public boolean equals(Object o){
        if(o == null || ! (o instanceof ListaCircular))
            return false;
        
        ListaCircular<E> other = (ListaCircular<E>) o;
        
        if(this.effectiveSize != other.effectiveSize)
            return false;
        
        for(int i =0; i< effectiveSize ;i++){
            if(!this.elements[i].equals(other.elements[i]))
                return false;
        }
        
        return true;
    }

    @Override
    public E get(int index) {
        if(effectiveSize == 0 || index < 0 || index >= effectiveSize){
            return null;
        }
        return elements[index];
    }

    @Override
    public int indexOf(E element) {
        if (element == null){
            return -1;
        }
        
        for(int i = 0; i < effectiveSize; i++){
            if(elements[i].equals(element)){
                return i;
            }
        }
        
        return -1;
    }
    
    public Lista<E> slicing(int start, int end){
        Lista<E> lista = new ListaCircular<>();
        
        if(start >= end || end > effectiveSize) {
            return lista;
        }
        
        for(int i = start; i < end; i++ ){
            lista.add(elements[i]);
        }
        
        return lista;
    }
    
    @Override
    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator<E>(){
            
            private int index = 0;
            
            @Override
            public boolean hasNext() {
                return index < effectiveSize;
            }

            @Override
            public E next() {
                E tmp = get(index);
                index++;
                return tmp;
            }
            
        };
        
        return it;
    }
    
    public void rotarIzquierda(int inicio, int fin){
        E first = elements[inicio];
        
        for(int i = inicio; i < fin; i++){
            E imas1 = elements[i +1];
            elements[i] = imas1;
        }
        
        elements[fin] = first;
    }
    
    public void rotarDerecha(int inicio, int fin){
        E last = elements[fin];
        
        for(int i = fin; i > inicio; i--){
            elements[i] = elements[i-1];
        }
        
        elements[inicio] = last;
    }
    
}
