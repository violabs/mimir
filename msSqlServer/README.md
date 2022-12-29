This is an example of a simple JPA setup with Postgres.

To run the tests (and the app), navigate to project folder `postgres` 
and run the docker compose command `docker compose -f docker-compose.test.yml up`. 
If you want to run local app I would recommend running the other docker compose file `docker compose up`

`docker compose up`. You may keep it if you want to persist data, or remove it with `docker compose down`. 
Add the `-f docker-compose.test.yml` if test setup. I will work on the embedded version of testing later.

If you want your data to persist past each application start, switch the property to `spring.jpa.hibernate.ddl-auto: update`

The contents of this simple example are stored in `springJpaCore` and `sharedSql` modules within `core`.

If you want to make your own service groups, you can extend the classes in `springJpaCore`.