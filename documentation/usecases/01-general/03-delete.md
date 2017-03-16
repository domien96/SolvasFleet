## Verwijderen/Archiveren van een [Object]

**Primaire actor:** Gebruiker

**Objectief:** De gebruiker moet een [Object] kunnen verwijderen/archiveren

**Normale flow:**

1. De gebruiker gaat naar de infopagina van het [Object]

2. De gebruiker geeft aan het [Object] te willen verwijderen/archiveren

3. Het systeem vraagt om bevestiging

4. De medewerker bevestigt de verwijdering

5. De gebruiker krijgt een bevestiging dat het [Object] verwijderd werd

**Post-condities:**

- Het [Object] werd verwijderd

**Alternatieve flow**

2. Het [Object] heeft nog relaties openstaan, bv een klant die nog verzekeringen heeft

  a. De gebruiker wordt verwittigd dat deze relaties ook verwijderd/geärchiveerd zullen worden

  b. Ga verder met stap (3)

4. De gebruiker annuleert de verwijdering

  a. De gebruiker wordt doorgestuurd
