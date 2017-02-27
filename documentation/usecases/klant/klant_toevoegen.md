## Use case: Klant toevoegen
---

**Primaire actor:** Productie- of schadebeheerder

**Objectief:** De productie- of schadebeheerder moet een klant kunnen toevoegen aan de databank

**Normale flow:**
1. Het bedrijf komt bij Solvas met de intentie om klant te worden

2. De klant geeft de nodige informatie door aan de medewerker van Solvas

3. De medewerker gaat op de webapplicatie naar klant toevoegen

4. De medewerker voert de gegevens in

5. Het syteem vraagt om bevestiging

6. De medewerker bevestigt de toevoeging en slaat de klant op in de databank


**Pre-condities:**
- De medewerker is ingelogd op de webapplicatie en heeft de nodige rechten
- De klant staat nog niet in de databank

**Post-condities:**
De klant werd toegevoegd aan de databank

**Alternatieve flow**
* (5). Gegevens staan in een fout formaat
&nbsp;&nbsp;&nbsp;&nbsp; a. Het systeem toont een foutmelding met mogelijkheid om gegevens aan te passen

&nbsp;&nbsp;&nbsp;&nbsp; b. Ga naar 5

* (8). De medewerker annuleert de toevoeging

&nbsp;&nbsp;&nbsp;&nbsp; a. De medewerker bedenkt zich en drukt op annuleren
