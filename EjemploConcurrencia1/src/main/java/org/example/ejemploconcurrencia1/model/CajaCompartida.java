package org.example.ejemploconcurrencia1.model;

public class CajaCompartida {

    private int cantidadVentas = 0;
    private int totalRecaudado = 0;


    //// Sin synchronized pueden ocurrir condiciones de carrera y resultados incorrectos al acceder varios hilos al mismo tiempo.
    public synchronized void registrarRecaudado(int monto) {
        cantidadVentas++;
        totalRecaudado += monto;
    }

    public synchronized int getCantidadVentas() {
        return cantidadVentas;
    }

    public synchronized int getTotalRecaudado() {
        return totalRecaudado;
    }

}
