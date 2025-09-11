import React from 'react'
import { useNavigate } from 'react-router-dom';

const DoctorHeader = () => {
  const navigate=useNavigate();
  function handleLogout(){
    localStorage.clear();
    navigate('/')
  }
  return (
    <div className='HeaderDoctor'>
      {/* Get Elements from the localStorage */}
        <div>{localStorage.getItem('name')}</div>
        <div>{localStorage.getItem('age')}</div>
        <div>{localStorage.getItem('specialization')}</div>
        <div>{localStorage.getItem('degrees')}</div>
        <div>{localStorage.getItem('hospitalName')}</div>
        <button className='Lgbtn' onClick={handleLogout}>Logout</button>
    </div>
  )
}

export default DoctorHeader