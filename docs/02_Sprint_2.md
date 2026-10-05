# Sprint 2 — Base de datos y catálogo

| | |
| --- | --- |
| **Objetivo del sprint** | La ropa se registra con foto en la base de datos y el cliente la ve en el catálogo por categoría. |
| **Duración** | 2 h 30 min |
| **Fechas** | Inicio: 05/10/2026 · Fin: 05/10/2026 |
| **Puntos comprometidos** | 16 |
| **Historias** | HU-04, HU-05, HU-06 |

## Sprint Backlog

### HU-04 · Base de datos y login con base de datos

- **Historia de usuario:** Como **administrador**, quiero **que los usuarios se guarden y validen en la base de datos del celular**, para no depender de credenciales escritas en el código.
- **Prioridad:** Alta (Must) · **Puntos:** 3 · **Sprint:** 2 · **Prototipo:** P2-01

**Criterios de aceptación**

1. **CA1.** Dado que instalo la app por primera vez, cuando se abre, entonces se crea `modaapp.db` con la tabla `usuario` y el usuario `admin / 1234` (rol ADMIN).
2. **CA2.** Dado que ingreso credenciales, cuando pulso «Ingresar», entonces se validan con una consulta parametrizada (`rawQuery` con `?`).
3. **CA3.** Dado que el login es correcto, cuando se abre el menú, entonces muestra el nombre y rol del usuario.
4. **CA4.** Dado que abro App Inspection → Database Inspector, cuando selecciono la BD, entonces veo la tabla `usuario` con sus registros.

**Tareas técnicas**

- ☐ Crear `data/DBHelper.kt` (SQLiteOpenHelper) con `DB_NAME = "modaapp.db"` y `DB_VERSION = 1`
- ☐ Crear la tabla `usuario` e insertar el admin en `onCreate`; activar FOREIGN KEY en `onConfigure`
- ☐ Agregar `validarUsuario(usuario, clave)` y usarla en `LoginActivity`
- ☐ Verificar la BD en Database Inspector

**Estado:** ☐ Por hacer · ☐ En curso · ☐ Hecho — Commit: ____________________

---

### HU-05 · Registrar ropa con foto

- **Historia de usuario:** Como **administrador**, quiero **registrar cada prenda con foto, categoría, talla, modelo, marca, color, cantidad y precio**, para publicarla en el catálogo.
- **Prioridad:** Alta · **Puntos:** 8 · **Sprint:** 2 · **Prototipo:** P2-03

**Criterios de aceptación**

1. **CA1.** Dado que toco «Elegir foto», cuando selecciono una imagen de la galería, entonces se muestra en el formulario y se copia a la carpeta interna de la app.
2. **CA2.** Dado que elijo categoría y talla en Spinners (categorías precargadas en la BD), cuando guardo, entonces la prenda queda asociada a esa categoría.
3. **CA3.** Dado que el modelo, la foto, el precio o la cantidad están vacíos, o el precio es menor o igual a 0, cuando pulso «Guardar», entonces se marca el error y no se guarda.
4. **CA4.** Dado que guardé prendas, cuando abro Ropa, entonces veo la lista con miniatura de foto, modelo, talla, color y cantidad.

**Tareas técnicas**

- ☐ Crear las tablas `categoria` (con 5 categorías insertadas en `onCreate`) y `ropa`
- ☐ Elegir la foto con `registerForActivityResult(ActivityResultContracts.PickVisualMedia())`
- ☐ Copiar la imagen a `filesDir` con `contentResolver.openInputStream` y guardar la ruta
- ☐ `RopaDao` (insertar, listar) y `RopaAdapter` con ImageView (`BitmapFactory.decodeFile`)

**Estado:** ☐ Por hacer · ☐ En curso · ☐ Hecho — Commit: ____________________

---

### HU-06 · Catálogo del cliente por categoría

- **Historia de usuario:** Como **cliente**, quiero **ver la ropa disponible filtrada por categoría**, para encontrar rápido lo que busco.
- **Prioridad:** Alta · **Puntos:** 5 · **Sprint:** 2 · **Prototipo:** P2-04

**Criterios de aceptación**

1. **CA1.** Dado que abro el catálogo, cuando carga, entonces veo las prendas en una grilla de 2 columnas con foto, modelo, talla y precio.
2. **CA2.** Dado que toco una categoría (chip), cuando cambia la selección, entonces solo se muestran las prendas de esa categoría; «Todas» muestra todo.
3. **CA3.** Dado que una prenda tiene cantidad 0, cuando veo el catálogo, entonces no aparece.

**Tareas técnicas**

- ☐ `RopaDao.listarDisponibles(idCategoria)` con `WHERE cantidad > 0`
- ☐ ChipGroup de categorías cargado desde la tabla `categoria`
- ☐ RecyclerView con `GridLayoutManager(this, 2)` e `item_catalogo.xml`

**Estado:** ☐ Por hacer · ☐ En curso · ☐ Hecho — Commit: ____________________

## Bitácora Daily

| Fecha | ¿Qué hice? | ¿Qué haré hoy? | ¿Qué me bloquea? |
| --- | --- | --- | --- |
| | | | |
| | | | |

## Sprint Review

| Historia | ¿Cumple la DoD? | Evidencia (captura / commit) | Visto bueno PO |
| --- | --- | --- | --- |
| HU-04 | ☐ Sí ☐ No | | |
| HU-05 | ☐ Sí ☐ No | | |
| HU-06 | ☐ Sí ☐ No | | |

## Retrospectiva

| ¿Qué funcionó? | ¿Qué mejorar? | Acción para el próximo sprint |
| --- | --- | --- |
| | | |
