# USE CASE: Gebruiker wijzigen

### Beschrijving
De gegevens van een gebruiker wijzigen. Deze gegevens zijn de persoonlijke gegevens van de gebruiker. Dit kan hij/zij zelf wijzigen via de webapplicatie, tenzij het gaat om specifieke schrijf- of leesrechten.
### Actoren


- De gebruiker
-  (optioneel) Een medewerker van solvas (administrator, schade- of productiebeheerder)
### Preconditie
- De persoon die de wijzigen uitvoert heeft zich reeds ingelogd op de webapplicatie
- De gebruiker is nog actief

### Normale flow (de gebruiker voert de wijzigingen zelf uit)
1. De gebruiker gaat naar zijn profiel op de webapplicatie
3. De gebruiker geeft aan dat hij zijn gegevens wil wijzigen
4. De gebruiker vult de nieuwe gegevens correct aan op het webplatform
5. De gebruiker bevestigt de aanpassingen en zorgt ervoor dat deze dan ook effectief in de databank aangepast worden

### Alternatieve flow (de gebruiker wilt andere schrijf- of leesrechten)
1. De gebruiker neemt contact op met Solvas en geeft de nodige aanpassingen door
2. De medewerker van Solvas zoekt de gebruiker op in de lijst van gebruikers
3. De medewerker past de schrijf- of leesrechten correct aan

### Postconditie
- De historiek moet nog steeds kloppen met deze nieuwe wijzigigen, m.a.w acties uit het verleden mogen niet gelinkt worden aan deze wijzigingen.
