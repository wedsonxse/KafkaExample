## Description

This simple Java application fetches data from an API and enqueues it to a Kafka topic using a producer. Its possible to index multiple apis to multiple topics.
Then a consumer will watch the topic and consume the data, logging it to the terminal

## Environment

The information used in the Java application can be changed in the ```.env``` file present in the repository root.  
**OBS**: Be careful to register URLs considering the correct docker service name.  
Example: ```<docker_service>:<port>```

## Execution

To execute the application using docker compose, run the following command:
```
docker compose up --force-recreate
```
