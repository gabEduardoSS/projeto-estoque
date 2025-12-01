package projeto.estoque.programaestoque;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Utilitarios {
    public void fade(Node widget, String tipo, double duracao){
        double in = 0;
        double out = 0;
        if(tipo.equalsIgnoreCase("in")){
            in = 0;
            out = 1;
        } else if(tipo.equalsIgnoreCase("out")){
            in = 1;
            out = 0;
        }
        FadeTransition fadeOut = new FadeTransition(Duration.seconds(duracao), widget);
        fadeOut.setFromValue(in);
        fadeOut.setToValue(out);
        fadeOut.play();
    }
}
