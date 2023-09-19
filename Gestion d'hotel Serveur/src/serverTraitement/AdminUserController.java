package serverTraitement;



import dataBaseTraitement.DataBase;
import model.Employe;

import javax.swing.*;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AdminUserController extends UnicastRemoteObject implements AdminUserInterface, Serializable {



    public AdminUserController() throws RemoteException {
        super();


    }

    @Override
    public String getAdminLoginCordinnates(String userName, String userPassword)
    {
        DataBase db = new DataBase();
        db.connectToDb();
        Connection con = db.getCon();

        try {
            Statement stm = con.createStatement();

            String ADMIN_INFO_SQL = "SELECT * FROM admin WHERE admin_username ='" + userName
                    + "' AND admin_password='" + userPassword +"'";

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

                    return resultSet.getString("admin_name");


                }

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Employe> getEmployeInfo(List<Employe> employes)
    {

        DataBase db = new DataBase();
        db.connectToDb();
        Connection con = db.getCon();




        try {
            Statement stm = con.createStatement();

            String ADMIN_INFO_SQL = "SELECT * FROM employe";

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
                    employe.setEmployeBitrh(resultSet.getString("employe_birth"));
                    employe.setEmployeAddr(resultSet.getString("employe_addr"));
                    employe.setEmployePhone(resultSet.getString("employe_phone"));
                    employe.setEmployeUserName(resultSet.getString("employe_username"));
                    employe.setEmployePassword(resultSet.getString("employe_password"));

                    employes.add(employe);


                }

               // System.out.println(employes.get(0).getEmployeName());
                return employes;

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return null;
        }

    }

    @Override
    public boolean deleteEmploye(int employeId) throws RemoteException {


        // add data to bd

        DataBase db = new DataBase();
        db.connectToDb();
        Connection con = db.getCon();

        try {
            Statement stm = con.createStatement();


            String DELETE_EMPLOYE = "DELETE FROM employe WHERE employe_id = " + employeId;

            int querryResult = stm.executeUpdate(DELETE_EMPLOYE);

            //System.out.println(querryResult);

            // querryResult > 0 then more than 1 row have been inserted to the table

            if (querryResult > 0)
                return true;
            else
                return false;

        } catch (SQLException | NullPointerException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    @Override
    public double getRevenue(String fromDate,String toDate) throws RemoteException {
        // add data to bd

        DataBase db = new DataBase();
        db.connectToDb();
        Connection con = db.getCon();

        try {
            Statement stm = con.createStatement();

            String ADMIN_INFO_SQL = "SELECT SUM(price) AS p FROM checkin WHERE date_checkin BETWEEN '"
                    + fromDate + "' AND '" + toDate +"'";

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
                    System.out.println(resultSet.getDouble("p"));
                    double total = resultSet.getDouble("p");
                    return total;
                }

            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return 0;
        }
        return 0;
    }

    @Override
    public boolean editEmploye(HashMap<String, Object> employeData) throws RemoteException {


        DataBase db = new DataBase();
        db.connectToDb();
        Connection con = db.getCon();

        try {
            Statement stm = con.createStatement();

            String EDIT_EMPLOYE = "UPDATE employe SET employe_name = '" + employeData.get("employe_name")
                    +"',employe_birth = '" + employeData.get("employe_birth") +"',employe_addr ='"
                    + employeData.get("employe_addr") +"',employe_phone='" + employeData.get("employe_phonr")
                    +"',employe_username ='" + employeData.get("employe_username") +"',employe_password = '"
                    + employeData.get("employe_password") + "' WHERE employe_id = " + employeData.get("employe_id");


            int querryResult = stm.executeUpdate(EDIT_EMPLOYE);

            //System.out.println(querryResult);

            // querryResult > 0 then more than 1 row have been inserted to the table

            if (querryResult > 0)
                return true;
            else
                return false;

        } catch (SQLException | NullPointerException throwables) {
            throwables.printStackTrace();
            return false;
        }

    }


}
