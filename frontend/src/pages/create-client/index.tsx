import React, { useEffect, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom'
import { FormikProvider, useFormik } from 'formik';
import * as Yup from 'yup';
import { Button, TextField, TextFieldProps } from '@material-ui/core';
import InputMask, { Props } from 'react-input-mask';
import personService from '../../services/person';
import addressService from '../../services/address';
import phoneService from '../../services/phone';
import personPhoneService from '../../services/person-phone';
import emailService from '../../services/email';
import personEmailService from '../../services/person-email';
import { Person } from '../../interfaces/person';
import Modal from '@material-ui/core/Modal';
import { createStyles, makeStyles, Theme } from '@material-ui/core/styles';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import { Phone } from '../../interfaces/phone';
import { Email } from '../../interfaces/email';
import ClearIcon from '@material-ui/icons/Clear';

function getModalStyle() {
  return {
    top: '50%',
    left: '50%',
    transform: `translate(-50%, -50%)`,
  };
}

const useStyles = makeStyles((theme: Theme) =>
  createStyles({
    paper: {
      position: 'absolute',
      width: 400,
      backgroundColor: theme.palette.background.paper,
      border: '2px solid #000',
      boxShadow: theme.shadows[5],
      padding: theme.spacing(2, 4, 3),
    },
    erro: {
      position: 'absolute',
      bottom: '62px',
      'font-size': '12px',
      color: 'red',
    },
    info: {
      padding: '5px 10px',
      border: '1px solid black',
      borderRadius: '10px',
      marginBottom: '5px'
    }
  }),
);

const CreateClientPage: React.FC = () => {
    let {id}: any = useParams();
    const history = useHistory();
    const [person, setPerson] = useState<Person>({} as Person)
    const [load, setLoad] = useState(false);

    // Controls phone modal
    const [openPhone, setOpenPhone] = React.useState(false);
    const [phones, setPhones] = React.useState<Phone[]>([]);
    const [newPhone, setNewPhone] = React.useState<any>();
    const [validPhone, setValidPhone] = React.useState(true);
    const [phoneType, setPhoneType] = React.useState(1);

    //Controls email modal
    const [openEmail, setOpenEmail] = React.useState(false);
    const [emails, setEmails] = React.useState<any[]>([]);
    const [newEmail, setNewEmail] = React.useState<any>();
    const [validEmail, setValidEmail] = React.useState(true);

    const classes = useStyles();
    const [modalStyle] = React.useState(getModalStyle);

    // Arrow functions to phone modal
    const openModalPhone = () => {
      setOpenPhone(true);
    };
    const closeModalPhone = () => {
      setOpenPhone(false);
    };
    const confirmPhone = () => {
      if (!phoneType || !newPhone) return;
      if (validPhone) {
        setOpenPhone(false);
        let value = phones;
        if (newPhone) {
          value.push(newPhone);
        }
        setPhones(value);
        setPhoneType(1);
      }
    }
    const validatePhone = (value: any) => {
      if (!phoneType || !value) return;
      const pattern = new RegExp(/^[0-9\b]+$/);
      if (!pattern.test(value)) {
        setValidPhone(false);
      } else if (phoneType === 3 && value.length !== 9) {
        setValidPhone(false);
      } else if ((phoneType === 1 || phoneType === 2) && value.length !== 8) {
        setValidPhone(false);
      } else {
        setValidPhone(true);
        setNewPhone({phone: value, phoneType});
      }
    }
    const handlePhoneType = (value: any) => {
      setPhoneType(value);
    }
    const bodyPhone = (
      <div style={modalStyle} className={classes.paper}>
        <h3 id="simple-modal-title">Adicionar telefone</h3>
        <Select
          labelId="demo-simple-select-label"
          id="demo-simple-select"
          onChange={event => handlePhoneType(event.target.value)}
          value={phoneType}
          style={{display: 'block'}}
        >
          <MenuItem value={1}>Residencial</MenuItem>
          <MenuItem value={2}>Comercial</MenuItem>
          <MenuItem value={3}>Celular</MenuItem>
        </Select>
        <input style={{display: 'block', margin: '20px 0'}} type="text"
               onChange={event => validatePhone(event.target.value)}/>
        {validPhone ? '' : <span className={classes.erro}>Telefone inválido</span>}
        <Button variant="contained" color="primary" onClick={confirmPhone}>Confirmar</Button>
      </div>
    );
    const removePhone = (id: any) => {
      const index = phones.findIndex((phone) => phone.id === id);
      const removed = phones.splice(index, 1);
      if (removed[0].id) {
        phoneService.deletePhone(removed[0].id).then(() => {
        });
      }
      setPhones(phones);
      setLoad(!load);
    }
    const formatPhone = (value: any, type: any) => {
      if (type === 3) {
        return value.substring(0, 5) + '-' + value.substring(5, 9);
      } else {
        return value.substring(0, 4) + '-' + value.substring(4, 8);
      }
    }

    // Arrow functions to email modal
    const openModalEmail = () => {
      setOpenEmail(true);
    };
    const closeModalEmail = () => {
      setOpenEmail(false);
    };
    const confirmEmail = () => {
      if (!newEmail) return;
      if (validEmail) {
        setOpenEmail(false);
        let value = emails;
        if (newEmail) {
          value.push(newEmail);
        }
        setEmails(value);
      }
    }
    const validateEmail = (value: any) => {
      if (!value) return;
      const pattern = new RegExp(/^(("[\w-\s]+")|([\w-]+(?:\.[\w-]+)*)|("[\w-\s]+")([\w-]+(?:\.[\w-]+)*))(@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$)|(@\[?((25[0-5]\.|2[0-4][0-9]\.|1[0-9]{2}\.|[0-9]{1,2}\.))((25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\.){2}(25[0-5]|2[0-4][0-9]|1[0-9]{2}|[0-9]{1,2})\]?$)/i);
      if (!pattern.test(value)) {
        setValidEmail(false);
      } else {
        setValidEmail(true);
        setNewEmail({email: value});
      }

    }
    const bodyEmail = (
      <div style={modalStyle} className={classes.paper}>
        <h3 id="simple-modal-title">Adicionar e-mail</h3>
        <input style={{display: 'block', margin: '20px 0'}} type="text"
               onChange={event => validateEmail(event.target.value)}/>
        {validEmail ? '' : <span className={classes.erro}>E-mail inválido</span>}
        <Button color="primary" variant="contained" onClick={confirmEmail}>Confirmar</Button>
      </div>
    );
    const removeEmail = (id: any) => {
      const index = emails.findIndex((email) => email.id === id);
      const removed = emails.splice(index, 1);
      if (removed[0].id) {
        emailService.deleteEmail(removed[0].id).then(() => {
        });
      }
      setEmails(emails);
      setLoad(!load);
    }

    const formik = useFormik({
      initialValues: person,
      validationSchema: Yup.object({
        name: Yup.string()
          .max(100, 'Máximo de 100 caracteres')
          .min(3, 'Mínimo de 3 caracteres')
          .matches(/^[a-z0-9 ]+$/i, 'Nome deve conter apenas caracteres alfanuméricos')
          .required('Campo obrigatório'),
        socialSecurityNumber: Yup.string().required('Campo obrigatório'),
        address: Yup.object({
          zipCode: Yup.string().required('CEP é obrigatório'),
          publicPlace: Yup.string().required('Logradouro é obrigatório'),
          neighborhood: Yup.string().required('Bairro é obrigatório'),
          city: Yup.string().required('Cidade é obrigatória'),
          fu: Yup.string().required('UF é obrigatório'),
          complement: Yup.string()
        })
      }),
      onSubmit: values => {
        if (!phones.length || !emails.length) {
          return;
        }
        let idPerson: any = null;
        values.address.zipCode = values.address.zipCode.replace(/\D/g, '');

        let sub = null;
        if (id) {
          sub = addressService.updateAddress(values.address, person.address.id)
            .then(() => {
              return personService.getPerson(id);
            }).then((response) => {
              response.name = values.name;
              response.socialSecurityNumber = values.socialSecurityNumber;
              return personService.updatePerson(response, response.id);
            });
        } else {
          sub = addressService.createAddress(values.address)
            .then((newAddress) => {
              const person: any = {
                name: values.name,
                socialSecurityNumber: values.socialSecurityNumber,
                address: {
                  id: newAddress.id
                }
              }
              return personService.createPerson(person);
            });
        }

        sub.then((newPerson) => {
          idPerson = newPerson.id;
          let phonesList = [];
          for (let value of phones) {
            if (value.id) continue;
            const phoneObj: any = {
              phone: value.phone,
              phoneType: value.phoneType
            };
            phonesList.push(phoneService.createPhone(phoneObj));
          }
          return Promise.all(phonesList);
        }).then((newPhones: Phone[]) => {
          let phonePersonList = [];
          for (let value of newPhones) {
            const phonePersonObj: any = {
              phone: {
                id: value.id
              },
              person: {
                id: idPerson
              }
            };
            phonePersonList.push(personPhoneService.createPersonPhone(phonePersonObj));
          }
          return Promise.all(phonePersonList);
        }).then(() => {
          let emailsList = [];
          for (let value of emails) {
            if (value.id) continue;
            const emailObj: any = {
              email: value.email
            };
            emailsList.push(emailService.createEmail(emailObj));
          }
          return Promise.all(emailsList);
        }).then((newEmails: Email[]) => {
          let emailPersonList = [];
          for (let value of newEmails) {
            const emailPersonObj: any = {
              email: {
                id: value.id
              },
              person: {
                id: idPerson
              }
            };
            emailPersonList.push(personEmailService.createPersonEmail(emailPersonObj));
          }
          return Promise.all(emailPersonList);
        }).then(() => {
          history.push('/');
        }).catch((error) => {
          throw new Error(error);
        });


      },
    });

    useEffect(() => {
      if (id) {
        personService.getPerson(id).then((response) => {
          setPerson(response);
          formik.setValues(response);
          setPhones(response.phones);
          setEmails(response.emails);
        })
      }
    }, [id]);

    const onChangeCep = (event: any) => {
      const cep = event.target.value.replace(/\D/g, '');
      if (cep.length === 8) {
        addressService.getCep(cep).then((response) => {
          formik.setFieldValue('address.publicPlace', response.logradouro, true);
          formik.setFieldValue('address.neighborhood', response.bairro, true);
          formik.setFieldValue('address.city', response.localidade, true);
          formik.setFieldValue('address.fu', response.uf, true);
          formik.setFieldValue('address.complement', response.complemento, true);
        })
      }
    };

    const returnPage = () => {
      history.push('/');
    }

    return (
      <div style={{margin: '10%'}}>
        <FormikProvider value={formik}>
          <form onSubmit={formik.handleSubmit}>
            <TextField
              fullWidth
              id="name"
              name="name"
              label="Nome"
              InputLabelProps={{
                shrink: true,
              }}
              value={formik.values.name}
              onChange={formik.handleChange}
              error={formik.touched.name && Boolean(formik.errors.name)}
              helperText={formik.touched.name && formik.errors.name}
            />

            <InputMask
              mask="999.999.999-99"
              value={formik.values.socialSecurityNumber}
              onChange={formik.handleChange}
            >
              {(inputProps: Props & TextFieldProps) =>
                <TextField
                  {...inputProps}
                  type="tel"
                  label="CPF"
                  fullWidth
                  InputLabelProps={{
                    shrink: true,
                  }}
                  id="socialSecurityNumber"
                  name="socialSecurityNumber"
                  value={formik.values.socialSecurityNumber}
                  error={formik.touched.socialSecurityNumber && Boolean(formik.errors.socialSecurityNumber)}
                  helperText={formik.touched.socialSecurityNumber && formik.errors.socialSecurityNumber}
                />
              }
            </InputMask>
            <InputMask
              mask="99999-999"
              value={formik.values.address?.zipCode}
              onChange={formik.handleChange}
              onBlur={onChangeCep}
            >
              {(inputProps: Props & TextFieldProps) =>
                <TextField
                  {...inputProps}
                  InputLabelProps={{
                    shrink: true,
                  }}
                  type="tel"
                  label="CEP"
                  fullWidth
                  id="address.zipCode"
                  name="address.zipCode"
                  error={formik.touched.address?.zipCode && Boolean(formik.errors.address?.zipCode)}
                  helperText={formik.touched.address?.zipCode && formik.errors.address?.zipCode}
                />
              }
            </InputMask>
            <TextField
              fullWidth
              InputLabelProps={{
                shrink: true,
              }}
              id="address.publicPlace"
              name="address.publicPlace"
              label="Logradouro"
              value={formik.values.address?.publicPlace}
              onChange={formik.handleChange}
              error={formik.touched.address?.publicPlace && Boolean(formik.errors.address?.publicPlace)}
              helperText={formik.touched.address?.publicPlace && formik.errors.address?.publicPlace}
            />
            <TextField
              fullWidth
              InputLabelProps={{
                shrink: true,
              }}
              id="address.neighborhood"
              name="address.neighborhood"
              label="Bairro"
              value={formik.values.address?.neighborhood}
              onChange={formik.handleChange}
              error={formik.touched.address?.neighborhood && Boolean(formik.errors.address?.neighborhood)}
              helperText={formik.touched.address?.neighborhood && formik.errors.address?.neighborhood}
            />
            <TextField
              fullWidth
              InputLabelProps={{
                shrink: true,
              }}
              id="address.city"
              name="address.city"
              label="Cidade"
              value={formik.values.address?.city}
              onChange={formik.handleChange}
              error={formik.touched.address?.city && Boolean(formik.errors.address?.city)}
              helperText={formik.touched.address?.city && formik.errors.address?.city}
            />
            <TextField
              fullWidth
              InputLabelProps={{
                shrink: true,
              }}
              id="address.fu"
              name="address.fu"
              label="UF"
              value={formik.values.address?.fu}
              onChange={formik.handleChange}
              error={formik.touched.address?.fu && Boolean(formik.errors.address?.fu)}
              helperText={formik.touched.address?.fu && formik.errors.address?.fu}
            />
            <TextField
              fullWidth
              InputLabelProps={{
                shrink: true,
              }}
              id="address.complement"
              name="address.complement"
              label="Complemento"
              value={formik.values.address?.complement}
              onChange={formik.handleChange}
              error={formik.touched.address?.complement && Boolean(formik.errors.address?.complement)}
              helperText={formik.touched.address?.complement && formik.errors.address?.complement}
            />
            <div style={{display: 'flex', justifyContent: 'space-around', marginTop: '50px'}}>
              <div>
                <Button style={{marginBottom: '10px'}} variant="outlined" color="primary" onClick={openModalPhone}>
                  Adicionar telefone
                </Button>
                {
                  phones.map((value, index) => {
                    return (
                      <div key={index} style={{position: 'relative'}} className={classes.info}>
                        {value.phoneType === 1 ? <div>Residencial</div> : ''}
                        {value.phoneType === 2 ? <div>Comercial</div> : ''}
                        {value.phoneType === 3 ? <div>Celular</div> : ''}
                        {formatPhone(value.phone, value.phoneType)}
                        <Button style={{position: 'absolute', right: '-12px', top: '11px'}}
                                onClick={() => removePhone(index)}><ClearIcon/></Button>
                      </div>
                    )
                  })
                }
              </div>
              <div>
                <Button style={{marginBottom: '10px'}} variant="outlined" color="primary" onClick={openModalEmail}>
                  Adicionar e-mail
                </Button>
                {
                  emails.map((value, index) => {
                    return (
                      <div className={classes.info} style={{position: 'relative'}} key={index}>
                        {value.email}
                        <Button style={{position: 'absolute', right: '-12px', top: '-1px'}}
                                onClick={() => removeEmail(index)}><ClearIcon/></Button>
                      </div>
                    )
                  })
                }
              </div>
            </div>
            <div style={{display: 'flex', justifyContent: 'space-between'}}>
              <Button style={{width: '200px', marginTop: '50px'}} color="primary" size="large" variant="contained"
                      onClick={() => returnPage()}>
                Voltar
              </Button>
              <Button style={{width: '200px', marginTop: '50px'}} color="primary" size="large" variant="contained"
                      type="submit">
                {id ? 'Atualizar' : 'Cadastrar'}
              </Button>
            </div>
          </form>
        </FormikProvider>
        <Modal
          open={openPhone}
          onClose={closeModalPhone}
          aria-labelledby="simple-modal-title"
          aria-describedby="simple-modal-description"
        >
          {bodyPhone}
        </Modal>
        <Modal
          open={openEmail}
          onClose={closeModalEmail}
          aria-labelledby="simple-modal-title"
          aria-describedby="simple-modal-description"
        >
          {bodyEmail}
        </Modal>
      </div>
    );
  }
;

export default CreateClientPage;
