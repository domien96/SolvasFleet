# Domeinmodel
## Tabellen

Concept		| Definitie					| Attributen  	
------		| --------					| ---------- 	
Vloot		| Groep subvloten met bijbehorende polis 	| polis
Subvloot	| Groep voertuigen van hetzelfde voertuigtype	| -
Voertuig 	| Voertuig dat verzekerd dient te worden	| chassisnummer
Voertuigtype	| Type voertuig (brommer, vrachtwagen)		| type
Gebruiker	| Gebruiker van het platform 			| -
Permissie	| Gebruikersrechten				| -
Klant		| Klant						| naam
|		| 						| BTW-nummer
Verzekeringsbedrijf	| Verzekeringsmaatschappij		| naam
|		| 						| BTW-nummer
|		| 						| rekeningnummer
Leasingmaatschappij	| Leasingmaatschappij/bank		| naam
|		| 						| BTW-nummer
|		| 						| rekeningnummer
Verzekeringstype| Verzekeringstype (BA, rechtsbijstand, omnium)	| naam
Verzekering	| Verzekeringstype die een verzekeringsmaatschappij	| forfaitair	
|		| aanbied voor een bepaald voertuigtype.		| percentueel
|		| 							| task
|		| 							| makelaarsloon
Factuur		| Te betalen factuur of correctiefactuur	| prijs
|		|						| datum
|		|						| betaald
Facturatiefrequentie	| Frequentie van factureren		| frequentie
Genomen verzekering	| samenvoegingstabel tussen verzekering | start
|			| en subvloot				| einde
Registratie	| Registratie van voertuig bij subvloot		| kenteken
