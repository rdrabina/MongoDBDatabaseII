#!/bin/bash
cd out/artifacts/mongo_lab_jar
java -jar mongo-lab.jar simpleQuery > ../../../simpleQuery.json
java -jar mongo-lab.jar aggregateQuery > ../../../aggregateQuery.json
echo Map reduce could take a while 
java -jar mongo-lab.jar mapReduceQuery > ../../../mapReduceQuery.json
