## Use case: Klant verwijderen

**Primaire actor:** Productie- of schadebeheerder

**Objectief:** De productie- of schadebeheerder moet een klant kunnen verwijderen

**Normale flow:**

1. De medewerker van Solvas zoekt de klant op

2. De medewerker selecteert de klant en geeft aan dat hij de klant wil verwijderen

3. Het systeem vraagt om bevestiging

4. De medewerker bevestigt de verwijdering

5. Het systeem verwijdert de klant


**Pre-condities:**

- De medewerker is ingelogd op de webapplicatie en heeft de nodige rechten
- De klant staat in het systeem
- De klant heeft geen lopende verzekeringen

**Post-condities:**

- De gegevens van de klant werden gewijzigd
- De gebruikers van de klant worden na een vaste periode ook gedeactiveerd, zodat ze geen toegang meer krijgen tot de webapplicatie.

**Alternatieve flow**

* (4). De medewerker annuleert de verwijdering

  a. De medewerker bedenkt zich en drukt op annuleren
