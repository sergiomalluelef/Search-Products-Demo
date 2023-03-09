
![State](https://img.shields.io/badge/kotlin-v1.7.0-blueviolet)
![State](https://img.shields.io/badge/gradle-v7.5-blue)
![State](https://img.shields.io/badge/retrofit-v2.9.0-brightgreen)
![State](https://img.shields.io/badge/hilt-v2.44-brightgreen)
![State](https://img.shields.io/badge/compose-v1.3.3-brightgreen)
![State](https://img.shields.io/badge/mockk-v1.12.0-brightgreen)

# Search-Products-Demo

Aplicación demo que permite buscar-listar `Accesorios para vehículos` y ver los detalles de los ítems.

El proyecto se separa en 3 capas:

- data
- presentation
- ui

## Capa de Data

Para la capa de `Data` solo se aborda con peticiones a remote sin datos guardados en caché como datastore o room.


Se utiliza `retrofit` para declarar los endpoint y la recepción de contratos. Las funciones de remote se abstraen del repositorio a través de interfaces y `hilt`. El repositorio es el encargado de ser la entrada a la capa de data desde presentation.

## Capa de Presentation

En la capa de `Presentation` se utiliza `MVI` para hacer una interacción unidireccional de datos y mantener la consistencia a través de los estados de esta misma. Siendo intermediario entre la capa de `UI` y `Data`.

Con esto se observan las intenciones de usuarios los cuales viajan hasta el processor y aplica lógica contra el Repositorio de Data, con esto se devuelve información hasta la capa de `UI` nuevamente.

### Flujo MVI

```intent -> action -> processor -> result -> reducer -> uistate```

## Capa de UI

En la capa de UI se observan los cambios de estado generados por la capa de presentation y mvi.

La capa de UI es construida por medio de `Compose`.

## Generación de APK

Para la generación de APK y pruebas se debe utilizar la opción simple sin ingresos de key.

Para esto ir a la opción de Android Studio en barra superior:

```Build -> Build Bundle(s) / APK(s) -> Build APK(s)```

## Imágenes de la Demo
<img src="https://user-images.githubusercontent.com/38668715/224065290-07ca1806-167b-4e03-9cc9-f0c58e8ab4b7.png" width="300"> <img src="https://user-images.githubusercontent.com/38668715/224067040-bba5c96e-4c89-481d-a8bb-248a6749c1ea.png" width="300">

<img src="https://user-images.githubusercontent.com/38668715/224065295-d8bf3c37-e2ce-4a37-930f-5c979b2fa632.png" width="300"> <img src="https://user-images.githubusercontent.com/38668715/224065273-b4099119-77c3-4b21-90a1-a09a2179c03e.png" width="300">

<img src="https://user-images.githubusercontent.com/38668715/224065280-2902e60c-8c42-4d35-8e2e-3a8bf2024d55.png" width="300"> <img src="https://user-images.githubusercontent.com/38668715/224065283-c8a43928-debc-439b-ae1b-4ea16134fb37.png" width="300">

## Video Demo

https://user-images.githubusercontent.com/38668715/224071524-78533339-22a3-439e-97e6-8470441a7f4d.mp4






