import axios from 'axios';

export const serviceGetNacionalidades = async () => {
  return await axios.post('/referenciales/nacionalidades/list', {});
};

export const serviceCreateNacionalidades = async (payload) => {
  return await axios.post('/referenciales/nacionalidades/newAction', payload);
};

export const serviceEditNacionalidades = async (payload) => {
  return await axios.post('/referenciales/nacionalidades/edit', payload);
};

export const serviceDeleteNacionalidades = async (id) => {
  return await axios.post(`/referenciales/nacionalidades/deleteById`, { id });
};
