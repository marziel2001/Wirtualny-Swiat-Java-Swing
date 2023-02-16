package po2proj.rosliny;

import po2proj.Punkt;
import po2proj.Roslina;
import po2proj.Swiat;

import java.awt.*;

public class Mlecz extends Roslina
{
    private static final int SILA_MLECZA = 0;
    public static final Color KOLOR_MLECZA =  new Color(191, 255, 79);

    public Mlecz(Swiat swiat, Punkt polozenie)
    {
        this.inicjatywa = INICJATYWA_ROSLIN;
        this.polozenie = polozenie;
        this.sila = SILA_MLECZA;
        this.swiat = swiat;
        this.typ = TypOrganizmu.MLECZ;
        this.turaUrodzenia = swiat.WezTura();
        this.zyje = true;
        this.kolor = KOLOR_MLECZA;
    }

    @Override
    public void Akcja()
    {
        for(int i = 0; i < 3; i++)
        {
            this.Rozprzestrzenianie();
        }
    }

    @Override
    public String WypiszGatunek()
    {
        return "Mlecz";
    }
}