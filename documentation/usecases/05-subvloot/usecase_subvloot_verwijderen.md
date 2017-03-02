## Use case: Subvloot van een klant wijzigen

**Primaire actor:** Productie- of schadebeheerder

**Objectief:** De productie- of schadebeheerder moet de subvloot van een klant verwijderen. Hierdoor is deze alleen zichtbaar voor de klant als hij de historiek nagaat.

**Normale flow:**

1. De medewerker van Solvas zoekt de klant op in de lijst van klanten

2. De medewerker selecteert de juiste klant

3. De medewerker selecteert de subvloot

4. De medewerker geeft aan dat hij de subvloot wil verwijderen

5. Het systeem vraagt om bevestiging

6. De medewerker bevestigt de verwijdering

7. Het systeem verwijdert de subvloot

**Pre-condities:**
- De gebruiker is ingelogd op de webapplicatie en heeft de nodige rechten
- De subvloot bestaat

**Post-condities:**
- De klant kan de subvloot enkel maar bekijken als hij de historiek nagaat

**Alternatieve flow:**

* (5) De medewerker annuleert de verwijdering

  a. Het systeem sluit het verwijderproces af, de subvloot blijft bestaan
