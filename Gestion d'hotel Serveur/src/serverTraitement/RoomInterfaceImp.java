package serverTraitement;

import dataBaseTraitement.DataBase;
import model.Room;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class RoomInterfaceImp extends UnicastRemoteObject implements Serializable,RoomInterface {
    public RoomInterfaceImp() throws RemoteException {
    }

    @Override
    public boolean addRoomToDb(Room room) throws RemoteException
    {

        DataBase db = new DataBase();
        db.connectToDb();
        Connection con = db.getCon();

        try
        {
            Statement stm = con.createStatement();


            String ADD_ROOM_SQL = "INSERT INTO chambre (room_num,room_cap,room_wifi,room_price)"
                    +"VALUE('" + room.getRoomNum()+"',"+room.getRoomCap()+",'" + room.isRoomWifi() + "',"
                    +room.getRoomPrice()+")";

            int querryResult = stm.executeUpdate(ADD_ROOM_SQL);

            //System.out.println(querryResult);

            // querryResult > 0 then more than 1 row have been inserted to the table

            if(querryResult > 0)
                return true;
            else
                return false;

        } catch (SQLException |NullPointerException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }

    @Override
    public List<Room> getRooms() throws RemoteException
    {
        DataBase db = new DataBase();
        db.connectToDb();
        Connection con = db.getCon();

        List<Room> rooms  = new ArrayList<>();

        try {
            Statement stm = con.createStatement();

            String ADMIN_INFO_SQL = "SELECT * FROM chambre ";

            ResultSet resultSet = stm.executeQuery(ADMIN_INFO_SQL);

            if (!resultSet.isBeforeFirst())
            {
                // no result
                //System.out.println("no result found");

                return null;
            }
            else
            {
                while(resultSet.next())
                {
                    // System.out.println("connected");

                    Room room = new Room();
                    room.setRoomId(resultSet.getInt("room_id"));
                    room.setRoomNum(resultSet.getString("room_num"));
                    room.setRoomCap(resultSet.getInt("room_cap"));
                    room.setRoomWifi(resultSet.getString("room_wifi"));
                    room.setRoomPrice(resultSet.getDouble("room_price"));

                    rooms.add(room);

                }
                return rooms;
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }

    @Override
    public boolean editRoomToDb(Room room) throws RemoteException {

        DataBase db = new DataBase();
        db.connectToDb();
        Connection con = db.getCon();

        try
        {
            Statement stm = con.createStatement();

            String EDIT_ROOM_SQL ="UPDATE chambre SET room_num = '" + room.getRoomNum() +"',room_cap="
                    + room.getRoomCap() +",room_wifi='" + room.isRoomWifi() +"',room_price="
                    +room.getRoomPrice() +" WHERE room_id = " + room.getRoomId();

            int querryResult = stm.executeUpdate(EDIT_ROOM_SQL);

            //System.out.println(querryResult);

            // querryResult > 0 then more than 1 row have been inserted to the table

            if(querryResult > 0)
                return true;
            else
                return false;

        } catch (SQLException |NullPointerException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean deleteRoomFromDb(Room room) throws RemoteException {
        DataBase db = new DataBase();
        db.connectToDb();
        Connection con = db.getCon();

        try
        {

            Statement stm = con.createStatement();

            String ADMIN_INFO_SQL = "SELECT * FROM room_reserv WHERE room_id = " + room.getRoomId();

            ResultSet resultSet = stm.executeQuery(ADMIN_INFO_SQL);

            if (!resultSet.isBeforeFirst())
            {
              /// if there is no  room reservation you can delete the room

                Statement stm2 = con.createStatement();

                String DELETE_ROOM_SQL ="DELETE FROM chambre WHERE room_id = " + room.getRoomId();

                int querryResult = stm2.executeUpdate(DELETE_ROOM_SQL);

                //System.out.println(querryResult);

                // querryResult > 0 then more than 1 row have been inserted to the table

                if(querryResult > 0)
                    return true;
                else
                    return false;
            }



        } catch (SQLException |NullPointerException throwables) {
            throwables.printStackTrace();
            return false;
        }
        return false;
    }
}
