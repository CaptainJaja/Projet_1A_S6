# 🚀 Projet 1A S6 - Robot Bluetooth + Odometrie

Ce projet consiste à concevoir un robot télécommandé via Bluetooth grâce à une application Android, avec odométrie, détection d'obstacles, et supervision ROS.

---

## 📌 Cahier des charges

### 🔹 Robot
- Communication **UART** avec le module **Bluetooth HC-05**  
- **Contrôle de deux moteurs DC à encodeurs** via un driver **TB6612FNG**  
- **Lecture des encodeurs** pour faire de l'**odométrie**  
- **Connexion à une Raspberry Pi** pour supervision et traitement via **ROS**  
- **Détection d'obstacle** via un capteur **ultrason HC-SR04**  
- **Module infrarouge** pour détection de ligne ou d'obstacles courts  
- **Affichage LED** pour état du robot ou esthétique  
- **Commande de deux servo-moteurs** pour actionner des éléments  

### 🔹 Application Android
- Interface graphique avec **boutons de commande**  
- **Connexion Bluetooth** pour envoyer des commandes au robot  

---

## ⚙️ Matériel utilisé

| Composant | Référence | Rôle |
|-----------|-----------|-----------|
| **Microcontrôleur** | STM32 (modèle à préciser) | Gestion des moteurs, capteurs, servo et communication Bluetooth |
| **Module Bluetooth** | HC-06 / HC-05 | Communication avec l’application Android |
| **Moteurs DC à encodeur** | GA12-N20 + encodeur magnétique | Déplacement du robot + odométrie |
| **Driver moteur** | TB6612FNG | Pilotage des moteurs |
| **Batterie** | LiPo 7.4V (capacité à préciser) | Alimentation du système |
| **Régulateurs de tension** | 7.4V → 5V (R-78B5.0-2.0), 5V → 3.3V (BU33SD5WG-TR)| Alimentation des composants |
| **Capteur ultrason** | HC-SR04 | Détection d'obstacles |
| **Module infrarouge** | (modèle à préciser) | Détection de ligne ou d'obstacle proche |
| **Servomoteurs** | (modèle à préciser) x2 | Actionneurs divers |
| **LEDs** | 2x minimum | Indicateurs visuels |
| **Raspberry Pi** | (modèle à préciser) | Exécution de ROS et traitement haut niveau |

---

## 🛠 Architecture du PCB

Notre projet comporte **deux versions de PCB** :

### V1 : Prototype de base
- Connecteurs moteurs, servo, HC-SR04, HC-05 ; Driver ; STM
- Pas d'encodeur, ni module infrarouge, ni connecteurs pour la Raspberry Pi

### V2 : Version finale
- Connecteurs pour les **encodeurs**, la **Raspberry Pi**, et le **module infrarouge** ajoutés
- Circuits d'alimentation optimisés pour alimentation partagée

---

## ⏳ Tableau d'avancement du projet

| Séance | Avancement                                                                                                                                                                                                           |
|--------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Séance 1 | Répartition des tâches (modélisation 3D / code + PCB / appli Android), choix des composants                                                                                                                          |
| Séance 2 | - Début du PCB V1 (sans encodeur, Raspberry, infrarouge) <br>- Début application Android <br>- Début modélisation mécanique                                                                                          |
| Séance 3 | - Finalisation du PCB V1 <br>- 1ère version de l'application (nombreuses fonctionnalités manquantes)<br>-la conception 3D continue d'avancer                                                                         |
| Séance 4 | - Début des tests du **module infrarouge** <br>- Implémentation des fonctionnalités mais beaucoup d'erreurs <br>-changement des dimensions de la structure 3D car changement d'agencement des éléments dans le robot |
| Séance 5 | Finalisation de la **commande à distance** (prototype robot avec HC-05)<br>-la conception 3D continue d'avancer                                                                                                      |
| Séance 6 | - Début du PCB V2 (ajout Raspberry, encodeurs, infrarouge) <br>- Plus d'erreurs grâce aux bonnes permissions demandées <br>- début de l'impression 3D                                                                |
| Séance 7 | Finalisation du modèle du PCB V2, **commande des composants et du PCB** <br>-fin de l'impression 3D                                                                                                                  |
| Séance 8 | Finalisation du modèle 3D, montage PCB et robot prototype, test |
| Séance 9 | Corrections et impressions 3D, montage du robot final |
---

## 📋 Répartition des tâches

| Nom | Tâche principale                                                                                                                                                       |
|-----------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| **CAPODAGI Janus** | Conception PCB (https://github.com/CaptainJaja/Projet_1A_S6/tree/main/Hardware/PCB),  code STM32 (https://github.com/CaptainJaja/Projet_1A_S6/tree/main/Software/Code) |
| **BUI HAI Christophe** | Développement de l’application Android (https://github.com/CaptainJaja/Projet_1A_S6/tree/main/Application)                                                             |
| **MILLON Raphaëlle** | Conception mécanique du robot :modélisation 3D, intégration Raspberry (https://github.com/CaptainJaja/Projet_1A_S6/tree/main/Hardware/3D)                              |

---

## 📝 Remarques et pistes d'amélioration

- Ajouter un schéma de l'architecture du système (STM32 + Raspberry + ROS)
- Tester la communication entre STM32 et Raspberry via UART 

---

Ce README est susceptible d'être mis à jour au fur et à mesure de l'avancement du projet. 🚀