package po2proj;

import java.awt.*;
import java.util.Random;

public abstract class Organizm
{
    static public final int ILOSC_ORGANIZMOW = 10;

    protected Color kolor;
    protected int sila;
    protected int inicjatywa;
    protected Punkt polozenie;
    protected Swiat swiat;
    protected TypOrganizmu typ;
    protected int turaUrodzenia;
    protected boolean zyje;

    public enum TypOrganizmu
    {
        CZLOWIEK,
        WILK,
        OWCA,
        LIS,
        ZOLW,
        ANTYLOPA,
        TRAWA,
        MLECZ,
        GUARANA,
        WILCZE_JAGODY,
        BARSZCZ_SOSNOWSKIEGO;

        public static TypOrganizmu LosujTyp()
        {
            Random rand = new Random();
            return values()[rand.nextInt(values().length-1)+1];
        }
    }

    public Organizm()
    {
        this.inicjatywa = 0;
        this.polozenie = new Punkt(0, 0);
        this.sila = 0;
        this.swiat = null;
        this.turaUrodzenia = 0;
        this.typ = TypOrganizmu.CZLOWIEK;
        this.zyje = true;
    }

    public abstract void Akcja();
    public abstract void Kolizja(Organizm atakujacy);
    public abstract boolean CzyZwierze();
    public abstract String WypiszGatunek();

    public void WykonajRuch(Punkt polozenie)
    {
        if(!(this.swiat.CzyPozaPlansza(polozenie)))
        {
            this.swiat.UstawPlansze(this.polozenie.WezX(), this.polozenie.WezY(), null);
            this.polozenie = polozenie;
            this.swiat.UstawPlansze(polozenie.WezX(), polozenie.WezY(), this);
        }
    }
    public String WypiszOrganizm()
    {
        return this.WypiszGatunek() + " x: "
                + this.polozenie.WezX()
                + " y: " + this.polozenie.WezY()
                + " sila: " + this.WezSile();
    }

    public void Zabij() {
        this.zyje = false;
    }
    public void UstawSile(int sila)
    {
        this.sila = sila;
    }
    public void UstawTureUrodzenia(int tura)
    {
        this.turaUrodzenia = tura;
    }
    public void UstawZyje(boolean zyje) {
        this.zyje = zyje;
    }

    public int WezSile()
    {
        return this.sila;
    }
    public int WezTureUrodzenia()
{
    return this.turaUrodzenia;
}
    public int WezInicjatywe()
    {
        return this.inicjatywa;
    }
    public boolean CzyZyje()
    {
        return this.zyje;
    }
    public TypOrganizmu WezTyp()
    {
        return this.typ;
    }
    public Punkt WezPolozenie()
    {
        return this.polozenie;
    }
    public Color WezKolor() {
        return this.kolor;
    }
}