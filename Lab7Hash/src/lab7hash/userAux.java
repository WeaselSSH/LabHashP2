/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab7hash;

import java.util.ArrayList;

/**
 *ESTA CLASE ES UNA CLASE AUXILIAR PRINCIPALEMNTE PARA MANEJAR LA INFORMACION QUE SE MOSTRARA EN PANTALLA
 * @author David
 */
public class userAux {
    private String nombre;
    private int trofeos;
    private int puntos;
    private boolean estado;
    
    private ArrayList<trofeosAux> userTrophies;
    
    public userAux(String nombre, int trofeos, int puntos, boolean estado){
        this.nombre=nombre;
        this.trofeos=trofeos;
        this.puntos=puntos;
        this.estado=estado;
        userTrophies = new ArrayList<>();
    }
    
    
    
    public String getName(){
        return nombre;
    }
    
    public int getcantTrofeos(){
        return trofeos;
    }
    
    public int getPuntos(){
        return puntos;
    }
    
    public ArrayList<trofeosAux> getrofeos(){
        return userTrophies;
    }
    
    public boolean isActivo(){
        return estado;
    }
    
    public void addTrophy(trofeosAux trophy){
        userTrophies.add(trophy);
    }
    
}
