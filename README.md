# Tipealo Backend API

REST API construida con Java 21 + Spring Boot + PostgreSQL (Neon).

## Requisitos previos

- Java 21 (JDK)
- Maven 3.9+
- IntelliJ IDEA Community (recomendado)
- Git

---

## 1. Instalar Java 21

Abrí PowerShell como administrador y ejecutá:

```powershell
winget install EclipseAdoptium.Temurin.21.JDK
```

Cerrá y abrí PowerShell de nuevo, luego verificá:

```powershell
java -version
```

Debe decir: `openjdk version "21.x.x"`

---

## 2. Instalar Maven

```powershell
winget install Apache.Maven
```

Si no funciona, instalalo manualmente:

1. Bajá el **Binary zip archive** desde https://maven.apache.org/download.cgi
2. Extraé y mové la carpeta a `C:\Program Files\Maven`
3. Agregá al PATH (PowerShell como administrador):

```powershell
[System.Environment]::SetEnvironmentVariable("Path", [System.Environment]::GetEnvironmentVariable("Path", "Machine") + ";C:\Program Files\Maven\bin", "Machine")
```

4. Cerrá y abrí PowerShell, verificá:

```powershell
mvn -version
```

---

## 3. Instalar IntelliJ IDEA Community

```powershell
winget install JetBrains.IntelliJIDEA.Community
```

---

## 4. Clonar el repositorio

```powershell
git clone https://github.com/EderJair/Tipealo-Backend.git
cd Tipealo-Backend
```

---

## 5. Configurar credenciales

Copiá el archivo de ejemplo y completá con las credenciales reales:

```powershell
copy src\main\resources\application.yaml.example src\main\resources\application.yaml
```

Editá `src/main/resources/application.yaml` y reemplazá los valores:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://TU_HOST_NEON/neondb?sslmode=require
    username: TU_USUARIO
    password: TU_PASSWORD

jwt:
  secret: TU_SECRET_KEY_MIN_32_CHARS
  expiration: 86400000
```

> Pedile las credenciales al líder del proyecto.

---

## 6. Abrir en IntelliJ

1. Abrí IntelliJ IDEA
2. Click en **Open**
3. Navegá a la carpeta del proyecto y seleccioná el archivo `pom.xml`
4. Click en **Trust Project**
5. Esperá que IntelliJ descargue las dependencias
6. Cuando aparezca el SDK warning, seleccioná **Eclipse Temurin 21**

---

## 7. Correr la aplicación

Desde la terminal de IntelliJ o PowerShell dentro de la carpeta del proyecto:

```powershell
mvn spring-boot:run
```

La app está corriendo cuando ves:

```
Tomcat started on port 8080 (http)
Started ApiApplication in X seconds
```

---

## 8. Verificar que funciona

```powershell
curl http://localhost:8080/api/test/public
```

Respuesta esperada:
```json
{"message":"API funcionando correctamente"}
```

---

## Endpoints disponibles

| Método | Ruta | Auth | Descripción |
|--------|------|------|-------------|
| GET | `/api/test/public` | No | Health check |
| GET | `/api/test/private` | JWT | Ruta protegida de prueba |
| POST | `/api/auth/register` | No | Registrar usuario |
| POST | `/api/auth/login` | No | Login, devuelve JWT |

### Ejemplo register

```powershell
curl -X POST http://localhost:8080/api/auth/register `
  -H "Content-Type: application/json" `
  -d "{\"name\":\"Tu Nombre\",\"email\":\"tu@email.com\",\"password\":\"123456\"}"
```

### Ejemplo login

```powershell
curl -X POST http://localhost:8080/api/auth/login `
  -H "Content-Type: application/json" `
  -d "{\"email\":\"tu@email.com\",\"password\":\"123456\"}"
```

### Ejemplo ruta privada

```powershell
curl http://localhost:8080/api/test/private `
  -H "Authorization: Bearer TU_TOKEN_JWT"
```
