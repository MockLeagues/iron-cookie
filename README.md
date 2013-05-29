iron-cookie
===========

This is a JAX-RS 2 based example showing how to use [jron](https://github.com/algermissen/jiron)-sealed
tokens for stateless cookie-based authentication.

jiron ensures that the data (e.g. user login, realname, token expire time) stored in the cookie
cannot be read or modified by the client or any man in the middle.

Note that this does not solve the problem that with cookie-based authentication anyone
in possessionof the cookie can send requests indistinguishable from requests by
the real cookie-owner. You still need transport layer security to protect the cookie from
being stolen.

  


