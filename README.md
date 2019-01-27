# Architecture spring security

Spring Security est un Framework qui permet de sécuriser principalement les applications web. Il fournit deux services de sécurité: l’authentification et l’autorisation
- *Authentification* : il s'agit de la vérification de l'identité d'un principal (utilisateur physique, un appareil, système...). Parmi les modèles d’authentification fournis par spring on cite HTTP BASIC authentication, HTTP Digest authentication, Form-based authentication, LDAP, CAS...     
- *Autorisation* : il s'agit de la vérification  des droits accordés à un principal. Les niveaux d’autorisations fournis par spring sont : autorisations basées sur les URLS, l’invocation des méthodes et  l’invocation des objets (ACL)

## Chaîne de filtres de sécurité  
Spring security s’appuie sur une chaine de filtres dont le filtre DelegatingFilterProxy  représente le point d’entrée, il intercepte les requêtes http et déclenche les services d’authentification et d’autorisation. DelegatingFilterProxy doit être déclaré dans le web.xml et sa responsabilité consiste à déléguer le traitement des requêtes http au bean spring FilterChainProxy  

![](https://github.com/AfifBouzidi/SPRING-SECURITY/blob/master/filterChainProxy.PNG)  
ref: https://www.slideshare.net  
Selon le modèle d’authentification, plusieurs filtres sont ajoutés à la chaine par exemple    


| Méthode  |   |
|---|---|
| HTTP BASIC  |BasicAuthenticationFilter  |
|  HTTP DIGEST | DigestAuthenticationFilter  |
|Form Based Login 	|UsernamePasswordAuthenticationFilter   |
  
  Les filtres d'authentication exécutent les actions suivantes:  
  
1- Construction de l’objet Authentication  

2- Invoquer la méthode authenticate de l’AuthenticationManager   

3- Si l’authentification est un succès, ajout de l’objet Authentication dans le contexte de securité  

Autres filtres :  

- ExceptionTranslationFilter : intercepte toutes les exceptions Spring Security pour retourner une erreur HTTP ou lancer un autre AuthenticationEntryPoint. 
- FilterSecurityInterceptor , protége les URI et génère des exceptions lorsque l'accès est refusé, il utilise un AuthenticationManager et un AccessDecisionManager pour gérer les services d’autorisation. 


## L’authentification
Comprendre l’authentification via Spring sécurité revient à déterminer les responsabilités des classes, respectivement des interfaces, suivantes :
- *AuthenticationManager* 
- *ProviderManager*  
- *AuthenticationProvider*
- *Authentication* 
- *UserDetailsService*
- *UserDetails*
- *User*
- *GrantedAuthority*
- *PasswordEncoder*
- *SecurityContext*
- *SecurityContextHolder* 

### L’authentification Manager et l’authentification Provider 

La relation entre ces deux briques de base est illustrée par le diagramme suivant:
![](https://github.com/AfifBouzidi/SPRING-SECURITY/blob/master/class%20diagram_1.png)
- *AuthenticationManager* est une interface (avec une seule méthode) qui représente le point d’entrée du mécanisme de l’authentification 
- *AuthenticationProvider* est l’implémentation par défaut de l’interface *AuthenticationManager*. L’implémentation est basée sur le design pattern chaîne de responsabilité, elle permet à un nombre quelconque de classes (implémentant l’interface *AuthenticationProvider*) d'essayer de répondre à la  requête d'authentification.  
Chaque *AuthenticationProvider* invoqué soit il retourne un objet *Authentication* en cas de réussite ou génère une *AuthentificationException*(runtime exception) en cas d’échec. En cas d’échec le *ProviderManager*  passe alors à l’*AuthenticationProvider* suivant s’il existe.
- *AuthenticationProvider* est interface avec deux méthodes :  
```Authentication authenticate (Authentication authentication) throws AuthenticationException```  
```Boolean supports (java.lang.Class<?> authentication)```: permet de vérifier si le provider est compatible avec l’objet *Authentication*. Spring fournit un nombre d’implémentations de l'interface *AuthenticationProvider* tels que : *DaoAuthenticationProvider*, *LdapAuthenticationProvider*...  

![](https://github.com/AfifBouzidi/SPRING-SECURITY/blob/master/AuthenticationProvider.png)  
  
 - *Authentication*  
Une interface qui représente les informations relatives à un Principal authentifié (ou à authentifier). Si l’authentification est réussie, cet objet est stocké pour pouvoir procéder à l’étape d’autorisation.  Spring fournit un nombre d’implémentations de l'interface *Authentication* : UsernamePasswordAuthenticationToken, CasAuthenticationToken ...
 
| Méthode  |   |
|---|---|
|  java.util.Collection<? extends GrantedAuthority>	getAuthorities() |retourne la liste des droits de l’utilisateur   |
| java.lang.Object	getCredentials()  |retourne les informations d’identification (mot de passe)   |
| 	java.lang.Object	getDetails()  | retourne des informations additionnelles (mail,@ip...)  |
|  	java.lang.Object	getPrincipal()| retourne le Principal l’utilisateur identifié  |
|   boolean	isAuthenticated()|  est-ce que le Principal est authentifié ou non  |
|  void	setAuthenticated(boolean isAuthenticated)| valider ou non l’authentification  |

### Chargement de détail d’un Principal  
Pour pouvoir construire les objets Authentication chaque Provider d’authentification doit avoir un service pour charger les détails d’un Principal. Par exemple le DaoAuthenticationProvider doit accéder à une base de données, le LdapAuthenticationProvider doit accéder un annuaire LDAP...   


 - *UserDetailsService*
Spring security fournit une interface pour charger un utilisateur à partir de son identifiant principal, cette interface possède une seule méthode: ```UserDetails	loadUserByUsername(java.lang.String username)``` Spring fournit plusieurs implémentations : LdapUserDetailsService, InMemoryUserDetailsManager, JdbcDaoImpl... 

- *UserDetails*
Enregistre les informations du Principal qui sont ensuite encapsulées dans l’objet Authentication    

|  Méthode |   | 
|---|---|
|  java.util.Collection<? extends GrantedAuthority>	getAuthorities() | Renvoie les autorisations accordées à l'utilisateur  |
|  java.lang.String	getPassword() | Renvoie le mot de passe utilisé pour authentifier l'utilisateur.  |
|  java.lang.String	getUsername() | Renvoie le nom d'utilisateur utilisé pour authentifier l'utilisateur.  |
| boolean	isAccountNonExpired()  | Indique si le compte de l'utilisateur a expiré.  |
|  boolean	isAccountNonLocked() |Indique si l'utilisateur est verrouillé ou déverrouillé.   |
|   boolean	isCredentialsNonExpired()|  Indique si les informations d'identification de l'utilisateur (mot de passe) ont expiré. |
|  boolean	isEnabled() |  Indique si l'utilisateur est activé ou désactivé. |

- *User*  
La classe User représente l'implémentation fourni par Spring de l'interface UserDetails  

- *GrantedAuthority*  
Cette interface représente une autorité accordée à un Principal, Spring fournit une implémentation: SimpleGrantedAuthority où le rôle  est une simple chaîne de caractère. 

### Encodage de mots de passe
Généralement et pour des raisons de sécurité le mot de passe n'est pas stocké en clair. Pour chaque utilisateur seule une hache est sauvegardée. L’authentification se fait en comparant les mots de passe hachés. 
- *PasswordEncoder*  
Représente une interface pour le codage des mots de passe, l’implémentation recommandée par Spring est la classe BCryptPasswordEncoder 

![](https://github.com/AfifBouzidi/SPRING-SECURITY/blob/master/PasswordEncoder.PNG)  
ref: https://www.researchgate.net

### Context de sécurité
- *SecurityContextHolder*  
La responsabilité principale de cette classe est de stocker les détails du Principal en interaction avec l'application. Par exemple on peut utiliser le bloc de code suivant pour obtenir le nom de l’utilisateur actuellement authentifié : ```SecurityContextHolder.getContext().getAuthentication().getPrincipal()```  
L’accès au contexte de sécurité est thread-safe, le SecurityContextHolder utilise un ThreadLocal pour stocker les  détails, ce qui signifie que le contexte de sécurité est toujours disponible pour les méthodes du même thread d'exécution.  

- *SecurityContext*
Interface définissant les informations de sécurité associées au thread d'exécution en cours.

 ## L’autorisation
