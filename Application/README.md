# Contrôle d'une Voiture Télécommandée via Bluetooth

## Introduction

Nous souhaitons programmer une application sur **Android Studio** afin de contrôler une voiture télécommandée via **Bluetooth** depuis un smartphone.

---

## Difficultés

- **Android Studio** utilise trois langages de programmation : **Kotlin, Java et XML**.
- Les fichiers présents sur GitHub contiennent un programme en **Kotlin** permettant d’établir une connexion Bluetooth.
- Cependant, ce programme comporte des **erreurs** que je n’arrive pas à corriger (ne connaissant pas Kotlin).
- Par conséquent, j’ai décidé de configurer **Android Studio** pour ne programmer qu’en **Java**.

---

## Mise en place du code Java

Sur un **nouveau projet vierge** :

### 1️⃣ Remplacement du fichier MainActivity
📂 **Chemin du fichier :**  
`D:\Projets\Formation_Android_Studio\app\src\main\java\com\example\formation`

![Fichier](Fichier.png)


✅ **Modification :**  
- Remplacement du fichier **MainActivity.kt** par une classe **Java**.


### 2️⃣ Modification du fichier activity_main.xml
📂 **Chemin du fichier :**  
`D:\Projets\Formation_Android_Studio\app\src\main\res\layout\activity_main.xml`



🖼️ **Objectif :**  
- Ajuster automatiquement l’interface utilisateur aux dimensions de l’appareil.
- Le fichier `activity_main.xml` gère la **mise en page de l’application**.

![activity_main.xml](xml.png)

### 3️⃣ Configuration du fichier AndroidManifest.xml
📂 **Chemin du fichier :**  
`D:\Projets\Formation_Android_Studio\app\src\main\AndroidManifest.xml`

📜 **Modifications :**  
- Déclaration de **MainActivity** comme **activité principale** de l’application.

 
![AndroidManifest.xml](Manifest.png)

---

## Travail à faire ✅

🔹 **Se familiariser** avec **Android Studio**  
🔹 **Implémenter** le programme de **connexion Bluetooth en Java**  

---