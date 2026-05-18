package prog2.vista;

import prog2.adaptador.Adaptador;
import prog2.model.Usuari;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Diàleg per a la gestió d'usuaris de la biblioteca.
 * Permet visualitzar, afegir i gestionar usuaris.
 *
 * @author Ternero David, Tribo Miquel
 * @version 2.0
 */
public class GestioUsuaris extends JDialog {

    private Adaptador adaptador;
    private DefaultListModel<Usuari> modelLlista;

    // Components de la interfície
    private JPanel panelMain;
    private JList<Usuari> listUsuaris;
    private JTextField txtEmail;
    private JTextField txtNom;
    private JTextField txtAdreca;
    private JCheckBox chkEstudiant;
    private JButton btnAfegir;
    private JButton btnTancar;
    private JButton btnNetejar;
    private JLabel lblEstat;

    /**
     * Constructor.
     * @param parent Finestra pare
     * @param adaptador Adaptador amb les dades
     */
    public GestioUsuaris(JFrame parent, Adaptador adaptador) {
        super(parent, "Gestió d'Usuaris", true);
        this.adaptador = adaptador;

        initComponents();
        addEventListeners();
        carregarLlistaUsuaris();
        actualitzarEstat();

        setSize(600, 500);
        setLocationRelativeTo(parent);
    }

    /**
     * Inicialitza tots els components gràfics.
     */
    private void initComponents() {
        // Panel principal amb BorderLayout
        panelMain = new JPanel(new BorderLayout(10, 10));
        panelMain.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // ===== PANEL FORMULARI (entrada de dades) =====
        JPanel panelFormulari = new JPanel(new GridBagLayout());
        panelFormulari.setBorder(BorderFactory.createTitledBorder("Afegir Nou Usuari"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Email
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulari.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        txtEmail = new JTextField(20);
        panelFormulari.add(txtEmail, gbc);

        // Nom
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panelFormulari.add(new JLabel("Nom:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        txtNom = new JTextField(20);
        panelFormulari.add(txtNom, gbc);

        // Adreça
        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulari.add(new JLabel("Adreça:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        txtAdreca = new JTextField(20);
        panelFormulari.add(txtAdreca, gbc);

        // Tipus d'usuari (CheckBox)
        gbc.gridx = 0; gbc.gridy = 3;
        panelFormulari.add(new JLabel("Tipus:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        chkEstudiant = new JCheckBox("És estudiant");
        panelFormulari.add(chkEstudiant, gbc);

        // Botons del formulari
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JPanel panelBotonsForm = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnAfegir = new JButton("➕ Afegir Usuari");
        btnNetejar = new JButton("🧹 Netejar Camps");
        panelBotonsForm.add(btnAfegir);
        panelBotonsForm.add(btnNetejar);
        panelFormulari.add(panelBotonsForm, gbc);

        // ===== PANEL LLISTA (visualització) =====
        JPanel panelLlista = new JPanel(new BorderLayout(5, 5));
        panelLlista.setBorder(BorderFactory.createTitledBorder("Llista d'Usuaris"));

        modelLlista = new DefaultListModel<>();
        listUsuaris = new JList<>(modelLlista);
        listUsuaris.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(listUsuaris);
        scrollPane.setPreferredSize(new Dimension(550, 200));
        panelLlista.add(scrollPane, BorderLayout.CENTER);

        // Etiqueta d'estat
        lblEstat = new JLabel(" ", SwingConstants.LEFT);
        lblEstat.setFont(new Font("Arial", Font.ITALIC, 11));
        lblEstat.setForeground(Color.GRAY);
        panelLlista.add(lblEstat, BorderLayout.SOUTH);

        // ===== PANEL BOTONS PRINCIPALS =====
        JPanel panelBotons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        btnTancar = new JButton("❌ Tancar");
        panelBotons.add(btnTancar);

        // Muntar el panell principal
        panelMain.add(panelFormulari, BorderLayout.NORTH);
        panelMain.add(panelLlista, BorderLayout.CENTER);
        panelMain.add(panelBotons, BorderLayout.SOUTH);

        setContentPane(panelMain);

        // Deshabilitar botó afegir inicialment
        btnAfegir.setEnabled(false);
    }

    /**
     * Afegeix tots els event listeners.
     */
    private void addEventListeners() {
        // DocumentListener per validar camps en temps real
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

        // ActionListener pel botó Afegir
        btnAfegir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afegirUsuari();
            }
        });

        // ActionListener pel botó Netejar
        btnNetejar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                netejarCamps();
            }
        });

        // ActionListener pel botó Tancar
        btnTancar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // ListSelectionListener per la llista
        listUsuaris.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    mostrarInformacioUsuari();
                }
            }
        });
    }

    /**
     * Valida si els camps són correctes i habilita/deshabilita el botó.
     */
    private void validarCamps() {
        boolean campsOk = !txtEmail.getText().trim().isEmpty() &&
                !txtNom.getText().trim().isEmpty() &&
                !txtAdreca.getText().trim().isEmpty();
        btnAfegir.setEnabled(campsOk);
    }

    /**
     * Afegeix un nou usuari al sistema.
     */
    private void afegirUsuari() {
        try {
            String email = txtEmail.getText().trim();
            String nom = txtNom.getText().trim();
            String adreca = txtAdreca.getText().trim();
            boolean esEstudiant = chkEstudiant.isSelected();

            adaptador.getDades().afegirUsuari(email, nom, adreca, esEstudiant);

            // Missatge de confirmació
            String tipus = esEstudiant ? "estudiant" : "professor";
            JOptionPane.showMessageDialog(this,
                    "✅ Usuari afegit correctament!\n" +
                            "📧 Email: " + email + "\n" +
                            "👤 Nom: " + nom + "\n" +
                            "🎓 Tipus: " + tipus,
                    "Usuari Afegit", JOptionPane.INFORMATION_MESSAGE);

            netejarCamps();
            carregarLlistaUsuaris();
            actualitzarEstat();

        } catch (BiblioException ex) {
            JOptionPane.showMessageDialog(this,
                    "❌ Error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "❌ Error inesperat: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Neteja tots els camps del formulari.
     */
    private void netejarCamps() {
        txtEmail.setText("");
        txtNom.setText("");
        txtAdreca.setText("");
        chkEstudiant.setSelected(false);
        txtEmail.requestFocus();
    }

    /**
     * Carrega la llista d'usuaris des de l'adaptador.
     */
    private void carregarLlistaUsuaris() {
        modelLlista.clear();
        for (Usuari u : adaptador.getDades().recuperaUsuaris()) {
            modelLlista.addElement(u);
        }
    }

    /**
     * Actualitza l'etiqueta d'estat amb el número d'usuaris.
     */
    private void actualitzarEstat() {
        int numUsuaris = modelLlista.getSize();
        lblEstat.setText("📊 Total usuaris: " + numUsuaris);
    }

    /**
     * Mostra la informació de l'usuari seleccionat.
     */
    private void mostrarInformacioUsuari() {
        Usuari u = listUsuaris.getSelectedValue();
        if (u != null) {
            String info = "👤 " + u.getNom() + " | " + u.getEmail();
            // Opcional: mostrar en una barra d'estat o tooltip
            // System.out.println(info);
        }
    }
}