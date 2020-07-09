up:
	mvn package -DskipTests=true -Dmaven.javadoc.skip=true
	docker-compose up --build

aws:
	docker login -u ${DOCKER_LOGIN_USER} -p ${DOCKER_LOGIN_PASSWORD} gitlab-registry.tubit.tu-berlin.de
	docker-compose pull
	docker-compose start

clean:
	mvn clean
	docker-compose down
	docker-compose rm --force

.PHONY: up aws clean
