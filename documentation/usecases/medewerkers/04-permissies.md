## Use case: Wijzigen van de rechten van een gebruiker
---

**Primaire actor:** Gebruiker

**Objectief:** De gebruiker moet een gebruiker zijn rechten kunnen aanpassen.

**Normale flow:**

1. De gebruiker gaat naar een gebruiker zijn profiel

2. De gebruiker geeft aan dat hij deze gebruiker zijn rechten wil wijzigen

3. De gebruiker wijzigt de gewenste rechten

4. De gebruiker bevestigt de wijzigingen

5. De gebruiker wordt doorgestuurd naar het profiel van de gebruiker


**Pre-condities:** De gebruiker is ingelogd op de webapplicatie en heeft de nodige rechten

**Post-condities:** De rechten van de gekozen gebruiker zijn gewijzigd

**Alternatieve flow:**
* (3). De gebruiker probeert rechten toe te kennen waartoe hij zelf geen rechten heeft
 
&nbsp;&nbsp;&nbsp;&nbsp; a. Het systeem toont een foutmelding, met de mogelijkheid om de gekozen rechten aan te passen

&nbsp;&nbsp;&nbsp;&nbsp; b. Ga naar (3)

* (4). De gebruiker annuleert zijn wijzigen.

&nbsp;&nbsp;&nbsp;&nbsp; De gebruiker wordt teruggestuurd naar het (ongewijzigde) profiel van de gebruiker
