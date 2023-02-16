package po2proj.rosliny;

import po2proj.Organizm;
import po2proj.Punkt;
import po2proj.Roslina;
import po2proj.Swiat;

import java.awt.Color;

public class BarszczSosnowskiego extends Roslina
{
    private static final int SILA_BARSZCZU = 10;
    public static final Color KOLOR_BARSZCZU = new Color(26, 86, 0);

    public BarszczSosnowskiego(Swiat swiat, Punkt polozenie)
    {
        this.inicjatywa = INICJATYWA_ROSLIN;
        this.polozenie = polozenie;
        this.sila = SILA_BARSZCZU;
        this.swiat = swiat;
        this.typ = TypOrganizmu.BARSZCZ_SOSNOWSKIEGO;
        this.turaUrodzenia = swiat.WezTura();
        this.zyje = true;
        this.kolor =KOLOR_BARSZCZU;
    }

    @Override
    public void Akcja()
    {
        // zabija wszystkie zwierzeta dookola
        int x = this.WezPolozenie().WezX();
        int y = this.WezPolozenie().WezY();

        if( x > 0 && (this.swiat.WezPlansze()[x - 1][y] != null))
        {
            if(this.swiat.WezPlansze()[x - 1][y].CzyZyje() && this.swiat.WezPlansze()[x - 1][y].CzyZwierze())
            {
                this.swiat.DodajTekst(this.WypiszOrganizm() + " zabija " + this.swiat.WezPlansze()[x-1][y].WypiszOrganizm());
                this.swiat.UsunOrganizm(this.swiat.WezPlansze()[x - 1][y]);
            }
        }
        if(x < this.swiat.WezX()-1 && this.swiat.WezPlansze()[x + 1][y] != null)
        {
            if(this.swiat.WezPlansze()[x + 1][y].CzyZyje() && this.swiat.WezPlansze()[x + 1][y].CzyZwierze())
            {
                this.swiat.DodajTekst(this.WypiszOrganizm() + " zabija " + this.swiat.WezPlansze()[x + 1][y].WypiszOrganizm());
                this.swiat.UsunOrganizm(this.swiat.WezPlansze()[x + 1][y]);
            }
        }
        if( y>0 && this.swiat.WezPlansze()[x][y - 1] != null)
        {
            if(this.swiat.WezPlansze()[x][y - 1].CzyZyje() && this.swiat.WezPlansze()[x][y - 1].CzyZwierze())
            {
                this.swiat.DodajTekst(this.WypiszOrganizm() + " zabija " + this.swiat.WezPlansze()[x][y - 1].WypiszOrganizm());
                this.swiat.UsunOrganizm(this.swiat.WezPlansze()[x][y - 1]);
            }
        }
        if( y<this.swiat.WezY() - 1 && this.swiat.WezPlansze()[x][y + 1] != null)
        {
            if(this.swiat.WezPlansze()[x][y + 1].CzyZyje() && this.swiat.WezPlansze()[x][y + 1].CzyZwierze())
            {
                this.swiat.DodajTekst(this.WypiszOrganizm() + " zabija " + this.swiat.WezPlansze()[x][y + 1].WypiszOrganizm());
                this.swiat.UsunOrganizm(this.swiat.WezPlansze()[x][y + 1]);
            }
        }

        this.Rozprzestrzenianie();
    }

    @Override
    public void Kolizja(Organizm atakujacy)
    {
        if(atakujacy.CzyZyje())
        {
            this.swiat.UsunOrganizm(atakujacy);
            this.swiat.UsunOrganizm(this);
        }
    }

    @Override
    public String WypiszGatunek()
    {
        return "BarszczSosnowskiego";
    }
}