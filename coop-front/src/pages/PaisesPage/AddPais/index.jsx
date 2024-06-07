import { useState } from 'react';
import { ArrowLeft, Check } from 'react-feather';
import { Link, useNavigate } from 'react-router-dom';
import { serviceCreateCountry } from '../../../services/paises/paises'; // Ajusta la importación del servicio de crear país
import Alertas from '../../../components/Alertas/Alertas';
import '../../../components/EstandarCSS/Insert.css';

export const AddPais = () => {
  const P = "país";
  const navigate = useNavigate();
  const [descripcion, setDescripcion] = useState('');

  const handleDescripcionChange = (e) => {
    const { value } = e.target;
    const regex = /^[A-Za-z\s]*$/;
    if (regex.test(value)) {
      setDescripcion(value);
    } else {
      Alertas.customAlert_string(P);
    }
  };

  const handleSave = async (e) => {
    e.preventDefault();

    if (!descripcion) {
      Alertas.customAlert_descripcion();
      return;
    }

    try {
      await serviceCreateCountry({ descripcion });
      Alertas.customAlert_agregar(P);
      navigate('/paises');
    } catch (err) {
      console.log(err);

      if (err.response && err.response.data) {
        console.log(err.response.data);

        if (err.response.data.message === 'El país ya existe') {
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
    <div className='contenido_general_A'>
      <div className='text-custom-black p-12 bg-gray-100 sm:bg-white sm:rounded-xl flex flex-col sm:justify-center sm:align-baseline gap-14 absolute top-0 left-0 bottom-0 right-0 sm:static w-full h-auto sm:w-auto sm:h-auto overflow-auto sm:overflow-visible'>
        <h3 className='titulo_general_A'>
          Agregar País
        </h3>
        <div className='flex sm:hidden mb-0 justify-normal items-center'>
          <h3 className='text-xl items-start ml-2'>Agregar País</h3>
          <Link className='absolute left-2' to='/paises'>
            <ArrowLeft />
          </Link>
          <button
            onClick={handleSave}
            className='absolute right-7 rounded-3xl'
          >
            <Check size={30} color='#3E43C7' />
          </button>
        </div>
        <div className='w-full grid grid-cols-1 sm:grid-cols-2 gap-7'>
          <div className='flex flex-col'>
            <input
              type='text'
              value={descripcion}
              onChange={handleDescripcionChange}
              className='border-b-2 border-gray-300 p-2 input-animated-border'
              placeholder='Ingresa un país'
              required
            />
          </div>
        </div>
        <div className='flex justify-end'>
          <Link to='/paises'>
            <button className='cta-button ml-7'>Atras</button>
          </Link>
          <button
            onClick={handleSave}
            className='cta-button ml-12'
          >
            Guardar
          </button>
        </div>
      </div>
    </div>
  );
};

export default AddPais;
