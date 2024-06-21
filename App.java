
package app;


import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

class Libro {
    private String titulo;
    private String isbn;
    private Autor autor;
    private Categoria categoria;

    public Libro(String titulo, String isbn, Autor autor, Categoria categoria) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.autor = autor;
        this.categoria = categoria;
    }

    public String mostrarInfo() {
        return "Libro: " + titulo + ", ISBN: " + isbn + ", Autor: " + autor.getNombre() + " " + autor.getApellido() + ", Categoría: " + categoria.getNombre();
    }

    public String getTitulo() { return titulo; }
    public String getIsbn() { return isbn; }
    public Autor getAutor() { return autor; }
    public Categoria getCategoria() { return categoria; }
}

class Autor {
    private String nombre;
    private String apellido;

    public Autor(String nombre, String apellido) {
        this.nombre = nombre;
        this.apellido = apellido;
    }

    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
}

class Usuario {
    private String nombre;
    private String apellido;
    private String idUsuario;

    public Usuario(String nombre, String apellido, String idUsuario) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.idUsuario = idUsuario;
    }

    public String mostrarInfo() {
        return "Usuario: " + nombre + " " + apellido + " (ID: " + idUsuario + ")";
    }

    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public String getIdUsuario() { return idUsuario; }
}

class Prestamo {
    private Libro libro;
    private Usuario usuario;
    private String fechaPrestamo;
    private String fechaDevolucion;

    public Prestamo(Libro libro, Usuario usuario, String fechaPrestamo, String fechaDevolucion) {
        this.libro = libro;
        this.usuario = usuario;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
    }

    public Libro getLibro() { return libro; }
    public Usuario getUsuario() { return usuario; }
    public String getFechaPrestamo() { return fechaPrestamo; }
    public String getFechaDevolucion() { return fechaDevolucion; }
    public void setFechaDevolucion(String fechaDevolucion) { this.fechaDevolucion = fechaDevolucion; }
}

class Biblioteca {
    private List<Libro> libros;
    private List<Usuario> usuarios;
    private List<Prestamo> prestamos;

    public Biblioteca() {
        libros = new ArrayList<>();
        usuarios = new ArrayList<>();
        prestamos = new ArrayList<>();
    }

    public void registrarLibro(Libro libro) {
        libros.add(libro);
    }

    public void registrarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }

    public void realizarPrestamo(Libro libro, Usuario usuario, String fechaPrestamo, String fechaDevolucion) {
        Prestamo prestamo = new Prestamo(libro, usuario, fechaPrestamo, fechaDevolucion);
        prestamos.add(prestamo);
    }

    public void devolverLibro(Prestamo prestamo, String fechaDevolucion) {
        prestamo.setFechaDevolucion(fechaDevolucion);
    }

    public List<String> mostrarLibros() {
        List<String> infoLibros = new ArrayList<>();
        for (Libro libro : libros) {
            infoLibros.add(libro.mostrarInfo());
        }
        return infoLibros;
    }

    public List<Libro> getLibros() { return libros; }
    public List<Usuario> getUsuarios() { return usuarios; }
    public List<Prestamo> getPrestamos() { return prestamos; }
}

class Categoria {
    private String nombre;

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() { return nombre; }
}

public class App extends JFrame {
    private Biblioteca biblioteca;
    private JTextField tituloEntry, isbnEntry, autorEntry, categoriaEntry;
    private JTextField nombreUsuarioEntry, apellidoUsuarioEntry, idUsuarioEntry;
    private JTextField libroPrestamoEntry, usuarioPrestamoEntry, fechaPrestamoEntry, fechaDevolucionEntry;
    private JList<String> librosListBox, usuariosListBox, prestamosListBox;

    public App() {
        biblioteca = new Biblioteca();
        setTitle("Gestión de Biblioteca");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createWidgets();
        pack();
        setLocationRelativeTo(null);
    }

    private void createWidgets() {
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Libros", createBookPanel());
        tabbedPane.addTab("Usuarios", createUserPanel());
        tabbedPane.addTab("Préstamos", createLoanPanel());
        add(tabbedPane);
    }

    private JPanel createBookPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Título:"), gbc);
        gbc.gridx = 1;
        tituloEntry = new JTextField(20);
        panel.add(tituloEntry, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("ISBN:"), gbc);
        gbc.gridx = 1;
        isbnEntry = new JTextField(20);
        panel.add(isbnEntry, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Autor (Nombre Apellido):"), gbc);
        gbc.gridx = 1;
        autorEntry = new JTextField(20);
        panel.add(autorEntry, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Categoría:"), gbc);
        gbc.gridx = 1;
        categoriaEntry = new JTextField(20);
        panel.add(categoriaEntry, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JButton registrarLibroButton = new JButton("Registrar Libro");
        registrarLibroButton.addActionListener(e -> registrarLibro());
        panel.add(registrarLibroButton, gbc);

        gbc.gridy = 5;
        librosListBox = new JList<>(new DefaultListModel<>());
        JScrollPane scrollPane = new JScrollPane(librosListBox);
        scrollPane.setPreferredSize(new Dimension(300, 200));
        panel.add(scrollPane, gbc);

        return panel;
    }

    private JPanel createUserPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        nombreUsuarioEntry = new JTextField(20);
        panel.add(nombreUsuarioEntry, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Apellido:"), gbc);
        gbc.gridx = 1;
        apellidoUsuarioEntry = new JTextField(20);
        panel.add(apellidoUsuarioEntry, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("ID Usuario:"), gbc);
        gbc.gridx = 1;
        idUsuarioEntry = new JTextField(20);
        panel.add(idUsuarioEntry, gbc);

        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        JButton registrarUsuarioButton = new JButton("Registrar Usuario");
        registrarUsuarioButton.addActionListener(e -> registrarUsuario());
        panel.add(registrarUsuarioButton, gbc);

        gbc.gridy = 4;
        usuariosListBox = new JList<>(new DefaultListModel<>());
        JScrollPane scrollPane = new JScrollPane(usuariosListBox);
        scrollPane.setPreferredSize(new Dimension(300, 200));
        panel.add(scrollPane, gbc);

        return panel;
    }

    private JPanel createLoanPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0; gbc.gridy = 0;
        panel.add(new JLabel("Libro (ISBN):"), gbc);
        gbc.gridx = 1;
        libroPrestamoEntry = new JTextField(20);
        panel.add(libroPrestamoEntry, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        panel.add(new JLabel("Usuario (ID):"), gbc);
        gbc.gridx = 1;
        usuarioPrestamoEntry = new JTextField(20);
        panel.add(usuarioPrestamoEntry, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        panel.add(new JLabel("Fecha Préstamo:"), gbc);
        gbc.gridx = 1;
        fechaPrestamoEntry = new JTextField(20);
        panel.add(fechaPrestamoEntry, gbc);

        gbc.gridx = 0; gbc.gridy = 3;
        panel.add(new JLabel("Fecha Devolución:"), gbc);
        gbc.gridx = 1;
        fechaDevolucionEntry = new JTextField(20);
        panel.add(fechaDevolucionEntry, gbc);

        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JButton realizarPrestamoButton = new JButton("Realizar Préstamo");
        realizarPrestamoButton.addActionListener(e -> realizarPrestamo());
        panel.add(realizarPrestamoButton, gbc);

        gbc.gridy = 5;
        prestamosListBox = new JList<>(new DefaultListModel<>());
        JScrollPane scrollPane = new JScrollPane(prestamosListBox);
        scrollPane.setPreferredSize(new Dimension(300, 200));
        panel.add(scrollPane, gbc);

        return panel;
    }

    private void registrarLibro() {
        String titulo = tituloEntry.getText();
        String isbn = isbnEntry.getText();
        String[] autorParts = autorEntry.getText().split(" ");
        String autorNombre = autorParts[0];
        String autorApellido = autorParts[1];
        String categoriaNombre = categoriaEntry.getText();

        Autor autor = new Autor(autorNombre, autorApellido);
        Categoria categoria = new Categoria(categoriaNombre);
        Libro libro = new Libro(titulo, isbn, autor, categoria);

        biblioteca.registrarLibro(libro);
        actualizarLibrosListBox();

        JOptionPane.showMessageDialog(this, "Libro registrado exitosamente");
    }

    private void registrarUsuario() {
        String nombre = nombreUsuarioEntry.getText();
        String apellido = apellidoUsuarioEntry.getText();
        String idUsuario = idUsuarioEntry.getText();

        Usuario usuario = new Usuario(nombre, apellido, idUsuario);
        biblioteca.registrarUsuario(usuario);
        actualizarUsuariosListBox();

        JOptionPane.showMessageDialog(this, "Usuario registrado exitosamente");
    }

    private void realizarPrestamo() {
        String isbn = libroPrestamoEntry.getText();
        String idUsuario = usuarioPrestamoEntry.getText();
        String fechaPrestamo = fechaPrestamoEntry.getText();
        String fechaDevolucion = fechaDevolucionEntry.getText();

        Libro libro = biblioteca.getLibros().stream()
                .filter(l -> l.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);

        Usuario usuario = biblioteca.getUsuarios().stream()
                .filter(u -> u.getIdUsuario().equals(idUsuario))
                .findFirst()
                .orElse(null);

        if (libro != null && usuario != null) {
            biblioteca.realizarPrestamo(libro, usuario, fechaPrestamo, fechaDevolucion);
            actualizarPrestamosListBox();
            JOptionPane.showMessageDialog(this, "Préstamo realizado exitosamente");
        } else {
            JOptionPane.showMessageDialog(this, "Libro o Usuario no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarLibrosListBox() {
        DefaultListModel<String> model = (DefaultListModel<String>) librosListBox.getModel();
        model.clear();
        for (String libroInfo : biblioteca.mostrarLibros()) {
            model.addElement(libroInfo);
        }
    }

    private void actualizarUsuariosListBox() {
        DefaultListModel<String> model = (DefaultListModel<String>) usuariosListBox.getModel();
        model.clear();
        for (Usuario usuario : biblioteca.getUsuarios()) {
            model.addElement(usuario.mostrarInfo());
        }
    }

    private void actualizarPrestamosListBox() {
        DefaultListModel<String> model = (DefaultListModel<String>) prestamosListBox.getModel();
        model.clear();
        for (Prestamo prestamo : biblioteca.getPrestamos()) {
            String prestamoInfo = "Libro: " + prestamo.getLibro().getTitulo() + ", Usuario: " + 
                                  prestamo.getUsuario().getNombre() + " " + prestamo.getUsuario().getApellido() + 
                                  ", Fecha de Préstamo: " + prestamo.getFechaPrestamo() + 
                                  ", Fecha de Devolución: " + prestamo.getFechaDevolucion();
            model.addElement(prestamoInfo);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App app = new App();
            app.setVisible(true);
        });
    }
}