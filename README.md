# Jahia OAuth Ping Federate

This module is a community module to show how to implement a Ping Federate Authentication based on OAuth protocol.

jahia-oauth-pingfederate depends on jahia-authentication and jahia-oauth modules.

## How to configure ?

Activate those modules to your website and go to site settings.
* Domain: the Ping Federate domain without ending with slash character `/`
* ApiKey: ClientID from Ping Federate
* ApiSecret: Client secret from Ping Federate
* Scope: OpenID scopes
  * `openid`: to enable openid flow
  * `profile`: to be able to get profile information (email in the sub property from the JSON token)
* CallbackUrl: Jahia callback URL, the return url set up in Ping Federate

```shell
curl -s --user root:password -X POST https://jahia/modules/api/provisioning --form script='[{ "editConfiguration": "org.jahia.modules.auth", "configIdentifier": "systemsite", "properties": {
"siteKey": "systemsite",
"PingFederateApi.domain": "https://mysite",
"PingFederateApi.apiKey": "clientid",
"PingFederateApi.apiSecret": "clientsecret",
"PingFederateApi.scope": "openid profile",
"PingFederateApi.callbackUrl": "https://jahia/sites/digitall/home.pingFederateOAuthCallbackAction.do",
"PingFederateApi.enabled": "true" } }]'
```
