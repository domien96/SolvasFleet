Use case: Gegevens wijzigen van een gebruiker van een klant
-----------------------------------------

**Primaire actor:** Gebruiker (medewerker)

**Objectief:** De medewerker moet de gegevens van de gebruiker voor een klant kunnen wijzigen.

**Normale flow:**

1.  Een medewerker wordt op de hoogte gebracht van een wijziging in de gegevens
    van een gebruiker.

2.  De medewerker gaat naar het profiel van de gebruiker in kwestie.

3.  De medewerker geeft aan dat hij deze gebruiker zijn gegevens wil wijzigen.

4.  De medewerker wijzigt de gewenste gegevens.

5.  De medewerker bevestigt de wijzigingen.

6.  De medewerker krijgt het nieuwe profiel van de gebruiker te zien.

**Pre-condities**

De gebruiker is ingelogd op de webapplicatie en heeft de nodige rechten; d.w.z.
dat gebruiker de medewerkerrol en bijhorende permissies heeft. De medewerker moet minstens
1 klant hebben.

**Post-condities**

De gekozen klant zijn gegevens zijn aangepast.

**Alternatieve flow**

-   (4) De medewerker heeft foute gegevens ingegeven.

    1.  Het systeem toont een foutmelding, met de mogelijkheid om de gegevens
        aan te passen.

    2.  Ga naar (3)

-   (5) De medewerker annuleert zijn wijzigen.

    1.  De medewerker wordt teruggestuurd naar het (ongewijzigde) profiel van de
        klant.
		
-   (3) De medewerker wilt gegevens wijzigen van een gebruiker die bij een klant hoort waarvoor de medewerker geen machtigingen heeft.

    1.  De medewerker wordt teruggestuurd naar het (ongewijzigde) profiel van de
        klant.
