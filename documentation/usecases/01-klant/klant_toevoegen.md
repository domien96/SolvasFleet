## Use case: Klant toevoegen

**Primaire actor:** Productie- of schadebeheerder

**Objectief:** De productie- of schadebeheerder moet een klant kunnen toevoegen aan de databank

**Normale flow:**

1. De medewerker laat het systeem weten dat hij/zij een nieuwe klant wil toevoegen.

2. De medewerker voert de gegevens in

3. Het syteem vraagt om bevestiging

4. De medewerker bevestigt de toevoeging 

5. Het systeem slaat de klant op


**Pre-condities:**
- De medewerker is ingelogd op de webapplicatie en heeft de nodige rechten
- De klant is nog niet gekend bij het systeem
- De medewerker heeft de nodige informatie van de klant

**Post-condities:**
De klant werd toegevoegd aan de databank

**Alternatieve flow**
* (2). Gegevens staan in een fout formaat of zijn onvolledig

  a. Het systeem toont een foutmelding met mogelijkheid om gegevens aan te passen

  b. Ga naar 5

* (4). De medewerker annuleert de toevoeging

  a. Het systeem slaat de medewerker niet op en de toevoeg-operatie wordt afgesloten

**Exceptionele flow**

* (5). De klant kan niet opgeslaan worden om ongekende reden.

  a. Het systeem toont een foutmelding
