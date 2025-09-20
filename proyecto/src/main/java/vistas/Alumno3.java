/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vistas;
import clasesjava.Alumno;
import clasesjava.AlumnoDAO;
import clasesjava.VehiculoDAO;
import clasesjava.VehiculoHistorialDTO;
import java.awt.HeadlessException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;

/**
 *
 * @author MSI A15
 */
public class Alumno3 extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Alumno3.class.getName());
    private final DefaultTableModel modelo;
    /**
     * Creates new form Alumno3
     */
    public Alumno3() {
        initComponents();
        // Toma el modelo que ya definiste en el GUI builder
        modelo = (DefaultTableModel) tablaDatos.getModel();
        // Cargar datos al abrir
        cargar();
    }

    // ===================== LÓGICA =====================

    private void cargar() {
        modelo.setRowCount(0);
        List<Alumno> alumnos = AlumnoDAO.listarTodos();
        for (Alumno a : alumnos) {
            modelo.addRow(new Object[]{
                a.getIdPersona(),
                a.getNombre(),
                a.getApellido(),
                a.getNumeroControl(), // matricula
                a.getSemestre()
            });
        }
    }

    private void buscar() {
        String t = buscar.getText().trim();
        modelo.setRowCount(0);
        for (Alumno a : AlumnoDAO.buscar(t)) {
            modelo.addRow(new Object[]{
                a.getIdPersona(),
                a.getNombre(),
                a.getApellido(),
                a.getNumeroControl(),
                a.getSemestre()
            });
        }
    }

    private Integer idSeleccionado() {
        int row = tablaDatos.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un registro");
            return null;
        }
        Object val = modelo.getValueAt(row, 0);
        if (val instanceof Integer integer) return integer;
        if (val instanceof Number number) return number.intValue();
        return Integer.parseInt(val.toString());
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

            String matricula = JOptionPane.showInputDialog(this, "Matrícula:");
            if (matricula == null) return;
            String semestreStr = JOptionPane.showInputDialog(this, "Semestre (1-12):");
            if (semestreStr == null) return;
            int semestre = Integer.parseInt(semestreStr);

            String estado = JOptionPane.showInputDialog(this, "Estado (opcional):");
            if (estado == null) estado = "";
            String desc = JOptionPane.showInputDialog(this, "Descripción (opcional):");
            if (desc == null) desc = "";

            if (semestre < 1 || semestre > 12) {
                JOptionPane.showMessageDialog(this, "Semestre debe estar entre 1 y 12.");
                return;
            }

            Alumno a = new Alumno(0, nombre, apP, matricula, /* carrera (no está en BD) */ "", semestre);
            if (AlumnoDAO.crear(a, apM, tel, estado, desc)) {
                JOptionPane.showMessageDialog(this, "Alumno creado");
                cargar();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo crear");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Semestre inválido.");
        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void editar() {
        Integer id = idSeleccionado();
        if (id == null) return;

        Alumno a = AlumnoDAO.obtenerPorId(id);
        if (a == null) {
            JOptionPane.showMessageDialog(this, "No encontrado");
            return;
        }

        try {
            String nombre = JOptionPane.showInputDialog(this, "Nombre:", a.getNombre());
            if (nombre == null) return;
            String apP = JOptionPane.showInputDialog(this, "Apellido Paterno:", a.getApellido());
            if (apP == null) return;
            String apM = JOptionPane.showInputDialog(this, "Apellido Materno (opcional):", "");
            if (apM == null) apM = "";
            String tel = JOptionPane.showInputDialog(this, "Teléfono (opcional):", "");
            if (tel == null) tel = "";

            String matricula = JOptionPane.showInputDialog(this, "Matrícula:", a.getNumeroControl());
            if (matricula == null) return;
            String semestreStr = JOptionPane.showInputDialog(this, "Semestre:", a.getSemestre());
            if (semestreStr == null) return;
            int semestre = Integer.parseInt(semestreStr);

            String estado = JOptionPane.showInputDialog(this, "Estado (opcional):", "");
            if (estado == null) estado = "";
            String desc = JOptionPane.showInputDialog(this, "Descripción (opcional):", "");
            if (desc == null) desc = "";

            if (semestre < 1 || semestre > 12) {
                JOptionPane.showMessageDialog(this, "Semestre debe estar entre 1 y 12.");
                return;
            }

            a = new Alumno(id, nombre, apP, matricula, "", semestre);
            if (AlumnoDAO.actualizar(a, apM, tel, estado, desc)) {
                JOptionPane.showMessageDialog(this, "Actualizado");
                cargar();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo actualizar");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Semestre inválido.");
        } catch (HeadlessException ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void eliminar() {
        Integer id = idSeleccionado();
        if (id == null) return;

        int ok = JOptionPane.showConfirmDialog(this, "¿Eliminar?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            if (AlumnoDAO.eliminar(id)) {
                JOptionPane.showMessageDialog(this, "Eliminado");
                cargar();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar");
            }
        }
    }


 
    private Integer idSeleccionadoModelo() {
    int row = tablaDatos.getSelectedRow(); // usa el nombre real de tu JTable
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Selecciona un registro");
        return null;
    }
    int modelRow = tablaDatos.convertRowIndexToModel(row);
    Object val = tablaDatos.getModel().getValueAt(modelRow, 0); // Columna 0 = ID Persona
    if (val instanceof Integer i) return i;
    if (val instanceof Number n) return n.intValue();
    return Integer.valueOf(val.toString());
}

private void mostrarHistorialSeleccionado() {
    Integer id = idSeleccionadoModelo();
    if (id == null) return;

    java.util.List<clasesjava.VehiculoHistorialDTO> items =
            clasesjava.VehiculoDAO.historialPorPersona(id);

    // Como es JFrame, el owner es "this"
    new vistas.HistorialVehiculosDialog(this, id, "Historial | Alumno", items)
            .setVisible(true);
}


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnRefrescar = new javax.swing.JButton();
        buscar = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDatos = new javax.swing.JTable();
        btnCrear = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnHistorialVehiculos = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btnRefrescar.setText("Refrescar");
        btnRefrescar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefrescarActionPerformed(evt);
            }
        });

        buscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarActionPerformed(evt);
            }
        });

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        tablaDatos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID Persona", "Nombre", "Apellido P", "Matricula", "Semestre"
            }
        ));
        jScrollPane1.setViewportView(tablaDatos);

        btnCrear.setText("Crear");
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });

        btnEditar.setText("Editar");
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
            }
        });

        btnHistorialVehiculos.setText("Historial Vehiculos");
        btnHistorialVehiculos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHistorialVehiculosActionPerformed(evt);
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
                        .addComponent(buscar, javax.swing.GroupLayout.PREFERRED_SIZE, 723, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(btnBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCrear)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar)
                        .addGap(18, 18, 18)
                        .addComponent(btnHistorialVehiculos)
                        .addGap(18, 18, 18)
                        .addComponent(btnRefrescar)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBuscar))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCrear)
                    .addComponent(btnEditar)
                    .addComponent(btnEliminar)
                    .addComponent(btnHistorialVehiculos)
                    .addComponent(btnRefrescar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnRefrescarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefrescarActionPerformed
        // TODO add your handling code here:
        cargar();
    }//GEN-LAST:event_btnRefrescarActionPerformed

    private void buscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarActionPerformed
        // TODO add your handling code here:
        buscar();
    }//GEN-LAST:event_buscarActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        // TODO add your handling code here:
        crear();
    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        editar();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        // TODO add your handling code here:
        eliminar();
    }//GEN-LAST:event_btnEliminarActionPerformed

    private void btnHistorialVehiculosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHistorialVehiculosActionPerformed
        // TODO add your handling code here:
        mostrarHistorialSeleccionado();

    }//GEN-LAST:event_btnHistorialVehiculosActionPerformed

    /**
     * @param args the command line arguments
     */
public static void main(String[] args) {
    try {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }
    } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
        java.util.logging.Logger.getLogger(Alumno3.class.getName())
                .log(java.util.logging.Level.SEVERE, null, ex);
    }

    javax.swing.SwingUtilities.invokeLater(() -> {
        Alumno3 frame = new Alumno3();  // Alumno3 ES el JFrame
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    });
}




    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnHistorialVehiculos;
    private javax.swing.JButton btnRefrescar;
    private javax.swing.JTextField buscar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaDatos;
    // End of variables declaration//GEN-END:variables
}
