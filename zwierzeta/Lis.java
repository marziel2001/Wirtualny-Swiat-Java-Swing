package po2proj.zwierzeta;

import po2proj.Organizm;
import po2proj.Punkt;
import po2proj.Swiat;
import po2proj.Zwierze;

import java.awt.*;

public class Lis extends Zwierze
{
    private static final int INICJATYWA_LISA = 7;
    private static final int SILA_LISA = 3;
    public static final Color KOLOR_LISA = new Color(255, 115, 0);

    public Lis(Swiat swiat, Punkt polozenie)
    {
        this.inicjatywa = INICJATYWA_LISA;
        this.sila = SILA_LISA;
        this.typ = TypOrganizmu.LIS;
        this.kolor = KOLOR_LISA;
        this.polozenie = polozenie;
        this.swiat = swiat;
        this.turaUrodzenia = swiat.WezTura();
        this.zyje = true;
    }
    @Override
    public void Akcja()
    {
        this.Ruch();
    }

    @Override
    public void Ruch()
    {
        Punkt noweMiejsce = this.swiat.LosujPoleObok(this.polozenie, ZASIEG_ZWIERZECIA);
        Organizm przeszkoda = this.swiat.WezPlansze()[noweMiejsce.WezX()][noweMiejsce.WezY()];
        if(przeszkoda != null && !przeszkoda.CzyZyje())
        {
            this.WykonajRuch(noweMiejsce);
        }
        else if(przeszkoda !=null && przeszkoda != this && przeszkoda.CzyZyje())
        {
            if(przeszkoda.WezSile() <= this.sila)
            {
                przeszkoda.Kolizja(this);
                if(!przeszkoda.CzyZyje())
                {
                    this.WykonajRuch(noweMiejsce);
                }
            }
        }
    }

    @Override
    public String WypiszGatunek()
    {
        return "Lis";
    }
}
