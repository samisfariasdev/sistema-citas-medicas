=== MENÚ PRINCIPAL ===
1. Registrar doctor
2. Registrar paciente
3. Agendar cita
4. Listar citas
5. Salir
Registrar doctor: ingresa nombre y especialidad.

Registrar paciente: ingresa nombre y edad.

Agendar cita: elige doctor y paciente (por número) y escribe fecha/hora dd/MM/yyyy HH:mm.

Listar citas: muestra todas las citas guardadas.

Salir: termina la aplicación.

Créditos
Desarrollado por samisfariasdev para la materia de Programación Orientada a Objetos.

Licencia
Este proyecto está bajo licencia MIT.

sql
Copiar
Editar

Luego, en Git Bash:

```bash
git add README.md
git commit -m "Agregar README con instrucciones básicas"
git push origin develop
Si quieres que quede en master, fusiona y crea un nuevo tag:

bash
Copiar
Editar
git checkout master
git merge develop
git push origin master
git tag -a v1.1 -m "v1.1: añadir README"
git push origin v1.1
