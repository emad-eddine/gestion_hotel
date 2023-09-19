package layoutsControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import model.Employe;
import serverTraitement.AdminUserInterface;
import utils.EditUtils;

import java.io.IOException;
import java.io.NotSerializableException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminEmployeCotroller implements Initializable, Serializable {

    private static final long serialVersionUID = 1L;

    @FXML
    private TableView<Employe> employeTable;

    @FXML
    private TableColumn<Employe,Integer> TEmployeId;

    @FXML
    private TableColumn<Employe,String> TEmployeName;

    @FXML
    private TableColumn<Employe,String> TEmployeBirth;

    @FXML
    private TableColumn<Employe,String> TEmployeAddr;

    @FXML
    private TableColumn<Employe,String> TEmployePhone;

    @FXML
    private TableColumn<Employe,String> TEmployeUserName;

    @FXML
    private TableColumn<Employe,String> TEmployePassword;


    @FXML
    private AnchorPane employeLayoutPane;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {

        TEmployeId.setCellValueFactory(new PropertyValueFactory<Employe,Integer>("employeId"));

        TEmployeName.setCellValueFactory(new PropertyValueFactory<Employe,String>("employeName"));

        TEmployeBirth.setCellValueFactory(new PropertyValueFactory<Employe,String>("employeBitrh"));

        TEmployeAddr.setCellValueFactory(new PropertyValueFactory<Employe,String>("employeAddr"));

        TEmployePhone.setCellValueFactory(new PropertyValueFactory<Employe,String>("employePhone"));

        TEmployeUserName.setCellValueFactory(new PropertyValueFactory<Employe,String>("employeUserName"));

        TEmployePassword.setCellValueFactory(new PropertyValueFactory<Employe,String>("employePassword"));

        fillEmployeTable();


    }


    public void fillEmployeTable()
    {
        try {
            AdminUserInterface stub = (AdminUserInterface) Naming.lookup("rmi://localhost:1099/GH");

            List<Employe> employes1 = new ArrayList<>();
            employes1 = stub.getEmployeInfo(employes1);

            if(employes1 != null)
                employeTable.getItems().setAll(employes1);

        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void toAdminAddEmployeLayout(ActionEvent event)
    {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource("../layouts/admin_add_employe_layout.fxml"));
            employeLayoutPane.getChildren().removeAll();
            employeLayoutPane.getChildren().setAll(fxml);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void toAdminEditEmployeLayout(ActionEvent event)
    {
        try {

            Employe employe = employeTable.getSelectionModel().getSelectedItem();
            EditUtils.employeToEdit = employe;
            Parent fxml = FXMLLoader.load(getClass().getResource("../layouts/admin_edit_employe_layout.fxml"));
            employeLayoutPane.getChildren().removeAll();
            employeLayoutPane.getChildren().setAll(fxml);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void deleteEmploye(ActionEvent event)
    {
        ObservableList<Employe> selectedEmploye = employeTable.getSelectionModel().getSelectedItems();
        if(selectedEmploye != null )
        {
            int selectedEmployeId = selectedEmploye.get(0).getEmployeId();
            try {
                AdminUserInterface stub = (AdminUserInterface) Naming.lookup("rmi://localhost:1099/GH");


                boolean isDeleted = stub.deleteEmploye(selectedEmployeId);

                if(isDeleted == true)
                {
                   // System.out.println("employe supprimer");
                    try {
                        Parent fxml = FXMLLoader.load(getClass().getResource("../layouts/admin_employe_layout.fxml"));
                        employeLayoutPane.getChildren().removeAll();
                        employeLayoutPane.getChildren().setAll(fxml);

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



    }

}
