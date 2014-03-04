## Xiami

xiami robot

------------


### Version
- v0.1 - 20140303

### Functions
- Auto sign in for xiami.com

### Usage

1. Fullfil your username & password in XiamiLogin.java
2. cd<path-to-project>/Xiami
3. mvn clean package
4. cd <path-to-project>Xiami/target/Xiami-0.1-release/xiami
5. vim run.sh
6. Add the following commands to run.sh:

	```
	#!/bin/sh
	java -cp .:lib -jar Xiami*.jar
	```
7. Add "sh <path-to-project>Xiami/target/Xiami-0.1-release/xiami/run.sh" to your crontab