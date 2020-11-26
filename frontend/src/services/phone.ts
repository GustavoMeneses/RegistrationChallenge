import api from '../services/api';
import { Phone } from '../interfaces/phone';

const createPhone = (phone: Phone): Promise<Phone> => {
  return api.post('phone', {...phone});
}

const deletePhone = (id: number) => {
  return api.delete('phone', id);
}

export default {createPhone, deletePhone};
