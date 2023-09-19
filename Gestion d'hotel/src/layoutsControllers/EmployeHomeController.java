package layoutsControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class EmployeHomeController implements Initializable {

    /* switching scenes */
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Pane EmployeMainScreenPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        changeLayout("../layouts/employe_main_layout.fxml");
    }

    public void toEmployeCheckIn(ActionEvent event)
    {
        changeLayout("../layouts/employe_checkin_layout.fxml");
    }
    public void toEmployeCheckOut(ActionEvent event)
    {
        changeLayout("../layouts/employe_checkout_layout.fxml");
    }
    public void toEmployeMainScreen(ActionEvent event)
    {
        changeLayout("../layouts/employe_main_layout.fxml");
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

    private void changeLayout(String resUrl)
    {
        try {
            Parent fxml = FXMLLoader.load(getClass().getResource(resUrl));
            EmployeMainScreenPane.getChildren().removeAll();
            EmployeMainScreenPane.getChildren().setAll(fxml);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
