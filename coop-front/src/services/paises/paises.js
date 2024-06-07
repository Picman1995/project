import axios from 'axios';

export const serviceGetCountries = async () => {
  return await axios.post('/referenciales/paises/list', {});
};

export const serviceCreateCountry = async (payload) => {
  return await axios.post('/referenciales/paises/new', payload);
};

export const serviceEditCountry = async (payload) => {
  return await axios.post('/referenciales/paises/save', payload);
};

export const serviceDeleteCountry = async (id) => {
  return await axios.post(`/referenciales/paises/deleteById`, { codPais: id });
};

