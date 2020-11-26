import React, { createContext, useContext, useEffect, useState } from 'react';
import api from '../services/api';
import { AuthContextData } from '../interfaces/auth';
import { User } from '../interfaces/user';

const AuthContext = createContext<AuthContextData>({} as AuthContextData);

const AuthProvider: React.FC = ({ children }) => {
  useEffect(() => {
    const stringUser = localStorage.getItem('user');
    if (stringUser) {
      const storagedUser: User = JSON.parse(stringUser);
      setUser(storagedUser);
      api.axiosInstance.defaults.headers.Authorization = `${storagedUser.access_token}`;
    }
    setLoading(false);
  }, []);

  const [user, setUser] = useState<null | User>(null);
  const [loading, setLoading] = useState(true);

  const signIn = (login: string, password: string) => {
    return api.login<User>('/login', {
      login, password
    }).then((response) => {
      setUser(response);
      api.axiosInstance.defaults.headers.Authorization = response?.access_token;
      localStorage.setItem('user', JSON.stringify(response));
      return response;
    }).catch((error) => {
      throw new Error(error);
    })
  };

  function signOut() {
    localStorage.removeItem('user');
    setUser(null);
  }

  return (
    <AuthContext.Provider value={{ signed: user !== null, user, loading, signIn, signOut }}>
      {children}
    </AuthContext.Provider>
  );
};

function useAuth() {
  const context = useContext(AuthContext);

  if (!context) {
    throw new Error('useAuth must be used within an AuthProvider.');
  }

  return context;
}

export { AuthProvider, useAuth };
