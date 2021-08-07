/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package elementos;

import javafx.scene.layout.Pane;
import lista.ListaCircular;

/**
 *
 * @author Jahir Veliz
 */
public class CircularPane extends Pane{

    private final double radio;
    private double gap;
    private final int nFichas;
    private final int nAnillos;


    public CircularPane(int nFichas, int nAnillos, double radio) {
        this.radio = radio;
        this.nFichas = nFichas;
        this.nAnillos = nAnillos;
    }

    @Override
    protected void layoutChildren() {
        // determina la distancia entre cada nodo de la ruleta
        // le restamos el circulo exterior de la ruleta
        if(getChildren().size() > 0){
            gap = 360d / (getChildren().size()/nAnillos);

            double pos = 0;
            for(int f=0; f<(getChildren().size()/nAnillos); f++){
                double r = radio;

                for(int a=0; a<nAnillos; a++){
                    double x = r * Math.cos(Math.toRadians(pos)) + getWidth() / 2;
                    double y = r * Math.sin(Math.toRadians(pos)) + getHeight() / 2;
                    getChildren().get(f+((getChildren().size()/nAnillos)*a)).relocate(x, y);
                    r+=50;
                }
                pos+=gap;
            }
        }
    }

    public double getRadio() {
        return radio;
    }
    
    public void añadirTodo(ListaCircular<Ficha> lista){
        for(int i=0; i<lista.size(); i++){
            getChildren().add(lista.get(i));
        }
    }    
}
