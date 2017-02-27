## Use case: Klant wijzigen

**Beschrijving**
De gegevens van een klant wijzigen. Dit gaat gepaard met een bezoek aan het kantoor. Dit gebeurt door de productie- of schadebeheerder.
**Actoren**
- De administrator, schade- of productiebeheerder
- De klant

**Preconditie**
- De schade- of productiebeheerder heeft zich reeds ingelogd
- De klant werd al reeds in de databank geregistreerd

**Normale flow**
1. De klant meldt zich aan bij het kantoor van solvas. Hier geeft hij wijzigingen door van de gegevens van het bedrijf.
2. De medewerker van solvas zoekt het bedrijf van de klant op met behulp van het webplatform.
3. De medewerker geeft aan dat hij gegevens wilt veranderen.
4. De medewerker vult de nieuwe gegevens correct aan op het webplatform
5. De medewerker bevestigt de aanpassingen en zorgt ervoor dat deze dan ook effectief in de databank aangepast worden

**Postconditie**
- De historiek moet nog steeds kloppen met deze nieuwe wijzigigen, m.a.w acties uit het verleden mogen niet gelinkt worden aan deze wijzigingen.
