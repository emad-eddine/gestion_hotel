package layoutsControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Employe;
import serverTraitement.ClientUserInterface;
import serverTraitement.EmployeUserInterface;
import utils.RoomReservation;
import utils.UserLogins;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class EmployeLoginScreenController {

    /* switching scenes */
    private Stage stage;
    private Scene scene;
    private Parent root;



    public void toWelcomeScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../layouts/welcome_screen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // login

    @FXML
    private TextField employeUserName;

    @FXML
    private TextField employePassword;

    public void employeLogin(ActionEvent event)
    {

        if((employeUserName.getText() == "" || employeUserName.getText() == null)
                || (employePassword.getText() == "" || employePassword.getText() == null))
        {
            // Show Error Stage
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("DES CHAMPS VIDE");
            alert.show();
        }
        else
        {
            String employeUserNameText = employeUserName.getText();
            String employePasswordText = employePassword.getText();

            try {
                EmployeUserInterface stub = (EmployeUserInterface) Naming.lookup("rmi://localhost:1099/GHADMINEMPLOYE");

                Employe isEmployeExist = stub.employeLogin(employeUserNameText,employePasswordText);

                if(isEmployeExist != null)
                {
                    UserLogins.USER_ID = isEmployeExist.getEmployeId();
                    UserLogins.USER_NAME = isEmployeExist.getEmployeName();
                    try {
                        root = FXMLLoader.load(getClass().getResource("../layouts/employe_home_layout.fxml"));
                        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        scene = new Scene(root,1000,600);
                        stage.setScene(scene);
                        stage.setResizable(false);
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Nom d'Utilisateur ou mot de passe incorrect");
                    alert.show();

                }


            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (RemoteException e) {
                e.printStackTrace();
            }








        }



    }




}
