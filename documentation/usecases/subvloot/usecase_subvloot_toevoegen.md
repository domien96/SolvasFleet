## Use case: Subvloot van een klant toevoegen
---

**Primaire actor:** Productie- of schadebeheerder

**Objectief:** De productie- of schadebeheerder moet een subvloot van een klant toevoegen aan de databank

**Normale flow:**

1. De medewerker van Solvas zoekt de klant op in de lijst van klanten

2. De medewerker selecteert de juiste klant

3. De medewerker geeft aan dat hij een subvloot wil toevoegen aan de klant

4. De medewerker geeft het voertuigtype door, horend bij de subvloot

5. De medewerker bevestigt de toevoeging en slaat deze op in de databank


**Pre-condities:**
- De gebruiker is ingelogd op de webapplicatie en heeft de nodige rechten
- De klant staat in de databank

**Post-condities:**
De subvloot is klaar om er voertuigen aan toe te voegen.

**Alternatieve flow:**
* (5). De klant heeft al reeds een vloot van dat voertuigtype

&nbsp;&nbsp;&nbsp;&nbsp; a. Het systeem toont een foutmelding, met de mogelijkheid om het voertuigtype aan te passen

&nbsp;&nbsp;&nbsp;&nbsp; b. Ga naar (5)
