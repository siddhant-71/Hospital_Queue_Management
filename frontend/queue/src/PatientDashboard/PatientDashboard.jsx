import React from 'react'
import "./PatientDashboard.css"
import PatientBody from './PatientBody'
const PatientDashboard = () => {
    const name=localStorage.getItem('name')
    const age=localStorage.getItem('age')
    const email=localStorage.getItem('email')
    const id=localStorage.getItem('id')
    const bloodGroup=localStorage.getItem('bloodGroup')
    const gender=localStorage.getItem('gender')
    const phoneNo=localStorage.getItem('phoneNo')
  return (
    <div className='PatientDashboard'>
        <div className='Header'>
            <p>name </p>
            <p>Email</p>
            <p>Phone</p>
            <p>age</p>
            <p>BloodG</p>
            <button>LogOut</button>
        </div>
        <PatientBody/>
        <div>Footer</div>
    </div>
  )
}

export default PatientDashboard