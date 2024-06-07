import { SearchBar } from '../../components/SearchBar';
import { EditIcon, MoreOptionsIcon } from '../../components/Icons';
import { Menu, Transition } from '@headlessui/react';
import { Fragment, useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useNavigate } from 'react-router-dom';


import { serviceGetPersons, serviceDeletePerson } from '../../services/personas/personas'; // Importa la función serviceDeletePerson
import '../DireccionesPage/style.css'

const DireccionesPage = () => {
  const navigate = useNavigate();

  const [personas, setPersonas] = useState([]);
  const [searching, setSearching] = useState('');
  const [deleteId, setDeleteId] = useState(null); // Agrega estado para almacenar el ID de la persona a eliminar
  const [alertDelete, setAlertDelete] = useState(false); // Estado para mostrar una alerta de eliminación

  const handleSearchChange = (e) => setSearching(e.target.value);

  useEffect(() => {
    const fetchPersonas = async () => {
      try {
        const response = await serviceGetPersons();
        const { personas } = response.data;
        setPersonas(personas);
      } catch (error) {
        console.error('Error al obtener el listado de personas:', error);
      }
    };
  
    fetchPersonas();
  }, []);

  const personasFound = personas.filter((persona) => {
    const nombreCompleto = `${persona.nombre1} ${persona.apellido1}`.toLowerCase();
    const cedulaMatch = persona.numeroCedula && persona.numeroCedula.toLowerCase().includes(searching.toLowerCase());
    return nombreCompleto.includes(searching.toLowerCase()) || cedulaMatch;
  });
  
  

  // Función para manejar la eliminación de una persona
  const handleDelete = async () => {
    try {
      await serviceDeletePerson(deleteId);
      setDeleteId(null); // Reinicia el ID de la persona a eliminar
      setAlertDelete(false); // Oculta la alerta de eliminación
      // Actualiza la lista de personas después de eliminar
      const response = await serviceGetPersons();
      const { personas } = response.data;
      setPersonas(personas);
    } catch (error) {
      console.error('Error al eliminar la persona:', error);
    }
  };

  return (
    <>
    <div className='contenido_persona'>
    <div className='hidden sm:flex flex-row justify-between text-custom-black'>
        <h2>Cooperativa 8 de Marzo Ltda</h2>
        <h2>Inova8M</h2>
      </div>
  
      <hr className='hidden sm:flex border-t-1 border-[#898AA3] mb-3' />
  
      <p className='text-2xl font-[500] text-custom-icon'>Direcciones</p>
  
      <div className='flex justify-between gap-2 my-5'>
        <SearchBar
          placeholder='Buscar...'
          className='w-full sm:w-[424px]'
          searching={searching}
          setSearching={setSearching}
          searchTye='personas'
          products={personas}
        >
          <button
            className='hidden sm:flex p-2 items-center hover:bg-[#B8B9CF] rounded-full transition w-8 h-8'
            onClick={() => setSearching('')}
          ></button>
        </SearchBar>
        <Link
          to='/AddDireccion'
          className="text-white text-xs text-center min-w-[100px] bg-black rounded-lg px-2 py-1 sombra"
        >
          Agregar Direccion
        </Link>
      </div>
  
      <div className='table-container border sm:border-4 border-custom-naranja'>
        <table className='w-full text-[#1A1A1A]'>
          <thead>
            <tr className='text-left'>
              <th className='bg-gradient-header min-w-[168px] px-5 py-3'>
                Nombres
              </th>
              <th className='bg-gradient-header px-5 py-3'>Apellidos</th>
              <th className='bg-gradient-header px-5 py-3'>Fecha de Nacimiento</th>
              <th className='bg-gradient-header px-5 py-3'>Sexo</th>
              <th className='bg-gradient-header px-5 py-3'>Tipo de Documento</th>
              <th className='bg-gradient-header px-5 py-3'>Tipo de Persona</th>
              <th className='bg-gradient-header px-5 py-3'>Estado Civil</th>
              <th className='bg-gradient-header px-5 py-3'>Ciudad</th>
              <th className='bg-gradient-header text-center'>
                Nacionalidad
              </th>
              <th className='bg-gradient-header px-5 py-3 text-center'></th>
            </tr>
          </thead>
  
          <tbody>
            {personasFound.map((persona) => (
              <tr
                key={persona.id}
                className='border-b sm:border-b-4 border-custom-button-hover last:border-b-0'
              >
                <td className='px-5 py-3 flex items-center min-w-[168px]'>
                  {persona.nombre1} {persona.nombre2} {persona.nombre3}
                </td>
                <td className='px-5 py-3'>{persona.apellido1} {persona.apellido2} {persona.apellido3}</td>
                <td className='px-5 py-3'>{persona.fechaNacimiento}</td>
                <td className='px-5 py-3'>{persona.sexo}</td>
                <td className='px-5 py-3'>{persona.tipoDocumento}</td>
                <td className='px-5 py-3'>{persona.tipoPersona}</td>
                <td className='px-5 py-3'>{persona.estadoCivil}</td>
                <td className='px-5 py-3'>{persona.ciudad}</td>
                <td className='px-5 py-3'>{persona.nacionalidad}</td>
                <td className='px-5 py-3'>
                  <div className='flex items-center justify-center'>
                    <Menu as='div' className='relative'>
                      <Menu.Button
                        aria-label='Mas opciones'
                        className='text-custom-blue'
                      >
                        <EditIcon className='hidden sm:inline-block' />
                        <MoreOptionsIcon className='sm:hidden' />
                      </Menu.Button>
  
                      <Transition
                        as={Fragment}
                        enter='transition ease-out duration-100'
                        enterFrom='transform opacity-0 scale-95'
                        enterTo='transform opacity-100 scale-100'
                        leave='transition ease-in duration-75'
                        leaveFrom='transform opacity-100 scale-100'
                        leaveTo='transform opacity-0 scale-95'
                      >
<Menu.Items className='absolute top-full mt-2 right-0 bg-[#E7E7E7] flex flex-col rounded shadow z-10 min-w-[130px]'>
  <Menu.Item>
    {({ active }) => (
      <Link
        to={`/personas/edit/${persona.id}`}
        className={`text-left px-4 py-2 ${active ? 'hover:text-custom-blue' : ''}`}
      >
        Editar
      </Link>
    )}
  </Menu.Item>
  <Menu.Item>
    {({ active }) => (
      <button
        onClick={() => {
          setDeleteId(persona.id);
          setAlertDelete(true);
        }}
        className={`text-left px-4 py-2 ${active ? 'hover:text-custom-blue' : ''}`}
      >
        Eliminar
      </button>
    )}
  </Menu.Item>
</Menu.Items>
</Transition>
</Menu>
</div>
</td>
</tr>
))}
</tbody>
</table>
</div>

{personasFound.length === 0 && (
<div className="flex justify-center text-center w-full">
  <p className="my-7 text-black text-center flex justify-center w-full">
    No se encontraron Personas
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
          handleDelete(); // Llama a la función handleDelete al confirmar la eliminación
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

export default DireccionesPage;