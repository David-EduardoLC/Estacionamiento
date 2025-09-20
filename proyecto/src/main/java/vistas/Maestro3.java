/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vistas;
import clasesjava.Maestro;
import clasesjava.MaestroDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
/**
 *
 * @author MSI A15
 */
public class Maestro3 extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Maestro3.class.getName());

    // Modelo de la tabla
    private DefaultTableModel modelo;

    public Maestro3() {
        initComponents();
        // Configurar modelo de tabla (usa el del GUI o lo reemplaza)
        modelo = (DefaultTableModel) jTable1.getModel();
        // Sugerido: limpiar y definir cabeceras por si NetBeans generó filas dummy
        modelo.setRowCount(0);
        jTable1.setAutoCreateRowSorter(true); // ordenar columnas
        // Cargar datos al abrir
        cargar();
        // Tamaño y centrado opcional
        setSize(900, 540);
        setLocationRelativeTo(null);
    }

    /* ===================== LÓGICA CRUD ===================== */

    private void cargar() {
        modelo.setRowCount(0);
        List<Maestro> maestros = MaestroDAO.listarTodos();
        for (Maestro m : maestros) {
            modelo.addRow(new Object[]{
                    m.getIdPersona(),
                    m.getNombre(),
                    m.getApellido(),
                    m.getClaveEmpleado()
            });
        }
        ajustarAnchos();
    }

    private void ajustarAnchos() {
        var cm = jTable1.getColumnModel();
        if (cm.getColumnCount() >= 4) {
            cm.getColumn(0).setPreferredWidth(90);   // ID
            cm.getColumn(1).setPreferredWidth(220);  // Nombre
            cm.getColumn(2).setPreferredWidth(220);  // Apellido
            cm.getColumn(3).setPreferredWidth(120);  // No. Empleado
        }
        jTable1.setAutoResizeMode(JTable.AUTO_RESIZE_SUBSEQUENT_COLUMNS);
    }

    private Integer idSeleccionado() {
        int row = jTable1.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un registro");
            return null;
        }
        // Si hay sorter activo, convertir índice de vista a modelo
        int modelRow = jTable1.convertRowIndexToModel(row);
        Object val = modelo.getValueAt(modelRow, 0);
        if (val instanceof Integer i) return i;
        if (val instanceof Number n) return n.intValue();
        return Integer.valueOf(val.toString());
    }

    private void buscar() {
        String t = jTextField1.getText().trim();
        modelo.setRowCount(0);
        for (Maestro m : MaestroDAO.buscar(t)) {
            modelo.addRow(new Object[]{
                    m.getIdPersona(),
                    m.getNombre(),
                    m.getApellido(),
                    m.getClaveEmpleado()
            });
        }
        ajustarAnchos();
    }

    private void crear() {
        try {
            String nombre = JOptionPane.showInputDialog(this, "Nombre:");
            if (nombre == null) return;

            String apP = JOptionPane.showInputDialog(this, "Apellido Paterno:");
            if (apP == null) return;

            String apM = JOptionPane.showInputDialog(this, "Apellido Materno (opcional):");
            if (apM == null) apM = "";

            String tel = JOptionPane.showInputDialog(this, "Teléfono (opcional):");
            if (tel == null) tel = "";

            String noEmp = JOptionPane.showInputDialog(this, "Número de empleado:");
            if (noEmp == null) return;

            Maestro m = new Maestro(0, nombre, apP, Integer.parseInt(noEmp), /* depto en POJO (no BD) */ "");
            if (MaestroDAO.crear(m, apM, tel)) {
                JOptionPane.showMessageDialog(this, "Maestro creado");
                cargar();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo crear");
            }
        } catch (HeadlessException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void editar() {
        Integer id = idSeleccionado();
        if (id == null) return;

        Maestro m = MaestroDAO.obtenerPorId(id);
        if (m == null) {
            JOptionPane.showMessageDialog(this, "No encontrado");
            return;
        }

        try {
            String nombre = JOptionPane.showInputDialog(this, "Nombre:", m.getNombre());
            if (nombre == null) return;

            String apP = JOptionPane.showInputDialog(this, "Apellido Paterno:", m.getApellido());
            if (apP == null) return;

            String apM = JOptionPane.showInputDialog(this, "Apellido Materno (opcional):", "");
            if (apM == null) apM = "";

            String tel = JOptionPane.showInputDialog(this, "Teléfono (opcional):", "");
            if (tel == null) tel = "";

            String noEmp = JOptionPane.showInputDialog(this, "Número de empleado:", m.getClaveEmpleado());
            if (noEmp == null) return;

            m = new Maestro(id, nombre, apP, Integer.parseInt(noEmp), "");
            if (MaestroDAO.actualizar(m, apM, tel)) {
                JOptionPane.showMessageDialog(this, "Actualizado");
                cargar();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar");
            }
        } catch (HeadlessException | NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void eliminar() {
        Integer id = idSeleccionado();
        if (id == null) return;

        int ok = JOptionPane.showConfirmDialog(this, "¿Eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            if (MaestroDAO.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "Eliminado");
                cargar();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar");
            }
        }
    }
    
    private Integer idSeleccionadoModelo() {
    int row = jTable1.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Selecciona un registro");
        return null;
    }
    int modelRow = jTable1.convertRowIndexToModel(row);
    Object val = jTable1.getModel().getValueAt(modelRow, 0);
    if (val instanceof Integer i) return i;
    if (val instanceof Number n) return n.intValue();
    return Integer.valueOf(val.toString());
}

private void mostrarHistorialSeleccionado() {
    Integer id = idSeleccionadoModelo();
    if (id == null) return;

    java.util.List<clasesjava.VehiculoHistorialDTO> items =
            clasesjava.VehiculoDAO.historialPorPersona(id);

    HistorialVehiculosDialog dlg = new HistorialVehiculosDialog(
            this, id, "Historial | Maestro", items);
    dlg.setVisible(true);
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnCrear = new javax.swing.JButton();
        BtnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnRefrescar = new javax.swing.JButton();
        btnHistorial = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID persona", "Nombre", "Apellido P", "No. Empleado"
            }
        ));
        jScrollPane1.setViewportView(jTable1);

        btnCrear.setText("Crear");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });

        BtnEditar.setText("Editar");
        BtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnRefrescar.setText("Refrescar");
        btnRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrescarActionPerformed(evt);
            }
        });

        btnHistorial.setText("Historial vehiculos");
        btnHistorial.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistorialActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 739, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.DEFAULT_SIZE, 94, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCrear)
                        .addGap(18, 18, 18)
                        .addComponent(BtnEditar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar)
                        .addGap(18, 18, 18)
                        .addComponent(btnHistorial)
                        .addGap(18, 18, 18)
                        .addComponent(btnRefrescar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrear)
                    .addComponent(BtnEditar)
                    .addComponent(btnEliminar)
                    .addComponent(btnHistorial)
                    .addComponent(btnRefrescar))
                .addContainerGap(8, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
        buscar();
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        // TODO add your handling code here:
        crear();
    }//GEN-LAST:event_btnCrearActionPerformed

    private void BtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditarActionPerformed
        // TODO add your handling code here:
        editar();
    }//GEN-LAST:event_BtnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        eliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrescarActionPerformed
        // TODO add your handling code here:
        cargar();
    }//GEN-LAST:event_btnRefrescarActionPerformed

    private void btnHistorialActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistorialActionPerformed
        // TODO add your handling code here:
        mostrarHistorialSeleccionado();
    }//GEN-LAST:event_btnHistorialActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Look & Feel Nimbus */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Maestro3().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnEditar;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnHistorial;
    private javax.swing.JButton btnRefrescar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
