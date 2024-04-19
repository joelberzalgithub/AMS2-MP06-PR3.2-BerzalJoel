# Plantilla projecte Java amb Maven DAM2-MP06 #

## Estat del projecte
![maven workflow](https://github.com/jpala4-ieti/DAM2-MP06-UF03-Base/actions/workflows/maven.yml/badge.svg)

## Arrencada ràpida ##
Execució ràpida dels diferents exemples i resolusions de problemes

## Windows ##
```bash
.\run.ps1 cat.iesesteveterradas.Main
```

## Linux ##
```bash
run.sh  cat.iesesteveterradas.Main
```

# MongoDB, posada en marxa

Dins del directori etc/mongodb-docker es troba el fitxer de configuració docker-compose.yml

Per arrencar les instàncies, al canviar a dins del directori.

## Creació d'instàncies ##
```bash
docker-compose up -d 
```

## Aturada d'instàncies ##
```bash
docker-compose stop
```

## Arrencada d'instàncies ##
```bash
docker-compose start
```

## Esborrat d'instàncies ##
```bash
docker-compose down
```

## Administració a través d'interfície web
http://127.0.0.1:8081/
usuari: admin
contrasenya: pass
