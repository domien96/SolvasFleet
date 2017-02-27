## Use case: afsluiten van een verzekering

**Primaire actor:** De beheerder

**Objectief:** Er wordt een verzekering afgesloten tussen de klant en een verzekeringsmaatschappij over een voertuig.


**Normale flow:**

1. De beheerder selecteert het account van de klant.

2. De beheerder selecteert het voertuig waarvoor de klant een verzekering wilt hebben door de juiste vloot, subvloot te kiezen.

3. De klant geeft aan welk type verzekering hij wilt voor het voertuig.

4. De klant kan uit een lijst kiezen welke verzekering hij/zij wilt.

5. De beheerder selecteert deze verzekering en slaat de upgedate gegevens op

**Pre-condities:** De klant heeft een profiel bij solvas en heeft minstens 1 voertuig in zijn vloot alsook is de beheerder ingelogd in het systeem.

**Post-condities:** Het voertuig heeft een nieuwe verzekering

**Alternatieve flow:**
* (1). De beheerder heeft de rechten niet om het account van de klant te beheren
 a. De beheerder vraagt deze rechten aan aan de administrator

 b. Ga naar (2)

* (3). Het gekozen voertuig heeft al een verzekering voor op het voertuig voor het gekozen type

 a. Het systeem geeft een melding van het probleem

 b. Huidige verzekering stopzetten
 
 c. Ga naar (4)
