#!/bin/sh
wget -qO- https://toolbelt.heroku.com/install-ubuntu.sh | sh
cd docker/production
heroku plugins:install heroku-container-registry
docker login -e _ -u _ --password=$HEROKU_API_KEY registry.heroku.com
heroku container:login
heroku container:push web --app $HEROKU_APP_NAME