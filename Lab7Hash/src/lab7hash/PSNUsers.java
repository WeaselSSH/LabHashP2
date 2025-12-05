/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab7hash;

import java.io.RandomAccessFile;

/**
 *
 * @author David
 */
public class PSNUsers {
    public static RandomAccessFile archivoPSN;
    public static HashTable lista;
    public PSNUsers(RandomAccessFile archivoPSN, HashTable lista){
        this.archivoPSN=archivoPSN;
        this.lista=lista;
        
        //reloadHastTable();
    }
    
    
}
