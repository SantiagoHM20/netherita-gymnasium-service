# ECIBienestarGym
BackEnd del componente de Gimnasio del proyecto ECIBienestar

### Tecnologias

Lenguaje: Java 17
Construcción: Apache Maven 3.9.x.
Framework: SpringBoot 3.3.4.
Despliegue: AzureDevops.
Base de Datos: MongoDB.

### Módulo de Gestión de Gimnasio

Este módulo permite a los estudiantes reservar sesiones de entrenamiento, diseñar sus rutinas personalizadas y realizar un seguimiento detallado de su progreso físico.

Con el acompañamiento de un profesor, los usuarios reciben comentarios sobre su evolución, obtienen recomendaciones adaptadas a sus objetivos y acceden a sesiones estructuradas según sus necesidades. 

Además, los entrenadores pueden generar reportes de desempeño individuales y grupales, así como reportes sobre sesiones, gestionar rutinas y optimizar la experiencia de entrenamiento.

### Funcion del modulo

Para la funcionalidad del modulo estamos usando el patron de diseño de Modelo Vista Controlador,
usamos el modulo 6 de gestion de usuarios para manejar que funciones tiene el rol de administrador, entrenador y de 
estudiantes.

### Diagrama de datos:

![image](assets/Diagrama%20de%20datps.png)

### Diagrama de clases

![image](assets/Diagrama%20de%20clases.png)

### Diagrama de componentes

![image](assets/Diagrama%20de%20componentes.png)


### Funcionalidades

Las funcionalidades estan repartidos en difentes controladores con sus 
respectivos endpoints.

## Endpoints expuestos:

### GymSessionController:

Get/ getAllGymSessions: Obtener todas las sesiones del gimnasio.

Get/ getGymSessionsById: Obtener las sesiones del gimnasio, teniendo en cuenta el id de esa sesion
(Entrada: id, Salida: La session segun el id)

Get / getGymSessionByCoachId: Obtener las sesiones de gimnasio segun el entrenador que lo maneje
Entrada: coach, Salida: Las sesiones segun el coach buscado)

Get / getGymSessionByCapacity: Obtener las sesiones de gimnasio segun su capacidad.
Entrada: capacity, Salida: Las sesiones de gimnasio con la capacidad buscada)

Get /getGymSessionsBySchedule: Obtener la sesion, segun la fecha
8Entrada: schedule, Salida: Las sesiones de gimnasio con la fecha buscada)

### PhysicalProgressControler:

Get / getAllPhysicalProgress: Obtener todos los progresos fisicos

Get / getPhysicalProgressById: Obtener los progresos por su id
(Entrada: id, Salida: Obtener el progreso fisico segun el id del progreso)

Get / getPhysicalProgressByUserId: Obtener progresos segun el id de un usuario
(Entrada: user, Salida:El progresos segun el usuario que los halla hecho )

Get / getPhysicalProgressByRegistrationDate: Obtener progresos segun la fecha en los que se hayan creado
(Entrada: date, Salida: Progreso fisico segun la fecha buscada)


### ReportController:

Get / getAllReport: Obtener todos los reportes que halla hecho el entrenador y administrador

Get / getReportById: Obtener reportes por su ID
(Entrada: id, Salida: Reportes segun el id buscado)

Get / getReportsByCoach: Obtener reportes segun el entrenador que lo haya hecho
(Entrada: coach, Salida: Reportes segun el coahc buscado)

Get / getReportsByGeneratedAt: Obtener reportes segun su fecha
(Entrada: date, Salida: Reportes segun la fecha de creación)

Get / getReportsByType: Obtener reporte segun el tipo de reporte
(Entrada: type, Salida: Reportes segun el tipo especificado)

### ReservationController:

Get / getAllReservations: Obtener todas las reservas

Get / getReservationById: Obtner las reservaciones existentes segun el id buscado
(Entrada: id, Salida: Reservacion con la id buscada)

Get / getReservationsByUserId: Obtner reserva a la que este asociada el usuario
(Entrada: userId, Salida: Reservaciones en las que este el usuario)

Get / getReservationsByGymSession: Obtener las reservas por sesiones de gimnasio
(Entrada: gymSession, Salida: Reservas que tengan la sesion de gimnasio buscada)

Get / getReservationsByReservationDate: Obtener reservas segun la fecha de la reserva
(Entrada: ReservationDate, Salida: La reserva que tenga la fecha de reservacion)

Get / getReservationsByState: Obtener reservas segun su estado
(Entrada: status, Salida: Las reservas segun su estado )

### RoutineController:

Get / getAllRoutines: Obtener todas las rutinas

Get / getRoutineById: Obtener la rutina segun su id
(Entrada: id, Salida: Rutina segu la id buscada)

Get / getRoutinesByName: Obtener la rutina segun el nombre
(Entrada: name, Salida: Rutina segun el nombre de la rutina)

Get / getRoutinesByDifficulty: Obtener la rutina segun su difilcultad
(Entrada: level, Salida: Las rutinas con la dificultad asociada)

Get / getRoutinesByExercises: Obtener la turina si contiene un jercicio buscado
(Entrada: exercises, Salida: Las rutinas que contengan el ejercicio buscado)

### UserController:
Get / getAllUsers: Obtener todos los usuarios

Get / getUsersByName: Obtener usuario por nombre
(Entrada: name, Salida: El usuario segun el nombre dado)

Get / getUsersByEmail: Obtener usuario por email
(Entrada: email, Salida: El usuario segun el email)

Get / getUsersByRole: Obtener el usuario segun su rol
(Entrada: role,  Salida: Usuario segun su rol)

Get / getUsersByRegistrationDate: Obtener el usuario segun su fechya de registro
(Entrada: date, Salida: Usuarios segun su fecha de registro)
 
## Como ejecutar el proyecto

1: Clonar repositorio

git clone https://github.com/ECIBienestar/netherita-gymnasium-service.git

cd ECIBienestar

2 Correr poryecto con mvn 

mvn clean install

3: Iniciar el back:

Ir a EciBienestarGymApplication

Colocar en el terminal mvn spring-boot:run

