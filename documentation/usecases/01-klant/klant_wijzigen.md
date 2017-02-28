## Use case: Klant wijzigen
---

**Primaire actor:** Productie- of schadebeheerder

**Objectief:** De productie- of schadebeheerder moet de gegevens van een klant kunnen wijzigen

**Normale flow:**

1. De medewerker van Solvas zoekt de klant op

2. De medewerker selecteert de klant en geeft aan dat hij de gegevens wilt veranderen

3. De medewerker vult de nieuwe gegevens in

4. Het systeem vraagt om bevestiging

5. De medewerker bevestigt de wijzigingen


**Pre-condities:**
- De medewerker is ingelogd op de webapplicatie en heeft de nodige rechten
- De klant staat in het systeem

**Post-condities:**
- De gegevens van de klant werden gewijzigd
- De historiek moet nog steeds kloppen met deze nieuwe wijzigingen.

**Alternatieve flow**
* (4). Gegevens staan in een fout formaat
&nbsp;&nbsp;&nbsp;&nbsp; a. Het systeem toont een foutmelding met mogelijkheid om gegevens aan te passen

&nbsp;&nbsp;&nbsp;&nbsp; b. Ga naar 4

* (5). De medewerker annuleert de wijzigingen

&nbsp;&nbsp;&nbsp;&nbsp; a. De medewerker bedenkt zich en drukt op annuleren
