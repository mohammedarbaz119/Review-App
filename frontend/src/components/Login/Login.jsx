import React, { useState } from 'react';
import {
  MDBBtn,
  MDBContainer,
  MDBRow,
  MDBCol,
  MDBCard,
  MDBCardBody,
  MDBInput,
  MDBIcon
}
from 'mdb-react-ui-kit';
import { Link, useNavigate } from 'react-router-dom';
import axiosConfig from '../../api/axiosConfig';
import { useUserContext } from '../../Context/UserContext';
import { Button } from 'react-bootstrap';

export default function Login() {
  const navigate = useNavigate()
const [err,setErr] = useState("")
  const [formData, setFormData] = useState({
    username: '',
    password: '',
  });
  const {user,dispatch} = useUserContext();
  const handleChange = (e) => {
    setErr("")
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();

    if(formData.username===''||formData.password===''){      
      return
    }
    // Handle form submission with the formData
    axiosConfig.post('user/login',formData).then(res=>{
        dispatch({type:"LOGIN",payload:res.data})
        navigate("/")
        
    }).catch(err=>setErr(err.response.data))
  };

  return (
    <MDBContainer fluid>
      <MDBRow className='d-flex justify-content-center align-items-center h-100'>
        <MDBCol col='12'>
          <MDBCard className='bg-dark text-white my-5 mx-auto' style={{ borderRadius: '1rem', maxWidth: '400px' }}>
            <MDBCardBody className='p-5 d-flex flex-column align-items-center mx-auto w-100'>
              <h2 className="fw-bold mb-2 text-uppercase">Login</h2>
              <p className="text-white-50 mb-5">Please enter your login and password!</p>
              <form onSubmit={handleSubmit}>
                <MDBInput
                  wrapperClass='mb-4  w-100'
                  labelClass='text-white'
                  label='Username'
                  id='username'
                  type='text'
                  size="lg"
                  name='username'
                  value={formData.username}
                  onChange={handleChange}
                />
                <MDBInput
                  wrapperClass='mb-4 w-100'
                  labelClass='text-white'
                  label='Password'
                  id='password'
                  type='password'
                  size="lg"
                  name='password'
                  value={formData.password}
                  onChange={handleChange}
                />
                <Button  className='mx-5 px-5 bg-white text-black' type='submit' size='lg'>
                  Login
                </Button>
              </form>
            {err&&<p style={{color:'red',margin:'10px'}}>{err}</p>}

              <div className='d-flex flex-row mt-3 mb-5'>
                <MDBBtn tag='a' color='none' className='m-3' style={{ color: 'white' }}>
                  <MDBIcon fab icon='facebook-f' size="lg"/>
                </MDBBtn>
                <MDBBtn tag='a' color='none' className='m-3' style={{ color: 'white' }}>
                  <MDBIcon fab icon='twitter' size="lg"/>
                </MDBBtn>
                <MDBBtn tag='a' color='none' className='m-3' style={{ color: 'white' }}>
                  <MDBIcon fab icon='google' size="lg"/>
                </MDBBtn>
              </div>
              <div>
                <p className="mb-0">Don&apost have an account? <Link to="/register">Sign up</Link></p>
              </div>
            </MDBCardBody>
          </MDBCard>
        </MDBCol>
      </MDBRow>
    </MDBContainer>
  );
}

