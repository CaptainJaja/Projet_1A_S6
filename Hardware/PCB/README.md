
# 🚀 Robot PCB - STM32 + HC-05 + Motor Driver  

Ce projet est un PCB conçu pour contrôler un robot suiveur de ligne avec obstacle, utilisant un **STM32L552CCTx** et un module **Bluetooth HC-05** pour la communication. Il comprend un **driver moteur TB6612FNG**, plusieurs GPIO et connecteurs pour capteurs.

## 📸 Aperçu du PCB
### **Face Avant**
![Front PCB](c:\PROJET ENSEA S6\Projet_1A_S6\Hardware\PCB\image.png)
### **Face Arrière**
![Back PCB](c:\PROJET ENSEA S6\Projet_1A_S6\Hardware\PCB\image-1.png)

## 📦 Composants intégrés
- **Microcontrôleur :** STM32L552CCTx
- **Communication :** HC-05 (Bluetooth, connecté en UART au MCU)
- **Contrôle des moteurs :** TB6612FNG (Driver moteur DC)
- **Indicateur d'état Bluetooth :** LED
- **Interface GPIO :** Rangée **3x4 pinheader** (GPIO-3.3V-GND)
- **Connectique :**
  - 2x **Connecteurs JST** pour servomoteurs
  - 2x **Connecteurs JST** pour moteurs DC
  - 1x **Connecteur JST** pour module infrarouge
  - 1x **Connecteur JST** pour alimentation
  - 1x **Connecteur ST-LINK** pour programmation/debug
- **Alimentation :**
  - **Régulateur 7.5V → 5V**
  - **Régulateur 5V → 3.3V**
- **Boutons poussoirs :**
  - **Reset du MCU**
  - **Reset du module Bluetooth**
- **Passifs :** Condensateurs de découplage, bobine, résistances (**SMD**)




