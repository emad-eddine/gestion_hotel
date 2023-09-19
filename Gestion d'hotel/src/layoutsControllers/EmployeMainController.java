package layoutsControllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import utils.UserLogins;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.ResourceBundle;

public class EmployeMainController implements Initializable {
    @FXML
    private Label timeLabel;

    @FXML
    private Label dateLabel;


    @FXML
    private Label employeName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        statTime();
        employeName.setText(UserLogins.USER_NAME);
    }

    public void statTime() {

        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    while (true) {
                        java.util.Date d = new java.util.Date();

                        SimpleDateFormat sdft = new SimpleDateFormat("hh:mm:ss a");
                        SimpleDateFormat sdfd = new SimpleDateFormat("dd/MM/YYYY");

                        String time = sdft.format(d);

                        Calendar cal = Calendar.getInstance();
                        String date = sdfd.format(cal.getTime());


                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                timeLabel.setText(time);
                                dateLabel.setText(date);
                            }
                        });


                        Thread.sleep(1000);
                    }

                } catch (Exception e) {
                }
            }
        }).start();
    }
}
