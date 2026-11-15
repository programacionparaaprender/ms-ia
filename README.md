
### API de IA

En Git Bash, configura `GROQ_API_KEY` antes de iniciar la aplicación:

```bash
export GROQ_API_KEY="PEGA_AQUI_TU_CLAVE_DE_GROQ"
./mvnw spring-boot:run
```

Prueba el endpoint `/ia/v1/ia/preguntar` (el texto usa escapes Unicode JSON para evitar problemas de codificación en la terminal):

```bash
curl.exe -X POST "http://localhost:8080/ia/v1/ia/preguntar" \
  -H "Content-Type: application/json; charset=utf-8" \
  --data-raw '{"pregunta":"\u00bfQu\u00e9 es una API REST?","palabras_maximas":100}'
```

Swagger: http://localhost:8080/swagger-ui/index.html

### Crear un agente

El endpoint `POST /agent/v1/ia/agentes` recibe un nombre e instrucciones para
registrar un agente en memoria. La respuesta incluye su identificador UUID; los
agentes se pierden al reiniciar la aplicación.

```bash
curl.exe -X POST "http://localhost:8080/agent/v1/ia/agentes" \
  -H "Content-Type: application/json" \
  --data-raw '{"nombre":"Tutor","instrucciones":"Explica los conceptos con ejemplos claros."}'
```

Usa el `id` recibido para enviar consultas al agente. Groq aplicará sus
instrucciones y la cantidad máxima de palabras indicada:

```bash
curl.exe -X POST "http://localhost:8080/agent/v1/ia/agentes/ID_DEL_AGENTE/preguntar" \
  -H "Content-Type: application/json" \
  --data-raw '{"pregunta":"¿Qué es una API REST?","palabras_maximas":100}'
```

### docker
>- docker build -t debuggeando-ideas:2.0 .
>- docker rmi image_id
>- docker run --net=host -p 8080:8080 6a218c066db9
>- docker run -p 8080:8080 6a218c066db9 -d

### curso de docker
>- https://www.youtube.com/watch?v=--zEFJ6g4lo

### Agregar tipo de dato de entrada en post
>- https://stackoverflow.com/questions/75362235/spring-boot-returning-error-content-type-text-plaincharset-utf-8-not-supporte

### definir relaciones en jpa
>- https://www.baeldung.com/jpa-many-to-many

### definir un campo email en tabla jpa
>- https://stackoverflow.com/questions/65370879/javax-validation-constraints-email-in-springboot

### com.microsoft.sqlserver.jdbc.SQLServerException: El controlador no pudo establecer una conexión segura con SQL Server con el cifrado de Capa de sockets seguros (SSL). Error: "sun.security.validator.ValidatorException: PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target". ClientConnectionId:5ae4c793-3c0f-4c8a-a63e-fee386f4706c
>- https://www.ibm.com/docs/es/db2-for-zos/12?topic=ssl-configuring-java-runtime-environment-use
>- https://stackoverflow.com/questions/41257366/import-windows-certificates-to-java
>- https://stackoverflow.com/questions/32766114/sql-server-jdbc-error-on-java-8-the-driver-could-not-establish-a-secure-connect


### urls del proyecto


### swagger, el proyecto falla
>- http://localhost:8080/swagger-ui.html

### swagger, su nueva ruta es
>- http://localhost:8080/swagger-ui/index.html
>- http://localhost:8080/swagger-ui/index.html#/tipo-cambio-controller/getCambio

### proyecto de graphql
>- http://localhost:8080/graphql/schema.json

### Endpoints de AuthController (cURL)

#### Autenticar usuario y obtener token
```bash
curl -X POST http://localhost:8080/api/auth/authenticate \
-H "Content-Type: application/json" \
-d '{ "username": "admin", "password": "password" }'
```



### configuración de heroku para postgre

```bash
spring.datasource.url=postgres://fnyhjwrbujkrso:2d46c6c4dc5734fe37da9a16c9093426d546ca33d2058d8e898ade6224692a23@ec2-23-21-229-200.compute-1.amazonaws.com:5432/dalvkofnhhb3ub

# nombre de usuario y contraseï¿½a
spring.datasource.username = fnyhjwrbujkrso
spring.datasource.password =2d46c6c4dc5734fe37da9a16c9093426d546ca33d2058d8e898ade6224692a23

# mostrar sentencias SQL en la consola
spring.jpa.show-sql = true

# actualizar base de datos y crear entidades
spring.jpa.hibernate.ddl-auto = update

# hibernate genera SQL optimizado
spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.PostgreSQLDialect
```