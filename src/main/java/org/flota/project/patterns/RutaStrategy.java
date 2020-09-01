package org.flota.project.patterns;

import java.util.ArrayList;

import org.flota.project.models.Despacho;
import org.flota.project.models.Ruta;


public interface RutaStrategy {
    Ruta crearRuta(double maxPesos, int maxPuntos, ArrayList<Despacho> rutas);

    public Integer maximosPuntosParada();

    public Double maximoPesoPermitido();
}
