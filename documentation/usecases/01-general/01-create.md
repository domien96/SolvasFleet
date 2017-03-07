## Aanmaken van een [Object]

**Primaire actor:** Gebruiker

**Objectief:** De gebruiker moet een [Object] kunnen toevoegen aan de databank

**Normale flow:**

1. De gebruiker geeft aan dat hij/zij een nieuwe [Object] wil toevoegen

2. De gebruiker krijgt een formulier voor een nieuw [Object] te zien

3. De gebruiker vult de gegevens in voor het [Object] dat hij wil toevoegen

4. De gebruiker submit het formulier

5. Het syteem vraagt de gebruiker om de gegevens te bevestigen

6. De gebruiker bevestigt de gegevens van het nieuwe [Object]

7. De gebruiker krijgt een bevestiging dat het [Object] werd toegevoegd

**Pre-condities:**

- De gebruiker beschikt over de nodige informatie over het [Object]

**Post-condities:**

- Het [Object] werd successvol toegevoegd aan de databank

**Alternatieve flow:**

5. De ingevulde gegevens zijn fout (niet uniek, fout formaat, ...) of onvolledig

  a. De gebruiker krijgt het formulier te zien met de ingevulde gegevens,
     waarbij aangeduid staat welke gegevens fout zijn

  b. Na het aanpassen van het formulier kan de gebruiker het formulier weer submitten (stap (4))

6. De gebruiker annuleert de toevoeging

  a. De gebruiker wordt doorgestuurd zonder zijn wijzigingen op te slaan

**Exceptionele flow**

7. Het [Object] wordt niet opgeslaan (wegens onvoorziene fout)

  a. De gebruiker krijgt een foutmelding te zien, met de boodschap opnieuw te proberen
