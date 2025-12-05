/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab7hash;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 *
 * @author David
 */
public class PSNUsers {
    private RandomAccessFile archivoPSN;
    private HashTable lista;
    private RandomAccessFile trophiesPSN; //COMO NO PIDE QUE VAYA COMO PARAMETRO, ESTE SE CARGA MEDIANTE METODO APARTE!!
    
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
    
    
    
    //USAR ESTE METODO AL INICIAR EL PROGRAMA Y CREAR EL ARCHIVO DE TROPHIES
    public void setTrophiesFile(RandomAccessFile trophies) throws IOException{
        this.trophiesPSN=trophies;
    }
    
    
    
    public boolean addUser(String username) throws IOException{
        archivoPSN.seek(archivoPSN.length());
       
        if(!userRepetido(username)){
            long pos = archivoPSN.getFilePointer();
            archivoPSN.seek(pos);
            archivoPSN.writeUTF(username);
            archivoPSN.writeInt(0); //contador trofeos
            archivoPSN.writeInt(0); //contador puntos
            archivoPSN.writeBoolean(true);
            lista.add(username, pos);
            
            return true;
        }
        
        
        return false;//el false indica que no se pudo agregar debido a que el usuario ya esta repetdio
        
    }
    

    public boolean deactivateUser(String username) throws IOException{
        archivoPSN.seek(0);
       
        if(userExistence(username)){
            long posUser=lista.search(username);
            archivoPSN.seek(posUser);
            
            String name= archivoPSN.readUTF();
            archivoPSN.readInt(); //contador trofeos
            archivoPSN.readInt(); //contador puntos
            
            long posBool = archivoPSN.getFilePointer();
            archivoPSN.seek(posBool);
            boolean actualstatus = archivoPSN.readBoolean();
            if(actualstatus){
                archivoPSN.seek(posBool);
                archivoPSN.writeBoolean(false);
                lista.remove(username);
                return true;
            }else{
                return false;
            }   
            /*
               while(archivoPSN.getFilePointer()< archivoPSN.length()){
                String name= archivoPSN.readUTF();
                archivoPSN.readInt(); //contador trofeos
                archivoPSN.readInt(); //contador puntos
                if(name.equals(username)){
                    long posBool = archivoPSN.getFilePointer();
                    archivoPSN.seek(posBool);
                    boolean actualstatus = archivoPSN.readBoolean();
                    if(actualstatus){
                        archivoPSN.seek(posBool);
                        archivoPSN.writeBoolean(false);
                        lista.remove(username);
                        return true;
                    }else{
                        return false;
                    }

                }else{
                    archivoPSN.readBoolean();
                }
            }
*/
        }
        return false;        
    }
    
    
    
    
    
    //esto hace referencia a que se debe que convertir la imagen a bytes
    public boolean addTrophy(String username, String trophyGame, String trophyName, Trophy type, byte[] trophyImagesBytes) throws IOException{
        
        //comprobar primero si el usuario existe
        if(userExistence(username)){
            trophiesPSN.seek(trophiesPSN.length());
            String typeString = type.name();
            int points = type.points;
            Calendar hoy = Calendar.getInstance();
            SimpleDateFormat formato= new SimpleDateFormat("dd/MM/yyyy");
            String fechaTrofeo = formato.format(hoy.getTime());

            //Actualizar informacion en archivo de trofeos
            trophiesPSN.writeUTF(username);//nombre
            trophiesPSN.writeUTF(typeString);//tipo de trofeo
            trophiesPSN.writeUTF(trophyGame);//juego 
            trophiesPSN.writeUTF(trophyName);//nombre de trofeo
            trophiesPSN.writeUTF(fechaTrofeo);//fecha de obtencion
            trophiesPSN.writeInt(trophyImagesBytes.length);//linea necesaria para extraer el arreglo de bytes a futuro
            trophiesPSN.write(trophyImagesBytes);//bytes de imagen


            //actualizar info de jugador
            actualizarPuntaje(username, points);
            return true;
        }
        return false;
        
    }
    
    
    public userAux playerInfo(String username) throws IOException {
        if(userExistence(username)){
             archivoPSN.seek(0);
             
            long posUser=lista.search(username);
            archivoPSN.seek(posUser);
            String name =archivoPSN.readUTF();
            int cantTrophies = archivoPSN.readInt();;
            int cantPts= archivoPSN.readInt();
            boolean statUser = archivoPSN.readBoolean();

            userAux userInfo = new userAux(name,cantTrophies, cantPts, statUser);
            addTrophiesUser(userInfo, username);
            return userInfo;           
             /*
             while(archivoPSN.getFilePointer()< archivoPSN.length()){
                 String name =archivoPSN.readUTF();
                 if(name.equals(username)){
                     int cantTrophies = archivoPSN.readInt();;
                     int cantPts= archivoPSN.readInt();
                     boolean statUser = archivoPSN.readBoolean();
                     
                     userAux userInfo = new userAux(name,cantTrophies, cantPts, statUser);
                     addTrophiesUser(userInfo, username);
                     return userInfo;
                 }

                 archivoPSN.readInt();
                 archivoPSN.readInt();
                 archivoPSN.readBoolean();
             }
*/
        }
        return null;
    }
    
    
    private void addTrophiesUser(userAux user, String username) throws IOException{
        if(userExistence(username)){
            trophiesPSN.seek(0);
            while(trophiesPSN.getFilePointer()< trophiesPSN.length()){
                String name = trophiesPSN.readUTF();
                if(name.equals(username)){
                   String type= trophiesPSN.readUTF();
                   String gameName= trophiesPSN.readUTF();
                   String NameTrof= trophiesPSN.readUTF();
                   String fecha= trophiesPSN.readUTF();
                   byte[] imagBytes= new byte[trophiesPSN.readInt()];
                   trophiesPSN.read(imagBytes);
                   
                   trofeosAux newTrophy = new trofeosAux(name, type, gameName, NameTrof, fecha, imagBytes);
                   user.addTrophy(newTrophy);
                 }

                trophiesPSN.readUTF();
                trophiesPSN.readUTF();
                trophiesPSN.readUTF();
                trophiesPSN.readUTF();
                byte[] holder = new byte[trophiesPSN.readInt()];//para que no se crashee
                trophiesPSN.read(holder);
            }
      
        }
       
    }
    
    
    
    private void  actualizarPuntaje(String username, int puntos) throws IOException{
         archivoPSN.seek(0);
         while(archivoPSN.getFilePointer()< archivoPSN.length()){
             if(archivoPSN.readUTF().equals(username)){
                 //actualizar cantidad de trofeos
                 long posTrophies = archivoPSN.getFilePointer(); //posicion de trofeos
                 archivoPSN.seek(posTrophies);
                 int cantT= archivoPSN.readInt();
                 archivoPSN.seek(posTrophies);
                 archivoPSN.writeInt((cantT+1));//actualizar cant trofeos
                 
                 
                 //ACtualizar puntaje
                 long posPTS = archivoPSN.getFilePointer();//posicion de puntos
                 archivoPSN.seek(posPTS);
                 int points= archivoPSN.readInt();
                 int newCant =points+puntos;
                 archivoPSN.seek(posPTS);
                 archivoPSN.writeInt(newCant);//actualiza los puntos
                 
                 break;
             }
             
             archivoPSN.readInt();
             archivoPSN.readInt();
             archivoPSN.readBoolean();
         }
    }
    
    
    private boolean userRepetido(String username) throws IOException{
        archivoPSN.seek(0);
        boolean repetido=false;
        
        //OJO--> REVISAR QUE NO CREE INCONSISTENCIAS POR EL HECHO DE DEJAR QUE EL CICLO TERMINE A TODO SU LENGTH
        while(archivoPSN.getFilePointer()< archivoPSN.length()){
            if(archivoPSN.readUTF().equals(username)){
                repetido=true;
            }
            archivoPSN.readInt();//read trofeos
            archivoPSN.readInt();//read puntos
            archivoPSN.readBoolean();
            
        }
        
        if(repetido){
            return true;
        }
        
        return false;
        
    }
    
    
    private boolean userExistence(String username) throws IOException{
        archivoPSN.seek(0);
        boolean existencia=false;
        
        //OJO--> REVISAR QUE NO CREE INCONSISTENCIAS POR EL HECHO DE DEJAR QUE EL CICLO TERMINE A TODO SU LENGTH
        while(archivoPSN.getFilePointer()< archivoPSN.length()){
            if(archivoPSN.readUTF().equals(username)){
                existencia=true;
            }
            archivoPSN.readInt();//read trofeos
            archivoPSN.readInt();//read puntos
            archivoPSN.readBoolean();
            
        }
        
        return existencia;
    }
    
    
}
