# Twitch Mentions Discord

This application joins twitch channels you are following and twitch channels which are in your application.yml. Every 5
minutes the twitch channels are reloaded from your application.yml.

## twitch4j

For the use of the Twitch API the library twitch4j was used.
https://github.com/twitch4j/twitch4j

## Oauth-Token / Chat-Token

You can generate a oauth chat token using the `Twitch Chat OAuth Password Generator`:
http://twitchapps.com/tmi/

## client-id / client-secret

You can get a client id and client secret from https://dev.twitch.tv/console after register an application

## application.yml

A template for application.yml can be found at `/src/resources/application.yml.example`.

## todo before start

## docker

To start the project with Docker you have to build the project once and copy the JAR file to the folder `docker/target`
and create the file `application.yml` from the template `application.yml.example` and copy it to `docker/config`.

## without docker

The project can also be used without Docker. To do this, simply create the `application.yml` from the
`application.yml.example` template and start the JAR file with the `--spring.config.location=` parameter. With
`--spring.config.location=` the folder with the `application.yml` is specified.
