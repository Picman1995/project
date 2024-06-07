import axios from 'axios';

export const serviceGetTipoPersonas = async () => {
  return await axios.post('/referenciales/tipoPersonas/list', {});
};

export const serviceCreateTipoPersonas = async (payload) => {
  return await axios.post('/referenciales/tipoPersonas/new', payload);
};

export const serviceEditTipoPersonas = async (payload) => {
  return await axios.post('/referenciales/tipoPersonas/save', payload);
};

export const serviceDeleteTipoPersonas = async (id) => {
  return await axios.post(`/referenciales/tipoPersonas/deleteById`, { codTipoPersonas: id });
};

