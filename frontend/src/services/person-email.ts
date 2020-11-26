import api from '../services/api';

const createPersonEmail = (personEmail: any): Promise<any> => {
  return api.post('person-email', {...personEmail});
}

export default {createPersonEmail};
