## Use case: Voertuig verwijderen van een subvloot
---

**Primaire actor:** Productie- of schadebeheerder

**Objectief:** De productie- of schadebeheerder moet een voertuig verwijderen van een subvloot van een klant

**Normale flow:**

1. De klant neemt contact op met solvas en vraagt de verwijdering van het voertuig aan

2. De medewerker van Solvas zoekt de klant op in de lijst van klanten

3. De medewerker selecteert de juiste klant

4. De medewerker zoekt het voertuig in de lijst van voertuigen

5. De medewerker selecteert het juiste voertuig

6. De medewerker geeft aan dat hij het voertuig wil verwijderen

7. Het systeem vraagt om bevestiging

8. De medewerker geeft bevestiging

**Pre-condities:**
- De medewerker is ingelogd op de webapplicatie en heeft de nodige rechten
- De klant heeft een profiel in het system
- Het voertuig staat in de subvloot

**Post-condities:**
- Het voertuig werd verwijderd uit de subvloot
- Het voertuig bestaat nog steeds in het systeem

**Alternatieve flow:**
* (7). De medewerker annuleert de verwijdering

&nbsp;&nbsp;&nbsp;&nbsp; a. De medewerker bedenkt zich en drukt op annuleren

* (6). Het voertuig heeft nog een active verzekering

&nbsp;&nbsp;&nbsp;&nbsp; a. Het systeem toont een foutmelding die het probleem beschrijft

&nbsp;&nbsp;&nbsp;&nbsp; b. De active verzekering word stop gezet

&nbsp;&nbsp;&nbsp;&nbsp; c. Ga naar 7
