## Use case: Voertuig wijzigen
---

**Primaire actor:** Productie- of schadebeheerder

**Objectief:** De productie- of schadebeheerder moet een voertuig verwijderen van een subvloot van een klant

**Normale flow:**
1. De klant neemt contact op met solvas en verwijdering van het voertuig door

2. De medewerker van Solvas zoekt de klant op in de lijst van klanten

3. De medewerker selecteert de juiste klant

4. De medewerker zoekt het voertuig in de lijst van voertuigen

5. De medewerker selecteert het juiste voertuig

6. De medewerker geeft aan dat hij het voertuig wil wijzigen

7. De medewerker geeft de wijzigingen door

8. Het systeem vraagt om bevestiging

9. De medewerker geeft bevestiging

**Pre-condities:**
- De medewerker is ingelogd op de webapplicatie en heeft de nodige rechten
- De klant staat in de databank
- Het voertuig staat in de subvloot

**Post-condities:**
- Het voertuig werd aangepast

**Alternatieve flow:**
* (7). Gegevens staan in een fout formaat
&nbsp;&nbsp;&nbsp;&nbsp; a. Het systeem toont een foutmelding met mogelijkheid om gegevens aan te passen

&nbsp;&nbsp;&nbsp;&nbsp; b. Ga naar 7

* (9). De medewerker annuleert de wijziging

&nbsp;&nbsp;&nbsp;&nbsp; a. De medewerker bedenkt zich en drukt op annuleren
