import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import personService from '../../services/person';
import { Person } from '../../interfaces/person';
import { createStyles, makeStyles, Theme, withStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableContainer from '@material-ui/core/TableContainer';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';
import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';
import Button from '@material-ui/core/Button';

const StyledTableCell = withStyles((theme: Theme) =>
  createStyles({
    head: {
      backgroundColor: theme.palette.info.dark,
      color: theme.palette.common.white,
    },
    body: {
      fontSize: 14,
    },
  }),
)(TableCell);

const StyledTableRow = withStyles((theme: Theme) =>
  createStyles({
    root: {
      '&:nth-of-type(odd)': {
        backgroundColor: theme.palette.action.hover,
      },
    },
  }),
)(TableRow);

const useStyles = makeStyles({
  table: {
    minWidth: 700,
  },
});

const ClientPage: React.FC = () => {

  const history = useHistory();

  const [people, setPeople] = useState<Person[]>([]);
  const [load, setLoad] = useState(false);
  const classes = useStyles();

  const edit = (person: Person) => {
    history.push('/client/' + person.id);
  };

  const remove = (person: Person) => {
    personService.deletePerson(person.id)
      .then((response) => {
        setLoad(!load);
      })
  };

  const create = () => {
    history.push('/client');
  };

  const formatPhone = (value: any, type: any) => {
    if (type === 3) {
      return value.substring(0, 5) + '-' + value.substring(5, 9);
    } else {
      return value.substring(0, 4) + '-' + value.substring(4, 8);
    }
  }

  useEffect(() => {
    personService.getPeople().then((response) => {
      setPeople(response.content);
    }).catch((error) => {
      console.log(error);
    })
  }, [load]);

  return (<div style={{margin: '10%'}}>
      <TableContainer component={Paper}>
        <Table className={classes.table} aria-label="customized table">
          <TableHead>
            <TableRow>
              <StyledTableCell>Nome</StyledTableCell>
              <StyledTableCell>CPF</StyledTableCell>
              <StyledTableCell>Endereço</StyledTableCell>
              <StyledTableCell>E-mail(s)</StyledTableCell>
              <StyledTableCell>Telefone(s)</StyledTableCell>
              <StyledTableCell>&nbsp;</StyledTableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {people.map((person) => (
              <StyledTableRow key={person.id}>
                <StyledTableCell component="th" scope="row">
                  {person.name}
                </StyledTableCell>
                <StyledTableCell>{person.socialSecurityNumber}</StyledTableCell>
                <StyledTableCell>
                  <div><strong>CEP:</strong> {person.address.zipCode}</div>
                  <div><strong>Logradouro:</strong> {person.address.publicPlace}</div>
                  <div><strong>Bairro:</strong> {person.address.neighborhood}</div>
                  <div><strong>Cidade:</strong> {person.address.city}</div>
                  <div><strong>UF:</strong> {person.address.fu}</div>
                  <div><strong>Complemento:</strong> {person.address.complement}</div>
                </StyledTableCell>
                <StyledTableCell>
                  {person.emails.map((value) => {
                    return <div key={value.id}>{value.email}</div>
                  })}
                </StyledTableCell>
                <StyledTableCell>
                  {person.phones.map((value) => {
                    return (
                      <div key={value.id}>
                        {value.phoneType === 1 ? <div><strong>Residencial:</strong></div> : ''}
                        {value.phoneType === 2 ? <div><strong>Comercial:</strong></div> : ''}
                        {value.phoneType === 3 ? <div><strong>Celular:</strong></div> : ''}
                        <div>
                          {formatPhone(value.phone, value.phoneType)}
                        </div>
                      </div>
                    )
                  })}
                </StyledTableCell>
                <StyledTableCell>
                  <IconButton aria-label="delete" size="small" onClick={() => edit(person)}>
                    <EditIcon/>
                  </IconButton>
                  <IconButton aria-label="delete" size="small" onClick={() => remove(person)}>
                    <DeleteIcon/>
                  </IconButton>
                </StyledTableCell>
              </StyledTableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      <Button style={{marginTop: '15px'}} size="large" onClick={() => create()} variant="contained" color="primary">
        Cadastrar cliente
      </Button>
    </div>
  )
};


export default ClientPage;
