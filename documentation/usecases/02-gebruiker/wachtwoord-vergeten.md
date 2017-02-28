## Use case: Wijzigen van wachtwoord van niet-ingelogde klant

**Primaire actor:** De klant (een bedrijf)

**Objectief:** De klant moet in staat zijn een nieuw wachtwoord aan te vragen
indien hij het oude vergeten is

**Normale flow:**

1.  De gebruiker geeft aan dat hij zijn wachtwoord vergeten is

2.  Het systeem vraagt om het e-mailadres van de gebruiker

3.  De gebruiker geeft zijn e-mailadres op

4.  Het systeem stuur een link naar de gebruiker om zijn wachtwoord te kunnen
    wijzigen

5.  De gebruiker klikt op de link in de e-mail

6.  Het systeem vraagt om een nieuw wachtwoord tweemaal in te geven

7.  De gebruiker geeft het nieuwe wachtwoord tweemaal in

8.  Het systeem verandert het wachtwoord van de gebruiker naar het nieuwe
    wachtwoord en stuurt een email naar de gebruiker als bevestiging dat het
    wachtwoord gewijzigd is

**Pre-condities**

De klant is niet ingelogd op de webapplicatie

**Post-condities**

Het wachtwoord van de klant is gewijzigd

**Alternatieve flow:**

* (3). De gebruiker geeft een verkeerd e-mailadres in

  a. Het systeem geeft een melding dat het e-mailadres niet gevonden is

  b. Ga naar 2

* (7). De twee ingegeven wachtwoorden komen niet overeen

  a. Het systeem geeft een melding van het probleem

  b. Ga naar 4

* (7). Het ingegeven wachtwoord voldoet niet aan de minimum vereisten voor een
    wachtwoord

  a. Het systeem geeft een melding van het probleem
 
  b. Ga naar 4
