# Linked data linker - Java zápočtový test

## Popis

### Formát N-Triples
```
<http://one.example/subject1> <http://one.example/predicate1> <http://one.example/object1> . # comments here
# or on a line by themselves
<http://one.example/subject2> <http://one.example/predicate2> <http://one.example/object2> .
<http://one.example/subject2> <http://one.example/predicate3> "literal" .
```
`< >` označujú IRI = jednoznačný identifikátor

N-Triples súbor obsahuje na 1 riadku tieto informácie:
* `subject` - prvý element na riadku, reprezentuje 1 entitu
   * je to vždy IRI
* `predicate` - druhý element na riadku, reprezentuje vlastnosť
    * je to vždy IRI
* `object` - tretí element na riadku, reprezentuje 1 entitu
    * je to buď IRI (ohraničené `<>`) alebo literál (ohraničené `""` - neobsahuje medzeru) 

Subjekt môže byť opakovaný viac krát, viz. príklad a `<http://one.example/subject2>`

Subjekt môže mať `typ` určený predikátom `<https://www.w3.org/1999/02/22-rdf-syntax-ns#type>`, príklad:
```
# v tomto prípade je typom <http://one.example/object3>
<http://one.example/subject2> <https://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://one.example/object3> .
```

#### Linked Data Cesta
Formát: `<http://one.example/predicate1>|<http://one.example/predicate2>|<http://one.example/predicate3>` 

Je zoznam predikátov.

V príklade: 
```
<http://one.example/subject2> <http://one.example/predicate1> <http://one.example/object2> .
<http://one.example/object2> <http://one.example/predicate2> "literal" .
```
Cesta `<http://one.example/predicate1>|<http://one.example/predicate2>` pre entitu `<http://one.example/subject2>`
reprezentuje hodnotu "literal".

### Program

Úlohou je naprogramovať program, ktorý spozná 2 rovnaké entity na základe zadaných pravidiel a vyprodukuje výstup
na štandardný výstup (pomocou `System.out.println()`) vo formáte N-Triples ako:
```
# v prípade, že `<http://one.example/subject1>` a `<http://one.example/subject2>` boli uznané ako rovnaké
<http://one.example/subject1> <http://www.w3.org/2002/07/owl#sameAs> <http://one.example/subject2> . 
```

Parametre programu (parameter `args` v `main` metóde) sú vo formáte:
```
{file1} {type1} {path1} {file2} {type2} {path2}
```
kde 
* `{fileX}` absolútna cesta k X. vstupnému súboru vo formáte N-Triples.
* `{typeX}` typ entít, ktoré sa uvažujú pre porovnávanie
* `{pathX}` Linked Data cesta, ktorá reprezentuje objekt, podľa ktorého sa entity porovnávajú

Implementáciu začnite písať do metódy `main` v `cz.cuni.mff.linkeddatalinker.Main` triede. 

#### Príklad
Súbor `x.nt`
```
<http://matfyz.cz/student/1> <https://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://matfyz.cz/student> .
<http://matfyz.cz/student/1> <http://matfyz.cz/ma_jmeno> "AdamHornacek" .
```

Súbor `y.nt`
```
<http://matfyz.cz/ucitel/24> <https://www.w3.org/1999/02/22-rdf-syntax-ns#type> <http://matfyz.cz/ucitel> .
<http://matfyz.cz/ucitel/24> <http://matfyz.cz/ma_jmeno> "AdamHornacek" .
```

Parametre programu:
```
x.nt <http://matfyz.cz/student> <http://matfyz.cz/ma_jmeno> y.nt <http://matfyz.cz/ucitel> <http://matfyz.cz/ma_jmeno>
```

Výstup:
```
<http://matfyz.cz/student/1> <http://www.w3.org/2002/07/owl#sameAs> <http://matfyz.cz/ucitel/24> .
```


### Testovanie

V termináli choďte do root adresára projektu (pomocou príkazov `cd`), potom spustite príkaz `mvn clean test`.

Pokiaľ všetky testy prejdú, tak ma zavolajte.

GL & HF :\)