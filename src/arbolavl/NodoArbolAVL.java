package arbolavl;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author eaguilarg
 */
public class NodoArbolAVL<T extends Comparable<T>> {
   T dato;
   int fe;
    NodoArbolAVL hijoIzquierdo,hijoDerecho;
    
    NodoArbolAVL(T d){
        this.dato=d;
        this.fe=0;
        this.hijoIzquierdo=null;
        this.hijoDerecho=null;
    }
    public int compareTo(NodoArbolAVL otro){
        return this.dato.compareTo((T)otro.dato);
    }
    
    
    
    
    public static void main(String[] args) {
        // TODO code application logic here
    }
    
}
    

