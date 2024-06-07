import { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { serviceGetTipoVinculos, serviceDeleteTipoVinculos, serviceEditTipoVinculos } from '../../services/tipoVinculos/tipoVinculos'
import Swal from 'sweetalert2';
import '../../components/EstandarCSS/Listado.css';

const TipoVinculosPage = () => {
  const navigate = useNavigate();

  const [tipoVinculos, setTipoVinculos] = useState([]);
  const [searching, setSearching] = useState('');
  const [editingMode, setEditingMode] = useState(false);
  const [editedCountry, setEditedCountry] = useState(null);
  const [editedDescription, setEditedDescription] = useState('');

  useEffect(() => {
    const fetchTipoVinculos = async () => {
      try {
        const response = await serviceGetTipoVinculos();       
        const { tipoVinculos } = response.data;
        console.log("respuesta",response)
        setTipoVinculos(tipoVinculos);
      } catch (error) {
        console.error('Error al obtener el listado de tipo vinculos:', error);
      }
    };

    fetchTipoVinculos();
  }, []);

  const handleSearchChange = (e) => setSearching(e.target.value);

  const handleDelete = async (tipoVinculosId) => {
    try {
      await serviceDeleteTipoVinculos(tipoVinculosId);
      const response = await serviceGetTipoVinculos();
      const { tipoVinculos } = response.data;
      setTipoVinculos(tipoVinculos);
      Swal.fire('Eliminado', 'El país ha sido eliminado con éxito.', 'success');
    } catch (error) {
      console.error('Error al eliminar el tipo vinculos:', error);
      Swal.fire('Error', 'Hubo un error al eliminar el tipo de vinculos.', 'error');
    }
  };

  const confirmDelete = (tipoVinculosId) => {
    Swal.fire({
      title: '¿Estás seguro de eliminar este tipo Vinculos?',
      text: 'Esta acción no se puede deshacer.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar'
    }).then((result) => {
      if (result.isConfirmed) {
        handleDelete(tipoVinculosId);
      }
    });
  };

  const handleEdit = (tipoVinculos) => {
    setEditedCountry(tipoVinculos);
    setEditedDescription(tipoVinculos.descripcion);
    setEditingMode(true);
  };

  const handleSaveEdit = async () => {
    try {
      await serviceEditTipoVinculos({ codTipoVinculos: editedCountry.codTipoVinculos, descripcion: editedDescription });
      const response = await serviceGetTipoVinculos();
      const { tipoVinculos } = response.data;
      setTipoVinculos(tipoVinculos);
      setEditingMode(false);
      Swal.fire('Guardado', 'Los cambios han sido guardados con éxito.', 'success');
    } catch (error) {
      console.error('Error al guardar los cambios del tipoVinculos:', error);
      Swal.fire('Error', 'Hubo un error al guardar los cambios.', 'error');
    }
  };

  const handleCancelEdit = () => {
    setEditingMode(false);
    setEditedCountry(null);
    setEditedDescription('');
  };

  const tipoVinculosFiltered = tipoVinculos.filter((tipoVinculos) => {
    return tipoVinculos.descripcion.toLowerCase().includes(searching.toLowerCase());
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
            <p className='titulo_general'>Tipo Vinculos</p>
            <input
              type='text'
              placeholder='Buscar...'
              className='text-black w-full sm:w-[400px] hidden sm:flex p-2 items-center hover:bg-[#fff] rounded-full transition w-10 h-10 '
              value={searching}
              onChange={handleSearchChange}
            />
            <Link
              to='/addTipoVinculos'
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
              {tipoVinculosFiltered.map((tipoVinculos) => (
                <tr
                  key={tipoVinculos.codTipoVinculos}
                  className='sm:border-b-2 last:border-b-0'
                >
                  <td className='px-5 py-5 flex items-center min-w-[168px] '>
                    {editingMode && editedCountry && editedCountry.codTipoVinculos === tipoVinculos.codTipoVinculos ? (
                      <input
                        type="text"
                        value={editedDescription}
                        onChange={(e) => setEditedDescription(e.target.value)}
                      />
                    ) : (
                      <span>{tipoVinculos.descripcion}</span>
                    )}
                  </td>
                  <td className='px-5 py-3'>
                    <div className='flex items-center justify-center'>
                      {!editingMode && (
                        <button
                          onClick={() => handleEdit(tipoVinculos)}
                          className="bg-custom-naranja hover:bg-custom-naranja-claro text-white font-bold py-2 px-4 rounded inline-flex items-center"
                        >
                          Editar
                        </button>
                      )}
                      {editingMode && editedCountry && editedCountry.codTipoVinculos === tipoVinculos.codTipoVinculos && (
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
                          onClick={() => confirmDelete(tipoVinculos.codTipoVinculos)}
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

        {tipoVinculosFiltered.length === 0 && (
          <div className="flex justify-center text-centerw-full">
            <p className="my-7 text-black text-center flex justify-center w-full">
              No se encontraron vinculos
            </p>
          </div>
        )}
      </div>
    </>
  );
};

export default TipoVinculosPage;
