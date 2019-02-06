FROM java:8
MAINTAINER bruno.guedes@couchbase.com
COPY /target/spring-multi-cluster-example-0.0.1-SNAPSHOT.jar /opt/mca-demo/lib/
#COPY files/spring-cloud-config-server-entrypoint.sh /opt/spring-cloud/bin/
#ENV SPRING_APPLICATION_JSON= \ 
#  '{"spring": {"cloud": {"config": {"server": \
#  {"git": {"uri": "/var/lib/spring-cloud/config-repo", \
#  "clone-on-start": true}}}}}}'
ENTRYPOINT ["/usr/bin/java"]
CMD ["-jar", "/opt/mca-demo/lib/spring-multi-cluster-example-0.0.1-SNAPSHOT.jar"]
VOLUME /var/lib/mca-demo/repository
EXPOSE 8080