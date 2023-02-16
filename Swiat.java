package po2proj;

import po2proj.zwierzeta.Czlowiek;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

public class Swiat
{
    private static final int ZAPELNIENIE_SWIATA = 10; //%

    static private String komunikat = "";
    private int x, y;
    private int tura;
    private boolean pauza;// jak pauza
    private boolean koniec;
    private Organizm[][] plansza;
    private ArrayList<Organizm> organizmy;
    private Czlowiek czlowiek;
    private UI InterfejsGraficzny;

    public enum Kierunek
    {
        GORA,
        PRAWO,
        DOL,
        LEWO,
        BRAK;

        public static Kierunek losujKierunek()
        {
            Random rand = new Random();
            return values()[rand.nextInt(4)];
        }

        public static Kierunek WezKierunek(int x)
        {
            return values()[x%4];
        }
    }
    public Swiat(int x, int y, UI InterfejsGraficzny)
    {
        this.x = x;
        this.y = y;
        this.koniec = false;
        this.tura = 0;
        this.pauza = true;
        this.organizmy = new ArrayList<>();
        this.StworzPlansze(x, y);
        this.czlowiek = null;
        this.WypelnijSwiat();
        this.InterfejsGraficzny = InterfejsGraficzny;
    }
    public Swiat(UI InterfejsGraficzny)
    {
        this.x = 0;
        this.y = 0;
        this.koniec = false;
        this.tura = 0;
        this.pauza = true;
        this.organizmy = new ArrayList<>();
        this.plansza = null;
        this.czlowiek = null;
        this.InterfejsGraficzny = InterfejsGraficzny;
    }
    public void WykonajTure()
    {
        this.tura++;
        this.DodajTekst("Numer tury: " + this.tura);

        if(this.czlowiek != null && this.czlowiek.CzyUmiejetnoscAktywna())
        {
            this.DodajTekst("Umiejetnosc czlowieka aktywna");
        }
        else if(this.czlowiek == null)
        {
            this.DodajTekst("Czlowiek nie zyje");
        }
        this.SortujOrganizmy();
        this.WykonajAkcjeOrganizmow();
        this.CzyscListe();  // usuwa zabite organizmy z arraylisty
    }
    private void WykonajAkcjeOrganizmow()
    {
        for (int i = 0; i < organizmy.size(); i++)
        {
            if (organizmy.get(i).CzyZyje() && organizmy.get(i).WezTureUrodzenia() != this.tura)
            {
                organizmy.get(i).Akcja();
            }
        }
    }
    public void DodajOrganizm(Organizm organizm)
    {
        if(this.organizmy.size() < ((this.x * this.y)))
        {
            this.organizmy.add(organizm);
            Punkt polozenie = organizm.WezPolozenie();
            this.plansza[polozenie.WezX()][polozenie.WezY()] = organizm;
        }
    }
    public void UsunOrganizm(Organizm organizm)
    {
        Punkt polozenie = organizm.WezPolozenie();
        this.plansza[polozenie.WezX()][polozenie.WezY()] = null;

        if(organizm.WezTyp() == Organizm.TypOrganizmu.CZLOWIEK) this.czlowiek = null;

        organizm.Zabij();
    }
    public void CzyscListe()
    {
        for (int i = 0; i < this.organizmy.size();i++)
        {
            if (!organizmy.get(i).CzyZyje())
            {
                organizmy.remove(i);
                i--;
            }
        }
    }
    public Punkt LosujWolnePole()
    {
        boolean miejsce = false;

        for( int i = 0; i < this.x; i++)
        {
            for( int j = 0; j < this.y; j++)
            {
                if(plansza[i][j] == null)
                {
                    miejsce = true;
                    break;
                }
            }
        }

        if(miejsce)
        {
            Random rand = new Random();

            while(true)
            {
                int t_x = rand.nextInt(this.x);
                int t_y = rand.nextInt(this.y);

                if(plansza[t_x][t_y] == null)
                {
                    return new Punkt(t_x, t_y);
                }
            }
        }
        else
        {
            return new Punkt(-1, 0);
        }
    }
    public Punkt LosujPoleObok(Punkt polozenie, int zasieg)
    {
        Kierunek kierunek = Kierunek.losujKierunek();

        while(zasieg>=1)
        {
            if(kierunek == Kierunek.LEWO && polozenie.WezX() >= zasieg)
            {
                return new Punkt(polozenie.WezX() - zasieg, polozenie.WezY());
            }
            else if(kierunek == Kierunek.PRAWO && polozenie.WezX() < this.x - zasieg)
            {
                return new Punkt(polozenie.WezX() + zasieg, polozenie.WezY());
            }
            else if(kierunek == Kierunek.GORA && polozenie.WezY() >= zasieg)
            {
                return new Punkt(polozenie.WezX(), polozenie.WezY() - zasieg);
            }
            else if(kierunek == Kierunek.DOL && polozenie.WezY() < this.WezY() - zasieg)
            {
                return new Punkt(polozenie.WezX(), polozenie.WezY() + zasieg);
            }
            zasieg--;
        }
        return polozenie;
    }
    public Punkt LosujWolnePoleObok(Punkt polozenie, int zasieg)
    {
        Random rand = new Random();
        int kierunek = rand.nextInt(4);

        while(zasieg>=1)
        {
            for(int i =0;i<4;i++)
            {
                if(Kierunek.WezKierunek(kierunek+i)==Kierunek.LEWO
                        && polozenie.WezX() >= zasieg
                        && this.plansza[polozenie.WezX() - zasieg][polozenie.WezY()] == null)
                {
                    return new Punkt(polozenie.WezX() - zasieg, polozenie.WezY());
                }
                else if(Kierunek.WezKierunek(kierunek+i)==Kierunek.PRAWO
                        && polozenie.WezX() < this.x - zasieg
                        && this.plansza[polozenie.WezX() + zasieg][polozenie.WezY()] == null)
                {
                    return new Punkt(polozenie.WezX() + zasieg, polozenie.WezY());
                }
                else if(Kierunek.WezKierunek(kierunek+i)==Kierunek.GORA
                        && polozenie.WezY() >= zasieg
                        && this.plansza[polozenie.WezX()][polozenie.WezY() - zasieg] == null)
                {
                    return new Punkt(polozenie.WezX(), polozenie.WezY() - zasieg);
                }
                else if(Kierunek.WezKierunek(kierunek+i)==Kierunek.DOL
                        && polozenie.WezY() < this.y - zasieg
                        && this.plansza[polozenie.WezX()][polozenie.WezY() + zasieg] == null)
                {
                    return new Punkt(polozenie.WezX(), polozenie.WezY() + zasieg);
                }
            }
            zasieg--;
        }
        return polozenie;
    }
    public boolean CzyPozaPlansza(Punkt polozenie)
    {
        return polozenie.WezX() < 0
                || polozenie.WezX() >= this.x
                || polozenie.WezY() < 0
                || polozenie.WezY() >= this.y;
    }
    public void DodajTekst(String tekst)
    {
        komunikat += tekst += "\n";
    }

    public static Swiat WczytajZPliku(String nazwaPliku)
    {
        try {
                nazwaPliku += ".txt";
                File plik = new File(nazwaPliku);

                Scanner wejscie = new Scanner(plik);
                String linia = wejscie.nextLine();
                String[] rozdzielony = linia.split(" "); // podajemy jaki znak rozdziela kolejne zmienne
                int rozmiarX = Integer.parseInt(rozdzielony[0]);
                int rozmiarY = Integer.parseInt(rozdzielony[1]);
                Swiat tmpSwiat = new Swiat(null);
                tmpSwiat.UstawXY(rozmiarX, rozmiarY);
                tmpSwiat.StworzPlansze(rozmiarX,rozmiarY);
                tmpSwiat.tura = Integer.parseInt(rozdzielony[2]);
                tmpSwiat.koniec = Boolean.parseBoolean(rozdzielony[4]);
                tmpSwiat.czlowiek = null;

                while(wejscie.hasNextLine())
                {
                    linia = wejscie.nextLine();
                    rozdzielony = linia.split(" ");
                    Organizm.TypOrganizmu typOrganizmu = Organizm.TypOrganizmu.valueOf(rozdzielony[0]);
                    int x = Integer.parseInt(rozdzielony[1]);
                    int y = Integer.parseInt(rozdzielony[2]);

                    Organizm tmpOrganizm = KreatorOrganizmow.StworzNowyOrganizm(typOrganizmu, new Punkt(x, y), tmpSwiat);
                    tmpOrganizm.UstawSile(Integer.parseInt(rozdzielony[3]));
                    tmpOrganizm.UstawTureUrodzenia(Integer.parseInt(rozdzielony[4]));
                    tmpOrganizm.UstawZyje(Boolean.parseBoolean(rozdzielony[5]));

                    if(typOrganizmu == Organizm.TypOrganizmu.CZLOWIEK)
                    {
                        tmpSwiat.czlowiek = (Czlowiek)tmpOrganizm;
                    }
                    tmpSwiat.DodajOrganizm(tmpOrganizm);
                }
                wejscie.close();
                return tmpSwiat;
        }
        catch (IOException wyjatek)
        {
            System.out.println("WYjatek: " + wyjatek);
            return null;
        }
    }
    private void SortujOrganizmy()
    {
        organizmy.sort((o1, o2) -> {
            if (o1.WezInicjatywe() != o2.WezInicjatywe())
                return Integer.compare(o2.WezInicjatywe(), o1.WezInicjatywe());
            else
                return Integer.compare(o1.WezTureUrodzenia(), o2.WezTureUrodzenia());
        });
    }
    private void WypelnijSwiat()
    {
        int rozmiarPlanszy = this.x * this.y;
        Punkt pole = this.LosujWolnePole();
        Organizm tmpOrganizm = KreatorOrganizmow.StworzNowyOrganizm(Organizm.TypOrganizmu.CZLOWIEK, pole, this);
        this.DodajOrganizm(tmpOrganizm);
        this.czlowiek = (Czlowiek)tmpOrganizm;

        for(int i = 0; i<rozmiarPlanszy * ( ZAPELNIENIE_SWIATA / (double)100); i++)
        {
            Organizm.TypOrganizmu typ = Organizm.TypOrganizmu.LosujTyp();
            pole = this.LosujWolnePole();
            this.DodajOrganizm(KreatorOrganizmow.StworzNowyOrganizm(typ, pole, this));
        }
    }
    public void ZapiszDoPliku(String nazwaPliku)
    {
        try {
            nazwaPliku += ".txt";
            File plik = new File(nazwaPliku);
            plik.createNewFile();

            PrintWriter zapis = new PrintWriter(plik);
            zapis.print(this.x + " ");
            zapis.print(this.y + " ");
            zapis.print(this.tura + " ");
            zapis.print(this.CzyCzlowiekZyje() + " ");
            zapis.print(this.WezKoniec() + "\n");

            for (int i = 0; i < organizmy.size(); i++) {
                zapis.print(organizmy.get(i).WezTyp() + " ");
                zapis.print(organizmy.get(i).WezPolozenie().WezX() + " ");
                zapis.print(organizmy.get(i).WezPolozenie().WezY() + " ");
                zapis.print(organizmy.get(i).WezSile() + " ");
                zapis.print(organizmy.get(i).WezTureUrodzenia() + " ");
                zapis.print(organizmy.get(i).CzyZyje() + " ");
                zapis.println();
            }
            zapis.close();
        }

        catch (IOException wyjatek)
        {
            System.out.println("Wyjatek: " + wyjatek);
        }
    }
    private void StworzPlansze(int x, int y)
    {
        this.plansza = new Organizm[x][y];

        for(int i = 0;i<x;i++)
        {
            for(int j = 0; j< y; j++)
            {
                this.plansza[i][j] = null;
            }
        }
    }
    public void CzyscTekst()
    {
        komunikat = "";
    }

    public int WezX()
    {
        return this.x;
    }
    public int WezY()
    {
        return this.y;
    }
    public int WezTura()
    {
        return tura;
    }
    public boolean WezKoniec()
    {
        return koniec;
    }
    public boolean CzyCzlowiekZyje()
    {
        return this.czlowiek != null;
    }
    public Organizm[][] WezPlansze()
    {
        return this.plansza;
    }
    public Czlowiek WezCzlowieka()
    {
        return this.czlowiek;
    }
    public String WezTekst()
    {
        return komunikat;
    }
    public boolean WezPauze() {
        return pauza;
    }

    public void UstawPlansze(int x, int y, Organizm referencja)
    {
        this.plansza[x][y] = referencja;
    }
    public void UstawPauze(boolean pauza) {
        this.pauza = pauza;
    }
    public void UstawInterfejsGraficzny(UI InterfejsGraficzny)
    {
        this.InterfejsGraficzny = InterfejsGraficzny;
    }
    private void UstawXY(int x, int y) {
        this.x = x;
        this.y = y;
    }
}