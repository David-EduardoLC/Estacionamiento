# 🚗 Sistema de Estacionamiento Escolar con Control de Espacios y Horarios

## 📌 Instituto Tecnológico de Oaxaca  
**Tecnológico Nacional de México**  
**Departamento de Sistemas y Computación**  

**Docente:** Martínez Nieto Adelina  

**Alumnos:**  
- Zenón Regalado Vicente de Jesús  
- Juárez Monjaraz Griselda Itzel  
- Carlos Raúl Sánchez Pérez  
- David Eduardo López Cruz  
- Salinas Montesinos Cintia Yadai  

**Grupo:** 7SA  

---

## 📖 Introducción
El crecimiento en la comunidad escolar ha generado una mayor demanda de espacios de estacionamiento, provocando saturación y conflictos en el uso de los lugares disponibles.  

Aunque el acceso es gratuito, la permanencia prolongada de algunos vehículos, debido a los horarios dispersos de maestros y alumnos, limita la rotación y el aprovechamiento del estacionamiento.  

Este sistema busca:  
- Organizar la asignación de lugares.  
- Priorizar a los maestros.  
- Optimizar el uso de espacios para alumnos y motocicletas.  
- Brindar administración eficiente con consultas en tiempo real y generación de reportes.  

---

## ⚙️ Características principales
1. **Registro de vehículos** (alumnos, maestros y motos).  
2. **Diferenciación de usuarios** con prioridad a maestros.  
3. **Manejo de sobrecarga en el registro** (mínimo: placa+marca, completo: con más datos).  
4. **Administración automática de espacios** y consultas en tiempo real.  

---

## 📊 Escenarios de uso
- Asignación automática de espacios prioritarios a maestros.  
- Identificación de ocupación prolongada en alumnos.  
- Aprovechamiento de cajones para motos (más de una por espacio).  

---

## ✅ Beneficios esperados
- Uso eficiente de los espacios disponibles.  
- Priorización justa a maestros sin cobros.  
- Reducción de conflictos entre usuarios.  
- Reportes claros para la toma de decisiones.  

---

## 🗂 Diagramas

### 📌 Diagrama E-R
![Imagen de WhatsApp 2025-09-18 a las 17 42 35_a5f4a2e5](https://github.com/user-attachments/assets/f1f66158-0992-49f4-9980-8630a9dabef5)


### 📌 Diagrama UML
![Imagen de WhatsApp 2025-09-18 a las 17 42 35_a88d007e](https://github.com/user-attachments/assets/24b12fe1-a631-4669-b0ac-50b199004d99)


---

## 🛢 Base de datos
Tablas principales en PostgreSQL:  

- **Estacionamiento** (id_estacionamiento, nombre, ubicación, hora_apertura, hora_cierre)  
- **Zona** (id_zona, descripción, id_estacionamiento)  
- **Espacio** (id_espacio, tipo_lugar, estado, id_zona)  
- **Persona** (id_persona, nombre, apellidos, teléfono)  
- **Alumno** (id_persona, matrícula, semestre)  
- **Maestro** (id_persona, departamento, carrera)  
- **Vehículo** (id_vehiculo, placa, marca, modelo, id_persona)  

---

## 🔗 Enlace al Proyecto
👉 [Repositorio en GitHub](https://github.com/David-EduardoLC/Estacionamiento)

