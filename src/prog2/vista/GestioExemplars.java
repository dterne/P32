package prog2.vista;

import prog2.adaptador.Adaptador;
import prog2.model.Exemplar;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestioExemplars extends JDialog {
    private JPanel panelMain;
    private JList<Exemplar> listExemplars;
    private JTextField txtId;
    private JTextField txtTitol;
    private JTextField txtAutor;
    private JCheckBox chkAdmetLlarg;
    private JButton btnAfegir;
    private JButton btnTancar;

    private Adaptador adaptador;
    private DefaultListModel<Exemplar> modelLlista;

    public GestioExemplars(JFrame parent, Adaptador adaptador) {
        super(parent, "Gestió Exemplars", true);
        this.adaptador = adaptador;

        setContentPane(panelMain);
        setSize(450, 400);
        setLocationRelativeTo(parent);

        modelLlista = new DefaultListModel<>();
        listExemplars.setModel(modelLlista);

        carregarLlista();

        btnAfegir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String id = txtId.getText();
                    String titol = txtTitol.getText();
                    String autor = txtAutor.getText();
                    boolean admetLlarg = chkAdmetLlarg.isSelected();

                    adaptador.getDades().afegirExemplar(id, titol, autor, admetLlarg);

                    txtId.setText("");
                    txtTitol.setText("");
                    txtAutor.setText("");
                    chkAdmetLlarg.setSelected(false);

                    carregarLlista();

                } catch (BiblioException ex) {
                    JOptionPane.showMessageDialog(GestioExemplars.this, ex.getMessage());
                }
            }
        });

        btnTancar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void carregarLlista() {
        modelLlista.clear();
        for (Exemplar e : adaptador.getDades().recuperaExemplars()) {
            modelLlista.addElement(e);
        }
    }
}