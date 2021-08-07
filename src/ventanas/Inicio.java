/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas;

import java.util.Random;
import java.util.function.UnaryOperator;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import juego.Juego;
/**
 *
 * @author Margarita Chavez
 */
public class Inicio {
    
    private Scene escena;
    
    public Inicio(){
        GridPane root = new GridPane();
        root.setPadding(new Insets(20));
        root.setVgap(10);
        root.setHgap(10);
        
        String[] lista = new String[]{"Fichas", "Anillos", "Apuesta Inicial"};
        TextField[] tfs = new TextField[lista.length];
        
        
        for(int i=0; i<lista.length; i++){
            Label label = new Label(lista[i]+":");
            TextField textField = new TextField ();
            textField.setPromptText(lista[i]);
            textField.setFocusTraversable(false);

            textField.setTextFormatter(validador());
            tfs[i] = textField;
            
            GridPane.setConstraints(label, 0, i);
            GridPane.setConstraints(textField, 1, i);
            
            root.getChildren().addAll(label, textField);
        }
        
        Button btn = new Button("Iniciar");
        GridPane.setConstraints(btn, 1, 4);
        root.getChildren().add(btn);
        
        Text nota = new Text("Nota: por defecto, el juego \n"
                            +"empieza con 2 anillos y 5 fichas");
        nota.setFont(Font.font(null, FontPosture.ITALIC, 12));
        GridPane.setConstraints(nota, 1, 5);
        root.getChildren().add(nota);
        
        btn.setOnAction(e->{
            int[] input = new int[tfs.length];
            for(int i=0; i<tfs.length; i++){
                if(! tfs[i].getText().equals("")){
                    input[i] = Integer.valueOf(tfs[i].getText());
                    System.out.println(tfs[i].getText());
                }else{
                    Random rand = new Random();
                    switch(i){
                        case 0: input[i] = 5;
                                break;
                        case 1: input[i] = 2;
                                break;
                        case 2: input[i] = rand.nextInt(40);
                                break;
                    }
                }
            }            
            Partida p = new Partida(input[0], input[1], input[2]);
            Juego.getStage().setScene(p.getEscena());
            Juego.getStage().centerOnScreen();
            Juego.getStage().show();
        });
        
        escena = new Scene(root, 300, 210);
    }

    public Scene getEscena() {
        return escena;
    }

    public void setEscena(Scene escena) {
        this.escena = escena;
    }
    
    public TextFormatter<String> validador(){
        UnaryOperator<TextFormatter.Change> numberValidationFormatter = change -> {
            if(change.getText().matches("\\d+")){
                return change; //if change is a number
            } else {
                change.setText(""); //else make no change
                change.setRange(    //don't remove any selected text either.
                        change.getRangeStart(),
                        change.getRangeStart()
                );
                return change;
            }
        };
        TextFormatter<String> textFormatter = new TextFormatter<>(numberValidationFormatter);
        return textFormatter;
    }
}
