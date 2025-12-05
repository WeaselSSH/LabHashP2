package HashGui;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;

public class PanelNewTrophy extends JPanel {

    private JLabel lblTitle;

    private JTextField txtUsername;
    private JTextField txtGame;
    private JTextField txtTrophyName;

    private JComboBox<String> cbType;

    private JButton btnChooseImage;
    private JButton btnAdd;

    private JLabel imgPreview;
    private File selectedImageFile;

    public PanelNewTrophy() {

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

            FileNameExtensionFilter filter = new FileNameExtensionFilter("Imágenes (.png, .jpg, .jpeg)",
                    "png", "jpg", "jpeg");
            chooser.setFileFilter(filter);

            chooser.showOpenDialog(this);
            selectedImageFile = chooser.getSelectedFile();
            mostrarPreview(selectedImageFile);
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
}
