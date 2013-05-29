iron-cookie
===========

This is a JAX-RS 2 based example showing how to use [jron](https://github.com/algermissen/jiron)-sealed
tokens for stateless cookie-based authentication.

jiron ensures that the data (e.g. user login, realname, token expire time) stored in the cookie
cannot be read or modified by the client or any man in the middle.

Note that this does not solve the problem that with cookie-based authentication anyone
in possession of the cookie can send requests indistinguishable from requests by
the real cookie-owner. You still need transport layer security to protect the cookie from
being stolen.

How it Works
============

[DashboardResource](https://github.com/algermissen/iron-cookie/blob/master/src/main/java/net/jalg/ironcookie/DashboardResource.java) is the protected resource.

[LoginResource](https://github.com/algermissen/iron-cookie/blob/master/src/main/java/net/jalg/ironcookie/LoginResource.java) authenticates user credentials and issues Cookie. Here, jiron-sealing is done.
  
[AuthFilter](https://github.com/algermissen/iron-cookie/blob/master/src/main/java/net/jalg/ironcookie/AuthFilter.java) is a JAX-RS 2.0 filter class, enforing cookie-authentication. Here, jiron-unsealing is done, if the cookie
is present. Otherwise the client is redirected to the login form.

A Note on Project Setup
=======================

jiron is not yet in any public Maven repository. You will need to download jiron yourself and adjust the POM
to your needs.

