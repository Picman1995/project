import Swal from 'sweetalert2';

const Alertas = {
  success_delete: (x) => {
    const style = document.createElement('style');
    style.innerHTML = `
      .swal-confirm-button {
        background-color: #4CAF50 !important;
        color: #ffffff !important;
      }
    `;
    document.head.appendChild(style);

    Swal.fire({
      title: 'Eliminado',
      text: 'El ' + x + ' ha sido eliminado con éxito.',
      icon: 'success',
      confirmButtonText: 'Aceptar',
      customClass: {
        confirmButton: 'swal-confirm-button',
      }
    });
  },
  error_delete: (x) => {
    const style = document.createElement('style');
    style.innerHTML = `
      .swal-confirm-button {
        background-color: #f44336 !important;
        color: #ffffff !important;
      }
    `;
    document.head.appendChild(style);

    Swal.fire({
      title: 'Error',
      text: 'Hubo un error al eliminar el ' + x + '.',
      icon: 'error',
      confirmButtonText: 'Aceptar',
      customClass: {
        confirmButton: 'swal-confirm-button',
      }
    });
  },
  success_edit: () => {
    const style = document.createElement('style');
    style.innerHTML = `
      .swal-confirm-button {
        background-color: #4CAF50 !important;
        color: #ffffff !important;
      }
    `;
    document.head.appendChild(style);

    Swal.fire({
      title: 'Guardado',
      text: 'Los cambios han sido guardados con éxito.',
      icon: 'success',
      confirmButtonText: 'Aceptar',
      customClass: {
        confirmButton: 'swal-confirm-button',
      }
    });
  },
  error_edit: () => {
    const style = document.createElement('style');
    style.innerHTML = `
      .swal-confirm-button {
        background-color: #f44336 !important;
        color: #ffffff !important;
      }
    `;
    document.head.appendChild(style);

    Swal.fire({
      title: 'Error',
      text: 'Hubo un error al guardar los cambios.',
      icon: 'error',
      confirmButtonText: 'Aceptar',
      customClass: {
        confirmButton: 'swal-confirm-button',
      }
    });
  },
  confirm: (x, callback) => {
    const style = document.createElement('style');
    style.innerHTML = `
      .swal-confirm-button {
        background-color: #4CAF50 !important;
        color: #ffffff !important;
      }
      .swal-cancel-button {
        background-color: #f44336 !important;
        color: #ffffff !important;
      }
    `;
    document.head.appendChild(style);

    Swal.fire({
      title: '¿Estás seguro de eliminar este ' + x + '?',
      text: 'Esta acción no se puede deshacer.',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonText: 'Sí, eliminar',
      cancelButtonText: 'Cancelar',
      customClass: {
        confirmButton: 'swal-confirm-button',
        cancelButton: 'swal-cancel-button',
      }
    }).then((result) => {
      if (result.isConfirmed) {
        callback();
      }
    });
  },
  customAlert_error: (x) => {
    const style = document.createElement('style');
    style.innerHTML = `
      .swal-confirm-button {
        background-color: #f44336 !important;
        color: #ffffff !important;
      }
    `;
    document.head.appendChild(style);

    Swal.fire({
      title: 'Error',
      text: 'Hubo un error al agregar el ' + x + '. Por favor, intenta nuevamente.',
      icon: 'error',
      confirmButtonText: 'Aceptar',
      customClass: {
        confirmButton: 'swal-confirm-button',
      }
    });
  },
  customAlert_string: (x) => {
    const style = document.createElement('style');
    style.innerHTML = `
      .swal-confirm-button {
        background-color: #f44336 !important;
        color: #ffffff !important;
      }
    `;
    document.head.appendChild(style);

    Swal.fire({
      title: 'Error',
      text: 'Por favor, ingresa solo letras y espacios en el campo ' + x + '.',
      icon: 'error',
      confirmButtonText: 'Aceptar',
      customClass: {
        confirmButton: 'swal-confirm-button',
      }
    });
  },
  customAlert_descripcion: () => {
    const style = document.createElement('style');
    style.innerHTML = `
      .swal-confirm-button {
        background-color: #f44336 !important;
        color: #ffffff !important;
      }
    `;
    document.head.appendChild(style);

    Swal.fire({
      title: 'Error',
      text: 'Por favor, ingresa una descripción.',
      icon: 'error',
      confirmButtonText: 'Aceptar',
      customClass: {
        confirmButton: 'swal-confirm-button',
      }
    });
  },
  customAlert_agregar: (x) => {
    const style = document.createElement('style');
    style.innerHTML = `
      .swal-confirm-button {
        background-color: #4CAF50 !important;
        color: #ffffff !important;
      }
    `;
    document.head.appendChild(style);

    Swal.fire({
      title: '¡Éxito!',
      text: '¡Agregado! Se ha insertado un nuevo ' + x + '.',
      icon: 'success',
      confirmButtonText: 'Aceptar',
      customClass: {
        confirmButton: 'swal-confirm-button',
      }
    });
  },
  customAlert_existente: (x) => {
    const style = document.createElement('style');
    style.innerHTML = `
      .swal-confirm-button {
        background-color: #f44336 !important;
        color: #ffffff !important;
      }
    `;
    document.head.appendChild(style);

    Swal.fire({
      title: 'Error',
      text: 'El ' + x + ' ingresado ya existe. Por favor, ingresa un ' + x + ' diferente.',
      icon: 'error',
      confirmButtonText: 'Aceptar',
      customClass: {
        confirmButton: 'swal-confirm-button',
      }
    });
  },
};

export default Alertas;
