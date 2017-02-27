Use case: Aanmaken van een nieuwe klant
---------------------------------------

**Primaire actor:** Gebruiker (medewerker)

**Objectief:** De medewerker moet een nieuwe klant kunnen toevoegen aan het
systeem.

**Normale flow**

1.  De medewerker geeft aan dat hij een nieuwe klant wil toevoegen, door naar
    het overzicht van zijn klanten te gaan en van daar naar de pagina voor een
    nieuwe klant te gaan.

2.  De medewerker vult de basisgegevens voor deze klant in.

3.  De medewerker bevestigt de gegevens.

4.  De medewerker wordt naar het profiel van de nieuwe klant gestuurd.

5.  De nieuwe klant krijgt een e-mail toegestuurd met een inloglink.

**Pre-condities**

De gebruiker is ingelogd op de webapplicatie en heeft de nodige rechten; d.w.z.
dat gebruiker de medewerkerrol en bijhorende permissies heeft.

**Post-condities**

Er is een nieuwe klant aangemaakt.

**Alternatieve flow**

-   (3) De gegevens zijn fout.

    1.  Het systeem toont de foute gegevens, met een melding.

    2.  Ga naar (2)
