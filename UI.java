package po2proj;

import po2proj.rosliny.*;
import po2proj.zwierzeta.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class UI extends JFrame implements ActionListener, KeyListener
{
    private Swiat swiat;

    private final JFrame okno;
    private final JMenuItem nowaGra;
    private final JMenuItem wczytaj;
    private final JMenuItem zapisz;
    private final JMenuItem wyjscie;
    private final JPanel glownyPanel;

    private UIPlanszy planszaGraficzna = null;
    private UIKomunikatu komunikatGraficzny = null;
    private Legenda koloryOrganizmow = null;

    static public final int szerokoscOkna = 600 ;
    static public final int wysokoscOkna = 900;

    static public int szerokoscPlanszy;
    static public int wysokoscPlanszy;
    static public final int wysokoscLegendy = 70;

    public UI() // Konstruktor obiektu rozszerzajacego JFrame - tworzy podstawowe okienko
    {
        Dimension ekran = Toolkit.getDefaultToolkit().getScreenSize();

        okno = new JFrame();

        //ustawia pozycje calego okna na ekranie
        okno.setBounds((ekran.width - szerokoscOkna) / 2,
                        (ekran.height - wysokoscOkna) / 2,
                    szerokoscOkna,
                    wysokoscOkna);

        setTitle("Wirtualny Swiat");
        okno.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okno.setResizable(false);
        okno.setLayout(null);

        JMenuBar pasekMenu = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        nowaGra = new JMenuItem("Nowa Gra");
        wczytaj = new JMenuItem("Wczytaj");
        zapisz = new JMenuItem("Zapisz");
        wyjscie = new JMenuItem("Wyjscie");

        nowaGra.addActionListener(this);
        wczytaj.addActionListener(this);
        zapisz.addActionListener(this);
        wyjscie.addActionListener(this);

        menu.add(nowaGra);
        menu.add(wczytaj);
        menu.add(zapisz);
        menu.add(wyjscie);

        pasekMenu.add(menu);
        okno.setJMenuBar(pasekMenu);
        okno.setLayout(new CardLayout());

        glownyPanel = new JPanel();
        glownyPanel.setBackground(Color.BLACK);
        glownyPanel.setLayout(null);

        okno.addKeyListener(this); // obsluga klawiatury
        okno.add(glownyPanel);
        okno.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == nowaGra)
        {
            int rozmiarX = Integer.parseInt(JOptionPane.showInputDialog(okno, "Podaj szerokosc swiata", "10"));
            int rozmiarY = Integer.parseInt(JOptionPane.showInputDialog(okno, "Podaj wysokosc swiata", "10"));

            swiat = new Swiat(rozmiarX, rozmiarY, this);
            swiat.CzyscTekst();

            if(planszaGraficzna != null) glownyPanel.remove(planszaGraficzna);
            if(komunikatGraficzny != null) glownyPanel.remove(komunikatGraficzny);
            if(koloryOrganizmow != null) glownyPanel.remove(koloryOrganizmow);

            UruchomGre();
        }

        if(e.getSource() == wczytaj)
        {
            if(swiat!=null) swiat = null;

            if(planszaGraficzna != null) glownyPanel.remove(planszaGraficzna);
            if(komunikatGraficzny != null) glownyPanel.remove(komunikatGraficzny);
            if(koloryOrganizmow != null) glownyPanel.remove(koloryOrganizmow);

            String nazwaPliku = JOptionPane.showInputDialog(okno, "Nazwa pliku do wczytania:", "");
            swiat = Swiat.WczytajZPliku(nazwaPliku);
            swiat.UstawInterfejsGraficzny(this);
            swiat.CzyscTekst();
            planszaGraficzna = new UIPlanszy(swiat);
            komunikatGraficzny = new UIKomunikatu();
            koloryOrganizmow = new Legenda();

            UruchomGre();
        }
        if(e.getSource() == zapisz)
        {
            String nazwaPliku = JOptionPane.showInputDialog(okno, "Nazwa pliku do zapisania:", "");
            swiat.ZapiszDoPliku(nazwaPliku);
            swiat.DodajTekst("Zapisano.");
            komunikatGraficzny.OdswiezKomunikat();
        }
        if(e.getSource() == wyjscie)
        {
            okno.dispose(); // czysci pamiec zabrana przez GUI
        }
    }
    @Override
    public void keyPressed(KeyEvent e)
    {
        if(swiat!=null&& swiat.WezPauze())
        {
            int klawisz = e.getKeyCode();

            if(swiat.CzyCzlowiekZyje())
            {
                if(klawisz == KeyEvent.VK_UP) swiat.WezCzlowieka().UstawKierunekRuchu(Swiat.Kierunek.GORA);
                else if(klawisz == KeyEvent.VK_DOWN) swiat.WezCzlowieka().UstawKierunekRuchu(Swiat.Kierunek.DOL);
                else if(klawisz == KeyEvent.VK_LEFT) swiat.WezCzlowieka().UstawKierunekRuchu(Swiat.Kierunek.LEWO);
                else if(klawisz == KeyEvent.VK_RIGHT) swiat.WezCzlowieka().UstawKierunekRuchu(Swiat.Kierunek.PRAWO);

                else if(klawisz == KeyEvent.VK_U)
                {
                    if(swiat.WezCzlowieka().CzyUmiejetnoscAktywna())
                    {
                        swiat.DodajTekst("Umiejetnosc jest juz aktywna");
                        WezKomunikatGraficzny().OdswiezKomunikat();
                        return;
                    }
                    else if(swiat.WezCzlowieka().CzyCooldown())
                    {
                        swiat.DodajTekst("Musisz odczekać jeszcze " + swiat.WezCzlowieka().WezCooldown() + " tur by aktywowac umiejetnosc");
                        WezKomunikatGraficzny().OdswiezKomunikat();
                        return;
                    }
                    else
                    {
                        swiat.WezCzlowieka().WlaczUmiejetnosc();
                        swiat.DodajTekst("Umiejetnosc czlowieka aktywowana. Bedzie aktywna przez 5 tur");
                        WezKomunikatGraficzny().OdswiezKomunikat();
                        return;
                    }
                }
                if((klawisz == KeyEvent.VK_UP||klawisz == KeyEvent.VK_DOWN
                        ||klawisz == KeyEvent.VK_LEFT||klawisz == KeyEvent.VK_RIGHT)&&!swiat.CzyCzlowiekZyje())
                {
                    swiat.DodajTekst("Czlowiek nie zyje, nie mozesz nim sterowac");
                    WezKomunikatGraficzny().OdswiezKomunikat();
                    return;
                }

            } else if(klawisz == KeyEvent.VK_SPACE)
            {//nastepna tura
            }
            else
            {
                return;
            }

            swiat.UstawPauze(false);
            swiat.CzyscTekst();
            swiat.WykonajTure();
            OdswiezWszystko();
            swiat.UstawPauze(true);
        }
    }
    private class UIPlanszy extends JPanel
    {
        private final int rozmiarX;
        private final int rozmiarY;
        private Kwadracik[][] kwadraciki;

            public UIPlanszy(Swiat swiat)
            {
                super();

                this.rozmiarX = swiat.WezX();
                this.rozmiarY = swiat.WezY();

                double stosunek = (double)rozmiarX/rozmiarY;

                if(stosunek >1.0)
                {
                    szerokoscPlanszy = glownyPanel.getWidth();
                    wysokoscPlanszy = (int)(szerokoscPlanszy * (1/stosunek));
                }
                else
                {
                    wysokoscPlanszy =(int) ((double)glownyPanel.getHeight() * ((double)3/5));
                    szerokoscPlanszy = (int)(wysokoscPlanszy * stosunek );
                }

                setBounds(glownyPanel.getX() + glownyPanel.getWidth()/2 - szerokoscPlanszy/2,
                        glownyPanel.getY(),
                        szerokoscPlanszy
                        ,wysokoscPlanszy
                );

                kwadraciki = new Kwadracik[rozmiarX][rozmiarY];

                for(int j = 0; j < rozmiarY; j++)
                {
                    for(int i = 0; i < rozmiarX; i++)
                    {
                        kwadraciki[i][j] = new Kwadracik(i, j);
                        kwadraciki[i][j].addActionListener(e -> {
                            if(e.getSource() instanceof Kwadracik pole)
                            {
                                if(pole.CzyPuste())
                                {
                                    new ListaOrganizmow(pole.getX() + okno.getX(),
                                            pole.getY() + okno.getY(),
                                            new Punkt(pole.WezX(), pole.WezY()));
                                }
                            }
                        });
                    }
                }

                for(int j = 0; j < rozmiarY; j++)
                {
                    for(int i = 0; i < rozmiarX; i++)
                    {
                        this.add(kwadraciki[i][j]);
                    }   // dodaje nam kwadraciki na pusta mape
                }
                this.setLayout(new GridLayout(rozmiarY, rozmiarX));
            }

        private class Kwadracik extends JButton
        {
            private boolean czyPuste;
            private final int pozX;
            private final int pozY;

            public Kwadracik(int x, int y)
            {
                super();
                pozX = x;
                pozY = y;
                czyPuste = true;
                setBackground(Color.WHITE);
            }

            public boolean CzyPuste()
            {
                return czyPuste;
            }
            public void UstawPuste(boolean puste)
            {
                czyPuste = puste;
            }
            public int WezX()
            {
                return pozX;
            }
            public int WezY()
            {
                return pozY;
            }
        }

        public void OdswiezPlansze()
        {
            for(int j = 0; j < rozmiarY; j++)
            {
                for(int i = 0; i < rozmiarX; i++)
                {
                    Organizm tmpOrganizm = swiat.WezPlansze()[i][j];
                    if(tmpOrganizm != null)
                    {
                        kwadraciki[i][j].UstawPuste(false);
                        kwadraciki[i][j].setEnabled(false);
                        kwadraciki[i][j].setBackground(swiat.WezPlansze()[i][j].WezKolor());
                    }
                    else
                    {
                        kwadraciki[i][j].UstawPuste(true);
                        kwadraciki[i][j].setEnabled(true);
                        kwadraciki[i][j].setBackground(Color.WHITE);
                    }
                }
            }
        }

    }
    private class UIKomunikatu extends JPanel
    {
        private String komunikat;
        private JTextArea poleTekstowe;

        public UIKomunikatu()
        {
            super();
            setBounds(glownyPanel.getX(),
                    glownyPanel.getY() + planszaGraficzna.getHeight() + wysokoscLegendy,
                    glownyPanel.getWidth(),
                    ((glownyPanel.getHeight() - planszaGraficzna.getHeight()) - wysokoscLegendy)
                    );

            komunikat = swiat.WezTekst();
            poleTekstowe = new JTextArea(komunikat);
            poleTekstowe.setLineWrap(true);
            poleTekstowe.setWrapStyleWord(true);    // lamanie slow na spacjach
            poleTekstowe.setMargin(new Insets(10, 10, 20, 20));
            poleTekstowe.setEditable(false);
            setLayout(new CardLayout());

            JScrollPane widok = new JScrollPane(poleTekstowe);
            add(widok);
        }

        public void OdswiezKomunikat()
        {
            String naglowek = "Autor: Marcel Zielinski - 191005\n" +
                    "poruszanie - strzalki, umiejetnosc - u, nastepna tura - spacja\n";
            komunikat = naglowek + swiat.WezTekst();
            poleTekstowe.setText(komunikat);
        }
    }
    private class Legenda extends JPanel
    {

        public Legenda()
        {
            super();
            this.setBounds(glownyPanel.getX(),
                    glownyPanel.getY() + planszaGraficzna.getHeight(),
                    glownyPanel.getWidth() ,
                    wysokoscLegendy
            );

            setBackground(new Color(224, 118, 118));
            setLayout(new FlowLayout(FlowLayout.CENTER));

            JButton[] przyciski = new JButton[Organizm.ILOSC_ORGANIZMOW + 1];

            przyciski[0] = new JButton("Czlowiek");
            przyciski[0].setBackground(Czlowiek.KOLOR_CZLOWIEKA);

            przyciski[1] = new JButton("Wilk");
            przyciski[1].setBackground(Wilk.KOLOR_WILKA);

            przyciski[2] = new JButton("Owca");
            przyciski[2].setBackground(Owca.KOLOR_OWCY);

            przyciski[3] = new JButton("Lis");
            przyciski[3].setBackground(Lis.KOLOR_LISA);

            przyciski[4] = new JButton("Zolw");
            przyciski[4].setBackground(Zolw.KOLOR_ZOLWIA);

            przyciski[5] = new JButton("Antylopa");
            przyciski[5].setBackground(Antylopa.KOLOR_ANTYLOPY);

            przyciski[6] = new JButton("Trawa");
            przyciski[6].setBackground(Trawa.KOLOR_TRAWY);

            przyciski[7] = new JButton("Mlecz");
            przyciski[7].setBackground(Mlecz.KOLOR_MLECZA);

            przyciski[8] = new JButton("Guarana");
            przyciski[8].setBackground(Guarana.KOLOR_GUARANY);

            przyciski[9] = new JButton("Wilcze-jagody");
            przyciski[9].setBackground(WilczeJagody.KOLOR_JAGOD);

            przyciski[10] = new JButton("Barszcz-sosnowskiego");
            przyciski[10].setBackground(BarszczSosnowskiego.KOLOR_BARSZCZU);

            for(int i = 0; i < Organizm.ILOSC_ORGANIZMOW+1; i++)
            {
                przyciski[i].setEnabled(false);
                add(przyciski[i]);
            }
        }
    }
    private class ListaOrganizmow extends JFrame
    {
        private Organizm.TypOrganizmu[] listaTypowOrganizmow;
        private final JList PoleKombi;

        public ListaOrganizmow(int x, int y, Punkt nowePolozenie)
        {
            super("Organizmy:");
            setBounds(x, y, 150, 400);
            String[] organizmy = new String[]{
                    "Wilk", "Owca", "Lis", "Zolw",
                    "Antylopa", "Trawa", "Mlecz",
                    "Guarana", "WilczeJagody",
                    "BarszczSosnowskiego"
            };
            listaTypowOrganizmow = new Organizm.TypOrganizmu[]{
                    Organizm.TypOrganizmu.WILK,
                    Organizm.TypOrganizmu.OWCA,
                    Organizm.TypOrganizmu.LIS,
                    Organizm.TypOrganizmu.ZOLW,
                    Organizm.TypOrganizmu.ANTYLOPA,
                    Organizm.TypOrganizmu.TRAWA,
                    Organizm.TypOrganizmu.MLECZ,
                    Organizm.TypOrganizmu.GUARANA,
                    Organizm.TypOrganizmu.WILCZE_JAGODY,
                    Organizm.TypOrganizmu.BARSZCZ_SOSNOWSKIEGO
            };

            PoleKombi = new JList(organizmy);
            PoleKombi.setVisibleRowCount(organizmy.length);
            PoleKombi.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            PoleKombi.addListSelectionListener(e -> {
                Organizm tmpOrganizm = KreatorOrganizmow.StworzNowyOrganizm
                        (listaTypowOrganizmow[PoleKombi.getSelectedIndex()], nowePolozenie, swiat);
                swiat.DodajOrganizm(tmpOrganizm);
                swiat.DodajTekst("Gracz tworzy nowy organizm: " + tmpOrganizm.WypiszOrganizm());
                OdswiezWszystko();
                dispose();  //usuwa okienko wyboru
            });

            JScrollPane widok = new JScrollPane(PoleKombi);
            add(widok);
            setVisible(true);
        }
    }
    private void UruchomGre()
    {
        planszaGraficzna = new UIPlanszy(swiat);
        glownyPanel.add(planszaGraficzna);

        komunikatGraficzny = new UIKomunikatu();
        glownyPanel.add(komunikatGraficzny);

        koloryOrganizmow = new Legenda();
        glownyPanel.add(koloryOrganizmow);

        OdswiezWszystko();
    }
    public void OdswiezWszystko()
    {
        planszaGraficzna.OdswiezPlansze();
        komunikatGraficzny.OdswiezKomunikat();
        SwingUtilities.updateComponentTreeUI(okno);
        okno.requestFocusInWindow();
    }
    public UIKomunikatu WezKomunikatGraficzny() {
        return komunikatGraficzny;
    }
    @Override
    public void keyTyped(KeyEvent e){}
    @Override
    public void keyReleased(KeyEvent e) {}
}