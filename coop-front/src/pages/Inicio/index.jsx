
import { ArrowLeft, Check } from 'react-feather';
import { Link, useNavigate } from 'react-router-dom';

import myImage from '../../assets/naranja.png';

import '../Inicio/inicio.css'

export const Inicio = () => {

  return (
    <>


      <div className='wrapper'>

        <img src={myImage} alt="My image" />

        <div className="logo"></div>


        <h2 className='text-rigth'>Bienvenido</h2>

        <h2 className='text-rigth_inova'>Inova8M</h2>

      </div>

    </>

  );
}
