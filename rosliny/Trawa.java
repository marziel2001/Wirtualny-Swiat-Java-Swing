package po2proj.rosliny;

import po2proj.Punkt;
import po2proj.Roslina;
import po2proj.Swiat;

import java.awt.*;

public class Trawa extends Roslina
{
    private static final int SILA_TRAWY = 0;
    public static final Color KOLOR_TRAWY = new Color(0, 255, 10);

    public Trawa(Swiat swiat, Punkt polozenie)
    {
        this.inicjatywa = INICJATYWA_ROSLIN;
        this.polozenie = polozenie;
        this.sila = SILA_TRAWY;
        this.swiat = swiat;
        this.typ = TypOrganizmu.TRAWA;
        this.turaUrodzenia = swiat.WezTura();
        this.zyje = true;
        this.kolor = KOLOR_TRAWY;
    }

    @Override
    public String WypiszGatunek()
    {
        return "trawa";
    }
}