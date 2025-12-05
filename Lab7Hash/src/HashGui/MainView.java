package HashGui;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    JPanel container;
    CardLayout card;
    
    JPanel menuPanel;
    
    JButton btnNewUser = new JButton("Agregar Usuario");
    JButton btnDeacUser = new JButton("Desactivar Usuario");
    JButton btnNewTrophy = new JButton("Agregar Trofeos");
    JButton btnPrint = new JButton("Ver Información");
    
    Font font = new Font("Arial", Font.BOLD, 14);
    public MainView() {
        setTitle("PSN Manager");
        setSize(800, 600);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        getContentPane().setBackground(Color.decode("#0F0F1A"));
        
        card = new CardLayout();
        container = new JPanel(card);
        container.setBackground(Color.decode("#0F0F1A"));
        
        container.add(new PanelNewUser(), "newUser");
        container.add(new PanelDeacUser(), "deacUser");
        container.add(new PanelNewTrophy(), "newTrophy");
        container.add(new PanelPrint(),"printUser");
        
        menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        menuPanel.setBackground(new Color(20, 20, 30));
        menuPanel.setPreferredSize(new Dimension(230, getHeight()));
        
        configurarBoton(btnNewUser);
        configurarBoton(btnDeacUser);
        configurarBoton(btnNewTrophy);
        configurarBoton(btnPrint);
        
        menuPanel.add(btnNewUser);
        menuPanel.add(Box.createVerticalStrut(40));
        menuPanel.add(btnDeacUser);
        menuPanel.add(Box.createVerticalStrut(40));
        menuPanel.add(btnNewTrophy);
        menuPanel.add(Box.createVerticalStrut(40));
        menuPanel.add(btnPrint);
        
        btnNewUser.addActionListener(e -> {
            card.show(container, "newUser");
        });
        btnDeacUser.addActionListener(e -> {
            card.show(container, "deacUser");
        });
        btnNewTrophy.addActionListener(e -> {
            card.show(container, "newTrophy");
        });
        btnPrint.addActionListener(e->{
            card.show(container, "printUser");
        });
        
        setLayout(new BorderLayout());
        add(menuPanel, BorderLayout.WEST);
        add(container, BorderLayout.CENTER);
        
        setVisible(true);
    }
    
    private void configurarBoton(JButton b) {
        b.setFont(font);
        b.setMaximumSize(new Dimension(200, 40));
        b.setAlignmentX(Component.CENTER_ALIGNMENT);
        b.setBackground(new Color(34, 34, 46));
        b.setForeground(Color.WHITE);
        b.setFocusPainted(false);
        b.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        b.setOpaque(true);
    }
    
    public static void main(String[] args) {
        new MainView();
    }
}
