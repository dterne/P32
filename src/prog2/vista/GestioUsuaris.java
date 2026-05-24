package prog2.vista;

import prog2.adaptador.Adaptador;
import prog2.model.Usuari;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GestioUsuaris extends JDialog {
    private JPanel panelMain;
    private JList<Usuari> listUsuaris;
    private JTextField txtEmail;
    private JTextField txtNom;
    private JTextField txtAdreca;
    private JCheckBox chkEstudiant;
    private JButton btnAfegir;
    private JButton btnTancar;

    private Adaptador adaptador;
    private DefaultListModel<Usuari> modelLlista;

    public GestioUsuaris(JFrame parent, Adaptador adaptador) {
        super(parent, "Gestió Usuaris", true);
        this.adaptador = adaptador;

        setContentPane(panelMain);
        setSize(450, 400);
        setLocationRelativeTo(parent);

        modelLlista = new DefaultListModel<>();
        listUsuaris.setModel(modelLlista);

        carregarLlista();

        btnAfegir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    String email = txtEmail.getText();
                    String nom = txtNom.getText();
                    String adreca = txtAdreca.getText();
                    boolean esEstudiant = chkEstudiant.isSelected();

                    adaptador.getDades().afegirUsuari(email, nom, adreca, esEstudiant);

                    txtEmail.setText("");
                    txtNom.setText("");
                    txtAdreca.setText("");
                    chkEstudiant.setSelected(false);

                    carregarLlista();

                } catch (BiblioException ex) {
                    JOptionPane.showMessageDialog(GestioUsuaris.this, ex.getMessage());
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
        for (Usuari u : adaptador.getDades().recuperaUsuaris()) {
            modelLlista.addElement(u);
        }
    }
}