## Use case: Voertuig aan een subvloot toevoegen

**Primaire actor:** Productie- of schadebeheerder

**Objectief:** De productie- of schadebeheerder moet een voertuig toevoegen aan een subvloot van een klant

**Normale flow:**

1. De klant neemt contact op met solvas en geeft de nieuwe voertuigen door

2. De medewerker van Solvas zoekt de klant op in de lijst van klanten

3. De medewerker selecteert de juiste klant

4. De medewerker zoekt de subvloot op in de lijst van subvloten

5. De medewerker selecteert de juiste subvloot

6. De medewerker geeft aan dat hij een voertuig wil toevoegen

7. De medewerker geeft de gegevens van het voertuig door (uniek chassisnummer,...)

8. Het syteem vraagt om bevestiging

9. De medewerker bevestigt de toevoeging 

10. Het systeem slaat het voertuig op in de databank


**Pre-condities:**
- De medewerker is ingelogd op de webapplicatie en heeft de nodige rechten
- De klant heeft een profiel in het systeem

**Post-condities:**
Het voertuig werd toegevoegd

**Alternatieve flow**

* (4). De klant heeft nog geen vloten

  a. Het systeem toont een foutmelding

  b. Er wordt een nieuwe vloot gemaakt na bespreking met de klant

  c. Ga naar 4

* (4). De klant heeft geen subvloot voor het type voertuig

  a. Het systeem toont een foutmelding

  b. Er wordt een nieuwe subvloot aangemaakt na bespreking met de klant

  c. Ga naar 4

* (7). Het chassinummer werd al gebruikt bij een andere subvloot

  a. Het systeem toont een foutmelding

  b. De medewerker neemt eventueel contact op met solvas

* (7). Gegevens staan in een fout formaat

  a. Het systeem toont een foutmelding met mogelijkheid om gegevens aan te passen

  b. Ga naar 7

* (8). De medewerker annuleert de verwijdering

  a. De medewerker bedenkt zich en drukt op annuleren
