package layoutsControllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import serverTraitement.ClientUserInterface;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.regex.Pattern;

public class ClientSignupScreenController {

    /* switching scenes */
    private Stage stage;
    private Scene scene;
    private Parent root;



    public void toClientLoginScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../layouts/client_login_screen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    // signup coordinates

    @FXML
    private TextField clientName;

    @FXML
    private TextField clientBirth;

    @FXML
    private TextField clientEmail;

    @FXML
    private TextField clientUserName;

    @FXML
    private PasswordField clientPassword;

    @FXML
    private PasswordField clientConfirmPassword;


    public void signUpClientBtn(ActionEvent event)
    {
        //check if userInput are valid

        // check if null
        // regex



        if((clientName.getText() == null || clientName.getText() =="")
            && (clientBirth.getText()==null || clientBirth.getText()=="")
                && (clientEmail.getText()==null || clientEmail.getText()=="")
                && (clientUserName.getText()==null || clientUserName.getText()=="")
                && (clientPassword.getText()==null || clientPassword.getText()=="")
                && (clientConfirmPassword.getText()==null || clientConfirmPassword.getText()=="")){

            // Show Error Stage
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("DES CHAMPS VIDE");
            alert.show();
        }
        else
        {
            // check the validity of user inputs for
            // name dont contains number
            // client birth
            // client email



            if(Pattern.matches("^[a-z ,.'-]+",clientName.getText()))
            {
                if(Pattern.matches("^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$",clientBirth.getText()))
                {
                    if(Pattern.matches("^(.+)@(.+)$",clientEmail.getText()))
                    {
                        // sign him up

                        HashMap<String,String> clientData = new HashMap<>();

                        clientData.put("client_name",clientName.getText());
                        clientData.put("client_birth",clientBirth.getText());
                        clientData.put("client_mail",clientEmail.getText());
                        clientData.put("client_username",clientUserName.getText());
                        clientData.put("client_password",clientPassword.getText());

                        try {

                            ClientUserInterface stub = (ClientUserInterface) Naming.lookup("rmi://localhost:1099/GHCLIENT");

                            boolean isSuccess = stub.clientSignupToBd(clientData);

                            // if isSuccess == true go back to client signup scene
                            // if isSuccess == true go back to client signup scene


                            if(isSuccess)
                            {

                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("Client a été enregisterer avec succee!");
                                alert.show();
                                alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
                                    @Override
                                    public void handle(DialogEvent dialogEvent) {
                                        try {
                                            root = FXMLLoader.load(getClass().getResource("../layouts/client_login_screen.fxml"));
                                            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                                            scene = new Scene(root);
                                            stage.setScene(scene);
                                            stage.show();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }

                        } catch (NotBoundException e) {
                            e.printStackTrace();
                        } catch (MalformedURLException e) {
                            e.printStackTrace();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }




                    }
                    else
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Adresse mail Invalide");
                        alert.show();
                    }
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Date De Naissance Invalide");
                    alert.show();
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Nom Client Invalide");
                alert.show();
            }


        }

    }



}
