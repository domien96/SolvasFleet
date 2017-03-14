#Request for comments
De commentaren hier zal Niko meenemen naar de meetings met de andere API-beheerders.

## Template voor een rfc
**Aanvrager:** <naam>

**Datum:** <huidige datum>

**Commentaar:** <beschrijving>

**Doorgegeven:** PENDING

**Respons:** ...


## RFC's
**Aanvrager:** Domien

**Datum:** 5 maart

**Commentaar:** in rollen user-> users

**Doorgegeven:** OK

**Respons:** Nee. Het huidige model noemt wel Rol, 
maar stelt conceptueel meer een soort permissie voor. Een gebruiker 
heeft permissie om iets te doen gedurende een tijd bij een bedrijf.
Wat de permissies precies inhouden (lezen, schrijven, enz.) wordt niet 
gedefinieerd door de API, en is iets dat op de backend moet gebeuren. 
Ook zouden meerdere gebruikers/bedrijven per rol het archiveren en 
bijhouden van de geschiedenis (toch op API-vlak) moeilijker maken.
