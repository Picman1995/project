import { useState, useEffect } from 'react';
import { ArrowLeft, Check } from 'react-feather';
import { Link, useNavigate } from 'react-router-dom';
import { serviceCreateNacionalidades } from '../../../services/nacionalidades/nacionalidades'; // Importa la función para crear nacionalidades
import '../../../components/EstandarCSS/Insert.css';

export const AddNacionalidad = () => {
  const navigate = useNavigate();

  const [descripcion, setDescripcion] = useState('');
  const [showConfirmation, setShowConfirmation] = useState(false);

  const handleDescripcionChange = (e) => setDescripcion(e.target.value.toUpperCase());

  const handleSave = async (e) => {
    e.preventDefault();
    try {
      await serviceCreateNacionalidades({ descripcion });
      setShowConfirmation(true);
      setTimeout(() => {
        setShowConfirmation(false);
        navigate('/nacionalidades');
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
              <h2 className="text-2xl font-bold text-white mb-4">¡Nacionalidad Agregada!</h2>
              <p className="text-white text-lg mb-4">Se ha insertado una nueva nacionalidad.</p>
              <button onClick={() => setShowConfirmation(false)}>OK</button>
            </div>
          </div>
        )}
        <div className="text-custom-black p-12 bg-gray-100 sm:bg-white sm:rounded-xl flex flex-col sm:justify-center sm:align-baseline gap-14 absolute top-0 left-0 bottom-0 right-0 sm:static w-full h-auto sm:w-auto sm:h-auto overflow-auto sm:overflow-visible">
          <h3 className="titulo_general_A">Agregar Nacionalidad</h3>
          <div className="flex sm:hidden mb-0 justify-normal items-center">
            <h3 className="text-xl items-start ml-2">Agregar Nacionalidad</h3>
            <Link className="absolute left-2" to="/nacionalidades">
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
                placeholder="Ingresa la descripción de la nacionalidad"
                required
              />
            </div>
          </div>
          <div className="flex justify-end mt-6">
            <Link to="/nacionalidades">
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

export default AddNacionalidad;
