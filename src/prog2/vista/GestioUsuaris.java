package prog2.vista;

import prog2.adaptador.Adaptador;
import prog2.model.Usuari;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class GestioUsuaris extends JDialog {
    private JPanel panelMain;
    private JList<Usuari> listUsuaris;
    private JTextField txtEmail;
    private JTextField txtNom;
    private JTextField txtAdreca;
    private JCheckBox chkEstudiant;
    private JButton btnAfegir;
    private JButton btnTancar;

    private DefaultListModel<Usuari> listModel;
    private Adaptador adaptador;

    public GestioUsuaris(JFrame parent, Adaptador adaptador) {
        super(parent, "Gestió d'Usuaris", true);
        this.adaptador = adaptador;

        setContentPane(panelMain);
        setSize(500, 400);
        setLocationRelativeTo(parent);

        // Inicialitzar el model de la llista
        listModel = new DefaultListModel<>();
        listUsuaris.setModel(listModel);

        // Carregar usuaris existents
        refrescarLlista();

        // Deshabilitar botó Afegir al principi
        btnAfegir.setEnabled(false);

        // Listener per habilitar/deshabilitar botó segons si hi ha dades
        DocumentListener dl = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { validarCamps(); }
            @Override
            public void removeUpdate(DocumentEvent e) { validarCamps(); }
            @Override
            public void changedUpdate(DocumentEvent e) { validarCamps(); }
        };

        txtEmail.getDocument().addDocumentListener(dl);
        txtNom.getDocument().addDocumentListener(dl);
        txtAdreca.getDocument().addDocumentListener(dl);

        // Acció del botó Afegir
        btnAfegir.addActionListener(e -> afegirUsuari());

        // Acció del botó Tancar
        btnTancar.addActionListener(e -> dispose());
    }

    private void validarCamps() {
        boolean ok = !txtEmail.getText().trim().isEmpty() &&
                !txtNom.getText().trim().isEmpty() &&
                !txtAdreca.getText().trim().isEmpty();
        btnAfegir.setEnabled(ok);
    }

    private void afegirUsuari() {
        try {
            String email = txtEmail.getText().trim();
            String nom = txtNom.getText().trim();
            String adreca = txtAdreca.getText().trim();
            boolean esEstudiant = chkEstudiant.isSelected();

            adaptador.getDades().afegirUsuari(email, nom, adreca, esEstudiant);

            // Netejar camps
            txtEmail.setText("");
            txtNom.setText("");
            txtAdreca.setText("");
            chkEstudiant.setSelected(false);

            // Refrescar llista
            refrescarLlista();

            JOptionPane.showMessageDialog(this, "Usuari afegit correctament!");

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void refrescarLlista() {
        listModel.clear();
        for (Usuari u : adaptador.getDades().recuperaUsuaris()) {
            listModel.addElement(u);
        }
    }
}