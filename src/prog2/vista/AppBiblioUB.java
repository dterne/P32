package prog2.vista;

import prog2.adaptador.Adaptador;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AppBiblioUB extends JFrame {
    private JButton btnGestioUsuaris;
    private JButton btnGestioExemplars;
    private JButton btnGestioPrestecs;
    private JButton btnGuardar;
    private JButton btnCarregar;
    private JPanel panelMain;

    private Adaptador adaptador;

    public AppBiblioUB() {
        adaptador = new Adaptador();

        setContentPane(panelMain);
        setTitle("Biblioteca UB");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Listeners
        btnGestioUsuaris.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GestioUsuaris dialog = new GestioUsuaris(AppBiblioUB.this, adaptador);
                dialog.setVisible(true);
            }
        });

        btnGestioExemplars.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GestioExemplars dialog = new GestioExemplars(AppBiblioUB.this, adaptador);
                dialog.setVisible(true);
            }
        });

        btnGestioPrestecs.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                GestioPrestecs dialog = new GestioPrestecs(AppBiblioUB.this, adaptador);
                dialog.setVisible(true);
            }
        });

        btnGuardar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                if (fc.showSaveDialog(AppBiblioUB.this) == JFileChooser.APPROVE_OPTION) {
                    try {
                        adaptador.guardaDades(fc.getSelectedFile().getAbsolutePath());
                        JOptionPane.showMessageDialog(AppBiblioUB.this, "Dades guardades");
                    } catch (BiblioException ex) {
                        JOptionPane.showMessageDialog(AppBiblioUB.this, ex.getMessage());
                    }
                }
            }
        });

        btnCarregar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fc = new JFileChooser();
                if (fc.showOpenDialog(AppBiblioUB.this) == JFileChooser.APPROVE_OPTION) {
                    try {
                        adaptador.carregaDades(fc.getSelectedFile().getAbsolutePath());
                        JOptionPane.showMessageDialog(AppBiblioUB.this, "Dades carregades");
                    } catch (BiblioException ex) {
                        JOptionPane.showMessageDialog(AppBiblioUB.this, ex.getMessage());
                    }
                }
            }
        });
    }
}