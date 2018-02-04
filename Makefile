run:
	docker-compose -f docker/debug/docker-compose.yml down
	./gradlew jar
	docker-compose -f docker/debug/docker-compose.yml up