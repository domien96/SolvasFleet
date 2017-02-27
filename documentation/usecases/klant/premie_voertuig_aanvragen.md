## Use case: Opvragen premie van een voertuig door klant

**Primaire actor:** De klant (een bedrijf)

**Objectief:** De klant moet in staat zijn de premie van een voertuig op te vragen

**Normale flow:**


1. De gebruiker selecteert een vloot

2. Het systeem toont informatie over de vloot

3. De gebruiker selecteert een subvloot van de vloot

4. Het systeem toont informatie over de subvloot

5. De gebruiker selecteert een voertuig van de subvloot

6. Het systeem toont informatie over dit voertuig

7. De gebruiker vraagt de premie op van dit voertuig

8. Het systeem geeft de premie weer van het voertuig


**Pre-condities:** De klant is ingelogd op de webapplicatie

**Post-condities:** De klant kent de premie van een bepaald voertuig

**Alternatieve flow:**
* (7). Het voertuig heeft geen premie waardoor de deze niet weergegeven kan worden

&nbsp;&nbsp;&nbsp;&nbsp; a. Het systeem laat weten dat het voertuig nog geen premie heeft


**Exceptionele flow:**
* (1). De gebruiker heeft geen vloten

&nbsp;&nbsp;&nbsp;&nbsp; a. Het systeem geeft een melding dat er eerst vloten toegekend moeten worden

* (3). De vloot heeft geen subvloten

&nbsp;&nbsp;&nbsp;&nbsp; a. Het systeem geeft een melding dat er eerst subvloten toegekend moeten worden aan de vloot

* (5). De subvloot heeft geen voertuigen

&nbsp;&nbsp;&nbsp;&nbsp; a. Het systeem geeft een melding dat er eerst voertuigen toegevoegd moeten worden aan de subvloot
