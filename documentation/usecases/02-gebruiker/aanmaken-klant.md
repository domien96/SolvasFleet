## Use case: Aanmaken van een nieuwe gebruiker voor een klant

**Primaire actor:** Gebruiker (medewerker)

**Objectief:** De medewerker moet een nieuwe gebruiker voor een klant kunnen
toevoegen aan het systeem.

**Normale flow**

1.  De medewerker gaat naar het overzicht van zijn klanten (transportbedrijven)

2.  De medewerker selecteert het bedrijf waarvoor hij een gebruiker wil
    toevoegen

3.  De medewerker vult de basisgegevens voor deze gebruiker in

4.  De medewerker bevestigt de gegevens

5.  Het systeem stuurt de medewerker naar het profiel van de nieuwe klant

6.  De nieuwe gebruiker krijgt een e-mail toegestuurd met een inloglink

**Pre-condities**

De gebruiker is ingelogd op de webapplicatie en heeft de nodige rechten; d.w.z.
dat gebruiker de medewerkerrol en bijhorende permissies heeft. De medewerker
heeft minstens 1 klant.

**Post-condities**

Er is een nieuwe gebruiker voor een klant aangemaakt.

**Alternatieve flow**

* (4). De gegevens zijn fout

  a. Het systeem toont de foute gegevens, met een melding

  b. Ga naar 3

* (4). De medewerker bevestigt de gegevens niet

  a. Ga naar 3
