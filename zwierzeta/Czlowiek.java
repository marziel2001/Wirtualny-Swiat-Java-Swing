package po2proj.zwierzeta;

import po2proj.Organizm;
import po2proj.Punkt;
import po2proj.Swiat;
import po2proj.Zwierze;

import java.awt.*;

public class Czlowiek extends Zwierze
{
    private static final int INICJATYWA_CZLOWIEKA = 4;
    private static final int SILA_CZLOWIEKA = 5;
    private static final int CZAS_COOLDOWNU = 5;
    private static final int CZAS_UMIEJETNOSCI = 5;
    public static final Color KOLOR_CZLOWIEKA = new Color(0, 0, 0);

    private Swiat.Kierunek nastepnyRuch;
    private int cooldown;
    private int zostaloUmiejetnosci;

    public Czlowiek(Swiat swiat, Punkt polozenie)
    {
        this.swiat = swiat;
        this.polozenie = polozenie;
        this.inicjatywa = INICJATYWA_CZLOWIEKA;
        this.sila = SILA_CZLOWIEKA;
        this.turaUrodzenia = swiat.WezTura();
        this.typ = TypOrganizmu.CZLOWIEK;
        this.nastepnyRuch = Swiat.Kierunek.BRAK;
        this.zostaloUmiejetnosci = 0;
        this.cooldown = 0;
        this.zyje = true;
        this.kolor = KOLOR_CZLOWIEKA;
    }

    @Override
    public void Akcja()
    {
        this.zmniejszCooldownIUmiejetnosc();

        Punkt noweMiejsce = new Punkt(this.polozenie.WezX(), this.polozenie.WezY());

        if(this.nastepnyRuch == Swiat.Kierunek.PRAWO) noweMiejsce.UstawX( noweMiejsce.WezX() + 1);
        else if(this.nastepnyRuch == Swiat.Kierunek.LEWO) noweMiejsce.UstawX( noweMiejsce.WezX() - 1);
        else if(this.nastepnyRuch == Swiat.Kierunek.DOL)  noweMiejsce.UstawY( noweMiejsce.WezY() + 1);
        else if(this.nastepnyRuch == Swiat.Kierunek.GORA) noweMiejsce.UstawY( noweMiejsce.WezY() - 1);

        if(!(this.polozenie.equals(noweMiejsce)))
        {
            if(!(this.swiat.CzyPozaPlansza(noweMiejsce)))
            {
                Organizm przeszkoda = this.swiat.WezPlansze()[noweMiejsce.WezX()][noweMiejsce.WezY()];

                if(przeszkoda!=null)    // cos stoi
                {
                    if(przeszkoda.CzyZyje())
                    {
                        przeszkoda.Kolizja(this);
                        if((!przeszkoda.CzyZyje())&&this.CzyZyje())
                        {
                            this.WykonajRuch(noweMiejsce);
                        }
                    }
                    else this.WykonajRuch(noweMiejsce);
                }
                else this.WykonajRuch(noweMiejsce);
            }
        }
        this.nastepnyRuch = Swiat.Kierunek.BRAK;
    }

    @Override
    public void Kolizja(Organizm atakujacy)
    {
        if(this.CzyUmiejetnoscAktywna())
        {
            if(atakujacy.WezSile()<this.sila)
            {
                super.Kolizja(atakujacy);
            }
            else
            {
                this.swiat.DodajTekst(this.WypiszOrganizm() + " odbija atak " + atakujacy.WypiszOrganizm());
            }
        }
        else
        {
            super.Kolizja(atakujacy);
        }
    }
    public void zmniejszCooldownIUmiejetnosc()
    {
        if(this.cooldown > 0)
        {
            this.cooldown--;
        }
        if(this.zostaloUmiejetnosci > 0)
        {
            this.zostaloUmiejetnosci--;
        }
    }
    public void WlaczUmiejetnosc()
    {
        if(this.cooldown == 0)
        {
            this.swiat.DodajTekst("Aktywacja niesmiertelnosci czlowieka");
            this.zostaloUmiejetnosci = CZAS_UMIEJETNOSCI;
            this.cooldown = CZAS_COOLDOWNU + CZAS_UMIEJETNOSCI;
        }
    }
    public boolean CzyUmiejetnoscAktywna()
    {
        return this.zostaloUmiejetnosci > 0;
    }
    public boolean CzyCooldown() {
            return cooldown>0;
    }

    public int WezCooldown()
    {
        return cooldown;
    }
    @Override
    public String WypiszGatunek()
    {
        return "Czlowiek";
    }

    public void UstawKierunekRuchu(Swiat.Kierunek nowyKierunek)
    {
        this.nastepnyRuch = nowyKierunek;
    }
}