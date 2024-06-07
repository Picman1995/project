import axios from 'axios';

export const serviceGetTipoDocumentos = async () => {
  return await axios.post('/referenciales/tipoDocumentos/list', {});
};

export const serviceCreateTipoDocumentos = async (payload) => {
  return await axios.post('/referenciales/tipoDocumentos/new', payload);
};

export const serviceEditTipoDocumentos = async (payload) => {
  return await axios.post('/referenciales/tipoDocumentos/save', payload);
};

export const serviceDeleteTipoDocumentos = async (id) => {
  return await axios.post(`/referenciales/tipoDocumentos/deleteById`, { codTipoDocumentos: id });
};

