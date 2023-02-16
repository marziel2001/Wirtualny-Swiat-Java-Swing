package po2proj.zwierzeta;

import po2proj.Organizm;
import po2proj.Punkt;
import po2proj.Swiat;
import po2proj.Zwierze;

import java.awt.*;
import java.util.Random;

public class Zolw extends Zwierze
{
    private static final int INICJATYWA_ZOLWIA = 1;
    private static final int SILA_ZOLWIA = 2;
    public static final Color KOLOR_ZOLWIA = new Color(255, 0, 0);

    public Zolw(Swiat swiat, Punkt polozenie)
    {
        this.inicjatywa = INICJATYWA_ZOLWIA;
        this.polozenie = polozenie;
        this.sila = SILA_ZOLWIA;
        this.swiat = swiat;
        this.typ = TypOrganizmu.ZOLW;
        this.turaUrodzenia = swiat.WezTura();
        this.zyje = true;
        this.kolor = KOLOR_ZOLWIA;
    }
    @Override
    public void Akcja()
    {
        Random rand = new Random();
        if( rand.nextInt(100) > 75)
        {
            this.Ruch();
        }
    }

    @Override
    public void Kolizja(Organizm atakujacy)
    {
        if(this.WezTyp() == atakujacy.WezTyp())
        {
            this.Rozmnazaj();
        }
        else if(atakujacy.WezSile() > 5)
        {
            if(this.CzyWygralWalke(atakujacy))
            {
                this.swiat.DodajTekst(this.WypiszOrganizm() + " zabija " + atakujacy.WypiszOrganizm());
                this.swiat.UsunOrganizm(atakujacy);
            }
            else
            {
                this.swiat.DodajTekst(atakujacy.WypiszOrganizm() + " zabija " + this.WypiszGatunek());
                this.swiat.UsunOrganizm(this);
            }
        }
        else
        {
            this.swiat.DodajTekst(this.WypiszOrganizm() + " odpiera atak " + atakujacy.WypiszOrganizm());
        }
    }

    public String WypiszGatunek()
    {
        return "zolw";
    }
}