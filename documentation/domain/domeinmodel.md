
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

Een gebruiker is via zijn rol verbonden met zijn rechten.
Klanten, verzekeraars en leasingmaatschappijen vallen samen onder het concept 'Bedrijf'. Zo kunnen we simpel voorzien dat een bedrijf meerdere types heeft. De type(s) van een bedrijf worden afgeleid uit zijn relaties. Zo is een bedrijf een verzekeraar als en slecht als dat bedrijf minstens één verzekering aanbiedt.

Voor elk concept bestaat er een log-variant, bijvoorbeeld "BedrijfsLog". Hier worden alle wijziging in opgeslagen, samen met de datum van de wijzigingen en een referentie naar de gebruiker die de wijzigingen uitvoerde. Voor de simpliciteit zijn deze echter niet opgenomen in de tabel en in de visuele voorstelling van de relaties.
