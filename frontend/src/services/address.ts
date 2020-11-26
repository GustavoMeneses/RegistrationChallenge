import api from '../services/api';
import { Cep } from '../interfaces/cep';
import { Address } from '../interfaces/address';

const createAddress = (address: Address): Promise<Address> => {
  return api.post('address', {...address});
}

const updateAddress = (address: Address, id: number): Promise<Address> => {
  return api.put(`address/${id}`, {...address})
}

const getCep = (cep: string): Promise<Cep> => {
  return api.get(`address/cep/${cep}`);
}

export default {createAddress, updateAddress, getCep};
