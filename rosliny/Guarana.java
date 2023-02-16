package po2proj.rosliny;

import po2proj.Organizm;
import po2proj.Punkt;
import po2proj.Roslina;
import po2proj.Swiat;

import java.awt.Color;

public class Guarana extends Roslina
{
    private static final int SILA_GUARANY = 0;
    public static final Color KOLOR_GUARANY = new Color(0, 255, 209);

    public Guarana(Swiat swiat, Punkt polozenie)
    {
        this.inicjatywa = INICJATYWA_ROSLIN;
        this.polozenie = polozenie;
        this.sila = SILA_GUARANY;
        this.swiat = swiat;
        this.typ = TypOrganizmu.GUARANA;
        this.turaUrodzenia = swiat.WezTura();
        this.zyje = true;
        this.kolor = KOLOR_GUARANY;
    }

    @Override
    public void Kolizja(Organizm atakujacy)
    {
        atakujacy.UstawSile(atakujacy.WezSile() + 3);
        this.swiat.DodajTekst(atakujacy.WypiszGatunek() + " zjada " + this.WypiszGatunek() + ", otrzymuje +3 do sily");
        this.swiat.UsunOrganizm(this);
    }

    @Override
    public String WypiszGatunek()
    {
        return "guarana";
    }
}
