import React from 'react'

const Doctor = ({doctor,setslot,slot,setindividual}) => {
    function setAllSlot(){
        setindividual(doctor);
        setslot(doctor.slots);
        slot(4);
    }
  return (
    <div className='doctor'>
        <div>Name</div>
        <div>Specialization , department</div>
        <div>degrees</div>
        <div>age , Gender</div>
        <button onClick={setAllSlot}>Book Appointment</button>
    </div>
  )
}

export default Doctor