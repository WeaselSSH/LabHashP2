/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab7hash;

/**
 *
 * @author edwin
 */
public class Entry {

    public String username;
    public long posURegistro;
    public Entry siguiente;

    Entry(String username, long posURegistro) {
        this.username = username;
        this.posURegistro = posURegistro;
        this.siguiente = null;
    }
}
