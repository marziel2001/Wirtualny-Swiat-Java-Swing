package po2proj.rosliny;

import po2proj.Organizm;
import po2proj.Punkt;
import po2proj.Roslina;
import po2proj.Swiat;

import java.awt.*;

public class WilczeJagody extends Roslina
{
    private static final int SILA_JAGOD = 99;
    public static final Color KOLOR_JAGOD = new Color(122, 0, 131);

    public WilczeJagody(Swiat swiat, Punkt polozenie)
    {
        this.inicjatywa = INICJATYWA_ROSLIN;
        this.polozenie = polozenie;
        this.sila = SILA_JAGOD;
        this.typ = TypOrganizmu.WILCZE_JAGODY;
        this.swiat = swiat;
        this.turaUrodzenia = swiat.WezTura();
        this.zyje = true;
        this.kolor = KOLOR_JAGOD;
    }
    @Override
    public void Kolizja(Organizm atakujacy)
    {
        if(atakujacy.CzyZwierze())
        {
            this.swiat.DodajTekst( atakujacy.WypiszOrganizm() + " zjada " + this.WypiszOrganizm() + " i umiera ");
            this.swiat.UsunOrganizm(atakujacy);
            this.swiat.UsunOrganizm(this);
        }
    }

    @Override
    public String WypiszGatunek()
    {
        return "wilczeJagody";
    }
}