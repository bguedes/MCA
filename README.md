# Multi Cluster Awareness demo

Demonstration for Multi-Cluster Awareness (MCA) capabilities

## Setup

For the 2 data centers configuration :

docker run -d -p 7091:8091  --name dc1.server1.1 couchbase/server
docker run -d -p 9091:8091  --name dc1.server1.2 couchbase/server
docker run -d -p 10091:8091 --name dc1.server1.3 couchbase/server

docker run -d -p 11091:8091 --name dc2.server2.1 couchbase/server
docker run -d -p 12091:8091 --name dc2.server2.2 couchbase/server
docker run -d -p 13091:8091 --name dc2.server2.3 couchbase/server

For the MCA application :

docker build --file=Dockerfile --tag=mca-demo:latest --rm=true .
  
docker volume create --name=mca-repository

## To Run
  
`docker run --name=mca-demo --publish=8080:8080 --volume=mca-repository:/var/lib/mca-demo/repository mca-demo:latest`

## To Test

http://localhost:8080/api/airports/airport/airport_1254/

Simulate node failure in primary Data Center :

docker stop server1.2
docker start server1.2

Simulate node failure in another Data Center (secondary) :

docker stop server2.2
docker start server2.2
