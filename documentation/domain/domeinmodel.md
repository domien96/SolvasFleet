
  Concept     | Definitie                                                   | Attributen
|:----------- |:-----------------------------------------------------------:| -----:|
Bedrijf       | Een klant, verzekeringsmaatschappij, of leasingmaatschappij | naam, BTW-nummer, adres
Gebruiker     | Persoon die het platform gebruikt                           |  voornaam, achternaam, email
Rol           | Functie die een gebruiker kan uitvoeren                     | naam
Permissie     | Rechten die verbonden zijn aan een rol                 | naam
Vloot         | Een groep van voertuigen                                            | facturatiefrequentie
Factuur       | Factuur die een klant moet betalen voor een vloot           | bedrag, periode
Subvloot      | Groep voertuigen van éénzelfde type binnen een vloot
Voertuig      | Voertuig dat verzekerd moet worden                          | waarde, chassisnummer, kenteken
VoertuigType  | Een bepaald type van voertuigen                                  | naam
Verzekering   | Verzekering dat een bedrijf aanbiedt                         | risiscopremie, toepasselijke vrijstelling
VerzekeringsType | Een type van autoverzekering (BA, rechtsbijstand, omnium, ...)    | forfaitair, taksen, commissie, naam
Overschreven kosten | Kosten voor verzekering die afwijken van de standaard | forfaitair, taksen, commissie, begin, eind
Vlootinschrijving | Bepaalt de periode waarin een voertuig deel uitmaakt van een vloot | begin, eind

### Bijkomende toelichtingen
Onder een gebruiker vallen zowel de klanten, medewerkers als administrator.
Het onderscheid tussen deze worden gerealiseerd a.d.h.v. het toekennen van permissies
