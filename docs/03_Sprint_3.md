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

- ☐ `obtener()`, `actualizar()`, `eliminar()`, `listar(filtro)` en `RopaDao`
- ☐ Modo edición con `putExtra("id")` y cambio opcional de foto
- ☐ AlertDialog + `SQLiteConstraintException`
- ☐ Buscador con `LIKE` en modelo, marca y color

**Estado:** ☐ Por hacer · ☐ En curso · ☐ Hecho — Commit: ____________________

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

- ☐ `object Carrito` (singleton) con lista mutable de `ItemCarrito(ropa, cantidad)`
- ☐ Ícono de carrito con contador en `CatalogoActivity`
- ☐ `CarritoActivity` con RecyclerView y total

**Estado:** ☐ Por hacer · ☐ En curso · ☐ Hecho — Commit: ____________________

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

- ☐ `DB_VERSION = 2` con tablas `cliente`, `pedido` y `detalle_pedido` (`onUpgrade`)
- ☐ `ClienteDao.buscarPorTelefono()` e `insertar()`
- ☐ `PedidoDao.registrar(idCliente, items)` con `beginTransaction` / `setTransactionSuccessful` / `endTransaction`
- ☐ `PedidoActivity`: teléfono y, si es nuevo, nombres y apellidos (visibility GONE/VISIBLE)

**Estado:** ☐ Por hacer · ☐ En curso · ☐ Hecho — Commit: ____________________

## Bitácora Daily

| Fecha | ¿Qué hice? | ¿Qué haré hoy? | ¿Qué me bloquea? |
| --- | --- | --- | --- |
| | | | |
| | | | |

## Sprint Review

| Historia | ¿Cumple la DoD? | Evidencia (captura / commit) | Visto bueno PO |
| --- | --- | --- | --- |
| HU-07 | ☐ Sí ☐ No | | |
| HU-08 | ☐ Sí ☐ No | | |
| HU-09 | ☐ Sí ☐ No | | |

## Retrospectiva

| ¿Qué funcionó? | ¿Qué mejorar? | Acción para el próximo sprint |
| --- | --- | --- |
| | | |
