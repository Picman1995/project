import { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { serviceGetDistritos, serviceDeleteDistritos, serviceEditDistritos } from '../../services/distritos/distritos';
import { serviceGetDepartamento } from '../../services/departamentos/departamentos';
import '../../components/EstandarCSS/Listado.css';

const DistritosPage = () => {
  const navigate = useNavigate();

  const [distritos, setDistritos] = useState([]);
  const [searching, setSearching] = useState('');
  const [deleteId, setDeleteId] = useState(null);
  const [alertDelete, setAlertDelete] = useState(false);
  const [editingMode, setEditingMode] = useState(false);
  const [editedDistrito, setEditedDistrito] = useState(null);
  const [editedDescription, setEditedDescription] = useState('');
  const [departamentos, setDepartamentos] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const responseDistritos = await serviceGetDistritos();
        const { distritos } = responseDistritos.data;
        setDistritos(distritos);

        const responseDepartamentos = await serviceGetDepartamento();
        const { departamentos } = responseDepartamentos.data;
        setDepartamentos(departamentos);
      } catch (error) {
        console.error('Error al obtener el listado de distritos o departamentos:', error);
      }
    };

    fetchData();
  }, []);

  const handleSearchChange = (e) => setSearching(e.target.value);

  const handleDelete = async () => {
    try {
      await serviceDeleteDistritos(deleteId);
      setDeleteId(null);
      setAlertDelete(false);
      const response = await serviceGetDistritos();
      const { distritos } = response.data;
      setDistritos(distritos);
    } catch (error) {
      console.error('Error al eliminar el distrito:', error);
    }
  };

  const handleEdit = (distrito) => {
    setEditedDistrito(distrito);
    setEditedDescription(distrito.descripcion);
    setEditingMode(true);
  };

  const handleSaveEdit = async () => {
    try {
      const upperCaseDescription = editedDescription.toUpperCase();
      await serviceEditDistritos({ id: editedDistrito.id, descripcion: upperCaseDescription });
      const response = await serviceGetDistritos();
      const { distritos } = response.data;
      setDistritos(distritos);
      setEditingMode(false);
    } catch (error) {
      console.error('Error al guardar los cambios del distrito:', error);
    }
  };

  const handleCancelEdit = () => {
    setEditingMode(false);
    setEditedDistrito(null);
    setEditedDescription('');
  };

  const distritosFiltered = distritos.filter((distrito) => {
    return distrito.descripcion.toLowerCase().includes(searching.toLowerCase());
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
            <p className='titulo_general'>Distritos</p>
            <input
              type='text'
              placeholder='Buscar...'
              className='text-black w-full sm:w-[400px] hidden sm:flex p-2 items-center hover:bg-[#fff] rounded-full transition w-10 h-10'
              value={searching}
              onChange={handleSearchChange}
            />
            <Link
              to='/adddistrito'
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
                  Distrito
                </th>
                <th className='bg-degradado px-5 py-3'>
                  Departamento
                </th>
                <th className='bg-degradado rounded-tr-lg sm:rounded-tr-md px-5 py-3 text-center'>Acciones</th>
              </tr>
            </thead>

            <tbody>
              {distritosFiltered.map((distrito) => (
                <tr
                  key={distrito.id}
                  className='sm:border-b-2 last:border-b-0'
                >
                  <td className='px-5 py-5 flex items-center min-w-[168px]'>
                    {editingMode && editedDistrito && editedDistrito.id === distrito.id ? (
                      <input
                        type="text"
                        value={editedDescription}
                        onChange={(e) => setEditedDescription(e.target.value)}
                        className="border border-gray-300 rounded-lg p-2"
                      />
                    ) : (
                      <span>{distrito.descripcion}</span>
                    )}
                  </td>
                  <td className='px-5 py-5'>
                    {distrito.departamento}
                  </td>
                  <td className='px-5 py-3'>
                    <div className='flex items-center justify-center'>
                      {!editingMode && (
                        <button
                          onClick={() => handleEdit(distrito)}
                          className="bg-custom-naranja hover:bg-custom-naranja-claro text-white font-bold py-2 px-4 rounded inline-flex items-center"
                          >
                            Editar
                          </button>
                        )}
                        {editingMode && editedDistrito && editedDistrito.id === distrito.id && (
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
                              setDeleteId(distrito.id);
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
  
          {distritosFiltered.length === 0 && (
            <div className="flex justify-center text-centerw-full">
              <p className="my-7 text-black text-center flex justify-center w-full">
                No se encontraron Distritos
              </p>
            </div>
          )}
  
          {alertDelete && (
            <div className="fixed inset-0 bg-gray-600 bg-opacity-50 flex items-center justify-center">
              <div className="bg-white p-4 rounded-lg">
                <p>¿Estás seguro de eliminar este distrito?</p>
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
  
  export default DistritosPage;
  
