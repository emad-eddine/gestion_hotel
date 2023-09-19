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
import java.util.HashMap;
import java.util.List;

public class ClientUserImpl extends UnicastRemoteObject implements ClientUserInterface, Serializable {

    public ClientUserImpl() throws RemoteException {
    }


    @Override
    public boolean clientSignupToBd(HashMap<String,String> clientSignData) throws RemoteException
    {
        // add data to bd

        DataBase db = new DataBase();
        db.connectToDb();
        Connection con = db.getCon();

        try
        {
            Statement stm = con.createStatement();

            String clientName = clientSignData.get("client_name");
            String clientBirth = clientSignData.get("client_birth");
            String clientEmail = clientSignData.get("client_mail");
            String clientUsername = clientSignData.get("client_username");
            String clientPassword = clientSignData.get("client_password");


            String ADD_CLIENT_SQL = "INSERT INTO client (client_name,client_birth,client_mail,client_username,client_password)"
                    +"VALUE('" + clientName+"','"+clientBirth+"','"
                    +clientEmail+"','"+clientUsername+"','"+clientPassword+"')";

            int querryResult = stm.executeUpdate(ADD_CLIENT_SQL);

            //System.out.println(querryResult);

            // querryResult > 0 then more than 1 row have been inserted to the table

            if(querryResult > 0)
                return true;
            else
                return false;

        } catch (SQLException|NullPointerException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }

    @Override
    public int clientLogin(String clientUserName, String clientPassword) throws RemoteException {

        DataBase db = new DataBase();
        db.connectToDb();
        Connection con = db.getCon();

        try {
            Statement stm = con.createStatement();

            String ADMIN_INFO_SQL = "SELECT * FROM client WHERE client_username ='" + clientUserName
                    + "' AND client_password='" + clientPassword +"'";

            ResultSet resultSet = stm.executeQuery(ADMIN_INFO_SQL);

            if (!resultSet.isBeforeFirst())
            {
                // no result
                //System.out.println("no result found");

                return 0;
            }
            else
            {
                while(resultSet.next())
                {
                    // System.out.println("connected");
                    return resultSet.getInt("client_id");
                }

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;


    }

    @Override
    public List<Room> getAvailableRoom(HashMap<String, String> reservationDetails) throws RemoteException
    {


        DataBase db = new DataBase();
        db.connectToDb();
        Connection con = db.getCon();
        List<Room> availableRoom = new ArrayList<>();

        try {
            Statement stm = con.createStatement();


            String ROOM_INFO_SQL = "SELECT * FROM chambre WHERE room_cap>= " + reservationDetails.get("room_cap")
                    + " AND room_wifi ='" + reservationDetails.get("room_wifi")
                    + "' AND chambre.room_id NOT IN(SELECT room_id FROM room_reserv WHERE '"+
                    reservationDetails.get("from_date") + "' < to_date AND '" +
                    reservationDetails.get("to_date") + "' > from_date )";

            ResultSet resultSet = stm.executeQuery(ROOM_INFO_SQL);

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
                    room.setRoomCap(resultSet.getInt("room_cap"));
                    room.setRoomNum(resultSet.getString("room_num"));
                    room.setRoomPrice(resultSet.getDouble("room_price"));
                    room.setRoomWifi(resultSet.getString("room_wifi"));

                    availableRoom.add(room);

                }

                return availableRoom;

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;


    }

    @Override
    public boolean validateReservation(HashMap<String, Object> reservationDetails) throws RemoteException {

        DataBase db = new DataBase();
        db.connectToDb();
        Connection con = db.getCon();

        try {
            Statement stm = con.createStatement();

            String ROOM_RESERV_INSERT = "INSERT INTO room_reserv(room_id,client_id,from_date,to_date,price) VALUES("
                    + reservationDetails.get("room_id") + "," + reservationDetails.get("client_id")
                    + ",'" + reservationDetails.get("from_date") +"','" + reservationDetails.get("to_date")
                    +"'," + reservationDetails.get("room_price") +")";

            int querryResult = stm.executeUpdate(ROOM_RESERV_INSERT);

            //System.out.println(querryResult);

            // querryResult > 0 then more than 1 row have been inserted to the table

            if(querryResult > 0)
                return true;
            else
                return false;

        } catch (SQLException|NullPointerException throwables) {
            throwables.printStackTrace();
            return false;
        }


    }

    @Override
    public int getLastReservation(int clientID) throws RemoteException {

        DataBase db = new DataBase();
        db.connectToDb();
        Connection con = db.getCon();


        try {
            Statement stm = con.createStatement();


            String RESERV_SQL = "SELECT * FROM room_reserv WHERE client_id = " + clientID +" ORDER BY room_reserv_id DESC LIMIT 1";

            ResultSet resultSet = stm.executeQuery(RESERV_SQL);

            if (!resultSet.isBeforeFirst())
            {
                // no result
                //System.out.println("no result found");
                return 0;
            }
            else
            {
                while(resultSet.next())
                {
                    System.out.println(resultSet.getInt("room_reserv_id"));
                    return resultSet.getInt("room_reserv_id");

                }

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        }
        return 0;

    }
}
