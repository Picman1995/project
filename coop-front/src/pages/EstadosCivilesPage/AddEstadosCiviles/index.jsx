import { useState } from 'react';
import { ArrowLeft, Check } from 'react-feather';
import { Link, useNavigate } from 'react-router-dom';
import { serviceCreateEstCivil } from '../../../services/estadosCiviles/estadosCiviles';
import Alertas from '../../../components/Alertas/Alertas';
import '../../../components/EstandarCSS/Insert.css';

export const AddEstadosCiviles = () => {
  const E = "estado civil";
  const navigate = useNavigate();
  const [descripcion, setDescripcion] = useState('');

  const handleDescripcionChange = (e) => {
    const { value } = e.target;
    const regex = /^[A-Za-z\s]*$/;
    if (regex.test(value)) {
      setDescripcion(value);
    } else {
      Alertas.customAlert_string(E);
    }
  };

  const handleSave = async (e) => {
    e.preventDefault();

    if (!descripcion) {
      Alertas.customAlert_descripcion();
      return;
    }

    try {
      await serviceCreateEstCivil({ descripcion });
      Alertas.customAlert_agregar(E);
      navigate('/estadosCiviles');
    } catch (err) {
      console.log(err);

      if (err.response && err.response.data) {
        console.log(err.response.data);

        if (err.response.data.message === 'El estado civil ya existe') {
          Alertas.customAlert_existente(E);
        } else {
          Alertas.customAlert_error(E, err.response.data.message);
        }
      } else {
        Alertas.customAlert_error(E);
      }
    }
  };

  return (
    <div className='contenido_general_A'>
      <div className='text-custom-black p-12 bg-gray-100 sm:bg-white sm:rounded-xl flex flex-col sm:justify-center sm:align-baseline gap-14 absolute top-0 left-0 bottom-0 right-0 sm:static w-full h-auto sm:w-auto sm:h-auto overflow-auto sm:overflow-visible'>
        <h3 className='titulo_general_A'>
          Agregar Estado Civil
        </h3>
        <div className='flex sm:hidden mb-0 justify-normal items-center'>
          <h3 className='text-xl items-start ml-2'>Agregar Estado Civil</h3>
          <Link className='absolute left-2' to='/estadosciviles'>
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
              placeholder='Ingresa un estado civil'
              required
            />
          </div>
        </div>
        <div className='flex justify-end'>
          <Link to='/estadosCiviles'>
            <button className='cta-button ml-7'>Atrás</button>
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

export default AddEstadosCiviles;
