package lab7hash;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class PSNUsers {

    private RandomAccessFile archivoPSN;
    private HashTable lista;
    private RandomAccessFile trophiesPSN;

    public PSNUsers(RandomAccessFile archivoPSN, HashTable lista) {
        this.archivoPSN = archivoPSN;
        this.lista = lista;
        try {
            reloadHashTable();
        } catch (IOException e) {
        }
    }

    private void reloadHashTable() throws IOException {
        archivoPSN.seek(0);
        while (archivoPSN.getFilePointer() < archivoPSN.length()) {
            long pos = archivoPSN.getFilePointer();
            String username = archivoPSN.readUTF();
            archivoPSN.readInt();
            archivoPSN.readInt();
            boolean activo = archivoPSN.readBoolean();
            if (activo) {
                lista.add(username, pos);
            }
        }
    }

    public void setTrophiesFile(RandomAccessFile trophies) throws IOException {
        this.trophiesPSN = trophies;
    }

    public boolean addUser(String username) throws IOException {
        archivoPSN.seek(archivoPSN.length());
        if (!userExistence(username)) {
            long pos = archivoPSN.getFilePointer();
            archivoPSN.writeUTF(username);
            archivoPSN.writeInt(0);
            archivoPSN.writeInt(0);
            archivoPSN.writeBoolean(true);
            lista.add(username, pos);
            return true;
        }
        return false;
    }

    public boolean deactivateUser(String username) throws IOException {
        long posUser = lista.search(username);
        if (posUser == -1) {
            return false;
        }
        archivoPSN.seek(posUser);
        archivoPSN.readUTF();
        archivoPSN.readInt();
        archivoPSN.readInt();
        long posBool = archivoPSN.getFilePointer();
        boolean actualstatus = archivoPSN.readBoolean();
        if (!actualstatus) {
            return false;
        }
        archivoPSN.seek(posBool);
        archivoPSN.writeBoolean(false);
        lista.remove(username);
        return true;
    }

    public boolean addTrophy(String username, String trophyGame, String trophyName, Trophy type, byte[] trophyImagesBytes) throws IOException {
        long posUser = lista.search(username);
        if (posUser == -1) {
            return false;
        }

        trophiesPSN.seek(trophiesPSN.length());
        String typeString = type.name();
        int points = type.points;
        Calendar hoy = Calendar.getInstance();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String fechaTrofeo = formato.format(hoy.getTime());

        trophiesPSN.writeUTF(username);
        trophiesPSN.writeUTF(typeString);
        trophiesPSN.writeUTF(trophyGame);
        trophiesPSN.writeUTF(trophyName);
        trophiesPSN.writeUTF(fechaTrofeo);
        trophiesPSN.writeInt(trophyImagesBytes.length);
        trophiesPSN.write(trophyImagesBytes);

        actualizarPuntaje(username, points);
        return true;
    }

    public userAux playerInfo(String username) throws IOException {
        long posUser = lista.search(username);
        if (posUser == -1) {
            return null;
        }

        archivoPSN.seek(posUser);
        String name = archivoPSN.readUTF();
        int cantTrophies = archivoPSN.readInt();
        int cantPts = archivoPSN.readInt();
        boolean statUser = archivoPSN.readBoolean();

        userAux userInfo = new userAux(name, cantTrophies, cantPts, statUser);
        addTrophiesUser(userInfo, username);
        return userInfo;
    }

    private void addTrophiesUser(userAux user, String username) throws IOException {
        if (trophiesPSN == null) {
            return;
        }
        trophiesPSN.seek(0);
        while (trophiesPSN.getFilePointer() < trophiesPSN.length()) {
            String name = trophiesPSN.readUTF();
            String type = trophiesPSN.readUTF();
            String gameName = trophiesPSN.readUTF();
            String nameTrof = trophiesPSN.readUTF();
            String fecha = trophiesPSN.readUTF();
            int len = trophiesPSN.readInt();
            byte[] imagBytes = new byte[len];
            trophiesPSN.read(imagBytes);

            if (name.equals(username)) {
                trofeosAux newTrophy = new trofeosAux(name, type, gameName, nameTrof, fecha, imagBytes);
                user.addTrophy(newTrophy);
            }
        }
    }

    private void actualizarPuntaje(String username, int puntos) throws IOException {
        long posUser = lista.search(username);
        if (posUser == -1) {
            return;
        }

        archivoPSN.seek(posUser);
        archivoPSN.readUTF();

        long posTrophies = archivoPSN.getFilePointer();
        int cantT = archivoPSN.readInt();
        archivoPSN.seek(posTrophies);
        archivoPSN.writeInt(cantT + 1);

        long posPTS = archivoPSN.getFilePointer();
        int points = archivoPSN.readInt();
        archivoPSN.seek(posPTS);
        archivoPSN.writeInt(points + puntos);
    }

    private boolean userExistence(String username) throws IOException {
        archivoPSN.seek(0);
        boolean repetido = false;
        while (archivoPSN.getFilePointer() < archivoPSN.length()) {
            if (archivoPSN.readUTF().equals(username)) {
                repetido = true;
            }
            archivoPSN.readInt();
            archivoPSN.readInt();
            archivoPSN.readBoolean();
        }
        return repetido;
    }
}
