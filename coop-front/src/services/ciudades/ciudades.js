import axios from 'axios';

export const serviceGetCiudades = async () => {
  return await axios.post('/referenciales/ciudades/list', {});
};

export const serviceCreateCiudades = async (payload) => {
  return await axios.post('/referenciales/ciudades/newAction', payload);
};

export const serviceEditCiudades= async (payload) => {
  return await axios.post('/referenciales/ciudades/save', payload);
};

export const serviceDeleteCiudades = async (id) => {
  return await axios.post(`/referenciales/ciudades/deleteById`, { id: id });
};

