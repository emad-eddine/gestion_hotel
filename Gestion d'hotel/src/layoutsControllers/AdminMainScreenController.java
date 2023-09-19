package layoutsControllers;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminMainScreenController implements Initializable {
    /* switching scenes */
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Pane adminMainScreenPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        changeLayout("../layouts/admin_home_layout.fxml");
    }


    public void toAdminHomeScreen(ActionEvent event)
    {
        changeLayout("../layouts/admin_home_layout.fxml");
    }

    public void toAdminEmployeScreen(ActionEvent event)
    {
        changeLayout("../layouts/admin_employe_layout.fxml");
    }

    public void toAdminRoomScreen(ActionEvent event)
    {
        changeLayout("../layouts/admin_room_layout.fxml");

    }

    public void toAdminProfit(ActionEvent event)
    {
        changeLayout("../layouts/admin_profit_layout.fxml");
    }


    private void changeLayout(String resUrl)
    {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource(resUrl));
            adminMainScreenPane.getChildren().removeAll();
            adminMainScreenPane.getChildren().setAll(fxml);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void disconnectBtn(ActionEvent event)
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
