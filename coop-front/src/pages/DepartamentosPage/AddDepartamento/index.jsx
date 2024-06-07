import { useState, useEffect } from 'react';
import { ArrowLeft, Check } from 'react-feather';
import { Link, useNavigate } from 'react-router-dom';
import { serviceCreateDepartamento } from '../../../services/departamentos/departamentos';
import { serviceGetCountries } from '../../../services/paises/paises';
import '../../../components/EstandarCSS/Insert.css';
import Alertas from '../../../components/Alertas/Alertas';

export const AddDepartamento = () => {
  const P = "departamento";
  const navigate = useNavigate();
  const [descripcion, setDescripcion] = useState('');
  const [idPais, setIdPais] = useState(0);
  const [paises, setPaises] = useState([]);
  const [showConfirmation, setShowConfirmation] = useState(false);

    const handleDescripcionChange = (e) => {
    const { value } = e.target;
    const regex = /^[A-Za-z\s]*$/;
    if (regex.test(value)) {
      setDescripcion(value);
    } else {
      Alertas.customAlert_string(P);
    }
  };

  useEffect(() => {
    const fetchPaises = async () => {
      try {
        const response = await serviceGetCountries();
        if (response.data && Array.isArray(response.data.paises)) {
          setPaises(response.data.paises);
        } else {
          console.error('La respuesta no contiene la propiedad "paises" o no es un array:', response.data);
        }
      } catch (error) {
        console.error('Error al obtener el listado de países:', error);
      }
    };

    fetchPaises();
  }, []);



  const handlePaisChange = (e) => {
    const selectedPaisId = parseInt(e.target.value, 10); // Asegúrate de especificar la base 10 para el parseo
    if (isNaN(selectedPaisId)) {
      console.error("ID del país no es un número válido:", e.target.value);
      setIdPais(0);
    } else {
      setIdPais(selectedPaisId);
    }
    console.log("ID del país seleccionado:", selectedPaisId);
  };

  const handleSave = async (e) => {
    e.preventDefault();
    if (!idPais) {
      console.error("No se ha seleccionado ningún país.");
      Alertas.customAlert_descripcion();
      return;
    }
    try {
      console.log("Datos a enviar:", { descripcion, pais: idPais });
      await serviceCreateDepartamento({ descripcion, pais: idPais });
      navigate('/departamentos');
      Alertas.customAlert_agregar(P);

    } catch (err) {
      console.log(err);
      if (err.response && err.response.data) {
        console.log(err.response.data);

        if (err.response.data.message === 'El departamento ya existe') {
          Alertas.customAlert_existente(P);
        } else {
          Alertas.customAlert_error(P, err.response.data.message );
        }
      } else {
        Alertas.customAlert_error(P);
      }
    }
  };

  return (
    <>
    <div className='contenido_general_A'>
    {showConfirmation && (
        <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
          <div className="bg-custom-naranja p-8 rounded-xl shadow-lg text-center max-w-md">
            <Check size={48} color="#fff" className="mx-auto mb-4" />
            <h2 className="text-2xl font-bold text-white mb-4">¡Departamento Agregado!</h2>
            <p className="text-white text-lg mb-4">Se ha insertado un nuevo departamento.</p>
            <button onClick={() => setShowConfirmation(false)}>OK</button>
          </div>
        </div>
      )}
      <div className="text-custom-black p-12 bg-gray-100 sm:bg-white sm:rounded-xl flex flex-col sm:justify-center sm:align-baseline gap-14 absolute top-0 left-0 bottom-0 right-0 sm:static w-full h-auto sm:w-auto sm:h-auto overflow-auto sm:overflow-visible">
        <h3 className="titulo_general_A">Agregar Departamento</h3>
        <div className="flex sm:hidden mb-0 justify-normal items-center">
          <h3 className="text-xl items-start ml-2">Agregar Departamento</h3>
          <Link className="absolute left-2" to="/departamentos">
            <ArrowLeft />
          </Link>
          <button onClick={handleSave} className="absolute right-7 rounded-3xl">
            <Check size={30} color="#3E43C7" />
          </button>
        </div>
        <div className="w-full grid grid-cols-1 sm:grid-cols-2 gap-7">
          <div className="flex flex-col">
            <input
              type="text"
              value={descripcion}
              onChange={handleDescripcionChange}
              className="border-b-2 border-gray-300 p-2 input-animated-border"
              placeholder="Ingresa un departamento"
              required
            />
          </div>
          <div className="flex flex-col">
            <select onChange={handlePaisChange} className="border border-gray-300 rounded-lg p-2" required>
              <option value={0} disabled selected>Selecciona un país</option>
              {paises.map((pais) => (
                <option key={pais.codPais} value={pais.codPais}>
                  {pais.descripcion}
                </option>
              ))}
            </select>
          </div>
        </div>
        <div className="flex justify-end mt-6">
          <Link to="/departamentos">
            <button className="cta-button ml-7">Atras</button>
          </Link>
          <button onClick={handleSave} className="cta-button ml-12">
            Guardar
          </button>
        </div>
      </div>
    </div>
      
    </>
  );
};

export default AddDepartamento;
