package layoutsControllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogEvent;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import serverTraitement.ClientUserInterface;
import utils.RoomReservation;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.ResourceBundle;

public class RoomReservationController implements Initializable {

    /* switching scenes */
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Label roomNum;

    @FXML
    Label roomWifi;

    @FXML
    Label roomDate;

    @FXML
    Label roomPrice;
    double prix;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        String msg = RoomReservation.rooms.get(0).getRoomNum();
        roomNum.setText(msg);

        msg = RoomReservation.rooms.get(0).isRoomWifi();
        roomWifi.setText(msg);

        msg = "DE : " + RoomReservation.fromDate  + " A " + RoomReservation.toDate ;
        roomDate.setText(msg);

        prix = RoomReservation.rooms.get(0).getRoomPrice();

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate date1 = LocalDate.parse(RoomReservation.fromDate, dtf);
        LocalDate date2 = LocalDate.parse( RoomReservation.toDate, dtf);
        long daysBetween = Duration.between(date1.atStartOfDay(), date2.atStartOfDay()).toDays();
       // System.out.println ("Days: " + daysBetween);

        prix = prix * daysBetween;

        msg = prix + "DA/"+ daysBetween + " Jours";

        roomPrice.setText(msg);

    }



    public void makeReservation(ActionEvent event) throws RemoteException {

        HashMap<String,Object> reservationDetails = new HashMap<>();

        reservationDetails.put("client_id",RoomReservation.clientID);
        reservationDetails.put("room_id",RoomReservation.rooms.get(0).getRoomId());
        reservationDetails.put("from_date",RoomReservation.fromDate);
        reservationDetails.put("to_date",RoomReservation.toDate);
        reservationDetails.put("room_price",prix);


        try {
            ClientUserInterface stub = (ClientUserInterface) Naming.lookup("rmi://localhost:1099/GHCLIENT");
            boolean isReserved = stub.validateReservation(reservationDetails);


            if(isReserved ==true)
            {

                int resevID  = stub.getLastReservation(RoomReservation.clientID);
                if(resevID != 0)
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("La chambre a été reservée avec succee!\n prendre note de cette numero de reservation : " + resevID);
                    alert.show();
                    alert.setOnCloseRequest(new EventHandler<DialogEvent>() {
                        @Override
                        public void handle(DialogEvent dialogEvent) {
                            //  root = FXMLLoader.load(getClass().getResource("../layouts/client_login_screen.fxml"));
                            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                            stage.close();

                        }
                    });
                }



            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Ressayer plus tards!");
                alert.show();
            }






        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }




    }




}
