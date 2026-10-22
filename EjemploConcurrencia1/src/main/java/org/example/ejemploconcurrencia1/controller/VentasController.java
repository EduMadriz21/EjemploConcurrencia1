package org.example.ejemploconcurrencia1.controller;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import org.example.ejemploconcurrencia1.model.CajaCompartida;

public class VentasController {

    @FXML
    private ProgressBar pbVendedor1;
    @FXML
    private ProgressBar pbVendedor2;
    @FXML
    private ProgressBar pbVendedor3;
    @FXML
    private Label lblVendedor1;
    @FXML
    private Label lblVendedor2;
    @FXML
    private Label lblVendedor3;
    @FXML
    private Label lblCantidadVentas;
    @FXML
    private Label lblTotal;
    @FXML
    private TextArea txtRegistro;
    @FXML
    private Button btnIniciar;

    /**
     * Objeto compartido por todos los hilos
     */
    private CajaCompartida cajaCompartida;

    @FXML
    private void iniciarVentas() {
        //Solo cuando se inicia una venta, el objeto de caja compartida se debe crear
        cajaCompartida = new CajaCompartida();
        // Cada vez que se inicia el proceso de ventas, se reinicia la interfaz
        reiniciarInterfaz();

        //Evitamos que el usuario pueda iniciar el proceso de ventas nuevamente mientras este en ejecución
        btnIniciar.setDisable(true);

        /**
         * Crear los tres hilos
         */

        Thread vendedir1 = new Thread(() -> {
            procesarVentas("Vendedor 1", pbVendedor1, lblVendedor1);
        });

        Thread vendedir2 = new Thread(() -> {
            procesarVentas("Vendedor 2", pbVendedor2, lblVendedor2);
        });

        Thread vendedir3 = new Thread(() -> {
            procesarVentas("Vendedor 3", pbVendedor3, lblVendedor3);
        });

        // Iniciar los hilos
        vendedir1.start();
        vendedir2.start();
        vendedir3.start();


        /**
         * Crear un Thread adicional encargado de esperar que los tres vendedores terminen
         */
        Thread supervisor1 = new Thread(() -> {

            try {
                vendedir1.join();
                vendedir2.join();
                vendedir3.join();

                /*
                 * Una vez que los tres vendedores han terminado, actualizamos la interfaz con los resultados finales
                 */
                Platform.runLater(() -> {
                    txtRegistro.appendText("---- Procesamiento finalizado ----");
                    btnIniciar.setDisable(false);
                });

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
        supervisor1.start();
    }

    /**
     * Simula las ventas realizada por los vendedores
     */
    private void procesarVentas(String vendedor, ProgressBar pbVendedor, Label lblVendedor) {
        for (int i = 0; i < 5; i++) {
            try {
                Thread.sleep(1000);
                cajaCompartida.registrarRecaudado(100);

                int numeroVenta = i;
                double progreso = numeroVenta / 5.0;

                Platform.runLater(() -> {
                    pbVendedor.setProgress(progreso);
                    lblVendedor.setText("Procesando venta " + numeroVenta + " de 5");
                    txtRegistro.appendText((vendedor + " registro una venta de C$ 100"));
                    actualizarTotales();
                });
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Platform.runLater(() -> {
            lblVendedor.setText("Proceso completado");
        });
    }

    private void actualizarTotales() {
        lblCantidadVentas.setText("Cantidad de ventas: " + cajaCompartida.getCantidadVentas());
        lblTotal.setText("Total recaudado: C$ " + cajaCompartida.getTotalRecaudado());
    }

    private void reiniciarInterfaz() {
        pbVendedor1.setProgress(0);
        pbVendedor2.setProgress(0);
        pbVendedor3.setProgress(0);
        lblVendedor1.setText("Esperando...");
        lblVendedor2.setText("Esperando...");
        lblVendedor3.setText("Esperando...");
        lblCantidadVentas.setText("Cantidad de ventas: 0");
        lblTotal.setText("Total recaudado: C$ 0");
        txtRegistro.clear();
    }

    @FXML
    private void limpiar() {
        pbVendedor1.setProgress(0);
        pbVendedor2.setProgress(0);
        pbVendedor3.setProgress(0);
        lblVendedor1.setText("Esperando...");
        lblVendedor2.setText("Esperando...");
        lblVendedor3.setText("Esperando...");
        lblCantidadVentas.setText("Cantidad de ventas: 0");
        lblTotal.setText("Total recaudado: C$ 0");
        txtRegistro.clear();

    }
}





