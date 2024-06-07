import { useState, useEffect } from 'react';
import { ArrowLeft, Check } from 'react-feather';
import { Link, useNavigate } from 'react-router-dom';
import { serviceCreateCiudades } from '../../../services/ciudades/ciudades'; // Importa la función para crear ciudades
import { serviceGetDistritos } from '../../../services/distritos/distritos'; // Importa la función para obtener distritos
import '../../../components/EstandarCSS/Insert.css';

export const AddCiudad = () => {
  const navigate = useNavigate();

  const [descripcion, setDescripcion] = useState('');
  const [codigoSet, setCodigoSet] = useState('');
  const [codigoSicoop, setCodigoSicoop] = useState('');
  const [idDistrito, setIdDistrito] = useState(0);
  const [distritos, setDistritos] = useState([]);
  const [showConfirmation, setShowConfirmation] = useState(false);

  useEffect(() => {
    const fetchDistritos = async () => {
      try {
        const response = await serviceGetDistritos();
        if (response.data && Array.isArray(response.data.distritos)) {
          setDistritos(response.data.distritos);
        } else {
          console.error('La respuesta no contiene la propiedad "distritos" o no es un array:', response.data);
        }
      } catch (error) {
        console.error('Error al obtener el listado de distritos:', error);
      }
    };

    fetchDistritos();
  }, []);

  const handleDescripcionChange = (e) => setDescripcion(e.target.value.toUpperCase());
  const handleCodigoSetChange = (e) => {
    const value = e.target.value;
    if (!isNaN(value)) {
      setCodigoSet(value);
    }
  };
  const handleCodigoSicoopChange = (e) => {
    const value = e.target.value;
    if (!isNaN(value)) {
      setCodigoSicoop(value);
    }
  };

  const handleDistritoChange = (e) => {
    const selectedDistritoId = parseInt(e.target.value, 10); // Asegúrate de especificar la base 10 para el parseo
    if (!isNaN(selectedDistritoId)) {
      setIdDistrito(selectedDistritoId);
      console.log("ID del distrito seleccionado:", selectedDistritoId);
    } else {
      console.error("ID del distrito no es un número válido:", e.target.value);
    }
  };

  const handleSave = async (e) => {
    e.preventDefault();
    if (!idDistrito) {
      console.error("No se ha seleccionado ningún distrito.");
      return;
    }
    try {
      console.log("Datos a enviar:", { descripcion, codigoSet, codigoSicoop, distrito: idDistrito });
      await serviceCreateCiudades({ descripcion, codigoSet, codigoSicoop, distrito: idDistrito });
      setShowConfirmation(true);
      setTimeout(() => {
        setShowConfirmation(false);
        navigate('/ciudades');
      }, 1000);
    } catch (err) {
      console.log(err);
    }
  };

  return (
    <>
      <div className='contenido_general_A'>
        {showConfirmation && (
          <div className="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center">
            <div className="bg-custom-naranja p-8 rounded-xl shadow-lg text-center max-w-md">
              <Check size={48} color="#fff" className="mx-auto mb-4" />
              <h2 className="text-2xl font-bold text-white mb-4">¡Ciudad Agregada!</h2>
              <p className="text-white text-lg mb-4">Se ha insertado una nueva ciudad.</p>
              <button onClick={() => setShowConfirmation(false)}>OK</button>
            </div>
          </div>
        )}
        <div className="text-custom-black p-12 bg-gray-100 sm:bg-white sm:rounded-xl flex flex-col sm:justify-center sm:align-baseline gap-14 absolute top-0 left-0 bottom-0 right-0 sm:static w-full h-auto sm:w-auto sm:h-auto overflow-auto sm:overflow-visible">
          <h3 className="titulo_general_A">Agregar Ciudad</h3>
          <div className="flex sm:hidden mb-0 justify-normal items-center">
            <h3 className="text-xl items-start ml-2">Agregar Ciudad</h3>
            <Link className="absolute left-2" to="/ciudades">
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
                placeholder="Ingresa la descripción de la ciudad"
                required
              />
            </div>
            <div className="flex flex-col">
              <input
                type="text"
                value={codigoSet}
                onChange={handleCodigoSetChange}
                className="border-b-2 border-gray-300 p-2 input-animated-border"
                placeholder="Ingresa el código SET"
                required
              />
            </div>
            <div className="flex flex-col">
              <input
                type="text"
                value={codigoSicoop}
                onChange={handleCodigoSicoopChange}
                className="border-b-2 border-gray-300 p-2 input-animated-border"
                placeholder="Ingresa el código SICOOP"
                required
              />
            </div>
            <div className="flex flex-col">
              <select onChange={handleDistritoChange} className="border border-gray-300 rounded-lg p-2" required>
                <option value={0} disabled selected>Selecciona un distrito</option>
                {distritos.map((distrito) => (
                  <option key={distrito.id} value={distrito.id}>
                    {distrito.descripcion}
                  </option>
                ))}
              </select>
            </div>
          </div>
          <div className="flex justify-end mt-6">
            <Link to="/ciudades">
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

export default AddCiudad;