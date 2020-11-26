import React, { useState } from 'react';
import './style.scss';
import { useAuth } from '../../context/auth';
import { Redirect } from 'react-router';
import { Card, Form, Button, Spinner, Toast } from 'react-bootstrap';


const LoginPage: React.FC = () => {
  const { signed, signIn } = useAuth();

  const [login, setLogin] = useState<string>();
  const [senha, setSenha] = useState<string>();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(false);

  const handleSubmit = (event: any) => {
    const form = event.currentTarget;
    if (!login || ! senha) {
      event.preventDefault();
      event.stopPropagation();
      return;
    }
    setLoading(true);
    signIn(login, senha).then(() => {
      setLoading(false);
    }, () =>{
      setError(true);
      setLoading(false);
      setTimeout(() => {
        setError(false);
      }, 1000);
    });
    event.preventDefault();
    event.stopPropagation();
  };

  return (
    signed ? <Redirect to='/' /> :
      <div className="wrapper">
        <Card className="card-wrapper">
          <Form className="form" onSubmit={handleSubmit}>
            <Form.Group controlId="login">
              <Form.Label>Login</Form.Label>
              <Form.Control type="text" required placeholder="Digite seu login" onChange={(event: any) => {setLogin(event.target.value)}}/>
            </Form.Group>

            <Form.Group controlId="senha">
              <Form.Label>Senha</Form.Label>
              <Form.Control type="password" required placeholder="Senha" onChange={(event: any) => {setSenha(event.target.value)}} />
              <span className="help-block">Digite sua senha</span>
            </Form.Group>
            <Button variant="primary" type="submit">
              {loading && <Spinner
                as="span"
                animation="grow"
                size="sm"
                role="status"
                aria-hidden="true"
              />}
              Entrar
            </Button>
          </Form>
          <div style={{height: 90, marginTop: 20}}>
            {error && <Toast>
              <Toast.Header>
                <strong className="mr-auto">Atenção!</strong>
              </Toast.Header>
              <Toast.Body>Não foi possível autenticar-se com as credenciais fornecidas.</Toast.Body>
            </Toast>}
          </div>
        </Card>
      </div>
  );
};

export default LoginPage;
