package prog2.vista;

import prog2.adaptador.Adaptador;
import prog2.model.Exemplar;
import prog2.model.Prestec;
import prog2.model.Usuari;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Diàleg per a la gestió de préstecs de la biblioteca.
 * Permet visualitzar, afegir i retornar préstecs.
 *
 * @author Ternero David, Tribo Miquel
 * @version 2.0
 */
public class FrmGestioPrestecs extends JDialog {

    private Adaptador adaptador;
    private DefaultListModel<Prestec> modelLlista;

    // Components de la interfície
    private JPanel panelMain;
    private JList<Prestec> listPrestecs;
    private JComboBox<Exemplar> cmbExemplar;
    private JComboBox<Usuari> cmbUsuari;
    private JCheckBox chkEsLlarg;
    private JCheckBox chkNomésNoRetornats;
    private JButton btnAfegir;
    private JButton btnRetornar;
    private JButton btnRefrescar;
    private JButton btnTancar;
    private JLabel lblEstat;
    private JLabel lblExemplarInfo;
    private JLabel lblUsuariInfo;

    /**
     * Constructor.
     * @param parent Finestra pare
     * @param adaptador Adaptador amb les dades
     */
    public FrmGestioPrestecs(JFrame parent, Adaptador adaptador) {
        super(parent, "Gestió de Préstecs", true);
        this.adaptador = adaptador;

        initComponents();
        addEventListeners();
        refrescarDades();

        setSize(700, 600);
        setLocationRelativeTo(parent);
    }

    /**
     * Inicialitza tots els components gràfics.
     */
    private void initComponents() {
        // Panel principal
        panelMain = new JPanel(new BorderLayout(10, 10));
        panelMain.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Panel superior
        JPanel panelSuperior = new JPanel(new GridBagLayout());
        panelSuperior.setBorder(BorderFactory.createTitledBorder("Nou Préstec"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Exemplar
        gbc.gridx = 0; gbc.gridy = 0;
        panelSuperior.add(new JLabel("📖 Exemplar:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; gbc.weightx = 1.0;
        cmbExemplar = new JComboBox<>();
        cmbExemplar.setPreferredSize(new Dimension(300, 25));
        panelSuperior.add(cmbExemplar, gbc);
        gbc.gridx = 2; gbc.gridy = 0; gbc.weightx = 0;
        lblExemplarInfo = new JLabel(" ");
        lblExemplarInfo.setFont(new Font("Arial", Font.ITALIC, 10));
        panelSuperior.add(lblExemplarInfo, gbc);

        // Usuari
        gbc.gridx = 0; gbc.gridy = 1;
        panelSuperior.add(new JLabel("👤 Usuari:"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 1.0;
        cmbUsuari = new JComboBox<>();
        cmbUsuari.setPreferredSize(new Dimension(300, 25));
        panelSuperior.add(cmbUsuari, gbc);
        gbc.gridx = 2; gbc.gridy = 1;
        lblUsuariInfo = new JLabel(" ");
        lblUsuariInfo.setFont(new Font("Arial", Font.ITALIC, 10));
        panelSuperior.add(lblUsuariInfo, gbc);

        // Tipus de préstec
        gbc.gridx = 0; gbc.gridy = 2;
        panelSuperior.add(new JLabel("🔄 Tipus:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2;
        chkEsLlarg = new JCheckBox("Préstec llarg (més durada)");
        panelSuperior.add(chkEsLlarg, gbc);

        // Botó afegir
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 3;
        JPanel panelBotoAfegir = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnAfegir = new JButton("Crear Préstec");
        btnAfegir.setBackground(new Color(70, 130, 200));
        btnAfegir.setForeground(Color.WHITE);
        btnAfegir.setFont(new Font("Arial", Font.BOLD, 12));
        panelBotoAfegir.add(btnAfegir);
        panelSuperior.add(panelBotoAfegir, gbc);

        // Panel central
        JPanel panelCentral = new JPanel(new BorderLayout(5, 5));
        panelCentral.setBorder(BorderFactory.createTitledBorder("Llista de Préstecs"));

        // Filtre
        JPanel panelFiltre = new JPanel(new FlowLayout(FlowLayout.LEFT));
        chkNomésNoRetornats = new JCheckBox("🔍 Mostrar només préstecs no retornats");
        panelFiltre.add(chkNomésNoRetornats);
        panelCentral.add(panelFiltre, BorderLayout.NORTH);

        // Llista
        modelLlista = new DefaultListModel<>();
        listPrestecs = new JList<>(modelLlista);
        listPrestecs.setFont(new Font("Monospaced", Font.PLAIN, 12));

        // Custom cell renderer per mostrar estat
        listPrestecs.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                                                          int index, boolean isSelected,
                                                          boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Prestec) {
                    Prestec p = (Prestec) value;
                    String text = "[" + p.tipusPrestec() + "] " +
                            p.getExemplar().getTitol() + " - " +
                            p.getUsuari().getNom();
                    if (p.getRetornat()) {
                        text += " RETORNAT";
                        setForeground(Color.GRAY);
                    } else if (p.prestecEndarrerit()) {
                        text += " ⚠ENDARRERIT!";
                        setForeground(Color.RED);
                    } else {
                        setForeground(isSelected ? Color.WHITE : Color.BLACK);
                    }
                    setText(text);
                }
                return c;
            }
        });

        JScrollPane scrollPane = new JScrollPane(listPrestecs);
        scrollPane.setPreferredSize(new Dimension(650, 250));
        panelCentral.add(scrollPane, BorderLayout.CENTER);

        // Etiqueta d'estat
        lblEstat = new JLabel(" ", SwingConstants.LEFT);
        lblEstat.setFont(new Font("Arial", Font.ITALIC, 11));
        lblEstat.setForeground(Color.GRAY);
        panelCentral.add(lblEstat, BorderLayout.SOUTH);

        // Panel inferior
        JPanel panelInferior = new JPanel(new BorderLayout(10, 5));

        JPanel panelBotonsAccio = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        btnRetornar = new JButton("↩️ Retornar Préstec Seleccionat");
        btnRetornar.setBackground(new Color(50, 150, 50));
        btnRetornar.setForeground(Color.WHITE);
        btnRefrescar = new JButton("Refrescar Dades");

        panelBotonsAccio.add(btnRetornar);
        panelBotonsAccio.add(btnRefrescar);

        JPanel panelBotonsTancar = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnTancar = new JButton("Tancar");
        panelBotonsTancar.add(btnTancar);

        panelInferior.add(panelBotonsAccio, BorderLayout.CENTER);
        panelInferior.add(panelBotonsTancar, BorderLayout.SOUTH);

        // Muntem el panell principal
        panelMain.add(panelSuperior, BorderLayout.NORTH);
        panelMain.add(panelCentral, BorderLayout.CENTER);
        panelMain.add(panelInferior, BorderLayout.SOUTH);

        setContentPane(panelMain);
    }

    /**
     * Afegeix tots els event listeners.
     */
    private void addEventListeners() {
        // ActionListener crear préstec
        btnAfegir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearPrestec();
            }
        });

        // ActionListener retornar préstec
        btnRetornar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                retornarPrestec();
            }
        });

        // ActionListener refrescar
        btnRefrescar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refrescarDades();
                JOptionPane.showMessageDialog(FrmGestioPrestecs.this,
                        "Dades actualitzades correctament!",
                        "Refrescar", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        // ActionListener filtre
        chkNomésNoRetornats.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refrescarLlistaPrestecs();
            }
        });

        // ActionListener selecció d'exemplar
        cmbExemplar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Exemplar ex = (Exemplar) cmbExemplar.getSelectedItem();
                if (ex != null) {
                    lblExemplarInfo.setText("Autor: " + ex.getAutor());
                }
            }
        });

        // ActionListener selecció d'usuari
        cmbUsuari.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Usuari u = (Usuari) cmbUsuari.getSelectedItem();
                if (u != null) {
                    lblUsuariInfo.setText("Tipus: " + u.tipusClient() +
                            " | Normals: " + u.getNumPrestecsNormals() + "/" + u.getMaxPrestecsNormals() +
                            " | Llargs: " + u.getNumPrestecsLlargs() + "/" + u.getMaxPrestecsLlargs());
                }
            }
        });

        // ListSelectionListener la llista de préstecs
        listPrestecs.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    Prestec p = listPrestecs.getSelectedValue();
                    if (p != null && p.getRetornat()) {
                        btnRetornar.setEnabled(false);
                    } else {
                        btnRetornar.setEnabled(p != null);
                    }
                }
            }
        });

        // Tanquem
        btnTancar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }

    /**
     * Refresca totes les dades (comboboxes i llista).
     */
    private void refrescarDades() {
        refrescarComboboxes();
        refrescarLlistaPrestecs();
        actualitzarEstat();
    }

    /**
     * Refresca els comboboxes d'exemplars i usuaris.
     */
    private void refrescarComboboxes() {
        // Guardem selecció actual si existeix
        Exemplar exSeleccionat = (Exemplar) cmbExemplar.getSelectedItem();
        Usuari usSeleccionat = (Usuari) cmbUsuari.getSelectedItem();

        cmbExemplar.removeAllItems();
        cmbUsuari.removeAllItems();

        // Afegi  només exemplars disponibles
        for (Exemplar e : adaptador.getDades().recuperaExemplars()) {
            if (e.isDisponible()) {
                cmbExemplar.addItem(e);
            }
        }

        // Afegim tots els usuaris
        for (Usuari u : adaptador.getDades().recuperaUsuaris()) {
            cmbUsuari.addItem(u);
        }

        // Restaurem selecció si es possible
        if (exSeleccionat != null && exSeleccionat.isDisponible()) {
            cmbExemplar.setSelectedItem(exSeleccionat);
        }
        if (usSeleccionat != null) {
            cmbUsuari.setSelectedItem(usSeleccionat);
        }

        // Actualitzem informació
        if (cmbExemplar.getItemCount() > 0) {
            Exemplar e = (Exemplar) cmbExemplar.getSelectedItem();
            if (e != null) lblExemplarInfo.setText("Autor: " + e.getAutor());
        }
        if (cmbUsuari.getItemCount() > 0) {
            Usuari u = (Usuari) cmbUsuari.getSelectedItem();
            if (u != null) {
                lblUsuariInfo.setText("Tipus: " + u.tipusClient() +
                        " | Normals: " + u.getNumPrestecsNormals() + "/" + u.getMaxPrestecsNormals() +
                        " | Llargs: " + u.getNumPrestecsLlargs() + "/" + u.getMaxPrestecsLlargs());
            }
        }
    }

    /**
     * Refresca la llista de préstecs segons el filtre.
     */
    private void refrescarLlistaPrestecs() {
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

        actualitzarEstat();
    }

    /**
     * Crea un nou préstec.
     */
    private void crearPrestec() {
        try {
            Exemplar exemplar = (Exemplar) cmbExemplar.getSelectedItem();
            Usuari usuari = (Usuari) cmbUsuari.getSelectedItem();
            boolean esLlarg = chkEsLlarg.isSelected();

            if (exemplar == null) {
                JOptionPane.showMessageDialog(this,
                        "No hi ha exemplars disponibles per prestar!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (usuari == null) {
                JOptionPane.showMessageDialog(this,
                        "No hi ha usuaris registrats!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Trobem posicions
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

            String tipus = esLlarg ? "LLARG" : "NORMAL";
            JOptionPane.showMessageDialog(this,
                    "Préstec creat correctament!\n" +
                            " Exemplar: " + exemplar.getTitol() + "\n" +
                            "Usuari: " + usuari.getNom() + "\n" +
                            "Tipus: " + tipus,
                    "Préstec Creat", JOptionPane.INFORMATION_MESSAGE);

            // Refresquem totes les dades
            chkEsLlarg.setSelected(false);
            refrescarDades();

        } catch (BiblioException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Retorna un préstec seleccionat.
     */
    private void retornarPrestec() {
        try {
            Prestec p = listPrestecs.getSelectedValue();
            if (p == null) {
                JOptionPane.showMessageDialog(this,
                        "Selecciona un préstec per retornar!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (p.getRetornat()) {
                JOptionPane.showMessageDialog(this,
                        "Aquest préstec ja ha estat retornat!",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Confirmació
            int confirm = JOptionPane.showConfirmDialog(this,
                    "Vols retornar el préstec?\n" +
                            "Exemplar: " + p.getExemplar().getTitol() + "\n" +
                            "Usuari: " + p.getUsuari().getNom(),
                    "Confirmar Retorn", JOptionPane.YES_NO_OPTION);

            if (confirm != JOptionPane.YES_OPTION) return;

            // Trobem posició
            int pos = -1;
            ArrayList<Prestec> prestecs = adaptador.getDades().recuperaPrestecs();
            for (int i = 0; i < prestecs.size(); i++) {
                if (prestecs.get(i).equals(p)) {
                    pos = i;
                    break;
                }
            }

            adaptador.getDades().retornarPrestec(pos);

            JOptionPane.showMessageDialog(this,
                    "Préstec retornat correctament!",
                    "Retorn Completat", JOptionPane.INFORMATION_MESSAGE);

            // Refresquem dades
            refrescarDades();

        } catch (BiblioException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + ex.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Actualitza l'etiqueta d'estat.
     */
    private void actualitzarEstat() {
        int total = modelLlista.getSize();

        // Calculem quants estan endarrerits
        int endarrerits = 0;
        for (int i = 0; i < modelLlista.getSize(); i++) {
            Prestec p = modelLlista.getElementAt(i);
            if (!p.getRetornat() && p.prestecEndarrerit()) {
                endarrerits++;
            }
        }

        String filtre = chkNomésNoRetornats.isSelected() ? " (només no retornats)" : "";
        lblEstat.setText("Total préstecs" + filtre + ": " + total + " | Endarrerits: " + endarrerits);
    }
}