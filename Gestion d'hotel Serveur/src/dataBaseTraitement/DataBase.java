package dataBaseTraitement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBase {

    private String dbName = Utils.DB_NAME;
    private String dbUser = Utils.DB_USER;
    private String dbPassword = Utils.DB_PASSWORD;
    private String url = Utils.DB_URL;
    private Connection con;

    public void connectToDb()
    {
        try
        {
            con = DriverManager.getConnection(url,dbUser,dbPassword);
            System.out.println("connencté A base de donnee  ");
        }
        catch (SQLException exception)
        {
            System.out.println("Erreur Du connexion A base de donnee ");
            exception.printStackTrace();
        }
    }


    public Connection getCon()
    {
        return this.con;
    }









}
