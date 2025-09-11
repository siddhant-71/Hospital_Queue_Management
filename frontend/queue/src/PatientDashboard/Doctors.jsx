import React from 'react'
import Doctor from './Doctor';

const Doctors = ({doctorsData,setslot,slot,setindividual}) => {
  return (
    <div className='doctors'>
        {doctorsData.map((doctor)=>{
            return <Doctor key={doctor.id} doctor={doctor} setslot={setslot} slot={slot} setindividual={setindividual}/>
        })}
    </div>
  )
}

export default Doctors