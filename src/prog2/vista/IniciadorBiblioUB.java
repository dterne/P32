/*package prog2.vista;

public class IniciadorBiblioUB {

    public static void main(String[] args) {

        BiblioUB app = new BiblioUB();
        app.gestioBiblioUB();
    }
}*/
package prog2.vista;

import javax.swing.*;

/**
 * Classe principal que inicia l'aplicació de la biblioteca amb interfície gràfica.
 *
 * @author Ternero David, Tribo Miquel
 * @version 2.0
 */
public class IniciadorBiblioUB {

    public static void main(String[] args) {
        // Executar la interfície gràfica al thread d'esdeveniments de Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Establir Look and Feel del sistema
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // Crear i mostrar la finestra principal
                AppBiblioUB app = new AppBiblioUB();
                app.setVisible(true);
            }
        });
    }
}