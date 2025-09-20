package vistas;

import clasesjava.Vehiculo;
import clasesjava.Moto;
import clasesjava.MotoDAO;
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




public class MotoFrame extends javax.swing.JFrame {


    private DefaultTableModel modeloTabla;
    private int filaSeleccionada = -1;
     public MotoFrame() {
        initComponents();

        // En el constructor, después de initComponents();
        modeloTabla = new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID Vehículo", "Tipo de moto", "ID Persona", "Placa", "Modelo"} // <-- añade Modelo si lo quieres mostrar
        ) {
            @Override public boolean isCellEditable(int row, int column) { return false; }
        };
        tablarol.setModel(modeloTabla);


        cargarDatos(); 
    }
    


    // -------- Cargar datos en JTable (versión utilitaria estática) ----------
    private void cargarTabla() {
    List<Moto> lista = MotoDAO.listarTodas();
    DefaultTableModel modelo = (DefaultTableModel) tablarol.getModel();
    modelo.setRowCount(0);

    for (Moto m : lista) {
        Object[] fila = {
            m.getIdVehiculo(),
            m.getTipoMoto(),
            m.getIdPersona(),
            m.getPlaca(),
            m.getModelo()
        };
        modelo.addRow(fila);
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
        jTextField1 = new javax.swing.JTextField();
        txtTipoMoto = new javax.swing.JTextField();
        txtidpersona = new javax.swing.JTextField();
        txtPlaca = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txtModelo = new javax.swing.JTextField();

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
        btnactualizartabla1.setText("AGREGAR NUEVO CARRO");
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnactualizartabla, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btns, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(158, 158, 158))
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
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(296, 296, 296)
                        .addComponent(txtBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 341, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(78, 78, 78)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(agregarbtn)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btneditar1)
                                .addGap(281, 281, 281)
                                .addComponent(btneliminar))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 768, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(78, Short.MAX_VALUE))
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
                    .addComponent(agregarbtn))
                .addGap(34, 34, 34))
        );

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setText("Moto");

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });

        jLabel4.setText("ID DE LA MOTO");

        jLabel5.setText("TIPO DE MOTO");

        jLabel6.setText("ID PERSONA");

        jLabel7.setText("PLACA");

        jLabel8.setText("MODELO");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 376, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel4)
                            .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                            .addComponent(txtTipoMoto)
                            .addComponent(txtidpersona)
                            .addComponent(txtPlaca)
                            .addComponent(jLabel8)
                            .addComponent(txtModelo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 152, Short.MAX_VALUE)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(101, 101, 101))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(125, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jLabel5)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtTipoMoto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(40, 40, 40)
                        .addComponent(jLabel6)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtidpersona, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(50, 50, 50)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtPlaca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(59, 59, 59))
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
        JOptionPane.showMessageDialog(this, "Seleccione una moto para eliminar.",
                "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int idVehiculo = (int) tablarol.getValueAt(fila, 0);

    int confirm = JOptionPane.showConfirmDialog(this,
            "¿Seguro que deseas eliminar esta moto?",
            "Confirmar", JOptionPane.YES_NO_OPTION);

    if (confirm == JOptionPane.YES_OPTION) {
        boolean ok = MotoDAO.eliminarVehiculoYDependencias(idVehiculo);
        if (ok) {
            JOptionPane.showMessageDialog(this, "Moto eliminada correctamente.",
                    "Éxito", JOptionPane.INFORMATION_MESSAGE);
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo eliminar la moto.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    }//GEN-LAST:event_btneliminarActionPerformed

    private void btneditar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btneditar1ActionPerformed
             int row = tablarol.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(this, "Selecciona una moto.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        return;
    }

    int idVehiculo = (int) tablarol.getValueAt(row, 0);
    Moto actual = MotoDAO.obtenerPorIdVehiculo(idVehiculo);
    if (actual == null) {
        JOptionPane.showMessageDialog(this, "No se pudo cargar la moto seleccionada.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Diálogos simples para editar
    String nuevaPlaca = (String) JOptionPane.showInputDialog(this, "Placa:", "Editar moto",
            JOptionPane.PLAIN_MESSAGE, null, null, actual.getPlaca());
    if (nuevaPlaca == null) return; // cancelado
    nuevaPlaca = nuevaPlaca.trim().toUpperCase().replaceAll("\\s+", "");

    String nuevoModelo = (String) JOptionPane.showInputDialog(this, "Modelo:", "Editar moto",
            JOptionPane.PLAIN_MESSAGE, null, null, actual.getModelo());
    if (nuevoModelo == null) return;
    nuevoModelo = nuevoModelo.trim();

    String nuevoTipo = (String) JOptionPane.showInputDialog(this, "Tipo de moto:", "Editar moto",
            JOptionPane.PLAIN_MESSAGE, null, null, actual.getTipoMoto());
    if (nuevoTipo == null) return;
    nuevoTipo = nuevoTipo.trim().toLowerCase();

    String idPersonaStr = (String) JOptionPane.showInputDialog(this, "ID Persona:", "Editar moto",
            JOptionPane.PLAIN_MESSAGE, null, null, String.valueOf(actual.getIdPersona()));
    if (idPersonaStr == null) return;
    int nuevoIdPersona;
    try {
        nuevoIdPersona = Integer.parseInt(idPersonaStr.trim());
    } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(this, "ID Persona debe ser entero.", "Validación", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Validaciones básicas
    if (nuevaPlaca.isEmpty() || nuevoModelo.isEmpty() || nuevoTipo.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
        return;
    }
    if (!nuevaPlaca.matches("[A-Z0-9]{5,10}")) {
        JOptionPane.showMessageDialog(this, "Formato de placa inválido (5-10 caracteres alfanuméricos).", "Validación", JOptionPane.WARNING_MESSAGE);
        return;
    }
    if (!nuevaPlaca.equalsIgnoreCase(actual.getPlaca()) && VehiculoDAO.existePlaca(nuevaPlaca, idVehiculo)) {
        JOptionPane.showMessageDialog(this, "Ya existe un vehículo con esa placa.", "Duplicado", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Actualizar en BD (vehículo + tipo_moto)
    boolean okVehiculo = MotoDAO.actualizarVehiculoDeMoto(idVehiculo, nuevaPlaca, actual.getMarca(), nuevoModelo, nuevoIdPersona);
    boolean okTipo     = MotoDAO.actualizarTipoMoto(idVehiculo, nuevoTipo);

    if (okVehiculo && okTipo) {
        JOptionPane.showMessageDialog(this, "Moto actualizada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        cargarTabla();
    } else {
        JOptionPane.showMessageDialog(this, "No se pudo actualizar la moto.", "Error", JOptionPane.ERROR_MESSAGE);
    }

    }//GEN-LAST:event_btneditar1ActionPerformed

    private void agregarbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_agregarbtnActionPerformed
            // TODO add your handling code here:
         // TODO add your handling code here:
            String placa = txtPlaca.getText().trim();
            String modelo = txtModelo.getText().trim();
            String tipoMoto = txtTipoMoto.getText().trim();
            String idPersonaStr = txtidpersona.getText().trim();

            // Validación básica
            if (placa.isEmpty() || modelo.isEmpty() || tipoMoto.isEmpty() || idPersonaStr.isEmpty()) {
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

            // Crear objeto Vehiculo
            Vehiculo nuevo = new Vehiculo();
            nuevo.setPlaca(placa);
            nuevo.setModelo(modelo);
            nuevo.setIdPersona(idPersona);

            // Insertar en Vehiculo y obtener ID generado
            int idVehiculo = VehiculoDAO.insertarVehiculoYRetornarId(nuevo);

            if (idVehiculo > 0) {
                boolean motoInsertada = MotoDAO.insertarMoto(idVehiculo, tipoMoto);
                if (motoInsertada) {
                    JOptionPane.showMessageDialog(this, "Moto agregada correctamente.",
                            "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    limpiarCampos();
                    cargarTabla(); // refresca la JTable si aplica
                } else {
                    JOptionPane.showMessageDialog(this, "Vehículo agregado, pero falló al registrar como moto.",
                            "Advertencia", JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo agregar el vehículo.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }


    }//GEN-LAST:event_agregarbtnActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void txtModeloActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtModeloActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtModeloActionPerformed

    private void btnactualizartabla1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnactualizartabla1ActionPerformed
        // TODO add your handling code here:
        AutoFrame autoFrame = new AutoFrame();
    autoFrame.setVisible(true);

    // Opcional: cerrar el frame actual (si no quieres que se quede abierto)
    this.dispose();
    }//GEN-LAST:event_btnactualizartabla1ActionPerformed

    private void tablarolMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tablarolMouseClicked
        // TODO add your handling code here:
        JTable source = (JTable) evt.getSource();
    int row = source.rowAtPoint(evt.getPoint());
    int column = source.columnAtPoint(evt.getPoint());

    if (row >= 0 && column >= 0) {
        filaSeleccionada = row;

        // OJO: índices según tu modelo:
        // 0: ID Vehículo, 1: Tipo de moto, 2: ID Persona, 3: Placa, 4: Modelo
        String idVehiculo = source.getValueAt(row, 0).toString();
        String tipoMoto   = source.getValueAt(row, 1).toString();
        String idPersona  = source.getValueAt(row, 2).toString();
        String placa      = source.getValueAt(row, 3).toString();
        String modelo     = source.getValueAt(row, 4).toString();

        // Llena tus campos del formulario
        jTextField1.setText(idVehiculo);   // tu campo "ID DE LA MOTO" (realmente es id_vehiculo)
        txtTipoMoto.setText(tipoMoto);
        txtidpersona.setText(idPersona);
        txtPlaca.setText(placa);
        txtModelo.setText(modelo);
    }
    }//GEN-LAST:event_tablarolMouseClicked
private void limpiarCampos() {
    txtPlaca.setText("");
    txtTipoMoto.setText("");
    txtidpersona.setText("");
    txtModelo.setText("");
}

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
            java.util.logging.Logger.getLogger(MotoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MotoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MotoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MotoFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
       

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MotoFrame().setVisible(true);
            }
        });
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
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTable tablarol;
    private javax.swing.JTextField txtBusqueda;
    private javax.swing.JTextField txtModelo;
    private javax.swing.JTextField txtPlaca;
    private javax.swing.JTextField txtTipoMoto;
    private javax.swing.JTextField txtidpersona;
    // End of variables declaration//GEN-END:variables
}

