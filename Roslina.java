package po2proj;

import java.util.Random;

public abstract class Roslina extends Organizm
{
    protected static final int INICJATYWA_ROSLIN = 0;
    protected static final int ZASIEG_ROSLIN = 1;
    protected static final int SZANSA_ZASIANIA = 20;

    public Roslina() {}

    @Override
    public boolean CzyZwierze()
    {
        return false;
    }

    protected void Rozprzestrzenianie()
    {
        Random rand = new Random();

        if( rand.nextInt(100) < SZANSA_ZASIANIA)
        {
            Punkt nowePole = this.swiat.LosujWolnePoleObok(this.polozenie, ZASIEG_ROSLIN);
            if(!(nowePole.equals(this.polozenie)))
            {
                Organizm organizm = KreatorOrganizmow.StworzNowyOrganizm(this.typ, nowePole, this.swiat);
                this.swiat.DodajTekst("Wyrasta nowa " + this.WypiszOrganizm());
                this.swiat.DodajOrganizm(organizm);
            }
        }
    }

    @Override
    public void Akcja()
    {
        this.Rozprzestrzenianie();
    }

    @Override
    public void Kolizja(Organizm atakujacy)
    {
        if(atakujacy.CzyZwierze())
        {
            this.swiat.DodajTekst(atakujacy.WypiszOrganizm() + " zjada " + this.WypiszOrganizm());
            this.swiat.UsunOrganizm(this);
        }
    }
}