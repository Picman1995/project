import { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { 
  serviceGetEstCivil, 
  serviceDeleteEstCivil, 
  serviceEditEstCivil 
} from '../../services/estadosCiviles/estadosCiviles';
import Alertas from '../../components/Alertas/Alertas';
import '../../components/EstandarCSS/Listado.css';

const EstadosCivilesPage = () => {

  const E = "estado civil";
  const navigate = useNavigate();

  const [estadosCiviles, setEstadosCiviles] = useState([]);
  const [searching, setSearching] = useState('');
  const [editingMode, setEditingMode] = useState(false);
  const [editedEstCivil, setEditedEstCivil] = useState(null);
  const [editedDescription, setEditedDescription] = useState('');

  useEffect(() => {
    const fetchEstadosCiviles = async () => {
      try {
        const response = await serviceGetEstCivil();
        if (response.data && response.data.estadoCiviles) {
          setEstadosCiviles(response.data.estadoCiviles);
        } else {
          console.error('Formato de respuesta inesperado:', response.data);
        }
      } catch (error) {
        console.error('Error al obtener el listado de estados civiles:', error);
      }
    };

    fetchEstadosCiviles();
  }, []);

  const handleSearchChange = (e) => setSearching(e.target.value);

  const confirmDelete = (estCivilId) => {
    console.log('ID del estado civil a eliminar:', estCivilId); // Nuevo registro
    Alertas.confirm(E, () => handleDelete(estCivilId));
  };
  
  const handleDelete = async (estCivilId) => {
    console.log('Entrando a handleDelete con ID:', estCivilId); // Nuevo registro
    
    if (!estCivilId) {
      console.error('El ID del estado civil es nulo o indefinido.');
      return;
    }
  
    try {
      await serviceDeleteEstCivil(estCivilId);
      console.log('Estado civil eliminado con éxito.'); // Nuevo registro
      const response = await serviceGetEstCivil();
      if (response.data && response.data.estadoCiviles) {
        setEstadosCiviles(response.data.estadoCiviles);
      }
      Alertas.success_delete(E);
    } catch (error) {
      console.error('Error al eliminar el estado civil:', error);
      Alertas.error_delete(E);
    }
  };


  const handleEdit = (estCivil) => {
    setEditedEstCivil(estCivil);
    setEditedDescription(estCivil.descripcion);
    setEditingMode(true);
  };

  const handleSaveEdit = async () => {
    try {
      await serviceEditEstCivil({ codEstadoCiviles: editedEstCivil.codEstadoCiviles, descripcion: editedDescription });
      const response = await serviceGetEstCivil();
      if (response.data && response.data.estadoCiviles) {
        setEstadosCiviles(response.data.estadoCiviles);
      }
      setEditingMode(false);
      Alertas.success_edit();
    } catch (error) {
      console.error('Error al guardar los cambios del estado civil:', error);
      Alertas.error_edit();
    }
  };

  const handleCancelEdit = () => {
    setEditingMode(false);
    setEditedEstCivil(null);
    setEditedDescription('');
  };

  const estadosCivilesFiltered = estadosCiviles.filter((estCivil) => {
    return estCivil.descripcion.toLowerCase().includes(searching.toLowerCase());
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
            <p className='titulo_general'>Estados Civiles</p>
            <input
              type='text'
              placeholder='Buscar...'
              className='text-black w-full sm:w-[400px] hidden sm:flex p-2 items-center hover:bg-[#fff] rounded-full transition w-10 h-10 '
              value={searching}
              onChange={handleSearchChange}
            />
            <Link
              to='/addestadosCiviles' // Asegúrate de que esta ruta coincida con la definida en App.js
              className="cta-button_estandar bx bx-plus-medical"
            >
            </Link>
          </div>
        </div>
  
        <div className='table-container'>
          <table className='w-full text-[#1A1A1A]'>
            <thead>
              <tr className='text-left'>
                {/* Columna de ID eliminada */}
                <th className='bg-degradado px-5 py-3'>
                  Estado Civil
                </th>
                <th className='bg-degradado rounded-tr-lg sm:rounded-tr-md px-5 py-3 text-center'>Acciones</th>
              </tr>
            </thead>
  
            <tbody>
              {estadosCivilesFiltered.map((estCivil) => (
                <tr
                  key={estCivil.codEstadoCiviles}
                  className='sm:border-b-2 last:border-b-0'
                >
                  {/* Celda de ID eliminada */}
                  <td className='px-5 py-5 flex items-center min-w-[168px] '>
                    {editingMode && editedEstCivil && editedEstCivil.codEstadoCiviles === estCivil.codEstadoCiviles ? (
                      <input
                        type="text"
                        value={editedDescription}
                        onChange={(e) => setEditedDescription(e.target.value)}
                      />
                    ) : (
                      <span>{estCivil.descripcion}</span>
                    )}
                  </td>
                  <td className='px-5 py-3'>
                    <div className='flex items-center justify-center'>
                      {!editingMode && (
                        <button
                          onClick={() => handleEdit(estCivil)}
                          className="bg-custom-naranja hover:bg-custom-naranja-claro text-white font-bold py-2 px-4 rounded inline-flex items-center"
                        >
                          Editar
                        </button>
                      )}
                      {editingMode && editedEstCivil && editedEstCivil.codEstadoCiviles === estCivil.codEstadoCiviles && (
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
                            onClick={() => confirmDelete(estCivil.codEstadoCiviles)}
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
          {estadosCivilesFiltered.length === 0 && (
            <div className="flex justify-center text-center w-full">
              <p className="my-7 text-black text-center flex justify-center w-full">
                No se encontraron Estados Civiles
              </p>
            </div>
          )}
        </div>
      </>
    );
  };
  
  export default EstadosCivilesPage;
  
