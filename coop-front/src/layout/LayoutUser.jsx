import { Outlet } from 'react-router-dom';
import Header from '../components/Header';
import Sidebar from '../components/Sidebar';
import '../layout/style.css'

function LayoutUser() {
  return (
    <div className='flex flex-col min-h-screen text-custom-white'>
      
      <div className='flex flex-row flex-grow h-full w-full'>
        <Sidebar />
        <main className='flex-grow sm:bg-custom-gris-claro pt-10 pb-10 flex justify-center items-center'>
          <Outlet />
        </main>
      </div>
    </div>
  );
}

export default LayoutUser;
