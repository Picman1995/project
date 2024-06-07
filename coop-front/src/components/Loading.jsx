import { useState, useEffect } from 'react';
/*import '../components/loading.css';*/

const LoadingComponent = () => {
  // Estado para controlar el ancho de la barra de carga
  const [loadingWidth, setLoadingWidth] = useState(50);

  // Simula un efecto de carga con una duración mínima de un minuto
  useEffect(() => {
    const interval = setInterval(() => {
      if (loadingWidth < 100) {
        setLoadingWidth((prevWidth) => {
          const newWidth = prevWidth + 10;
          return newWidth > 100 ? 100 : newWidth;
        });
      }
    }, 20); // Adjust this value to control the speed of the animation

    // Ensure the loading lasts at least one minute
    const timeout = setTimeout(() => {
      if (loadingWidth < 100) {
        setLoadingWidth(100);
      }
    }, 60000); // Minimum duration in milliseconds (1 minute = 60 seconds * 1000 milliseconds)

    return () => {
      clearInterval(interval);
      clearTimeout(timeout);
    };
  }, [loadingWidth]);

  return (
    <div>

    </div>
  );
};

export default LoadingComponent;
