## Use case: Automatisch wijzigen van factuur van een klant

**Primaire actor:** De klant

**Objectief:** Het systeem moet automatisch facturen updaten bij een aanpassing door de klant

**Normale flow:**


1. De klant geeft een wijziging door aan het systeem (toevoegen/verwijderen voertuig)

2. Het systeem berekent een nieuwe correctiefactuur rekening houdend met de gedane wijzigingen

3. Het systeem stuurt de nieuwe factuur naar de klant en naar de makelaar van de klant


**Pre-condities:** De klant heeft de vereiste rechten om voertuigen te wijzigen en is ingelogd

**Post-condities:** De factuur van de klant is gewijzigd en de klant onvangt een correctiefactuur 

**Alternatieve flow:**

* (3). De klant heeft teveel betaald bij een vorige factuur door de wijziging

  a. Op de correctiefactuur wordt het te ontvangen bedrag voor de klant meegedeeld
