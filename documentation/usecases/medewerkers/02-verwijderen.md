## Use case: Verwijderen van een nieuwe medewerker
---

**Primaire actor:** Gebruiker

**Objectief:** De gebruiker moet een gebruiker kunnen verwijderen uit het systeem.

**Normale flow:**

1. De gebruiker geeft aan dat hij een medewerker wil verwijderen

2. De gebruiker selecteert de medewerker die verwijdert moet worden

3. De gebruiker bevestigt zijn keuze

4. De gebruiker wordt doorgestuurd naar een bevestigingspagina


**Pre-condities:** De gebruiker is ingelogd op de webapplicatie en heeft de nodige rechten

**Post-condities:** De gekozen gebruiker is verwijderd

**Alternatieve flow:**
* (2). De geselecteerde gebruiker kan niet verwijderd worden (admin, zichzelf, ...)
 
&nbsp;&nbsp;&nbsp;&nbsp; a. Het systeem toont een foutmelding, met de mogelijkheid om een nieuwe gebruiker te kiezen

&nbsp;&nbsp;&nbsp;&nbsp; b. Ga naar (2)
