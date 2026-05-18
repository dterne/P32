package prog2.vista;

import prog2.adaptador.Adaptador;
import prog2.model.Exemplar;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class GestioExemplars extends JDialog {
    private JPanel panelMain;
    private JList<Exemplar> listExemplars;
    private JTextField txtId;
    private JTextField txtTitol;
    private JTextField txtAutor;
    private JCheckBox chkAdmetLlarg;
    private JButton btnAfegir;
    private JButton btnTancar;

    private DefaultListModel<Exemplar> listModel;
    private Adaptador adaptador;

    public GestioExemplars(JFrame parent, Adaptador adaptador) {
        super(parent, "Gestió d'Exemplars", true);
        this.adaptador = adaptador;

        setContentPane(panelMain);
        setSize(500, 400);
        setLocationRelativeTo(parent);

        listModel = new DefaultListModel<>();
        listExemplars.setModel(listModel);

        refrescarLlista();
        btnAfegir.setEnabled(false);

        DocumentListener dl = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { validarCamps(); }
            @Override
            public void removeUpdate(DocumentEvent e) { validarCamps(); }
            @Override
            public void changedUpdate(DocumentEvent e) { validarCamps(); }
        };

        txtId.getDocument().addDocumentListener(dl);
        txtTitol.getDocument().addDocumentListener(dl);
        txtAutor.getDocument().addDocumentListener(dl);

        btnAfegir.addActionListener(e -> afegirExemplar());
        btnTancar.addActionListener(e -> dispose());
    }

    private void validarCamps() {
        boolean ok = !txtId.getText().trim().isEmpty() &&
                !txtTitol.getText().trim().isEmpty() &&
                !txtAutor.getText().trim().isEmpty();
        btnAfegir.setEnabled(ok);
    }

    private void afegirExemplar() {
        try {
            String id = txtId.getText().trim();
            String titol = txtTitol.getText().trim();
            String autor = txtAutor.getText().trim();
            boolean admetLlarg = chkAdmetLlarg.isSelected();

            adaptador.getDades().afegirExemplar(id, titol, autor, admetLlarg);

            txtId.setText("");
            txtTitol.setText("");
            txtAutor.setText("");
            chkAdmetLlarg.setSelected(false);

            refrescarLlista();
            JOptionPane.showMessageDialog(this, "Exemplar afegit correctament!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void refrescarLlista() {
        listModel.clear();
        for (Exemplar e : adaptador.getDades().recuperaExemplars()) {
            listModel.addElement(e);
        }
    }
}