```
Professeur : Nastaran Fatemi
Assistants : Christopher Meier
Edward Ransome
```
# Labo 1 : Couchbase

## 1 Introduction

### 1.1 Objectif

L’objectif de ce laboratoire est d’exercer _Couchbase_ et son langage de requête N1QL.

### 1.2 Organization

Ce laboratoire est à effectuer **par groupe de 2** étudiants. Tout plagiat sera sanctionné par la note de 1.

Ce laboratoire est à rendre pour le **14.03.2022** à 23h59 sur Cyberlearn, une archive zip contenant les sources
de votre projet doit y être déposée.

### 1.3 Mise en place

Le code source du labo vous est fourni sur Cyberlearn. Celui-ci contient:

- Un fichier pom.xml qui configure les dépendances du projet Maven.
- Un fichier docker-compose.yml qui permet le démarrage simple^1 d’une instance de couchbase. Afin
    de simplifier la mise en place du dataset une extension de l’image docker officielle vous est fournie.
- Un fichier Main.java qui se connecte à la base de donnée Couchbase et exécute les requêtes de
    Requests.java.
- Un fichier QueryOutputFormatTest qui permet de vérifier que vous avez le bon format d’output pour
    les requêtes. (Lancer les tests avant le rendu pour simplifier les tests automatiques de la correction)

**Ainsi que 2 fichiers à compléter** :

- Un fichier Requests.java.
- Un fichier Indices.java à compléter si un nouvel index est nécessaire pour l’exécution d’une requête.

Lors du premier lancement, configurer Couchbase à partir de son interface Web (disponible à l’adressehttp:
//localhost:8091). Les valeurs par défaut sont suffisantes. Vous pouvez utiliser les valeurs suivantes pour
les champs qui n’ont pas de défaut :

**Cluster Name** MAC
**Admin Username** Administrator
**Password** mac

### 1.4 Dataset

Ce laboratoire utilise un dataset appelé mflix-sample. Son installation se fait depuis l’interface Web à la

page Settings > Sample Buckets.

Le dataset s’installe dans le bucket mflix-sample contient un scope _default qui contient 4 collections:

```
comments , movies , theaters et users
```
(^1) Si vous souhaiter installer Couchbase sans utiliser docker, contacter l’assistant pour récupérer le dataset et les instructions de
configuration.


```
Professeur : Nastaran Fatemi
Assistants : Christopher Meier
Edward Ransome
```
```
Prenez le temps de vous familiariser avec la structures des différents documents (avec par exemple la clause
INFER ainsi qu’avec le schéma pdf fournit en annexe) ainsi qu’avec les index qui sont déjà créés.
```
## 2 Connexion

```
Vérifiez que vous pouvez vous connecter en exécutant la classe Main. Les noms des collections de mflix-sample
devraient s’afficher dans votre console. Si besoin modifier la méthode openConnection.
```
## 3 Requêtes

Dans les requêtes utiliser le nom original des champs. Renommer uniquement lorsque c’est indiqué entre
parenthèse.

_⋆_ 1. Implémenter la méthode inconsistentRating pour retourner les films ( imdb_id , tomatoes_rating ,
imdb_rating ) dont le rating tomatoes n’est pas zéro et dont l’écart entre le rating imdb et tomatoes
est plus que 7. Utiliser l’attribut tomatoes.viewer.rating comme le rating tomatoes.

_⋆_ 2. Implementer la méthode hiddenGem pour retourner le titre des films parfaitement notés (10) par les
critiques tomatoes mais qui n’ont pas été notés par les viewers tomatoes.

_⋆_ 3. Implémenter la méthode topReviewers pour retourner le nom des 10 personnes ayant fait le plus de
commentaires ainsi que le nombre de leurs commentaires ( cnt ).

_⋆_ 4. Implémenter la méthode greatReviewers pour retourner le nom des personnes ayant fait plus de 300
commentaires. Le résultat de la requête doit être une liste de String.

_⋆⋆_ 5. Implémenter la méthode bestMoviesOfActor pour retourner l’identifiant IMDB ( imdb_id ), le rating
imdb et le casting des films où le rating IMDB est un numéro plus grand que 8 et dans lequel un acteur
donné (par exemple "Ralph Fiennes") joue.

_⋆⋆_ 6. Implémenter la méthode plentifulDirectors pour retourner le nom et le nombre de films des di-
recteurs ( director_name , count_film ) ayant dirigé plus que 30 films.

_⋆⋆_ 7. Implémenter la méthode confusingMovies pour retourner l’id ( movie_id) et le titre des films qui
ont plus de 20 directeurs:

_⋆⋆_ 8. Implémenter les méthodes commentsOfDirector1 et commentsOfDirector2 qui implémentent 2
manières différentes pour retourner le text (doit être de typestringet non pas de typearray) de tous
les commentaires sur tous les films ( movie_id) dirigés par un directeur donné (par exemple "Woody
Allen").

_⋆⋆⋆_ 9. Implémenter la méthode removeEarlyProjection qui permet de mettre à jour la collection afin de
supprimer les projections d’un film donné avant 18h. La méthode retourne le nombre de mise à jour
effectuée.

_⋆⋆⋆_ 10.(Bonus) Implémenter la méthode nightMovies pour retourner les films qui sont projeté uniquement à
partir de 18h.


```
Professeur : Nastaran Fatemi
Assistants : Christopher Meier
Edward Ransome
```
## A Questions fréquentes

**Je n’ai pas suffisement de mémoire vive lorsque je veux configurer le cluster**
À la place d’utiliser "Finish With Defaults", cliquer sur "Configure Disk, Memory, Services" puis désactiver
les "Service Memory Quotas" pour "Analytics" et "Eventing".

**Je ne veux pas utiliser docker**
Il n’est pas obligatoire d’utiliser docker, mais c’est à vous de regler les soucis d’installations éventuels.
Une fois Couchbase installé, il faut copier le fichier mflix-sample.zip dans le dossier samples de
votre installation.


