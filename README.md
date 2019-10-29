## Building Token Management System event based using kafak 2.3.0 and java

![App Screenshot](screenshot.png)

## Steps to run TMS applocation

1. Pre-requisite of application to run java1.8 and kafka 2.3.0 should install on machine
   - Get clone from below git location and just copy this folder (i.e. kafka_2.12-2.3.0) after extracting to any of your drive 
   - Set below in environment variable in path:
	 <drive-name>:\kafka_2.12-2.3.0\bin\windows(If window machine is using to run this application)
	- create below directory in side the folder <drive-name>:\kafka_2.12-2.3.0 as below:
			<drive-name> 
				\/ kafka_2.12-2.3.0
					\/ data(New)
						> kafka(New)
						> zookeeper (New)
	- Next go to directory <drive-name>:\kafka_2.12-2.3.0\config
							Modify property log.dirs & num.partitions of server.properties as below
								log.dirs=C:/kafka_2.12-2.3.0/data/kafka
								num.partitions=6
							Modify property log.dirs of zookeepers.properties as below
							   dataDir=C:/kafka_2.12-2.3.0/data/zookeeper
    - Then zookeeper and kafka in seprate cmd window by using below command:
			<drive-name>:\kafka_2.12-2.3.0>zookeeper-server-start.bat config\zookeeper.properties
			<drive-name>:\kafka_2.12-2.3.0>kafka-server-start.bat config\server.properties

2. **Clone the application**

	```bash
	git clone https://github.com/punitsingh2/token-management-system.git

	```
3. **Import project into eclipse**
	- Search main class 'TokenManagementSystem.java' right click in simply run

```

The kafka server will start on port `2181`.
bootstrap server on port `9092`
