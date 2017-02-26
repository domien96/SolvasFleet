## Use case: Aanmaken van een nieuwe medewerker
---

**Primaire actor:** Gebruiker

**Objectief:** De gebruiker moet een nieuwe gebruiker kunnen toevoegen aan het systeem.

**Normale flow:**

1. De gebruiker geeft aan dat hij een nieuwe medewerker wil toevoegen

2. De gebruiker vult enkele basisgegevens voor deze medewerker in

3. De gebruiker selecteert de permissies voor de nieuwe gebruiker (indien hij dit kan doen)

4. De gebruiker bevestigt de gegevens

5. De gebruiker wordt doorgestuurd naar een bevestigingspagina

6. De nieuwe gebruiker krijgt een mail toegestuurd met een inloglink


**Pre-condities:** De gebruiker is ingelogd op de webapplicatie en heeft de nodige rechten

**Post-condities:** Er is een nieuwe gebruiker aangemaakt

**Alternatieve flow:**
* (3). De gegevens zijn fout
 
&nbsp;&nbsp;&nbsp;&nbsp; a. Het systeem toont de foute gegevens, met een melding

&nbsp;&nbsp;&nbsp;&nbsp; b. Ga naar (2)

* (4). De gebruiker probeert permissies toe te voegen waarvoor hij zelf geen permissies heeft

&nbsp;&nbsp;&nbsp;&nbsp; a. Het systeem toont de foute gegevens, met een melding

&nbsp;&nbsp;&nbsp;&nbsp; b. Ga naar (3)
