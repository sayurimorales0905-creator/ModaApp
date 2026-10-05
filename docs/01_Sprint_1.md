# Sprint 1 — App navegable

| | |
| --- | --- |
| **Objetivo del sprint** | App navegable: login del administrador, menú y flujo del cliente (catálogo y carrito) sin datos. |
| **Duración** | 2 h |
| **Fechas** | Inicio: 05/10/2026 · Fin: 05/10/2026 |
| **Puntos comprometidos** | 5 |
| **Historias** | HU-01, HU-02, HU-03 |

## Sprint Backlog

### HU-01 · Login del administrador y acceso del cliente

- **Historia de usuario:** Como **administrador**, quiero **ingresar con usuario y contraseña**, para que solo el personal autorizado use la app.
- **Prioridad:** Alta (Must) · **Puntos:** 2 · **Sprint:** 1 · **Prototipo:** P2-01

**Criterios de aceptación**

1. **CA1.** Dado que los campos están vacíos, cuando pulso «Ingresar», entonces se muestra un mensaje de error debajo de cada campo vacío.
2. **CA2.** Dado que escribo `admin / 1234`, cuando pulso «Ingresar», entonces se abre el menú principal y el login se cierra (atrás no regresa al login).
3. **CA3.** Dado que escribo credenciales incorrectas, cuando pulso «Ingresar», entonces aparece el Toast «Credenciales incorrectas».
4. **CA4.** Dado que escribo la contraseña, cuando la veo en pantalla, entonces aparece oculta y puedo mostrarla con el ícono de ojo.
5. **CA5.** Dado que soy cliente, cuando toco «Ver catálogo», entonces entro al catálogo sin usuario ni contraseña.

**Tareas técnicas**

- ☑ Crear el proyecto ModaApp (Empty Views Activity, Kotlin, API 26) con paquete `com.senati.modaapp`
- ☑ Activar `viewBinding` en `build.gradle.kts` (Module :app)
- ☑ Diseñar `activity_login.xml` con TextInputLayout (usuario, contraseña con `password_toggle`) y botón
- ☑ Programar la validación en `LoginActivity.kt` y declararla como LAUNCHER en `AndroidManifest.xml`
- ☑ Agregar el botón «Ver catálogo» que abre `CatalogoActivity`

**Estado:** ☐ Por hacer · ☐ En curso · ☑ Hecho — Commit: Sprint 1: login y menú

---

### HU-02 · Menú principal y navegación

- **Historia de usuario:** Como **administrador**, quiero **un menú con Ropa, Pedidos, Clientes, Reportes**, para llegar rápido a cada función de la app.
- **Prioridad:** Alta (Must) · **Puntos:** 2 · **Sprint:** 1 · **Prototipo:** P2-02

**Criterios de aceptación**

1. **CA1.** Dado que inicié sesión, cuando se abre el menú, entonces veo las opciones Ropa, Pedidos, Clientes, Reportes y el botón «Salir».
2. **CA2.** Dado que estoy en el menú, cuando toco una opción, entonces se abre su pantalla y con «atrás» vuelvo al menú.
3. **CA3.** Dado que estoy en el menú, cuando toco «Salir», entonces regreso al login.
4. **CA4.** Dado que entro como cliente («Ver catálogo»), cuando navego, entonces solo veo Catálogo y Carrito, nunca las opciones del administrador.

**Tareas técnicas**

- ☑ Renombrar `MainActivity` a `MenuActivity` y configurar la navegación entre pantallas (`MenuActivity`, `CatalogoActivity`, `LoginActivity`)
- ☑ Diseñar `activity_menu.xml` (administrador) en GridLayout de 2 columnas y `activity_catalogo.xml` (cliente) con su botón de carrito
- ☑ Programar los Intents de navegación en `MenuActivity.kt`
- ☑ Subir el proyecto a GitHub con el commit «Sprint 1: login y menú»

**Estado:** ☐ Por hacer · ☐ En curso · ☑ Hecho — Commit: Sprint 1: login y menú

---

### HU-03 · Identidad visual del negocio

- **Historia de usuario:** Como **dueño del negocio**, quiero **que la app tenga el nombre, colores e ícono de mi empresa**, para que se vea profesional ante mis clientes y trabajadores.
- **Prioridad:** Media (Should) · **Puntos:** 1 · **Sprint:** 1 · **Prototipo:** P2-02

**Criterios de aceptación**

1. **CA1.** Dado que instalo la app, cuando veo el launcher, entonces aparece el nombre «ModaApp» con un ícono propio.
2. **CA2.** Dado que abro cualquier pantalla, cuando la observo, entonces usa la paleta rosa y negro definida en `colors.xml`/`themes.xml`.
3. **CA3.** Dado que reviso el código, cuando busco textos fijos en los layouts, entonces todos están en `strings.xml`.

**Tareas técnicas**

- ☑ Definir la paleta rosa y negro en `colors.xml` y aplicarla en `themes.xml`
- ☑ Crear el ícono con Image Asset (res → New → Image Asset)
- ☑ Pasar los textos de los layouts a `strings.xml`

**Estado:** ☐ Por hacer · ☐ En curso · ☑ Hecho — Commit: Sprint 1: login y menú

## Bitácora Daily

| Fecha | ¿Qué hice? | ¿Qué haré hoy? | ¿Qué me bloquea? |
| --- | --- | --- | --- |
| 05/10/2026 | Configuración inicial del proyecto ModaApp, activación de ViewBinding y ajuste del paquete base. | Diseñar e implementar LoginActivity con validaciones de campos y navegación. | Ninguno |
| 05/10/2026 | Implementación de LoginActivity y creación de MenuActivity y CatalogoActivity con sus layouts XML. | Definir la paleta de colores corporativos (rosa/negro) y organizar strings.xml. | Ninguno |
| 05/10/2026 | Aplicación del tema rosa y negro en themes.xml, extracción de textos a strings.xml y verificación de navegación. | Realizar la prueba de compilación del Sprint 1 y preparar la documentación para GitHub. | Ninguno |

## Sprint Review

| Historia | ¿Cumple la DoD? | Evidencia (captura / commit) | Visto bueno PO |
| --- | --- | --- | --- |
| HU-01 | ☑ Sí ☐ No | Commit: Sprint 1: login y menú | Aprobado |
| HU-02 | ☑ Sí ☐ No | Commit: Sprint 1: login y menú | Aprobado |
| HU-03 | ☑ Sí ☐ No | Commit: Sprint 1: login y menú | Aprobado |

## Retrospectiva

| ¿Qué funcionó? | ¿Qué mejorar? | Acción para el próximo sprint |
| --- | --- | --- |
| La implementación limpia de ViewBinding, la navegación estructurada entre actividades y la paleta de colores rosa/negro. | Estructurar los recursos de cadenas e imágenes desde el primer momento de creación de layouts. | Implementar la base de datos SQLite y DBHelper tempranamente para el catálogo y el login con base de datos en el Sprint 2. |
