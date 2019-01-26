# SPRING-SECURITY
## Introduction
Spring Security est un Framework qui permet de sécuriser les applications web. Il fournit deux services de sécurité: l’authentification et l’autorisation
- *Authentification* : il s'agit de la vérification de l'identité d'un principal (utilisateur physique, un appareil, système...). Parmi les modèles d’authentification fournis par spring on cite HTTP BASIC authentication, HTTP Digest authentication, Form-based authentication, LDAP, CAS...     
- *Autorisation* : il s'agit de la vérification  des droits accordés à un principal. Les niveaux d’autorisations fournis par spring sont : autorisations basées sur les URLS, l’invocation des méthodes et  l’invocation des objets (ACL)
## L’authentification
Comprendre l’authentification via Spring sécurité revient à déterminer les responsabilités des classes, respectivement des interfaces, suivantes :
- *AuthenticationManager* 
- *ProviderManager*  
- *AuthenticationProvider*
- *Authentication* 

La relation entre ces briques de base est illustrée par le diagramme suivant:
![](https://github.com/AfifBouzidi/SPRING-SECURITY/blob/master/class%20diagram_1.png)
- *AuthenticationManager* est une interface (avec une seule méthode) qui représente le point d’entrée du mécanisme de l’authentification 
- *AuthenticationProvider* est l’implémentation par défaut de l’interface *AuthenticationManager*. L’implémentation est basée sur le design pattern chaîne de responsabilité, elle permet à un nombre quelconque de classes (implémentant l’interface *AuthenticationProvider*) d'essayer de répondre à la  requête d'authentification.  
Chaque *AuthenticationProvider* invoqué soit il retourne un objet *Authentication* en cas de réussite ou génère une *AuthentificationException* en cas d’échec. En cas d’échec le *ProviderManager*  passe alors à l’*AuthenticationProvider* suivant s’il existe.
- *AuthenticationProvider* est interface avec deux méthodes :  
```Authentication authenticate (Authentication authentication) throws AuthenticationException```  
```Boolean supports (java.lang.Class<?> authentication)```: permet de vérifier si le provider est compatible avec l’objet *Authentication*  
Spring fournit un nombre d’implémentations de l'interface *AuthenticationProvider* tels que : *DaoAuthenticationProvider*, *LdapAuthenticationProvider*...  
 




