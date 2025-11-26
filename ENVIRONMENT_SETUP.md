# Configuración de Variables de Entorno

## Variables Requeridas

Este proyecto requiere las siguientes variables de entorno:

- `MONGODB_URI`: URI de conexión a MongoDB Atlas
- `JWT_SECRET`: Clave secreta para JWT

## Configuración Local

1. Copia el archivo `.env.example` a `.env`:
   ```bash
   cp .env.example .env
   ```

2. Edita `.env` y agrega tus credenciales reales

## Configuración en Azure Web App

Para configurar las variables de entorno en Azure:

### Opción 1: Portal de Azure
1. Ve a tu Web App en el portal de Azure
2. Navega a **Configuration** > **Application settings**
3. Agrega las siguientes variables:
   - `MONGODB_URI` = tu URI de MongoDB
   - `JWT_SECRET` = tu clave JWT

### Opción 2: Azure CLI
```bash
az webapp config appsettings set --name forkGymnasiumService --resource-group <tu-resource-group> --settings MONGODB_URI="<tu-uri>" JWT_SECRET="<tu-secret>"
```

### Opción 3: GitHub Secrets (para CI/CD)
1. Ve a tu repositorio en GitHub
2. **Settings** > **Secrets and variables** > **Actions**
3. Agrega los secrets:
   - `MONGODB_URI`
   - `JWT_SECRET`

## ⚠️ IMPORTANTE - Seguridad

**NUNCA** hagas commit de archivos que contengan credenciales reales:
-  `.env` (ignorado por git)
-  `application.properties` con valores hardcodeados
-  `.env.example` (sin credenciales reales)

### Rotar Credenciales Comprometidas

Dado que las credenciales fueron expuestas en el repositorio:

1. **MongoDB**: Cambia la contraseña del usuario inmediatamente
   - Ve a MongoDB Atlas > Database Access
   - Edita el usuario y genera una nueva contraseña
   - Actualiza la variable `MONGODB_URI` en todos los ambientes

2. **JWT Secret**: Genera un nuevo secreto
   ```bash
   # Generar nuevo JWT secret (Linux/Mac)
   openssl rand -base64 64
   
   # O en PowerShell
   [Convert]::ToBase64String((1..64 | ForEach-Object { Get-Random -Maximum 256 }))
   ```
   - Actualiza `JWT_SECRET` en todos los ambientes
   -  Esto invalidará todos los tokens JWT existentes

3. **Limpia el historial de Git** (opcional pero recomendado):
   - Considera usar herramientas como `git filter-branch` o `BFG Repo-Cleaner`
   - O crea un nuevo repositorio limpio si es factible
