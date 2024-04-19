
### 4. Agregació i Resum de Dades Utilitzant XQuery

En aquesta secció del tutorial, ens endinsarem en com realitzar operacions d'agregació i generar resums de dades utilitzant XQuery a BaseX. Aquestes tècniques són crucials per analitzar grans volums de dades XML i extreure informació valuosa de manera eficient.

**Funcions d'Agregació Bàsiques**:

Les funcions d'agregació permeten realitzar càlculs com sumes, mitjanes, i totals sobre conjunts de dades. Aquí teniu alguns exemples pràctics utilitzant `factbook.xml`:

1. **Suma Total de la Població Mundial**:
   Calcula la població total sumant l'atribut `@population` de tots els elements `country`, assegurant-se de tractar els valors com a números:
   ```xquery
   sum(
     for $country in //country
     return number($country/@population)
   )
   ```
   
2. **Mitjana de la Població per País**:
   Determina la mitjana de població entre tots els països, novament convertint els valors a números per a l'operació:
   ```xquery
   avg(
     for $country in //country
     return number($country/@population)
   )
   ```
   
3. **Comptar el Nombre de Països**:
   Simplement compta el nombre d'elements `country` presents en el document:
   ```xquery
   count(//country)
   ```

**Generant Informes de Dades Agregades**:

A més de les funcions d'agregació bàsiques, XQuery pot ser utilitzat per crear informes detallats que resumin les dades de maneres complexes.

4. **Població Total d'un país sumant les poblacions de les ciutats**:
   Aquest exemple mostra com generar un resum de la població total per cada pais sumant les poblacions de les seves ciutats:
   ```xquery
    let $countries := 
      for $country in //country
      let $populationSum := sum($country/city/population/number())
      order by $populationSum descending
      return
        <country name="{$country/@name}" population="{$populationSum}"/>

    return $countries
   ```
   En aquest cas, s'utilitza `group by` per agrupar els resultats per continent, tot i que l'ús exacte de `group by` pot dependre de la versió d'XQuery suportada pel teu sistema BaseX.

**Conclusió**:
Les operacions d'agregació i els resums de dades són eines poderoses en l'anàlisi de dades XML, permetent-te obtenir insights profunds a partir de les teves col·leccions de dades. Amb aquestes habilitats, podràs realitzar anàlisis complexes i produir informes útils a partir de les teves dades XML utilitzant BaseX.
