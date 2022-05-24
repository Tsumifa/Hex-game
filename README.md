 # DOCUMENTATION PROJET JAVA : HEX

**Introduction ( [Wikipédia](https://fr.wikipedia.org/wiki/Hex) ):**
Le jeu de Hex est un jeu de société combinatoire abstrait pour deux joueurs. Il se joue sur un tablier en forme de losange dont les cases sont hexagonales. Le joueur 1 (respectivement le joueur 2) doit créer  un chemin continu entre le coté gauche et le coté droit (respectivement le coté haut et le coté bas) en plaçant chacun leur tour un jeton sur le plateau. Il ne peut pas y avoir d'égalité.

Un des objectifs de ce projet était de déterminer si un réseau de neurones de proportion raisonnable (capable d'être exécuté sur un ordinateur standard sans temps de calcul ressentit) peut jouer de manière intelligente à ce jeu. Après plusieurs tentatives, le réseau de neurones s'avère être légèrement plus efficace que le placement aléatoire mais n'est absolument pas capable d'affronter un humain.    

 >  Auteurs : **Thomas HODSON** & **Adam ALAMI**
 > [Université Paris Est Créteil : Faculté des Sciences et Technologies](https://www.u-pec.fr/)
 > Poster le : 22-05-2022
 > Langage : [Java](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
 > Éditeur : [Visual Studio Code](https://code.visualstudio.com/)
 > Images: [Flaticon](https://www.flaticon.com/fr/)

**Librairie:**
- [JsonSimple](https://code.google.com/archive/p/json-simple/) - Lecture de données enregistrées dans un fichier JSON.
- Les autres librairies sont présentes dans le [JDK-17](https://openjdk.java.net/projects/jdk/17/) (ex: [Swing](https://fr.wikipedia.org/wiki/Swing_(Java)), ...). 

**Sources:**
- [Bouttons Hexagones](https://harryjoy.me/2011/08/21/different-button-shapes-in-swing/)
- [Code de base de l'IA](https://github.com/StudioTV/AI/blob/master/flower_ai.py)

## INSTALLATION ET CONFIGURATION

Télécharger le projet sur [Github](https://github.com/Tsumifa/Hex-game) et ajouter la librairie [JsonSimple](https://code.google.com/archive/p/json-simple/) aux librairies du projet installation Java. Ceci s'effectue en ajoutant le fichier au dossier *Referenced Librairies* qui se trouve en bas dans l'onglet *JAVA PROJECTS* (voir image ci-dessous).

![installation](https://lh3.google.com/u/0/d/1myPeGhSUEp3RUX49mv3njefwx3xWizhk=w3840-h1847-iv1)

Pour configurer le projet, ouvrez le fichier */conf/settings.json*. Ce fichier contient l'ensemble des informations dont a besoin le programme pour fonctionner. Le fichier est le suivant:
```json
{
	"consoleDebug": "true",
	"fileDebug": "true",
	"language": "fr",
	"nonce" : "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789",
	"serverAddress": "https://<reste de la base de l'URL>",
	"channelPostName": "ChatTest",
	"channelGetName": "ChatTest",
	"channelScanDelay": "3000",
	"winLeftToRightSide": "1",
	"winTopToBottomSide": "2",
	"frameWidth": "1080",
	"frameHeight": "800",
	"frameResizable": "false",
	"frameBackgroundColor": "WHITE",
	"title": "HexGame",
	"btnSize": "40",
	"boardSize": "11",
	"playerOneColor": "RED",
	"playerTwoColor": "BLUE",
	"borderTopBackgroundColor": "RED",
	"borderTopBorderColor": "RED",
	"borderBottomBackgroundColor": "RED",
	"borderBottomBorderColor": "RED",
	"borderLeftBackgroundColor": "BLUE",
	"borderLeftBorderColor": "BLUE",
	"borderRightBackgroundColor": "BLUE",
	"borderRightBorderColor": "BLUE",
	"cornerBackgroundColor": "BLACK",
	"titleColor": "BLACK",
	"titleFontFamily": "Consolas",
	"titleFontSize": "70",
	"titleFontStyle": "PLAIN",
	"titlePosition": "CENTER",
	"alignTitle": "CENTER",
	"helperFontSize": "20",
	"helperFontStyle": "ITALIC",
	"helperFontFamily": "Consolas",
	"alignHelper": "CENTER",
	"helperPosition": "SOUTH",
	"menuMultiPlayerBackgroundColor": "DARK_GRAY",
	"menuMultiPlayerTextColor": "WHITE",
	"menuMultiPlayerFontFamily": "Consolas",
	"menuMultiPlayerFontSize": "50",
	"menuMultiPlayerFontStyle": "PLAIN",
	"menuSoloEasyBackgroundColor": "RED",
	"menuSoloEasyTextColor": "WHITE",
	"menuSoloEasyFontFamily": "Consolas",
	"menuSoloEasyFontSize": "30",
	"menuSoloEasyFontStyle": "PLAIN",
	"menuSoloHardBackgroundColor": "BLUE",
	"menuSoloHardTextColor": "WHITE",
	"menuSoloHardFontFamily": "Consolas",
	"menuSoloHardFontSize": "30",
	"menuSoloHardFontStyle": "PLAIN",
	"menuAiVsAiBackgroundColor": "BLUE",
	"menuAiVsAiTextColor": "WHITE",
	"menuAiVsAiFontFamily": "Consolas",
	"menuAiVsAiFontSize": "30",
	"menuAiVsAiFontStyle": "PLAIN",
	"menuAiVsAiOnlineBackgroundColor": "RED",
	"menuAiVsAiOnlineTextColor": "WHITE",
	"menuAiVsAiOnlineFontFamily": "Consolas",
	"menuAiVsAiOnlineFontSize": "30",
	"menuAiVsAiOnlineFontStyle": "PLAIN",
	"boardPosition": "CENTER",
	"tileBorderColor": "BLACK",
	"tileColor": "LIGHT_GRAY",
	"winnerTextFontFamily": "Consolas",
	"winnerTextFontSize": "50",
	"winnerTextFontStyle": "PLAIN",
	"winnerTextBackgroundColor": "WHITE",
	"subMenuTitleColor": "BLACK",
	"subMenuTitleBackgroundColor": "WHITE",
	"subMenuTitleFontFamily": "Consolas",
	"subMenuTitleFontSize": "50",
	"subMenuTitleFontStyle": "PLAIN",
	"subMenuPlayerOneBtnBackgroundColor": "RED",
	"subMenuPlayerOneBtnColor": "WHITE",
	"subMenuPlayerOneBtnFontFamily": "Consolas",
	"subMenuPlayerOneBtnFontSize": "30",
	"subMenuPlayerOneBtnFontStyle": "PLAIN",
	"subMenuPlayerTwoBtnBackgroundColor": "BLUE",
	"subMenuPlayerTwoBtnColor": "WHITE",
	"subMenuPlayerTwoBtnFontFamily": "Consolas",
	"subMenuPlayerTwoBtnFontSize": "30",
	"subMenuPlayerTwoBtnFontStyle": "PLAIN",
	"neuralNetworkInputSize": "121",
	"neuralNetworkOutputSize": "121",
	"availableColors": ["RED", "BLUE", "GREEN", "BLACK", "YELLOW", "WHITE", "PINK", "ORANGE", "CYAN", "DARK_GRAY", "GRAY", "LIGHT_GRAY", "MAGENTA"],
	"availableFontStyles": ["ITALIC", "PLAIN", "BOLD", "BOLD ITALIC"],
	"availableFontFamilies": [ < les polices> ]
}
```
Quelques détails sur les paramètres:

| CLÉ| DESCRIPTION | VALEURS ATTENDUES  |
|--|--|--|
| ***"consoleDebug"*** | Active ou désactive l'affichage de messages dans la console | "true" ou "false" |
| ***"fileDebug"*** | Active ou désactive l'écriture de messages dans le fichier */res/logs/logs.txt* | "true" ou "false" |
| ***"language"*** | Définit la langue utilisée par le programme | "en" ou "fr" ou "es" |
| ***"nonce"*** | Clé aléatoire utilisée pour l'envoi et la lecture de messages sur padiflac | String |
| ***"serverAddress"*** | Base de l'URL pour contacter un serveur padiflac | "baseURL" |
| ***"channelPostName"*** | Nom du channel utilisé pour poster les messages | String |
| ***"channelGetName"*** | Nom du channel utilisé pour récupérer les messages | String |
| ***"channelScanDelay"*** | Temps (en millisecondes) d'attente du récupérateur de message avant de retourner le dernier message | "int" |
| ***"winLeftToRightSide"*** | Détermine quel joueur gagne en rejoignant le coté droit et le coté gauche. | "1" ou "2" différent de "winTopToBottomSide" |
| ***"winTopToBottomSide"*** | Détermine quel joueur gagne en rejoignant le coté haut et le coté bas. | "1" ou "2" différent de "winLeftToRightSide" |
| ***"frameWidth"*** | Largeur de la fenêtre en pixels | "int" |
| ***"frameHeight"*** | Hauteur de la fenêtre en pixels | "int" |
| ***"frameResizable"*** | Définit si l'utilisateur peut modifier la taille de la fenêtre avec son curseur | "true" ou "false" |
| ***"title"*** | Le titre de la fenêtre | String |
| ***"btnSize"*** | La taille des cases du plateau en pixels | "int" |
| ***"boardSize"*** | Le nombre de lignes et de colonnes que contient le plateau | "int" |
| ***"neuralNetworkInputSize"*** | Nombres de neurones de la couche d'injection de l'intelligence artificielle | "int" |
| ***"neuralNetworkOutputSize"*** | Nombres de neurones de la couche de sortie de l'intelligence artificielle | "int" |
| ***Les éléments de la forme "...Colors"*** | Définit une couleur (de fond ou de texte) | Un des éléments de la liste "availableColors" |
| ***Les éléments de la forme "...FontSize"*** | Définit la taille de la police de l'élément | "int" |
| ***Les éléments de la forme "...FontFamily"*** | Défnit la police d'un élément" | Un des éléments de la liste "availableFontFamilies" |
| ***Les éléments de la forme "...FontStyle"*** | Définit le style d'un texte (ex: normal, gras, ...) | Un des éléments de la liste "availableFontStyles" |


## JOUER

Ce jeu dispose de plusieurs modes:

| MODE | DESCRIPTION |
|--|--|
| Multijoueur | Partie joueur VS joueur |
| Solo : facile | Partie joueur VS placement aléatoire |
| Solo : difficile | Partie joueur VS réseau de neurones |
| IA VS IA  | Le réseau de neurones s'affronte lui même |
| IA VS IA en ligne  | Affrontement du réseau de neurones et d'une IA adverse sur un serveur padiflac |

Pour jouer, lancez le programme. Une première fenêtre s'ouvre :
![Première fenêtre](https://lh3.google.com/u/0/d/12oQshCdyLK54w9LXi2vbIa8s9FLgRFsO=w2560-h1315-iv1)

Si vous sélectionnez les modes : *1 joueur : facile*, *1 joueur difficile* et *IA contre IA*, une seconde fenêtre s'ouvre :
![Deuxième fenêtre](https://lh3.google.com/u/0/d/1Aw7INq-FjzO-gaYZZQZFEOjgqU7KPUaE=w1731-h1315-iv1)

Enfin une dernière fenêtre s'ouvre et vous pouvez jouer ! 
![enter image description here](https://lh3.google.com/u/0/d/1Hnp4bEERoESzbsyBYFhDzI3lIhTW-OII=w1731-h1315-iv1)

## FONCTIONNEMENT 

### Structure

Le projet est divisé en trois répertoires :
- Le répertoire */conf* qui contient le fichier de configuration *settings.json*.
- Le répertoire */res* qui contient les ressources utilisées par le programme:
	- */img* : les images.
	- */data*: les poids des transitions inter-neurones de l'intelligence artificielle.
	- */i18n*: les  fichiers de langue.
	- */logs*: les logs du programme.
- Le répertoire */src* qui contient les fichiers java.   

### Les packages

Pour faciliter la maintenance du code, celui-ci est divisé en plusieurs packages:
| NOM | FONCTION(S) |
|--|--|
| ***network*** | Gestion de la communication avec le serveur padiflac. |
| ***graphics*** | Gestion des fenêtres. |
| ***components*** | Composants graphiques customisés. |
| ***game*** | Gestion Backend du jeu. |
| ***ia*** | Intelligence artificielle. |
| ***debug*** | Gestion des messages de debug. |
| ***config*** | Gestion des langues et du fichier de configuration. |

## Ajouter des langues

Pour ajouter des langues, il suffit de créer un fichier json nommé avec le pattern suivant: <nom_ extension>.json (ex: *fr.json*, *en.json*, *es.json*, ...). Pour déterminer le nom, vous pouvez consulter ce [lien](https://fr.wikipedia.org/wiki/Liste_des_codes_ISO_639-1). Il suffit ensuite de copier coller la base suivante dans votre fichier et de placer les textes entre les guillemets : 
```json
{
	"title": "",
	"playerOneTurnText": "",
	"playerTwoTurnText": "",
	"playerOneWinText": "",
	"playerTwoWinText": "",
	"multiplayer": "",
	"iavsia": "",
	"iavsiaOnline": "",
	"soloEasy": "",
	"soloHard": "",
	"ChoosePlayer": "",
	"playerOne": "",
	"playerTwo": ""
}
```
Il ne reste plus qu'à placer le fichier dans le dossier */res/i18n/* et mettre le nom du fichier dans le fichier de configuration */conf/settings.json*.
Attention, certains caractères ne sont pas reconnus ! Par exemple, le chinois, l'arabe et le russe ne sont pas interprétés par le programme.
