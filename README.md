* Add this option to karaf script

DEFAULT_JAVA_OPTS="-Xms$JAVA_MIN_MEM -Xmx$JAVA_MAX_MEM -XX:+UnlockDiagnosticVMOptions -XX:+UnsyncloadClass -XX:-UseSplitVerifier"

due to issue with JDK 7 and Spring : http://stackoverflow.com/questions/8958267/java-lang-verifyerror-expecting-a-stackmap-frame

* JBoss Fuse 6.1

install -s mvn:org.apache.servicemix.bundles/org.apache.servicemix.bundles.cglib/2.1_3_7
addurl mvn:org.apache.activemq/activemq-karaf/5.9.0.redhat-610379/xml/features
addurl mvn:org.fusesource.devoxx.reportincident/features/1.0-SNAPSHOT/xml/features
features:install reportincident-jpa


open http://localhost:8282/cxf/camel-example/incident?wsdl

open http://localhost:8181/reportincidentweb/

