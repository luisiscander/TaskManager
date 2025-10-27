# GitHub Actions Workflow

El archivo .github/workflows/build.yml está incluido localmente pero requiere permisos adicionales para ser subido.

Para agregarlo manualmente:
1. Ve a tu repositorio en GitHub
2. Crea el archivo .github/workflows/build.yml
3. Copia el contenido del archivo local

O reautentica gh con workflow scope:
```bash
gh auth refresh -h github.com -s workflow
```
