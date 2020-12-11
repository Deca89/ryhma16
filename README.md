# Ryhma16
![Github Actions](https://github.com/vuorenkoski/ryhma16/workflows/Automaattitesti/badge.svg)
[![codecov](https://codecov.io/gh/vuorenkoski/ryhma16/branch/main/graph/badge.svg?token=N8XK23PROJ)](https://codecov.io/gh/vuorenkoski/ryhma16)

## Dokumentit

### Burndown-kirjanpito
Lisää tehdyt taskit Seuranta-välilehdelle.

[Burndown-kirjanpito](https://docs.google.com/spreadsheets/d/1F7UD5SX0QfkrZj2iQEsYmjWsCflqhhSRyLpn6-PuG4c/edit#gid=0)

### Tuntikirjanpito
[Ajankäyttö](https://github.com/vuorenkoski/ryhma16/blob/main/Dokumentaatio/Ajankaytto.md)

### Backlogit

- [Product backlog](https://github.com/vuorenkoski/ryhma16/projects/1)
- [Sprintti 1](https://github.com/vuorenkoski/ryhma16/projects/2)
- [Sprintti 2](https://github.com/vuorenkoski/ryhma16/projects/3)
- [Sprintti 3](https://github.com/vuorenkoski/ryhma16/projects/4)

### Loppuraportti

[Loppuraportti](https://docs.google.com/document/d/1Fvppq7Zg8Ci1p25ikpNxgy9Z3YqIOJCPfncui3TO4Pc)

### Ohjelman asennusohje

1. Lataa ohjelma Githubista (komento *git clone*).
2. Avaa ohjelmakansio. Mene kansioon *Lukuvinkisto*.
3. Suorita ohjelma komentoriviltä komennolla *gradle run*.
4. Ohjelma avaa uudessa selainikkunassa Lukuvinkistön web-käyttöliittymän.
5. Kun haluat lopettaa ohjelman, klikkaa web-käyttöliittymässä linkkiä *Lopeta ohjelma*.

Suorittamalla ohjelma komennolla *gradle run \-\-args="demotietokanta"*, ohjelma aluksi poistaa mahdollisen aiemman tietokannan ja korvaa sen demotietokannalla.

### Käyttöohje

Ohjelma käynnistetään komentoriviltä. Seuraavat ohjeet koskevat web-käyttöliittymää.

#### Lisää vinkki

*Lisää vinkki* -linkkien avulla lukuvinkistöön voidaan lisätä uusia vinkkejä.

Valitse vasemmalla olevasta navigointipalkista joko *Lisää kirja*, *Lisää artikkeli* tai *Lisää video*.

Täytyä avautuva lomake. Kirjailijan nimessä on oltava vähintään kolme kirjainta.

Tagit lisätään yhteen kertaan pilkuilla eroteltuna.

#### Poista vinkki

Kun haluat poistaa lukuvinkistöstä vinkkejä, klikkaa aluksi vasemmalla olevasta navigointipalkista linkkiä *Poista kirja*, *Poista artikkeli* tai *Poista video*.

Täytä avautuva lomake ja lähetä se.

Ohjelma ilmoittaa, onnistuiko poistaminen.

#### Selailunäkymä

##### Haku
Jos haluat *hakea* tietokannasta vinkkejä, käytä selailunäkymän yläosassa olevaa hakukenttää. Valitse kategoria, tagi tai kirjoita vapaa hakusana.

##### Vinkin muokkaus
Jos haluat *muokata* vinkkejä, valitse selailunäkymän listalta haluamasi vinkki ja klikkaa sen välittömässä läheisyydessä olevaa *Muokkaa*-painiketta. Muokkaa haluamasi tiedot avautuvaan lomakkeeseen ja lähetä lomake.

#### Lopeta ohjelma -linkki

Sulkee ohjelman.


### Definition of done

Taski on suoritettu "definition of donen" tasolla, kun
1. Taskiin liittyvä koodi (esim. metodi) on kirjoitettu.
2. Tuotetun koodin on katsonut läpi ja hyväksynyt joku toinen projektin henkilö. Koodi toimii toivotusti.
3. Tuotettuun koodiin on kirjoitettu kattavat testit.
4. Toinen henkilö on tarkastanut ko. testit. Testit toimivat odotetusti.
5. Koodiin liittyvä dokumentaatio on lisätty  ohjelman ohjeisiin ja tarvittaessa README-tiedostoon.
6. Taski on siirretty backlogissa DONE-sarakkeeseen.
7. Taskin suoritus on lisätty burndown-listalle.

#### Parit tarkistusta varten

Sami & Ossi

Sonja & Dennis

Atte & Lauri
