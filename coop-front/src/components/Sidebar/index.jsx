import React, { useEffect, useRef } from "react";
import useAuthContext from '../../hooks/useAuthContext';
import { LogOut } from 'react-feather';
import '../Sidebar/style.css'
import 'boxicons/css/boxicons.min.css';

import Header from "../Header";

const Sidebar = () => {
  // #### Logout
  const { logout: handleLogout } = useAuthContext();


  const navbarRef = useRef(null);

  useEffect(() => {
    const mostrarMenu = () => {
      navbarRef.current.classList.toggle('mostrar-menu');

    };



    const enlacesColor = navbarRef.current.querySelectorAll('.nav__enlace');

    function colorEnlace() {
      enlacesColor.forEach(l => l.classList.remove('activo'));
      this.classList.add('activo');
    }

    enlacesColor.forEach(l => l.addEventListener('click', colorEnlace));

    // Cleanup function to remove event listeners when component unmounts
    return () => {

      enlacesColor.forEach(l => l.removeEventListener('click', colorEnlace));
    };
  }, []);


  return (

    <div className="cuerpo">


      {/* NAVEGACION */}
      <div className="navegacion" id="navbar" ref={navbarRef}>
        <nav className="contenedor-nav">
          <div>
            <a href="#" className="nav__enlace nav__logo">
              <i className='bx bxs-disc nav__icono'></i>
              <span className="nav__logo-nombre">Inova8M</span>
            </a>

            <div className="lista-nav">
              <div className="items-nav">


                <a href="/inicio" className="nav__enlace">
                  <i className='bx bx-home nav__icono'></i>
                  <span className="nav__nombre">Inicio</span>
                </a>

                <h3 className="nav__subtitulo">Menu</h3>

                <a href="#" className="nav__enlace ">
                  <i className='bx bxs-paste nav__icono'></i>
                  <span className="nav__nombre">Datos y Area Social</span>
                </a>

                <a href="#" className="nav__enlace ">
                  <i className='bx bx-book-alt nav__icono'></i>
                  <span className="nav__nombre">Ahorro</span>
                </a>

                <a href="#" className="nav__enlace ">
                  <i className='bx bx-badge-check nav__icono'></i>
                  <span className="nav__nombre">Debito Automático</span>
                </a>

                <a href="#" className="nav__enlace ">
                  <i className='bx bx-credit-card nav__icono'></i>
                  <span className="nav__nombre">Tarjetas</span>
                </a>

                <a href="#" className="nav__enlace ">
                  <i className='bx bx-money-withdraw nav__icono'></i>
                  <span className="nav__nombre">Prestamos</span>
                </a>

                <a href="#" className="nav__enlace ">
                  <i className='bx bx-receipt nav__icono'></i>
                  <span className="nav__nombre">Descuentos Vía Administrativa</span>
                </a>

                <a href="#" className="nav__enlace ">
                  <i className='bx bx-line-chart nav__icono'></i>
                  <span className="nav__nombre">Movimientos de Cobranza</span>
                </a>

                <a href="#" className="nav__enlace ">
                  <i className='bx bx-briefcase nav__icono'></i>
                  <span className="nav__nombre">Tesorería</span>
                </a>

                <a href="#" className="nav__enlace ">
                  <i className='bx bx-library nav__icono'></i>
                  <span className="nav__nombre">Contabilidad</span>
                </a>

                <div className="nav__desplegable">
                  <a href="#" className="nav__enlace">
                  <i className='bx bx-wrench nav__icono'></i>
                    <span className="nav__nombre">Administración de Sistema</span>
                    <i className='bx bx-chevron-down nav__icono nav__desplegable-icono'></i>
                  </a>

                  <div className="nav__desplegable-colapso">
                    <div className="nav__desplegable-contenido">

                      <div className="nav__desplegable">
                        <a href="#" className="nav__desplegable-item nav__enlace">
                        <i className='bx bx-group nav__icono'></i>
                          Personas
                          <i className='bx bx-chevron-right nav__icono nav__desplegable-icono'></i>
                        </a>
                        <div className="nav__desplegable-colapso">
                          <div className="nav__desplegable-contenido">
                            <a href="/personas" className="nav__desplegable-item">◆ Persona</a>
                            <a href="/estadosCiviles" className="nav__desplegable-item">◆ Estado Civil</a>
                            <a href="#" className="nav__desplegable-item">◆ Profesión</a>
                            <a href="#" className="nav__desplegable-item">◆ Vehiculo</a>
                            <a href="/nacionalidades" className="nav__desplegable-item">◆ Nacionalidad</a>
                            <a href="/tipoDocumentos" className="nav__desplegable-item">◆ Tipo de Documento</a>
                            <a href="/tipoVinculos" className="nav__desplegable-item">◆ Tipo de Vinculo</a>
                            <a href="/tipoPersonas" className="nav__desplegable-item">◆ Tipo Persona</a>
                          </div>
                        </div>
                      </div>

                      <div className="nav__desplegable">
                        <a href="#" className="nav__desplegable-item nav__enlace">
                        <i className='bx bx-map-pin nav__icono'></i>
                        localización
                          <i className='bx bx-chevron-right nav__icono nav__desplegable-icono'></i>
                        </a>
                        <div className="nav__desplegable-colapso">
                          <div className="nav__desplegable-contenido">
                            <a href="/paises" className="nav__desplegable-item">◆ Pais</a>
                            <a href="/departamentos" className="nav__desplegable-item">◆ Departamento</a>
                            <a href="/distritos" className="nav__desplegable-item">◆ Distrito</a>
                            <a href="/ciudades" className="nav__desplegable-item">◆ Ciudad</a>
                            <a href="/direcciones" className="nav__desplegable-item">◆ Direccion</a>
                            <a href="/barrios" className="nav__desplegable-item">◆ Barrio</a>
                          </div>
                        </div>
                      </div>


                    </div>
                  </div>
                </div>


                <a href="#" className="nav__enlace">
                  <i className='bx bx-microchip nav__icono'></i>
                  <span className="nav__nombre">Seguridad</span>
                </a>

                <a href="#" className="nav__enlace">
                  <i className='bx bx-file-find nav__icono'></i>
                  <span className="nav__nombre">Auditoria</span>
                </a>

              </div>


            </div>
          </div>



          <div className="x">
          <a
            href="#"
            onClick={(e) => {
              e.preventDefault();
              handleLogout();
            }}
            className="nav__enlace nav__cerrar-sesion"
          >
            <i className='bx bx-log-out nav__icono'></i>
            <span className="nav__nombre">Cerrar Sesión</span>
          </a>
        </div>

        </nav>


      </div>



    </div>

  );
}

export default Sidebar;
