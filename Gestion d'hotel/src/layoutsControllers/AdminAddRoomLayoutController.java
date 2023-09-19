package layoutsControllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import model.Room;
import serverTraitement.EmployeUserInterface;
import serverTraitement.RoomInterface;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AdminAddRoomLayoutController implements Initializable {


    @FXML
    private AnchorPane addRoomLayout;

    public void toRoomLayout(ActionEvent event)
    {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("../layouts/admin_room_layout.fxml"));
            addRoomLayout.getChildren().removeAll();
            addRoomLayout.getChildren().setAll(fxml);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private RadioButton yesBtn;
    @FXML
    private RadioButton noBtn;

    private ToggleGroup group  = new ToggleGroup();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        yesBtn.setToggleGroup(group);
        noBtn.setToggleGroup(group);
    }


    @FXML
    private TextField roomNum;
    @FXML
    private TextField roomCap;
    @FXML
    private TextField roomPrice;


    public void addRoom(ActionEvent event)
    {

        if((roomNum.getText() == null || roomNum.getText() =="")
                && (roomCap.getText()==null || roomCap.getText()=="")
                && (roomPrice.getText()==null || roomPrice.getText()=="")){

            // Show Error Stage
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("DES CHAMPS VIDE");
            alert.show();
        }
        else
        {
            // check the validity of user inputs for


            if(Pattern.matches("[0-9]+",roomNum.getText()))
            {
                if(Pattern.matches("[0-9]+",roomCap.getText()))
                {
                    if(Pattern.matches("[0-9]+",roomPrice.getText())) {
                        // sign him up

                        Room room = new Room();

                        room.setRoomNum(roomNum.getText());
                        room.setRoomCap(Integer.parseInt(roomCap.getText()));
                        room.setRoomPrice(Double.parseDouble(roomPrice.getText()));
                        RadioButton selectedBtn = (RadioButton) group.getSelectedToggle();

                        if(selectedBtn.getText().equals("Oui"))
                            room.setRoomWifi("inclus");
                        else
                            room.setRoomWifi("non inclus");

                        try {

                          RoomInterface stub = (RoomInterface) Naming.lookup("rmi://localhost:1099/ROOM");

                          boolean isSuccess = stub.addRoomToDb(room);


                            if(isSuccess)
                            {

                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setContentText("chambre a été enregisterer avec succee!");
                                alert.show();
                                alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
                                    @Override
                                    public void handle(DialogEvent dialogEvent) {
                                        try {
                                            Parent fxml = FXMLLoader.load(getClass().getResource("../layouts/admin_room_layout.fxml"));
                                            addRoomLayout.getChildren().removeAll();
                                            addRoomLayout.getChildren().setAll(fxml);

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



                    }else
                    {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("prix Chambre Invalide");
                        alert.show();
                    }
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Capacite de chambre Invalide");
                    alert.show();
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("numero de chambre Invalide");
                alert.show();
            }


        }

    }




}
