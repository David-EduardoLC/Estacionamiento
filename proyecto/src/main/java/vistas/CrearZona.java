/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vistas;


import clasesjava.Estacionamiento;
import clasesjava.EstacionamientoDAO;
import clasesjava.Zona;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlo
 */
public class CrearZona extends javax.swing.JFrame {
   
 private DefaultComboBoxModel<String> comboModel;
    /**
     * Creates new form CrearUser
     */
    public CrearZona() {
        initComponents();
        this.setSize(800, 600);      // tamaño fijo
        this.setLocationRelativeTo(null); // centrada        
        cargarEstacionamientosEnComboBox();
    }
    
 private void cargarEstacionamientosEnComboBox() {
        comboModel = new DefaultComboBoxModel<>();
        ComboBoxEstacionamiento.setModel(comboModel);
        
        List<Estacionamiento> estacionamientos = EstacionamientoDAO.obtenerTodosEstacionamientos();
        
        if (estacionamientos.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "No hay estacionamientos registrados en la base de datos", 
                "Advertencia", JOptionPane.WARNING_MESSAGE);
        } else {
            // Agregar cada estacionamiento al combo box
            for (Estacionamiento est : estacionamientos) {
                comboModel.addElement(est.getNombre() + " - " + est.getUbicacion());
            }
        }
    }
    
    public int obtenerIdEstacionamientoSeleccionado() {
        int selectedIndex = ComboBoxEstacionamiento.getSelectedIndex();
        if (selectedIndex >= 0) {
            List<Estacionamiento> estacionamientos = EstacionamientoDAO.obtenerTodosEstacionamientos();
            return estacionamientos.get(selectedIndex).getIdEstacionamiento();
        }
        return -1;
    }
    
    public Estacionamiento obtenerEstacionamientoSeleccionado() {
        int selectedIndex = ComboBoxEstacionamiento.getSelectedIndex();
        if (selectedIndex >= 0) {
            List<Estacionamiento> estacionamientos = EstacionamientoDAO.obtenerTodosEstacionamientos();
            return estacionamientos.get(selectedIndex);
        }
        return null;
    }
    
    
    
    // MÉTODO PARA GUARDAR LA ZONA
    private void guardarZona() {
        String nombreZona = txtNombre.getText().trim();
        int idEstacionamiento = obtenerIdEstacionamientoSeleccionado();
        
        // Validaciones
        if (nombreZona.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Por favor ingrese el nombre de la zona", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (idEstacionamiento == -1) {
            JOptionPane.showMessageDialog(this, 
                "Por favor seleccione un estacionamiento", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try {
            // Crear objeto Zona
            Zona zona = new Zona();
            zona.setDescripcion(nombreZona);
            zona.setIdEstacionamiento(idEstacionamiento);
            
            // Obtener información del estacionamiento para completar los datos
            Estacionamiento estacionamiento = obtenerEstacionamientoSeleccionado();
            if (estacionamiento != null) {
                zona.setNombreEstacionamiento(estacionamiento.getNombre());
                zona.setUbicacionEstacionamiento(estacionamiento.getUbicacion());
            }
            
            // Guardar en la base de datos (necesitarás implementar este método en ZonaDAO)
            boolean exito = guardarZonaEnBD(zona);
            
            if (exito) {
                JOptionPane.showMessageDialog(this, 
                    "Zona guardada exitosamente", 
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCampos();
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Error al guardar la zona", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Error inesperado: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Método para guardar en la base de datos (debes implementarlo en ZonaDAO)
    private boolean guardarZonaEnBD(Zona zona) {
        String sql = "INSERT INTO Zona (descripcion, id_estacionamiento) VALUES (?, ?)";
        
        try (java.sql.Connection conn = clasesjava.ConexionDB.conectar();
             java.sql.PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, zona.getDescripcion());
            pstmt.setInt(2, zona.getIdEstacionamiento());
            
            int filasAfectadas = pstmt.executeUpdate();
            return filasAfectadas > 0;
            
        } catch (java.sql.SQLException e) {
            System.err.println("Error al guardar zona: " + e.getMessage());
            return false;
        }
    }
    
    private void limpiarCampos() {
        txtNombre.setText("");
        ComboBoxEstacionamiento.setSelectedIndex(0);
    }
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnAcceder = new javax.swing.JButton();
        ComboBoxEstacionamiento = new javax.swing.JComboBox<>();
        btnSRC = new javax.swing.JButton();

        jLabel2.setFont(new java.awt.Font("Bernard MT Condensed", 0, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("BIENVENIDO");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel6.setBackground(new java.awt.Color(184, 224, 233));

        jPanel2.setBackground(new java.awt.Color(240, 249, 251));

        jLabel1.setFont(new java.awt.Font("Bernard MT Condensed", 0, 50)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(44, 116, 142));
        jLabel1.setText("     CREAR ZONA");

        jLabel3.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 0, 0));
        jLabel3.setText("Nombre o descripcion:");

        txtNombre.setBackground(new java.awt.Color(185, 196, 203));
        txtNombre.setForeground(new java.awt.Color(44, 116, 142));
        txtNombre.setBorder(null);
        txtNombre.setSelectedTextColor(new java.awt.Color(44, 116, 142));
        txtNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNombreActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial Black", 0, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(0, 0, 0));
        jLabel4.setText("Estacionamiento asignado:");

        btnAcceder.setBackground(new java.awt.Color(44, 116, 142));
        btnAcceder.setFont(new java.awt.Font("Bernard MT Condensed", 0, 18)); // NOI18N
        btnAcceder.setForeground(new java.awt.Color(255, 255, 255));
        btnAcceder.setText("Confirmar");
        btnAcceder.setBorder(null);
        btnAcceder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAccederActionPerformed(evt);
            }
        });

        ComboBoxEstacionamiento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxEstacionamientoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(56, 56, 56)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 55, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(ComboBoxEstacionamiento, javax.swing.GroupLayout.PREFERRED_SIZE, 260, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(154, 154, 154)
                .addComponent(btnAcceder, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addGap(12, 12, 12)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(ComboBoxEstacionamiento, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46)
                .addComponent(btnAcceder, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(66, Short.MAX_VALUE))
        );

        btnSRC.setBackground(new java.awt.Color(44, 116, 142));
        btnSRC.setBorder(null);
        btnSRC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSRCActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSRC)
                .addContainerGap(849, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(161, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(157, 157, 157))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnSRC)
                .addGap(75, 75, 75)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(163, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel6, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents
   

    private void txtNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNombreActionPerformed

    private void btnAccederActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAccederActionPerformed
    
    guardarZona(); 
    this.dispose();        
          
    }//GEN-LAST:event_btnAccederActionPerformed

    private void btnSRCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSRCActionPerformed
      
        
    }//GEN-LAST:event_btnSRCActionPerformed

    private void ComboBoxEstacionamientoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxEstacionamientoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboBoxEstacionamientoActionPerformed

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
            java.util.logging.Logger.getLogger(CrearZona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CrearZona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CrearZona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CrearZona.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CrearZona().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboBoxEstacionamiento;
    private javax.swing.JButton btnAcceder;
    private javax.swing.JButton btnSRC;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JTextField txtNombre;
    // End of variables declaration//GEN-END:variables
}
