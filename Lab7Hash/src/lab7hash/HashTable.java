/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab7hash;

/**
 *
 * @author edwin
 */
public class HashTable {

    private Entry inicio;

    public boolean isEmpty() {
        return inicio == null;
    }

    public void add(String username, long pos) {
        Entry obj = new Entry(username, pos);
        if (isEmpty()) {
            inicio = obj;
        } else {
            Entry tmp = inicio;
            while (tmp.siguiente != null) {
                tmp = tmp.siguiente;
            }
            tmp.siguiente = obj;
        }
    }

    public boolean remove(String username) {
        if (!isEmpty()) {
            if (inicio.username.equals(username)) {
                inicio = inicio.siguiente;
                return true;
            } else {
                Entry tmp = inicio;
                while (tmp.siguiente != null) {
                    if (tmp.siguiente.username.equals(username)) {
                        tmp.siguiente = tmp.siguiente.siguiente;
                        return true;
                    }
                    tmp = tmp.siguiente;
                }
            }
        }
        return false;
    }

    public long search(String username) {
        Entry tmp = inicio;
        while (tmp != null) {
            if (tmp.username.equals(username)) {
                return tmp.posURegistro;
            }
            tmp = tmp.siguiente;
        }
        return -1;
    }
}
