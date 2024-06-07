import axios from 'axios';

export const serviceGetPersons = async () => {
  return await axios.post('/personas/list', {});
};

export const serviceGetPersonByNroDocumento = async (nroDocumento) => {
  return await axios.post('/personas/getById', { nroDocumento });
};

export const serviceCreatePerson = async (payload) => {
  return await axios.post('/personas/newAction', payload);
};

export const serviceEditPerson = async (payload) => {
  try {
    const response = await axios.post('/personas/save', payload);
    return response.data; // Devolver los datos de la respuesta si es exitosa
  } catch (error) {
    console.error('Error al actualizar la persona:', error.response.data);
    throw error; // Lanzar el error para que pueda ser manejado por el código que llama a esta función
  }
};

export const serviceDeletePerson = async (id) => {
  return await axios.post(`/personas/deleteById`, { id });
};
