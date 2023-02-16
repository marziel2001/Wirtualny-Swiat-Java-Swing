package po2proj.zwierzeta;

import po2proj.Punkt;
import po2proj.Swiat;
import po2proj.Zwierze;

import java.awt.*;

public class Owca extends Zwierze
{
    private static final int INICJATYWA_OWCY = 4;
    private static final int SILA_OWCY = 4;
    public static final Color KOLOR_OWCY = new Color(117, 117, 117);


    public Owca(Swiat swiat, Punkt polozenie)
    {
        this.inicjatywa = INICJATYWA_OWCY;
        this.polozenie = polozenie;
        this.sila = SILA_OWCY;
        this.swiat = swiat;
        this.typ = TypOrganizmu.OWCA;
        this.turaUrodzenia = swiat.WezTura();
        this.zyje = true;
        this.kolor = KOLOR_OWCY;
    }

    @Override
    public String WypiszGatunek()
    {
        return "owca";
    }
}
