# Modify Karaf script

* Add the option `-XX:-UseSplitVerifier` to the line containing the DEFAULT_JAVA_OPTS

```
DEFAULT_JAVA_OPTS="-Xms$JAVA_MIN_MEM -Xmx$JAVA_MAX_MEM -XX:+UnlockDiagnosticVMOptions -XX:+UnsyncloadClass *-XX:-UseSplitVerifier"*
```

of the karaf script located in the bin folder 

REMARK : This is due to an issue with JDK 7 and Spring : http://stackoverflow.com/questions/8958267/java-lang-verifyerror-expecting-a-stackmap-frame

# JBoss Fuse 6.1

* Install the features repository of the project

```
    addurl mvn:org.fusesource.devoxx.reportincident/features/1.0-SNAPSHOT/xml/features
```

* Deploy the features reportincident-jpa
```
    features:install reportincident-jpa
```
* Verify that the Web Service works well
```
    http://localhost:8282/cxf/camel-example/incident?wsdl
```
* Check the web site
```
    http://localhost:8181/reportincidentweb/
```
* Send SOAP Request using SOAPUI
```
    <soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
                      xmlns:rep="http://reportincident.devoxx.fusesource.org">
        <soapenv:Header/>
        <soapenv:Body>
            <rep:inputReportIncident>
                <incidentId>999</incidentId>
                <incidentDate>08-05-2014</incidentDate>
                <givenName>Charles</givenName>
                <familyName>Moulliard</familyName>
                <summary>Issue took place during Demo</summary>
                <details>Room is burning !</details>
                <email>cmoulliard@redhat.com</email>
                <phone>+32473604014</phone>
            </rep:inputReportIncident>
        </soapenv:Body>
    </soapenv:Envelope>
```
* Copy some CSV records in the folder scanned by camel
```
    cp routing/src/data/csv.txt ${JBOSS_FUSE.HOME}/data/reportincident
``` 
Remark : the pdf file is not up to date !!

