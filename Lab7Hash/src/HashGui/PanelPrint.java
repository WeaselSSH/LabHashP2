package HashGui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import lab7hash.PSNUsers;
import lab7hash.trofeosAux;
import lab7hash.userAux;

public class PanelPrint extends JPanel {

    private JTextField txtBuscar;
    private JButton btnBuscar;

    private JPanel infoPanel;
    private JLabel lblUsernameValue;
    private JLabel lblPuntosValue;
    private JLabel lblTrofeosValue;

    private JPanel trophiesContainer;
    private JScrollPane scrollTrophies;

    private JPanel titlePanel;
    private JLabel labelTrophies;

    private PSNUsers psnUsers;

    public PanelPrint(PSNUsers psnUsers) {

        this.psnUsers = psnUsers;

        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        setBackground(Color.decode("#0F0F1A"));

        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
        searchPanel.setBackground(Color.decode("#0F0F1A"));

        JLabel lblBuscar = new JLabel("Buscar usuario:");
        lblBuscar.setForeground(Color.WHITE);
        lblBuscar.setFont(new Font("Arial", Font.BOLD, 14));

        txtBuscar = new JTextField();
        txtBuscar.setMaximumSize(new Dimension(300, 30));
        txtBuscar.setFont(new Font("Arial", Font.PLAIN, 14));

        btnBuscar = new JButton("Ver información");
        btnBuscar.setFont(new Font("Arial", Font.BOLD, 14));
        btnBuscar.setBackground(new Color(34, 34, 46));
        btnBuscar.setForeground(Color.WHITE);
        btnBuscar.setFocusPainted(false);
        btnBuscar.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        searchPanel.add(lblBuscar);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(txtBuscar);
        searchPanel.add(Box.createHorizontalStrut(10));
        searchPanel.add(btnBuscar);

        infoPanel = new JPanel(new GridLayout(4, 2, 10, 8));
        infoPanel.setBackground(new Color(26, 26, 36));
        infoPanel.setBorder(BorderFactory.createEmptyBorder(20, 15, 10, 15));
        infoPanel.setVisible(false);

        JLabel lblUsernameTitle = new JLabel("Username:");
        lblUsernameTitle.setForeground(new Color(160, 160, 160));
        lblUsernameTitle.setFont(new Font("Arial", Font.BOLD, 13));

        lblUsernameValue = new JLabel("-");
        lblUsernameValue.setForeground(Color.WHITE);
        lblUsernameValue.setFont(new Font("Arial", Font.PLAIN, 13));

        infoPanel.add(lblUsernameTitle);
        infoPanel.add(lblUsernameValue);

        JLabel lblPuntosTitle = new JLabel("Puntos:");
        lblPuntosTitle.setForeground(new Color(160, 160, 160));
        lblPuntosTitle.setFont(new Font("Arial", Font.BOLD, 13));

        lblPuntosValue = new JLabel("-");
        lblPuntosValue.setForeground(Color.WHITE);
        lblPuntosValue.setFont(new Font("Arial", Font.PLAIN, 13));

        infoPanel.add(lblPuntosTitle);
        infoPanel.add(lblPuntosValue);

        JLabel lblTrofeosTitle = new JLabel("Trofeos:");
        lblTrofeosTitle.setForeground(new Color(160, 160, 160));
        lblTrofeosTitle.setFont(new Font("Arial", Font.BOLD, 13));

        lblTrofeosValue = new JLabel("-");
        lblTrofeosValue.setForeground(Color.WHITE);
        lblTrofeosValue.setFont(new Font("Arial", Font.PLAIN, 13));

        infoPanel.add(lblTrofeosTitle);
        infoPanel.add(lblTrofeosValue);

        trophiesContainer = new JPanel();
        trophiesContainer.setLayout(new BoxLayout(trophiesContainer, BoxLayout.Y_AXIS));
        trophiesContainer.setBackground(Color.decode("#0F0F1A"));

        scrollTrophies = new JScrollPane(trophiesContainer);
        scrollTrophies.getViewport().setBackground(Color.decode("#0F0F1A"));
        scrollTrophies.setVisible(false);

        titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.decode("#0F0F1A"));

        labelTrophies = new JLabel("Trofeos del jugador");
        labelTrophies.setFont(new Font("Arial", Font.PLAIN, 13));
        labelTrophies.setForeground(Color.WHITE);
        labelTrophies.setHorizontalAlignment(SwingConstants.RIGHT);

        titlePanel.add(labelTrophies, BorderLayout.WEST);
        titlePanel.setVisible(false);

        add(searchPanel);
        add(Box.createVerticalStrut(15));
        add(infoPanel);
        add(Box.createVerticalStrut(15));
        add(titlePanel);
        add(Box.createVerticalStrut(15));
        add(scrollTrophies);

        btnBuscar.addActionListener(e -> {
            System.out.println("CLICK buscar");
            try {
                searchUser();
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this,
                        "Error leyendo archivos: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    public void showUserInfo() {
        titlePanel.setVisible(true);
        infoPanel.setVisible(true);
        scrollTrophies.setVisible(true);
        revalidate();
        repaint();
    }

    public void hideUserInfo() {
        titlePanel.setVisible(false);
        infoPanel.setVisible(false);
        scrollTrophies.setVisible(false);
        revalidate();
        repaint();
    }

    public void setUserInfo(String username, long puntos, int totalTrofeos, boolean activo) {
        if (!activo) {
            return;
        }

        lblUsernameValue.setText(username);
        lblPuntosValue.setText(String.valueOf(puntos));
        lblTrofeosValue.setText(String.valueOf(totalTrofeos));
    }

    public void removeTrophies() {
        trophiesContainer.removeAll();
        trophiesContainer.revalidate();
        trophiesContainer.repaint();
    }

    public void agregarTrofeo(String fecha, String tipo, String juego, String desc, ImageIcon icon) {
        JPanel row = new JPanel(new BorderLayout(10, 0));
        row.setBackground(new Color(26, 26, 36));
        row.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel imgLabel = new JLabel();
        if (icon != null) {
            Image scaled = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
            imgLabel.setIcon(new ImageIcon(scaled));
        }
        imgLabel.setPreferredSize(new Dimension(80, 80));
        row.add(imgLabel, BorderLayout.WEST);

        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(new Color(26, 26, 36));

        JLabel lblLinea1 = new JLabel(fecha + "  –  " + tipo);
        lblLinea1.setForeground(Color.WHITE);
        lblLinea1.setFont(new Font("Arial", Font.BOLD, 13));

        JLabel lblLinea2 = new JLabel("Juego: " + juego);
        lblLinea2.setForeground(Color.WHITE);
        lblLinea2.setFont(new Font("Arial", Font.PLAIN, 12));

        JLabel lblLinea3 = new JLabel("Descripción: " + desc);
        lblLinea3.setForeground(Color.WHITE);
        lblLinea3.setFont(new Font("Arial", Font.PLAIN, 12));

        textPanel.add(lblLinea1);
        textPanel.add(lblLinea2);
        textPanel.add(lblLinea3);

        row.add(textPanel, BorderLayout.CENTER);

        trophiesContainer.add(row);
        trophiesContainer.add(Box.createVerticalStrut(8));

        trophiesContainer.revalidate();
        trophiesContainer.repaint();
    }

    private void searchUser() throws IOException {
        String username = txtBuscar.getText().trim();

        if (username.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un username antes.");
            hideUserInfo();
            return;
        }

        userAux u = psnUsers.playerInfo(username);

        if (u == null) {
            JOptionPane.showMessageDialog(this, "Usuario no encontrado.");
            hideUserInfo();
            return;
        }

        System.out.println("Trofeos: " + u.getcantTrofeos());

        boolean activo = u.isActivo();
        if (!activo) {
            JOptionPane.showMessageDialog(this, "Usuario inactivo");
            hideUserInfo();
            return;
        }

        setUserInfo(u.getName(), u.getPuntos(), u.getcantTrofeos(), activo);
        ArrayList<trofeosAux> trofeos = u.getrofeos();

        if (trofeos == null || trofeos.isEmpty()) {
            labelTrophies.setText("Sin trofeos");
            removeTrophies();
            showUserInfo();
            return;
        }

        labelTrophies.setText("Trofeos");
        removeTrophies();
        for (trofeosAux trofeo : trofeos) {

            byte[] imgBytes = trofeo.getbytesImag();
            ImageIcon icon = null;

            if (imgBytes != null && imgBytes.length > 0) {
                icon = new ImageIcon(imgBytes);
            }

            agregarTrofeo(trofeo.getfecha(), trofeo.getTipo(), trofeo.getnomjuego(),
                    trofeo.getnomtrofeo(), icon
            );
        }
        showUserInfo();
    }
}
