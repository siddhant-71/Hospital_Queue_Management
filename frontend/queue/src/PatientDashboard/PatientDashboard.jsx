import React from 'react'
import "./PatientDashboard.css"
import PatientBody from './PatientBody'
import { useNavigate } from 'react-router-dom'
const PatientDashboard = () => {
  const navigate=useNavigate();
    const name=localStorage.getItem('name')
    const age=localStorage.getItem('age')
    const email=localStorage.getItem('email')
    const id=localStorage.getItem('id')
    const bloodGroup=localStorage.getItem('bloodGroup')
    const gender=localStorage.getItem('gender')
    const phoneNo=localStorage.getItem('phoneNo')
    function logout(){
      localStorage.clear();
      navigate('/')
    }
  return (
    <div className='PatientDashboard'>
        <div className='Header'>
            <p>{localStorage.getItem("name")} </p>
            <p>{localStorage.getItem("email")}</p>
            <p>{localStorage.getItem("phoneNo")}</p>
            <p>{localStorage.getItem("age")}</p>
            <p>{localStorage.getItem("bloodGroup")}</p>
            <button onClick={logout}>LogOut</button>
        </div>
        <PatientBody/>
        <div>Footer</div>
    </div>
  )
}

export default PatientDashboard