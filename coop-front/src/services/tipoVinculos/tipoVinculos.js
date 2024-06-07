import axios from 'axios';

export const serviceGetTipoVinculos = async () => {
  return await axios.post('/referenciales/tipoVinculos/list', {});
};

export const serviceCreateTipoVinculos = async (payload) => {
  return await axios.post('/referenciales/tipoVinculos/new', payload);
};

export const serviceEditTipoVinculos = async (payload) => {
  return await axios.post('/referenciales/tipoVinculos/save', payload);
};

export const serviceDeleteTipoVinculos = async (id) => {
  return await axios.post(`/referenciales/tipoVinculos/deleteById`, { codTipoVinculos: id });
};

