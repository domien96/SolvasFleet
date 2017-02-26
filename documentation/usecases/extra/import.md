## Use case: Importeren van Data
---

**Primaire actor:** Gebruiker

**Objectief:** De gebruiker moet data via een csv (of excel) data in bulk kunnen importeren in het database systeem.

**Normale flow:**

1. De gebruiker geeft aan dat hij een import wil starten

2. De gebruiker kiest zijn bestand om te importeren

3. Het systeem toont welke wijzigingen aangebracht zullen worden aan het databasesysteem

4. De gebruiker bevestigt de wijzigingen

5. De gebruiker wordt doorgestuurd naar een bevestigingspagina, en zal later een notificatie krijgen dat de wijzigingen successvol zijn doorgevoerd


**Pre-condities:** De gebruiker is ingelogd op de webapplicatie en heeft de nodige rechten

**Post-condities:** De gebruiker heeft zijn gegevens geimporteerd

**Alternatieve flow:**
* (3). Het bestand bevat foute gegevens
 
&nbsp;&nbsp;&nbsp;&nbsp; a. Het systeem toont de foute gegevens, met een melding

&nbsp;&nbsp;&nbsp;&nbsp; b. Ga naar (2)

* (4). De gebruiker aanvaardt de wijzigingen niet

&nbsp;&nbsp;&nbsp;&nbsp; a. Ga naar (1)
