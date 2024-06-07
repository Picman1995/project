import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { serviceGetPersons, serviceDeletePerson, serviceGetPersonByNroDocumento } from '../../services/personas/personas';
import '../../components/EstandarCSS/Listado.css';

const PersonasPage = () => {
  const [personas, setPersonas] = useState([]);
  const [searching, setSearching] = useState('');
  const [deleteId, setDeleteId] = useState(null);
  const [alertDelete, setAlertDelete] = useState(false);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await serviceGetPersons();
        if (response.data && Array.isArray(response.data.personas)) {
          setPersonas(response.data.personas);
        } else {
          console.error('La respuesta no contiene la propiedad "personas" o no es un array:', response.data);
        }
      } catch (error) {
        console.error('Error al obtener el listado de personas:', error);
      }
    };

    fetchData();
  }, []);

  const handleKeyDown = async (e) => {
    if (e.key === 'Enter') {
      // Verificamos si la búsqueda está vacía
      if (searching.trim() !== '') {
        try {
          const response = await serviceGetPersonByNroDocumento(searching);
          if (response.data && response.data.personas) {
            setPersonas(response.data.personas);
          } else {
            setPersonas([]);
          }
        } catch (error) {
          console.error('Error al buscar la persona:', error);
          setPersonas([]);
        }
      } else {
        // Si la búsqueda está vacía, volvemos a cargar todas las personas
        const response = await serviceGetPersons();
        if (response.data && Array.isArray(response.data.personas)) {
          setPersonas(response.data.personas);
        }
      }
    }
  };

  const handleDelete = async (id) => {
    try {
      await serviceDeletePerson(id);
      setAlertDelete(false);
      // Recargar la lista de personas después de eliminar
      const response = await serviceGetPersons();
      if (response.data && Array.isArray(response.data.personas)) {
        setPersonas(response.data.personas);
      }
    } catch (error) {
      console.error('Error al eliminar la persona:', error);
    }
  };



  return (
    <>
      <div className='contenido_general flex flex-col'>
        <div className='barra'>
          <div className='flex justify-between gap-2 my-3'>
            <p className='titulo_general'>Personas</p>
            <div className='flex gap-2'>
              <input
                type='text'
                placeholder='Buscar por número de cédula...'
                className='text-black w-full sm:w-[400px] p-2 items-center hover:bg-[#fff] rounded-full transition w-10 h-10'
                value={searching}
                onChange={(e) => setSearching(e.target.value)}
                onKeyDown={handleKeyDown}
              />
            </div>
            <Link
              to='/addpersona'
              className="cta-button_estandar bx bx-plus-medical"
            >
            </Link>
          </div>
        </div>

        <div className='table-container'>
          <table className='w-full text-[#1A1A1A]'>
            <thead>
              <tr className='text-left'>
                <th className='bg-degradado px-5 py-3'>Nombre</th>
                <th className='bg-degradado px-5 py-3'>Apellido</th>
                <th className='bg-degradado px-5 py-3'>Nro. Documento</th>
                <th className='bg-degradado px-5 py-3'>Ciudad</th>
                <th className='bg-degradado px-5 py-3'>Nacionalidad</th>
                <th className='bg-degradado px-5 py-3'>Tipo de Documento</th>
                <th className='bg-degradado px-5 py-3'>Estado Civil</th>
                <th className='bg-degradado px-5 py-3'>Tipo de Persona</th>
                <th className='bg-degradado rounded-tr-lg sm:rounded-tr-md px-5 py-3 text-center'>Acciones</th>
              </tr>
            </thead>
            <tbody>
              {personas.map((persona) => (
                <tr key={persona.id} className='sm:border-b-2 last:border-b-0'>
                  <td className='px-5 py-5'>{persona.nombre1} {persona.nombre2} {persona.nombre3}</td>
                  <td className='px-5 py-5'>{persona.apellido1} {persona.apellido2} {persona.apellido3}</td>
                  <td className='px-5 py-5'>{persona.nroDocumento}</td>
                  <td className='px-5 py-5'>{persona.ciudad}</td>
                  <td className='px-5 py-5'>{persona.nacionalidad}</td>
                  <td className='px-5 py-5'>{persona.tipoDocumento}</td>
                  <td className='px-5 py-5'>{persona.estadoCivil}</td>
                  <td className='px-5 py-5'>{persona.tipoPersona}</td>
                  <td className='px-5 py-3'>
                    <div className='flex items-center justify-center'>
                    <Link
  to={{
    pathname: `/editpersona/${persona.id}`,
    state: { persona },
  }}
  className="bg-custom-naranja hover:bg-custom-naranja-claro text-white font-bold py-2 px-4 rounded inline-flex items-center"
>
  Editar
</Link>

                      <button
                        onClick={() => {
                          setDeleteId(persona.id);
                          setAlertDelete(true);
                        }}
                        className="bg-custom-black hover:bg-gray-700 text-white font-bold py-2 px-4 rounded inline-flex items-center ml-2"
                      >
                        Eliminar
                      </button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>

        {personas.length === 0 && (
          <div className="flex justify-center text-center w-full">
            <p className="my-7 text-black text-center flex justify-center w-full">
              No se encontraron personas
            </p>
          </div>
        )}

        {alertDelete && (
          <div className="fixed inset-0 bg-gray-600 bg-opacity-50 flex items-center justify-center">
            <div className="bg-white p-4 rounded-lg">
              <p>¿Estás seguro de eliminar esta persona?</p>
              <div className="flex justify-end mt-4">
                <button
                  className="text-white bg-red-500 hover px-4 py-2 rounded-md mr-2"
                  onClick={() => {
                    handleDelete(deleteId);
                    setAlertDelete(false);
                  }}
                >
                  Sí, eliminar
                </button>
                <button
                  className="text-black bg-gray-300 hover px-4 py-2 rounded-md"
                  onClick={() => setAlertDelete(false)}
                >
                  Cancelar
                </button>
              </div>
            </div>
          </div>
        )}
      </div>
    </>
  );
};

export default PersonasPage;
