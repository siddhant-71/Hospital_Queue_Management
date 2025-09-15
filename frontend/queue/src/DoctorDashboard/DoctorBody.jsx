import React from 'react'
import './Doctor.css'
import DoctorHeader from './DoctorHeader'
import DoctorSlots from './DoctorSlots'
import DoctorFooter from './DoctorFooter'
const DoctorBody = () => {
  return (
    <div className='doctorPage'>
      <DoctorHeader/>
      <DoctorSlots/>
      <DoctorFooter/>
    </div>
  )
}

export default DoctorBody