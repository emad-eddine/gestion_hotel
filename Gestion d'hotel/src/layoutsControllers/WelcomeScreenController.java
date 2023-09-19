package layoutsControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class WelcomeScreenController {


    /* switching scenes */
    private Stage stage;
    private Scene scene;
    private Parent root;



    public void toAdminLoginScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../layouts/admin_login_screen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void toClientLoginScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../layouts/client_login_screen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void toEmployeLoginScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("../layouts/employe_login_screen.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }



}
