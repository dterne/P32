package prog2.vista;

import prog2.adaptador.Adaptador;
import javax.swing.*;

public class AppBiblioUB extends JFrame {

    private JPanel panelMain;
    private JButton btnGestioUsuaris;
    private JButton btnGestioExemplars;
    private JButton btnGestioPrestecs;
    private JButton btnGuardar;
    private JButton btnCarregar;

    private Adaptador adaptador = new Adaptador();

    public AppBiblioUB() {
        setTitle("Biblioteca UB");
        setContentPane(panelMain);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        btnGestioUsuaris.addActionListener(e -> {
            GestioUsuaris frm = new GestioUsuaris(this, adaptador);
            frm.setVisible(true);
        });

        btnGestioExemplars.addActionListener(e -> {
            GestioExemplars frm = new GestioExemplars(this, adaptador);
            frm.setVisible(true);
        });

        btnGestioPrestecs.addActionListener(e -> {
            GestioPrestecs frm = new GestioPrestecs(this, adaptador);
            frm.setVisible(true);
        });

        btnGuardar.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    adaptador.guardaDades(chooser.getSelectedFile().getAbsolutePath());
                    JOptionPane.showMessageDialog(this, "Dades guardades correctament.");
                } catch (BiblioException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            }
        });

        btnCarregar.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                try {
                    adaptador.carregaDades(chooser.getSelectedFile().getAbsolutePath());
                    JOptionPane.showMessageDialog(this, "Dades carregades correctament.");
                } catch (BiblioException ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            }
        });
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new AppBiblioUB().setVisible(true);
        });
    }
}