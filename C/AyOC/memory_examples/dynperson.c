#include <stdint.h>
#include <string.h>
#include <stdlib.h>

typedef struct {
	char *nombre;
	uint8_t edad;
} persona_t;

persona_t *crear_persona(const char *nombre, uint8_t edad) {
	persona_t *ptr = malloc(sizeof(persona_t));

	if (ptr == NULL)
		return NULL;

	(*ptr).nombre = malloc(strlen(nombre) + 1);
	if ((*ptr).nombre == NULL) {
		free(ptr);
		return NULL;
	}

	strcpy((*ptr).nombre, nombre);
	(*ptr).edad = edad;

	return ptr;
}


void eliminar_persona(persona_t *persona) {
	free((*persona).nombre);
	free(persona);
	return;
}

int main(void) {
	persona_t *pers = crear_persona("Foo", 20);
	eliminar_persona(pers);
	return 0;
}
