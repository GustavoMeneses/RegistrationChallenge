import api from '../services/api';
import { Email } from '../interfaces/email';

const createEmail = (email: Email): Promise<Email> => {
  return api.post('email', {...email});
}

const deleteEmail = (id: number) => {
  return api.delete('email', id);
}

export default {createEmail, deleteEmail};
