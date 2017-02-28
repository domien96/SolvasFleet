## Use case: Voertuigen van een subvloot van een klant oplijsten

**Primaire actor:** Een gebruiker

**Objectief:** De gebruiker wil de voertuigen van een subvloot van een klant oplijsten

**Normale flow:**

1. De gebruiker gaat naar de pagina van de klant

2. De gebruiker kiest de juiste vloot en subvloot

3. De gebruiker geeft eventuele filters en/of sorteervormen mee

4. De gebruiker bevestigt de zoekopdracht

5. Het systeem geeft de gefilterde  lijst van voertuigen weer

**Pre-condities:**
- De gebruiker is ingelogd op de webapplicatie en heeft de nodige rechten
- De subvloot bestaat
- De klant heeft een profiel in het systeem

**Post-condities:**
/

**Alternatieve flow:**

* (4). De gebruiker bevestigt de zoekopdracht niet

  a. Ga naar 1

* (3). De gebruiker geeft geen filters of sorteervormen mee en bevestigt de zoekopdracht

  a. Het systeem geeft de lijst weer van alle voertuigen van de klant