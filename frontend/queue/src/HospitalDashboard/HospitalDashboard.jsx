import React from 'react'
import { useLocation, useNavigate } from 'react-router-dom';
import DoctorCard from './DoctorCard';
import HospitalHeader from './HospitalHeader';
import "./Hospital.css"

const HospitalDashboard = () => {
  const location=useLocation();
  const navigate=useNavigate();
  const {doctors}=location.state;
  function logout(){
    navigate("/")
  }
  return (
    <div>
      <div className='headd'>
        <h1>{doctors[0].hospital.name}</h1>
        <button className='lg' onClick={logout}>LOGOUT</button>
      </div>
      <h2>Doctors List</h2>
      <div className="doctor-list">
        {doctors.map((doc, index) => (
          <DoctorCard key={index} doctor={doc} />
        ))}
      </div>
    </div>
  )
}

export default HospitalDashboard