package serverTraitement;

import model.Room;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.List;

public interface ClientUserInterface extends Remote {

    boolean clientSignupToBd(HashMap<String,String> clientSignData) throws RemoteException;
    int clientLogin(String clientUserName , String clientPassword) throws RemoteException;
    List<Room> getAvailableRoom(HashMap<String,String> reservationDetails) throws RemoteException;
    boolean validateReservation(HashMap<String,Object> reservationDetails) throws RemoteException;
    int getLastReservation(int clientID) throws RemoteException;
}
