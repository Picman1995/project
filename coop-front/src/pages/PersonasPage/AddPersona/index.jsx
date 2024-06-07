import { useState, useEffect } from 'react';
import { ArrowLeft, Check } from 'react-feather';
import { Link, useNavigate } from 'react-router-dom';
import { serviceCreatePerson } from '../../../services/personas/personas';  // Importa la función para crear personas
import { serviceGetCiudades } from '../../../services/ciudades/ciudades'; // Importa la función para listar ciudades
import { serviceGetTipoDocumentos } from '../../../services/tipoDocumentos/tipoDocumentos';
import { serviceGetEstCivil } from '../../../services/estadosCiviles/estadosCiviles';
import { serviceGetTipoPersonas } from '../../../services/tipoPersonas/tipoPersonas';
import { serviceGetNacionalidades } from '../../../services/nacionalidades/nacionalidades';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";
import '../../../components/EstandarCSS/Insert.css';

export const AddPersona = () => {
  const navigate = useNavigate();

  const [nombre1, setNombre1] = useState('');
  const [nombre2, setNombre2] = useState('');
  const [nombre3, setNombre3] = useState('');
  const [apellido1, setApellido1] = useState('');
  const [apellido2, setApellido2] = useState('');
  const [apellido3, setApellido3] = useState('');
  const [nroDocumento, setNroDocumento] = useState('');
  const [fechaNacimiento, setFechaNacimiento] = useState(new Date());
  const [sexo, setSexo] = useState('');
  const [ciudadId, setCiudadId] = useState(0);
  const [tipoDocumentoId, setTipoDocumentoId] = useState(0);
  const [estadoCivilId, setEstadoCivilId] = useState(0);
  const [tipoPersonaId, setTipoPersonaId] = useState(0);
  const [nacionalidadId, setNacionalidadId] = useState(0);
  const [ciudades, setCiudades] = useState([]);
  const [tipoDocumentos, setTipoDocumentos] = useState([]);
  const [estadosCiviles, setEstadosCiviles] = useState([]);
  const [tipoPersonas, setTipoPersonas] = useState([]);
  const [nacionalidades, setNacionalidades] = useState([]);
  const [showConfirmation, setShowConfirmation] = useState(false);

  useEffect(() => {
    const fetchData = async () => {
      try {

        const tipoDocumentosResponse = await serviceGetTipoDocumentos();
        if (tipoDocumentosResponse.data && Array.isArray(tipoDocumentosResponse.data.tipoDocumentos)) {
          setTipoDocumentos(tipoDocumentosResponse.data.tipoDocumentos);
        } else {
          console.error('La respuesta no contiene la propiedad "tipoDocumentos" o no es un array:', tipoDocumentosResponse.data);
        }

        const ciudadesResponse = await serviceGetCiudades();
        if (ciudadesResponse.data && Array.isArray(ciudadesResponse.data.ciudades)) {
          setCiudades(ciudadesResponse.data.ciudades);
        } else {
          console.error('La respuesta no contiene la propiedad "ciudades" o no es un array:', ciudadesResponse.data);
        }

        const estadosCivilesResponse = await serviceGetEstCivil();
        if (estadosCivilesResponse.data && Array.isArray(estadosCivilesResponse.data.estadoCiviles)) {
          setEstadosCiviles(estadosCivilesResponse.data.estadoCiviles);
        } else {
          console.error('La respuesta no contiene la propiedad "estadosCiviles" o no es un array:', estadosCivilesResponse.data);
        }

        const tipoPersonasResponse = await serviceGetTipoPersonas();
        if (tipoPersonasResponse.data && Array.isArray(tipoPersonasResponse.data.tipoPersonas)) {
          setTipoPersonas(tipoPersonasResponse.data.tipoPersonas);
        } else {
          console.error('La respuesta no contiene la propiedad "tipoPersonas" o no es un array:', tipoPersonasResponse.data);
        }

        const nacionalidadesResponse = await serviceGetNacionalidades();
        if (nacionalidadesResponse.data && Array.isArray(nacionalidadesResponse.data.nacionalidades)) {
          setNacionalidades(nacionalidadesResponse.data.nacionalidades);
        } else {
          console.error('La respuesta no contiene la propiedad "nacionalidades" o no es un array:', nacionalidadesResponse.data);
        }

      } catch (error) {
        console.error('Error al obtener los datos:', error);
      }
    };

    fetchData();
  }, []);

  const handleNombre1Change = (e) => setNombre1(e.target.value);
  const handleNombre2Change = (e) => setNombre2(e.target.value);
  const handleNombre3Change = (e) => setNombre3(e.target.value);
  const handleApellido1Change = (e) => setApellido1(e.target.value);
  const handleApellido2Change = (e) => setApellido2(e.target.value);
  const handleApellido3Change = (e) => setApellido3(e.target.value);
  const handleNroDocumentoChange = (e) => setNroDocumento(e.target.value);
  const handleFechaNacimientoChange = (date) => setFechaNacimiento(date);
  const handleSexoChange = (e) => setSexo(e.target.value);

  const handleCiudadChange = (e) => {
    const selectedCiudadId = parseInt(e.target.value, 10);
    if (!isNaN(selectedCiudadId)) {
      setCiudadId(selectedCiudadId);
    } else {
      console.error("ID de la ciudad no es un número válido:", e.target.value);
    }
    console.log("Ciudad seleccionada:", selectedCiudadId);
  };
  

  const handleTipoDocumentoChange = (e) => {
    const selectedTipoDocumentoId = parseInt(e.target.value, 10);
    if (!isNaN(selectedTipoDocumentoId)) {
      setTipoDocumentoId(selectedTipoDocumentoId);
    } else {
      console.error("ID del tipo de documento no es un número válido:", e.target.value);
    }
    console.log("Tipo de Documento seleccionado:", selectedTipoDocumentoId);
  };

  const handleEstadoCivilChange = (e) => {
    const selectedEstadoCivilId = parseInt(e.target.value, 10);
    if (!isNaN(selectedEstadoCivilId)) {
      setEstadoCivilId(selectedEstadoCivilId);
    } else {
      console.error("ID del estado civil no es un número válido:", e.target.value);
    }
    console.log("Estado Civil seleccionado:", selectedEstadoCivilId);
  };

  const handleTipoPersonaChange = (e) => {
    const selectedTipoPersonaId = parseInt(e.target.value, 10);
    if (!isNaN(selectedTipoPersonaId)) {
      setTipoPersonaId(selectedTipoPersonaId);
    } else {
      console.error("ID del tipo de persona no es un número válido:", e.target.value);
    }
    console.log("Tipo de Persona seleccionado:", selectedTipoPersonaId);
  };

  const handleNacionalidadChange = (e) => {
    const selectedNacionalidadId = parseInt(e.target.value, 10);
    if (!isNaN(selectedNacionalidadId)) {
      setNacionalidadId(selectedNacionalidadId);
    } else {
      console.error("ID de la nacionalidad no es un número válido:", e.target.value);
    }
    console.log("Nacionalidad seleccionada:", selectedNacionalidadId);
  };

  const handleSave = async (e) => {
    e.preventDefault();
    if (ciudadId === 0 || isNaN(ciudadId)) {
      console.error("La ciudad seleccionada no es válida.");
      return;
    }
    try {
      const fechaNacimientoFormatted = fechaNacimiento.toISOString().split('T')[0];
      console.log("Datos a enviar:", { 
        nombre1, nombre2, nombre3, 
        apellido1, apellido2, apellido3, 
        nroDocumento, fechaNacimiento: fechaNacimientoFormatted, 
        sexo, ciudad: ciudadId, tipoDocumento: tipoDocumentoId, 
        estadoCivil: estadoCivilId, tipoPersona: tipoPersonaId, nacionalidad: nacionalidadId 
      });
      await serviceCreatePerson({ 
        nombre1, nombre2, nombre3, 
        apellido1, apellido2, apellido3, 
        nroDocumento, fechaNacimiento: fechaNacimientoFormatted, 
        sexo, ciudad: ciudadId, tipoDocumento: tipoDocumentoId, 
        estadoCivil: estadoCivilId, tipoPersona: tipoPersonaId, nacionalidad: nacionalidadId 
      });
      setShowConfirmation(true);
      setTimeout(() => {
        setShowConfirmation(false);
        navigate('/personas');
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
              <h2 className="text-2xl font-bold text-white mb-4">¡Persona Agregada!</h2>
              <p className="text-white text-lg mb-4">Se ha insertado una nueva persona.</p>
              <button onClick={() => setShowConfirmation(false)}>OK</button>
            </div>
          </div>
        )}
        <div className="text-custom-black p-12 bg-gray-100 sm:bg-white sm:rounded-xl flex flex-col sm:justify-center sm:align-baseline gap-14 absolute top-0 left-0 bottom-0 right-0 sm:static w-full h-auto sm:w-auto sm:h-auto overflow-auto sm:overflow-visible">
          <h3 className="titulo_general_A">Agregar Persona</h3>
          <div className="flex sm:hidden mb-0 justify-normal items-center">
            <h3 className="text-xl items-start ml-2">Agregar Persona</h3>
            <Link className="absolute left-2" to="/personas">
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
                value={nombre1}
                onChange={handleNombre1Change}
                className="border-b-2 border-gray-300 p-2 input-animated-border"
                placeholder="Ingresa el primer nombre"
                required
              />
            </div>
            <div className="flex flex-col">
              <input
                type="text"
                value={nombre2}
                onChange={handleNombre2Change}
                className="border-b-2 border-gray-300 p-2 input-animated-border"
                placeholder="Ingresa el segundo nombre"
              />
            </div>
            <div className="flex flex-col">
              <input
                type="text"
                value={nombre3}
                onChange={handleNombre3Change}
                className="border-b-2 border-gray-300 p-2 input-animated-border"
                placeholder="Ingresa el tercer nombre"
              />
            </div>
            <div className="flex flex-col">
              <input
                type="text"
                value={apellido1}
                onChange={handleApellido1Change}
                className="border-b-2 border-gray-300 p-2 input-animated-border"
                placeholder="Ingresa el primer apellido"
                required
              />
            </div>
            <div className="flex flex-col">
              <input
                type="text"
                value={apellido2}
                onChange={handleApellido2Change}
                className="border-b-2 border-gray-300 p-2 input-animated-border"
                placeholder="Ingresa el segundo apellido"
              />
            </div>
            <div className="flex flex-col">
              <input
                type="text"
                value={apellido3}
                onChange={handleApellido3Change}
                className="border-b-2 border-gray-300 p-2 input-animated-border"
                placeholder="Ingresa el tercer apellido"
              />
            </div>
            <div className="flex flex-col">
              <input
                type="text"
                value={nroDocumento}
                onChange={handleNroDocumentoChange}
                className="border-b-2 border-gray-300 p-2 input-animated-border"
                placeholder="Ingresa el número de documento"
                required
              />
            </div>
            <div className="flex flex-col">
              <DatePicker
                selected={fechaNacimiento}
                onChange={date => handleFechaNacimientoChange(date)}
                className="border-b-2 border-gray-300 p-2 input-animated-border"
                dateFormat="yyyy-MM-dd"
                placeholderText="Seleccione la fecha de nacimiento"
                required
              />
            </div>
            <div className="flex flex-col">
              <select onChange={handleSexoChange} className="border border-gray-300 rounded-lg p-2" required>
                <option value="" disabled selected>Selecciona el sexo</option>
                <option value="M">Masculino</option>
                <option value="F">Femenino</option>
              </select>
            </div>
            <div className="flex flex-col">
              <select onChange={handleTipoDocumentoChange} className="border border-gray-300 rounded-lg p-2" required>
                <option value={0} disabled selected>Selecciona un tipo de documento</option>
                {tipoDocumentos.map((tipoDocumento) => (
                  <option key={tipoDocumento.codTipoDocumentos} value={tipoDocumento.codTipoDocumentos}>
                    {tipoDocumento.descripcion}
                  </option>
                ))}
              </select>
            </div>
            <div className="flex flex-col">
              <select onChange={handleCiudadChange} className="border border-gray-300 rounded-lg p-2" required>
                <option value={0} disabled selected>Selecciona una ciudad</option>
                {ciudades.map((ciudad) => (
                  <option key={ciudad.id} value={ciudad.id}>
                    {ciudad.descripcion}
                  </option>
                ))}
              </select>
            </div>
            <div className="flex flex-col">
              <select onChange={handleEstadoCivilChange} className="border border-gray-300 rounded-lg p-2" required>
                <option value={0} disabled selected>Selecciona un estado civil</option>
                {estadosCiviles.map((estadoCivil) => (
                  <option key={estadoCivil.codEstadoCiviles} value={estadoCivil.codEstadoCiviles}>
                    {estadoCivil.descripcion}
                  </option>
                ))}
              </select>
            </div>
            <div className="flex flex-col">
              <select onChange={handleTipoPersonaChange} className="border border-gray-300 rounded-lg p-2" required>
                <option value={0} disabled selected>Selecciona un tipo de persona</option>
                {tipoPersonas.map((tipoPersona) => (
                  <option key={tipoPersona.codTipoPersonas} value={tipoPersona.codTipoPersonas}>
                    {tipoPersona.descripcion}
                  </option>
                ))}
              </select>
            </div>
            <div className="flex flex-col">
              <select onChange={handleNacionalidadChange} className="border border-gray-300 rounded-lg p-2" required>
                <option value={0} disabled selected>Selecciona una nacionalidad</option>
                {nacionalidades.map((nacionalidad) => (
                  <option key={nacionalidad.id} value={nacionalidad.id}>
                    {nacionalidad.descripcion}
                  </option>
                ))}
              </select>
            </div>
          </div>
          <div className="flex justify-end mt-6">
            <Link to="/personas">
              <button className="cta-button ml-7">Atrás</button>
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

export default AddPersona;
