clean:
	rm -rf storybook-static
	rm -rf dist target
	mkdir -p dist
	touch dist/.gitkeep

#
# apollo type generation
#
.PHONY: apollo-generate
apollo-generate:
	npx apollo client:codegen \
		--excludes=node_modules/* \
		--excludes=src-ui/**/*.stories.* \
		--includes=src-ui/**/*.ts* \
		--target=typescript \
		--endpoint=http://vapormail.local:5000/graphql \
		--outputFlat \
		src-ui/schemaTypes

#
# Static Files
#
.PHONY: html-pages
html-pages:
	cp src-ui/index.html dist/index.html

.PHONY: static-files
static-files:
	cp -r static dist/

#
# Dev Server
#
.PHONY: run-webpack-dev
run-webpack-dev: html-pages
	npx webpack-dev-server --config webpack.js

.PHONY: run-graphql-service
run-graphql-service: build-no-test
	java -jar target/paramPropertyDemo-1.0-SNAPSHOT.jar

KOTLIN_SOURCES:= $(wildcard ./src/%)
KOTLIN_RESOURCES:= $(wildcard ./resources/%)

target/paramPropertyDemo-1.0-SNAPSHOT.jar: $(KOTLIN_SOURCES) $(KOTLIN_RESOURCES) pom.xml
	mvn package -Dmaven.test.skip=true

.PHONY: build-no-test
build-no-test: target/paramPropertyDemo-1.0-SNAPSHOT.jar