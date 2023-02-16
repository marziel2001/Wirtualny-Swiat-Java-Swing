package po2proj.zwierzeta;

import po2proj.Organizm;
import po2proj.Punkt;
import po2proj.Swiat;
import po2proj.Zwierze;

import java.awt.*;
import java.util.Random;

public class Antylopa extends Zwierze
{
    private static final int INICJATYWA_ANTYLOPY = 4;
    private static final int SILA_ANTYLOPY = 4;
    private static final int ZASIEG_ANTYLOPY = 2;
    public static final Color KOLOR_ANTYLOPY = new Color(255, 240, 55);


    public Antylopa(Swiat swiat, Punkt polozenie)
    {
        this.inicjatywa = INICJATYWA_ANTYLOPY;
        this.polozenie = polozenie;
        this.sila = SILA_ANTYLOPY;
        this.swiat = swiat;
        this.typ = TypOrganizmu.ANTYLOPA;
        this.turaUrodzenia = swiat.WezTura();
        this.zyje = true;
        this.kolor = KOLOR_ANTYLOPY;
    }
    @Override
    public void Ruch()
    {
        Punkt noweMiejsce = this.swiat.LosujPoleObok(this.polozenie, ZASIEG_ANTYLOPY);
        if(!(noweMiejsce.equals(this.polozenie)))
        {

            Organizm przeszkoda = this.swiat.WezPlansze()[noweMiejsce.WezX()][noweMiejsce.WezY()];

            if(przeszkoda != null)
            {
                if(przeszkoda.CzyZyje())
                {
                    przeszkoda.Kolizja(this);
                    if(!przeszkoda.CzyZyje())
                    {
                        this.WykonajRuch(noweMiejsce);
                    }
                }
                else
                {
                    this.WykonajRuch(noweMiejsce);
                }
            }
        } // else nie ma miejsca na planszy
    }

    @Override
    public void Kolizja(Organizm atakujacy)
    {
        if(atakujacy.WezTyp() != this.WezTyp())
        {
            Random rand = new Random();
            if( rand.nextInt(100) < 50)
            {
                Punkt nowePolozenie = this.swiat.LosujWolnePoleObok(this.polozenie, 1);

                if(nowePolozenie.equals(this.polozenie))
                {
                    super.Kolizja(atakujacy);
                }
                else
                {
                    this.swiat.DodajTekst(this.WypiszOrganizm() + " ucieka od: " + atakujacy.WypiszOrganizm());
                    this.WykonajRuch(nowePolozenie);
                }
            }
            else super.Kolizja(atakujacy);
        }
    }

    @Override
    public String WypiszGatunek()
    {
        return "antylopa";
    }
}
