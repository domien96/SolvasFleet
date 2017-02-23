
## Use case: wijzigen van wachtwoord van ingelogde klant
---

**Primaire actor:** De klant (een bedrijf)

**Objectief:** De klant moet in staat zijn een nieuw wachtwoord aan te vragen

**Normale flow:**


1. De gebruiker geeft aan dat hij zijn wachtwoord wil wijzigen

2. Het systeem vraagt om het oude (huidige) wachtwoord in te voeren

3. De gebruiker geeft het oude (huidige) wachtwoord in

4. Het systeem vraagt om het nieuwe wachtwoord tweemaal in te geven

5. De gebruiker geeft het nieuwe wachtwoord tweemaal in

6. Het systeem verandert het wachtwoord van de gebruiker naar het nieuwe wachtwoord en stuurt een email naar de gebruiker als bevestiging dat het wachtwoord gewijzigd is

**Pre-condities:** De klant is ingelogd op de webapplicatie

**Post-condities:** Het wachtwoord van de klant is gewijzigd 

**Alternatieve flow:**
* (3). De gebruiker geeft het verkeerde wachtwoord in

&nbsp;&nbsp;&nbsp;&nbsp; a. Ga naar (2)

* (3). De gebruiker geeft het verkeerde wachtwoord herhaaldelijk verkeerd in

&nbsp;&nbsp;&nbsp;&nbsp; a. De gebruiker dient een captcha op te lossen

&nbsp;&nbsp;&nbsp;&nbsp; b. Ga naar (2)

* (5). De twee ingegeven wachtwoorden komen niet overeen

&nbsp;&nbsp;&nbsp;&nbsp; a. Het systeem geeft een melding van het probleem

&nbsp;&nbsp;&nbsp;&nbsp; b. Ga naar (4)

* (5). Het ingegeven wachtwoord voldoet niet aan de minimum vereisten voor een wachtwoord

&nbsp;&nbsp;&nbsp;&nbsp; a. Het systeem geeft een melding van het probleem

&nbsp;&nbsp;&nbsp;&nbsp; b. Ga naar (4)
