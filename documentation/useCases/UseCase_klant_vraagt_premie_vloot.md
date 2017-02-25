
## Use case: opvragen premie van een vloot door klant
---

**Primaire actor:** De klant (een bedrijf)

**Objectief:** De klant moet in staat zijn de premie van een vloot op te vragen

**Normale flow:**


1. De gebruiker selecteert een vloot

2. Het systeem toont informatie over de vloot

3. De gebruiker vraagt de premie op van de vloot

4. Het systeem geeft de premie weer van de vloot


**Pre-condities:** De klant is ingelogd op de webapplicatie

**Post-condities:** De klant kent de premie van een bepaalde vloot

**Alternatieve flow:**
* (3). Niet alle voertuigen hebben een premie waardoor de vloot-premie niet weergegeven kan worden

&nbsp;&nbsp;&nbsp;&nbsp; a. Het systeem laat weten welke voertuigen nog geen premie hebben en geeft een tussenpremie weer

* (3). Er zijn nog geen voertuigen met een premie waardoor de vloot-premie niet weergegeven kan worden

&nbsp;&nbsp;&nbsp;&nbsp; a. Het systeem laat weten dat er alle voertuigen nog geen premie hebben


**Exceptionele flow:**
* (1). De gebruiker heeft geen vloten

&nbsp;&nbsp;&nbsp;&nbsp; a. Het systeem geeft een melding dat er eerst vloten toegekend moeten worden
