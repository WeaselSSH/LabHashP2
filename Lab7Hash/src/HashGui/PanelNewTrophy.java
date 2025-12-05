package HashGui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.swing.filechooser.FileNameExtensionFilter;
import lab7hash.PSNUsers;
import lab7hash.Trophy;

public class PanelNewTrophy extends JPanel {

    private PSNUsers psn;

    private JLabel lblTitle;

    private JTextField txtUsername;
    private JTextField txtGame;
    private JTextField txtTrophyName;

    private JComboBox<String> cbType;

    private JButton btnChooseImage;
    private JButton btnAdd;

    private JLabel imgPreview;
    private File selectedImageFile;

    public PanelNewTrophy(PSNUsers psn) {
        this.psn = psn;

        setBackground(Color.decode("#0F0F1A"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        lblTitle = new JLabel("Agregar Trofeo");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setForeground(Color.WHITE);

        txtUsername = createTextFld("Username del jugador");
        txtGame = createTextFld("Nombre del juego");
        txtTrophyName = createTextFld("Nombre o descripción del trofeo");

        cbType = new JComboBox<>(new String[]{"PLATINO", "ORO", "PLATA", "BRONCE"});
        cbType.setMaximumSize(new Dimension(300, 35));
        cbType.setFont(new Font("Arial", Font.PLAIN, 14));
        cbType.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnChooseImage = new JButton("Seleccionar imagen");
        btnChooseImage.setFont(new Font("Arial", Font.BOLD, 14));
        btnChooseImage.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnChooseImage.setMaximumSize(new Dimension(250, 40));

        btnAdd = new JButton("Agregar Trofeo");
        btnAdd.setFont(new Font("Arial", Font.BOLD, 16));
        btnAdd.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAdd.setMaximumSize(new Dimension(250, 40));

        imgPreview = new JLabel();
        imgPreview.setPreferredSize(new Dimension(100, 100));
        imgPreview.setMaximumSize(new Dimension(100, 100));
        imgPreview.setOpaque(true);
        imgPreview.setBackground(Color.LIGHT_GRAY);
        imgPreview.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        imgPreview.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnChooseImage.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "Imágenes (.png, .jpg, .jpeg)", "png", "jpg", "jpeg");
            chooser.setFileFilter(filter);

            int result = chooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                selectedImageFile = chooser.getSelectedFile();
                mostrarPreview(selectedImageFile);
            }
        });

        add(lblTitle);
        add(Box.createVerticalStrut(15));

        add(txtUsername);
        add(Box.createVerticalStrut(15));

        add(txtGame);
        add(Box.createVerticalStrut(15));

        add(txtTrophyName);
        add(Box.createVerticalStrut(15));

        add(cbType);
        add(Box.createVerticalStrut(15));

        add(imgPreview);
        add(Box.createVerticalStrut(15));

        add(btnChooseImage);
        add(Box.createVerticalStrut(15));

        add(btnAdd);

        btnAdd.addActionListener(e -> agregarTrofeo());
    }

    private JTextField createTextFld(String title) {
        JTextField tf = new JTextField();
        tf.setMaximumSize(new Dimension(300, 35));
        tf.setFont(new Font("Arial", Font.PLAIN, 14));
        tf.setAlignmentX(Component.CENTER_ALIGNMENT);
        tf.setBorder(BorderFactory.createTitledBorder(title));
        return tf;
    }

    private void mostrarPreview(File imgFile) {
        ImageIcon icon = new ImageIcon(imgFile.getAbsolutePath());
        Image scaled = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        imgPreview.setIcon(new ImageIcon(scaled));
    }

    private void agregarTrofeo() {
        String username = txtUsername.getText();
        String game = txtGame.getText();
        String trophyName = txtTrophyName.getText();
        String tipoTexto = (String) cbType.getSelectedItem();

        if (username.isBlank() || game.isBlank() || trophyName.isBlank()) {
            JOptionPane.showMessageDialog(this, "Error: complete todos los campos.");
            return;
        }
        if (selectedImageFile == null) {
            JOptionPane.showMessageDialog(this, "Error: seleccione una imagen.");
            return;
        }

        Trophy tipo;
        tipo = Trophy.valueOf(tipoTexto);

        byte imagenBytes[];
        try {
            imagenBytes = Files.readAllBytes(Path.of(selectedImageFile.getAbsolutePath()));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al leer la imagen: " + ex.getMessage());
            return;
        }

        try {
            if (psn.addTrophy(username, game, trophyName, tipo, imagenBytes)) {
                JOptionPane.showMessageDialog(this, "Trofeo agregado con éxito.");
                txtUsername.setText("");
                txtGame.setText("");
                txtTrophyName.setText("");
                selectedImageFile = null;
                imgPreview.setIcon(null);
            } else {
                JOptionPane.showMessageDialog(this, "Error: usuario no encontrado.");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar trofeo: " + ex.getMessage());
        }
    }
}
