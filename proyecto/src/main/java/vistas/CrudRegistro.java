package vistas;

import clasesjava.ConexionDB;
import clasesjava.RegistroDAO;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class CrudRegistro extends javax.swing.JFrame {

    private DefaultTableModel modeloTabla;
    private RegistroDAO registroDAO;
    
    public CrudRegistro() {
        initComponents();
        registroDAO = new RegistroDAO();
        initTabla();
        cargarDatos();
        this.setExtendedState(this.MAXIMIZED_BOTH);
    }
    
    private void initTabla() {
        modeloTabla = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Hora Entrada", "Hora Salida", "Vehículo", "Espacio", "Estado"}
        );
        tablaRegistros.setModel(modeloTabla);
    }
    
    private void cargarDatos() {
        try {
            modeloTabla.setRowCount(0);
            List<String> registros = registroDAO.leerRegistros();
            
            for (String registroStr : registros) {
                // Parsear la cadena para extraer los datos
                String[] partes = registroStr.split("\\|");
                Object[] fila = new Object[6];
                
                for (int i = 0; i < partes.length && i < 6; i++) {
                    String valor = partes[i].split(":")[1].trim();
                    fila[i] = valor;
                }
                
                modeloTabla.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage(),
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void crearRegistro() {
        String horaEntrada = txtHoraEntrada.getText().trim();
        String idVehiculoStr = txtIdVehiculo.getText().trim();
        String idEspacioStr = txtIdEspacio.getText().trim();
        
        if (horaEntrada.isEmpty() || idVehiculoStr.isEmpty() || idEspacioStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios",
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            int idVehiculo = Integer.parseInt(idVehiculoStr);
            int idEspacio = Integer.parseInt(idEspacioStr);
            
            registroDAO.crearRegistro(horaEntrada, idVehiculo, idEspacio);
            JOptionPane.showMessageDialog(this, "Registro creado exitosamente",
                                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarDatos();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID de vehículo y espacio deben ser números",
                                        "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al crear registro: " + e.getMessage(),
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void actualizarSalida() {
        int filaSeleccionada = tablaRegistros.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro para actualizar",
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String idRegistroStr = modeloTabla.getValueAt(filaSeleccionada, 0).toString();
        String horaSalida = txtHoraSalida.getText().trim();
        
        if (horaSalida.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese la hora de salida",
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            int idRegistro = Integer.parseInt(idRegistroStr);
            registroDAO.actualizarRegistro(idRegistro, horaSalida);
            JOptionPane.showMessageDialog(this, "Salida registrada exitosamente",
                                        "Éxito", JOptionPane.INFORMATION_MESSAGE);
            limpiarCampos();
            cargarDatos();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "ID de registro inválido",
                                        "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al actualizar registro: " + e.getMessage(),
                                        "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void eliminarRegistro() {
        int filaSeleccionada = tablaRegistros.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un registro para eliminar",
                                        "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String idRegistroStr = modeloTabla.getValueAt(filaSeleccionada, 0).toString();
        
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de eliminar el registro ID: " + idRegistroStr + "?",
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            try {
                int idRegistro = Integer.parseInt(idRegistroStr);
                registroDAO.eliminarRegistro(idRegistro);
                JOptionPane.showMessageDialog(this, "Registro eliminado exitosamente",
                                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                cargarDatos();
                
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "ID de registro inválido",
                                            "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar registro: " + e.getMessage(),
                                            "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void limpiarCampos() {
        txtHoraEntrada.setText("");
        txtHoraSalida.setText("");
        txtIdVehiculo.setText("");
        txtIdEspacio.setText("");
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnCrear = new javax.swing.JButton();
        btnActualizar = new javax.swing.JButton();
        btnSalir = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaRegistros = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtHoraEntrada = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtIdVehiculo = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtIdEspacio = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        txtHoraSalida = new javax.swing.JTextField();
        btnRegistrarEntrada = new javax.swing.JButton();
        btnRegistrarSalida = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(184, 224, 233));

        jPanel2.setBackground(new java.awt.Color(44, 116, 142));

        jLabel1.setFont(new java.awt.Font("Bernard MT Condensed", 0, 30)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("GESTIÓN DE REGISTROS");

        jLabel2.setText("¿Qué desea hacer?");

        btnCrear.setBackground(new java.awt.Color(184, 224, 233));
        btnCrear.setFont(new java.awt.Font("Bernard MT Condensed", 0, 24)); // NOI18N
        btnCrear.setForeground(new java.awt.Color(44, 116, 142));
        btnCrear.setText("Nuevo Registro");
        btnCrear.setBorder(null);
        btnCrear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearActionPerformed(evt);
            }
        });

        btnActualizar.setBackground(new java.awt.Color(184, 224, 233));
        btnActualizar.setFont(new java.awt.Font("Bernard MT Condensed", 0, 24)); // NOI18N
        btnActualizar.setForeground(new java.awt.Color(44, 116, 142));
        btnActualizar.setText("Actualizar Tabla");
        btnActualizar.setBorder(null);
        btnActualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnActualizarActionPerformed(evt);
            }
        });

        btnSalir.setBackground(new java.awt.Color(184, 224, 233));
        btnSalir.setFont(new java.awt.Font("Bernard MT Condensed", 0, 24)); // NOI18N
        btnSalir.setForeground(new java.awt.Color(44, 116, 142));
        btnSalir.setText("Salir");
        btnSalir.setBorder(null);
        btnSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalirActionPerformed(evt);
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
                        .addComponent(btnCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(30, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))
                        .addGap(74, 74, 74))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addComponent(btnCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 67, Short.MAX_VALUE)
                .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                .addComponent(btnSalir, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(44, 116, 142));

        tablaRegistros.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tablaRegistros);

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Hora Entrada (HH:MM):");

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("ID Vehículo:");

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("ID Espacio:");

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Hora Salida (HH:MM):");

        btnRegistrarEntrada.setText("Registrar Entrada");
        btnRegistrarEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarEntradaActionPerformed(evt);
            }
        });

        btnRegistrarSalida.setText("Registrar Salida");
        btnRegistrarSalida.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarSalidaActionPerformed(evt);
            }
        });

        btnEliminar.setText("Eliminar Registro");
        btnEliminar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarActionPerformed(evt);
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
                            .addComponent(btnRegistrarEntrada)
                            .addComponent(btnRegistrarSalida)
                            .addComponent(btnEliminar)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtHoraEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIdVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtIdEspacio, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtHoraSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtHoraEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txtIdVehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txtIdEspacio, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtHoraSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(btnRegistrarEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(29, 29, 29)
                        .addComponent(btnRegistrarSalida, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(115, 115, 115))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54))))
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

    private void btnCrearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearActionPerformed
        crearRegistro();
    }//GEN-LAST:event_btnCrearActionPerformed

    private void btnActualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnActualizarActionPerformed
        cargarDatos();
        JOptionPane.showMessageDialog(this, "Datos actualizados correctamente", 
                                    "Actualización", JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_btnActualizarActionPerformed

    private void btnSalirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalirActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnSalirActionPerformed

    private void btnRegistrarEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarEntradaActionPerformed
        crearRegistro();
    }//GEN-LAST:event_btnRegistrarEntradaActionPerformed

    private void btnRegistrarSalidaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarSalidaActionPerformed
        actualizarSalida();
    }//GEN-LAST:event_btnRegistrarSalidaActionPerformed

    private void btnEliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarActionPerformed
        eliminarRegistro();
    }//GEN-LAST:event_btnEliminarActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnCrear;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnRegistrarEntrada;
    private javax.swing.JButton btnRegistrarSalida;
    private javax.swing.JButton btnSalir;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaRegistros;
    private javax.swing.JTextField txtHoraEntrada;
    private javax.swing.JTextField txtHoraSalida;
    private javax.swing.JTextField txtIdEspacio;
    private javax.swing.JTextField txtIdVehiculo;
    // End of variables declaration//GEN-END:variables
}