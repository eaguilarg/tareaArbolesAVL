/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package arbolavl;

import static javafx.scene.input.KeyCode.T;

/**
 *
 * @author eaguilarg
 */
public class ArbolAVL <T extends Comparable<T>>{

  private NodoArbolAVL<T> raiz;
  
  public ArbolAVL(){
      raiz=null;
  }
  public NodoArbolAVL<T> obtenerRaiz(){
      return raiz;
  }
  //buscar Nodo
    public NodoArbolAVL<T> buscar(T d,NodoArbolAVL r){
        if(raiz==null){
            return null;
        }else if(r.dato.equals(d)){
            return r;
        }else if(d.compareTo((T)r.dato)>0){
            return buscar(d,r.hijoDerecho);
        }else{
            return buscar(d,r.hijoIzquierdo);
        }
    }
    //metodo obtener Factor equilibrio
    public int obtenerFE(NodoArbolAVL x){
        if(x==null){
            return -1;
        }else{
            return x.fe;
        }
    }
    //rotacion simple izquierda
    public NodoArbolAVL<T> rotacionIzquierda(NodoArbolAVL<T> c){
        NodoArbolAVL<T> auxiliar=c.hijoIzquierdo;
        c.hijoIzquierdo=auxiliar.hijoDerecho;
        auxiliar.hijoDerecho=c;
        c.fe=Math.max(obtenerFE(c.hijoIzquierdo), obtenerFE(c.hijoDerecho))+1;//altura real
        auxiliar.fe=Math.max(obtenerFE(auxiliar.hijoIzquierdo), obtenerFE(auxiliar.hijoDerecho))+1;
        return auxiliar;
    }
    //rotacion simple derecha
     public NodoArbolAVL<T> rotacionDerecha(NodoArbolAVL<T> c){
        NodoArbolAVL<T> auxiliar=c.hijoDerecho;
        c.hijoDerecho=auxiliar.hijoIzquierdo;
        auxiliar.hijoIzquierdo=c;
        c.fe=Math.max(obtenerFE(c.hijoIzquierdo), obtenerFE(c.hijoDerecho))+1;//altura real
        auxiliar.fe=Math.max(obtenerFE(auxiliar.hijoIzquierdo), obtenerFE(auxiliar.hijoDerecho))+1;
        return auxiliar;
    }
     //rotacion doble izquierda: der-izq
     public NodoArbolAVL<T> rotacionDobleIzquierda(NodoArbolAVL<T> c){
         NodoArbolAVL<T> temporal;
         c.hijoDerecho=rotacionDerecha(c.hijoDerecho);
         temporal=rotacionIzquierda(c);
         return temporal;
     }
     //rotacion doble derecha: izq-der
     public NodoArbolAVL<T> rotacionDobleDerecha(NodoArbolAVL<T> c){
         NodoArbolAVL<T> temporal;
         c.hijoIzquierdo=rotacionIzquierda(c.hijoDerecho);
         temporal=rotacionDerecha(c);
         return temporal;
     }
     //metodo insertar
     public NodoArbolAVL<T> insertarAVL(NodoArbolAVL<T> nuevo, NodoArbolAVL<T> subAr){
         NodoArbolAVL<T> nuevoPadre=subAr;
         if(nuevo.dato.compareTo((T)subAr.dato)<=0){//elem menor o igual a raiz
             if(subAr.hijoIzquierdo==null){//raiz no hijo izq
                 subAr.hijoIzquierdo=nuevo;//agregar
             }else{
                 subAr.hijoIzquierdo=insertarAVL(nuevo,subAr.hijoIzquierdo);//buscar pos
                 if((obtenerFE(subAr.hijoIzquierdo)-obtenerFE(subAr.hijoDerecho)==2)){//caso de desvalanceo cargado izquierda
                     if(nuevo.dato.compareTo((T)subAr.hijoIzquierdo.dato)<0){//elem agregado todo izq
                         nuevoPadre=rotacionIzquierda(subAr);
                     }else{
                         nuevoPadre=rotacionDobleIzquierda(subAr);//desvalanceo final ocurrio en derecha
                     }
                 }
             }
         }else if(nuevo.dato.compareTo((T)subAr.dato)>0){
             if(subAr.hijoDerecho==null){//raiz no tiene derecha
                 subAr.hijoDerecho=nuevo;//agregar
             }else{
                 subAr.hijoDerecho=insertarAVL(nuevo,subAr.hijoDerecho);//buscar pos
                 if((obtenerFE(subAr.hijoDerecho)-obtenerFE(subAr.hijoIzquierdo)==2)){//desvalanceo a der
                     if(nuevo.dato.compareTo((T)subAr.hijoDerecho.dato)>0){//elem agregado todo der
                         nuevoPadre=rotacionDerecha(subAr);
                     }else{
                         nuevoPadre=rotacionDobleDerecha(subAr);//desvalanceo final ocurrio en izq
                     }
                     
                 }
             }
         }
         //actualizar la altura
         if(subAr.hijoIzquierdo==null && subAr.hijoDerecho!=null){//no hijo izq si der
             subAr.fe=subAr.hijoDerecho.fe+1;
         }else if((subAr.hijoDerecho==null) && (subAr.hijoIzquierdo!=null)){//no hijo der si hijo izq
             subAr.fe=subAr.hijoIzquierdo.fe+1;
         }else{
             subAr.fe=Math.max(obtenerFE(subAr.hijoIzquierdo), obtenerFE(subAr.hijoDerecho))+1;//ambos hijos
         }
         return nuevoPadre;//balanceo
     }
     //metodo insertar
     public void insertar(int d){
         NodoArbolAVL nuevo=new NodoArbolAVL(d);
         if(raiz==null){//no hay nodos
             raiz=nuevo;
         }else{
             raiz=insertarAVL(nuevo,raiz);
         }
         
     }
     
     public NodoArbolAVL<T> elimina(NodoArbolAVL<T> borrar, NodoArbolAVL<T> raiz){
         if(borrar.dato.compareTo((T)raiz.dato)<0)
             elimina(borrar,raiz.hijoIzquierdo);
         else if(borrar.dato.compareTo((T)raiz.dato)>0){
             elimina(borrar,raiz.hijoDerecho);
         }
         else{//hubo una coincidencia 
             if(borrar.hijoIzquierdo==null && borrar.hijoDerecho==null){//es hoja
                 borrar=null;
             }
             if(borrar.hijoIzquierdo==null){//solo tiene hijo der
                borrar.dato=(T) borrar.hijoDerecho.dato;
                borrar.hijoDerecho=null;
             }
             if(borrar.hijoDerecho==null){//solo tiene hijo izq
                 borrar.dato=(T)borrar.hijoIzquierdo.dato;
                 borrar.hijoIzquierdo=null;
             }
             else{//tiene ambos hijos
                 borrar.dato=(T)eliminaMin(borrar.hijoDerecho);
             }
                          
         }
         balancear(borrar);
         actualizarFE(borrar);
      return null;
     }
     
public void elimina(T elem){
NodoArbolAVL<T> actual=null;
  if(buscar(elem,raiz)==null)
    System.out.println("ElementNotFoundExcpetion");
  else{
    NodoArbolAVL<T> nuevo=buscar(elem,raiz);
    actual=elimina(nuevo,raiz);
}
      
}

private T eliminaMin(NodoArbolAVL<T> actual){
    T aux = null;
    if(actual.hijoDerecho==null && actual.hijoIzquierdo==null)//nodo no hijos 
        return null;
    else{
        if(actual.hijoIzquierdo!=null){//tiene hijo izq
            aux=(T)eliminaMin(actual.hijoIzquierdo);
            balancear(actual);
            actualizarFE(actual);
            return aux;
        }else{
            actual=actual.hijoDerecho;
            actual.hijoDerecho=null;
            actual.hijoIzquierdo=null;
            balancear(actual);
            actualizarFE(actual);
            return aux;
        }
    }
}
     private void balancear(NodoArbolAVL<T> actual){
         if(actual.hijoIzquierdo!=null && actual.hijoDerecho!=null){
             if((obtenerFE(actual.hijoIzquierdo))-obtenerFE(actual.hijoDerecho)==2){
                    if(obtenerFE(actual.hijoIzquierdo.hijoIzquierdo)>=obtenerFE(actual.hijoIzquierdo.hijoDerecho))
                        rotacionIzquierda(actual);
                    else
                        rotacionDobleIzquierda(actual);
             }else if(obtenerFE(actual.hijoDerecho)-obtenerFE(actual.hijoIzquierdo)==2){
                 if(obtenerFE(actual.hijoDerecho.hijoDerecho)>=obtenerFE(actual.hijoDerecho.hijoIzquierdo))
                     rotacionDerecha(actual);
                 else
                     rotacionDobleDerecha(actual);
             }
         }
     }

    private void actualizarFE(NodoArbolAVL<T> actual) {
         //actualizar la altura
         if(actual.hijoIzquierdo==null && actual.hijoDerecho!=null){//no hijo izq si der
             actual.fe=actual.hijoDerecho.fe+1;
         }else if((actual.hijoDerecho==null) && (actual.hijoIzquierdo!=null)){//no hijo der si hijo izq
             actual.fe=actual.hijoIzquierdo.fe+1;
         }else{
             actual.fe=Math.max(obtenerFE(actual.hijoIzquierdo), obtenerFE(actual.hijoDerecho))+1;//ambos hijos
         }
    }
    
     //recorridos
     public void inOrden(NodoArbolAVL r){
         if(r!=null){
             inOrden(r.hijoIzquierdo);
             System.out.print(r.dato +", ");
             inOrden(r.hijoDerecho);
         }
     }
     public void preOrden(NodoArbolAVL r){
         StringBuilder sb=new StringBuilder();
         int val = 0;
         if(r!=null){
             val = Math.subtractExact(obtenerFE(r.hijoDerecho),obtenerFE(r.hijoIzquierdo));
              sb.append("Elemento: "+r.dato+" ");
              sb.append("Fe: "+val);
              System.out.println( sb.toString());
                                         
             preOrden(r.hijoIzquierdo);
             preOrden(r.hijoDerecho);
         }
     }
    
  
    
    
  public static void main(String[] args){
      ArbolAVL<Integer> arbol=new ArbolAVL<Integer>();
      //insertar
      arbol.insertar(10);
      arbol.insertar(5);
      arbol.insertar(13);
      arbol.insertar(1);
      arbol.insertar(6);
      arbol.insertar(16);
      arbol.insertar(17);
      arbol.insertar(4);
      arbol.elimina(10);
      arbol.preOrden(arbol.obtenerRaiz());
      
      
  }  

    
}
//http://ingsistemas.ufps.edu.co/SEED/arbolavl.html    simulador AVL descargar dist/.jar
    

