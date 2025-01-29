import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jasypt.util.text.BasicTextEncryptor;

public class    Starter extends Application {
    public static void main(String[] args) {

        String key = "12345";

        BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();
        basicTextEncryptor.setPassword(key);

        String password = "danuka123";

        String encrypt = basicTextEncryptor.encrypt(password);

        System.out.println(encrypt);

        String decrypt = basicTextEncryptor.decrypt(encrypt);

        System.out.println(decrypt);


        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("view/LoginForm.fxml"))));
        stage.show();
    }
}
