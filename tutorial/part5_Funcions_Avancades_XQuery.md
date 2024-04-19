
### 5. Funcions Avançades de XQuery i Ordenació de Dades

Explorarem com utilitzar funcions avançades de XQuery per realitzar operacions complexes de manipulació de dades i com ordenar aquestes dades de manera eficaç dins de BaseX, utilitzant `factbook.xml` com a exemple.

**Expressions FLWOR Avançades i Ordenació**:

1. **Funció Avançada per a la Densitat de Població**:
   ```xquery
   declare function local:populationDensity($country as element(country)) as xs:double? {
     let $population := number($country/@population)
     let $area := number($country/@total_area)
     return if ($area > 0 and $population > 0) then $population div $area else ()
   };
   
   for $country in //country
   let $density := local:populationDensity($country)
   order by $density descending
   return 
     <country name="{$country/@name}" density="{$density}"/>
   ```
   Aquesta consulta ordena els països segons la seva densitat de població de major a menor.


2. **Funció Avançada per a la Densitat de Població**:
   ```xquery
    declare function local:gdpPerArea($country as element(country)) as xs:double? {
      let $gdpTotal := number($country/@gdp_total) * 1000000 (: Convertint de milions a unitats per precisió :)
      let $totalArea := number($country/@total_area) (: Asumint que l'àrea està en quilòmetres quadrats :)
      return if ($totalArea > 0) then $gdpTotal div $totalArea else ()
    };

    for $country in //country
    let $gdpRatio := local:gdpPerArea($country)
    order by $gdpRatio descending
    return 
    <country name="{$country/@name}" gdp_per_area="{$gdpRatio}"/>
   ```
  Aquesta funció, local:gdpPerArea, calcula el PIB per àrea (quilòmetre quadrat) de cada país i després ordena els països basant-se en aquest ràtio de major a menor. Aquesta consulta pot revelar quins països tenen una economia "més intensa" en termes de producció econòmica per unitat d'àrea, potencialment indicant una major activitat econòmica o una millor utilització dels recursos terrestres.

