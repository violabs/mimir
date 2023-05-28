This is an example of how to connect to Milvus - the open source Vector DB.

To run the tests (and the app), navigate to project folder `milvus` 
and run the docker compose command `docker compose -f docker-compose.test.yml up`. 
If you want to run local app I would recommend running the other docker compose file `docker compose up`

`docker compose up`. You may keep it if you want to persist data, or remove it with `docker compose down`. 
Add the `-f docker-compose.test.yml` if test setup. I will work on the embedded version of testing later.