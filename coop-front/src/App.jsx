import { Route, Routes, Outlet } from 'react-router-dom';
import { ProtectedRoute } from './components/ProtectedRoute';
import { GuestRoute } from './components/GuestRoute';   
import LoginPage from './pages/LoginPage';
import SignUpPage from './pages/SignUpPage';

import PersonasPage from './pages/PersonasPage';
import { AddPersona } from './pages/PersonasPage/AddPersona';
import  EditPersona  from './pages/PersonasPage/EditPersona';

import PaisesPage from './pages/PaisesPage';
import { AddPais } from './pages/PaisesPage/AddPais';

import TipoVinculosPage from './pages/TipoVinculosPage';
import { AddTipoVinculos } from './pages/TipoVinculosPage/AddTipoVinculos';

import TipoDocumentosPage from './pages/TipoDocumentosPage';
import { AddTipoDocumentos } from './pages/TipoDocumentosPage/AddTipoDocumentos';

import TipoPersonasPage from './pages/TipoPersonasPage';
import { AddTipoPersonas } from './pages/TipoPersonasPage/AddTipoPersonas';

import DepartamentosPage from './pages/DepartamentosPage';
import { AddDepartamento } from './pages/DepartamentosPage/AddDepartamento';

import DistritosPage from './pages/DistritosPage';
import { AddDistrito } from './pages/DistritosPage/AddDistrito';

import CiudadesPage from './pages/CiudadesPage';
import { AddCiudad } from './pages/CiudadesPage/AddCiudad';

import DireccionesPage from './pages/DireccionesPage';
import { AddDireccion } from './pages/DireccionesPage/AddDireccion';

import EstadosCivilesPage from './pages/EstadosCivilesPage';
import AddEstadosCiviles from './pages/EstadosCivilesPage/AddEstadosCiviles';

import NacionalidadesPage from './pages/NacionalidadesPage';
import { AddNacionalidad } from './pages/NacionalidadesPage/AddNacionalidad';

import { Inicio } from './pages/Inicio';

import BarriosPage from './pages/BarriosPage';
import { AddBarrio } from './pages/BarriosPage/AddBarrio';

import UserProfilePage from './pages/UserProfilePage';
import NotFoundPage from './pages/NotFoundPage';
import BranchesPage from './pages/BranchesPage';
import LayoutUser from './layout/LayoutUser';
import axios from 'axios';


axios.defaults.baseURL =
  import.meta.env.VITE_BACKEND_URL || 'http://localhost:8081';

function App() {
  return (
    <main>
      <Routes>
        {/* Guest Routes */}
        <Route path='/' element={<GuestRoute component={<Outlet />} />}>
          <Route path='/' element={<LoginPage />} />
          <Route path='/login' element={<LoginPage />} />
          <Route path='/signup' element={<SignUpPage />} />
        </Route>

        {/* Protected Routes */}
        <Route path='/' element={<ProtectedRoute component={<LayoutUser />} />}>
          <Route path='/branches' element={<BranchesPage />} />

          {/* Rutas relacionadas con personas */}
          <Route path='/personas' element={<PersonasPage />} />
          <Route path='/addpersona' element={<AddPersona />} />
          <Route path='/editPersona/:id' element={<EditPersona />} />
          
          {/* Rutas relacionadas con tipo Vinculos */}
          <Route path='/tipoVinculos' element={<TipoVinculosPage />} />
          <Route path='/addTipoVinculos' element={<AddTipoVinculos />} />

          {/* Rutas relacionadas con tipo documentos */}
          <Route path='/tipoDocumentos' element={<TipoDocumentosPage />} />
          <Route path='/addTipoDocumentos' element={<AddTipoDocumentos />} />

          {/* Rutas relacionadas con estados civiles */}
          <Route path='/estadosCiviles' element={<EstadosCivilesPage />} />
          <Route path='/addEstadosCiviles' element={<AddEstadosCiviles />} />

          {/* Rutas relacionadas con países */}
          <Route path='/paises' element={<PaisesPage />} /> 
          <Route path='/addpais' element={<AddPais />} /> 

          {/* Rutas relacionadas con departamentos */}
          <Route path='/departamentos' element={<DepartamentosPage />} />
          <Route path='/addDepartamento' element={<AddDepartamento />} />

          {/* Rutas relacionadas con distritos */}
          <Route path='/distritos' element={<DistritosPage />} />
          <Route path='/addDistrito' element={<AddDistrito />} />

          {/* Rutas relacionadas con ciudades */}
          <Route path='/ciudades' element={<CiudadesPage />} />
          <Route path='/addCiudad' element={<AddCiudad />} />
          
          {/* Ruta relacionadas con inicio */}
          <Route path='/inicio' element={<Inicio />} /> 

          {/* Rutas relacionadas con direcciones */}
          <Route path='/direcciones' element={<DireccionesPage />} />
          <Route path='/addDireccion' element={<AddDireccion />} />

          {/* Rutas relacionadas con nacionalidades */}
          <Route path='/nacionalidades' element={<NacionalidadesPage />} />
          <Route path='/addNacionalidad' element={<AddNacionalidad />} />

           {/* Rutas relacionadas con tipo de personas */}
          <Route path='/tipoPersonas' element={<TipoPersonasPage />} />
          <Route path='/addTipoPersonas' element={<AddTipoPersonas />} />


          {/* Rutas relacionadas con barrios */}
          <Route path='/barrios' element={<BarriosPage />} />
          <Route path='/addBarrio' element={<AddBarrio />} />

          <Route path='/profile' element={<UserProfilePage />} />
          <Route path='*' element={<NotFoundPage />} />
        </Route>
      </Routes>
    </main>
  );
}

export default App;
