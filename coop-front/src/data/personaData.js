const personasResponse = {
  header: {
    codResultado: 200,
    txtResultado: "SUCCESS"
  },
  data: {
    personas: [
      {
        id: 7,
        nroDocumento: "44",
        nombres: "sssss  sss",
        apellidos: "sss  ss",
        fechaNacimiento: "31/01/1995",
        sexo: "M",
        tipoDocumento: "Cédula de Identidad",
        tipoPersona: "Descripción del tipo de persona",
        estadoCivil: "Soltero",
        ciudad: "Asunción",
        nacionalidad: "PARAGUAYA"
      }
    ],
    paginacion: {
      totalItems: 5,
      totalPages: 5,
      currentPages: 1
    }
  }
};

// Accede a personas dentro del objeto data de la respuesta
const personas = personasResponse.data.personas;

const personasFiltradas = personas.filter(persona => {
  // Supongamos que fechaNacimiento es de la forma "dd/mm/yyyy"
  const fechaNacimiento = persona.fechaNacimiento;
  const parts = fechaNacimiento.split('/');
  const year = parseInt(parts[2], 10);
  const currentYear = new Date().getFullYear();
  return currentYear - year > 18;
});


console.log(personasFiltradas);
