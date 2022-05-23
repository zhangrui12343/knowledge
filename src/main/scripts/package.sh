#!/bin/sh
#测试环境使用 application-test.yml profiles=test
CONFIG=bootstrap-test.yml
#正式环境使用application.yml profiles=online
#CONFIG=application.yml

THIS_PATH=$(pwd)

cd $(cd `dirname $0`; pwd)

FILE1=knowledge-1.0.0-SNAPSHOT.jar
#FILE2=knowledge-0.0.1-SNAPSHOT.jar
FILE2=knowledge-1.0.jar
VER=v1.0.4-test
PA=/Temp/knowledge

if [ ! -z $1 ];then
    PA=$1
fi

if [ ! -z $2 ]; then
    FILE2=$2
fi

if [ ! -z $3 ]; then
    VER=$3
fi

P=knowledge-${VER}
LOG=log4j2-for-test.xml

rm -rf ${PA}/${P}
rm -rf ../target
rm -rf ${PA}/${P}.zip

mkdir -p  ${PA}/${P}/temp
#mv  ../src/main/resources/bootstrap*.yml ${PA}/${P}/temp
#mv ../src/main/resources/*.xml ${PA}/${P}/temp

cd ../../../
THIS_PATH_TEMP=$(pwd)
echo "当前路径${THIS_PATH_TEMP}"

 mvn clean package -Dmaven.test.skip=true

if [ -e target/${FILE1} ]; then
    echo "编译完成...开始拷贝..."
    cp target/${FILE1} ${PA}/${P}/${FILE2}
    cp ${PA}/${P}/temp/${LOG} ${PA}/${P}
    cp ${PA}/${P}/temp/${CONFIG} ${PA}/${P}/bootstrap.yml
    if [ -d "sql" ]; then
        cp -R sql ${PA}/${P}
    fi
else
    echo "编译失败..."
    cd ${THIS_PATH}
    exit 1
fi
cd bin

mv ${PA}/${P}/temp/* ../src/main/resources
rm -rf ${PA}/${P}/temp

#if [ "$1" = "true" ]
#then
#    echo "zip..."
#    cd ${PA}
#    zip -r ${P}.zip ${P}
#fi

cd ${THIS_PATH}

#echo ${PA}/${P}

open ${PA}/${P}