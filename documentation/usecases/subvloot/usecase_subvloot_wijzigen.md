## Use case: Subvloot van een klant wijzigen
---

**Primaire actor:** Productie- of schadebeheerder

**Objectief:** De productie- of schadebeheerder moet de subvloot van een klant wijzigen. Het wijzigen gaat dus over de verzekering bij de subvloot.

**Normale flow:**  
(0. De klant contacteert Solvas om de verzekering van een subvloot te wijzigen)

1. De medewerker van Solvas zoekt de klant op in de lijst van klanten

2. De medewerker selecteert de juiste klant

3. De medewerker selecteert de subvloot

4. De medewerker verandert de verzekering, met begin- en einddatums

5. Het systeem vraagt om bevestiging

5. De medewerker bevestigt de wijziging en slaat deze op in de databank


**Pre-condities:**
- De gebruiker is ingelogd op de webapplicatie en heeft de nodige rechten
- De subvloot bestaat
- De klant heeft een profiel

**Post-condities:**
- Zowel de klant als de verzekeringsmaatschappij kunnen deze verandering bekijken

**Alternatieve flow:**
* (4). De begin- en/of einddatum zijn niet correct

&nbsp;&nbsp;&nbsp;&nbsp; a. Het systeem toont een foutmelding, met de mogelijkheid om de data aan te passen

&nbsp;&nbsp;&nbsp;&nbsp; b. Ga naar (5)

* (6). De medewerker wilt de aanpassing annuleren  
&nbsp;&nbsp;&nbsp;&nbsp; a. De medewerker drukt op annuleren  
&nbsp;&nbsp;&nbsp;&nbsp; b. De medewerker krijgt de subvloot in normale staat te zien  
