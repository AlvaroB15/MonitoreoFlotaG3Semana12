package org.flota.project.models;

import org.flota.project.patterns.RutaStrategy;

import java.util.ArrayList;

public class MotoRutaStrategy implements RutaStrategy {
    @Override
    public Ruta crearRuta(double maxPesos, int maxPuntos,  ArrayList<Despacho> rutas) {
        Ruta ruta = new Ruta(maxPesos, maxPuntos);
        // ruta.addPunto(new Recojo(-77.0844, -12.0561, "Jazmines", "Normal", 9.0));
        // ruta.addPunto(new Despacho(-77.084774, -12.055502, "Las Torres", "Normal", "7874321", 8.5));
        // ruta.addPunto(new Despacho(-77.085750, -12.057957, "Los Suaces", "Urgente", "3324321", 2.5));
        // ruta.addPunto(new Despacho(-77.084760, -12.058826, "Av. Sol 434", "Normal", "7879876", 3.1));
        // ruta.addPunto(new Recojo(-77.086194, -12.061448, "Calle 22", "Urgente", 4.5));
        // ruta.addPunto(new Despacho(-77.082715, -12.061067, 3.2));
        // ruta.addPunto(new Despacho(-77.081694, -12.067080, 4.1));
        // ruta.addPunto(new Recojo(-77.083207, -12.072865, 5.6));
        // ruta.addPunto(new Despacho(-77.084516, -12.078045, 3.3));
        // ruta.addPunto(new Despacho(-77.081588, -12.077808, 2.2));
        return ruta;
    }

    @Override
    public Integer maximosPuntosParada() {

        return 3;
    }

    @Override
    public Double maximoPesoPermitido() {

        return 5.5;
    }

}
