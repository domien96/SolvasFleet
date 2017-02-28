Use case: Gegevens wijzigen van een medewerker
----------------------------------------------

**Primaire actor:** Gebruiker (administrator)

**Objectief:** De administrator moet een medewerker zijn gegevens kunnen
wijzigen.

**Normale flow:**

1.  De administrator gaat naar het overzicht van de medewerkers en gaat vandaar
    naar het profiel van de medewerker, of komt op een andere manier bij het
    profiel terecht.

2.  De administrator geeft aan dat hij deze medewerker zijn gegevens wil
    wijzigen.

3.  De administrator wijzigt de gewenste gegevens.

4.  De administrator bevestigt de wijzigingen.

5.  De administrator krijgt het nieuwe profiel van de gebruiker te zien.

**Pre-condities**

De gebruiker is ingelogd op de webapplicatie en heeft de nodige rechten; d.w.z.
dat gebruiker de administratorrol en bijhorende permissies heeft.

**Post-condities**

De gekozen gebruiker zijn gegevens zijn aangepast.

**Alternatieve flow**

-   (3) De administrator heeft foute gegevens ingegeven.

    1.  Het systeem toont een foutmelding, met de mogelijkheid om de gegevens
        aan te passen.

    2.  Ga naar (3)

-   (4) De administrator annuleert zijn wijzigen.

    1.  De administrator wordt teruggestuurd naar het (ongewijzigde) profiel van
        de gebruiker.