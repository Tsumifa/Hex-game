# Projet Licence 1 Semestre 2 UPEC: Jeu du Hex

**Auteurs:**
> HODSON thomas - DLMI - u32112586

> ALAMI Adam - DLMI - n°etu


---

**Langage et librairies:**
> [Java 17](https://openjdk.java.net/projects/jdk/17/)

> Java Swing : Inclue dans le JDK (librairie mère de Java Simple Graphics)

> [Java Developpement Kit For Visual Studio Code](https://openjdk.java.net/projects/jdk/17/)

> [Json Simple](https://code.google.com/archive/p/json-simple/downloads)
---

**Editeur:**
> Visual Studio Code

---

**Sources:**
> [Bouttons Hexagones](https://harryjoy.me/2011/08/21/different-button-shapes-in-swing/)


# Structure du projet

Le langage est composé des packages suivants:
- ***src.graphics*** : Ensemble des fichiers dédiés à l'affichage.
- ***src.ia*** : Ensemble des fichiers de l'IA.
- ***src.game*** : Ensemble des fichiers liés au jeu.
- ***src.config*** : Ensemble des fichiers liés aux paramètres.


for(int i = 0; i < edges; i++) {
            if ( i == 0 ) {
                double v = (i+1)*angle;
                    x[i] = x0 + (int) Math.round( (getPreferredSize().width / 2) * Math.cos(v) );
                    y[i] = y0 + (int) Math.round( (getPreferredSize().height / 2) * Math.sin(v) );
                }
            else {
                double v = i*angle;
                x[i] = x0 + (int) Math.round( (getPreferredSize().width / 2) * Math.cos(v) );
                y[i] = y0 + (int) Math.round( (getPreferredSize().height / 2) * Math.sin(v) );}
        }