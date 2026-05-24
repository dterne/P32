package prog2.vista;

import prog2.adaptador.Adaptador;
import prog2.model.Exemplar;
import prog2.model.Prestec;
import prog2.model.Usuari;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class GestioPrestecs extends JDialog {
    private JPanel panelMain;
    private JList<Prestec> listPrestecs;
    private JComboBox<Exemplar> cmbExemplar;
    private JComboBox<Usuari> cmbUsuari;
    private JCheckBox chkEsLlarg;
    private JCheckBox chkNomésNoRetornats;
    private JButton btnAfegir;
    private JButton btnRetornar;
    private JButton btnTancar;

    private Adaptador adaptador;
    private DefaultListModel<Prestec> modelLlista;

    public GestioPrestecs(JFrame parent, Adaptador adaptador) {
        super(parent, "Gestió Préstecs", true);
        this.adaptador = adaptador;

        setContentPane(panelMain);
        setSize(500, 450);
        setLocationRelativeTo(parent);

        modelLlista = new DefaultListModel<>();
        listPrestecs.setModel(modelLlista);

        carregarComboboxes();
        carregarLlista();

        // Afegir préstec
        btnAfegir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Exemplar ex = (Exemplar) cmbExemplar.getSelectedItem();
                    Usuari us = (Usuari) cmbUsuari.getSelectedItem();
                    boolean esLlarg = chkEsLlarg.isSelected();

                    int posEx = -1;
                    int posUs = -1;

                    ArrayList<Exemplar> exemplars = adaptador.getDades().recuperaExemplars();
                    for (int i = 0; i < exemplars.size(); i++) {
                        if (exemplars.get(i).getId().equals(ex.getId())) {
                            posEx = i;
                            break;
                        }
                    }

                    ArrayList<Usuari> usuaris = adaptador.getDades().recuperaUsuaris();
                    for (int i = 0; i < usuaris.size(); i++) {
                        if (usuaris.get(i).getEmail().equals(us.getEmail())) {
                            posUs = i;
                            break;
                        }
                    }

                    adaptador.getDades().afegirPrestec(posEx, posUs, esLlarg);

                    carregarComboboxes();
                    carregarLlista();

                } catch (BiblioException ex) {
                    JOptionPane.showMessageDialog(GestioPrestecs.this, ex.getMessage());
                }
            }
        });

        // Retornar préstec
        btnRetornar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    Prestec p = listPrestecs.getSelectedValue();
                    if (p == null) {
                        JOptionPane.showMessageDialog(GestioPrestecs.this, "Selecciona un préstec");
                        return;
                    }

                    ArrayList<Prestec> prestecs = adaptador.getDades().recuperaPrestecs();
                    int pos = -1;
                    for (int i = 0; i < prestecs.size(); i++) {
                        if (prestecs.get(i).equals(p)) {
                            pos = i;
                            break;
                        }
                    }

                    adaptador.getDades().retornarPrestec(pos);

                    carregarComboboxes();
                    carregarLlista();

                } catch (BiblioException ex) {
                    JOptionPane.showMessageDialog(GestioPrestecs.this, ex.getMessage());
                }
            }
        });

        // Filtre
        chkNomésNoRetornats.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                carregarLlista();
            }
        });

        btnTancar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    private void carregarComboboxes() {
        cmbExemplar.removeAllItems();
        cmbUsuari.removeAllItems();

        for (Exemplar e : adaptador.getDades().recuperaExemplars()) {
            if (e.isDisponible()) {
                cmbExemplar.addItem(e);
            }
        }

        for (Usuari u : adaptador.getDades().recuperaUsuaris()) {
            cmbUsuari.addItem(u);
        }
    }

    private void carregarLlista() {
        modelLlista.clear();

        ArrayList<Prestec> prestecs;
        if (chkNomésNoRetornats.isSelected()) {
            prestecs = adaptador.getDades().recuperaPrestecsNoRetornats();
        } else {
            prestecs = adaptador.getDades().recuperaPrestecs();
        }

        for (Prestec p : prestecs) {
            modelLlista.addElement(p);
        }
    }
}