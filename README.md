# 🎵 Proyecto: Gestión Musical

## 📝 Descripción

Aplicación de escritorio desarrollada en Java para la gestión de una colección musical.
Permite administrar artistas, álbumes y canciones mediante operaciones CRUD (crear, consultar, modificar y eliminar).

El objetivo del proyecto es integrar:

* Acceso a base de datos relacional
* Interfaz gráfica de usuario
* Testing automatizado
* Uso de herramientas de desarrollo modernas

---

## 🧰 Tecnologías utilizadas

* Java 8 / JDK 22
* MySQL
* JDBC (conector MySQL)
* Mockito (testing con JARs)

**Herramientas de desarrollo:**

* NetBeans
* Eclipse IDE
* Visual Studio Code

---

## 📚 Dependencias

Este proyecto utiliza las siguientes librerías externas:

* Conector JDBC MySQL

  * `mysql-connector-java-8.0.15.jar`

* Mockito

  * `byte-buddy-1.18.8.jar`
  * `byte-budyy-agent-1.17.7.jar`
  * `mockito-core-5.23.0.jar`
  * `objenesis-3.3.jar`

* ⚠️ Otras dependencias necesarias (si aplica)

### ➕ Cómo añadir dependencias

**En Eclipse:**

```
File → Properties → Java Build Path → Classpath → Add JARs
```

---

## 🗄️ Base de datos

El proyecto incluye el script de base de datos:

```
RETOFINALBDA(DEF).sql
```

### ▶️ Cómo usarlo

1. Crear una base de datos en MySQL
2. Importar el fichero `RETOFINALBDA(DEF).sql`

**Desde consola:**

```
mysql -u usuario -p nombre_bd < RETOFINALBDA(DEF).sql
```

**O con herramienta gráfica:**

* MySQL Workbench

---

## 📦 Instalación

### 📥 Clonar el repositorio

```
git clone https://github.com/Azkona05/retoFinal2
```

### 📁 Importar el proyecto

* Abrir el proyecto en tu IDE (Eclipse / IntelliJ / VS Code)

---

## ➕ Añadir dependencias manualmente

1. Descargar:

   * Conector JDBC de MySQL
   * Librerías de Mockito

2. Añadir los `.jar` al proyecto (ver sección de dependencias)

---

## ▶️ Ejecución

1. Configurar la conexión a la base de datos en el proyecto
2. Ejecutar la aplicación desde la clase `Principal.java`

---

## 🧪 Tests

El proyecto incluye pruebas unitarias usando Mockito.

### ▶️ Cómo ejecutarlos

* Desde el IDE → Ejecutar tests
* Asegúrate de que los JAR de Mockito están correctamente añadidos

---

## 🚀 Funcionalidades principales

* Gestión de artistas, álbumes y canciones
* Operaciones CRUD completas
* Persistencia en base de datos MySQL
* Interfaz gráfica de usuario
* Testing con Mockito

---

## 🔧 Mejoras futuras

* Mejorar validación de datos
* Optimizar consultas SQL
* Mejorar la gestión de errores
* Añadir más cobertura de tests

---

## 👥 Autores

An Azkona, Nora Yakoubi, Ricardo Soza, Jon Ander Varela

Proyecto desarrollado como parte del **Reto Final**.
