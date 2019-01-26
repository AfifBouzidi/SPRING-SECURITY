# SPRING-SECURITY
## Introduction
Spring Security est un Framework qui permet de sécuriser les applications web. Il fournit deux services de sécurité: l’authentification et l’autorisation
- Authentification : il s'agit de la vérification de l'identité d'un principal (utilisateur physique, un appareil, système...). Parmi les modèles d’authentification fournis par spring on cite HTTP BASIC authentication, HTTP Digest authentication, Form-based authentication, LDAP, CAS...     
- Autorisation : il s'agit de la vérification  des droits accordés à un principal. Les niveaux d’autorisations fournis par spring sont : autorisations basées sur les URLS, l’invocation des méthodes et  l’invocation des objets (ACL)
## L’authentification
Comprendre l’authentification via Spring sécurité revient à déterminer les responsabilités des classes, respectivement des interfaces, suivantes :
- AuthenticationManager 
- ProviderManager  
- AuthenticationProvider
- Authentication 

La relation entre ces briques de base est illustrée par le diagramme suivant:
