
### 3. Introducció a XQuery

XQuery és un llenguatge de consulta potent per a XML que permet realitzar consultes complexes i manipular dades XML. A BaseX, XQuery s'utilitza per consultar, filtrar, i transformar dades d'una manera molt flexible i potent.

**Conceptes Bàsics de XQuery**:

- **FLWOR**: L'acrònim FLWOR representa "For, Let, Where, Order by, Return", que són els components clau d'una expressió XQuery. Aquests components permeten iterar sobre col·leccions de nodes, assignar valors a variables, filtrar resultats, ordenar-los i especificar què ha de ser retornat.

- **Funcions i Operadors**: XQuery inclou un conjunt ric de funcions integrades i operadors per manipular seqüències de nodes, cadenes de text, números i més.

**Exemples de Consultes XQuery a BaseX**:

1. **Llistar Noms i Poblacions dels Països**:
   ```xquery
   for $country in //country
   return <country>
     <name>{$country/@name}</name>
     <population>{number($country/@population)}</population>
   </country>
   ```
   Aquesta consulta utilitza una expressió FLWOR per seleccionar cada element `country`, i retorna un nou element XML per a cada país que conté el seu nom i població.

2. **Ordenar Països per Població en Ordre Descendent**:
   ```xquery
   for $country in //country
   order by number($country/@population) descending
   return $country/@name
   ```
   Aquí, la clàusula `order by` s'utilitza per ordenar els països basant-se en la seva població, de major a menor.
   S'usa `number` per convertir el text que representa la població en un número per tal que l'ordenació sigui correcta.

3. **Obtenir el Total de la Població Mundial**:
   ```xquery
   sum(//country/@population)
   ```

   ```xquery
   sum(//country/@population/number())
   ```

   ```xquery
   sum(
      for $country in //country
      return number($country/@population)
   )
   ```
   Utilitza la funció `sum()` per agregar la població total a partir dels atributs de població de tots els països.

**Execució de Consultes**:

- Per executar aquestes consultes, simplement copia i enganxa-les a l'editor de consultes de BaseX i prem `Ctrl+Enter` o el botó d'execució.
- Observa els resultats i experimenta amb modificacions de les consultes per familiaritzar-te amb diferents aspectes de XQuery.

**Conclusió**:
Aquesta introducció a XQuery et proporciona les eines bàsiques per començar a explorar i manipular dades XML dins de BaseX. A mesura que et familiaritzis amb aquests conceptes, podràs realitzar consultes cada vegada més complexes i extreure valor de les teves dades XML de maneres innovadores.
