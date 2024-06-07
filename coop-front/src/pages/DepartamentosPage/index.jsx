import { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { serviceGetDepartamento, serviceDeleteDepartamento, serviceEditDepartamento } from '../../services/departamentos/departamentos';
import { serviceGetCountries } from '../../services/paises/paises';
import Alertas from '../../components/Alertas/Alertas';
import '../../components/EstandarCSS/Listado.css';

const DepartamentosPage = () => {
  const P = "departamento";
  const navigate = useNavigate();
  const [departamentos, setDepartamentos] = useState([]);
  const [searching, setSearching] = useState('');
  const [deleteId, setDeleteId] = useState(null);
  const [editingMode, setEditingMode] = useState(false);
  const [editedDepartamento, setEditedDepartamento] = useState(null);
  const [editedDescription, setEditedDescription] = useState('');
  const [countries, setCountries] = useState([]);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const responseDepartamentos = await serviceGetDepartamento();
        const { departamentos } = responseDepartamentos.data;
        setDepartamentos(departamentos);

        const responseCountries = await serviceGetCountries();
        const { countries } = responseCountries.data;
        setCountries(countries);
      } catch (error) {
        console.error('Error al obtener el listado de departamentos o países:', error);
      }
    };
    fetchData();
  }, []);

  const handleSearchChange = (e) => setSearching(e.target.value);

  const handleDelete = async (id) => {
    try {
      await serviceDeleteDepartamento(id);
      setDeleteId(null);
      const response = await serviceGetDepartamento();
      const { departamentos } = response.data;
      setDepartamentos(departamentos);
      Alertas.success_delete(P);
    } catch (error) {
      console.error('Error al eliminar el departamento:', error);
      Alertas.error_delete(P);
    }
  };

  const handleEdit = (departamento) => {
    setEditedDepartamento(departamento);
    setEditedDescription(departamento.descripcion);
    setEditingMode(true);
  };

  const handleSaveEdit = async () => {
    try {
      await serviceEditDepartamento({ id: editedDepartamento.id, descripcion: editedDescription });
      const response = await serviceGetDepartamento();
      const { departamentos } = response.data;
      setDepartamentos(departamentos);
      setEditingMode(false);
      Alertas.success_edit();
    } catch (error) {
      console.error('Error al guardar los cambios del departamento:', error);
      Alertas.error_edit();
    }
  };

  const handleCancelEdit = () => {
    setEditingMode(false);
    setEditedDepartamento(null);
    setEditedDescription('');
  };

  const departamentosFiltered = departamentos.filter((departamento) => {
    return departamento.descripcion.toLowerCase().includes(searching.toLowerCase());
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
            <p className='titulo_general'>Departamentos</p>
            <input
              type='text'
              placeholder='Buscar...'
              className='text-black w-full sm:w-[400px] hidden sm:flex p-2 items-center hover:bg-[#fff] rounded-full transition w-10 h-10'
              value={searching}
              onChange={handleSearchChange}
            />
            <Link
              to='/adddepartamento'
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
                  Departamento
                </th>
                <th className='bg-degradado px-5 py-3'>
                  País
                </th>
                <th className='bg-degradado rounded-tr-lg sm:rounded-tr-md px-5 py-3 text-center'>Acciones</th>
              </tr>
            </thead>

            <tbody>
              {departamentosFiltered.map((departamento) => (
                <tr
                  key={departamento.id}
                  className='sm:border-b-2 last:border-b-0'
                >
                  <td className='px-5 py-5 flex items-center min-w-[168px]'>
                    {editingMode && editedDepartamento && editedDepartamento.id === departamento.id ? (
                      <input
                        type="text"
                        value={editedDescription}
                        onChange={(e) => setEditedDescription(e.target.value)}
                        className="border border-gray-300 rounded-lg p-2"
                      />
                    ) : (
                      <span>{departamento.descripcion}</span>
                    )}
                  </td>
                  <td className='px-5 py-5'>
                    {departamento.pais}
                  </td>
                  <td className='px-5 py-3'>
                    <div className='flex items-center justify-center'>
                      {!editingMode && (
                        <button
                          onClick={() => handleEdit(departamento)}
                          className="bg-custom-naranja hover:bg-custom-naranja-claro text-white font-bold py-2 px-4 rounded inline-flex items-center"
                        >
                          Editar
                        </button>
                      )}
                      {editingMode && editedDepartamento && editedDepartamento.id === departamento.id && (
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
                            setDeleteId(departamento.id);
                            Alertas.confirm(P, () => handleDelete(departamento.id));
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

        {departamentosFiltered.length === 0 && (
          <div className="flex justify-center text-centerw-full">
            <p className="my-7 text-black text-center flex justify-center w-full">
              No se encontraron Departamentos
            </p>
          </div>
        )}
      </div>
    </>
  );
};

export default DepartamentosPage;
