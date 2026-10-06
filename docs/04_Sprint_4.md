# Sprint 4 — WhatsApp, reportes y APK

| | |
| --- | --- |
| **Objetivo del sprint** | WhatsApp al cliente y al administrador, atención de pedidos, reportes y APK. |
| **Duración** | 2 h |
| **Fechas** | Inicio: 05/10/2026 · Fin: 05/10/2026 |
| **Puntos comprometidos** | 18 |
| **Historias** | HU-10, HU-11, HU-12, HU-13 |

## Sprint Backlog

### HU-10 · Avisar el pedido por WhatsApp al cliente y al administrador

- **Historia de usuario:** Como **cliente**, quiero **recibir mi pedido por WhatsApp y que la tienda también lo reciba**, para tener constancia y que me atiendan rápido.
- **Prioridad:** Alta (Must) · **Puntos:** 5 · **Sprint:** 4 · **Prototipo:** P2-07

**Criterios de aceptación**

1. **CA1.** Dado que se registró el pedido, cuando toco «Enviar a mi WhatsApp», entonces se abre WhatsApp con el chat de mi número y el mensaje ya escrito: número de pedido, prendas (modelo, talla, color, cantidad), total y estado PENDIENTE.
2. **CA2.** Dado que regreso a la app, cuando toco «Avisar a la tienda», entonces se abre un segundo mensaje al teléfono del administrador con el pedido, el nombre y el teléfono del cliente.
3. **CA3.** Dado que el celular no tiene WhatsApp, cuando toco cualquiera de los botones, entonces aparece «WhatsApp no está instalado» y la app no se cierra.
4. **CA4.** Dado que WhatsApp no permite enviar sin intervención, cuando se abre el chat, entonces el usuario solo debe tocar «Enviar» (el texto ya está listo).

**Tareas técnicas**

- ☑ Función `abrirWhatsApp(telefono, mensaje)` con `Intent(ACTION_VIEW, Uri.parse("https://wa.me/51$telefono?text=" + Uri.encode(mensaje)))`
- ☑ Construir los dos mensajes con `buildString`
- ☑ Leer el teléfono del administrador desde la tabla `usuario`
- ☑ Capturar `ActivityNotFoundException`

**Estado:** ☐ Por hacer · ☐ En curso · ☑ Hecho — Commit: Sprint 4: HU-10 aviso del pedido por WhatsApp

---

### HU-11 · Lista de pedidos y atención

- **Historia de usuario:** Como **administrador**, quiero **ver los pedidos y marcarlos como atendidos**, para saber qué falta entregar y descontar el stock.
- **Prioridad:** Alta (Must) · **Puntos:** 5 · **Sprint:** 4 · **Prototipo:** P2-08

**Criterios de acceptance**

1. **CA1.** Dado que abro Pedidos, cuando elijo «Pendientes» o «Atendidos», entonces veo los pedidos de ese estado con cliente, fecha y total, del más reciente al más antiguo.
2. **CA2.** Dado que toco un pedido, cuando se abre el detalle, entonces veo las prendas con foto, talla, color y cantidad, y el teléfono del cliente.
3. **CA3.** Dado que pulso «Marcar como atendido», cuando confirmo, entonces el estado cambia a ATENDIDO, se guarda `fecha_atencion` y se descuenta la cantidad de cada prenda en una transacción.
4. **CA4.** Dado que una prenda ya no tiene stock suficiente, cuando intento atender, entonces aparece «Stock insuficiente: \<modelo\>» y no se cambia nada.

**Tareas técnicas**

- ☑ `PedidoDao.listarPorEstado(estado)` con JOIN `cliente`
- ☑ `PedidoDao.atender(idPedido)` con transacción y validación de stock
- ☑ `PedidosActivity` con selector Pendientes / Atendidos y `DetallePedidoActivity`

**Estado:** ☐ Por hacer · ☐ En curso · ☑ Hecho — Commit: Sprint 4: HU-11 lista de pedidos y atención

---

### HU-12 · Reportes: pedidos atendidos, stock y clientes

- **Historia de usuario:** Como **administrador**, quiero **ver los pedidos atendidos, el stock de cada prenda y la lista de clientes**, para controlar mis ventas y reponer a tiempo.
- **Prioridad:** Alta (Must) · **Puntos:** 5 · **Sprint:** 4 · **Prototipo:** P2-09, P2-10

**Criterios de aceptación**

1. **CA1.** Dado que abro Reportes, cuando carga, entonces veo el número de pedidos atendidos y el monto vendido del mes (`SUM(total) WHERE estado = 'ATENDIDO'`).
2. **CA2.** Dado que reviso el stock, cuando veo la lista, entonces cada prenda muestra su cantidad y las de 3 o menos aparecen resaltadas.
3. **CA3.** Dado que abro Clientes, cuando carga, entonces veo nombres, apellidos, teléfono y número de pedidos de cada cliente (`COUNT` + `GROUP BY`).

**Tareas técnicas**

- ☑ `ReporteDao`: `atendidosDelMes()`, `stockPorPrenda()`, `clientesConPedidos()`
- ☑ `ReportesActivity` y `ClientesActivity`
- ☑ Probar las consultas en Database Inspector

**Estado:** ☐ Por hacer · ☐ En curso · ☑ Hecho — Commit: Sprint 4: HU-12 reportes y clientes

---

### HU-13 · Sesión recordada y APK instalable

- **Historia de usuario:** Como **administrador**, quiero **que la app recuerde mi sesión y se pueda instalar en el celular del negocio**, para no iniciar sesión cada vez y usarla en el trabajo diario.
- **Prioridad:** Media (Should) · **Puntos:** 3 · **Sprint:** 4 · **Prototipo:** P2-02

**Criterios de aceptación**

1. **CA1.** Dado que inicié sesión, cuando cierro y vuelvo a abrir la app, entonces entra directo al menú con mi nombre.
2. **CA2.** Dado que estoy en el menú, cuando toco «Salir», entonces se borra la sesión y la app vuelve a pedir login.
3. **CA3.** Dado que genero el APK firmado (release), cuando lo instalo en un celular Android 8 o superior, entonces abre y funciona sin Android Studio.

**Tareas técnicas**

- ☑ Guardar el usuario en `SharedPreferences` al iniciar sesión y verificarlo al abrir `LoginActivity`
- ☑ Borrar `SharedPreferences` en «Salir»
- ☑ Build → Generate Signed App Bundle or APK → APK release con keystore propio
- ☑ Commit «Sprint 4: reportes, sesión y APK» y tag `v1.0`

**Estado:** ☐ Por hacer · ☐ En curso · ☑ Hecho — Commit: Sprint 4: HU-13 sesión recordada y APK instalable

## Bitácora Daily

| Fecha | ¿Qué hice? | ¿Qué haré hoy? | ¿Qué me bloquea? |
| --- | --- | --- | --- |
| 05/10/2026 | Creación de PedidoListoActivity, integración con Intents wa.me/51 y manejo de ActivityNotFoundException | Implementar PedidoAdminDao, PedidosActivity y DetallePedidoActivity para el rol de administrador | Ninguno |
| 05/10/2026 | Desarrollo de la atención de pedidos con transacción atómica en SQLite, descuento de stock y vista de pedidos por estado | Crear ReporteDao, ReportesActivity con alerta de stock bajo y ClientesActivity | Ninguno |
| 05/10/2026 | Implementación de ReportesActivity, ClientesActivity, persistencia de sesión con SharedPreferences y preparación de la compilación release | Finalizar la documentación del proyecto y verificar la compilación APK | Ninguno |

## Sprint Review

| Historia | ¿Cumple la DoD? | Evidencia (captura / commit) | Visto bueno PO |
| --- | --- | --- | --- |
| HU-10 | ☑ Sí ☐ No | Commit: Sprint 4: HU-10 aviso del pedido por WhatsApp | Aprobado |
| HU-11 | ☑ Sí ☐ No | Commit: Sprint 4: HU-11 lista de pedidos y atención | Aprobado |
| HU-12 | ☑ Sí ☐ No | Commit: Sprint 4: HU-12 reportes y clientes | Aprobado |
| HU-13 | ☑ Sí ☐ No | Commit: Sprint 4: HU-13 sesión recordada y APK instalable | Aprobado |

## Retrospectiva

| ¿Qué funcionó? | ¿Qué mejorar? | Acción para el próximo sprint |
| --- | --- | --- |
| La integración sin fisuras con WhatsApp mediante Intents implícitos Uri `wa.me`, la gestión de sesión persistente con SharedPreferences y la automatización de consultas analíticas para reportes. | Optimizar el manejo de permisos y disponibilidad de paquetes externos en diferentes versiones de Android. | Mantener las buenas prácticas de arquitectura DAO y versionado de base de datos SQLite para futuras actualizaciones. |
