package vistas;
import clasesjava.Auto;
import clasesjava.AutoDAO;
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




public class AutoFrame extends javax.swing.JFrame {


    private DefaultTableModel modeloTabla;
    private int filaSeleccionada = -1;

     public AutoFrame() {
         
        initComponents();
        

        // Columnas que corresponden a la tabla `vehiculo`
        modeloTabla = new DefaultTableModel(
    new Object[][]{},
    new String[]{"ID Vehículo", "Placa", "Modelo", "ID Persona", "Tipo Auto"}
) {
    @Override public boolean isCellEditable(int row, int column) { return false; }
};
tablarol.setModel(modeloTabla);

        this.setExtendedState(this.MAXIMIZED_BOTH);
    cargarTabla();   // <--- aquí
    }
    
    


    // -------- Cargar datos en JTable (versión utilitaria estática) ----------
    private void cargarTabla() {
    DefaultTableModel modelo = (DefaultTableModel) tablarol.getModel();
    modelo.setRowCount(0); // limpiar

    try {
        java.util.List<Auto> lista = AutoDAO.listarAutos();

        if (lista == null || lista.isEmpty()) {
            // Si quieres, puedes mostrar mensaje o simplemente dejar la tabla vacía
            return;
        }

        for (Auto a : lista) {
            modelo.addRow(new Object[]{
                a.getIdVehiculo(),
                a.getPlaca(),
                a.getModelo(),
                a.getIdPersona(),
                a.getTipoAuto() // <- viene de tabla 'carro'
            });
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this,
            "Error al cargar autos: " + e.getMessage(),
            "Error", JOptionPane.ERROR_MESSAGE);
    }
}




    // -------- Cargar datos en este frame ----------
    private void cargarDatos() {
        try {
            modeloTabla.setRowCount(0);
            List<Vehiculo> vehiculos = VehiculoDAO.obtenerTodosVehiculos();

            for (Vehiculo v : vehiculos) {
                Object[] fila = {
                    v.getIdVehiculo(),
                    v.getPlaca(),
                    v.getModelo(),
                    v.getIdPersona()
                };
                modeloTabla.addRow(fila);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al cargar vehículos: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // -------- Obtener vehículo seleccionado ----------
    private Vehiculo obtenerVehiculoSeleccionado() {
        int fila = tablarol.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione un vehículo",
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            return null;
        }

        // Columna 0 = ID Vehículo
        Object val = modeloTabla.getValueAt(fila, 0);
        int idVehiculo = Integer.parseInt(val.toString());
        return VehiculoDAO.obtenerVehiculoPorId(idVehiculo);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnactualizartabla = new javax.swing.JButton();
        btns = new javax.swing.JButton();
        btnactualizartabla1 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablarol = new javax.swing.JTable();
        txtBusqueda = new javax.swing.JTextField();
        btneliminar = new javax.swing.JButton();
        btneditar1 = new javax.swing.JButton();
        agregarbtn = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtidcarro = new javax.swing.JTextField();
        txtPlaca = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtModelo = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txtIdpersona = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(184, 224, 233));

        jPanel2.setBackground(new java.awt.Color(44, 116, 142));

        jLabel2.setFont(new java.awt.Font("Bernard MT Condensed", 0, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("BIENVENIDO");

        jLabel1.setText("¿Que desea hacer hoy?");

        btnactualizartabla.setBackground(new java.awt.Color(184, 224, 233));
        btnactualizartabla.setFont(new java.awt.Font("Bernard MT Condensed", 0, 24)); // NOI18N
        btnactualizartabla.setForeground(new java.awt.Color(44, 116, 142));
        btnactualizartabla.setText("ESPACIO");
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

        btnactualizartabla1.setBackground(new java.awt.Color(184, 224, 233));
        btnactualizartabla1.setFont(new java.awt.Font("Bernard MT Condensed", 0, 24)); // NOI18N
        btnactualizartabla1.setForeground(new java.awt.Color(44, 116, 142));
        btnactualizartabla1.setText("AGREGAR NUEVA MOTO");
        btnactualizartabla1.setBorder(null);
        btnactualizartabla1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnactualizartabla1ActionPerformed(evt);
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
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(74, 74, 74))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnactualizartabla1, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btns, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnactualizartabla, javax.swing.GroupLayout.PREFERRED_SIZE, 247, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 15, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnactualizartabla1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnactualizartabla, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40)
                .addComponent(btns, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(101, 101, 101))
        );

        jPanel3.setBackground(new java.awt.Color(44, 116, 142));

        tablarol.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tablarol.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablarolMouseClicked(evt);
            }
        });
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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(296, 296, 296)
                .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(413, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(157, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(agregarbtn)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btneditar1)
                        .addGap(277, 277, 277)
                        .addComponent(btneliminar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 768, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(125, 125, 125))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 300, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btneliminar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btneditar1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(82, 82, 82))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(60, 60, 60)
                        .addComponent(agregarbtn)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setText("CARRO");

        jLabel4.setText("ID DEL CARRO");

        txtidcarro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtidcarroActionPerformed(evt);
            }
        });

        txtPlaca.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPlacaActionPerformed(evt);
            }
        });

        jLabel5.setText("PLACA");

        jLabel6.setText("MODELO");

        jLabel7.setText("ID PERSONA");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(935, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel4)
                            .addComponent(txtidcarro)
                            .addComponent(txtPlaca)
                            .addComponent(txtModelo)
                            .addComponent(txtIdpersona, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(125, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(80, 80, 80)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtidcarro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtIdpersona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(53, 53, 53))
        );

        getContentPane().add(jPanel1, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnactualizartablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizartablaActionPerformed
            EspacioFrame espacioFrame = new EspacioFrame();
    espacioFrame.setVisible(true);

    // Opcional: cerrar el frame actual (si no quieres que se quede abierto)
    this.dispose();
    }//GEN-LAST:event_btnactualizartablaActionPerformed

    private void btnsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnsActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnsActionPerformed

   
    
    private void txtBusquedaCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_txtBusquedaCaretUpdate
       
        
    }//GEN-LAST:event_txtBusquedaCaretUpdate

    private void txtBusquedaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBusquedaActionPerformed
     
      
    
    }//GEN-LAST:event_txtBusquedaActionPerformed

    private void btneliminarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneliminarActionPerformed
        int fila = tablarol.getSelectedRow();
    if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione un auto para eliminar.",
                "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int idVehiculo = (int) tablarol.getValueAt(fila, 0);

    int confirm = JOptionPane.showConfirmDialog(this,
            "¿Seguro que deseas eliminar este auto?",
            "Confirmar", JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        boolean ok = AutoDAO.eliminarVehiculoYDependencias(idVehiculo);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Auto eliminado correctamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar el auto.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    }//GEN-LAST:event_btneliminarActionPerformed

    private void btneditar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditar1ActionPerformed
            int row = tablarol.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Selecciona un auto.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int idVehiculo = (int) tablarol.getValueAt(row, 0);
    Auto actual = AutoDAO.obtenerAutoPorId(idVehiculo); // nombre correcto
    if (actual == null) {
        JOptionPane.showMessageDialog(this, "No se pudo cargar el auto seleccionado.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String nuevaPlaca = (String) JOptionPane.showInputDialog(this, "Placa:", "Editar auto",
            JOptionPane.PLAIN_MESSAGE, null, null, actual.getPlaca());
    if (nuevaPlaca == null) return;
    nuevaPlaca = nuevaPlaca.trim().toUpperCase().replaceAll("\\s+", "");

    String nuevoModelo = (String) JOptionPane.showInputDialog(this, "Modelo:", "Editar auto",
            JOptionPane.PLAIN_MESSAGE, null, null, actual.getModelo());
    if (nuevoModelo == null) return;
    nuevoModelo = nuevoModelo.trim();

    String nuevoTipoAuto = (String) JOptionPane.showInputDialog(this, "Tipo de auto:", "Editar auto",
            JOptionPane.PLAIN_MESSAGE, null, null, actual.getTipoAuto());
    if (nuevoTipoAuto == null) return;
    nuevoTipoAuto = nuevoTipoAuto.trim().toLowerCase();

    String idPersonaStr = (String) JOptionPane.showInputDialog(this, "ID Persona:", "Editar auto",
            JOptionPane.PLAIN_MESSAGE, null, null, String.valueOf(actual.getIdPersona()));
    if (idPersonaStr == null) return;

    int nuevoIdPersona;
    try {
        nuevoIdPersona = Integer.parseInt(idPersonaStr.trim());
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "ID Persona debe ser entero.", "Validación", JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (nuevaPlaca.isEmpty() || nuevoModelo.isEmpty() || nuevoTipoAuto.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
        return;
    }
    if (!nuevaPlaca.matches("[A-Z0-9]{5,10}")) {
        JOptionPane.showMessageDialog(this, "Formato de placa inválido.", "Validación", JOptionPane.WARNING_MESSAGE);
        return;
    }
    if (!nuevaPlaca.equalsIgnoreCase(actual.getPlaca()) && VehiculoDAO.existePlaca(nuevaPlaca, idVehiculo)) {
        JOptionPane.showMessageDialog(this, "Ya existe un vehículo con esa placa.", "Duplicado", JOptionPane.WARNING_MESSAGE);
        return;
    }

    boolean okVehiculo = AutoDAO.actualizarVehiculoDeAuto(idVehiculo, nuevaPlaca, actual.getMarca(), nuevoModelo, nuevoIdPersona);
    boolean okTipo     = AutoDAO.actualizarTipoAuto(idVehiculo, nuevoTipoAuto);

    if (okVehiculo && okTipo) {
        JOptionPane.showMessageDialog(this, "Auto actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        cargarTabla();
    } else {
        JOptionPane.showMessageDialog(this, "No se pudo actualizar el auto.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    }//GEN-LAST:event_btneditar1ActionPerformed

    private void agregarbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarbtnActionPerformed
        // TODO add your handling code here:
          cargarTabla();   // <--- aquí                                    
    String placa = txtPlaca.getText().trim();
    String modelo = txtModelo.getText().trim();
    String idPersonaStr = txtIdpersona.getText().trim();

    // Validación básica
    if (placa.isEmpty() || modelo.isEmpty() || idPersonaStr.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.",
                "Validación", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int idPersona;
    try {
        idPersona = Integer.parseInt(idPersonaStr);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "ID Persona debe ser un número entero.",
                "Error de formato", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Validar unicidad de placa
    if (VehiculoDAO.existePlaca(placa, null)) {
        JOptionPane.showMessageDialog(this, "Ya existe un vehículo con esa placa.",
                "Duplicado", JOptionPane.WARNING_MESSAGE);
        return;
    }

    Vehiculo nuevo = new Vehiculo();
    nuevo.setPlaca(placa);
    nuevo.setModelo(modelo);
    nuevo.setIdPersona(idPersona);

    boolean exito = VehiculoDAO.insertarVehiculo(nuevo);

    if (exito) {
        JOptionPane.showMessageDialog(this, "Vehículo agregado correctamente.",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
        limpiarCampos();
        cargarTabla(); // Si tienes una JTable, refresca los datos
    } else {
        JOptionPane.showMessageDialog(this, "No se pudo agregar el vehículo.",
                "Error", JOptionPane.ERROR_MESSAGE);
    }

    }//GEN-LAST:event_agregarbtnActionPerformed

    private void txtidcarroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtidcarroActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtidcarroActionPerformed

    private void tablarolMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablarolMouseClicked
        // TODO add your handling code here:                                     
    JTable source = (JTable) evt.getSource();
    int row = source.rowAtPoint(evt.getPoint());
    int column = source.columnAtPoint(evt.getPoint());

    if (row >= 0 && column >= 0) {
        filaSeleccionada = row; // Guarda la fila seleccionada

        // Extraer los datos de la fila
        String idCarro = source.getValueAt(row, 0).toString();      // ID DEL CARRO
        String placa = source.getValueAt(row, 1).toString();        // PLACA
        String modelo = source.getValueAt(row, 2).toString();       // MODELO
        String idPersona = source.getValueAt(row, 3).toString();    // ID PERSONA

        // Aquí podrías llenar los campos del formulario CARRO con estos valores
        txtidcarro.setText(idCarro);
        txtPlaca.setText(placa);
        txtModelo.setText(modelo);
        txtIdpersona.setText(idPersona);
    }


    }//GEN-LAST:event_tablarolMouseClicked

    private void txtPlacaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPlacaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtPlacaActionPerformed

    private void btnactualizartabla1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizartabla1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnactualizartabla1ActionPerformed

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
            java.util.logging.Logger.getLogger(AutoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AutoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AutoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AutoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
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
                new AutoFrame().setVisible(true);
            }
        });
}
    private void limpiarCampos() {
    txtPlaca.setText("");
    txtModelo.setText("");
    txtIdpersona.setText("");
}


    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton agregarbtn;
    private javax.swing.JButton btnactualizartabla;
    private javax.swing.JButton btnactualizartabla1;
    private javax.swing.JButton btneditar1;
    private javax.swing.JButton btneliminar;
    private javax.swing.JButton btns;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablarol;
    private javax.swing.JTextField txtBusqueda;
    private javax.swing.JTextField txtIdpersona;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtPlaca;
    private javax.swing.JTextField txtidcarro;
    // End of variables declaration//GEN-END:variables
}

