// src/pages/PaisesPage.js
import { useState, useEffect } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { serviceGetCountries, serviceDeleteCountry, serviceEditCountry } from '../../services/paises/paises';
import Alertas from '../../components/Alertas/Alertas';
import '../../components/EstandarCSS/Listado.css';




const PaisesPage = () => {

  const P = "país";

  const navigate = useNavigate();

  const [paises, setPaises] = useState([]);
  const [searching, setSearching] = useState('');
  const [editingMode, setEditingMode] = useState(false);
  const [editedCountry, setEditedCountry] = useState(null);
  const [editedDescription, setEditedDescription] = useState('');

  useEffect(() => {
    const fetchPaises = async () => {
      try {
        const response = await serviceGetCountries();
        const { paises } = response.data;
        setPaises(paises);
      } catch (error) {
        console.error('Error al obtener el listado de países:', error);
      }
    };

    fetchPaises();
  }, []);

  const handleSearchChange = (e) => setSearching(e.target.value);

  const handleDelete = async (paisId) => {
    try {
      await serviceDeleteCountry(paisId);
      const response = await serviceGetCountries();
      const { paises } = response.data;
      setPaises(paises);
      Alertas.success_delete(P);
    } catch (error) {
      console.error('Error al eliminar el país:', error);
      Alertas.error_delete(P);
    }
  };

  const confirmDelete = (paisId) => {
    Alertas.confirm(P,() => handleDelete(paisId)
    );
  };

  const handleEdit = (pais) => {
    setEditedCountry(pais);
    setEditedDescription(pais.descripcion);
    setEditingMode(true);
  };

  const handleSaveEdit = async () => {
    try {
      await serviceEditCountry({ codPais: editedCountry.codPais, descripcion: editedDescription });
      const response = await serviceGetCountries();
      const { paises } = response.data;
      setPaises(paises);
      setEditingMode(false);
      Alertas.success_edit();
    } catch (error) {
      console.error('Error al guardar los cambios del país:', error);
      Alertas.error_edit();
    }
  };

  const handleCancelEdit = () => {
    setEditingMode(false);
    setEditedCountry(null);
    setEditedDescription('');
  };

  const paisesFiltered = paises.filter((pais) => {
    return pais.descripcion.toLowerCase().includes(searching.toLowerCase());
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
            <p className='titulo_general'>Países</p>
            <input
              type='text'
              placeholder='Buscar...'
              className='text-black w-full sm:w-[400px] hidden sm:flex p-2 items-center hover:bg-[#fff] rounded-full transition w-10 h-10 '
              value={searching}
              onChange={handleSearchChange}
            />
            <Link
              to='/addpais'
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
                  País
                </th>
                <th className='bg-degradado rounded-tr-lg sm:rounded-tr-md px-5 py-3 text-center'>Acciones</th>
              </tr>
            </thead>

            <tbody>
              {paisesFiltered.map((pais) => (
                <tr
                  key={pais.codPais}
                  className='sm:border-b-2 last:border-b-0'
                >
                  <td className='px-5 py-5 flex items-center min-w-[168px] '>
                    {editingMode && editedCountry && editedCountry.codPais === pais.codPais ? (
                      <input
                        type="text"
                        value={editedDescription}
                        onChange={(e) => setEditedDescription(e.target.value)}
                      />
                    ) : (
                      <span>{pais.descripcion}</span>
                    )}
                  </td>
                  <td className='px-5 py-3'>
                    <div className='flex items-center justify-center'>
                      {!editingMode && (
                        <button
                          onClick={() => handleEdit(pais)}
                          className="bg-custom-naranja hover:bg-custom-naranja-claro text-white font-bold py-2 px-4 rounded inline-flex items-center"
                        >
                          Editar
                        </button>
                      )}
                      {editingMode && editedCountry && editedCountry.codPais === pais.codPais && (
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
                          onClick={() => confirmDelete(pais.codPais)}
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

        {paisesFiltered.length === 0 && (
          <div className="flex justify-center text-centerw-full">
            <p className="my-7 text-black text-center flex justify-center w-full">
              No se encontraron Países
            </p>
          </div>
        )}
      </div>
    </>
  );
};

export default PaisesPage;
