package prog2.vista;

import prog2.adaptador.Adaptador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Classe principal de l'aplicació amb interfície gràfica.
 * Gestiona el menú principal i les operacions de guardar/carregar.
 *
 * @author Ternero David, Tribo Miquel
 * @version 2.0
 */
public class AppBiblioUB extends JFrame {

    private Adaptador adaptador;

    // Components de la interfície
    private JButton btnGestioUsuaris;
    private JButton btnGestioExemplars;
    private JButton btnGestioPrestecs;
    private JButton btnGuardar;
    private JButton btnCarregar;
    private JPanel panelMain;

    /**
     * Constructor. Inicialitza la finestra principal i els seus components.
     */
    public AppBiblioUB() {
        // Inicialitzem l'adaptador
        adaptador = new Adaptador();

        // Configurem la finestra
        setTitle("Biblioteca UB - Sistema de Gestió");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);
        setResizable(false);

        // Creem la interfície
        initComponents();

        // Afegim els listeners
        addEventListeners();
    }

    /**
     * Crea i organitza tots els components gràfics.
     */
    private void initComponents() {
        // Panel principal amb BorderLayout
        panelMain = new JPanel(new BorderLayout());
        panelMain.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Títol
        JLabel lblTitol = new JLabel("Gestió de la Biblioteca UB", SwingConstants.CENTER);
        lblTitol.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitol.setBorder(BorderFactory.createEmptyBorder(0, 0, 20, 0));
        panelMain.add(lblTitol, BorderLayout.NORTH);

        // Panel central amb GridLayout pels botons
        JPanel panelBotons = new JPanel(new GridLayout(3, 1, 10, 15));
        panelBotons.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));

        // Creem botons
        btnGestioUsuaris = new JButton("👥 Gestió d'Usuaris");
        btnGestioExemplars = new JButton("📚 Gestió d'Exemplars");
        btnGestioPrestecs = new JButton("🔄 Gestió de Préstecs");



        // Estil dels botons principals
        estilitzarBoto(btnGestioUsuaris);
        estilitzarBoto(btnGestioExemplars);
        estilitzarBoto(btnGestioPrestecs);

        panelBotons.add(btnGestioUsuaris);
        panelBotons.add(btnGestioExemplars);
        panelBotons.add(btnGestioPrestecs);

        panelMain.add(panelBotons, BorderLayout.CENTER);

        // Panel inferior per guardar, carregar i sortir
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));

        btnGuardar = new JButton("💾 Guardar Dades");
        btnCarregar = new JButton("📂 Carregar Dades");
        JButton btnSortir = new JButton("❌ Sortir");

        estilitzarBotoSecundari(btnGuardar);
        estilitzarBotoSecundari(btnCarregar);
        estilitzarBotoSecundari(btnSortir);




        panelInferior.add(btnGuardar);
        panelInferior.add(btnCarregar);
        panelInferior.add(btnSortir);

        panelMain.add(panelInferior, BorderLayout.SOUTH);

        // Afegim el panel a la finestra
        setContentPane(panelMain);

        // Listener per al botó sortir
        btnSortir.addActionListener(e -> sortirAplicacio());
    }

    /**
     * Estilitza un botó principal.
     * @param boto El botó a estilitzar
     */
    private void estilitzarBoto(JButton boto) {
        boto.setFont(new Font("Arial", Font.BOLD, 14));
        boto.setBackground(new Color(70, 130, 200));
        boto.setForeground(Color.BLACK);
        boto.setFocusPainted(false);
        boto.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        boto.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * Estilitza un botó secundari.
     * @param boto El botó a estilitzar
     */
    private void estilitzarBotoSecundari(JButton boto) {
        boto.setFont(new Font("Arial", Font.PLAIN, 12));
        boto.setBackground(new Color(240, 240, 240));
        boto.setFocusPainted(false);
        boto.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    /**
     * Afegeix tots els event listeners als botons.
     */
    private void addEventListeners() {
        // Listener per Gestió d'Usuaris
        btnGestioUsuaris.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                obrirGestioUsuaris();
            }
        });

        // Listener per Gestió d'Exemplars
        btnGestioExemplars.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                obrirGestioExemplars();
            }
        });

        // Listener per Gestió de Préstecs
        btnGestioPrestecs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                obrirGestioPrestecs();
            }
        });

        // Listener per Guardar
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarDades();
            }
        });

        // Listener per Carregar
        btnCarregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                carregarDades();
            }
        });
    }

    /**
     * Obre el diàleg de gestió d'usuaris.
     */
    private void obrirGestioUsuaris() {
        GestioUsuaris dialog = new GestioUsuaris(this, adaptador);
        dialog.setVisible(true);
    }

    /**
     * Obre el diàleg de gestió d'exemplars.
     */
    private void obrirGestioExemplars() {
        GestioExemplars dialog = new GestioExemplars(this, adaptador);
        dialog.setVisible(true);
    }

    /**
     * Obre el diàleg de gestió de préstecs.
     */
    private void obrirGestioPrestecs() {
        GestioPrestecs dialog = new GestioPrestecs(this, adaptador);
        dialog.setVisible(true);
    }

    /**
     * Guarda les dades en un fitxer.
     */
    private void guardarDades() {
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setDialogTitle("Guardar dades de la biblioteca");

        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                String fitxer = fileChooser.getSelectedFile().getAbsolutePath();
                adaptador.guardaDades(fitxer);
                JOptionPane.showMessageDialog(this,
                        "✅ Dades guardades correctament al fitxer:\n" + fitxer,
                        "Guardar Dades", JOptionPane.INFORMATION_MESSAGE);
            } catch (BiblioException ex) {
                JOptionPane.showMessageDialog(this,
                        "❌ Error en guardar: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Carrega les dades des d'un fitxer.
     */
    private void carregarDades() {
        JFileChooser fileChooser = new JFileChooser(".");
        fileChooser.setDialogTitle("Carregar dades de la biblioteca");

        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                String fitxer = fileChooser.getSelectedFile().getAbsolutePath();
                adaptador.carregaDades(fitxer);
                JOptionPane.showMessageDialog(this,
                        "✅ Dades carregades correctament des del fitxer:\n" + fitxer,
                        "Carregar Dades", JOptionPane.INFORMATION_MESSAGE);
            } catch (BiblioException ex) {
                JOptionPane.showMessageDialog(this,
                        "❌ Error en carregar: " + ex.getMessage(),
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Surt de l'aplicació amb confirmació.
     */
    private void sortirAplicacio() {
        int confirmacio = JOptionPane.showConfirmDialog(this,
                "Segur que vols sortir de l'aplicació?",
                "Sortir", JOptionPane.YES_NO_OPTION);
        if (confirmacio == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}