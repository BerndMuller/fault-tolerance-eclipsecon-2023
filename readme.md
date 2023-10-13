# MicroProfile Fault Tolerance

## WildFly Maven Plugin

```
mvn clean wildfly:dev

mvn clean wildfly:package 
./target/server/bin/standalone.sh
```

MicroProfile is configured in pom.xml for WildFly Maven Plugin. If you want to use standard
WildFly you have to use MicroProfile explicitly.

```
standalone.sh -c standalone-microprofile.xml
```


## Demo 

Start with 
```
http://localhost:8080/fault-tolerance/demo
```



## Metrics

According to the Fault Tolerance spec metrics is activated automatically for all methods annotated with Fault Tolerance Annotations.
However, no metrics appear for me and I have no idea what to do.

