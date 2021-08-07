package juego;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import ventanas.Inicio;
/**
 *
 * @author Jahir Veliz
 */
public class Juego extends Application {
    static Stage stage;
    
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        stage.getIcons().add(new Image("https://cdn1.iconfinder.com/data/icons/sin-city-memories/128/roulette-512.png"));
        stage.setTitle("RULETAS NUMERICAS");
        Inicio i = new Inicio();
        stage.setScene(i.getEscena());
        stage.show();
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage() {
        return stage;
    }
}
