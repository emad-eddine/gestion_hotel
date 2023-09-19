package layoutsControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import serverTraitement.AdminUserInterface;
import utils.UserLogins;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class AdminLoginScreenController  implements Serializable {


    /* switching scenes */
    private Stage stage;
    private Scene scene;
    private Parent root;



    public void toWelcomeScreen(ActionEvent event) throws IOException  {
        root = FXMLLoader.load(getClass().getResource("../layouts/welcome_screen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    /* user inputs */

    @FXML
    private TextField adminUserName;

    @FXML
    private PasswordField adminPassword;

    private String adminUserNameString , adminPasswordString;

    public void getAdminLogins(ActionEvent event)
    {
        // check if adminLoginInput valid not emty !
        if((adminUserName.getText() == "" || adminUserName.getText() == null)
                || (adminPassword.getText() == "" || adminPassword.getText() == null))
        {
            // Show Error Stage
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("DES CHAMPS VIDE");
            alert.show();
        }
        else
        {
            adminUserNameString = adminUserName.getText();
            adminPasswordString = adminPassword.getText();

            try {

                AdminUserInterface stub = (AdminUserInterface)Naming.lookup("rmi://localhost:1099/GH");

                String isAdminExist = stub.getAdminLoginCordinnates(adminUserNameString,adminPasswordString);

                if(isAdminExist != null)
                {
                    UserLogins.USER_NAME  = isAdminExist;
                    try {
                        root = FXMLLoader.load(getClass().getResource("../layouts/admin_main_screen.fxml"));
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


//                stub.addDataBaseListener(this);

            } catch (NotBoundException e) {
                e.printStackTrace();
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (RemoteException e) {
                e.printStackTrace();
            }


           // System.out.println(adminUserNameString+" " + adminPasswordString);
        }

    }

}
