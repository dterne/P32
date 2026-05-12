package prog2.vista;

import java.util.Scanner;
import prog2.adaptador.Adaptador;

public class BiblioUB {

    private Adaptador adaptador;

    public BiblioUB() {
        adaptador = new Adaptador();
    }

    // ================= ENUMS =================

    private enum OpcionsMenuPrincipal {
        EXEMPLARS, USUARIS, PRESTECS, SAVE, LOAD, EXIT
    }

    private enum OpcionsMenuExemplars {
        ADD, VIEW, EXIT
    }

    private enum OpcionsMenuUsuaris {
        ADD, VIEW, EXIT
    }

    private enum OpcionsMenuPrestecs {
        ADD, RETURN, VIEW, VIEW_NOT_RETURNED, EXIT
    }

    // ================= DESCRIPCIONS =================

    private String[] descMenuPrincipal = {
            "Gestió Exemplars",
            "Gestió Usuaris",
            "Gestió Préstecs",
            "Guardar Dades",
            "Carregar Dades",
            "Sortir"
    };

    private String[] descMenuExemplars = {
            "Afegir Exemplar",
            "Visualitzar Exemplars",
            "Sortir"
    };

    private String[] descMenuUsuaris = {
            "Afegir Usuari",
            "Visualitzar Usuaris",
            "Sortir"
    };

    private String[] descMenuPrestecs = {
            "Afegir Préstec",
            "Retornar Préstec",
            "Visualitzar Préstecs",
            "Visualitzar No Retornats",
            "Sortir"
    };

    // ================= EXECUCIÓ =================

    public void gestioBiblioUB() {

        Scanner sc = new Scanner(System.in);

        Menu<OpcionsMenuPrincipal> menu =
                new Menu<>("MENU PRINCIPAL", OpcionsMenuPrincipal.values());

        menu.setDescripcions(descMenuPrincipal);

        OpcionsMenuPrincipal opcio;

        do {
            menu.mostrarMenu();
            opcio = menu.getOpcio(sc);

            switch (opcio) {
                case EXEMPLARS -> menuExemplars(sc);
                case USUARIS -> menuUsuaris(sc);
                case PRESTECS -> menuPrestecs(sc);
                case SAVE -> guardar(sc);
                case LOAD -> carregar(sc);
                case EXIT -> System.out.println("Sortint...");
            }

        } while (opcio != OpcionsMenuPrincipal.EXIT);
    }

    // ================= MENÚ EXEMPLARS =================

    private void menuExemplars(Scanner sc) {

        Menu<OpcionsMenuExemplars> menu =
                new Menu<>("GESTIÓ EXEMPLARS", OpcionsMenuExemplars.values());

        menu.setDescripcions(descMenuExemplars);

        OpcionsMenuExemplars opcio;

        do {
            menu.mostrarMenu();
            opcio = menu.getOpcio(sc);

            switch (opcio) {
                case ADD -> afegirExemplar(sc);
                case VIEW -> mostrarLlista(adaptador.getDades().recuperaExemplars());
            }

        } while (opcio != OpcionsMenuExemplars.EXIT);
    }

    // ================= MENÚ USUARIS =================

    private void menuUsuaris(Scanner sc) {

        Menu<OpcionsMenuUsuaris> menu =
                new Menu<>("GESTIÓ USUARIS", OpcionsMenuUsuaris.values());

        menu.setDescripcions(descMenuUsuaris);

        OpcionsMenuUsuaris opcio;

        do {
            menu.mostrarMenu();
            opcio = menu.getOpcio(sc);

            switch (opcio) {
                case ADD -> afegirUsuari(sc);
                case VIEW -> mostrarLlista(adaptador.getDades().recuperaUsuaris());
            }

        } while (opcio != OpcionsMenuUsuaris.EXIT);
    }

    // ================= MENÚ PRESTECS =================

    private void menuPrestecs(Scanner sc) {

        Menu<OpcionsMenuPrestecs> menu =
                new Menu<>("GESTIÓ PRESTECS", OpcionsMenuPrestecs.values());

        menu.setDescripcions(descMenuPrestecs);

        OpcionsMenuPrestecs opcio;

        do {
            menu.mostrarMenu();
            opcio = menu.getOpcio(sc);

            switch (opcio) {
                case ADD -> afegirPrestec(sc);
                case RETURN -> retornarPrestec(sc);
                case VIEW -> mostrarLlista(adaptador.getDades().recuperaPrestecs());
                case VIEW_NOT_RETURNED -> mostrarLlista(adaptador.getDades().recuperaPrestecsNoRetornats());
            }

        } while (opcio != OpcionsMenuPrestecs.EXIT);
    }

    // ================= FUNCIONS =================

    private void mostrarLlista(java.util.List<?> llista) {
        for (int i = 0; i < llista.size(); i++) {
            System.out.println(i + " -> " + llista.get(i));
        }
    }

    private void afegirExemplar(Scanner sc) {
        try {
            System.out.print("Id: ");
            String id = sc.nextLine();

            System.out.print("Títol: ");
            String titol = sc.nextLine();

            System.out.print("Autor: ");
            String autor = sc.nextLine();

            System.out.print("Admet préstec llarg (true/false): ");
            boolean llarg = sc.nextBoolean();
            sc.nextLine();

            adaptador.getDades().afegirExemplar(id, titol, autor, llarg);

            System.out.println("Exemplar afegit!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            sc.nextLine();
        }
    }

    private void afegirUsuari(Scanner sc) {
        try {
            System.out.print("Email: ");
            String email = sc.nextLine();

            System.out.print("Nom: ");
            String nom = sc.nextLine();

            System.out.print("Adreça: ");
            String adreca = sc.nextLine();

            System.out.print("És estudiant? (true/false): ");
            boolean est = sc.nextBoolean();
            sc.nextLine();

            adaptador.getDades().afegirUsuari(email, nom, adreca, est);

            System.out.println("Usuari afegit!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            sc.nextLine();
        }
    }

    private void afegirPrestec(Scanner sc) {
        try {
            mostrarLlista(adaptador.getDades().recuperaExemplars());
            System.out.print("Posició exemplar: ");
            int ex = sc.nextInt();

            mostrarLlista(adaptador.getDades().recuperaUsuaris());
            System.out.print("Posició usuari: ");
            int us = sc.nextInt();

            System.out.print("És llarg? (true/false): ");
            boolean llarg = sc.nextBoolean();
            sc.nextLine();

            adaptador.getDades().afegirPrestec(ex, us, llarg);

            System.out.println("Préstec creat!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            sc.nextLine();
        }
    }

    private void retornarPrestec(Scanner sc) {
        try {
            mostrarLlista(adaptador.getDades().recuperaPrestecs());
            System.out.print("Posició préstec: ");
            int pos = sc.nextInt();
            sc.nextLine();

            adaptador.getDades().retornarPrestec(pos);

            System.out.println("Préstec retornat!");

        } catch (Exception e) {
            System.out.println(e.getMessage());
            sc.nextLine();
        }
    }

    private void guardar(Scanner sc) {
        System.out.print("Nom fitxer: ");
        String f = sc.nextLine();
        try {
            adaptador.guardaDades(f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void carregar(Scanner sc) {
        System.out.print("Nom fitxer: ");
        String f = sc.nextLine();
        try {
            adaptador.carregaDades(f);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}