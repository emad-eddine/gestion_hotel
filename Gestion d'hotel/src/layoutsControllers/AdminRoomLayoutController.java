package layoutsControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import model.Employe;
import model.Room;
import serverTraitement.AdminUserInterface;
import serverTraitement.RoomInterface;
import utils.EditUtils;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminRoomLayoutController implements Initializable, Serializable {


    @FXML
    private AnchorPane roomLayoutPane;

    public void toAddRoomLayour(ActionEvent event)
    {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("../layouts/admin_add_room_layout.fxml"));
            roomLayoutPane.getChildren().removeAll();
            roomLayoutPane.getChildren().setAll(fxml);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    @FXML
    private TableColumn<Room,Integer> TRoomId;

    @FXML
    private TableColumn<Room,String> TRoomNum;

    @FXML
    private TableColumn<Room,Integer> TRoomCap;

    @FXML
    private TableColumn<Room,Boolean> TRoomWifi;

    @FXML
    private TableColumn<Room,Double> TRoomPrice;


    @FXML
    private TableView<Room> roomTable;

    private static final long serialVersionUID = 1L;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        TRoomId.setCellValueFactory(new PropertyValueFactory<Room,Integer>("roomId"));
        TRoomNum.setCellValueFactory(new PropertyValueFactory<Room,String>("RoomNum"));
        TRoomCap.setCellValueFactory(new PropertyValueFactory<Room,Integer>("RoomCap"));
        TRoomWifi.setCellValueFactory(new PropertyValueFactory<Room,Boolean>("RoomWifi"));
        TRoomPrice.setCellValueFactory(new PropertyValueFactory<Room,Double>("RoomPrice"));
        fillRoomTable();

    }

    public void fillRoomTable()
    {
        try {
            RoomInterface stub = (RoomInterface) Naming.lookup("rmi://localhost:1099/ROOM");

            List<Room> rooms = new ArrayList<>();
            rooms = stub.getRooms();

            if(rooms != null)
                roomTable.getItems().setAll(rooms);

        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }




    public void toEditRoomLayout(ActionEvent event)
    {
        try {

            Room room = roomTable.getSelectionModel().getSelectedItem();
            EditUtils.roomToEdit = room;
            Parent fxml = FXMLLoader.load(getClass().getResource("../layouts/admin_edit_room_layout.fxml"));
            roomLayoutPane.getChildren().removeAll();
            roomLayoutPane.getChildren().setAll(fxml);

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void deleteRoom(ActionEvent event)
    {

        try {
            RoomInterface stub = (RoomInterface) Naming.lookup("rmi://localhost:1099/ROOM");

            Room room = roomTable.getSelectionModel().getSelectedItem();

            boolean isDeleted = stub.deleteRoomFromDb(room);

            if(isDeleted)
            {
                try {
                    Parent fxml = FXMLLoader.load(getClass().getResource("../layouts/admin_room_layout.fxml"));
                    roomLayoutPane.getChildren().removeAll();
                    roomLayoutPane.getChildren().setAll(fxml);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
            {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Ressayez plus Tard Il y a Une Reservation pour cette Chambre");
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
