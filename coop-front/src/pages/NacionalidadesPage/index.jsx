import { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import { serviceGetNacionalidades, serviceDeleteNacionalidades, serviceEditNacionalidades } from '../../services/nacionalidades/nacionalidades';

const NacionalidadesPage = () => {
  const [nacionalidades, setNacionalidades] = useState([]);
  const [searching, setSearching] = useState('');
  const [editingMode, setEditingMode] = useState(false);
  const [editedNacionalidad, setEditedNacionalidad] = useState(null);
  const [editedDescription, setEditedDescription] = useState('');

  useEffect(() => {
    const fetchNacionalidades = async () => {
      try {
        const response = await serviceGetNacionalidades();
        const { nacionalidades } = response.data;
        setNacionalidades(nacionalidades);
      } catch (error) {
        console.error('Error al obtener el listado de nacionalidades:', error);
      }
    };

    fetchNacionalidades();
  }, []);

  const handleSearchChange = (e) => setSearching(e.target.value);

  const handleDelete = async (nacionalidadId) => {
    try {
      await serviceDeleteNacionalidades(nacionalidadId);
      const response = await serviceGetNacionalidades();
      const { nacionalidades } = response.data;
      setNacionalidades(nacionalidades);
      // Aquí podrías mostrar alguna notificación de éxito
    } catch (error) {
      console.error('Error al eliminar la nacionalidad:', error);
      // Aquí podrías mostrar alguna notificación de error
    }
  };

  const confirmDelete = (nacionalidadId) => {
    // Aquí podrías mostrar un modal de confirmación antes de eliminar
    if (window.confirm('¿Estás seguro de eliminar esta nacionalidad?')) {
      handleDelete(nacionalidadId);
    }
  };

  const handleEdit = (nacionalidad) => {
    setEditedNacionalidad(nacionalidad);
    setEditedDescription(nacionalidad.descripcion);
    setEditingMode(true);
  };

  const handleSaveEdit = async () => {
    try {
      await serviceEditNacionalidades({ id: editedNacionalidad.id, descripcion: editedDescription });
      const response = await serviceGetNacionalidades();
      const { nacionalidades } = response.data;
      setNacionalidades(nacionalidades);
      setEditingMode(false);
      // Aquí podrías mostrar alguna notificación de éxito
    } catch (error) {
      console.error('Error al guardar los cambios de la nacionalidad:', error);
      // Aquí podrías mostrar alguna notificación de error
    }
  };

  const handleCancelEdit = () => {
    setEditingMode(false);
    setEditedNacionalidad(null);
    setEditedDescription('');
  };

  const nacionalidadesFiltered = nacionalidades.filter((nacionalidad) => {
    return nacionalidad.descripcion.toLowerCase().includes(searching.toLowerCase());
  });

  return (
<>
  <div className='contenido_general flex flex-col'>
    <div className='hidden sm:flex flex-row justify-between text-custom-black'>
      <h2>Cooperativa 8 de Marzo Ltda</h2>
      <h2>Inova8M</h2>
    </div>

    <hr className='hidden sm:flex border-t-1 border-[#898AA3] mb-3' />

    <div className='barra'>
      <div className='flex justify-between gap-2 my-3'>
        <p className='titulo_general'>Nacionalidades</p>
        <input
          type='text'
          placeholder='Buscar...'
          className='text-black w-full sm:w-[400px] hidden sm:flex p-2 items-center hover:bg-[#fff] rounded-full transition w-10 h-10 '
          value={searching}
          onChange={handleSearchChange}
        />
        <Link
          to='/addnacionalidad'
          className="cta-button_estandar bx bx-plus-medical"
        >
          {/* Icono o texto para añadir nueva nacionalidad */}
        </Link>
      </div>
    </div>

    <div className='table-container'>
      <table className='w-full text-[#1A1A1A]'>
        <thead>
          <tr className='text-left'>
            <th className='bg-degradado px-5 py-3'>
              Descripción
            </th>
            <th className='bg-degradado rounded-tr-lg sm:rounded-tr-md px-5 py-3 text-center'>Acciones</th>
          </tr>
        </thead>

        <tbody>
          {nacionalidadesFiltered.map((nacionalidad) => (
            <tr
              key={nacionalidad.id}
              className='sm:border-b-2 last:border-b-0'
            >
              <td className='px-5 py-5 flex items-center min-w-[168px] '>
                {editingMode && editedNacionalidad && editedNacionalidad.id === nacionalidad.id ? (
                  <input
                    type="text"
                    value={editedDescription}
                    onChange={(e) => setEditedDescription(e.target.value)}
                  />
                ) : (
                  <span>{nacionalidad.descripcion}</span>
                )}
              </td>
              <td className='px-5 py-3'>
                <div className='flex items-center justify-center'>
                  {!editingMode && (
                    <button
                      onClick={() => handleEdit(nacionalidad)}
                      className="bg-custom-naranja hover:bg-custom-naranja-claro text-white font-bold py-2 px-4 rounded inline-flex items-center"
                    >
                      Editar
                    </button>
                  )}
                  {editingMode && editedNacionalidad && editedNacionalidad.id === nacionalidad.id && (
                    <>
                      <button
                        onClick={handleSaveEdit}
                        className="bg-custom-naranja hover:bg-custom-naranja-claro text-white font-bold py-2 px-4 rounded inline-flex items-center"
                      >
                        Guardar
                      </button>
                      <button
                        onClick={handleCancelEdit}
                        className="bg-custom-black hover:bg-gray-700 text-white font-bold py-2 px-4 rounded inline-flex items-center ml-2"
                      >
                        Cancelar
                      </button>
                    </>
                  )}
                  {!editingMode && (
                    <button
                      onClick={() => confirmDelete(nacionalidad.id)}
                      className="bg-custom-black hover:bg-gray-700 text-white font-bold py-2 px-4 rounded inline-flex items-center ml-2"
                    >
                      Eliminar
                    </button>
                  )}
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>

    {nacionalidadesFiltered.length === 0 && (
      <div className="flex justify-center text-center w-full">
        <p className="my-7 text-black text-center flex justify-center w-full">
          No se encontraron nacionalidades
        </p>
      </div>
    )}
  </div>
</>

  );
};

export default NacionalidadesPage;
