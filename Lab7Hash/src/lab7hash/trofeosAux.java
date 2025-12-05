/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab7hash;

/**
 *
 * @author David
 */
public class trofeosAux {
    private String username;
    private String tipo;
    private String nomjuego;
    private String nomtrofeo;
    private String fecha;
    private byte[] bytesImag;
    
    
    public trofeosAux(String username, String tipo, String nomjuego, String nomtrofeo, String fecha, byte[] imagb){
        this.username=username;
        this.tipo=tipo;
        this.nomjuego=nomjuego;
        this.nomtrofeo= nomtrofeo;
        this.fecha=fecha;
        this.bytesImag=imagb;
    }
    
    
    public String getusername(){
       return username; 
    
    }
    
    
    public String getTipo(){
        return tipo;
    }
    
    
    public String getnomjuego(){
        return nomjuego;
    
    }
    
    
    public String getnomtrofeo(){
        return nomtrofeo;
    }
    
    
    public String getfecha(){
        return fecha;
    }
    
    
    public byte[] getbytesImag(){
        return bytesImag;
    }
    
    
    
    
    
}
