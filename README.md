# NutriGear – Backend (API REST)

API REST de **NutriGear**, tienda online de suplementos y accesorios deportivos (Desarrollo Fullstack II, DUOC UC).
Frontend: <https://github.com/thezuazaeye/nutrigear> · Sitio: <https://nutrigear-tau.vercel.app/>

## Tecnologías
Java · Spring Boot 4.x · Maven · Spring Web · Spring Data JPA · H2 (desarrollo) · Spring Security + JWT (jjwt) · Bean Validation · Swagger/OpenAPI (springdoc)

## Cómo ejecutarlo
Requisitos: JDK 21 (o superior).

```bash
./mvnw spring-boot:run
```

- API: <http://localhost:8080>
- Documentación Swagger: <http://localhost:8080/swagger-ui.html>

### Variables de entorno (opcionales en desarrollo)
| Variable | Uso |
|---|---|
| `JWT_SECRET` | Clave Base64 (256 bits) para firmar los JWT. En producción es obligatoria; el valor por defecto es solo para desarrollo. |

> Nunca se suben claves reales al repositorio: los secretos se leen desde variables de entorno.

## Usuarios de demostración
| Rol | Correo | Clave |
|---|---|---|
| ADMIN | admin@nutrigear.cl | admin123 |
| VENDEDOR | vendedor@nutrigear.cl | vende123 |
| CLIENTE | cliente@nutrigear.cl | cliente123 |

## Endpoints
| Método | Ruta | Acceso | Descripción |
|---|---|---|---|
| POST | `/api/v1/auth/registro` | Público | Crea una cuenta (rol CLIENTE) |
| POST | `/api/v1/auth/login` | Público | Devuelve el JWT |
| GET | `/api/v1/productos` | Público | Lista productos (`?categoria=`) |
| GET | `/api/v1/productos/{id}` | Público | Detalle de un producto |
| POST | `/api/v1/productos` | ADMIN, VENDEDOR | Crea un producto |
| PUT | `/api/v1/productos/{id}` | ADMIN, VENDEDOR | Actualiza un producto |
| DELETE | `/api/v1/productos/{id}` | ADMIN, VENDEDOR | Elimina un producto |
| POST | `/api/v1/contacto` | Público | Envía un mensaje de contacto |
| GET | `/api/v1/contacto` | ADMIN, VENDEDOR | Lista los mensajes |
| GET | `/api/v1/contacto/{id}` | ADMIN, VENDEDOR | Detalle de un mensaje |

## Autenticación
1. `POST /api/v1/auth/login` con `{"email": "...", "password": "..."}` devuelve un `token`.
2. Las rutas protegidas reciben el header `Authorization: Bearer <token>`.
3. Sin token o con token inválido → `401`. Token válido pero rol insuficiente → `403`.

## Estructura
```
src/main/java/com/nutrigear/nutrigear_backend/
├── config/       Seguridad, Swagger y datos iniciales
├── controller/   Endpoints REST
├── dto/          Objetos de entrada/salida
├── exception/    Errores y manejador global
├── model/        Entidades JPA
├── repository/   Acceso a datos
├── security/     JWT (generación, validación y filtro)
└── service/      Lógica de negocio
```

## Próximos pasos
- Migrar la base de datos de H2 a Supabase (PostgreSQL) con variables de entorno.
- Integrar el frontend React con la API (`fetch` + JWT en `localStorage`).
- Carrito/compra y CRUD de usuarios.
