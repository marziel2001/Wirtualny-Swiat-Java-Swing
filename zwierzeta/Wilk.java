package po2proj.zwierzeta;

import po2proj.Punkt;
import po2proj.Swiat;
import po2proj.Zwierze;

import java.awt.*;

public class Wilk extends Zwierze
{
    private static final int INICJATYWA_WILKA = 5;
    private static final int SILA_WILKA = 9;
    public static final Color KOLOR_WILKA = new Color(112, 37, 0);

    public Wilk(Swiat swiat, Punkt polozenie)
    {
        this.inicjatywa = INICJATYWA_WILKA;
        this.polozenie = polozenie;
        this.sila = SILA_WILKA;
        this.swiat = swiat;
        this.typ = TypOrganizmu.WILK;
        this.turaUrodzenia = swiat.WezTura();
        this.zyje = true;
        this.kolor = KOLOR_WILKA;
    }

    @Override
    public String WypiszGatunek()
    {
        return "wilk";
    }
}
