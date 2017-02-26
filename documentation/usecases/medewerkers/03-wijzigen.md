## Use case: Gegevens wijzigen van een medewerker
---

**Primaire actor:** Gebruiker

**Objectief:** De gebruiker moet een gebruiker zijn gegevens kunnen wijzigen.

**Normale flow:**

1. De gebruiker gaat naar een gebruiker zijn profiel

2. De gebruiker geeft aan dat hij deze gebruiker zijn gegevens wil wijzigen

3. De gebruiker wijzigt de gewenste gegevens

4. De gebruiker bevestigt de wijzigingen

5. De gebruiker krijgt het nieuwe profiel van de gebruiker te zien


**Pre-condities:** De gebruiker is ingelogd op de webapplicatie en heeft de nodige rechten

**Post-condities:** De gekozen gebruiker is gewijzigd

**Alternatieve flow:**
* (3). De gebruiker heeft foute gegevens ingegeven
 
&nbsp;&nbsp;&nbsp;&nbsp; a. Het systeem toont een foutmelding, met de mogelijkheid om de gegevens aan te passen

&nbsp;&nbsp;&nbsp;&nbsp; b. Ga naar (3)

* (4). De gebruiker annuleert zijn wijzigen.

&nbsp;&nbsp;&nbsp;&nbsp; De gebruiker wordt teruggestuurd naar het (ongewijzigde) profiel van de gebruiker
