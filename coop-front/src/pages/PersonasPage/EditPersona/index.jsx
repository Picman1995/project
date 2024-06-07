import React, { useState, useEffect } from 'react';
import { ArrowLeft, Check } from 'react-feather';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import { serviceEditPerson, serviceGetPersons, serviceGetPersonByNroDocumento } from '../../../services/personas/personas';
import { serviceGetCiudades } from '../../../services/ciudades/ciudades';
import { serviceGetTipoDocumentos } from '../../../services/tipoDocumentos/tipoDocumentos';
import { serviceGetEstCivil } from '../../../services/estadosCiviles/estadosCiviles';
import { serviceGetTipoPersonas } from '../../../services/tipoPersonas/tipoPersonas';
import { serviceGetNacionalidades } from '../../../services/nacionalidades/nacionalidades';
import DatePicker from "react-datepicker";
import "react-datepicker/dist/react-datepicker.css";

const EditPersona = () => {
  const navigate = useNavigate();
  const location = useLocation();

  const [persona, setPersona] = useState({
    id: null,
    nombre1: '',
    nombre2: '',
    nombre3: '',
    apellido1: '',
    apellido2: '',
    apellido3: '',
    nroDocumento: '',
    fechaNacimiento:  Date(),
    sexo: '',
    tipoDocumentoId: 0,
    ciudadId: 0,
    codEstadoCiviles: 0,
    tipoPersonaId: 0,
    nacionalidadId: 0
  });

  const [ciudades, setCiudades] = useState([]);
  const [tipoDocumentos, setTipoDocumentos] = useState([]);
  const [estadosCiviles, setEstadosCiviles] = useState([]);
  const [tipoPersonas, setTipoPersonas] = useState([]);
  const [nacionalidades, setNacionalidades] = useState([]);
  const [searching, setSearching] = useState('');
  const [confirmationMessage, setConfirmationMessage] = useState('');

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [
          tipoDocumentosResponse,
          ciudadesResponse,
          estadosCivilesResponse,
          tipoPersonasResponse,
          nacionalidadesResponse
        ] = await Promise.all([
          serviceGetTipoDocumentos(),
          serviceGetCiudades(),
          serviceGetEstCivil(),
          serviceGetTipoPersonas(),
          serviceGetNacionalidades()
        ]);

        if (tipoDocumentosResponse.data && Array.isArray(tipoDocumentosResponse.data.tipoDocumentos)) {
          setTipoDocumentos(tipoDocumentosResponse.data.tipoDocumentos);
        }

        if (ciudadesResponse.data && Array.isArray(ciudadesResponse.data.ciudades)) {
          setCiudades(ciudadesResponse.data.ciudades);
        }

        if (estadosCivilesResponse.data && Array.isArray(estadosCivilesResponse.data.estadoCiviles)) {
          setEstadosCiviles(estadosCivilesResponse.data.estadoCiviles);
        }

        if (tipoPersonasResponse.data && Array.isArray(tipoPersonasResponse.data.tipoPersonas)) {
          setTipoPersonas(tipoPersonasResponse.data.tipoPersonas);
        }

        if (nacionalidadesResponse.data && Array.isArray(nacionalidadesResponse.data.nacionalidades)) {
          setNacionalidades(nacionalidadesResponse.data.nacionalidades);
        }

        if (location.state && location.state.persona) {
          setPersona(location.state.persona);
        } else {
          console.error('No se encontraron datos de persona en la ubicación.');
        }

      } catch (error) {
        console.error('Error al obtener los datos:', error);
      }
    };

    fetchData();
  }, [location]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setPersona(prevPersona => ({
      ...prevPersona,
      [name]: value
    }));
  };

  const handleFechaNacimientoChange = (date) => {
    setPersona(prevPersona => ({
      ...prevPersona,
      fechaNacimiento: date
    }));
  };

  const handleSave = async (e) => {
    e.preventDefault();
    try {
      console.log('Datos a enviar al servidor:', persona); // Agregar este console.log
      await serviceEditPerson(persona);
      setConfirmationMessage('Persona actualizada correctamente');
      setTimeout(() => {
        setConfirmationMessage('');
        navigate('/personas');
      }, 3000); // El mensaje desaparecerá después de 3 segundos
    } catch (error) {
      console.error('Error al actualizar la persona:', error);
      // Manejar el error de acuerdo a tus necesidades
    }
  };
  

  const handleSearchKeyDown = async (e) => {
    if (e.key === 'Enter') {
      handleSearch();
    }
  };

  const handleSearch = async () => {
    try {
      const response = await serviceGetPersonByNroDocumento(searching);
      if (response.data && response.data.personas && response.data.personas.length > 0) {
        const personaEncontrada = response.data.personas[0]; // Acceder al primer elemento del array
        console.log('Persona encontrada:', personaEncontrada);
        setPersona({
          id: personaEncontrada.id || null,
          nombre1: personaEncontrada.nombre1 || '',
          nombre2: personaEncontrada.nombre2 || '',
          nombre3: personaEncontrada.nombre3 || '',
          apellido1: personaEncontrada.apellido1 || '',
          apellido2: personaEncontrada.apellido2 || '',
          apellido3: personaEncontrada.apellido3 || '',
          nroDocumento: personaEncontrada.nroDocumento || '',
          fechaNacimiento: personaEncontrada.fechaNacimiento ? new Date(personaEncontrada.fechaNacimiento) : new Date(),
          sexo: personaEncontrada.sexo || '',
          tipoDocumentoId: personaEncontrada.idTipoDocumento || 0,
          ciudadId: personaEncontrada.idCiudad || 0,
          codEstadoCiviles: personaEncontrada.idEstadoCivil || 0,
          tipoPersonaId: personaEncontrada.idTipoPersona || 0,
          nacionalidadId: personaEncontrada.idNacionalidad || 0
        });
      } else {
        console.error('No se encontraron datos de persona con el número de cédula:', searching);
      }
    } catch (error) {
      console.error('Error al buscar la persona:', error);
    }
  };

  return (
    <>
      <div className='contenido_general_A'>
        <div className="text-custom-black p-12 bg-gray-100 sm:bg-white sm:rounded-xl flex flex-col sm:justify-center sm:align-baseline gap-14 absolute top-0 left-0 bottom-0 right-0 sm:static w-full h-auto sm:w-auto sm:h-auto overflow-auto sm:overflow-visible">
          <h3 className="titulo_general_A">Editar Persona</h3>
          <div className="flex sm:hidden mb-0 justify-normal items-center">
            <h3 className="text-xl items-start ml-2">Editar Persona</h3>
            <Link className="absolute left-2" to="/personas">
              <ArrowLeft />
            </Link>
            <button className="absolute right-7 rounded-3xl">
              <Check size={30} color="#3E43C7" />
            </button>
          </div>
          <div className="w-full grid grid-cols-1 sm:grid-cols-2 gap-7">
            <div className="flex flex-col">
              <input
                type="text"
                name="nombre1"
                value={persona.nombre1}
                onChange={handleChange}
                className="border-b-2 border-gray-300 p-2 input-animated-border"
                placeholder="Primer Nombre"
                required
              />
            </div>
            <div className="flex flex-col">
              <input
                type="text"
                name="nombre2"
                value={persona.nombre2}
                onChange={handleChange}
                className="border-b-2 border-gray-300 p-2 input-animated-border"
                placeholder="Segundo Nombre"
              />
            </div>
            <div className="flex flex-col">
              <input
                type="text"
                name="nombre3"
                value={persona.nombre3}
                onChange={handleChange}
                className="border-b-2 border-gray-300 p-2 input-animated-border"
                placeholder="Tercer Nombre"
              />
            </div>
            <div className="flex flex-col">
              <input
                type="text"
                name="apellido1"
                value={persona.apellido1}
                onChange={handleChange}
                className="border-b-2 border-gray-300 p-2 input-animated-border"
                placeholder="Primer Apellido"
                required
              />
            </div>
            <div className="flex flex-col">
              <input
                type="text"
                name="apellido2"
                value={persona.apellido2}
                onChange={handleChange}
                className="border-b-2 border-gray-300 p-2 input-animated-border"
                placeholder="Segundo Apellido"
              />
            </div>
            <div className="flex flex-col">
              <input
                type="text"
                name="apellido3"
                value={persona.apellido3}
                onChange={handleChange}
                className="border-b-2 border-gray-300 p-2 input-animated-border"
                placeholder="Tercer Apellido"
              />
            </div>
            <div className="flex flex-col">
              <input
                type="text"
                name="nroDocumento"
                value={persona.nroDocumento}
                onChange={handleChange}
                className="border-b-2 border-gray-300 p-2 input-animated-border"
                placeholder="Número de Documento"
                required
              />
            </div>
            <div className="flex flex-col">
              <DatePicker
                selected={persona.fechaNacimiento}
                onChange={date => handleFechaNacimientoChange(date)}
                className="border-b-2 border-gray-300 p-2 input-animated-border"
                dateFormat="yyyy-MM-dd"
                placeholderText="Fecha de Nacimiento"
                required
              />
            </div>
            <div className="flex flex-col">
              <select
                name="sexo"
                value={persona.sexo}
                onChange={handleChange}
                className="border border-gray-300 rounded-lg p-2"
                required
              >
                <option value="" disabled>Selecciona el sexo</option>
                <option value="M">Masculino</option>
                <option value="F">Femenino</option>
              </select>
            </div>
            <div className="flex flex-col">
              <select
                name="tipoDocumentoId"
                value={persona.tipoDocumentoId}
                onChange={handleChange}
                className="border border-gray-300 rounded-lg p-2"
                required
              >
                <option value={0} disabled>Selecciona un tipo de documento</option>
                {tipoDocumentos.map((tipoDocumento) => (
                  <option key={tipoDocumento.codTipoDocumentos} value={tipoDocumento.codTipoDocumentos}>
                    {tipoDocumento.descripcion}
                  </option>
                ))}
              </select>
            </div>
            <div className="flex flex-col">
              <select
                name="ciudadId"
                value={persona.ciudadId}
                onChange={handleChange}
                className="border border-gray-300 rounded-lg p-2"
                required
              >
                <option value={0} disabled>Selecciona una ciudad</option>
                {ciudades.map((ciudad) => (
                  <option key={ciudad.id} value={ciudad.id}>
                    {ciudad.descripcion}
                  </option>
                ))}
              </select>
            </div>
            <div className="flex flex-col">
              <select
                name="codEstadoCiviles"
                value={persona.codEstadoCiviles}
                onChange={handleChange}
                className="border border-gray-300 rounded-lg p-2"
                required
              >
                <option value={0} disabled>Selecciona un estado civil</option>
                {estadosCiviles.map((estadoCivil) => (
                  <option key={estadoCivil.codEstadoCiviles} value={estadoCivil.codEstadoCiviles}>
                    {estadoCivil.descripcion}
                  </option>
                ))}
              </select>
            </div>
            <div className="flex flex-col">
              <select
                name="tipoPersonaId"
                value={persona.tipoPersonaId}
                onChange={handleChange}
                className="border border-gray-300 rounded-lg p-2"
                required
              >
                <option value={0} disabled>Selecciona un tipo de persona</option>
                {tipoPersonas.map((tipoPersona) => (
                  <option key={tipoPersona.codTipoPersonas} value={tipoPersona.codTipoPersonas}>
                    {tipoPersona.descripcion}
                  </option>
                ))}
              </select>
            </div>
            <div className="flex flex-col">
              <select
                name="nacionalidadId"
                value={persona.nacionalidadId}
                onChange={handleChange}
                className="border border-gray-300 rounded-lg p-2"
                required
              >
                <option value={0} disabled>Selecciona una nacionalidad</option>
                {nacionalidades.map((nacionalidad) => (
                  <option key={nacionalidad.id} value={nacionalidad.id}>
                    {nacionalidad.descripcion}
                  </option>
                ))}
              </select>
            </div>
          </div>
          {confirmationMessage && (
            <div className="mt-4 text-green-500">
              {confirmationMessage}
            </div>
          )}
          <div className="flex justify-end mt-6">
            <Link to="/personas">
              <button className="cta-button ml-7">Cancelar</button>
            </Link>
            <button onClick={handleSave} className="cta-button ml-12">
              Guardar
            </button>
          </div>
        </div>
      </div>
      <div className='contenido_general_A'>
        <div className="text-custom-black p-12 bg-gray-100 sm:bg-white sm:rounded-xl flex flex-col sm:justify-center sm:align-baseline gap-14 absolute top-0 left-0 bottom-0 right-0 sm:static w-full h-auto sm:w-auto sm:h-auto overflow-auto sm:overflow-visible">
          <h3 className="titulo_general_A">Buscar Persona por Número de Cédula</h3>
          <div className="flex gap-4 items-center">
            <input
              type="text"
              value={searching}
              onChange={(e) => setSearching(e.target.value)}
              onKeyDown={handleSearchKeyDown}
              placeholder="Ingrese número de cédula"
              className="border-b-2 border-gray-300 p-2 input-animated-border"
            />
            <button onClick={handleSearch} className="cta-button">
              Buscar
            </button>
          </div>
        </div>
      </div>
    </>
  );
};

export default EditPersona;
