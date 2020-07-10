up:
	mvn package -DskipTests=true -Dmaven.javadoc.skip=true
	docker-compose up --build

aws:
	docker login -u ${DOCKER_LOGIN_USER} -p ${DOCKER_LOGIN_PASSWORD} gitlab-registry.tubit.tu-berlin.de
	docker-compose pull
	docker-compose start

install-aws:
	# TODO some install commands may miss here
	sudo yum update -y
	sudo yum install docker git
	sudo service docker start
	sudo usermod -a -G docker ec2-user
	sudo curl -L https://github.com/docker/compose/releases/latest/download/docker-compose-$(uname -s)-$(uname -m) -o /usr/local/bin/docker-compose
	sudo chmod +x /usr/local/bin/docker-compose
	docker-compose version

clean:
	mvn clean
	docker-compose down
	docker-compose rm --force

.PHONY: up aws clean
