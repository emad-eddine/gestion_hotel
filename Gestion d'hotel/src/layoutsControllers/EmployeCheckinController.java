package layoutsControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import serverTraitement.EmployeUserInterface;
import utils.UserLogins;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.regex.Pattern;

public class EmployeCheckinController {


    @FXML
    private TextField reservID;


    @FXML
    private Label nomClient;

    @FXML
    private Label roomNumber;

    @FXML
    private Label reservDates;

    @FXML
    private Label price;

    @FXML
    private DatePicker checkInDate;

    HashMap<String,Object> reservationDetails = new HashMap<>();
    int roomReservationId =0;

    public void checkRoomReservation(ActionEvent event)
    {


        if((!reservID.getText().equals("")) && Pattern.matches("[0-9]+",reservID.getText()))
        {
            roomReservationId = Integer.parseInt(reservID.getText());
            try
            {
                EmployeUserInterface stub = (EmployeUserInterface) Naming.lookup("rmi://localhost:1099/GHADMINEMPLOYE");



                reservationDetails = stub.getReservatinoDetails(Integer.parseInt(reservID.getText()));

                if(reservationDetails != null)
                {
                    nomClient.setText("Nom du client : " + reservationDetails.get("nomCLient"));
                    roomNumber.setText("Numero de chambre : " + reservationDetails.get("roomNumber"));
                    reservDates.setText("De " + reservationDetails.get("from_date") + " A " + reservationDetails.get("to_date"));
                    price.setText("Prix : " + reservationDetails.get("price") + " DZD");
                }
                else
                    {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("La réservation n'exite pas ou Déja checked-in");
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
        else
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Numero de reservation non valide");
            alert.show();
        }
    }



    public void checkCLientIn(ActionEvent event)
    {
        if(reservationDetails != null)
        {
            String checkinDate = checkInDate.getValue().toString();

            reservationDetails.put("checkInDate",checkinDate);
            reservationDetails.put("reservId",roomReservationId);
            reservationDetails.put("employeId", UserLogins.USER_ID);

            try {
                EmployeUserInterface stub = (EmployeUserInterface) Naming.lookup("rmi://localhost:1099/GHADMINEMPLOYE");


                boolean isClientIn = stub.checkClientIn(reservationDetails);

                if(isClientIn)
                {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Client Checked-IN avec succee !");
                    alert.show();
                }
                else
                {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setContentText("Ressayez plus tards!!!");
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
