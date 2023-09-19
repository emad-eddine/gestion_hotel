import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import layoutsControllers.SplashScreenController;

public class Main extends Application {

    private static final int COUNT_LIMIT = 10;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("layouts/welcome_screen.fxml"));
        primaryStage.setTitle("Gestion D'hotel");
        primaryStage.getIcons().add(new Image("assests/hotel.png"));
        primaryStage.setScene(new Scene(root, 700, 400));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public void init() throws Exception {


        // Perform some heavy lifting (i.e. database start, check for application updates, etc. )

        for (int i = 1; i <= COUNT_LIMIT; i++) {
            double progress =(double) i/10;
            // System.out.println("progress: " +  progress);

            SplashScreenController.statProgressBar.setProgress(progress);
            Thread.sleep(200);
        }

    }


    public static void main(String[] args) {
        System.setProperty("javafx.preloader", MyPreloader.class.getName());

        System.setProperty("java.security.policy","file:/client.policy");


        Application.launch(Main.class, args);
    }
}
