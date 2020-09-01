package org.flota.project.models;

import org.flota.project.patterns.RutaStrategy;

import java.util.ArrayList;

public class CamionRutaStrategy implements RutaStrategy {
    
    @Override
    public Ruta crearRuta(double maxPesos, int maxPuntos, ArrayList<Despacho> rutas) {
        Ruta ruta = new Ruta(maxPesos, maxPuntos);

        for(int i = 0 ; i<rutas.size(); i ++){
            ruta.addPunto(rutas.get(i));
        }
//      //   ruta.addPunto(new Recojo(-77.0844, -12.0561, "Las Garzas", "Urgente", 20.5));
//         ruta.addPunto(new Despacho(-77.083665, -12.054374, "Jazmines 432", "Normal", "1234321", 90.5));
//     //    ruta.addPunto(new Despacho(-77.080250, -12.056479, "Jazmines 670", "Normal", "7879088", 80.5));
//         ruta.addPunto(new Despacho(-77.077908, -12.065964, "Calle Alfonso Ugarte", "Urgente", "7867521", 10.5));
//         ruta.addPunto(new Despacho(-77.078538, -12.073385, "Teodoro Vargas", "Normal", "9043321", 69.1));
//   //      ruta.addPunto(new Despacho(-77.081588, -12.077808, "Las Flores Sur", "Urgente", "54574321", 80.3));
        return ruta;
    }

    @Override
    public Integer maximosPuntosParada() {

        return 7;
    }

    @Override
    public Double maximoPesoPermitido() {

        return 70.8;
    }
}
