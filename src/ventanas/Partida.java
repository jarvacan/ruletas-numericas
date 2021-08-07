/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ventanas;

import elementos.CircularPane;
import elementos.Ficha;
import java.util.Random;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import juego.Juego;
import lista.ListaCircular;
/**
 *
 * @author Margarita Chavez
 * @param <E>
 */
public class Partida<E> {
    private Scene escena;
    
    private final int nFichas;
    private final int nAnillos;
    private ListaCircular<Ficha> listaDeFichas;
    private CircularPane cp;
    
    private Text puntaje = new Text();
    private int total;

    private Text apuestaT = new Text();
    private int apuestaI;
    
    private boolean rotado;
    private boolean eliminado;
    
    private int radio = 50;
    
    public Partida(int nFichas, int nAnillos, int apuestaI){
        this.nFichas = nFichas;
        this.nAnillos = nAnillos;
        this.apuestaI = apuestaI;
        apuestaT.setText("Apuesta: " + String.valueOf(apuestaI));
        apuestaT.setFont(new Font(30));  
        
        BorderPane root = new BorderPane();
                
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("ATENCION");
        alert.setContentText("ROTACIONES y ELIMINACIONES deben realizarse siempre de manera INTERCALADA. "
                + "Es decir, después de una rotación debe ejecutarse una eliminación. Y viceversa. ");
        
        listaDeFichas = new ListaCircular();
        BorderPane puntajes = new BorderPane();
        
        cp = new CircularPane(nFichas, nAnillos, radio);
        StackPane stack = new StackPane();
        
        VBox botonesD = new VBox(15);
        VBox botonesI = new VBox(15);
        
        root.setPadding(new Insets(20));
        
        Random rand = new Random();
        
        for (int i=0; i<nAnillos; i++){
            // llenamos de fichas cada anillo
            for(int f=0; f<nFichas; f++){
                Ficha ficha = new Ficha(rand.nextInt(10), 15);
                cp.getChildren().add(ficha);
                listaDeFichas.add(ficha);
            }
            // Se crea un color aleatorio
            float r = rand.nextFloat();
            float g = rand.nextFloat(); 
            float b = rand.nextFloat();
            Color c = new Color(r, g, b, 1.0);
            // Se crean los anillos y se añaden las fichas
            //CircularPane cp = new CircularPane(radio, listaDeListas.get(i));
            // se crean los botones derecho e izquierdo del anillo
            Button btnD = new Button("Rotar Derecha");
            btnD.setId(i+"D");
            btnD.setStyle("-fx-background-color: #" + c.toString().substring(2) +"; ");
            
            Button btnI = new Button("Rotar Izquierda");
            btnI.setId(i+"I");
            btnI.setStyle("-fx-background-color: #" + c.toString().substring(2) +"; ");
            // circulos exteriores
            Circle circulo = new Circle(radio, null);
            circulo.setStroke(c);
            circulo.setStrokeWidth(5.0);
            // se agrega un nuevo anillo al Pane
            //listaCps[i] = cp;
            stack.getChildren().add(circulo);
            botonesD.getChildren().add(btnD);
            botonesI.getChildren().add(btnI);
            // decremento del radio
            radio+=50;
        }
        stack.getChildren().add(cp);
        puntaje.setFont(new Font(30));
        sumaTotal();
        puntajes.setLeft(puntaje);
        puntajes.setRight(apuestaT);

        for(Node ficha: cp.getChildren()){
            ficha.setOnMouseClicked(e -> {
                if(eliminado){
                    alert.setHeaderText("No se puede ELIMINAR 2 veces seguidas");
                    alert.showAndWait();
                }else{
                    int indiceFicha = cp.getChildren().indexOf(ficha);
                    int grupoFicha = indiceFicha % (cp.getChildren().size() / nAnillos);
                    int anillos = cp.getChildren().size() / nAnillos;

                    for(int i=0; i<nAnillos; i++){
                        int k = grupoFicha + (anillos * i) - i;
                        System.out.println(cp.getChildren().get(k));

                        listaDeFichas.remove(k);
                        cp.getChildren().remove(k);
                    }
                    
                    sumaTotal();
                    rotado =  false;
                    eliminado = true;
                    
                    verificarGanador();
                }
            });
        }
        
        for(Node botonD: botonesD.getChildren()){
            botonD.setOnMouseClicked(e -> {
                if(rotado){
                    alert.setHeaderText("No se puede ROTAR 2 veces seguidas");
                    alert.showAndWait();
                }else{                
                    int indiceBtn = Character.getNumericValue(botonD.getId().charAt(0));
                    girar(indiceBtn, "Derecha");
                    
                    sumaTotal();
                    rotado =  true;
                    eliminado = false;
                    
                    verificarGanador();
                }
            });
        }
        
        for(Node botonI: botonesI.getChildren()){
            botonI.setOnMouseClicked(e -> {
                if(rotado){
                    alert.setHeaderText("No se puede ROTAR 2 veces seguidas");
                    alert.showAndWait();
                }else{
                    int indiceBtn = Character.getNumericValue(botonI.getId().charAt(0));
                    girar(indiceBtn, "Izquierda");
                    
                    sumaTotal();
                    rotado =  true;
                    eliminado = false;
                    
                    verificarGanador();
                }
            });
        }
        
        root.setCenter(stack);
        root.setRight(botonesD);
        root.setLeft(botonesI);
        root.setBottom(puntajes);
        
        escena = new Scene(root, 700, 650);
        verificarGanador();
    }

    public Scene getEscena() {
        return escena;
    }

    public void setEscena(Scene escena) {
        this.escena = escena;
    }
    
    public void girar(int indiceBtn, String orientacion){
        int n = cp.getChildren().size() / nAnillos;

        if("Derecha".equals(orientacion)){
            listaDeFichas.rotarDerecha(indiceBtn*n, indiceBtn*n+(n-1));
            
            for(int i=indiceBtn*n; i<=indiceBtn*n+(n-1) ;i++){
                int valorAnt = listaDeFichas.get(i).getValor();
                listaDeFichas.get(i).setValor(valorAnt+1);
            }
            
        }else if("Izquierda".equals(orientacion)){
            listaDeFichas.rotarIzquierda(indiceBtn*n, indiceBtn*n+(n-1));
            
            for(int i=indiceBtn*n; i<=indiceBtn*n+(n-1) ;i++){
                int valorAnt = listaDeFichas.get(i).getValor();
                listaDeFichas.get(i).setValor(valorAnt-1);
            }
        }

        cp.getChildren().clear();
        cp.añadirTodo(listaDeFichas);

        System.out.println(listaDeFichas.slicing(indiceBtn*n, indiceBtn*n+(n-1)));
    }
    
    public void sumaTotal(){
        total = 0;
        for(int i=0; i<listaDeFichas.size() ;i++){
            int valor = listaDeFichas.get(i).getValor();
            total += valor;
        }
        puntaje.setText("Valor: " + String.valueOf(total));
    }
    
    public void verificarGanador(){
        boolean negativo=false;
        for(Ficha ficha: listaDeFichas){
            int valor = ficha.getValor();
            negativo = negativo || (valor<0);
        }
        
        if(apuestaI != total && total>0 && !negativo){
            
        }else{
            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
            a.setTitle("ATENCION");
            
            if (apuestaI == total){
                a.setHeaderText("Ud ha GANADO la partida, FELICIDADES");
            }else if (total == 0 || negativo){
                a.setHeaderText("Ud ha PERDIDO, intentelo de nuevo.");
            }
            
            a.setContentText("Presione ACEPTAR para REINICIAR la partida o CANCELAR para SALIR.");
            a.showAndWait().ifPresent(response  -> {
                if (response == ButtonType.OK){
                    Inicio i = new Inicio();
                    Juego.getStage().setScene(i.getEscena());
                    Juego.getStage().centerOnScreen();
                    Juego.getStage().show();
                }else{
                    Juego.getStage().close();
                }
            });
        }
    } 
}
