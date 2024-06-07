import axios from 'axios';

export const serviceGetDepartamento = async () => {
  return await axios.post('/referenciales/departamentos/list', {});
};

export const serviceCreateDepartamento = async (payload) => {
  return await axios.post('/referenciales/departamentos/newAction', payload);
};

export const serviceEditDepartamento = async (payload) => {
  return await axios.post('/referenciales/departamentos/save', payload);
};

export const serviceDeleteDepartamento = async (id) => {
  return await axios.post(`/referenciales/departamentos/deleteById`, { id: id });
};

