package prog2.vista;

import prog2.adaptador.Adaptador;
import prog2.model.Exemplar;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;

/**
 * Diàleg per a la gestió d'exemplars de la biblioteca.
 * Permet visualitzar, afegir i gestionar exemplars.
 *
 * @author Ternero David, Tribo Miquel
 * @version 2.0
 */
public class GestioExemplars extends JDialog {

    private Adaptador adaptador;
    private DefaultListModel<Exemplar> modelLlista;

    // Components de la interfície
    private JPanel panelMain;
    private JList<Exemplar> listExemplars;
    private JCheckBox chkAdmetLlarg;
    private JButton btnAfegir;
    private JButton btnTancar;
    private JButton btnNetejar;
    private JLabel lblEstat;
    private JTextField txtId;
    private JTextField txtTitol;
    private JTextField txtAutor;

    /**
     * Constructor.
     * @param parent Finestra pare
     * @param adaptador Adaptador amb les dades
     */
    public GestioExemplars(JFrame parent, Adaptador adaptador) {
        super(parent, "Gestió d'Exemplars", true);
        this.adaptador = adaptador;

        initComponents();
        addEventListeners();
        carregarLlistaExemplars();
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

        // Panel formulari
        JPanel panelFormulari = new JPanel(new GridBagLayout());
        panelFormulari.setBorder(BorderFactory.createTitledBorder("Afegir Nou Exemplar"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ID
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulari.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        txtId = new JTextField(20);
        panelFormulari.add(txtId, gbc);

        // Títol
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        panelFormulari.add(new JLabel("Títol:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        txtTitol = new JTextField(20);
        panelFormulari.add(txtTitol, gbc);

        // Autor
        gbc.gridx = 0; gbc.gridy = 2;
        panelFormulari.add(new JLabel("Autor:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        txtAutor = new JTextField(20);
        panelFormulari.add(txtAutor, gbc);

        // Admet préstec llarg
        gbc.gridx = 0; gbc.gridy = 3;
        panelFormulari.add(new JLabel("Préstec llarg:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3;
        chkAdmetLlarg = new JCheckBox("Admet préstec llarg");
        panelFormulari.add(chkAdmetLlarg, gbc);

        // Botons formulari
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 2;
        JPanel panelBotonsForm = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        btnAfegir = new JButton("➕ Afegir Exemplar");
        btnNetejar = new JButton("🧹 Netejar Camps");
        panelBotonsForm.add(btnAfegir);
        panelBotonsForm.add(btnNetejar);
        panelFormulari.add(panelBotonsForm, gbc);

        // Panel llista
        JPanel panelLlista = new JPanel(new BorderLayout(5, 5));
        panelLlista.setBorder(BorderFactory.createTitledBorder("Llista d'Exemplars"));

        modelLlista = new DefaultListModel<>();
        listExemplars = new JList<>(modelLlista);
        listExemplars.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Custom cell renderer per tal de poder mostrar disponibilitat
        listExemplars.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected,
                                                          boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Exemplar) {
                    Exemplar e = (Exemplar) value;
                    String text = e.toString();
                    setText(text);
                    if (!e.isDisponible()) {
                        setForeground(Color.RED);
                    } else {
                        setForeground(isSelected ? Color.WHITE : Color.BLACK);
                    }
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(listExemplars);
        scrollPane.setPreferredSize(new Dimension(550, 200));
        panelLlista.add(scrollPane, BorderLayout.CENTER);

        // Etiqueta d'estat
        lblEstat = new JLabel(" ", SwingConstants.LEFT);
        lblEstat.setFont(new Font("Arial", Font.ITALIC, 11));
        lblEstat.setForeground(Color.GRAY);
        panelLlista.add(lblEstat, BorderLayout.SOUTH);

        // Panel botons principals
        JPanel panelBotons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        btnTancar = new JButton("❌ Tancar");
        panelBotons.add(btnTancar);

        // Muntem el panell principal
        panelMain.add(panelFormulari, BorderLayout.NORTH);
        panelMain.add(panelLlista, BorderLayout.CENTER);
        panelMain.add(panelBotons, BorderLayout.SOUTH);

        setContentPane(panelMain);

        // Deshabilitem el botó afegir inicialment
        btnAfegir.setEnabled(false);
    }

    /**
     * Afegeix tots els event listeners.
     */
    private void addEventListeners() {
        // DocumentListener per validar camps
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

        // ActionListener botó Afegir
        btnAfegir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                afegirExemplar();
            }
        });

        // ActionListener botó Netejar
        btnNetejar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                netejarCamps();
            }
        });

        // ActionListener botó Tancar
        btnTancar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        // ListSelectionListener llista
        listExemplars.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    mostrarInformacioExemplar();
                }
            }
        });
    }

    /**
     * Valida els camps del formulari.
     */
    private void validarCamps() {
        boolean campsOk = !txtId.getText().trim().isEmpty() &&
                !txtTitol.getText().trim().isEmpty() &&
                !txtAutor.getText().trim().isEmpty();
        btnAfegir.setEnabled(campsOk);
    }

    /**
     * Afegeix un nou exemplar al sistema.
     */
    private void afegirExemplar() {
        try {
            String id = txtId.getText().trim();
            String titol = txtTitol.getText().trim();
            String autor = txtAutor.getText().trim();
            boolean admetLlarg = chkAdmetLlarg.isSelected();

            adaptador.getDades().afegirExemplar(id, titol, autor, admetLlarg);

            // Missatge de confirmació
            String tipus = admetLlarg ? "Sí" : "No";
            JOptionPane.showMessageDialog(this,
                    "✅ Exemplar afegit correctament!\n" +
                            "🆔 ID: " + id + "\n" +
                            "📖 Títol: " + titol + "\n" +
                            "✍️ Autor: " + autor + "\n" +
                            "🔁 Admet préstec llarg: " + tipus,
                    "Exemplar Afegit", JOptionPane.INFORMATION_MESSAGE);

            netejarCamps();
            carregarLlistaExemplars();
            actualitzarEstat();

        } catch (BiblioException ex) {
            JOptionPane.showMessageDialog(this,
                    "❌ Error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Neteja els camps del formulari.
     */
    private void netejarCamps() {
        txtId.setText("");
        txtTitol.setText("");
        txtAutor.setText("");
        chkAdmetLlarg.setSelected(false);
        txtId.requestFocus();
    }

    /**
     * Carrega la llista d'exemplars des de l'adaptador.
     */
    private void carregarLlistaExemplars() {
        modelLlista.clear();
        for (Exemplar e : adaptador.getDades().recuperaExemplars()) {
            modelLlista.addElement(e);
        }
    }

    /**
     * Actualitza l'etiqueta d'estat amb el número d'exemplars i disponibles.
     */
    private void actualitzarEstat() {
        int numExemplars = modelLlista.getSize();

        // Calculem exemplars disponibles
        int numDisponibles = 0;
        for (int i = 0; i < modelLlista.getSize(); i++) {
            Exemplar e = modelLlista.getElementAt(i);
            if (e.isDisponible()) {
                numDisponibles++;
            }
        }

        lblEstat.setText("📊 Total: " + numExemplars + " | ✅ Disponibles: " + numDisponibles);
    }

    /**
     * Mostra la informació de l'exemplar seleccionat.
     */
    private void mostrarInformacioExemplar() {
        Exemplar e = listExemplars.getSelectedValue();
        if (e != null) {
            String estat = e.isDisponible() ? "Disponible" : "Prestat";
        }
    }
}