package layoutsControllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;

public class SplashScreenController implements Initializable {

    @FXML
    private Label progress;

    @FXML
    private ProgressBar progressBar;

    private double progressCount  = 0;

    public static Label label;

    public static ProgressBar statProgressBar;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        label = progress ;
        statProgressBar = progressBar;
    }

}
