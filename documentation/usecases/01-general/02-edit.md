## Wijzigen van een [Object]

**Primaire actor:** Gebruiker

**Objectief:** De gebruiker moet de gegevens van een [Object] kunnen wijzigen

**Normale flow:**

1. De gebruiker gaat naar de infopagina van het [Object]

2. De gebruiker geeft aan het [Object] te willen wijzigen

3. De gebruiker krijgt een formulier te zien voor het bestaande [Object], waarbij de gegevens al zijn ingevuld

4. De gebruiker wijzigt de gegevens van het [Object]

5. De gebruiker submit het formulier

6. Het syteem vraagt de gebruiker om de wijzigingen te bevestigen

7. De gebruiker bevestigt de wijzigingen van het [Object]

7. De gebruiker krijgt een bevestiging dat het [Object] gewijzigd werd

**Post-condities:**

- Het [Object] werd gewijzigd

**Alternatieve flow**

6. De ingevulde gegevens zijn fout (niet uniek, fout formaat, ...) of onvolledig

  a. De gebruiker krijgt het formulier te zien met de ingevulde gegevens,
     waarbij aangeduid staat welke gegevens fout zijn

  b. Na het aanpassen van het formulier kan de gebruiker het formulier weer submitten (stap (4))

7. De gebruiker annuleert de wijzigingen

  a. De gebruiker wordt doorgestuurd zonder zijn wijzigingen op te slaan

**Exceptionele flow**

7. Het [Object] wordt niet gewijzigd (wegens onvoorziene fout)

  a. De gebruiker krijgt een foutmelding te zien, met de boodschap opnieuw te proberen
