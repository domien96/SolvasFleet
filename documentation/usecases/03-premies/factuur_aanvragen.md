## Use case: Opvragen van factuur van de polis door klant

**Primaire actor:** De klant (een bedrijf)

**Objectief:** De klant moet in staat zijn om elke factuur van zijn polis te raadplegen

**Normale flow:**


1. De gebruiker selecteert de datum van de factuur die hij wil raadplegen

2. Het systeem haalt de factuur van de gebruiker op de gekozen datum uit het archief en geeft deze weer aan de gebruiker


**Pre-condities:** De klant is ingelogd op de webapplicatie

**Post-condities:** De klant heeft zijn factuur ontvangen 

**Alternatieve flow:**

* (1). De gebruiker selecteert geen datum

  a. Het systeem vult de huidige datum in

  b. Het systeem berekent de nieuwe factuur voor de huidige periode en geeft deze weer

* (1). De gebruiker selecteert de huidige datum

  a. Het systeem berekent de nieuwe factuur voor de huidige periode en geeft deze weer
