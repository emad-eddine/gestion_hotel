package serverTraitement;

import model.Room;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface RoomInterface extends Remote {

    public boolean addRoomToDb(Room room) throws RemoteException;
    public List<Room> getRooms() throws RemoteException;
    public boolean editRoomToDb(Room room) throws RemoteException;
    boolean deleteRoomFromDb(Room room) throws RemoteException;
}
