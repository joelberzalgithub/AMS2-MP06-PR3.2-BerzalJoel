
### 6. Cerca de Text Complet

La funcionalitat de cerca de text complet permet realitzar consultes expressives sobre textos dins de bases de dades XML com BaseX. Aquesta secció explica com utilitzar BaseX per realitzar cerques de text complet eficients en el `factbook.xml`.

**Introducció a la Cerca de Text Complet a BaseX**:

La cerca de text complet facilita trobar coincidències de paraules clau o frases dins de grans conjunts de dades XML. Per optimitzar el rendiment, assegura't que la teva base de dades estigui indexada per suportar cerques de text complet.

**Exemple Bàsic de Cerca de Text**:

Per trobar les ciutats que continguin "gor" en el seu nom pots usar la seüent consulta:

```xquery
for $city in //city[name[contains(lower-case(.), 'gor')]]
return 
  <city name="{$city/name}"/>
```

Aquesta consulta filtra elements `city` basant-se en el contingut de l'atribut `@name`, fent la cerca insensible a majúscules.


**Cerca Avançada amb Expressions Regulars**:

Per a cerques més flexibles, BaseX permet l'ús d'expressions regulars:

```xquery
declare function local:matchesNamePattern($country as element(country)) as xs:boolean {
  exists($country/name[matches(., 'a.*i', 'i')])
};

for $country in //country
where local:matchesNamePattern($country)
order by lower-case($country/name[1])
return $country/@name
```
Aquest exemple mostra com utilitzar `matches` per realitzar cerques basades en expressions regulars dins del text dels documents XML.
Estem cercant països que continguin primer la vocal 'a' i després la 'i' pero no necessàriament juntes.

### Aplicació de la Cerca de Text Complet:

L'ús de cerques de text complet és clau per a consultar i analitzar contingut textual en bases de dades XML, permetent filtrar i trobar informació específica de manera ràpida i eficient.

---
