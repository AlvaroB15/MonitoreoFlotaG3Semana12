package org.flota.project.patterns;

import java.util.ArrayList;

import org.flota.project.models.Ruta;
import org.flota.project.models.Despacho;

public class Context {
    private RutaStrategy strategy;

    public Context(){    
    }

    public Context(RutaStrategy strategy){
        this.strategy = strategy;
    }

    public void setStrategy(RutaStrategy strategy) {
        this.strategy = strategy;
    }

    public Ruta crearRuta(double maxPesos, int maxPuntos, ArrayList<Despacho> rutas){
        return this.strategy.crearRuta(maxPesos, maxPuntos, rutas);
    }

    public double validarPeso(){
        return this.strategy.maximoPesoPermitido();
    }

    public Integer maxPuntos(){
        return this.strategy.maximosPuntosParada();
    }
}
