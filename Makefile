up:
	mvn package -DskipTests=true -Dmaven.javadoc.skip=true
	docker-compose up --build

clean:
	mvn clean
	docker-compose down
	docker-compose rm --force

.PHONY: up clean
