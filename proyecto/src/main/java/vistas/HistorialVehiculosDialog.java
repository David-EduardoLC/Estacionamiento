/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vistas;



import clasesjava.VehiculoHistorialDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HistorialVehiculosDialog extends JDialog {

    private final JTable tabla;
    private final DefaultTableModel modelo;
    private static final DateTimeFormatter TF = DateTimeFormatter.ofPattern("HH:mm:ss");

    public HistorialVehiculosDialog(Window owner, int idPersona, String titulo, List<VehiculoHistorialDTO> items) {
        super(owner, titulo, ModalityType.APPLICATION_MODAL);

        modelo = new DefaultTableModel(new Object[]{
                "ID Vehículo", "Placa", "Hora Entrada", "Hora Salida", "ID Espacio"
        }, 0);
        tabla = new JTable(modelo);
        JScrollPane sp = new JScrollPane(tabla);

        cargar(items);

        JPanel south = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cerrar = new JButton("Cerrar");
        cerrar.addActionListener(e -> dispose());
        south.add(cerrar);

        setLayout(new BorderLayout(10, 10));
        add(new JLabel("Historial de vehículos | Persona #" + idPersona, JLabel.CENTER), BorderLayout.NORTH);
        add(sp, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);

        setSize(720, 420);
        setLocationRelativeTo(owner);
    }

    private void cargar(List<VehiculoHistorialDTO> items) {
        modelo.setRowCount(0);
        if (items == null || items.isEmpty()) {
            modelo.addRow(new Object[]{"(sin registros)", "", "", "", ""});
            tabla.setEnabled(false);
            return;
        }
        for (VehiculoHistorialDTO it : items) {
            modelo.addRow(new Object[]{
                    it.getIdVehiculo(),
                    it.getPlaca(),
                    it.getHoraEnt()   != null ? TF.format(it.getHoraEnt())   : "",
                    it.getHoraSalida()!= null ? TF.format(it.getHoraSalida()): "",
                    it.getIdEspacio()
            });
        }
    }
}
