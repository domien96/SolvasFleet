Use case: Wijzigen van de rechten van een gebruiker
---------------------------------------------------

**Primaire actor:** Gebruiker

**Objectief:** De gebruiker moet een andere gebruiker zijn rechten kunnen
aanpassen.

**Normale flow:**

1.  De gebruiker gaat naar een andere gebruiker zijn profiel

2.  De gebruiker geeft aan dat hij deze gebruiker zijn rechten wil wijzigen

3.  De gebruiker wijzigt de gewenste rechten

4.  De gebruiker bevestigt de wijzigingen

5.  De gebruiker wordt doorgestuurd naar het profiel van de gebruiker, met een
    melding dat de rechten aangepast zijn

**Pre-condities**

De gebruiker is ingelogd op de webapplicatie en heeft de nodige rechten. Het is
dus een gebruiker met de rol administrator of medewerker

**Post-condities**

De rechten van de gekozen gebruiker zijn gewijzigd

**Alternatieve flow**

* (2). De gebruiker probeert de rechten van een gebruiker te wijzigen waarvoor hij het recht niet heeft. Dit gebeurt bijvoorbeeld als een medewerker de rechten van een andere medewerker probeert te wijzigen, aangezien dit enkel door de administrator kan gebeuren.

  a. Het systeem toont een foutmelding

  b. Ga naar 1

* (3). De gebruiker probeert rechten toe te kennen waartoe hij zelf geen rechten heeft. Dit gebeurt bijvoorbeeld als een medewerker rechten voor het beheer van een vloot aan een klant probeert toe te kennen.

  a. Het systeem toont een foutmelding, met de mogelijkheid om de gekozen rechten aan te passen

  b. Ga naar 3

* (4). De gebruiker annuleert zijn wijzigen

  a. De gebruiker wordt teruggestuurd naar het (ongewijzigde) profiel van de gebruiker
