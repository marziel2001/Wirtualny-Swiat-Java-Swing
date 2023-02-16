package po2proj;

import java.util.Random;

public abstract class Zwierze extends Organizm
{
    protected static final int ZASIEG_ZWIERZECIA = 1;
    protected static final int SZANSA_ROZMNOZENIA = 50;

    public Zwierze() {}
    @Override
    public void Akcja()
    {
        this.Ruch();
    }

    public void Rozmnazaj()
    {
        Random rand = new Random();

        if( rand.nextInt(100) < SZANSA_ROZMNOZENIA)
        {
            Punkt WolnePole = this.swiat.LosujWolnePoleObok(this.polozenie, 1);

            if(!(WolnePole.equals(this.polozenie)))
            {
                Organizm organizm = KreatorOrganizmow.StworzNowyOrganizm(this.typ, WolnePole,this.swiat);
                this.swiat.DodajTekst(this.WypiszOrganizm() + " rozmnaza sie");
                this.swiat.DodajOrganizm(organizm);
            }
        }
    }
    public boolean CzyWygralWalke(Organizm atakujacy)
    {
        return this.sila > atakujacy.WezSile();
    }

    public boolean CzyZwierze()
    {
        return true;
    }
    @Override
    public void Kolizja(Organizm atakujacy)
    {
        if(this.WezTyp() == atakujacy.WezTyp())
        {
            if(atakujacy.WezTureUrodzenia()!=this.swiat.WezTura())
            {
                this.Rozmnazaj();
            }
        }
        else
        {
            if(this.CzyWygralWalke(atakujacy))
            {
                this.swiat.DodajTekst(this.WypiszOrganizm() + " zabija " + atakujacy.WypiszOrganizm());
                this.swiat.UsunOrganizm(atakujacy);
            }
            else
            {
                this.swiat.DodajTekst(atakujacy.WypiszOrganizm() + " zabija " + this.WypiszOrganizm());
                this.swiat.UsunOrganizm(this);
            }
        }
    }
    protected void Ruch()
    {
        Punkt noweMiejsce = this.swiat.LosujPoleObok(this.polozenie, ZASIEG_ZWIERZECIA);

        if(!(noweMiejsce.equals(this.polozenie)))
        {
            Organizm przeszkoda = this.swiat.WezPlansze()[noweMiejsce.WezX()][noweMiejsce.WezY()];

                if(przeszkoda != null)  // cos stoi
                {
                    if(przeszkoda.CzyZyje())
                    {
                        przeszkoda.Kolizja(this);
                        if((!przeszkoda.CzyZyje())&&this.CzyZyje())
                        {
                            this.WykonajRuch(noweMiejsce);
                        }
                    }
                    else
                    {
                        this.WykonajRuch(noweMiejsce);
                    }
                }
                else
                {
                    this.WykonajRuch(noweMiejsce);
                }
        } // else nie ma miejsca na planszy
    }
}