# Test du module Bluetooth HC-05 avec une STM32 Nucleo L476RG

## 📌 Introduction
Ce projet teste la communication entre une STM32 Nucleo L476RG et un module HC-05 via Bluetooth.  
L'objectif est d'envoyer et de recevoir des données en UART entre le PC et la carte STM32.

## 🔧 Configuration matérielle et logicielle

### 📍 Matériel utilisé
- **Carte STM32** : Nucleo L476RG  
- **Module Bluetooth** : HC-05  
- **Communication UART** :  
  - UART4 (STM32) ⇄ HC-05 (9600 bits/s)  
  - UART2 (STM32) ⇄ PC (115200 bits/s) pour affichage sur CubeIDE  
- **Logiciels utilisés** :  
  - STM32CubeIDE  
  - PuTTY (pour la communication avec le port COM Bluetooth)  

### 🔌 Connexions HC-05 vers STM32
| HC-05 | STM32 Nucleo L476RG |
|-------|---------------------|
| VCC   | 3.3V ou 5V         |
| GND   | GND                |
| TXD   | RX (UART4)         |
| RXD   | TX (UART4)         |

## ⚙️ Configuration de PuTTY

Une fois le module HC-05 appairé avec le PC, il est nécessaire de configurer PuTTY pour établir la communication série :
- **Port série** : COM9 (varie selon le PC)  
- **Baud rate** : 9600  
- **Data bits** : 8  
- **Parity** : None  
- **Stop bits** : 1  

Paramétrage de PuTTY :  

![Configuration de PuTTY](test_HC05_3.png)

## 🛠️ Implémentation du code STM32
Le code STM32 utilise la bibliothèque HAL pour gérer la communication UART avec le HC-05.  
Extrait du code utilisé :

```c
while (1)
{
    // Recevoir des données du HC05 via UART2
    if (HAL_UART_Receive(&huart4, (uint8_t*)&received_data, 1, 1000) == HAL_OK)
    {
        // Afficher le caractère reçu avec printf (via UART2)
        printf("Caractère reçu : %c\n\r", received_data);
    }
}
```

## 🔬 Procédure de test
1. Alimenter la STM32 et connecter le HC-05.  
2. Associer le module HC-05 au PC via Bluetooth.  
3. Ouvrir **PuTTY** et se connecter au port COM9 à 9600 bauds.  
4. Envoyer des caractères via PuTTY.  
5. Observer la réception des caractères dans **STM32CubeIDE** via UART2 (115200 bauds).  

## 📊 Résultat du test
Après l'envoi de caractères depuis PuTTY, ceux-ci sont correctement reçus et affichés sur la console de STM32CubeIDE :  

### Connexion avec le module HC-05 via le port COM9 dans PuTTY :  
![Console dans CubeIDE](test_HC05_2.png)

### Réception des caractères sur STM32CubeIDE :  
![Résultat du test](test_HC05.png)

## 📌 Conclusion
Le test démontre que la communication entre le HC-05 et la STM32 fonctionne correctement via UART.  
Le module HC-05 reçoit et transmet bien les données entre la carte et le PC, permettant ainsi un échange bidirectionnel efficace.  

