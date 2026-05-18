package prog2.vista;

import prog2.adaptador.Adaptador;
import prog2.model.Exemplar;
import prog2.model.Prestec;
import prog2.model.Usuari;

import javax.swing.*;
import java.util.ArrayList;

public class GestioPrestecs extends JDialog {
    private JPanel panelMain;
    private JList<Prestec> listPrestecs;
    private JComboBox<Exemplar> cmbExemplar;
    private JComboBox<Usuari> cmbUsuari;
    private JCheckBox chkEsLlarg;
    private JButton btnAfegir;
    private JButton btnRetornar;
    private JButton btnRefrescar;
    private JCheckBox chkNomesNoRetornats;
    private JButton btnTancar;

    private DefaultListModel<Prestec> listModel;
    private Adaptador adaptador;

    public GestioPrestecs(JFrame parent, Adaptador adaptador) {
        super(parent, "Gestió de Préstecs", true);
        this.adaptador = adaptador;

        setContentPane(panelMain);
        setSize(600, 500);
        setLocationRelativeTo(parent);

        listModel = new DefaultListModel<>();
        listPrestecs.setModel(listModel);

        // Omplir comboboxes
        refrescarComboboxes();
        refrescarLlista();

        // Accions
        btnAfegir.addActionListener(e -> afegirPrestec());
        btnRetornar.addActionListener(e -> retornarPrestec());
        btnRefrescar.addActionListener(e -> {
            refrescarComboboxes();
            refrescarLlista();
        });
        chkNomesNoRetornats.addActionListener(e -> refrescarLlista());
        btnTancar.addActionListener(e -> dispose());
    }

    private void refrescarComboboxes() {
        cmbExemplar.removeAllItems();
        cmbUsuari.removeAllItems();

        // Només mostrar exemplars disponibles
        for (Exemplar e : adaptador.getDades().recuperaExemplars()) {
            if (e.isDisponible()) {
                cmbExemplar.addItem(e);
            }
        }

        for (Usuari u : adaptador.getDades().recuperaUsuaris()) {
            cmbUsuari.addItem(u);
        }
    }

    private void refrescarLlista() {
        listModel.clear();

        ArrayList<Prestec> prestecs;
        if (chkNomesNoRetornats.isSelected()) {
            prestecs = adaptador.getDades().recuperaPrestecsNoRetornats();
        } else {
            prestecs = adaptador.getDades().recuperaPrestecs();
        }

        for (Prestec p : prestecs) {
            listModel.addElement(p);
        }
    }

    private void afegirPrestec() {
        try {
            Exemplar exemplar = (Exemplar) cmbExemplar.getSelectedItem();
            Usuari usuari = (Usuari) cmbUsuari.getSelectedItem();
            boolean esLlarg = chkEsLlarg.isSelected();

            if (exemplar == null || usuari == null) {
                JOptionPane.showMessageDialog(this, "Selecciona un exemplar i un usuari");
                return;
            }

            // Buscar posicions
            int exemplarPos = -1;
            int usuariPos = -1;

            ArrayList<Exemplar> exemplars = adaptador.getDades().recuperaExemplars();
            for (int i = 0; i < exemplars.size(); i++) {
                if (exemplars.get(i).getId().equals(exemplar.getId())) {
                    exemplarPos = i;
                    break;
                }
            }

            ArrayList<Usuari> usuaris = adaptador.getDades().recuperaUsuaris();
            for (int i = 0; i < usuaris.size(); i++) {
                if (usuaris.get(i).getEmail().equals(usuari.getEmail())) {
                    usuariPos = i;
                    break;
                }
            }

            adaptador.getDades().afegirPrestec(exemplarPos, usuariPos, esLlarg);

            refrescarComboboxes();
            refrescarLlista();

            JOptionPane.showMessageDialog(this, "Préstec creat correctament!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void retornarPrestec() {
        try {
            Prestec p = listPrestecs.getSelectedValue();
            if (p == null) {
                JOptionPane.showMessageDialog(this, "Selecciona un préstec per retornar");
                return;
            }

            if (p.getRetornat()) {
                JOptionPane.showMessageDialog(this, "Aquest préstec ja ha estat retornat");
                return;
            }

            // Buscar posició
            int pos = -1;
            ArrayList<Prestec> prestecs = adaptador.getDades().recuperaPrestecs();
            for (int i = 0; i < prestecs.size(); i++) {
                if (prestecs.get(i).equals(p)) {
                    pos = i;
                    break;
                }
            }

            adaptador.getDades().retornarPrestec(pos);

            refrescarComboboxes();
            refrescarLlista();

            JOptionPane.showMessageDialog(this, "Préstec retornat correctament!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}