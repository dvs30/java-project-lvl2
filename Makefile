.DEFAULT_GOAL := build-run

install:
		./gradlew clean install

run-dist:
		./build/install/app/bin/app

run:
		./gradlew run

check-updates:
		./gradlew dependencyUpdates

lint:
		./gradlew checkstyleMain

build:
		./gradlew clean build

.PHONY: install, build