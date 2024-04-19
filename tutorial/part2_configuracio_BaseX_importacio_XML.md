
### 2. Configuració de BaseX i Importació d'XML

**Instal·lar BaseX**:
1. **Descàrrega**: Ves a la pàgina oficial de BaseX (http://basex.org) i navega fins a la secció de descàrregues. Selecciona la versió adequada per al teu sistema operatiu (recomanació .zip).

2. **Instal·lació**: Descomprimeix l'arxiu i localitza els executables dins el subdirectori `bin`. Pots exeecutar-los des d'un terminal.

**Crear una Nova Base de Dades**:
1. **Obre BaseX GUI**: Un cop instal·lat, inicia BaseX. Habitualment, es començarà amb la interfície gràfica d'usuari (GUI).
2. **Nova Base de Dades**: Vés a `Database` > `New`. Apareixerà un diàleg que et demanarà un nom per a la nova base de dades. Potser voldràs anomenar-la `Factbook` per a aquest tutorial.
3. **Importar `factbook.xml`**: Amb el diàleg de creació de la nova base de dades encara obert, busca l'opció per seleccionar un fitxer i navega fins al subdirectori `./etc/factbook.xml`. Selecciona'l per a que sigui importat com a part de la creació de la base de dades.

**Explorar la Base de Dades**:
Una vegada creada la base de dades i importat el `factbook.xml`, pots explorar la seva estructura:
- Utilitza la vista d'arbre a l'esquerra per veure l'estructura del document XML.
- Pots expandir els nodes per veure elements i atributs específics.

**Realitzar Consultes Bàsiques**:
Amb la base de dades `Factbook` ja carregada, pots començar a realitzar consultes per familiaritzar-te amb la informació:
- Navega fins a la pestanya `Editor` a la GUI.
- Introdueix una consulta XQuery per seleccionar els noms dels països:
  ```xquery
  for $country in //country
  return $country/@name
  ```
- Prem `Ctrl+Enter` o fes clic a l'opció `Execute` per veure els noms dels països continguts en el document.

**Conclusió**:
Ara hauries de tenir BaseX instal·lat, una nova base de dades creada, i el document `factbook.xml` importat i llest per a ser consultat mitjançant XQuery. Això et proporciona una base sòlida per a començar a explorar més enllà amb consultes més complexes i aprendre sobre el processament i la gestió de dades XML.
