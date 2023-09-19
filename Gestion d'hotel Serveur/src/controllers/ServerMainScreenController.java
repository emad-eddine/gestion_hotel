package controllers;

import dataBaseTraitement.DataBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import serverTraitement.AdminUserController;
import serverTraitement.ClientUserImpl;
import serverTraitement.EmployeUserImpl;
import serverTraitement.RoomInterfaceImp;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class ServerMainScreenController implements Serializable {


    @FXML
    private Button serverOnBtn;
    @FXML
    private Button serverOffBtn;

    @FXML
    public Label serverStatusLabel1;


    // server on btn

    // turn server on + connect to bd

    public void turnServerOn(ActionEvent event) {

        // RMI

        System.setProperty("java.security.policy", "file:/C:/Users/IMAD/Desktop/Projet SRA V2/Gestion d'hotel Serveur/src/server.policy");


        try {

            LocateRegistry.createRegistry(1099);

            AdminUserController Obj = new AdminUserController();

            ClientUserImpl clientUser = new ClientUserImpl();

            EmployeUserImpl employeUser = new EmployeUserImpl();

            RoomInterfaceImp roomInterfaceImp = new RoomInterfaceImp();

            Naming.rebind("rmi://localhost:1099/GH", Obj);
            Naming.rebind("rmi://localhost:1099/GHCLIENT", clientUser);
            Naming.rebind("rmi://localhost:1099/GHADMINEMPLOYE", employeUser);
            Naming.rebind("rmi://localhost:1099/ROOM", roomInterfaceImp);

            serverStatusLabel1.setText("ON");
            serverStatusLabel1.setTextFill(Color.rgb(12, 54, 12));

        } catch (RemoteException | MalformedURLException e) {
            e.printStackTrace();
        }

        // serverTraitement.AdminUserInterface adminUserController = new serverTraitement.AdminUserController();
        //System.setProperty("java.rmi.server.hostname","192.168.43.79");


    }
    }
