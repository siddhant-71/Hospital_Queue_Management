import React from 'react'

const Doctor = ({doctor,setslot,slot,setindividual}) => {
    function setAllSlot(){
        setindividual(doctor);
        setslot(doctor.slots);
        slot(4);
    }
  return (
    <div className='doctor'>
        <div>{doctor.name}</div>
        <div>{doctor.specialization} , {doctor.department}</div>
        <div>{doctor.degrees}</div>
        <div>{doctor.age} , {doctor.gender}</div>
        <button onClick={setAllSlot}>Book Appointment</button>
    </div>
  )
}

export default Doctor