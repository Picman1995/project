import { useState, useEffect } from 'react';
import { ArrowLeft, Check } from 'react-feather';
import { Link, useNavigate } from 'react-router-dom';
import { serviceCreatePerson } from '../../../services/personas/personas';
import './style.css';
import Swal from 'sweetalert2';

export const AddBarrio = () => {
  const navigate = useNavigate();

  const [form, setForm] = useState({
    nroDocumento: '',
    nombre1: '',
    nombre2: '',
    nombre3: '',
    apellido1: '',
    apellido2: '',
    apellido3: '',
    fechaNacimiento: '',
    sexo: '',
    tipoDocumento: 1,
    tipoPersona: 1,
    estadoCivil: 1,
    ciudad: 1,
    nacionalidad: 1,
  });

  const handleFormChange = (e) => {
    setForm((prevForm) => ({ ...prevForm, [e.target.name]: e.target.value }));
  };

  const handleSave = async (e) => {
    e.preventDefault();
    try {
      await serviceCreatePerson(form);

      Swal.fire({
        position: "top-center",
        icon: "success",
        title: "Persona creada correctamente",
        showConfirmButton: false,
        timer: 1500
      });

      navigate('/barrios');
    } catch (err) {
      console.log(err);
    }
  };

  useEffect(() => {
    const dateInput = document.querySelector('input[type="date"]');
    if (dateInput && dateInput.value) {
      dateInput.classList.add('not-empty');
    }
  }, [form.fechaNacimiento]);

  return (
    <>
  

   
      <div className='contenido'>
        <div className='text-custom-black m-5  flex flex-col gap-14'>
          <h3 className='titulo_persona'>
            Agregar Barrio
          </h3>
          <div className='flex sm:hidden mb-0 justify-normal items-center'>
            <h3 className='text-xl  items-start ml-2'>Agregar Personas</h3>
            <Link className='absolute left-2 ' to='/barrios'>
              <ArrowLeft />
            </Link>
            <button
              onClick={handleSave}
              className='absolute right-7 rounded-3xl'
            >
              <Check size={30} color='#3E43C7' />
            </button>
          </div>
          <div className='w-full grid grid-cols-1 sm:grid-cols-2 gap-7  '>
            <div className='flex flex-col mr-5'>
              <input
                type='text'
                value={form.nombre1}
                onChange={handleFormChange}
                name='nombre1'
                className='border-b-2 border-gray-300 p-2 input-animated-border'
                placeholder='Ingresa el primer nombre'
                required
              />
            </div>
            <div className='flex flex-col mr-5'>
              <input
                type='text'
                value={form.nombre2}
                onChange={handleFormChange}
                name='nombre2'
                className='border-b-2 border-gray-300 p-2 input-animated-border'
                placeholder='Ingresa el segundo nombre'
                required
              />
            </div>
            <div className='flex flex-col mr-5'>
              <input
                type='text'
                value={form.nombre3}
                onChange={handleFormChange}
                name='nombre3'
                className='border-b-2 border-gray-300 p-2 input-animated-border'
                placeholder='Ingresa el tercer nombre'
                required
              />
            </div>
            <div className='flex flex-col mr-5'>
              <input
                type='text'
                value={form.apellido1}
                onChange={handleFormChange}
                name='apellido1'
                className='border-b-2 border-gray-300 p-2 input-animated-border'
                placeholder='Ingresa el primer apellido'
                required
              />
            </div>
            <div className='flex flex-col mr-5'>
              <input
                type='text'
                value={form.apellido2}
                onChange={handleFormChange}
                name='apellido2'
                className='border-b-2 border-gray-300 p-2 input-animated-border'
                placeholder='Ingresa el segundo apellido'
              />
            </div>
            <div className='flex flex-col mr-5'>
              <input
                type='text'
                value={form.apellido3}
                onChange={handleFormChange}
                name='apellido3'
                className='border-b-2 border-gray-300 p-2 input-animated-border'
                placeholder='Ingresa el tercer apellido'
              />
            </div>
            <div className='flex flex-col mr-5'>
              <input
                type='text'
                value={form.nroDocumento}
                onChange={handleFormChange}
                name='nroDocumento'
                className='border-b-2 border-gray-300 p-2 input-animated-border'
                placeholder='Ingresa el número de documento'
                required
              />
            </div>

            <div className='flex flex-col mr-5'>
            <p className=''>Fecha de nacimiento</p>
              <div className="input-wrapper" data-placeholder="">
                <input
                  type='date'
                  value={form.fechaNacimiento}
                  onChange={handleFormChange}
                  name='fechaNacimiento'
                  className='border-b-2 border-gray-300 p-2 input-animated-border'
                  required
                  placeholder="Ingrese fecha de nacimiento" />
              </div>
            </div>

            <div className='flex flex-col mr-5'>
             
              <select
                value={form.sexo}
                onChange={handleFormChange}
                name='sexo'
                className='border-b-2 border-gray-300 p-2 input-animated-border'
                required
              >
                <option value=''>Seleccione el sexo</option>
                <option value='M'>Masculino</option>
                <option value='F'>Femenino</option>
              </select>
            </div>
            {/* Add other fields similarly */}
            <div className='flex flex-col mr-5'>
              <p className='font-sans text-base'>Tipo de Documento</p>
              <input
                type='number'
                value={form.tipoDocumento}
                onChange={handleFormChange}
                name='tipoDocumento'
                className='border-b-2 border-gray-300 p-2 input-animated-border'
              />
            </div>
            <div className='flex flex-col mr-5'>
              <p className=''>Tipo de Persona</p>
              <input
                type='number'
                value={form.tipoPersona}
                onChange={handleFormChange}
                name='tipoPersona'
                className='border-b-2 border-gray-300 p-2 input-animated-border'
              />
            </div>
            <div className='flex flex-col mr-5'>
              <p className=''>Estado Civil</p>
              <input
                type='number'
                value={form.estadoCivil}
                onChange={handleFormChange}
                name='estadoCivil'
                className='border-b-2 border-gray-300 p-2 input-animated-border'
              />
            </div>
            <div className='flex flex-col mr-5'>
              <p className=''>Nacionalidad</p>
              <input
                type='number'
                value={form.nacionalidad}
                onChange={handleFormChange}
                name='nacionalidad'
                className='border-b-2 border-gray-300 p-2 input-animated-border'
              />
            </div>
            <div className='flex flex-col mr-5'>
              <p className=''>Ciudad</p>
              <input
                type='number'
                value={form.ciudad}
                onChange={handleFormChange}
                name='ciudad'
                className='border-b-2 border-gray-300 p-2 input-animated-border'
              />
            </div>
          </div>
          <div className='flex justify-end '>
            <Link to='/barrios'>
            
              <button className='bx bxs-left-arrow cta-button ml-7'></button>
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
   
      
    </>
    
  );
}
