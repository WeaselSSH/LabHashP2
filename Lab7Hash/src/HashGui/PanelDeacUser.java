package HashGui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import lab7hash.PSNUsers;

public class PanelDeacUser extends JPanel {

    private JLabel lblTitle;
    private JTextField txtUsername;
    private JButton btnDeac;
    private PSNUsers psn;

    public PanelDeacUser(PSNUsers psn) {
        this.psn = psn;

        setBackground(Color.decode("#0F0F1A"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        lblTitle = new JLabel("Desactivar Usuario");
        lblTitle.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setForeground(Color.WHITE);

        txtUsername = new JTextField();
        txtUsername.setMaximumSize(new Dimension(300, 35));
        txtUsername.setFont(new Font("Arial", Font.PLAIN, 14));
        txtUsername.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnDeac = new JButton("Desactivar");
        btnDeac.setFont(new Font("Arial", Font.BOLD, 16));
        btnDeac.setMaximumSize(new Dimension(200, 40));
        btnDeac.setAlignmentX(Component.CENTER_ALIGNMENT);

        btnDeac.addActionListener(e -> desactivarUsuario());

        add(lblTitle);
        add(Box.createVerticalStrut(30));
        add(txtUsername);
        add(Box.createVerticalStrut(20));
        add(btnDeac);

    }

    private void desactivarUsuario() {
        String username = txtUsername.getText();
        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Error: Favor ingrese un usuario.");
            return;
        }
        try {
            if (psn.deactivateUser(username)) {
                JOptionPane.showMessageDialog(this, "Usuario desactivado con éxito.");
                txtUsername.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Error: usuario no encontrado o ya inactivo.");
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error al desactivar usuario: " + ex.getMessage());
        }
    }
}
