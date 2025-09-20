package vistas;

import clasesjava.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class CRUDUsuarioRol extends JFrame {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JButton btnCrear, btnActualizar, btnSalir;
    private JButton btnEditar, btnInformacion, btnEliminar;
    private Color colorFondo = new Color(173, 216, 230);
    private Color colorPanel = new Color(95, 158, 160);
    private Color colorBoton = new Color(176, 224, 230);
    private UsuarioRolDAO usuarioRolDAO;

    public CRUDUsuarioRol() {
        setTitle("BIENVENIDO - Gestión de Roles de Usuario");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(colorFondo);
        
        usuarioRolDAO = new UsuarioRolDAO();
        inicializarComponentes();
        cargarDatos();
        
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    private void inicializarComponentes() {
        // Panel superior con título
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(colorPanel);
        panelTitulo.setLayout(new FlowLayout(FlowLayout.LEFT));
        JLabel lblTitulo = new JLabel("BIENVENIDO");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblTitulo);
        
        JLabel lblSubtitulo = new JLabel("     ¿Qué desea hacer hoy?");
        lblSubtitulo.setFont(new Font("Arial", Font.PLAIN, 14));
        lblSubtitulo.setForeground(Color.WHITE);
        panelTitulo.add(lblSubtitulo);
        
        // Panel izquierdo con botones principales
        JPanel panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new GridLayout(3, 1, 20, 20));
        panelIzquierdo.setBackground(colorPanel);
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(50, 30, 50, 30));
        
        btnCrear = crearBotonPrincipal("Crear");
        btnActualizar = crearBotonPrincipal("Actualizar");
        btnSalir = crearBotonPrincipal("Salir");
        
        panelIzquierdo.add(btnCrear);
        panelIzquierdo.add(btnActualizar);
        panelIzquierdo.add(btnSalir);
        
        // Panel central con tabla
        JPanel panelCentral = new JPanel(new BorderLayout());
        panelCentral.setBackground(colorFondo);
        panelCentral.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Crear tabla
        String[] columnas = {"ID", "Usuario", "Rol", "Carrera/Depto", "Estado"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabla = new JTable(modeloTabla);
        tabla.setFont(new Font("Arial", Font.PLAIN, 12));
        tabla.setRowHeight(30);
        tabla.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabla.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabla.getTableHeader().setBackground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(tabla);
        scrollPane.setBackground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.WHITE);
        
        // Panel con tabla y borde
        JPanel panelTabla = new JPanel(new BorderLayout());
        panelTabla.setBackground(colorPanel);
        panelTabla.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panelTabla.add(scrollPane, BorderLayout.CENTER);
        
        panelCentral.add(panelTabla, BorderLayout.CENTER);
        
        // Panel derecho con botones de acción
        JPanel panelDerecho = new JPanel();
        panelDerecho.setLayout(new GridLayout(3, 1, 10, 10));
        panelDerecho.setBackground(colorPanel);
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(50, 20, 50, 30));
        
        btnEditar = crearBotonAccion("EDITAR");
        btnInformacion = crearBotonAccion("INFORMACIÓN");
        btnEliminar = crearBotonAccion("ELIMINAR");
        
        panelDerecho.add(btnEditar);
        panelDerecho.add(btnInformacion);
        panelDerecho.add(btnEliminar);
        
        // Agregar paneles al frame
        add(panelTitulo, BorderLayout.NORTH);
        add(panelIzquierdo, BorderLayout.WEST);
        add(panelCentral, BorderLayout.CENTER);
        add(panelDerecho, BorderLayout.EAST);
        
        // Configurar eventos
        configurarEventos();
    }

    private JButton crearBotonPrincipal(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 18));
        boton.setBackground(colorBoton);
        boton.setForeground(colorPanel);
        boton.setPreferredSize(new Dimension(200, 60));
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createRaisedBevelBorder());
        return boton;
    }

    private JButton crearBotonAccion(String texto) {
        JButton boton = new JButton(texto);
        boton.setFont(new Font("Arial", Font.BOLD, 12));
        boton.setBackground(Color.WHITE);
        boton.setForeground(colorPanel);
        boton.setPreferredSize(new Dimension(120, 40));
        boton.setFocusPainted(false);
        return boton;
    }

    private void configurarEventos() {
        btnCrear.addActionListener(e -> crearAsignacion());
        btnActualizar.addActionListener(e -> actualizarAsignacion());
        btnSalir.addActionListener(e -> System.exit(0));
        btnEditar.addActionListener(e -> editarAsignacion());
        btnInformacion.addActionListener(e -> mostrarInformacion());
        btnEliminar.addActionListener(e -> eliminarAsignacion());
    }

    private void crearAsignacion() {
        JDialog dialog = new JDialog(this, "Asignar Rol a Usuario", true);
        dialog.setLayout(new GridBagLayout());
        dialog.setSize(450, 300);
        dialog.setLocationRelativeTo(this);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Cargar usuarios
        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> usuarios = usuarioDAO.obtenerTodosUsuarios();
        JComboBox<Usuario> cmbUsuario = new JComboBox<>(usuarios.toArray(new Usuario[0]));
        
        // Cargar roles
        List<Rol> roles = RolDAO.obtenerTodosRoles();
        JComboBox<Rol> cmbRol = new JComboBox<>(roles.toArray(new Rol[0]));
        
        // Combo para Ubicación
        JComboBox<String> cmbUbicacion = new JComboBox<>();
        cmbUbicacion.addItem("Seleccione ubicación");
        
        // Agregar departamentos
        List<Departamento> departamentos = DepartamentoDAO.obtenerTodosDepartamentos();
        for (Departamento d : departamentos) {
            cmbUbicacion.addItem("Depto: " + d.getNombre());
        }
        
        // Agregar carreras
        List<Carrera> carreras = CarreraDAO.obtenerTodasCarreras();
        for (Carrera c : carreras) {
            cmbUbicacion.addItem("Carrera: " + c.getNombre());
        }
        
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 1;
        dialog.add(cmbUsuario, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Rol:"), gbc);
        gbc.gridx = 1;
        dialog.add(cmbRol, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Ubicación:"), gbc);
        gbc.gridx = 1;
        dialog.add(cmbUbicacion, gbc);
        
        JPanel panelBotones = new JPanel();
        JButton btnGuardar = new JButton("Asignar");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnGuardar.addActionListener(ev -> {
            Usuario usuario = (Usuario) cmbUsuario.getSelectedItem();
            Rol rol = (Rol) cmbRol.getSelectedItem();
            
            if (usuario == null || rol == null) {
                JOptionPane.showMessageDialog(dialog, "Seleccione usuario y rol");
                return;
            }
            
            // Verificar si ya existe la asignación
            if (usuarioRolDAO.existeUsuarioRol(usuario.getIdUsuario(), rol.getIdRol())) {
                JOptionPane.showMessageDialog(dialog, 
                    "Este usuario ya tiene asignado este rol", 
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            UsuarioRol nuevoUR = new UsuarioRol(0, usuario, rol);
            if (usuarioRolDAO.insertarUsuarioRol(nuevoUR)) {
                JOptionPane.showMessageDialog(dialog, "Rol asignado correctamente");
                cargarDatos();
                dialog.dispose();
            }
        });
        
        btnCancelar.addActionListener(ev -> dialog.dispose());
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        dialog.add(panelBotones, gbc);
        
        dialog.setVisible(true);
    }

    private void actualizarAsignacion() {
        editarAsignacion();
    }

    private void editarAsignacion() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una asignación de la tabla");
            return;
        }
        
        int idUsuarioRol = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        UsuarioRol ur = usuarioRolDAO.obtenerUsuarioRolPorId(idUsuarioRol);
        
        if (ur == null) return;
        
        JDialog dialog = new JDialog(this, "Actualizar Asignación de Rol", true);
        dialog.setLayout(new GridBagLayout());
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Cargar roles
        List<Rol> roles = RolDAO.obtenerTodosRoles();
        JComboBox<Rol> cmbRol = new JComboBox<>(roles.toArray(new Rol[0]));
        
        // Seleccionar el rol actual
        for (int i = 0; i < cmbRol.getItemCount(); i++) {
            if (cmbRol.getItemAt(i).getIdRol() == ur.getRol().getIdRol()) {
                cmbRol.setSelectedIndex(i);
                break;
            }
        }
        
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Usuario:"), gbc);
        gbc.gridx = 1;
        dialog.add(new JLabel(ur.getUsuario().getUsername()), gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Rol Actual:"), gbc);
        gbc.gridx = 1;
        dialog.add(new JLabel(ur.getRol().getNombre()), gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Nuevo Rol:"), gbc);
        gbc.gridx = 1;
        dialog.add(cmbRol, gbc);
        
        JPanel panelBotones = new JPanel();
        JButton btnGuardar = new JButton("Actualizar");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnGuardar.addActionListener(ev -> {
            Rol nuevoRol = (Rol) cmbRol.getSelectedItem();
            
            // Verificar si es diferente al actual
            if (nuevoRol.getIdRol() == ur.getRol().getIdRol()) {
                JOptionPane.showMessageDialog(dialog, "Seleccione un rol diferente");
                return;
            }
            
            // Verificar si ya existe esa combinación
            if (usuarioRolDAO.existeUsuarioRol(ur.getUsuario().getIdUsuario(), nuevoRol.getIdRol())) {
                JOptionPane.showMessageDialog(dialog, 
                    "Este usuario ya tiene asignado ese rol", 
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            ur.setRol(nuevoRol);
            
            if (usuarioRolDAO.actualizarUsuarioRol(ur)) {
                JOptionPane.showMessageDialog(dialog, "Asignación actualizada correctamente");
                cargarDatos();
                dialog.dispose();
            }
        });
        
        btnCancelar.addActionListener(ev -> dialog.dispose());
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        gbc.gridx = 0; gbc.gridy = 3;
        gbc.gridwidth = 2;
        dialog.add(panelBotones, gbc);
        
        dialog.setVisible(true);
    }

    private void mostrarInformacion() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una asignación de la tabla");
            return;
        }
        
        int idUsuarioRol = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        UsuarioRol ur = usuarioRolDAO.obtenerUsuarioRolPorId(idUsuarioRol);
        
        if (ur == null) return;
        
        StringBuilder info = new StringBuilder();
        info.append("INFORMACIÓN DE LA ASIGNACIÓN\n\n");
        info.append("ID Asignación: ").append(ur.getIdUsuarioRol()).append("\n");
        info.append("Usuario: ").append(ur.getUsuario().getUsername()).append("\n");
        info.append("ID Usuario: ").append(ur.getUsuario().getIdUsuario()).append("\n");
        info.append("Rol: ").append(ur.getRol().getNombre()).append("\n");
        info.append("Ubicación: ").append(modeloTabla.getValueAt(filaSeleccionada, 3)).append("\n");
        info.append("Estado: ").append(modeloTabla.getValueAt(filaSeleccionada, 4)).append("\n");
        
        JOptionPane.showMessageDialog(this, info.toString(), "Información Detallada", JOptionPane.INFORMATION_MESSAGE);
    }

    private void eliminarAsignacion() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione una asignación de la tabla");
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de eliminar esta asignación de rol?", 
            "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            int idUsuarioRol = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            if (usuarioRolDAO.eliminarUsuarioRol(idUsuarioRol)) {
                JOptionPane.showMessageDialog(this, "Asignación eliminada correctamente");
                cargarDatos();
            }
        }
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<UsuarioRol> asignaciones = usuarioRolDAO.obtenerTodosUsuarioRol();
        
        // Obtener listas de carreras y departamentos para mostrar
        List<Carrera> carreras = CarreraDAO.obtenerTodasCarreras();
        List<Departamento> departamentos = DepartamentoDAO.obtenerTodosDepartamentos();
        
        for (UsuarioRol ur : asignaciones) {
            // Determinar ubicación según el rol
            String ubicacion = "";
            String rolNombre = ur.getRol().getNombre();
            
            if (rolNombre.equalsIgnoreCase("Administrador")) {
                // Asignar departamento aleatorio o el primero
                if (!departamentos.isEmpty()) {
                    ubicacion = "Depto: " + departamentos.get(0).getNombre();
                } else {
                    ubicacion = "Oficina Principal";
                }
            } else if (rolNombre.equalsIgnoreCase("Guardia")) {
                // Asignar carrera aleatoria o la primera
                if (!carreras.isEmpty()) {
                    ubicacion = "Carrera: " + carreras.get(0).getNombre();
                } else {
                    ubicacion = "Entrada Principal";
                }
            } else {
                ubicacion = "Sin asignar";
            }
            
            // Estado siempre activo para usuarios con rol
            String estado = "Activo";
            
            Object[] fila = {
                ur.getIdUsuarioRol(),
                ur.getUsuario().getUsername(),
                ur.getRol().getNombre(),
                ubicacion,
                estado
            };
            modeloTabla.addRow(fila);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CRUDUsuarioRol().setVisible(true);
        });
    }
}