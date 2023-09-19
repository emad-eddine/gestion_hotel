package layoutsControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.w3c.dom.Text;
import serverTraitement.ClientUserInterface;
import utils.RoomReservation;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class ClientLoginScreenController {

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

    public void toClientSignUoScreen(MouseEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../layouts/client_signup_screen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    // login

    @FXML
    private TextField clientUserName;

    @FXML
    private TextField clientPassword;


    public void loginClient(ActionEvent event)
    {

        if((clientUserName.getText() == "" || clientUserName.getText() == null)
                || (clientPassword.getText() == "" || clientPassword.getText() == null))
        {
            // Show Error Stage
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("DES CHAMPS VIDE");
            alert.show();
        }
        else
        {
            String clientUserNameText = clientUserName.getText();
            String clientPasswordText = clientPassword.getText();

            try {
                ClientUserInterface stub = (ClientUserInterface) Naming.lookup("rmi://localhost:1099/GHCLIENT");

                int isClientExist = stub.clientLogin(clientUserNameText,clientPasswordText);

                if(isClientExist != 0)
                {
                    RoomReservation.clientID = isClientExist;
                    try {
                        root = FXMLLoader.load(getClass().getResource("../layouts/client_main_screen.fxml"));
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
