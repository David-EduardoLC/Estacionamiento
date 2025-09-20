package vistas;
import clasesjava.OcupacionDAO;
import clasesjava.EspacioC;
import clasesjava.EspacioDAO;
import clasesjava.Vehiculo;
import clasesjava.VehiculoDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;




public class EspacioFrame extends javax.swing.JFrame {


    private DefaultTableModel modeloTabla;
    private static final int ID_ESTACIONAMIENTO_ACTIVO = 1;

     public EspacioFrame() {
        initComponents();

        // Columnas que SÍ corresponden a la tabla `espacio`
        modeloTabla = new DefaultTableModel(
                new Object[][]{},
                new String[]{"ID Zona", "ID Espacio", "Tipo de lugar", "Estado"}
        ) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };

        tablarol.setModel(modeloTabla);

        cargarDatos();   // ← ahora carga de `espacio`
        iniciarAlertas();
    }
    
    
    



    private javax.swing.Timer timerAlertas;

private void iniciarAlertas() {
    int cadaMs = 60_000; // 60s
    timerAlertas = new javax.swing.Timer(cadaMs, e -> checarAlertas());
    timerAlertas.setRepeats(true);
    timerAlertas.start();
}

private void checarAlertas() {
    java.time.Duration umbral = java.time.Duration.ofMinutes(120); // 2 horas (ajusta)
    List<OcupacionDAO.OcupacionAlerta> alertas = OcupacionDAO.obtenerAlertas(umbral);
    if (!alertas.isEmpty()) {
        // Puedes mostrar un popup resumido...
        StringBuilder sb = new StringBuilder("Alertas de ocupación prolongada:\n");
        for (OcupacionDAO.OcupacionAlerta a : alertas) {
            sb.append("Espacio ").append(a.idEspacio)
              .append(" - Placa ").append(a.placa)
              .append(" - ")
              .append(a.minutos).append(" min\n");
        }
        JOptionPane.showMessageDialog(this, sb.toString(), "Alertas", JOptionPane.WARNING_MESSAGE);

        // ...o marcar filas en rojo en tu JTable (pinta el renderer si lo quieres más pro).
    }
}

 
  // Método para cargar datos en una JTable
public static void cargarEspaciosEnTabla(JTable tabla) {
    DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();
    modelo.setRowCount(0); // Limpiar tabla

    List<EspacioC> espacios = EspacioDAO.obtenerTodosEspacios();

    for (EspacioC esp : espacios) {
        Object[] fila = {
            esp.getIdZona(),
            esp.getIdEspacio(),
            esp.getTipoLugar(),
            esp.getEstado()
        };
        modelo.addRow(fila);
    }
}

// Método para cargar datos en el JFrame
private void cargarDatos() {
    try {
        modeloTabla = (DefaultTableModel) tablarol.getModel(); // Asegúrate de inicializar
        modeloTabla.setRowCount(0); // Limpiar tabla

        List<EspacioC> espacios = EspacioDAO.obtenerTodosEspacios();

        for (EspacioC esp : espacios) {
            Object[] fila = {
                esp.getIdZona(),
                esp.getIdEspacio(),
                esp.getTipoLugar(),
                esp.getEstado()
            };
            modeloTabla.addRow(fila);
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Error al cargar datos: " + e.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
    }
}

// Método para obtener espacio seleccionado
private EspacioC obtenerEspacioSeleccionado() {
    int filaSeleccionada = tablarol.getSelectedRow();

    if (filaSeleccionada == -1) {
        JOptionPane.showMessageDialog(this, "Por favor, seleccione un espacio",
                "Advertencia", JOptionPane.WARNING_MESSAGE);
        return null;
    }

    Object valor = modeloTabla.getValueAt(filaSeleccionada, 1); // Columna 1 = ID EspacioFrame
    int idEspacio = Integer.parseInt(valor.toString());

    return EspacioDAO.obtenerEspacioPorId(idEspacio);
}


     

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btncrearvehiculo = new javax.swing.JButton();
        btns = new javax.swing.JButton();
        btncrearvehiculo1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablarol = new javax.swing.JTable();
        txtBusqueda = new javax.swing.JTextField();
        btneliminar = new javax.swing.JButton();
        btneditar1 = new javax.swing.JButton();
        agregarbtn = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(184, 224, 233));

        jPanel2.setBackground(new java.awt.Color(44, 116, 142));

        jLabel2.setFont(new java.awt.Font("Bernard MT Condensed", 0, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("BIENVENIDO");

        jLabel1.setText("¿Que desea hacer hoy?");

        btncrearvehiculo.setBackground(new java.awt.Color(184, 224, 233));
        btncrearvehiculo.setFont(new java.awt.Font("Bernard MT Condensed", 0, 14)); // NOI18N
        btncrearvehiculo.setForeground(new java.awt.Color(44, 116, 142));
        btncrearvehiculo.setText("AGREGAR NUEVO VEHICULO ");
        btncrearvehiculo.setBorder(null);
        btncrearvehiculo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncrearvehiculoActionPerformed(evt);
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

        btncrearvehiculo1.setBackground(new java.awt.Color(184, 224, 233));
        btncrearvehiculo1.setFont(new java.awt.Font("Bernard MT Condensed", 0, 14)); // NOI18N
        btncrearvehiculo1.setForeground(new java.awt.Color(44, 116, 142));
        btncrearvehiculo1.setText("AGREGAR NUEVA MOTO");
        btncrearvehiculo1.setBorder(null);
        btncrearvehiculo1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btncrearvehiculo1ActionPerformed(evt);
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(74, 74, 74))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btncrearvehiculo1, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btns, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btncrearvehiculo, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addComponent(btncrearvehiculo1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btns, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(180, 180, 180))
        );

        jPanel3.setBackground(new java.awt.Color(44, 116, 142));

        tablarol.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID Zona", "ID Espacio", "Tipo de lugar", "Estado"
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

        btneditar1.setText("EDITAR");
        btneditar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btneditar1ActionPerformed(evt);
            }
        });

        agregarbtn.setText("AGREGAR");
        agregarbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                agregarbtnActionPerformed(evt);
            }
        });

        jButton1.setText("ASIGNAR ESPACIO");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("LIBERAR ESPACIO");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(296, 296, 296)
                .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(467, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addComponent(agregarbtn)
                        .addGap(79, 79, 79)
                        .addComponent(btneditar1)
                        .addGap(118, 118, 118)
                        .addComponent(jButton1)
                        .addGap(127, 127, 127)
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btneliminar))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 863, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btneliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btneditar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(agregarbtn)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addGap(34, 34, 34))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(207, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(163, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btncrearvehiculoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncrearvehiculoActionPerformed
       // Abrir el frame de Autos
    AutoFrame autoFrame = new AutoFrame();
    autoFrame.setVisible(true);

    // Opcional: cerrar el frame actual (si no quieres que se quede abierto)
    this.dispose();
    
    }//GEN-LAST:event_btncrearvehiculoActionPerformed

    private void btnsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnsActionPerformed

   
    
    private void txtBusquedaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtBusquedaCaretUpdate
       
        
    }//GEN-LAST:event_txtBusquedaCaretUpdate

    private void txtBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBusquedaActionPerformed
    String q = txtBusqueda.getText();

    // Llama a tu DAO con el criterio
    List<EspacioC> lista = EspacioDAO.buscarEspacios(q, null);

    // Limpia la tabla y agrega resultados
    modeloTabla.setRowCount(0);
    for (EspacioC esp : lista) {
        modeloTabla.addRow(new Object[]{
            esp.getIdZona(),
            esp.getIdEspacio(),
            esp.getTipoLugar(),
            esp.getEstado()
        });
    }        
    }//GEN-LAST:event_txtBusquedaActionPerformed

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed

    EspacioC espacio = obtenerEspacioSeleccionado();
    if (espacio == null) return;

    int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Estás seguro de que deseas eliminar el espacio seleccionado?",
            "Confirmar eliminación", JOptionPane.YES_NO_OPTION);

    if (confirmacion == JOptionPane.YES_OPTION) {
        boolean exito = EspacioDAO.eliminarEspacio(espacio.getIdEspacio());
        if (exito) {
            JOptionPane.showMessageDialog(this, "Espacio eliminado correctamente");
            cargarDatos(); // refrescar tabla
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar el espacio");
        }
    }

    }//GEN-LAST:event_btneliminarActionPerformed

    private void btneditar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditar1ActionPerformed

    EspacioC espacio = obtenerEspacioSeleccionado();
    if (espacio == null) return;

    // Pedir nuevos valores al usuario
    String nuevoTipo = JOptionPane.showInputDialog(this, "Nuevo tipo de lugar:", espacio.getTipoLugar());
    if (nuevoTipo == null || nuevoTipo.trim().isEmpty()) return;

    String nuevoEstado = JOptionPane.showInputDialog(this, "Nuevo estado:", espacio.getEstado());
    if (nuevoEstado == null || nuevoEstado.trim().isEmpty()) return;

    String zonaStr = JOptionPane.showInputDialog(this, "Nuevo ID de zona:", String.valueOf(espacio.getIdZona()));
    if (zonaStr == null || zonaStr.trim().isEmpty()) return;

    try {
        int nuevaZona = Integer.parseInt(zonaStr);

        // Actualizar objeto
        espacio.setTipoLugar(nuevoTipo);
        espacio.setEstado(nuevoEstado);
        espacio.setIdZona(nuevaZona);

        // Guardar en BD
        boolean exito = EspacioDAO.actualizarEspacio(espacio);
        if (exito) {
            JOptionPane.showMessageDialog(this, "Espacio actualizado correctamente");
            cargarDatos(); // refrescar tabla
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar el espacio");
        }

    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "ID de zona inválido");
    }

    }//GEN-LAST:event_btneditar1ActionPerformed

    private void agregarbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarbtnActionPerformed
                                       
    String tipoLugar = JOptionPane.showInputDialog(this, "Tipo de lugar (carro/moto):");
    if (tipoLugar == null || tipoLugar.trim().isEmpty()) return;

    String estado = JOptionPane.showInputDialog(this, "Estado (libre/ocupado o 'disponible'):");
    if (estado == null || estado.trim().isEmpty()) return;

    String letraZona = JOptionPane.showInputDialog(this, "Zona (A, B, C, D, E, F):");
    if (letraZona == null || letraZona.trim().isEmpty()) return;

    // idZona lo resuelve el DAO usando (letraZona + estacionamiento activo)
    EspacioC nuevo = new EspacioC(tipoLugar, estado, 0);
    boolean exito = EspacioDAO.insertarEspacio(nuevo, letraZona, ID_ESTACIONAMIENTO_ACTIVO);

    if (exito) {
        JOptionPane.showMessageDialog(this, "Espacio agregado correctamente");
        cargarDatos();
    } else {
        JOptionPane.showMessageDialog(this, "No se pudo agregar el espacio");
    }



    }//GEN-LAST:event_agregarbtnActionPerformed

    private void btncrearvehiculo1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btncrearvehiculo1ActionPerformed
        // TODO add your handling code here:
        
        // Abrir el frame de Autos
    MotoFrame motoFrame = new MotoFrame();
    motoFrame.setVisible(true);

    // Opcional: cerrar el frame actual (si no quieres que se quede abierto)
    this.dispose();
    }//GEN-LAST:event_btncrearvehiculo1ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        int fila = tablarol.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Selecciona un espacio.", "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }
    int idEspacio = (int) tablarol.getValueAt(fila, 1); // ajusta al índice de tu columna ID_Espacio
    String placa = JOptionPane.showInputDialog(this, "Placa del vehículo:");
    if (placa == null || placa.isBlank()) return;

    Vehiculo veh = VehiculoDAO.obtenerVehiculoPorPlaca(placa.trim().toUpperCase());
    if (veh == null) {
        JOptionPane.showMessageDialog(this, "No existe un vehículo con esa placa.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    boolean ok = OcupacionDAO.asignarVehiculoAEspacio(veh.getIdVehiculo(), idEspacio);
    if (ok) {
        JOptionPane.showMessageDialog(this, "Asignación realizada.");
        cargarDatos(); // refresca tabla
    } else {
        JOptionPane.showMessageDialog(this, "No se pudo asignar (espacio/vehículo ya ocupados).",
                "Error", JOptionPane.ERROR_MESSAGE);
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
    int fila = tablarol.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Selecciona un espacio.", "Aviso", JOptionPane.WARNING_MESSAGE);
        return;
    }
    int idEspacio = (int) tablarol.getValueAt(fila, 1); // ajusta índice
    boolean ok = OcupacionDAO.liberarEspacio(idEspacio);
    if (ok) {
        JOptionPane.showMessageDialog(this, "Espacio liberado.");
        cargarDatos();
    } else {
        JOptionPane.showMessageDialog(this, "No hay ocupación activa para ese espacio.",
                "Info", JOptionPane.INFORMATION_MESSAGE);
    }

    }//GEN-LAST:event_jButton2ActionPerformed

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
            java.util.logging.Logger.getLogger(EspacioFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EspacioFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EspacioFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EspacioFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EspacioFrame().setVisible(true);
            }
        });
}
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton agregarbtn;
    private javax.swing.JButton btncrearvehiculo;
    private javax.swing.JButton btncrearvehiculo1;
    private javax.swing.JButton btneditar1;
    private javax.swing.JButton btneliminar;
    private javax.swing.JButton btns;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
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

