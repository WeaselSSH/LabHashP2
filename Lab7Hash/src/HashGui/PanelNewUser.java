package HashGui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import lab7hash.PSNUsers;

public class PanelNewUser extends JPanel {

    private JLabel lblTitle;
    private JTextField txtUsername;
    private JButton btnAdd;
    private PSNUsers psn;

    public PanelNewUser(PSNUsers psn) {
        this.psn = psn;

        setBackground(Color.decode("#0F0F1A"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        lblTitle = new JLabel("Agregar Usuario");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setForeground(Color.WHITE);

        txtUsername = new JTextField();
        txtUsername.setMaximumSize(new Dimension(300, 35));
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        txtUsername.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnAdd = new JButton("Agregar");
        btnAdd.setFont(new Font("Arial", Font.BOLD, 16));
        btnAdd.setMaximumSize(new Dimension(200, 40));
        btnAdd.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnAdd.addActionListener(e -> agregarUsuario());

        add(lblTitle);
        add(Box.createVerticalStrut(30));
        add(txtUsername);
        add(Box.createVerticalStrut(20));
        add(btnAdd);
    }

    private void agregarUsuario() {
        String username = txtUsername.getText();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Error: Favor ingrese un usuario.");
            return;
        }
        try {
            if (psn.addUser(username)) {
                JOptionPane.showMessageDialog(this, "Usuario agregado con éxito.");
                txtUsername.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Error: el usuario ya existe y está activo.");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar usuario: " + ex.getMessage());
        }
    }
}
