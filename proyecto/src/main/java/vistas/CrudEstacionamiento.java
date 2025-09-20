package vistas;

import clasesjava.EstacionamientoDAO;
import clasesjava.Estacionamiento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;



public class CrudEstacionamiento extends javax.swing.JFrame {

    private DefaultTableModel modeloTabla;
    
    public CrudEstacionamiento() {
          initComponents();
            modeloTabla = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Nombre", "Ubicación", "Hora Apertura", "Hora Cierre"}
        );
        tablarol.setModel(modeloTabla);
        configurarAnchosColumnas();
        this.setExtendedState(this.MAXIMIZED_BOTH);
        cargarDatos();
    }
    
    
    
     private void configurarAnchosColumnas() {
        TableColumnModel columnModel = tablarol.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);    // ID
        columnModel.getColumn(1).setPreferredWidth(250);   // Nombre
        columnModel.getColumn(2).setPreferredWidth(250);   // Ubicación
        columnModel.getColumn(3).setPreferredWidth(100);   // Hora Apertura
        columnModel.getColumn(4).setPreferredWidth(100);   // Hora Cierre
        tablarol.setAutoResizeMode(tablarol.AUTO_RESIZE_OFF);
    }
    
 
   // Método para cargar datos en una JTable
public static void cargarEstacionamientosEnTabla(JTable tabla) {
    DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
    modelo.setRowCount(0); // Limpiar tabla
    
    List<Estacionamiento> estacionamientos = EstacionamientoDAO.obtenerTodosEstacionamientos();
    
    for (Estacionamiento est : estacionamientos) {
        Object[] fila = {
            est.getIdEstacionamiento(),
            est.getNombre(),
            est.getUbicacion(),
            est.getHoraApertura().toString(),
            est.getHoraCierre().toString()
        };
        modelo.addRow(fila);
    }
}

// Método para cargar datos de estacionamientos
    private void cargarDatos() {
        try {
            modeloTabla.setRowCount(0); // Limpiar tabla
            
            List<Estacionamiento> estacionamientos = EstacionamientoDAO.obtenerTodosEstacionamientos();
            
            for (Estacionamiento est : estacionamientos) {
                Object[] fila = {
                    est.getIdEstacionamiento(),
                    est.getNombre(),
                    est.getUbicacion(),
                    est.getHoraApertura().toString(),
                    est.getHoraCierre().toString()
                };
                modeloTabla.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Método para obtener estacionamiento seleccionado
    private Estacionamiento obtenerEstacionamientoSeleccionado() {
        int filaSeleccionada = tablarol.getSelectedRow();
        
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un estacionamiento",
                                        "Advertencia", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        
        int idEstacionamiento = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);
        return EstacionamientoDAO.obtenerEstacionamientoPorId(idEstacionamiento);
    }
     

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btncrearvehiculo = new javax.swing.JButton();
        btnactualizartabla = new javax.swing.JButton();
        btns = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablarol = new javax.swing.JTable();
        txtBusqueda = new javax.swing.JTextField();
        btneliminar = new javax.swing.JButton();
        btneditar = new javax.swing.JButton();
        btneditar1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(184, 224, 233));

        jPanel2.setBackground(new java.awt.Color(44, 116, 142));

        jLabel2.setFont(new java.awt.Font("Bernard MT Condensed", 0, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("BIENVENIDO");

        jLabel1.setText("¿Que desea hacer hoy?");

        btncrearvehiculo.setBackground(new java.awt.Color(184, 224, 233));
        btncrearvehiculo.setFont(new java.awt.Font("Bernard MT Condensed", 0, 24)); // NOI18N
        btncrearvehiculo.setForeground(new java.awt.Color(44, 116, 142));
        btncrearvehiculo.setText("Crear ");
        btncrearvehiculo.setBorder(null);
        btncrearvehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncrearvehiculoActionPerformed(evt);
            }
        });

        btnactualizartabla.setBackground(new java.awt.Color(184, 224, 233));
        btnactualizartabla.setFont(new java.awt.Font("Bernard MT Condensed", 0, 24)); // NOI18N
        btnactualizartabla.setForeground(new java.awt.Color(44, 116, 142));
        btnactualizartabla.setText("Actualizar");
        btnactualizartabla.setBorder(null);
        btnactualizartabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnactualizartablaActionPerformed(evt);
            }
        });

        btns.setBackground(new java.awt.Color(184, 224, 233));
        btns.setFont(new java.awt.Font("Bernard MT Condensed", 0, 24)); // NOI18N
        btns.setForeground(new java.awt.Color(44, 116, 142));
        btns.setText("Salir");
        btns.setBorder(null);
        btns.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(btncrearvehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(30, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btns, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnactualizartabla, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(74, 74, 74))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addComponent(btncrearvehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addComponent(btnactualizartabla, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addComponent(btns, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(44, 116, 142));

        tablarol.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre ", "Ubicacion", "hora_aperuta", "hora_cierre"
            }
        ));
        jScrollPane1.setViewportView(tablarol);

        txtBusqueda.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                txtBusquedaCaretUpdate(evt);
            }
        });
        txtBusqueda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBusquedaActionPerformed(evt);
            }
        });

        btneliminar.setText("ELIMINAR");
        btneliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneliminarActionPerformed(evt);
            }
        });

        btneditar.setText("INFORMACION");
        btneditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditarActionPerformed(evt);
            }
        });

        btneditar1.setText("EDITAR");
        btneditar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditar1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(84, 84, 84)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 768, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btneliminar)
                            .addComponent(btneditar)
                            .addComponent(btneditar1)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(296, 296, 296)
                        .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btneditar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(btneditar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btneliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(115, 115, 115))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(54, 54, 54))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(294, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(160, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(181, 181, 181))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btncrearvehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncrearvehiculoActionPerformed


        // Abrir la ventana de creación
    CrearEstacionamiento crearWindow = new CrearEstacionamiento();
    crearWindow.setVisible(true);
    
    // Centrar la ventana
    crearWindow.setLocationRelativeTo(this);
    
    // Opcional: agregar listener para recargar datos cuando se cierre
    crearWindow.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosed(java.awt.event.WindowEvent e) {
            cargarDatos(); // Recargar la tabla después de crear
        }
    });
    
    }//GEN-LAST:event_btncrearvehiculoActionPerformed

    private void btnactualizartablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizartablaActionPerformed
cargarDatos();
        JOptionPane.showMessageDialog(this, "Datos actualizados correctamente", 
                                    "Actualización", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnactualizartablaActionPerformed

    private void btnsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsActionPerformed


        
    }//GEN-LAST:event_btnsActionPerformed

   
    
    private void txtBusquedaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtBusquedaCaretUpdate
       
        
    }//GEN-LAST:event_txtBusquedaCaretUpdate

    private void txtBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBusquedaActionPerformed
     
         String textoBusqueda = txtBusqueda.getText().trim();
        
        if (textoBusqueda.isEmpty()) {
            cargarDatos();
            return;
        }
        
        try {
            modeloTabla.setRowCount(0);
            
            List<Estacionamiento> resultados = EstacionamientoDAO.buscarEstacionamientos(textoBusqueda);
            
            for (Estacionamiento est : resultados) {
                Object[] fila = {
                    est.getIdEstacionamiento(),
                    est.getNombre(),
                    est.getUbicacion(),
                    est.getHoraApertura().toString(),
                    est.getHoraCierre().toString()
                };
                modeloTabla.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error en búsqueda: " + e.getMessage(), 
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    
    }//GEN-LAST:event_txtBusquedaActionPerformed

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed

        Estacionamiento est = obtenerEstacionamientoSeleccionado();
        
        if (est != null) {
            int confirmacion = JOptionPane.showConfirmDialog(this, 
                "¿Está seguro de que desea eliminar el estacionamiento: " + est.getNombre() + "?", 
                "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                if (EstacionamientoDAO.eliminarEstacionamiento(est.getIdEstacionamiento())) {
                    JOptionPane.showMessageDialog(this, "Estacionamiento eliminado correctamente", 
                                                "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarDatos(); // Recargar datos después de eliminar
                }
            }
        }
        
    }//GEN-LAST:event_btneliminarActionPerformed

    private void btneditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditarActionPerformed

        
         int filaSeleccionada = tablarol.getSelectedRow();
    
    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this, "Por favor, seleccione un vehículo para editar", 
                                    "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }
    
    int idVehiculo = (Integer) modeloTabla.getValueAt(filaSeleccionada, 0);
    String placa = (String) modeloTabla.getValueAt(filaSeleccionada, 1);
    String modelo = (String) modeloTabla.getValueAt(filaSeleccionada, 2);
    
    // Aquí deberías abrir un formulario de edición con los datos del vehículo
    JOptionPane.showMessageDialog(this, 
        "Funcionalidad de edición para el vehículo:\nID: " + idVehiculo + 
        "\nPlaca: " + placa + "\nModelo: " + modelo, 
        "Editar Vehículo", JOptionPane.INFORMATION_MESSAGE);
        
    }//GEN-LAST:event_btneditarActionPerformed

    private void btneditar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditar1ActionPerformed
        Estacionamiento est = obtenerEstacionamientoSeleccionado();
    
    if (est != null) {
        CrearEstacionamiento editarWindow = new CrearEstacionamiento(est);
        editarWindow.setVisible(true);
        
        editarWindow.setLocationRelativeTo(this);
        
        editarWindow.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent e) {
                cargarDatos();
            }
        });
    }
    }//GEN-LAST:event_btneditar1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CrudEstacionamiento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CrudEstacionamiento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CrudEstacionamiento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CrudEstacionamiento.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CrudEstacionamiento().setVisible(true);
            }
        });
}

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnactualizartabla;
    private javax.swing.JButton btncrearvehiculo;
    private javax.swing.JButton btneditar;
    private javax.swing.JButton btneditar1;
    private javax.swing.JButton btneliminar;
    private javax.swing.JButton btns;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablarol;
    private javax.swing.JTextField txtBusqueda;
    // End of variables declaration//GEN-END:variables
}

