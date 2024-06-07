import { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { serviceGetTipoPersonas, serviceDeleteTipoPersonas, serviceEditTipoPersonas } from '../../services/tipoPersonas/tipoPersonas';
import Swal from 'sweetalert2';
import '../../components/EstandarCSS/Listado.css';

const TipoPersonasPage = () => {
  const navigate = useNavigate();

  const [tipoPersonas, setTipoPersonas] = useState([]);
  const [searching, setSearching] = useState('');
  const [editingMode, setEditingMode] = useState(false);
  const [editedPersonas, setEditedPersonas] = useState(null);
  const [editedDescription, setEditedDescription] = useState('');

  useEffect(() => {
    const fetchTipoPersonas = async () => {
      try {
        const response = await serviceGetTipoPersonas();
        const { tipoPersonas } = response.data;
        setTipoPersonas(tipoPersonas);
      } catch (error) {
        console.error('Error al obtener el listado de tipo personas:', error);
      }
    };

    fetchTipoPersonas();
  }, []);

  const handleSearchChange = (e) => setSearching(e.target.value);

  const handleDelete = async (tipoPersonasId) => {
    try {
      await serviceDeleteTipoPersonas(tipoPersonasId);
      const response = await serviceGetTipoPersonas();
      const { tipoPersonas } = response.data;
      setTipoPersonas(tipoPersonas);
      Swal.fire('Eliminado', 'El tipo de persona ha sido eliminado con éxito.', 'success');
    } catch (error) {
      console.error('Error al eliminar el tipo de personas:', error);
      Swal.fire('Error', 'Hubo un error al eliminar el tipo de personas.', 'error');
    }
  };

  const confirmDelete = (tipoPersonasId) => {
    Swal.fire({
      title: '¿Estás seguro de eliminar este tipo de persona?',
      text: 'Esta acción no se puede deshacer.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        handleDelete(tipoPersonasId);
      }
    });
  };

  const handleEdit = (tipoPersonas) => {
    setEditedPersonas(tipoPersonas);
    setEditedDescription(tipoPersonas.descripcion);
    setEditingMode(true);
  };

  const handleSaveEdit = async () => {
    try {
      await serviceEditTipoPersonas({ codTipoPersonas: editedPersonas.codTipoPersonas, descripcion: editedDescription });
      const response = await serviceGetTipoPersonas();
      const { tipoPersonas } = response.data;
      setTipoPersonas(tipoPersonas);
      setEditingMode(false);
      Swal.fire('Guardado', 'Los cambios han sido guardados con éxito.', 'success');
    } catch (error) {
      console.error('Error al guardar los cambios del tipoPersonas:', error);
      Swal.fire('Error', 'Hubo un error al guardar los cambios.', 'error');
    }
  };

  const handleCancelEdit = () => {
    setEditingMode(false);
    setEditedPersonas(null);
    setEditedDescription('');
  };

  const tipoPersonasFiltered = tipoPersonas.filter((tipoPersonas) => {
    return tipoPersonas.descripcion.toLowerCase().includes(searching.toLowerCase());
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
            <p className='titulo_general'>Tipo Personas</p>
            <input
              type='text'
              placeholder='Buscar...'
              className='text-black w-full sm:w-[400px] hidden sm:flex p-2 items-center hover:bg-[#fff] rounded-full transition w-10 h-10 '
              value={searching}
              onChange={handleSearchChange}
            />
            <Link
              to='/addTipoPersonas'
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
                  Descripción
                </th>
                <th className='bg-degradado rounded-tr-lg sm:rounded-tr-md px-5 py-3 text-center'>Acciones</th>
              </tr>
            </thead>

            <tbody>
              {tipoPersonasFiltered.map((tipoPersonas) => (
                <tr
                  key={tipoPersonas.codTipoPersonas}
                  className='sm:border-b-2 last:border-b-0'
                >
                  <td className='px-5 py-5 flex items-center min-w-[168px] '>
                    {editingMode && editedPersonas && editedPersonas.codTipoPersonas === tipoPersonas.codTipoPersonas ? (
                      <input
                        type="text"
                        value={editedDescription}
                        onChange={(e) => setEditedDescription(e.target.value)}
                      />
                    ) : (
                      <span>{tipoPersonas.descripcion}</span>
                    )}
                  </td>
                  <td className='px-5 py-3'>
                    <div className='flex items-center justify-center'>
                      {!editingMode && (
                        <button
                          onClick={() => handleEdit(tipoPersonas)}
                          className="bg-custom-naranja hover:bg-custom-naranja-claro text-white font-bold py-2 px-4 rounded inline-flex items-center"
                        >
                          Editar
                        </button>
                      )}
                      {editingMode && editedPersonas && editedPersonas.codTipoPersonas === tipoPersonas.codTipoPersonas && (
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
                          onClick={() => confirmDelete(tipoPersonas.codTipoPersonas)}
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

        {tipoPersonasFiltered.length === 0 && (
          <div className="flex justify-center text-centerw-full">
            <p className="my-7 text-black text-center flex justify-center w-full">
              No se encontraron tipo personas
            </p>
          </div>
        )}
      </div>
    </>
  );
};

export default TipoPersonasPage;
