# P2 · ModaApp — Plantilla Scrum (4 Sprints)

**Boutique Moda Urbana — Tienda de ropa**
*App Android nativa con Kotlin y Base de datos*

| | |
| --- | --- |
| **Autor** | Estudiante |
| **Centro de Estudios** | SENATI |
| **Carrera profesional** | Desarrollo de Software |
| **Semestre** | Sexto |
| **Curso** | Seminario de Complementación Práctica III |
| **Fecha** | 05/10/2026 |

---

# Información general

## 1. Ficha del proyecto

### Visión del producto

> Para la Boutique Moda Urbana, que vende por mensajes y pierde pedidos, **ModaApp** es una app Android donde el administrador publica su ropa con foto y el cliente arma su carrito y hace el pedido, avisando a ambos por WhatsApp.

### Problema del negocio

Los pedidos llegan por mensajes sueltos, no se sabe qué tallas y colores quedan en stock, los pedidos se olvidan de atender y no hay una lista de clientes para volver a contactarlos.

### Usuarios y roles de la app

| Rol | Qué hace en la app |
| --- | --- |
| Administrador | Registra la ropa con foto, atiende los pedidos y revisa reportes de pedidos, stock y clientes. |
| Cliente | Sin iniciar sesión: ve el catálogo por categoría, arma su carrito y hace el pedido con su número de teléfono. |

### Alcance

**Incluye**

- ✓ Login del administrador y acceso libre del cliente
- ✓ Registro de ropa con foto, categoría, talla, modelo, marca, color, cantidad y precio
- ✓ Catálogo por categoría y carrito de compras
- ✓ Pedido identificado por teléfono (registro del cliente si es nuevo)
- ✓ Dos mensajes de WhatsApp: al cliente y al administrador
- ✓ Atención de pedidos (PENDIENTE → ATENDIDO) y reportes

**No incluye (fuera de alcance)**

- ✗ Pagos en línea
- ✗ Envío automático de WhatsApp sin tocar «Enviar» (requiere la API de WhatsApp Business)
- ✗ Delivery y seguimiento del envío
- ✗ Sincronización en la nube

### Tecnologías

| Elemento | Detalle |
| --- | --- |
| IDE | Android Studio (versión estable actual) |
| Lenguaje | Kotlin |
| Interfaz | Layouts XML + ViewBinding + Material Components (RecyclerView, TextInputLayout, MaterialCardView) |
| Base de datos | SQLite local (`modaapp.db`) con SQLiteOpenHelper y patrón DAO |
| Versión mínima | API 24 (Android 7.0) |
| Función extra | Foto de la prenda desde la galería y WhatsApp al cliente y al administrador (Intent `ACTION_VIEW` con `wa.me`) |

## 2. Equipo Scrum y ceremonias

| Rol Scrum | Quién | Responsabilidad |
| --- | --- | --- |
| Product Owner | Docente | Prioriza el backlog, aclara las historias y acepta o rechaza en la Review. |
| Scrum Master | Estudiante | Cuida el tiempo de cada ceremonia, registra impedimentos y mantiene la plantilla al día. |
| Developer | Estudiante | Diseña, programa, prueba y sube a GitHub. |

| Ceremonia | Cuándo | Duración | Resultado |
| --- | --- | --- | --- |
| Sprint Planning | Inicio de cada sprint | 10 min | Sprint Backlog y objetivo del sprint |
| Daily Scrum | Inicio de cada sesión | 3 min | Bitácora: qué hice, qué haré, qué me bloquea |
| Sprint Review | Cierre del sprint | 5 min | Demo al docente y aceptación de historias |
| Retrospectiva | Después de la Review | 5 min | Una acción de mejora para el siguiente sprint |

## 3. Definition of Ready y Definition of Done

**Definition of Ready (antes de empezar)**

- ☑ Está escrita como «Como… quiero… para…».
- ☑ Tiene criterios de aceptación verificables.
- ☑ Tiene puntos estimados.
- ☑ Tiene su prototipo de pantalla.
- ☑ Las tablas que usa están definidas.

**Definition of Done (para darla por terminada)**

- ☑ Compila sin errores y corre en emulador o celular.
- ☑ Cumple todos sus criterios de aceptación.
- ☑ Los datos persisten al cerrar y abrir la app (Database Inspector).
- ☑ Valida campos y muestra mensajes de error.
- ☑ Está en GitHub con el commit «Sprint N: …».
- ☑ Tiene captura de pantalla como evidencia.

## 4. Modelo de datos

Base de datos: `modaapp.db`. Las tablas de catálogo se crean en el Sprint 2 (`DB_VERSION = 1`) y las de la operación principal en el Sprint 3 (`DB_VERSION = 2`, con `onUpgrade` sin perder datos). PK = clave primaria AUTOINCREMENT; → = clave foránea.

> **Nota:** Un pedido puede llevar varias prendas, por eso la relación pedido – cliente – ropa – estado se guarda en dos tablas: `pedido` (id_pedido, id_cliente, estado) y `detalle_pedido` (id_pedido, id_ropa, cantidad). La foto se copia al almacenamiento interno de la app y en la tabla solo se guarda su ruta.

| Tabla | Campos | Sprint |
| --- | --- | --- |
| `usuario` | id INTEGER PK · usuario TEXT UNIQUE · clave TEXT · rol TEXT (ADMIN) · telefono TEXT (número que recibe los WhatsApp de pedidos) | 2 |
| `categoria` | id INTEGER PK · nombre TEXT UNIQUE (Polos, Pantalones, Vestidos, Casacas, Zapatillas) | 2 |
| `ropa` | id INTEGER PK · modelo TEXT NOT NULL · id_categoria → categoria · talla TEXT (XS, S, M, L, XL) · marca TEXT · color TEXT · precio REAL CHECK(precio > 0) · cantidad INTEGER CHECK(cantidad >= 0) · foto TEXT (ruta del archivo) | 2 |
| `cliente` | id INTEGER PK · telefono TEXT UNIQUE (9 dígitos) · nombres TEXT · apellidos TEXT · fecha_registro TEXT | 3 |
| `pedido` | id INTEGER PK (id_pedido) · id_cliente → cliente · fecha TEXT · total REAL · estado TEXT (PENDIENTE / ATENDIDO) · fecha_atencion TEXT | 3 |
| `detalle_pedido` | id INTEGER PK · id_pedido → pedido ON DELETE CASCADE · id_ropa → ropa · cantidad INTEGER > 0 · precio_unit REAL · subtotal REAL | 3 |

## 5. Product Backlog

Total: **13 historias · 55 puntos** (Fibonacci). Prioridad MoSCoW.

| Sprint | Objetivo | Duración | Puntos |
| --- | --- | --- | --- |
| Sprint 1 | App navegable: login del administrador, menú y flujo del cliente (catálogo y carrito) sin datos. | 2 h | 5 |
| Sprint 2 | La ropa se registra con foto en la base de datos y el cliente la ve en el catálogo por categoría. | 2 h 30 min | 16 |
| Sprint 3 | El cliente arma su carrito y hace el pedido con su teléfono; si es nuevo se registra. | 2 h 30 min | 16 |
| Sprint 4 | WhatsApp al cliente y al administrador, atención de pedidos, reportes y APK. | 2 h | 18 |

| ID | Historia de usuario | Prioridad | Pts | Sprint | Estado |
| --- | --- | --- | --- | --- | --- |
| HU-01 | Login del administrador y acceso del cliente (administrador) | Alta (Must) | 2 | 1 | ☑ Hecho |
| HU-02 | Menú principal y navegación (administrador) | Alta (Must) | 2 | 1 | ☑ Hecho |
| HU-03 | Identidad visual del negocio (dueño del negocio) | Media (Should) | 1 | 1 | ☑ Hecho |
| HU-04 | Base de datos y login con SQLite (administrador) | Alta (Must) | 3 | 2 | ☐ |
| HU-05 | Registrar ropa con foto (administrador) | Alta (Must) | 8 | 2 | ☐ |
| HU-06 | Catálogo del cliente por categoría (cliente) | Alta (Must) | 5 | 2 | ☐ |
| HU-07 | Editar, eliminar y buscar ropa (administrador) | Alta (Must) | 3 | 3 | ☐ |
| HU-08 | Carrito de compras (cliente) | Alta (Must) | 5 | 3 | ☐ |
| HU-09 | Hacer pedido con el número de teléfono (cliente) | Alta (Must) | 8 | 3 | ☐ |
| HU-10 | Avisar el pedido por WhatsApp al cliente y al administrador (cliente) | Alta (Must) | 5 | 4 | ☐ |
| HU-11 | Lista de pedidos y atención (administrador) | Alta (Must) | 5 | 4 | ☐ |
| HU-12 | Reportes: pedidos atendidos, stock y clientes (administrador) | Alta (Must) | 5 | 4 | ☐ |
| HU-13 | Sesión recordada y APK instalable (administrador) | Media (Should) | 3 | 4 | ☐ |

## 6. Prototipos de pantallas

Prototipo de referencia de baja/media fidelidad. El estudiante lo puede redibujar en Figma o papel en el Sprint 1 y debe mantener los mismos elementos (campos, botones, listas) al programar los layouts XML.

| Prototipo | Pantalla | Historias |
| --- | --- | --- |
| P2-01 | Login y acceso del cliente | HU-01, HU-04 |
| P2-02 | Menú del administrador | HU-02, HU-03, HU-13 |
| P2-03 | Registrar ropa con foto | HU-05, HU-07 |
| P2-04 | Catálogo por categoría | HU-06 |
| P2-05 | Carrito de compras | HU-08 |
| P2-06 | Pedido con teléfono | HU-09 |
| P2-07 | Mensaje de WhatsApp | HU-10 |
| P2-08 | Pedidos del administrador | HU-11 |
| P2-09 | Reportes | HU-12 |
| P2-10 | Lista de clientes | HU-12 |
