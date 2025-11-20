# 🖥️  ProyectoSO – Simulador Educativo de Sistemas Operativos

Proyecto desarrollado como parte del curso de **Sistemas Operativos**, cuyo objetivo es implementar un simulador modular que permita comprender y analizar conceptos fundamentales como planificación de procesos, administración de memoria, paginación, manejo de archivos y recolección de métricas del sistema.

---

## 👥 Integrantes

- **Haiver Trujillo**  
- **Nicolás Chaparro**

---

## 📚 Características principales

### 🧵 Gestión de procesos
- Modelo de PCB completo.
- Estados: NEW, READY, RUNNING, WAITING, TERMINATED.
- Burst time, prioridad, páginas, tiempo restante.
- Cálculo automático de:
  - **Waiting Time**
  - **Turnaround Time**

### 🔄 Algoritmos de planificación
Incluye tres algoritmos totalmente funcionales:

- **RR (Round Robin)** con quantum real configurable.  
- **SJF (Shortest Job First)** no expropiativo.  
- **PRIORITY** no expropiativo.

### 🧠 Gestión de memoria
- Paginación bajo demanda.  
- Tabla de páginas por proceso.  
- 4 frames simulados (configurables).  
- Algoritmos de reemplazo:
  - **LRU (Least Recently Used)**
  - **FIFO (First In First Out)**

### 📁 Sistema de archivos
- Operaciones simuladas de lectura y escritura.
- Bloqueo de archivos durante acceso.
- Registro de conflictos, lecturas y escrituras.

### 📊 Métricas recolectadas automáticamente
- Tiempo total de simulación.  
- Cambios de contexto.  
- Accesos a memoria.  
- Fallos de página.  
- Lecturas y escrituras en archivo.  
- Conflictos del sistema de archivos.  
- Métricas completas por proceso.  
- Exportación automática a **simulation_results.txt**.

---

### 📂 Estructura del proyecto

OSSimulator/  
│  
├── src/  
│ ├── main/  
│ │ └── OSSSimulator.java  
│ │  
│ ├── process/  
│ │ ├── PCB.java  
│ │ ├── Process.java  
│ │ └── ProcessGenerator.java  
│ │  
│ ├── scheduling/  
│ │ ├── Scheduler.java  
│ │ ├── RoundRobinScheduler.java  
│ │ ├── SJFScheduler.java  
│ │ └── PriorityScheduler.java  
│ │  
│ ├── memory/  
│ │ ├── MemoryManager.java  
│ │ ├── PageTable.java  
│ │ └── Frame.java  
│ │  
│ ├── filesystem/  
│ │ └── FileManager.java  
│ │  
│ └── metrics/  
│ └── SimulationMetrics.java  
│  
└── simulation_results.txt  

---

### ▶ Cómo ejecutar el simulador

Utilizar algun IDE de java y ejecutar normalmente con el boton Run en un IDE como visual studio code o Eclipse

###  Configuración

- En el archivo OSSimulator.java se pueden modificar:

- schedulerType → RR, SJF, PRIORITY

- quantum → solo para RR

- replacementAlgorithm → LRU, FIFO

- frames → número de marcos

- processCount → procesos a generar

### 🤝 Contribuciones

Este proyecto fue desarrollado para fines académicos, pero puede ser extendido libremente.
Pull requests son bienvenidos.

### 📜 Licencia

Licencia MIT — Libre para uso académico y personal.
