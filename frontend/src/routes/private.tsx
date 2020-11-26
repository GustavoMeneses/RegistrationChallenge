import React, { Component } from 'react';
import { Redirect, Route } from 'react-router-dom';
import { useAuth } from '../context/auth';
import Spinner from 'react-bootstrap/Spinner';
import './loading.scss';

const PrivateRoute = ({ component: Component, ...rest }: any) => {
  const { signed, loading } = useAuth();
  return (
    loading ? <div className="loading">
        <Spinner animation="border" />
      </div> :
      <Route {...rest} render={(props) => (
        signed
          ? <Component {...props} />
          : <Redirect to='/login' />
      )} />
  )
};

export default PrivateRoute;
