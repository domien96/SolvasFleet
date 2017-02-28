## Use case: Simuleren van de premiewijzigingen bij het veranderen van verzekering en/of toevoegen van voertuigen

**Primaire actor:** De klant (een bedrijf)

**Objectief:** De klant moet in staat zijn te weten wat er wijzigt bij het overschakelen naar een andere verzekering of bij het toevoegen van een voertuig

**Normale flow:**


1. De gebruiker geeft aan dat hij een simulatie wil starten

2. Het systeem vraagt om de huidige verzekering te wijzigen in een andere verzekering

3. De gebruiker geeft een andere verzekering op

4. Het systeem geeft de nieuwe premie weer, berekend met de gekozen verzekering in de simulatie en vraagt de gebruiker een voertuig toe te voegen, te verwijderen, te wijzigen of een andere verzekering te kiezen

5. De gebruiker voegt een voertuig toe

6. Het systeem geeft de nieuwe premie weer, berekend met de gekozen verzekering en het gewijzigde aantal en type voertuigen

**Pre-condities:** De klant is ingelogd op de webapplicatie

**Post-condities:** De klant heeft een simulatie van mogelijke premie wijzigingen gemaakt

**Alternatieve flow:**

* (3). De gebruiker geeft geen andere verzekering op
 
  a. Het systeem geeft de huidige premie weer en vraagt de gebruiker een voertuig toe te voegen of te verwijderen, of een andere verzekering te kiezen

  b. Ga naar 5

* (5). De gebruiker verwijdert een voertuig

  a. Ga naar 6

* (5). De gebruiker wijzigt het type van een voertuig

  a. Ga naar 6

* (5). De gebruiker wijzigt de verzekering opnieuw
  
  a. Ga naar 4
