package layoutsControllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import serverTraitement.ClientUserInterface;
import serverTraitement.EmployeUserInterface;

import java.io.IOException;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.regex.Pattern;

public class AdminAddEmployeController
{

    @FXML
    private AnchorPane addEmployeLayout;

    public void toAdminAddEmployeLayout(ActionEvent event)
    {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("../layouts/admin_employe_layout.fxml"));
            addEmployeLayout.getChildren().removeAll();
            addEmployeLayout.getChildren().setAll(fxml);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    /*****************************/

    @FXML
    private TextField employeName;

    @FXML
    private TextField employeBirth;

    @FXML
    private TextField employeAddr;

    @FXML
    private TextField employePhone;

    @FXML
    private TextField employeUsername;

    @FXML
    private PasswordField employePassword;




    public void addEmploye(ActionEvent event)
    {

        if((employeName.getText() == null || employeName.getText() =="")
                && (employeBirth.getText()==null || employeBirth.getText()=="")
                && (employeAddr.getText()==null || employeAddr.getText()=="")
                && (employePhone.getText()==null || employePhone.getText()=="")
                && (employePassword.getText()==null || employePassword.getText()=="")
                && (employeUsername.getText()==null || employeUsername.getText()=="")){

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



            if(Pattern.matches("^[a-z ,.'-]+",employeName.getText()))
            {
                if(Pattern.matches("^([0-2][0-9]||3[0-1])/(0[0-9]||1[0-2])/([0-9][0-9])?[0-9][0-9]$",employeBirth.getText()))
                {
                    if(Pattern.matches("[0-9]+",employePhone.getText()))
                    {
                        // sign him up

                        HashMap<String,String> employeData = new HashMap<>();

                        employeData.put("employe_name",employeName.getText());
                        employeData.put("employe_birth",employeBirth.getText());
                        employeData.put("employe_addr",employeAddr.getText());
                        employeData.put("employe_phone",employePhone.getText());
                        employeData.put("employe_username",employeUsername.getText());
                        employeData.put("employe_password",employePassword.getText());

                        try {

                            EmployeUserInterface stub = (EmployeUserInterface) Naming.lookup("rmi://localhost:1099/GHADMINEMPLOYE");

                            boolean isSuccess = stub.employeSignupToBd(employeData);

                            // if isSuccess == true go back to client signup scene
                            // if isSuccess == true go back to client signup scene


                            if(isSuccess)
                            {

                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("employe a été enregisterer avec succee!");
                                alert.show();
                                alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
                                    @Override
                                    public void handle(DialogEvent dialogEvent) {
                                        try {
                                            Parent fxml = FXMLLoader.load(getClass().getResource("../layouts/admin_employe_layout.fxml"));
                                            addEmployeLayout.getChildren().removeAll();
                                            addEmployeLayout.getChildren().setAll(fxml);

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
                        alert.setContentText("Numero téléphone Invalide");
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
                alert.setContentText("Nom d'employe Invalide");
                alert.show();
            }


        }

    }



}
