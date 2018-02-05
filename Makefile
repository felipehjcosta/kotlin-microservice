run:
	docker-compose down
	./gradlew jar
	docker-compose up