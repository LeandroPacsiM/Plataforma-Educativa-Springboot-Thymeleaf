# Plataforma Educativa — Spring Boot + Thymeleaf

Plataforma educativa en desarrollo para gestión de cursos, lecciones, evaluaciones y certificados.

## Características principales

- Gestión de cursos y lecciones
- Sistema de evaluaciones (quizzes)
- Seguimiento de progreso de los estudiantes
- Emisión de certificados al completar cursos
- Autenticación y roles (Spring Security / OAuth2)
- Dashboard personalizado por rol (usuario / admin)

## Tech Stack

- Java 21
- Spring Boot 3.x (MVC, Security)
- Thymeleaf (server-side templates)
- Tailwind CSS (migración vía CDN)
- JPA / Hibernate
- H2 (desarrollo/testing)
- Maven

## Instalación y ejecución (local)

Requisitos: Java 21+, Maven 3.x

1. Clona el repositorio:

   git clone <repo-url>
2. En la raíz del proyecto ejecuta:

```bash
mvn clean install
mvn spring-boot:run
```
3. Abre la aplicación en: http://localhost:8080

## Estructura del proyecto (resumen)

- `src/main/java` — Código Java organizado por paquetes (`controller`, `service`, `repository`, `model`, `security`)
- `src/main/resources/templates` — Plantillas Thymeleaf
- `src/main/resources/static` — Recursos estáticos (CSS, JS, imágenes)
- `pom.xml` — Configuración de dependencias y build

## Endpoints principales (muestras)

- `GET /` — Landing pública
- `GET /home` — Dashboard autenticado
- `GET /login` — Página de login
- `GET /courses` — Listado de cursos
- `GET /courses/{id}` — Detalle de curso
- `POST /courses` — Crear curso (admin)
- Recursos adicionales: `lessons`, `quizzes`, `progress`, `certificates`

## Licencia

Indica aquí la licencia del proyecto si aplica (por ejemplo, MIT). Si no hay licencia definida, añádelo o contacta al propietario del repositorio.

## Configuración de Google OAuth2 (desarrollo)

Para permitir login con Google la aplicación usa las variables de entorno `GOOGLE_CLIENT_ID` y `GOOGLE_CLIENT_SECRET` que se inyectan en `src/main/resources/application.yml`.

Pasos rápidos:

1. Abre la Google Cloud Console: https://console.cloud.google.com
2. Crea o selecciona un proyecto.
3. En "APIs & Services" configura la "OAuth consent screen" (External), añade scopes: `openid`, `profile`, `email`.
4. Crea credenciales → "OAuth 2.0 Client IDs" → Tipo: "Web application".
   - Authorized JavaScript origins: `http://localhost:8080`
   - Authorized redirect URIs: `http://localhost:8080/login/oauth2/code/google`
5. Copia el `Client ID` y `Client Secret`.

Configura localmente:

- Copia `.env.example` a `.env` y pega las credenciales:

```
GOOGLE_CLIENT_ID=tu-client-id-aqui.apps.googleusercontent.com
GOOGLE_CLIENT_SECRET=tu-client-secret-aqui
```

- No subas `.env` al repositorio. Está en `.gitignore`.

Ejecución local recomendada (VS Code):

1. En VS Code selecciona la configuración "Debug Spring Boot (with .env)" y ejecuta (F5). La `launch.json` incluida carga el `.env`.
2. Abre `http://localhost:8080/login` y usa el botón de Google.

Ejecución alternativa (terminal):

```bash
# Linux / macOS
export GOOGLE_CLIENT_ID=...
export GOOGLE_CLIENT_SECRET=...
mvn spring-boot:run

# Windows PowerShell
$env:GOOGLE_CLIENT_ID = '...'
$env:GOOGLE_CLIENT_SECRET = '...'
mvn spring-boot:run
```

Seguridad:

- Si tu `.env` ya fue comiteado con credenciales reales, rota las credenciales en Google Cloud inmediatamente y limpia el historial git si es necesario.
- En producción, pasa estas variables mediante el gestor de secretos o variables de entorno del servidor (no uses archivos `.env` en repositorios públicos).

