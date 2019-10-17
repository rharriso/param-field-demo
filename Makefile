clean:
	rm -rf storybook-static
	rm -rf dist
	mkdir -p dist

#
# apollo type generation
#
.PHONY: apollo-generate
apollo-generate:
	npx apollo client:codegen \
		--excludes=node_modules/* \
		--excludes=src/**/*.stories.* \
		--includes=src/**/*.ts* \
		--target=typescript \
		--endpoint=http://vapormail.local:3000/graphql \
		--outputFlat \
		src-ui/schemaTypes

#
# Static Files
#
.PHONY: html-pages
html-pages:
	cp src-ui/index.html dist/index.html

#
# Dev Server
#
run-dev: html-pages
	npx webpack-dev-server --config webpack.js