Use case: Verwijderen van een gebruiker
---------------------------------------

**Primaire actor:** Gebruiker (administrator of medewerker)

**Objectief:** De gebruiker moet een andere gebruiker kunnen archiveren in het
systeem.

**Normale flow:**

1.  De gebruiker gaat naar het profiel van de gebruiker.

2.  De gebruiker geeft aan dat hij de gebruiker wil verwijderen.

3.  De gebruiker bevestigt zijn keuze.

4.  De gebruiker wordt teruggestuurd naar het overzicht van de medewerkers en
    krijgt een melding dat de medewerker verwijderd is.

**Pre-condities**

De gebruiker is ingelogd op de webapplicatie en heeft de nodige rechten; d.w.z.
dat gebruiker de administratorrol of medewerkerrol heeft. Ook moet er minstens 1
andere gebruiker in het systeem zijn.

**Post-condities**

De gekozen gebruiker is gearchiveerd.

**Alternatieve flow**

-   (2). De geselecteerde gebruiker kan niet verwijderd worden. Dit kan zijn omdat de
    te verwijderen gebruiker nog actieve dingen heeft (zoals lopende
    verzekeringen) of omdat de gebruiker niet de rechten heeft om de gebruiker
    te kunnen verwijderen (zoals een medewerker die een leasingmaatschappij
    probeert te verwijderen).

    1.  Het systeem gaat terug naar de overzichtspagina en toont een foutmelding
        met uitleg waarom de gebruiker niet verwijderd kon worden.

    2.  Ga naar (2).
