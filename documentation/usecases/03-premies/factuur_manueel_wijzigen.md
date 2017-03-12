## Use case: Manueel wijzigen van factuur van een klant

**Primaire actor:** Makelaar als gebruiker

**Objectief:** De gebruiker moet in staat zijn om facturen van zijn klanten aan te passen

**Normale flow:**


1. De gebruiker kiest een factuur uit een lijst van facturen van een bepaalde periode

2. Het systeem vraagt om een bevestiging dat de gebruiker de juiste factuur geselecteerd heeft

3. De gebruiker bevestigt de gekozen factuur

2. Het systeem haalt de factuur van de gebruiker op uit het archief en geeft deze weer aan de gebruiker

3. De gebruiker past de wijzigingen toe aan de factuur

4. Het systeem berekent een correctiefactuur voor de klant, rekening houdend met de reeds betaalde som

5. Het systeem stuurt de nieuwe factuur naar de klant en naar de gebruiker (de makelaar)


**Pre-condities:** De gebruiker heeft de vereiste rechten om facturen aan te passen en is ingelogd

**Post-condities:** De factuur van de klant is gewijzigd en de klant onvangt een nieuwe factuur 

**Alternatieve flow:**

* (1). De gebruiker selecteert geen factuur

  a. Het systeem geeft een errormelding voor onvolledige invoer weer

  b. ga naar stap (1)
  
* (3). De gebruiker selecteerde de verkeerde factuur

  a. ga naar stap (1)

* (4). De klant heeft teveel betaald bij een vorige factuur door de wijziging

  a. Op de correctiefactuur wordt het te ontvangen bedrag voor de klant meegedeeld
