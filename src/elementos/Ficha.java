/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 *
 * @author Jahir Veliz
 */
public class Ficha extends StackPane{
    private int valor;
    private int size;
    private Text texto;

    public Ficha(int valor, int size) {
        this.valor = valor;
        this.size = size;
        
        Circle circulo = new Circle(size, Color.ALICEBLUE);
        
        Circle c = new Circle(size, Color.TRANSPARENT);
        circulo.setStroke(Color.BLACK);
        
        texto = new Text(String.valueOf(valor));
        texto.setFont(new Font(1.25*size));
        
        getChildren().addAll(circulo, texto, c);
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
        texto.setText(String.valueOf(valor));
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    @Override
    public String toString() {
        return "Ficha{" + "valor=" + valor + '}';
    }

}
