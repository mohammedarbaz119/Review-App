import React, { useState } from 'react';
import {
  MDBBtn,
  MDBContainer,
  MDBCard,
  MDBCardBody,
  MDBInput,
  MDBCheckbox
}
from 'mdb-react-ui-kit';
import { Button } from 'react-bootstrap';
import axiosConfig from '../../api/axiosConfig';
import { useUserContext } from '../../Context/UserContext';
import { useNavigate } from 'react-router-dom';

function Register() {
  const [err,setErr]= useState("")
  const {user,dispatch} = useUserContext()
  const navigate = useNavigate()
  const [formData, setFormData] = useState({
    username: '',
    password: '',
    repeatPassword: '',
    agreeTerms: false
  });

  const handleChange = (e) => {
          setErr("")

    const { name, value, type, checked } = e.target;
    if(name==='agreeTerms' && !e.target.checked){
       setErr("You must agree to the terms and conditions")
  
      }
    

    
  
    const newValue = type === 'checkbox' ? checked : value;

    setFormData({
      ...formData,
      [name]: newValue
    });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    // Handle form submission with the formData
    if(formData.name===''||formData.agreeTerms===false||formData.password===''||formData.repeatPassword===''){
 setErr("please enter all the fields")
      return
    }



    if(formData.password!==formData.repeatPassword){
      console.log(err)

      setErr("Password and Retype password must match")
      return
    }
    axiosConfig.post('user/register',formData).then(res=>{
      dispatch({type:"LOGIN",payload:res.data})
      navigate("/")
  }).catch(err=>{
    setErr(err.response.data)
  })

  };

  return (
    <MDBContainer fluid className='d-flex align-items-center justify-content-center bg-image' style={{backgroundImage: 'url(https://mdbcdn.b-cdn.net/img/Photos/new-templates/search-box/img4.webp)'}}>
      <div className='mask gradient-custom-3'></div>
      <MDBCard className='m-5 text-white my-5 mx-auto bg-dark' >
        <MDBCardBody className='px-5 bg-black text-white ' style={{maxWidth: '600px', borderRadius: '1.2rem'}}>
          <h2 className="text-uppercase text-center mt-3 mb-5">Create an account</h2>
          <form onSubmit={handleSubmit}>
            <MDBInput
              wrapperClass='mb-4'
              label='Your username'
              size='lg'
              id='form2'
              type='text'
              name='username'
              value={formData.username}
              onChange={handleChange}
            />
            <MDBInput
              wrapperClass='mb-4'
              label='Password'
              size='lg'
              id='form3'
              type='password'
              name='password'
              value={formData.password}
              onChange={handleChange}
              
            />
            <MDBInput
              wrapperClass='mb-4'
              label='Repeat your password'
              size='lg'
              id='form4'
              type='password'
              name='repeatPassword'
              value={formData.repeatPassword}
              onChange={handleChange}
            />
            {err&&<p style={{color:'red'}}>{err}</p>}
            
            <div className='d-flex flex-row justify-content-center mb-4'>
              <MDBCheckbox
                name='agreeTerms'
                id='flexCheckDefault'
                label='I agree all statements in Terms of service'
                checked={formData.agreeTerms}
                onChange={handleChange}
              />
            </div>
            <Button className='mb-4 w-100 gradient-custom-4' type='submit' size='lg' disabled={!formData.agreeTerms}>Register</Button>
          </form>
        </MDBCardBody>
      </MDBCard>
    </MDBContainer>
  );
}

export default Register;
