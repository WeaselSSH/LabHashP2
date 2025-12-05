/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab7hash;

import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * @author David
 */
public class PSNUsers {
    public RandomAccessFile archivoPSN;
    private HashTable lista;
    
    public PSNUsers(RandomAccessFile archivoPSN, HashTable lista){
        this.archivoPSN=archivoPSN;
        this.lista=lista;
        
        try{
            reloadHashTable();
        }catch(IOException e){};
        
    }
    
    
    
    
    /*
    
    */
    private void reloadHashTable() throws IOException{
        //chequeo de usuarios dentro del archivo binario
        archivoPSN.seek(0);
        long pos;
        while(archivoPSN.getFilePointer()< archivoPSN.length()){
            pos =archivoPSN.getFilePointer();//posicion de donde empiezan los datos de X usuario
            
            archivoPSN.seek(pos);//volvemos a la posicion para ahora sacar los datos
            String username = archivoPSN.readUTF();//read name
            archivoPSN.readInt();//read trofeos
            archivoPSN.readInt();//read puntos
            
             //agregar al hastable
            if(archivoPSN.readBoolean()==true){
                lista.add(username, pos);//agrega usuario si este esta activo
            }
        }
    }
    
}
