import { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { serviceGetCiudades, serviceDeleteCiudades, serviceEditCiudades } from '../../services/ciudades/ciudades';
import { serviceGetDistritos } from '../../services/distritos/distritos';
import '../../components/EstandarCSS/Listado.css';

const CiudadesPage = () => {
  const navigate = useNavigate();

  const [ciudades, setCiudades] = useState([]);
  const [searching, setSearching] = useState('');
  const [deleteId, setDeleteId] = useState(null);
  const [alertDelete, setAlertDelete] = useState(false);
  const [editingMode, setEditingMode] = useState(false);
  const [editedCiudad, setEditedCiudad] = useState(null);
  const [editedDescription, setEditedDescription] = useState('');
  const [distritos, setDistritos] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const responseCiudades = await serviceGetCiudades();
        if (responseCiudades.data && Array.isArray(responseCiudades.data.ciudades)) {
          setCiudades(responseCiudades.data.ciudades);
        } else {
          console.error('La respuesta no contiene la propiedad "ciudades" o no es un array:', responseCiudades.data);
        }

        const responseDistritos = await serviceGetDistritos();
        if (responseDistritos.data && Array.isArray(responseDistritos.data.distritos)) {
          setDistritos(responseDistritos.data.distritos);
        } else {
          console.error('La respuesta no contiene la propiedad "distritos" o no es un array:', responseDistritos.data);
        }
      } catch (error) {
        console.error('Error al obtener el listado de ciudades o distritos:', error);
      }
    };

    fetchData();
  }, []);

  const handleSearchChange = (e) => setSearching(e.target.value);

  const handleDelete = async () => {
    try {
      await serviceDeleteCiudades(deleteId);
      setDeleteId(null);
      setAlertDelete(false);
      const response = await serviceGetCiudades();
      if (response.data && Array.isArray(response.data.ciudades)) {
        setCiudades(response.data.ciudades);
      }
    } catch (error) {
      console.error('Error al eliminar la ciudad:', error);
    }
  };

  const handleEdit = (ciudad) => {
    setEditedCiudad(ciudad);
    setEditedDescription(ciudad.descripcion);
    setEditingMode(true);
  };

  const handleSaveEdit = async () => {
    try {
      const upperCaseDescription = editedDescription.toUpperCase();
      await serviceEditCiudades({ id: editedCiudad.id, descripcion: upperCaseDescription });
      const response = await serviceGetCiudades();
      if (response.data && Array.isArray(response.data.ciudades)) {
        setCiudades(response.data.ciudades);
      }
      setEditingMode(false);
    } catch (error) {
      console.error('Error al guardar los cambios de la ciudad:', error);
    }
  };

  const handleCancelEdit = () => {
    setEditingMode(false);
    setEditedCiudad(null);
    setEditedDescription('');
  };

  const ciudadesFiltered = ciudades.filter((ciudad) => {
    return ciudad.descripcion.toLowerCase().includes(searching.toLowerCase());
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
            <p className='titulo_general'>Ciudades</p>
            <input
              type='text'
              placeholder='Buscar...'
              className='text-black w-full sm:w-[400px] hidden sm:flex p-2 items-center hover:bg-[#fff] rounded-full transition w-10 h-10'
              value={searching}
              onChange={handleSearchChange}
            />
            <Link
              to='/addciudad'
              className="cta-button_estandar bx bx-plus-medical"
            >
            </Link>
          </div>
        </div>

        <div className='table-container'>
          <table className='w-full text-[#1A1A1A]'>
            <thead>
              <tr className='text-left'>
                <th className='bg-degradado px-5 py-3'>
                  Ciudad
                </th>
                <th className='bg-degradado px-5 py-3'>
                  Distrito
                </th>
                <th className='bg-degradado rounded-tr-lg sm:rounded-tr-md px-5 py-3 text-center'>Acciones</th>
              </tr>
            </thead>

            <tbody>
              {ciudadesFiltered.map((ciudad) => (
                <tr
                  key={ciudad.id}
                  className='sm:border-b-2 last:border-b-0'
                >
                  <td className='px-5 py-5 flex items-center min-w-[168px]'>
                    {editingMode && editedCiudad && editedCiudad.id === ciudad.id ? (
                      <input
                        type="text"
                        value={editedDescription}
                        onChange={(e) => setEditedDescription(e.target.value)}
                        className="border border-gray-300 rounded-lg p-2"
                      />
                    ) : (
                      <span>{ciudad.descripcion}</span>
                    )}
                  </td>
                  <td className='px-5 py-5'>
                    {ciudad.distrito}
                  </td>
                  <td className='px-5 py-3'>
                    <div className='flex items-center justify-center'>
                      {!editingMode && (
                        <button
                          onClick={() => handleEdit(ciudad)}
                          className="bg-custom-naranja hover:bg-custom-naranja-claro text-white font-bold py-2 px-4 rounded inline-flex items-center"
                          >
                            Editar
                          </button>
                        )}
                        {editingMode && editedCiudad && editedCiudad.id === ciudad.id && (
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
                            onClick={() => {
                              setDeleteId(ciudad.id);
                              setAlertDelete(true);
                            }}
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
  
          {ciudadesFiltered.length === 0 && (
            <div className="flex justify-center text-center w-full">
              <p className="my-7 text-black text-center flex justify-center w-full">
                No se encontraron Ciudades
              </p>
            </div>
          )}
  
          {alertDelete && (
            <div className="fixed inset-0 bg-gray-600 bg-opacity-50 flex items-center justify-center">
              <div className="bg-white p-4 rounded-lg">
                <p>¿Estás seguro de eliminar esta ciudad?</p>
                <div className="flex justify-end mt-4">
                  <button
                    className="text-white bg-red-500 hover px-4 py-2 rounded-md mr-2"
                    onClick={() => {
                      handleDelete();
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
  
  export default CiudadesPage;
