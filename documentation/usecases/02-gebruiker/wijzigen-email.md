Use case: Een gebruiker wijzigt zijn eigen e-mail
-------------------------------------------------

**Primaire actor:** Gebruiker

**Objectief:** De gebruiker moet zijn eigen e-mail kunnen wijzigen.

**Normale flow**

1.  De gebruiker gaat naar zijn eigen profielpagina.

2.  De gebruiker geeft aan zijn inloggegevens te willen wijzigen.

3.  De gebruiker vult zijn nieuwe e-mail in.

4.  De gebruiker bevestigt de wijzigingen.

5.  Het systeem stuurt de gebruiker een e-mail ter bevestiging van de
    wijzigingen naar het nieuwe e-mailadres.

6.  Het systeem stuurt een e-mail naar het oude e-mailadres ter informatie.

7.  De gebruiker opent de e-mail en klikt op bevestigen.

8.  De gebruiker ziet een bevestiging van de wijzigingen.

**Pre-condities**

De gebruiker is ingelogd op de webapplicatie.

**Post-condities**

De gebruiker zijn gegevens zijn aangepast.

**Alternatieve flow**

* (4). De gebruiker annuleert de wijzigingen.

  a. De gebruiker wordt teruggestuurd naar zijn profielpagina.

* (5). De e-mail kon niet verstuurd worden omdat het opgeven e-mailadres fout is.

  a. De wijzigingen worden verworpen; er zal niets veranderen.

* (7). De gebruiker negeert de bevestigingse-mail of klikt niet op de
    bevestigingslink binnen een bepaalde periode (zoals 24 uur, 1 week, ...).

  a. De wijzigingen worden verworpen; er zal niets veranderen.
