import axios from 'axios';

export const serviceGetEstCivil = async () => {
  return await axios.post('/referenciales/estadoCiviles/list', {});
};

export const serviceCreateEstCivil = async (payload) => {
  return await axios.post('/referenciales/estadoCiviles/new', payload);
};

export const serviceEditEstCivil = async (payload) => {
  return await axios.post('/referenciales/estadoCiviles/save', payload);
};

export const serviceDeleteEstCivil = async (id) => {
  return await axios.post(`/referenciales/estadoCiviles/deleteById`, { codEstadoCiviles: id });
};

