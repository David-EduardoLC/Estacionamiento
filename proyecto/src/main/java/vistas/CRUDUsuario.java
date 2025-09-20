package vistas;

import clasesjava.*;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class CRUDUsuario extends JFrame {
    private JTable tabla;
    private DefaultTableModel modeloTabla;
    private JButton btnCrear, btnActualizar, btnSalir;
    private JButton btnEditar, btnInformacion, btnEliminar;
    private Color colorFondo = new Color(173, 216, 230);
    private Color colorPanel = new Color(95, 158, 160);
    private Color colorBoton = new Color(176, 224, 230);
    private UsuarioDAO usuarioDAO;

    public CRUDUsuario() {
        setTitle("BIENVENIDO - Gestión de Usuarios");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(colorFondo);
        
        usuarioDAO = new UsuarioDAO();
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
        
        // Crear tabla con columnas exactas como en la imagen
        String[] columnas = {"ID", "Nombre", "Ubicación", "hora_aperuta", "hora_cierre"};
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
        btnCrear.addActionListener(e -> crearUsuario());
        btnActualizar.addActionListener(e -> actualizarUsuario());
        btnSalir.addActionListener(e -> System.exit(0));
        btnEditar.addActionListener(e -> editarUsuario());
        btnInformacion.addActionListener(e -> mostrarInformacion());
        btnEliminar.addActionListener(e -> eliminarUsuario());
    }

    private void crearUsuario() {
        JDialog dialog = new JDialog(this, "Crear Usuario", true);
        dialog.setLayout(new GridBagLayout());
        dialog.setSize(450, 350);
        dialog.setLocationRelativeTo(this);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JTextField txtUsername = new JTextField(20);
        JPasswordField txtPassword = new JPasswordField(20);
        
        // Combo para Rol
        List<Rol> roles = RolDAO.obtenerTodosRoles();
        JComboBox<Rol> cmbRol = new JComboBox<>(roles.toArray(new Rol[0]));
        
        // Combo para Ubicación (Carreras y Departamentos)
        JComboBox<String> cmbUbicacion = new JComboBox<>();
        
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
        dialog.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        dialog.add(txtUsername, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        dialog.add(txtPassword, gbc);
        
        gbc.gridx = 0; gbc.gridy = 2;
        dialog.add(new JLabel("Rol:"), gbc);
        gbc.gridx = 1;
        dialog.add(cmbRol, gbc);
        
        gbc.gridx = 0; gbc.gridy = 3;
        dialog.add(new JLabel("Ubicación:"), gbc);
        gbc.gridx = 1;
        dialog.add(cmbUbicacion, gbc);
        
        JPanel panelBotones = new JPanel();
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnGuardar.addActionListener(ev -> {
            String username = txtUsername.getText().trim();
            String password = new String(txtPassword.getPassword());
            
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Complete todos los campos");
                return;
            }
            
            Usuario nuevoUsuario = new Usuario(0, username, password);
            if (usuarioDAO.insertarUsuario(nuevoUsuario)) {
                // Asignar rol al usuario
                Rol rolSeleccionado = (Rol) cmbRol.getSelectedItem();
                if (rolSeleccionado != null) {
                    // Obtener el usuario recién creado
                    List<Usuario> usuarios = usuarioDAO.buscarUsuarios(username);
                    if (!usuarios.isEmpty()) {
                        Usuario usuarioCreado = usuarios.get(0);
                        UsuarioRol ur = new UsuarioRol(0, usuarioCreado, rolSeleccionado);
                        UsuarioRolDAO urDAO = new UsuarioRolDAO();
                        urDAO.insertarUsuarioRol(ur);
                    }
                }
                
                JOptionPane.showMessageDialog(dialog, "Usuario creado correctamente");
                cargarDatos();
                dialog.dispose();
            }
        });
        
        btnCancelar.addActionListener(ev -> dialog.dispose());
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        gbc.gridx = 0; gbc.gridy = 4;
        gbc.gridwidth = 2;
        dialog.add(panelBotones, gbc);
        
        dialog.setVisible(true);
    }

    private void actualizarUsuario() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla");
            return;
        }
        
        int idUsuario = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        Usuario usuario = usuarioDAO.obtenerUsuarioPorId(idUsuario);
        
        if (usuario == null) return;
        
        JDialog dialog = new JDialog(this, "Actualizar Usuario", true);
        dialog.setLayout(new GridBagLayout());
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JTextField txtUsername = new JTextField(usuario.getUsername(), 20);
        JPasswordField txtPassword = new JPasswordField(usuario.getPassword(), 20);
        
        gbc.gridx = 0; gbc.gridy = 0;
        dialog.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        dialog.add(txtUsername, gbc);
        
        gbc.gridx = 0; gbc.gridy = 1;
        dialog.add(new JLabel("Nueva Password:"), gbc);
        gbc.gridx = 1;
        dialog.add(txtPassword, gbc);
        
        JPanel panelBotones = new JPanel();
        JButton btnGuardar = new JButton("Actualizar");
        JButton btnCancelar = new JButton("Cancelar");
        
        btnGuardar.addActionListener(ev -> {
            usuario.setUsername(txtUsername.getText().trim());
            usuario.setPassword(new String(txtPassword.getPassword()));
            
            if (usuarioDAO.actualizarUsuario(usuario)) {
                JOptionPane.showMessageDialog(dialog, "Usuario actualizado correctamente");
                cargarDatos();
                dialog.dispose();
            }
        });
        
        btnCancelar.addActionListener(ev -> dialog.dispose());
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        gbc.gridx = 0; gbc.gridy = 2;
        gbc.gridwidth = 2;
        dialog.add(panelBotones, gbc);
        
        dialog.setVisible(true);
    }

    private void editarUsuario() {
        actualizarUsuario();
    }

    private void mostrarInformacion() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla");
            return;
        }
        
        int idUsuario = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
        Usuario usuario = usuarioDAO.obtenerUsuarioPorId(idUsuario);
        
        if (usuario == null) return;
        
        // Obtener información del rol
        UsuarioRolDAO urDAO = new UsuarioRolDAO();
        List<UsuarioRol> roles = urDAO.obtenerRolesPorUsuario(idUsuario);
        String rolInfo = roles.isEmpty() ? "Sin rol asignado" : 
                        roles.get(0).getRol().getNombre();
        
        StringBuilder info = new StringBuilder();
        info.append("INFORMACIÓN DEL USUARIO\n\n");
        info.append("ID: ").append(usuario.getIdUsuario()).append("\n");
        info.append("Username: ").append(usuario.getUsername()).append("\n");
        info.append("Rol: ").append(rolInfo).append("\n");
        info.append("Ubicación: ").append(modeloTabla.getValueAt(filaSeleccionada, 2)).append("\n");
        info.append("Hora Apertura: ").append(modeloTabla.getValueAt(filaSeleccionada, 3)).append("\n");
        info.append("Hora Cierre: ").append(modeloTabla.getValueAt(filaSeleccionada, 4)).append("\n");
        
        JOptionPane.showMessageDialog(this, info.toString(), "Información", JOptionPane.INFORMATION_MESSAGE);
    }

    private void eliminarUsuario() {
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un usuario de la tabla");
            return;
        }
        
        int confirmacion = JOptionPane.showConfirmDialog(this,
            "¿Está seguro de eliminar este usuario?", "Confirmar eliminación",
            JOptionPane.YES_NO_OPTION);
        
        if (confirmacion == JOptionPane.YES_OPTION) {
            int idUsuario = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            
            // Primero eliminar las asignaciones de rol
            UsuarioRolDAO urDAO = new UsuarioRolDAO();
            List<UsuarioRol> roles = urDAO.obtenerRolesPorUsuario(idUsuario);
            for (UsuarioRol ur : roles) {
                urDAO.eliminarUsuarioRol(ur.getIdUsuarioRol());
            }
            
            // Luego eliminar el usuario
            if (usuarioDAO.eliminarUsuario(idUsuario)) {
                JOptionPane.showMessageDialog(this, "Usuario eliminado correctamente");
                cargarDatos();
            }
        }
    }

    private void cargarDatos() {
        modeloTabla.setRowCount(0);
        List<Usuario> usuarios = usuarioDAO.obtenerTodosUsuarios();
        UsuarioRolDAO urDAO = new UsuarioRolDAO();
        
        for (Usuario u : usuarios) {
            // Obtener rol del usuario
            List<UsuarioRol> roles = urDAO.obtenerRolesPorUsuario(u.getIdUsuario());
            String rolTexto = roles.isEmpty() ? "Sin rol" : roles.get(0).getRol().getNombre();
            
            // Determinar ubicación según el rol
            String ubicacion = "";
            if (rolTexto.equals("Administrador")) {
                ubicacion = "Oficina Principal";
            } else if (rolTexto.equals("Guardia")) {
                ubicacion = "Entrada Principal";
            } else {
                ubicacion = "Sin asignar";
            }
            
            // Horarios según el rol
            String horaApertura = rolTexto.equals("Guardia") ? "06:00" : "08:00";
            String horaCierre = rolTexto.equals("Guardia") ? "22:00" : "18:00";
            
            Object[] fila = {
                u.getIdUsuario(),
                u.getUsername(),
                ubicacion,
                horaApertura,
                horaCierre
            };
            modeloTabla.addRow(fila);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new CRUDUsuario().setVisible(true);
        });
    }
}