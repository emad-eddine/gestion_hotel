package layoutsControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import serverTraitement.AdminUserInterface;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class AdminProfitController implements Initializable {


    @FXML
    private DatePicker fromDate;

    @FXML
    private DatePicker toDate;

    @FXML
    private Label revenue;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        revenue.setText("-/DZD");
    }


    public void printRevenue(ActionEvent event)
    {
        String fromDateString = fromDate.getValue().toString();
        String toDateString = toDate.getValue().toString();

        try {
            AdminUserInterface stub = (AdminUserInterface) Naming.lookup("rmi://localhost:1099/GH");

            double revenuePrice = stub.getRevenue(fromDateString,toDateString);

            if(revenuePrice != 0)
            {
                revenue.setText(revenuePrice + "/DZD");
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
