package layoutsControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import model.Room;
import serverTraitement.ClientUserInterface;
import serverTraitement.RoomInterface;
import utils.RoomReservation;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.*;
import java.util.regex.Pattern;

public class ClientMainScreenController implements Initializable {

    /* switching scenes */
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private DatePicker fromDate;

    @FXML
    private DatePicker toDate;

    @FXML
    private TextField roomCap;

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


    public void checkAvailableRooms(javafx.event.ActionEvent event)
    {
        // get room details

        HashMap<String,String> roomDetails = new HashMap<>();


        try
        {
            if(Pattern.matches("[0-9]+",roomCap.getText()))
            {

                RadioButton selectedBtn = (RadioButton) group.getSelectedToggle();

                if(selectedBtn.getText().equals("Oui"))
                    roomDetails.put("room_wifi","inclus");
                else
                    roomDetails.put("room_wifi","non inclus");


                roomDetails.put("room_cap",roomCap.getText());
                roomDetails.put("from_date",fromDate.getValue().toString());
                roomDetails.put("to_date",toDate.getValue().toString());
               // System.out.println(roomDetails.get("to_date"));

                RoomReservation.fromDate = fromDate.getValue().toString();
                RoomReservation.toDate = toDate.getValue().toString();

                try {
                    ClientUserInterface stub = (ClientUserInterface) Naming.lookup("rmi://localhost:1099/GHCLIENT");

                    List<Room> rooms = new ArrayList<>();
                    rooms = stub.getAvailableRoom(roomDetails);

                    if(rooms != null)
                    {

                        try
                        {
                            RoomReservation.rooms = rooms;
                            root = FXMLLoader.load(getClass().getResource("../layouts/room_reservation_layout.fxml"));
                            stage = new Stage();
                            stage.setTitle("Gestion D'hotel");
                            stage.getIcons().add(new Image("assests/hotel.png"));
                            stage.setScene(new Scene(root, 700, 400));

                            // init
                            roomCap.setText("");
                            fromDate.setValue(null);
                            toDate.setValue(null);


                            stage.setResizable(false);
                            stage.show();



                        } catch (IOException e) {
                            e.printStackTrace();
                        }





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
                alert.setContentText("Nombre de personne doit etre numerique!");
                alert.show();
            }

        }
        catch (NullPointerException exception)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("DES CHAMPS VIDES!");
            alert.show();
        }



    }



    public void disconnectClientBtn(ActionEvent event)
    {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Déconnexion");
        alert.setContentText("VOULEZ VOUS VRAIMENT DECONNECTE");
        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == ButtonType.OK)
        {
            try {
                root = FXMLLoader.load(getClass().getResource("../layouts/welcome_screen.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root,700,400);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }



    }


}
