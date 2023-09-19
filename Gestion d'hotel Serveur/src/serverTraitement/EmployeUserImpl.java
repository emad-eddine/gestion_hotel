package serverTraitement;

import dataBaseTraitement.DataBase;
import model.Employe;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class EmployeUserImpl extends UnicastRemoteObject implements EmployeUserInterface, Serializable {
    public EmployeUserImpl() throws RemoteException {
    }


    @Override
    public boolean employeSignupToBd(HashMap<String, String> employeSignData) throws RemoteException {
        // add data to bd

        DataBase db = new DataBase();
        db.connectToDb();
        Connection con = db.getCon();

        try
        {
            Statement stm = con.createStatement();

            String employename = employeSignData.get("employe_name");
            String employebirth = employeSignData.get("employe_birth");
            String employeaddr = employeSignData.get("employe_addr");
            String employePhone = employeSignData.get("employe_phone");
            String employeusername = employeSignData.get("employe_username");
            String employepassword = employeSignData.get("employe_password");


            String ADD_EMPLOYE_SQL = "INSERT INTO employe (employe_name,employe_birth,employe_phone,employe_addr,employe_username,employe_password)"
                    +"VALUE('" + employename+"','"+employebirth+"','" + employePhone + "','"
                    +employeaddr+"','"+employeusername+"','"+employepassword+"')";

            int querryResult = stm.executeUpdate(ADD_EMPLOYE_SQL);

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
    public Employe employeLogin(String employeUserName, String employetPassword) throws RemoteException {

        DataBase db = new DataBase();
        db.connectToDb();
        Connection con = db.getCon();

        try {
            Statement stm = con.createStatement();

            String ADMIN_INFO_SQL = "SELECT * FROM employe WHERE employe_username ='" + employeUserName
                    + "' AND employe_password='" + employetPassword +"'";

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
                    Employe employe = new Employe();
                    employe.setEmployeId(resultSet.getInt("employe_id"));
                    employe.setEmployeName(resultSet.getString("employe_name"));
                    return employe;
                }

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }

    @Override
    public HashMap<String, Object> getReservatinoDetails(int reservId) throws RemoteException {


        DataBase db = new DataBase();
        db.connectToDb();
        Connection con = db.getCon();

        HashMap<String, Object> details = new HashMap<>();

        try {
            Statement stm = con.createStatement();
            Statement stm1 = con.createStatement();
            Statement stm2 = con.createStatement();
            Statement stm3 = con.createStatement();

            String CHECK_SQL = "SELECT * FROM checkin WHERE room_reserv_id =" + reservId;

            ResultSet checkSet = stm3.executeQuery(CHECK_SQL);

            if (checkSet.isBeforeFirst()) {
                // THERE IS  result
                //System.out.println("no result found");

                return null;
            } else {

                String ROOM_SQL = "SELECT * FROM room_reserv WHERE room_reserv_id =" + reservId;

                ResultSet resultSet = stm.executeQuery(ROOM_SQL);

                if (!resultSet.isBeforeFirst()) {
                    // no result
                    //System.out.println("no result found");

                    return null;
                } else {
                    while (resultSet.next()) {
                        // System.out.println("connected");

                        int clientID = resultSet.getInt("client_id");
                        int roomID = resultSet.getInt("room_id");

                        String nomClient = "";
                        String numeroChambre = "";
                        String reservDatesFrom = "";
                        String reservDatesTo = "";
                        double price = 0;

                        reservDatesFrom = resultSet.getDate("from_date").toString();
                        reservDatesTo = resultSet.getDate("to_date").toString();

                        price = resultSet.getDouble("price");

                        //client
                        ROOM_SQL = "SELECT * FROM client WHERE client_id = " + clientID;
                        ResultSet clientResult = stm1.executeQuery(ROOM_SQL);
                        if (clientResult.isBeforeFirst()) {
                            while (clientResult.next()) {
                                nomClient = clientResult.getString("client_name");
                                System.out.println(nomClient);
                            }
                        }

                        //room

                        ROOM_SQL = "SELECT * FROM chambre WHERE room_id = " + roomID;
                        ResultSet roomResult = stm2.executeQuery(ROOM_SQL);
                        if (roomResult.isBeforeFirst()) {
                            while (roomResult.next()) {
                                numeroChambre = roomResult.getString("room_num");
                            }
                        }


                        details.put("nomCLient", nomClient);
                        details.put("roomNumber", numeroChambre);
                        details.put("from_date", reservDatesFrom);
                        details.put("to_date", reservDatesTo);
                        details.put("price", price);

                    }
                    return details;

                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;

    }

    @Override
    public HashMap<String, Object> getReservatinoDetailsOut(int reservId) throws RemoteException {
        DataBase db = new DataBase();
        db.connectToDb();
        Connection con = db.getCon();

        HashMap<String, Object> details = new HashMap<>();

        try {
            Statement stm = con.createStatement();
            Statement stm1 = con.createStatement();
            Statement stm2 = con.createStatement();
            Statement stm3 = con.createStatement();
            Statement stm4 = con.createStatement();

            String CHECK_SQL = "SELECT * FROM checkin WHERE room_reserv_id =" + reservId;

            ResultSet checkSet = stm3.executeQuery(CHECK_SQL);

            if (!checkSet.isBeforeFirst()) {
                // THERE IS no result
                //System.out.println("no result found");
                return null;
            } else {

                String CHECK_SQL2 = "SELECT * FROM checkout WHERE room_reserv_id =" + reservId;
                ResultSet checkSet2 = stm4.executeQuery(CHECK_SQL2);

                if (checkSet2.isBeforeFirst()) {
                    // THERE IS no result
                    //System.out.println("no result found");
                    return null;
                } else {

                    String ROOM_SQL = "SELECT * FROM room_reserv WHERE room_reserv_id =" + reservId;

                    ResultSet resultSet = stm.executeQuery(ROOM_SQL);

                    if (!resultSet.isBeforeFirst()) {
                        // no result
                        //System.out.println("no result found");

                        return null;
                    } else {
                        while (resultSet.next()) {
                            // System.out.println("connected");

                            int clientID = resultSet.getInt("client_id");
                            int roomID = resultSet.getInt("room_id");

                            String nomClient = "";
                            String numeroChambre = "";
                            String reservDatesFrom = "";
                            String reservDatesTo = "";
                            double price = 0;

                            reservDatesFrom = resultSet.getDate("from_date").toString();
                            reservDatesTo = resultSet.getDate("to_date").toString();

                            price = resultSet.getDouble("price");

                            //client
                            ROOM_SQL = "SELECT * FROM client WHERE client_id = " + clientID;
                            ResultSet clientResult = stm1.executeQuery(ROOM_SQL);
                            if (clientResult.isBeforeFirst()) {
                                while (clientResult.next()) {
                                    nomClient = clientResult.getString("client_name");
                                    System.out.println(nomClient);
                                }
                            }

                            //room

                            ROOM_SQL = "SELECT * FROM chambre WHERE room_id = " + roomID;
                            ResultSet roomResult = stm2.executeQuery(ROOM_SQL);
                            if (roomResult.isBeforeFirst()) {
                                while (roomResult.next()) {
                                    numeroChambre = roomResult.getString("room_num");
                                }
                            }


                            details.put("nomCLient", nomClient);
                            details.put("roomNumber", numeroChambre);
                            details.put("from_date", reservDatesFrom);
                            details.put("to_date", reservDatesTo);
                            details.put("price", price);

                        }
                        return details;

                    }

                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

        @Override
    public boolean checkClientIn(HashMap<String, Object> details) throws RemoteException {

        DataBase db = new DataBase();
        db.connectToDb();
        Connection con = db.getCon();


        int clientId = -1;
        int roomId = -1;

        try {
            Statement stm1 = con.createStatement();

            // client

            String CLIENT_SQl = "SELECT * FROM client WHERE client_name ='" + details.get("nomCLient") + "'";

            ResultSet resultSet = stm1.executeQuery(CLIENT_SQl);

            if (resultSet.isBeforeFirst())
            {
                while (resultSet.next())
                {
                    // System.out.println("connected");
                    clientId = resultSet.getInt("client_id");
                }
            }

            // chambre

            Statement stm2 = con.createStatement();

            String ROOM_SQl = "SELECT * FROM chambre WHERE room_num =" + details.get("roomNumber");

            ResultSet resultSet2 = stm1.executeQuery(ROOM_SQl);

            if (resultSet2.isBeforeFirst())
            {
                while (resultSet2.next())
                {
                    // System.out.println("connected");
                    roomId = resultSet2.getInt("room_id");
                }
            }


            String CHECK_IN_SQL = "INSERT INTO checkin(room_reserv_id,room_id,client_id,employe_id,date_checkin,price)"
                    +" VALUES(" + details.get("reservId") +"," + roomId + "," + clientId + "," + details.get("employeId")
                    +",'" + details.get("checkInDate") +"'," + details.get("price") +")";

            Statement stm3 = con.createStatement();

            int querryResult = stm3.executeUpdate(CHECK_IN_SQL);

            //System.out.println(querryResult);

            // querryResult > 0 then more than 1 row have been inserted to the table

            if(querryResult > 0)
                return true;
            else
                return false;


        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }


    }

    @Override
    public boolean checkClientOut(HashMap<String, Object> details) throws RemoteException {
        DataBase db = new DataBase();
        db.connectToDb();
        Connection con = db.getCon();


        int clientId = -1;
        int roomId = -1;

        try {
            Statement stm1 = con.createStatement();

            // client

            String CLIENT_SQl = "SELECT * FROM client WHERE client_name ='" + details.get("nomCLient") + "'";

            ResultSet resultSet = stm1.executeQuery(CLIENT_SQl);

            if (resultSet.isBeforeFirst())
            {
                while (resultSet.next())
                {
                    // System.out.println("connected");
                    clientId = resultSet.getInt("client_id");
                }
            }

            // chambre

            Statement stm2 = con.createStatement();

            String ROOM_SQl = "SELECT * FROM chambre WHERE room_num =" + details.get("roomNumber");

            ResultSet resultSet2 = stm1.executeQuery(ROOM_SQl);

            if (resultSet2.isBeforeFirst())
            {
                while (resultSet2.next())
                {
                    // System.out.println("connected");
                    roomId = resultSet2.getInt("room_id");
                }
            }

            String CHECK_OUT_SQL = "INSERT INTO checkout(room_reserv_id,room_id,client_id,employe_id,date_checkout)"
                    +" VALUES(" + details.get("reservId") +"," + roomId + "," + clientId + "," + details.get("employeId")
                    +",'" + details.get("checkOutDate") +"')";

            Statement stm3 = con.createStatement();

            int querryResult = stm3.executeUpdate(CHECK_OUT_SQL);

            //System.out.println(querryResult);

            // querryResult > 0 then more than 1 row have been inserted to the table

            if(querryResult > 0)
                return true;
            else
                return false;


        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }
}
