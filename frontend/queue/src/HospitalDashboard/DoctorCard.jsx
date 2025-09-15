import axios from 'axios'
import React from 'react'

const DoctorCard = ({doctor}) => {
    console.log(doctor)
  return (
    <div className='card'>
        <div>Name - {doctor.name}</div>
        <hr />
        <div>Specialization - {doctor.specialization}</div>
        <hr />
        <div>Department - {doctor.department}</div>
        <hr />
        <div>Degrees - {doctor.degrees}</div>
        <hr />
        <div>
            ADDRESS - {doctor.street} {doctor.area} {doctor.pincode}
        </div>
        <div>
            {doctor.city} {doctor.state} {doctor.country}
        </div>
    </div>
  )
}

export default DoctorCard