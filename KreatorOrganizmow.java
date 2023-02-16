package po2proj;

import po2proj.rosliny.*;
import po2proj.zwierzeta.*;

public class KreatorOrganizmow
{
    static Organizm StworzNowyOrganizm(Organizm.TypOrganizmu typ, Punkt pozycja, Swiat swiat)
    {
        if(typ== Organizm.TypOrganizmu.CZLOWIEK) { return new Czlowiek(swiat, pozycja);}
        else if(typ== Organizm.TypOrganizmu.WILK) { return new Wilk(swiat, pozycja);}
        else if(typ== Organizm.TypOrganizmu.OWCA) { return new Owca(swiat, pozycja);}
        else if(typ== Organizm.TypOrganizmu.LIS) { return new Lis(swiat, pozycja);}
        else if(typ== Organizm.TypOrganizmu.ZOLW) { return new Zolw(swiat, pozycja);}
        else if(typ== Organizm.TypOrganizmu.ANTYLOPA) { return new Antylopa(swiat, pozycja);}
        else if(typ== Organizm.TypOrganizmu.TRAWA) { return new Trawa(swiat, pozycja);}
        else if(typ== Organizm.TypOrganizmu.MLECZ) { return new Mlecz(swiat, pozycja);}
        else if(typ== Organizm.TypOrganizmu.GUARANA) { return new Guarana(swiat, pozycja);}
        else if(typ== Organizm.TypOrganizmu.WILCZE_JAGODY) { return new WilczeJagody(swiat, pozycja);}
        else if(typ== Organizm.TypOrganizmu.BARSZCZ_SOSNOWSKIEGO) { return new BarszczSosnowskiego(swiat, pozycja);}
        else return null;
    }
}
