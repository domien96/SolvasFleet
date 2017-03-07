## Wijzigen van wachtwoord van niet-ingelogde gebruiker

**Primaire actor:** Gebruiker

**Objectief:** De gebruiker moet zijn passwoord kunnen aanpassen zonder in te loggen indien hij vergeten is

**Normale flow:**

1. De gebruiker slaagt er niet in om in te loggen

2. De gebruiker geeft aan dat hij zijn passwoord vergeten is

3. De gebruiker vult zijn emailadres in

4. De gebruiker bevestigt zijn emailadres

5. De gebruiker krijgt de melding dat hij een email met een passwoord reset link heeft gekregen

6. De gebruiker volgt deze link

7. De gebruiker kan een nieuw passwoord kiezen door het tweemaal in te vullen

8. De gebruiker bevestigt zijn nieuwe passwoord

9. De gebruiker krijgt een bevestigingsemail en wordt doorgestuurd naar de inlogpagina

**Pre-condities:** 

- De gebruiker is niet ingelogd op de webapplicatie

**Post-condities:** 

- De gebruiker heeft een nieuw passwoord ingesteld

**Alternatieve flow:**

4. Het emailadres is niet gekend in de databank

  a. Stap (5) wordt nog steeds uitgevoerd

  b. De gebruiker zal niet verder kunnen aangezien hij geen email heeft ontvangen

8. De wachtwoorden komen niet overeen

  a. De gebruiker krijgt een foutboodschap te zien

  b. Ga naar stap (7)
