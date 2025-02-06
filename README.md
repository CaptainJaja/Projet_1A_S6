
# 🚀 Projet 1A S6 - Robot Bluetooth

Ce projet consiste à concevoir un robot télécommandé via Bluetooth grâce à une application Android.

---

## 📌 Cahier des charges

### 🔹 Robot
- Communication **UART** avec le module **Bluetooth HC-06**  
- **Contrôle de deux moteurs DC** via un driver **TB6612FNG**  
- **Détection d'obstacle** via un capteur **ultrason HC-SR04**  
- **Affichage LED** pour état du robot ou esthétique  
- **Commande de deux servo-moteurs** pour actionner des éléments  

### 🔹 Application Android
- Interface graphique avec **boutons de commande**  
- **Connexion Bluetooth** pour envoyer des commandes au robot  

---

## ⚙️ Matériel utilisé

| Composant | Référence | Rôle |
|-----------|-----------|-----------|
| **Microcontrôleur** | STM32 (modèle à préciser) | Gestion des moteurs et communication Bluetooth |
| **Module Bluetooth** | HC-06 | Communication avec l’application Android |
| **Moteurs DC** | GA12-N20 (6V, 200 RPM) x2 | Déplacement du robot |
| **Driver moteur** | TB6612FNG | Pilotage des moteurs |
| **Batterie** | LiPo 7.4V (capacité à préciser) | Alimentation du système |
| **Régulateurs de tension** | 7.4V → 5V (R-78B5.0-2.0), 5V → 3.3V (BU33SD5WG-TR)| Alimentation des composants |
| **Capteur ultrason** | HC-SR04 | Détection d'obstacles |
| **Servomoteurs** | (modèle à préciser) x2 | Actionneurs divers |
| **LEDs** | 2x minimum | Indicateurs visuels |

---

## 🛠 Architecture du PCB

Notre PCB 4 couches comprend :
- Connecteurs pour tous les composants (moteurs, capteurs, etc.)
- **STM32** pour la gestion du robot
- **Deux régulateurs de tension** :
  - **7.4V → 5V** pour le module ultrason et les servomoteurs
  - **5V → 3.3V** pour le STM32 et le Bluetooth

---

## 🔄 Répartition des tâches

| Nom | Tâche principale |
|-----------|----------------|
| **CAPODAGI Janus** | Conception PCB et code STM32 |
| **BUI HAI Christophe** | Développement de l’application Android |
| **MILLON Raphaëlle** | Conception mécanique du robot |


---

## 📝 Remarques et pistes d'amélioration

- Intégrer un retour d'information via Bluetooth (ex: état batterie, distance d'obstacle)
- Ajouter un schéma de l'architecture du système
- Tester la durée de vie de la batterie pour ajuster la capacité si besoin

---

Ce README est susceptible d'être mis à jour au fur et à mesure de l'avancement du projet. 🚀



