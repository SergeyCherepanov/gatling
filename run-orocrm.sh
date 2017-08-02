#!/bin/bash
pushd `dirname $0` > /dev/null;DIR=`pwd -P`;popd > /dev/null

JAVA_OPTS=""

if [[ ! -z $1 ]]; then
  JAVA_OPTS="-Dusers=$1 ${JAVA_OPTS}"
fi

if [[ ! -z $2 ]]; then
  JAVA_OPTS="-Dduration=$2 ${JAVA_OPTS}"
fi

if [[ ! -z $3 ]]; then
  JAVA_OPTS="-Dramp=$3 ${JAVA_OPTS}"
fi

echo "Please specify url:"
read oroUrl

echo "Please specify admin user name:"
read oroAdminUser

echo "Please specify admin user password:"
read oroAdminPassword

#JAVA_OPTS="-DoroUrl=$oroUrl -DoroAdminUser=$oroAdminUser -DoroAdminPassword=$oroAdminPassword ${JAVA_OPTS}"

docker run -v ${DIR}/conf:/opt/gatling/conf \
           -v ${DIR}/results:/opt/gatling/results \
           -v ${DIR}/user-files:/opt/gatling/user-files \
           -e JAVA_OPTS="${JAVA_OPTS}" \
           -e ORO_URL="$oroUrl" \
           -e ORO_USER="$oroAdminUser" \
           -e ORO_PASSWORD="$oroAdminPassword" \
           --rm \
           -it denvazh/gatling \
           -s OroCrmSimulation
