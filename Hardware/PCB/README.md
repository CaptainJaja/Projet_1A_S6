
# 🚀 Robot PCB - STM32 + HC-05 + Motor Driver  

Ce projet est un PCB conçu pour contrôler un robot suiveur de ligne avec obstacle, utilisant un **STM32L552CCTx** et un module **Bluetooth HC-05** pour la communication. Il comprend un **driver moteur TB6612FNG**, plusieurs GPIO et connecteurs pour capteurs.

## 📸 Aperçu du PCB version 1 (moteur sans encodeur, pas de connexion raspberry)
### **Face Avant**
![Front PCB](image-2.png)
### **Face Arrière**
![Back PCB](image-1-1.png)

## 📸 Aperçu du PCB version 2 (+moteur avec encodeur, +connexion raspberry (UART), +Module infrarouge)
### **Face Avant**
![Front PCB](image-3.png)
### **Face Arrière**
![Back PCB](image-4.png)

## 📦 Composants intégrés (version finale : V2)
- **Microcontrôleur :** STM32L552CCTx
- **Communication (PinHeader 01x04) :** HC-05 (Bluetooth, connecté en UART au MCU)
- **Contrôle des moteurs :** TB6612FNG (Driver moteur DC)
- **Indicateur d'état Bluetooth :** LED
- **Interface GPIO :** Rangée **3x4 pinheader** (GPIO-3.3V-GND)
- **Connectique :**
  - 2x **Connecteurs JST 01x03** pour servomoteurs
  - 2x **Connecteurs JST 01x06** pour moteurs DC
  - 1x **Connecteur JST 01x06** pour module infrarouge
  - 1x **Connecteur JST 01x02** pour alimentation
  - 1x **Connecteur ST-LINK** pour programmation/debug
  - 1x **Connecteur PinHeader 01x02** pour connexion UART avec raspberry
  - 1x **Connecteur PinHeader 02x03** pour connexion avec les LEDs
  - 1x **Connecteur PinHeader 01x02** pour connecter les masses STM/Raspberry
- **Alimentation :**
  - **Régulateur 7.5V → 5V**
  - **Régulateur 5V → 3.3V**
- **Boutons poussoirs :**
  - **Reset du MCU**
- **Passifs :** Condensateurs de découplage, bobine, résistances (**SMD**)




