package org.flota.project.models;

import com.esri.arcgisruntime.geometry.PointCollection;
import com.esri.arcgisruntime.geometry.Polyline;
import com.esri.arcgisruntime.symbology.SimpleLineSymbol;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import javax.swing.JOptionPane;

import com.esri.arcgisruntime.mapping.view.GraphicsOverlay;
import com.esri.arcgisruntime.geometry.Point;
import com.esri.arcgisruntime.geometry.SpatialReferences;
import com.esri.arcgisruntime.mapping.view.Graphic;
import com.esri.arcgisruntime.symbology.SimpleMarkerSymbol;
import org.flota.project.patterns.Context;

import java.util.ArrayList;
import java.util.Arrays;

public class Ventana extends Application {

    private Mapa mapaBase;

    @Override
    public void start(Stage stage) throws Exception {

        // set the title and size of the stage and show it
        stage.setTitle("Sistema de Monitoreo de Vehiculos");
        stage.setWidth(800);
        stage.setHeight(700);
        stage.show();





        // create a JavaFX scene with a stack pane as the root node and add it to the scene
        BorderPane stackPane = new BorderPane();
        Scene scene = new Scene(stackPane);
        stage.setScene(scene);


        Button botonCamionRuta = new Button("Right");
        botonCamionRuta.setText("botonCamionRuta");
        BorderPane.setAlignment(botonCamionRuta, Pos.CENTER_LEFT );

        Button botonMotoRuta = new Button("Left");
        botonMotoRuta.setText("botonMotoRuta");
        BorderPane.setAlignment(botonMotoRuta, Pos.CENTER_RIGHT );

        stackPane.setLeft(botonCamionRuta);
        stackPane.setRight(botonMotoRuta);


        botonCamionRuta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // stage.hide();
                ventanaRuta("camion");
                // stage.show();
            }
        });
        
        botonMotoRuta.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                // stage.hide();
                // ventanaMotoRuta();
                ventanaRuta("moto");
                // stage.show();
            }
        });

    }

    public void ventanaRuta(String tipo) {

        Stage stage = new Stage();



        stage.setTitle("Sistema de Monitoreo de Vehiculos");
        stage.setWidth(800);
        stage.setHeight(700);
        stage.show();

        // create a JavaFX scene with a stack pane as the root node and add it to the scene
        StackPane stackPane = new StackPane();
        Scene scene = new Scene(stackPane);
        stage.setScene(scene);

        // create a MapView to display the map and add it to the stack pane
        /*
        this.mapaBase = new Mapa();
        this.mapaBase.imprimeCoordenadasActual();
        stackPane.getChildren().add(this.mapaBase.getMapView());
        */
        //mapaBase = new Mapa();
        //mapaBase.imprimeCoordenadasActual();
        //stackPane.getChildren().add(mapaBase.getMapView());
        FachadaMapa facade = new FachadaMapa();
        mapaBase = facade.mostrarMapa(stackPane);



        // create a graphics overlay for displaying different geometries as graphics
        GraphicsOverlay graphicsOverlay = new GraphicsOverlay();
        mapaBase.getMapView().getGraphicsOverlays().add(graphicsOverlay);

        // create a point geometry
        Point point = new Point(-77.0844, -12.0561, SpatialReferences.getWgs84());
        Graphic pointGraphic = new Graphic(point, new SimpleMarkerSymbol(SimpleMarkerSymbol.Style.DIAMOND, 0xFF0000FF, 14));

        graphicsOverlay.getGraphics().addAll(Arrays.asList(pointGraphic));
        //graphicsOverlay.getGraphics().remove(point);
        //stackPane.getChildren().addAll(mapaBase.getMapView());


        /* Context */
        
        ArrayList<Despacho> rutas = new ArrayList<>();
        
        String opcion = JOptionPane.showInputDialog("Que tipo de vehiculo quiere agregar punto (camion / moto)");
        boolean condition = false;
        System.out.println(opcion);

        Context context = new Context();

        do {
            if(opcion.equals("camion")){
                // hacer otra validacion si es Recojo o Despacho, copy y paste de la misma forma a las de abajo xd
                double lat = Double.parseDouble(JOptionPane.showInputDialog("Latitu?"));
                double lng = Double.parseDouble(JOptionPane.showInputDialog("Longitud?"));
                // renombra segun se deberi hacer xd
                String n = JOptionPane.showInputDialog("Longitud?");
                String n1 = JOptionPane.showInputDialog("Longitud?");
                String dni = JOptionPane.showInputDialog("DNI?");
                double peso = Double.parseDouble(JOptionPane.showInputDialog("Peso?"));
                Despacho d = new Despacho(lat,lng,n,n1,dni,peso);

                rutas.add(d);

            }else if(opcion == "moto"){

            }else{
                JOptionPane.showMessageDialog(null, "Escriba los datos segun lo establecido");
            } 
            
            String name = JOptionPane.showInputDialog("Desea continuar (S/N)?");
            if( name == "S" || name == "s" || name == "si" ||name == "SI" || name == "Si" ){
                condition = true;
            }else  {// se puede poner para el no, pero no es necesario creo
                // solo he hecho el de camion y de frente
                context.setStrategy(new CamionRutaStrategy());
            }
            // se deberia hacer de forma ternaria mas rapido y bontia
        } while (condition);

        
    // -----------------valida el de moto--------------------------------------------------------------------------------------------------
       
        // if(tipo == "camion"){
        //     context.setStrategy(new CamionRutaStrategy());
        // }else if(tipo == "moto"){
        //     context.setStrategy(new MotoRutaStrategy());
        // }
        
        double maxPesos = context.validarPeso();

        int maxPuntos = context.maxPuntos();

        Ruta ruta = context.crearRuta(maxPesos, maxPuntos, rutas );


        PointCollection polylinePoints = new PointCollection(SpatialReferences.getWgs84());
        polylinePoints.addAll(ruta.getPoints());

        Polyline polyline = new Polyline(polylinePoints);
        SimpleLineSymbol polylineSymbol = new SimpleLineSymbol(SimpleLineSymbol.Style.SOLID, 0xFF00FF00, 3.0f);
        Graphic polylineGraphic = new Graphic(polyline, polylineSymbol);
        graphicsOverlay.getGraphics().add(polylineGraphic);


        JSONExportVisitor jsonVisitor = new JSONExportVisitor();
        for (Punto punto : ruta.getPuntos()){
            punto.accept(jsonVisitor);
        }
        
        System.out.println("\n\t\t\tPuntos en formato XML\n");
        
        XMLExportVisitor xmlVisitor = new XMLExportVisitor();
        for (Punto punto : ruta.getPuntos()){
            punto.accept(xmlVisitor);
        }
        System.out.println();


        // //  Clonacion de MapaBase
        // Mapa mapaBase2 = (Mapa)mapaBase.copiar();

        // mapaBase2.imprimeCoordenadasActual();
        // stackPane.getChildren().add(mapaBase2.getMapView());

        // stage.show();

    }

}