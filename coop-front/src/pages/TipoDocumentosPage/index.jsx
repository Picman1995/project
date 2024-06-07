import { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { serviceGetTipoDocumentos, serviceDeleteTipoDocumentos, serviceEditTipoDocumentos } from '../../services/tipoDocumentos/tipoDocumentos'
import Swal from 'sweetalert2';
import '../../components/EstandarCSS/Listado.css';

const TipoDocumentosPage = () => {
  const navigate = useNavigate();

  const [tipoDocumentos, setTipoDocumentos] = useState([]);
  const [searching, setSearching] = useState('');
  const [editingMode, setEditingMode] = useState(false);
  const [editedDocumentos, setEditedDocumentos] = useState(null);
  const [editedDescription, setEditedDescription] = useState('');

  useEffect(() => {
    const fetchTipoDocumentos = async () => {
      try {
        const response = await serviceGetTipoDocumentos();
        const { tipoDocumentos } = response.data;
        setTipoDocumentos(tipoDocumentos);
      } catch (error) {
        console.error('Error al obtener el listado de tipo documentos:', error);
      }
    };

    fetchTipoDocumentos();
  }, []);

  const handleSearchChange = (e) => setSearching(e.target.value);

  const handleDelete = async (tipoDocumentosId) => {
    try {
      await serviceDeleteTipoDocumentos(tipoDocumentosId);
      const response = await serviceGetTipoDocumentos();
      const { tipoDocumentos } = response.data;
      setTipoDocumentos(tipoDocumentos);
      Swal.fire('Eliminado', 'El país ha sido eliminado con éxito.', 'success');
    } catch (error) {
      console.error('Error al eliminar el tipo vinculos:', error);
      Swal.fire('Error', 'Hubo un error al eliminar el tipo de vinculos.', 'error');
    }
  };

  const confirmDelete = (tipoDocumentosId) => {
    Swal.fire({
      title: '¿Estás seguro de eliminar este tipo Vinculos?',
      text: 'Esta acción no se puede deshacer.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        handleDelete(tipoDocumentosId);
      }
    });
  };

  const handleEdit = (tipoDocumentos) => {
    setEditedDocumentos(tipoDocumentos);
    setEditedDescription(tipoDocumentos.descripcion);
    setEditingMode(true);
  };

  const handleSaveEdit = async () => {
    try {
      await serviceEditTipoDocumentos({ codTipoDocumentos: editedDocumentos.codTipoDocumentos, descripcion: editedDescription });
      const response = await serviceGetTipoDocumentos();
      const { tipoDocumentos } = response.data;
      setTipoDocumentos(tipoDocumentos);
      setEditingMode(false);
      Swal.fire('Guardado', 'Los cambios han sido guardados con éxito.', 'success');
    } catch (error) {
      console.error('Error al guardar los cambios del tipoDocumentos:', error);
      Swal.fire('Error', 'Hubo un error al guardar los cambios.', 'error');
    }
  };

  const handleCancelEdit = () => {
    setEditingMode(false);
    setEditedDocumentos(null);
    setEditedDescription('');
  };

  const tipoDocumentosFiltered = tipoDocumentos.filter((tipoDocumentos) => {
    return tipoDocumentos.descripcion.toLowerCase().includes(searching.toLowerCase());
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
            <p className='titulo_general'>Tipo Documentos</p>
            <input
              type='text'
              placeholder='Buscar...'
              className='text-black w-full sm:w-[400px] hidden sm:flex p-2 items-center hover:bg-[#fff] rounded-full transition w-10 h-10 '
              value={searching}
              onChange={handleSearchChange}
            />
            <Link
              to='/addTipoDocumentos'
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
              {tipoDocumentosFiltered.map((tipoDocumentos) => (
                <tr
                  key={tipoDocumentos.codTipoDocumentos}
                  className='sm:border-b-2 last:border-b-0'
                >
                  <td className='px-5 py-5 flex items-center min-w-[168px] '>
                    {editingMode && editedDocumentos && editedDocumentos.codTipoDocumentos === tipoDocumentos.codTipoDocumentos ? (
                      <input
                        type="text"
                        value={editedDescription}
                        onChange={(e) => setEditedDescription(e.target.value)}
                      />
                    ) : (
                      <span>{tipoDocumentos.descripcion}</span>
                    )}
                  </td>
                  <td className='px-5 py-3'>
                    <div className='flex items-center justify-center'>
                      {!editingMode && (
                        <button
                          onClick={() => handleEdit(tipoDocumentos)}
                          className="bg-custom-naranja hover:bg-custom-naranja-claro text-white font-bold py-2 px-4 rounded inline-flex items-center"
                        >
                          Editar
                        </button>
                      )}
                      {editingMode && editedDocumentos && editedDocumentos.codTipoDocumentos === tipoDocumentos.codTipoDocumentos && (
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
                          onClick={() => confirmDelete(tipoDocumentos.codTipoDocumentos)}
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

        {tipoDocumentosFiltered.length === 0 && (
          <div className="flex justify-center text-centerw-full">
            <p className="my-7 text-black text-center flex justify-center w-full">
              No se encontraron tipo documentos
            </p>
          </div>
        )}
      </div>
    </>
  );
};

export default TipoDocumentosPage;
