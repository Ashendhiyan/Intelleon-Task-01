package lk.intelleon.springbootrestfulwebservices;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.stage.Stage;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

@SpringBootApplication
public class SpringbootRestfulWebservicesApplication extends Application {
    public static ConfigurableApplicationContext applicationContext;
    public static Parent rootNode;
    public static Stage stage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        applicationContext = SpringApplication.run(SpringbootRestfulWebservicesApplication.class);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        loader.setControllerFactory(applicationContext::getBean);
        try {
            rootNode = loader.load();
        } catch (IOException e) {
            // Handle FXML loading error
            e.printStackTrace();
        }
        Scene scene = new Scene(rootNode, 1000, 600, false, SceneAntialiasing.BALANCED);
        stage.setTitle("Login Page");
        stage.setScene(scene);
        stage.show();
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

}
