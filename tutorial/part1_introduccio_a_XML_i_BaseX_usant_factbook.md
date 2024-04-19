
# Introducció a XML i BaseX Usant `factbook.xml`

## Conceptes Bàsics d'Estructura XML:
- XML (eXtensible Markup Language) és un llenguatge de marques dissenyat per emmagatzemar i transportar dades. És llegible tant per humans com per màquines.
- Els components clau inclouen elements (etiquetes) que poden tenir atributs i elements niuats, proporcionant una estructura de dades jeràrquica.

## Entenent `factbook.xml`:
- `factbook.xml` conté dades estructurades sobre països, incloent geografia, població, economia, etc.
- Està estructurat jeràrquicament, amb elements pare (per exemple, `country`) que contenen elements fills (per exemple, `population`, `gdp`).

## Introducció a BaseX:
- BaseX és un motor de base de dades XML ràpid, potent i lleuger, i un processador XPath/XQuery.
- Admet la gestió avançada de dades XML, incloent consultes, indexació i emmagatzematge.

## Exemples:
1. Fragment XML bàsic de `factbook.xml`:
```xml
<country name="Exampleland">
  <population>123456</population>
  <gdp>654321</gdp>
</country>
```
2. Obrint BaseX i Creant una Base de Dades:
   - Inicia BaseX GUI.
   - Clica a `Database` -> `New` per crear una nova base de dades.
   - Importa `factbook.xml`.
