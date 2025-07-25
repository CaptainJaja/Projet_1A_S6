/* USER CODE BEGIN Header */
/**
  ******************************************************************************
  * @file           : main.h
  * @brief          : Header for main.c file.
  *                   This file contains the common defines of the application.
  ******************************************************************************
  * @attention
  *
  * Copyright (c) 2025 STMicroelectronics.
  * All rights reserved.
  *
  * This software is licensed under terms that can be found in the LICENSE file
  * in the root directory of this software component.
  * If no LICENSE file comes with this software, it is provided AS-IS.
  *
  ******************************************************************************
  */
/* USER CODE END Header */

/* Define to prevent recursive inclusion -------------------------------------*/
#ifndef __MAIN_H
#define __MAIN_H

#ifdef __cplusplus
extern "C" {
#endif

/* Includes ------------------------------------------------------------------*/
#include "stm32l5xx_hal.h"

/* Private includes ----------------------------------------------------------*/
/* USER CODE BEGIN Includes */

/* USER CODE END Includes */

/* Exported types ------------------------------------------------------------*/
/* USER CODE BEGIN ET */

/* USER CODE END ET */

/* Exported constants --------------------------------------------------------*/
/* USER CODE BEGIN EC */

/* USER CODE END EC */

/* Exported macro ------------------------------------------------------------*/
/* USER CODE BEGIN EM */

/* USER CODE END EM */

void HAL_TIM_MspPostInit(TIM_HandleTypeDef *htim);

/* Exported functions prototypes ---------------------------------------------*/
void Error_Handler(void);

/* USER CODE BEGIN EFP */

/* USER CODE END EFP */

/* Private defines -----------------------------------------------------------*/
#define LED1_Pin GPIO_PIN_13
#define LED1_GPIO_Port GPIOC
#define LED2_Pin GPIO_PIN_14
#define LED2_GPIO_Port GPIOC
#define LED3_Pin GPIO_PIN_15
#define LED3_GPIO_Port GPIOC
#define HC05_RX_Pin GPIO_PIN_0
#define HC05_RX_GPIO_Port GPIOA
#define HC05_TX_Pin GPIO_PIN_1
#define HC05_TX_GPIO_Port GPIOA
#define X1_Pin GPIO_PIN_4
#define X1_GPIO_Port GPIOA
#define X2_Pin GPIO_PIN_5
#define X2_GPIO_Port GPIOA
#define X3_Pin GPIO_PIN_6
#define X3_GPIO_Port GPIOA
#define X4_Pin GPIO_PIN_7
#define X4_GPIO_Port GPIOA
#define ECHO_Pin GPIO_PIN_0
#define ECHO_GPIO_Port GPIOB
#define TRIG_Pin GPIO_PIN_1
#define TRIG_GPIO_Port GPIOB
#define Raspb_RX_Pin GPIO_PIN_10
#define Raspb_RX_GPIO_Port GPIOB
#define Raspb_TX_Pin GPIO_PIN_11
#define Raspb_TX_GPIO_Port GPIOB
#define Servo1_Pin GPIO_PIN_14
#define Servo1_GPIO_Port GPIOB
#define Servo2_Pin GPIO_PIN_15
#define Servo2_GPIO_Port GPIOB
#define EncodeurA1_Pin GPIO_PIN_8
#define EncodeurA1_GPIO_Port GPIOA
#define EncodeurA2_Pin GPIO_PIN_9
#define EncodeurA2_GPIO_Port GPIOA
#define AIN2_Pin GPIO_PIN_11
#define AIN2_GPIO_Port GPIOA
#define AIN1_Pin GPIO_PIN_12
#define AIN1_GPIO_Port GPIOA
#define EncodeurB1_Pin GPIO_PIN_4
#define EncodeurB1_GPIO_Port GPIOB
#define EncodeurB2_Pin GPIO_PIN_5
#define EncodeurB2_GPIO_Port GPIOB
#define PWMA_Pin GPIO_PIN_6
#define PWMA_GPIO_Port GPIOB
#define STBY_Pin GPIO_PIN_7
#define STBY_GPIO_Port GPIOB
#define BIN1_Pin GPIO_PIN_3
#define BIN1_GPIO_Port GPIOH
#define BIN2_Pin GPIO_PIN_8
#define BIN2_GPIO_Port GPIOB
#define PWMB_Pin GPIO_PIN_9
#define PWMB_GPIO_Port GPIOB

/* USER CODE BEGIN Private defines */

/* USER CODE END Private defines */

#ifdef __cplusplus
}
#endif

#endif /* __MAIN_H */
