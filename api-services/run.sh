#Copyright (C) Nisum Technologies
#
#This software may be modified and distributed under the terms
#of the MIT license.  See the LICENSE file for details. 

export ENV=prod
mvn install -Dmaven.test.skip=true
nohup java -jar ./target/quickout-0.0.1-SNAPSHOT.jar &