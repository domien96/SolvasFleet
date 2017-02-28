## Use case: Verwijderen van een gebruiker

**Primaire actor:** Gebruiker (administrator of medewerker)

**Objectief:** De gebruiker moet een andere gebruiker kunnen archiveren in het
systeem.

**Normale flow:**

1.  De gebruiker gaat naar het profiel van de gebruiker.

2.  De gebruiker geeft aan dat hij de gebruiker wil verwijderen.

3.  De gebruiker bevestigt zijn keuze.

4.  Het systeem stuurt de gebruiker terug naar het overzicht van de medewerkers en
    geeft een melding dat de medewerker verwijderd is.

**Pre-condities**

De gebruiker is ingelogd op de webapplicatie en heeft de nodige rechten; d.w.z.
dat gebruiker de administratorrol of medewerkerrol heeft. Ook moet er minstens 1
andere gebruiker in het systeem zijn.

**Post-condities**

De gekozen gebruiker is gearchiveerd.

**Alternatieve flow**

* (2). De geselecteerde gebruiker kan niet verwijderd worden. Dit kan zijn omdat de te verwijderen gebruiker nog lopende verzekeringen en dergelijke heeft, of omdat de gebruiker niet de rechten heeft om de gebruiker te kunnen verwijderen (zoals een medewerker die een leasingmaatschappij probeert te verwijderen)

  a. Het systeem gaat terug naar de overzichtspagina en toont een foutmelding met uitleg waarom de gebruiker niet verwijderd kon worden.

  b. Ga naar 2

* (3) De gebruiker bevestigt de keuze niet

  a. Ga naar 1
