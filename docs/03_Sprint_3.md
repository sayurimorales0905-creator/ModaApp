# Sprint 3 — Carrito y pedido

| | |
| --- | --- |
| **Objetivo del sprint** | El cliente arma su carrito y hace el pedido con su teléfono; si es nuevo se registra. |
| **Duración** | 2 h 30 min |
| **Fechas** | Inicio: 05/10/2026 · Fin: 05/10/2026 |
| **Puntos comprometidos** | 16 |
| **Historias** | HU-07, HU-08, HU-09 |

## Sprint Backlog

### HU-07 · Editar, eliminar y buscar ropa

- **Historia de usuario:** Como **administrador**, quiero **corregir, eliminar y buscar prendas**, para mantener el catálogo y el stock al día.
- **Prioridad:** Alta · **Puntos:** 3 · **Sprint:** 3 · **Prototipo:** P2-03

**Criterios de aceptación**

1. **CA1.** Dado que toco una prenda, cuando se abre el formulario, entonces muestra sus datos y su foto, y el botón dice «Actualizar».
2. **CA2.** Dado que pulso «Eliminar» en una prenda que está en algún pedido, cuando confirmo, entonces aparece «No se puede eliminar: tiene pedidos».
3. **CA3.** Dado que escribo en el buscador, cuando cambia el texto, entonces se filtra por modelo, marca o color.

**Tareas técnicas**

- ☑ `obtener()`, `actualizar()`, `eliminar()`, `listar(filtro)` en `RopaDao`
- ☑ Modo edición con `putExtra("id")` y cambio opcional de foto
- ☑ AlertDialog + `SQLiteConstraintException`
- ☑ Buscador con `LIKE` en modelo, marca y color

**Estado:** ☐ Por hacer · ☐ En curso · ☑ Hecho — Commit: Sprint 3: HU-07 editar, eliminar y buscar ropa

---

### HU-08 · Carrito de compras

- **Historia de usuario:** Como **cliente**, quiero **agregar prendas a un carrito con su cantidad**, para pedir varias prendas a la vez.
- **Prioridad:** Alta · **Puntos:** 5 · **Sprint:** 3 · **Prototipo:** P2-05

**Criterios de aceptación**

1. **CA1.** Dado que toco «Agregar» en una prenda, cuando elijo la cantidad, entonces no puedo superar el stock disponible y aparece «Disponible: N».
2. **CA2.** Dado que agrego prendas, cuando abro el carrito, entonces veo cada prenda con foto, talla, cantidad, subtotal y el total.
3. **CA3.** Dado que mantengo presionada una prenda del carrito, cuando confirmo, entonces se quita y el total se recalcula.
4. **CA4.** Dado que el carrito está vacío, cuando lo abro, entonces aparece «Tu carrito está vacío» y el botón «Hacer pedido» está deshabilitado.

**Tareas técnicas**

- ☑ `object Carrito` (singleton) con lista mutable de `ItemCarrito(ropa, cantidad)`
- ☑ Ícono de carrito con contador en `CatalogoActivity`
- ☑ `CarritoActivity` con RecyclerView y total

**Estado:** ☐ Por hacer · ☐ En curso · ☑ Hecho — Commit: Sprint 3: HU-08 carrito de compras

---

### HU-09 · Hacer pedido con el número de teléfono

- **Historia de usuario:** Como **cliente**, quiero **hacer mi pedido escribiendo solo mi teléfono**, para no registrar mis datos cada vez.
- **Prioridad:** Alta (Must) · **Puntos:** 8 · **Sprint:** 3 · **Prototipo:** P2-06

**Criterios de aceptación**

1. **CA1.** Dado que toco «Hacer pedido», cuando escribo un teléfono de 9 dígitos ya registrado, entonces se muestra «Hola, \<nombres\>» y puedo confirmar.
2. **CA2.** Dado que el teléfono no existe, cuando pulso «Continuar», entonces se piden nombres y apellidos (obligatorios) y se registra el cliente.
3. **CA3.** Dado que confirmo, cuando se guarda, entonces se crean el pedido (id_cliente, fecha, total, estado PENDIENTE) y su detalle (id_pedido, id_ropa, cantidad) en una sola transacción y el carrito se vacía.
4. **CA4.** Dado que el pedido se guardó, cuando termina, entonces aparece «Pedido #N registrado».

**Tareas técnicas**

- ☑ `DB_VERSION = 2` con tablas `cliente`, `pedido` y `detalle_pedido` (`onUpgrade`)
- ☑ `ClienteDao.buscarPorTelefono()` e `insertar()`
- ☑ `PedidoDao.registrar(idCliente, items)` con `beginTransaction` / `setTransactionSuccessful` / `endTransaction`
- ☑ `PedidoActivity`: teléfono y, si es nuevo, nombres y apellidos (visibility GONE/VISIBLE)

**Estado:** ☐ Por hacer · ☐ En curso · ☑ Hecho — Commit: Sprint 3: HU-09 hacer pedido con teléfono

## Bitácora Daily

| Fecha | ¿Qué hice? | ¿Qué haré hoy? | ¿Qué me bloquea? |
| --- | --- | --- | --- |
| 05/10/2026 | Implementación de las funciones de actualizar, eliminar, obtener y búsqueda por filtro LIKE en RopaDao y actualización de RopaActivity / RopaFormActivity. | Implementar el modelo Singleton Carrito, NumberPicker para limitar por stock y CarritoActivity. | Ninguno |
| 05/10/2026 | Desarrollo de CarritoActivity, CarritoAdapter, contador dinámico en el FAB del catálogo y diálogo de confirmación para eliminar ítems del carrito. | Incrementar DB_VERSION a 2 con las tablas cliente, pedido y detalle_pedido e implementar PedidoActivity. | Ninguno |
| 05/10/2026 | Implementación de ClienteDao, PedidoDao con transacciones SQLite, validación de cliente por teléfono y confirmación de pedidos. | Realizar pruebas finales del Sprint 3 y actualizar la documentación del proyecto. | Ninguno |

## Sprint Review

| Historia | ¿Cumple la DoD? | Evidencia (captura / commit) | Visto bueno PO |
| --- | --- | --- | --- |
| HU-07 | ☑ Sí ☐ No | Commit: Sprint 3: HU-07 editar, eliminar y buscar ropa | Aprobado |
| HU-08 | ☑ Sí ☐ No | Commit: Sprint 3: HU-08 carrito de compras | Aprobado |
| HU-09 | ☑ Sí ☐ No | Commit: Sprint 3: HU-09 hacer pedido con teléfono | Aprobado |

## Retrospectiva

| ¿Qué funcionó? | ¿Qué mejorar? | Acción para el próximo sprint |
| --- | --- | --- |
| El uso de transacciones en SQLite para la integridad del pedido, la sincronización en tiempo real del estado del carrito y la reutilización del flujo de cliente. | Asegurar que las migraciones en `onUpgrade` mantengan intactos los datos de catálogo existentes al subir la versión de la BD. | Implementar el envío de pedidos por WhatsApp, la atención de pedidos por el administrador y los reportes para el Sprint 4. |
