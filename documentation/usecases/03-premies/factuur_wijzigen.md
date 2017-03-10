## Use case: Wijzigen van factuur van een klant

**Primaire actor:** Makelaar als gebruiker

**Objectief:** De gebruiker moet in staat zijn om facturen van zijn klanten aan te passen

**Normale flow:**


1. De gebruiker selecteert de datum van de factuur die hij wil wijzigen

2. Het systeem haalt de factuur van de gebruiker op de gekozen datum uit het archief en geeft deze weer aan de gebruiker

3. De gebruiker past de wijzigingen toe aan de factuur

4. Het systeem berekent een nieuwe factuur voor de klant, rekening houdend met de reeds betaalde som

5. Het systeem stuurt de nieuwe factuur naar de klant


**Pre-condities:** De gebruiker heeft de vereiste rechten om facturen aan te passen

**Post-condities:** De factuur van de klant is gewijzigd en de klant onvangt een nieuwe factuur 

**Alternatieve flow:**

* (1). De gebruiker selecteert geen datum

  a. Het systeem vult de huidige datum in

  b. ga naar stap (2)

* (4). De klant heeft teveel betaald bij een vorige factuur door de wijziging

  a. De aangemaakte factuur wordt getoond aan de makelaar in plaats van verzonden naar de klant. De makelaar ontvangt eveneens een mail van deze factuur.
