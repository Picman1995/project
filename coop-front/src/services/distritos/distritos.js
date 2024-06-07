import axios from 'axios';

export const serviceGetDistritos = async () => {
  return await axios.post('/referenciales/distritos/list', {});
};

export const serviceCreateDistritos = async (payload) => {
  return await axios.post('/referenciales/distritos/newAction', payload);
};

export const serviceEditDistritos = async (payload) => {
  return await axios.post('/referenciales/distritos/save', payload);
};

export const serviceDeleteDistritos = async (id) => {
  return await axios.post(`/referenciales/distritos/deleteById`, { id: id });
};

