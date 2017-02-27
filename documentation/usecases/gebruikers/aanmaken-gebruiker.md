Use case: Aanmaken van een nieuwe medewerker of gebruiker voor bank/leasing of verzekeraar
------------------------------------------------------------------------------------------

**Primaire actor:** Gebruiker (administrator)

**Objectief:** De administrator moet een nieuwe medewerker of gebruiker voor een
bank/leasing of verzekeraar kunnen toevoegen aan het systeem. (Dus geen nieuwe
klant)

**Normale flow**

1.  De administrator geeft aan dat hij een nieuwe gebruiker wil toevoegen, door
    naar het overzicht van de gebruikers te gaan en van daar naar de pagina voor
    een nieuwe gebruiker te gaan.

2.  De administrator vult de gegevens voor deze gebruiker in.

3.  De administrator selecteert de juiste permissies voor de nieuwe medewerker
    (welke klanten de medewerker beheert, of koppelt de gebruiker aan een
    verzekeraar of bank/leasingmaatschappij).

4.  De administrator bevestigt de gegevens.

5.  De administrator wordt teruggestuurd naar het overzicht van de gebruikers en
    krijgt een melding dat de gebruiker is toegevoegd.

6.  De nieuwe gebruiker krijgt een e-mail toegestuurd met een inloglink.

**Pre-condities**

De gebruiker is ingelogd op de webapplicatie en heeft de nodige rechten; d.w.z.
dat gebruiker de administratorrol en bijhorende permissies heeft. Indien een
gebruiker voor een verzekeraar of bank/leasing toegevoegd moet worden, dient er
ook minstens 1 verzekeraar of bank/leasing in het systeem te zijn.

**Post-condities**

Er is een nieuwe gebruiker aangemaakt.

**Alternatieve flow**

-   De gegevens zijn fout.

    1.  Het systeem toont de foute gegevens, met een melding.

    2.  Ga naar (2)
