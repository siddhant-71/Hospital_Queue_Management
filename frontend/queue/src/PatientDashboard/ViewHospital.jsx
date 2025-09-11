import React from 'react'
import HospitalCard from './HospitalCard';

const ViewHospital = ({hospitalData,doctorsData,setsearched,setdoctorsData}) => {
  return (
    <div className='HospitalContainer'>
        {
            hospitalData.map((hospital)=>{
                return <HospitalCard key={hospital.id} card={hospital} doctorsData={doctorsData} setsearched={setsearched} setdoctorsData={setdoctorsData}/>
            })
        }
    </div>
  )
}

export default ViewHospital