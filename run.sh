#!/bin/bash
pushd `dirname $0` > /dev/null;DIR=`pwd -P`;popd > /dev/null

JAVA_OPTS=""

if [ ! -z $1 ]; then
  JAVA_OPTS="-Dusers=$1 ${JAVA_OPTS}"
fi

if [ ! -z $2 ]; then
  JAVA_OPTS="-Dduration=$2 ${JAVA_OPTS}"
fi

if [ ! -z $3 ]; then
  JAVA_OPTS="-Dramp=$3 ${JAVA_OPTS}"
fi

docker run -v ${DIR}/conf:/opt/gatling/conf \
           -v ${DIR}/results:/opt/gatling/results \
           -v ${DIR}/user-files:/opt/gatling/user-files \
           -e JAVA_OPTS="${JAVA_OPTS}" \
           -it maluuba/gatling\
           -s saas.Saas
